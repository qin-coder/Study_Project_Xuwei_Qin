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
root_directory = r'E:\evo_dataset\classfile_new_temp'

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
            package, class_name = os.path.split(relative_class_name)  
            class_name_parts = class_name.split('.')  
            if len(class_name_parts) > 1:
                class_name = '.'.join(class_name_parts[2:]) 
            print(f"Going to generate test cases for class: {class_name}")


            class Environment:
                def __init__(self):
                    self.previous_coverage = 0.5
                    self.previous_crossover_rate = 0.5
                    self.state = 1

                def step(self, action, num_coverage, num_crossover_rate):
                    coverage_increase = num_coverage - self.previous_coverage
                    crossover_change = num_crossover_rate - self.previous_crossover_rate
                    reward = coverage_increase * 0.3 + 0.2 * coverage_increase ** 2 - 0.1 * abs(crossover_change)

                    if action == 1:  # increase crossover rate
                        num_crossover_rate = num_crossover_rate * (1 + reward)
                    elif action == 2:  # decrease crossover rate
                        num_crossover_rate = num_crossover_rate * (1 - abs(reward))
                    elif action == 3:
                        num_crossover_rate = num_crossover_rate

                    global rtimes
                    if rtimes < 7 and num_coverage - self.previous_coverage == 0:
                        num_crossover_rate = random.uniform(0.3, 0.8)
                        print("num_crossover_rate =" + str(num_crossover_rate))
                        rtimes = rtimes + 1

                    # Some classes have a fixed coverage value no matter how many times they are run.
                    # In order to confirm whether the reason why the coverage value is fixed is related to the crossover rate or mutation rate, a logic is set up here.
                    # When the current coverage value is equal to the last coverage value, a random value between 0.3 and 0.8 is assigned to the crossover rate or mutation rate.
                    # and set the number of changes to 7 times to prevent the program from falling into a situation where the parameters do not change anyway in the early stage.
                    # If the coverage value remains unchanged after 7 times, it means that no matter how the crossover rate or mutation rate changes, it has nothing to do with the generated coverage value.
                    # Such classes can be filtered out.

                    # init state
                    if num_coverage == self.previous_coverage:
                        self.state = 1
                    elif num_coverage > self.previous_coverage:
                        self.state = 2
                    else:
                        self.state = 3

                    num_crossover_rate = max(0, min(1, num_crossover_rate))
                    self.previous_coverage = num_coverage
                    self.previous_crossover_rate = num_crossover_rate

                    return self.state, num_coverage, num_crossover_rate, reward


            class RLAgent:
                def __init__(self, num_states, num_actions):
                    self.num_states = num_states
                    self.num_actions = num_actions
                    self.q_table = np.zeros((num_states, num_actions))
                    self.state = 1
                    self.action = 0

                def choose_action(self):
                    if random.random() < 0.1:
                        return random.choice(range(self.num_actions))
                    else:
                        # choose the action based on the Q table
                        max_q_value = np.max(self.q_table[self.state - 1])
                        best_actions = [action for action, q_value in enumerate(self.q_table[self.state - 1]) if
                                        q_value == max_q_value]
                        return random.choice(best_actions)

                def update_q_table(self, state, action, reward, next_state):
                    learning_rate = 0.05
                    discount_factor = 0.9  # also called gamma

                    current_q = self.q_table[state - 1, action]
                    max_next_q = np.max(self.q_table[next_state - 1])
                    new_q = (1 - learning_rate) * current_q + learning_rate * (reward + discount_factor * max_next_q)
                    self.q_table[state - 1, action] = new_q

                def run_episode(self, env, num_coverage, num_crossover_rate):
                    state = env.state
                    action = self.choose_action()
                    next_state, num_coverage, num_crossover_rate, reward = env.step(action, num_coverage,
                                                                                    num_crossover_rate)

                    present_num_crossover_rate = num_crossover_rate
                    self.update_q_table(state, action, reward, next_state)
                    env.state = next_state

                    return num_coverage, present_num_crossover_rate, reward


            # init env and agent
            env = Environment()
            num_states = 3  # 3 kind of states
            num_actions = 3  # 3 kinds of actions
            previous_coverage = 0.5
            rl_model = RLAgent(num_states, num_actions)

            rl_model.q_table[0][0] = 0.6
            rl_model.q_table[0][1] = 0.4
            rl_model.q_table[0][2] = 0.5
            rl_model.q_table[1][0] = 0.4
            rl_model.q_table[1][1] = 0.6
            rl_model.q_table[1][2] = 0.5
            rl_model.q_table[2][0] = 0.5
            rl_model.q_table[2][1] = 0.5
            rl_model.q_table[2][2] = 0.5

            rtimes = 0
            max_episode = 40

            for episode in range(1, max_episode):

                with open('swap.txt', 'r') as file:
                    num_crossover_rate = float(file.read())

                dataframe = pd.read_csv('evosuite-report\statistics.csv')
                last_row = dataframe.tail(1)

                algorithm = last_row['Algorithm'].values[0]
                target_class = last_row['TARGET_CLASS'].values[0]
                criterion = last_row['criterion'].values[0]
                coverage = last_row['Coverage'].values[0]
                branch_coverage = last_row['BranchCoverage'].values[0]
                total_goals = last_row['Total_Goals'].values[0]
                covered_goals = last_row['Covered_Goals'].values[0]

                num_coverage = float(coverage)
                if num_coverage == 1:
                    print("reached 1,skipping")
                    break

                num_coverage, present_num_crossover_rate, reward = rl_model.run_episode(env, num_coverage, num_crossover_rate)

                with open('swap.txt', 'w') as file:
                    file.write(str(present_num_crossover_rate))

                with open('qtable.txt', 'w') as file:
                    file.write("\nQ-Table:")
                    file.write(str(rl_model.q_table))

                with open('log.txt', 'a') as file:
                    file.write("------------------------QLearning------------------------\n")
                    file.write("Episodes : ")
                    file.write(str(episode))
                    file.write("\nPrevious coverage:")
                    file.write(str(previous_coverage))
                    file.write("\nPresent coverage:")
                    file.write(str(num_coverage))
                    file.write("\nPresent branch_coverage:")
                    file.write(str(branch_coverage))
                    file.write("\nPresent action:")
                    file.write(str(reward))
                    file.write("\nPresent crossover rate:")
                    file.write(str(present_num_crossover_rate))
                    file.write("\nPresent class:")
                    file.write(class_name)
                    file.write("\n-------------------------------------------------------\n")

                previous_coverage = num_coverage
                previous_crossover_rate = present_num_crossover_rate

                os.system(f'java -jar evosuite_cr.jar -class {class_name} -projectCP ' + classpath)

            os.system(f"copy evosuite-report\\statistics.csv evosuite-report\\" + class_name + str(
                int(random.random() * 500)) + ".csv")

    except Exception as e:
        error_message = f"An error occurred for jar: {jar_file}. Error details: {str(e)}"
        print(error_message)
        with open(error_file, 'a') as err_file:
            err_file.write(error_message + '\n')
        continue




