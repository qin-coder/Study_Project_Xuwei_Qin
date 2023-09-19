import pandas as pd
import os
import numpy as np
import random


class Environment:
    def __init__(self):
        self.previous_coverage = 0.5
        self.previous_mutation_rate = 0.5
        self.state = 1

    def step(self, action, num_coverage, num_mutation_rate):
        coverage_increase = num_coverage - self.previous_coverage
        mutation_change = num_mutation_rate - self.previous_mutation_rate
        reward = coverage_increase + 0.2 * coverage_increase ** 2 - 0.1 * abs(mutation_change)

        if action == 1:  # increase mutation rate
            num_mutation_rate = num_mutation_rate * (1 + reward)
        elif action == 2:  # decrease mutation rate
            num_mutation_rate = num_mutation_rate * (1 - abs(reward))
        elif action == 3:
            num_mutation_rate = num_mutation_rate

        global rtimes
        if rtimes < 7 and num_coverage - self.previous_coverage == 0:
            num_mutation_rate = random.uniform(0.3, 0.8)
            print("num_mutation_rate =" + str(num_mutation_rate))
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

        num_mutation_rate = max(0, min(1, num_mutation_rate))
        self.previous_coverage = num_coverage
        self.previous_mutation_rate = num_mutation_rate

        return self.state, num_coverage, num_mutation_rate, reward


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

    def run_episode(self, env, num_coverage, num_mutation_rate):
        state = env.state
        action = self.choose_action()
        next_state, num_coverage, num_mutation_rate, reward = env.step(action, num_coverage, num_mutation_rate)

        num_mutation_rate = num_mutation_rate
        self.update_q_table(state, action, reward, next_state)
        env.state = next_state

        return num_coverage, num_mutation_rate, reward


# init env
env = Environment()
num_states = 3  # 3 kinds of states
num_actions = 3  # 3 kinds of actions
previous_coverage = 0.5
rl_model = RLAgent(num_states, num_actions)

# initialize state
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
max_episode = 501

for episode in range(0, max_episode):
    with open('swap.txt', 'r') as file:
        num_mutation_rate = float(file.read())

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

    num_coverage, num_mutation_rate, reward = rl_model.run_episode(env, num_coverage, num_mutation_rate)

    with open('swap.txt', 'w') as file:
        file.write(str(num_mutation_rate))

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
        file.write("\nReward:")
        file.write(str(reward))
        file.write("\nPresent mutation rate:")
        file.write(str(num_mutation_rate))
        file.write("\nPresent class:")
        file.write("com.ib.client.EWrapperMsgGenerator(Fixed seed After RL)")
        file.write("\n-------------------------------------------------------\n")

    previous_coverage = num_coverage
    previous_mutation_rate = num_mutation_rate

    os.system(f'java -jar evosuite_fixedseed_mu.jar -class com.ib.client.EWrapperMsgGenerator -projectCP ./')

os.system(f"copy evosuite-report\\statistics.csv evosuite-report\\" + str(int(random.random() * 500)) + ".csv")
