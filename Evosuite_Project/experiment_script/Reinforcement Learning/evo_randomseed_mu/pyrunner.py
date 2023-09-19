import pandas as pd
import os
import numpy as np
import random
import zipfile

error_file = 'error.txt'
# Function to recursively find all .class files in a directory
def find_class_files(root_dir):
    class_files = []
    for root, dirs, files in os.walk(root_dir):
        for file in files:
            if file.endswith('.class'):
                class_files.append(os.path.join(root, file))
    return class_files


def listjar(root_dir):
    jar_files = []
    jar_paths = []
    for root, dirs, files in os.walk(root_dir):
        for file in files:
            if file.endswith('.jar'):
                jar_files.append(os.path.join(root, file))
                jar_paths.append(os.path.dirname(os.path.join(root, file)))
    return jar_files, jar_paths

# Root directory containing all .class files
root_directory = r'E:\google\mu\evosuite-landscape\shaded\target\classfile_new_temp'

jar_files, jar_paths = listjar(root_directory)

for jar_file, jar_path in zip(jar_files, jar_paths):
    try:
        if not os.path.exists(jar_path + '\classes'):
            os.makedirs(jar_path + '\classes')
        with zipfile.ZipFile(jar_file, 'r') as zip_ref:
            zip_ref.extractall(jar_path + '\classes')

        classpath = jar_path + '\classes'
        class_files = find_class_files(classpath)

        for class_file in class_files:
            # Convert class file path to class name in JVM format
            relative_class_name = class_file[len(root_directory) + 1:-6].replace('\\', '.')
            package, class_name = os.path.split(relative_class_name)  # Split package and class name
            class_name_parts = class_name.split('.')  # Split class name into parts
            if len(class_name_parts) > 1:
                class_name = '.'.join(class_name_parts[2:])  # Join parts after the first one
            print(f"Going to generate test cases for class: {class_name}")

            with open('swap.txt', 'w') as file:
                file.write("0.5")

            os.system(f'java -jar evosuite_rl.jar -class {class_name} -projectCP ' + classpath)

            class RLModel():

                def __init__(self, num_states, num_actions):
                    self.num_states = num_states
                    self.num_actions = num_actions
                    self.q_table = np.zeros((num_states, num_actions))
                    self.state = 0
                    self.action = 0
                    self.previous_coverage = 0.5
                    self.previous_crossover_rate = 0.5

                def calculate_reward(self, current_coverage, current_crossover):
                    coverage_increase = current_coverage - self.previous_coverage
                    crossover_change = current_crossover - self.previous_crossover_rate
                    reward = coverage_increase + 0.2 * coverage_increase ** 2 - 0.1 * abs(crossover_change)
                    return reward

                def update_states(self, new_state):
                    self.state = new_state

                def choose_action(self):
                    if random.random() < 0.1:
                        return random.choice(range(self.num_actions))
                    else:
                        return np.argmax(self.q_table[self.state])

                def update_q_table(self, reward, next_state):
                    learning_rate = 0.05
                    discount_factor = 0.9

                    current_q = self.q_table[self.state, self.action]
                    max_next_q = np.max(self.q_table[next_state])
                    new_q = (1 - learning_rate) * current_q + learning_rate * (reward + discount_factor * max_next_q)
                    self.q_table[self.state, self.action] = new_q

                def run_episode(self, numq, numcr):
                    next_state = 0 if self.previous_coverage > numq else 1
                    self.action = self.choose_action()
                    reward = self.calculate_reward(numq, numcr)
                    self.update_q_table(reward, next_state)

                    if reward == 0:
                        numcr = self.previous_crossover_rate
                    elif reward > 0:
                        numcr = numcr * (1 + reward)
                    else:
                        numcr = numcr * (1 - abs(reward))

                    numcr = max(0, min(1, numcr))

                    # Adding the condition to adjust numcr
                    global rtimes  # Add this line to access the global rtimes variable
                    if rtimes < 9 and numq - self.previous_coverage == 0:
                        numcr = random.uniform(0.3, 1)
                        print("numcr =" + str(numcr))
                        rtimes = rtimes + 1

                    self.state = next_state
                    self.previous_coverage = numq
                    self.previous_crossover_rate = numcr

                    return numcr

            # init RLModel

            num_states = 2
            num_actions = 2
            max_episodes = 200
            previous_coverage = 0.0
            rtimes = 0

            rl_model = RLModel(num_states, num_actions)
            rl_model.q_table[0][0] = 0.6
            rl_model.q_table[0][1] = 0.4
            rl_model.q_table[1][0] = 0.4
            rl_model.q_table[1][1] = 0.6

            for i in range(1, max_episodes+1):

                with open('swap.txt', 'r') as file:
                    numcr = float(file.read())

                dataframe = pd.read_csv('evosuite-report\statistics.csv')
                last_row = dataframe.tail(1)

                algorithm = last_row['Algorithm'].values[0]
                target_class = last_row['TARGET_CLASS'].values[0]
                criterion = last_row['criterion'].values[0]
                coverage = last_row['Coverage'].values[0]
                branch_coverage = last_row['BranchCoverage'].values[0]
                total_goals = last_row['Total_Goals'].values[0]
                covered_goals = last_row['Covered_Goals'].values[0]

                numq = float(coverage)
                if numq == 1:
                    print("reached 1,skipping")
                    break

                with open('swap.txt', 'r') as file:
                    numcr = float(file.read())

                present_numcr = rl_model.run_episode(numq, numcr)
                reward = rl_model.calculate_reward(numq, numcr)

                if previous_coverage > numq:
                    next_state = 0
                else:
                    next_state = 1

                rl_model.update_states(next_state)

                with open('swap.txt', 'w') as file:
                    file.write(str(present_numcr))

                with open('qtable.txt', 'w') as file:
                    file.write("\nQ-Table:")
                    file.write(str(rl_model.q_table))

                with open('log.txt', 'a') as file:
                    file.write("------------------------QLearning------------------------\n")
                    file.write("Episodes : ")
                    file.write(str(i))
                    file.write("\nPrevious coverage:")
                    file.write(str(previous_coverage))
                    file.write("\nPresent coverage:")
                    file.write(str(numq))
                    file.write("\nPresent branch_coverage:")
                    file.write(str(branch_coverage))
                    file.write("\nPresent action:")
                    file.write(str(num_actions))
                    file.write("\nReward:")
                    file.write(str(reward))
                    file.write("\nPresent crossover rate:")
                    file.write(str(numcr))
                    file.write("\nPresent class:")
                    file.write(str(class_name))
                    file.write("\n-------------------------------------------------------\n")

                previous_coverage = numq
                previous_crossover_rate = present_numcr
                state = next_state

                os.system(f'java -jar evosuite-shaded-1.2.1-SNAPSHOT.jar -class {class_name} -projectCP ' + classpath)

            os.system(f"copy evosuite-report\\statistics.csv evosuite-report\\" + class_name + str(
                int(random.random() * 500)) + ".csv")

    except Exception as e:
        error_message = f"An error occurred for jar: {jar_file}. Error details: {str(e)}"
        print(error_message)
        with open(error_file, 'a') as err_file:
            err_file.write(error_message + '\n')
        continue




