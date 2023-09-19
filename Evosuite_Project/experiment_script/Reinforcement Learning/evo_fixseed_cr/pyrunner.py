import pandas as pd
import os
import numpy as np
import random

class Environment:
    def __init__(self):
        self.previous_coverage = 0.5
        self.previous_crossover_rate = 0.5
        self.state = 1  # 初始状态为1，表示当前覆盖率与上次的覆盖率相等

    def step(self, action, num_coverage, num_crossover_rate):
        coverage_increase = num_coverage - self.previous_coverage
        crossover_change = num_crossover_rate - self.previous_crossover_rate
        reward = coverage_increase * 0.1 + 0.2 * coverage_increase ** 2 - 0.1 * abs(crossover_change)

        # 覆盖率变化：奖励会根据覆盖率的增加或减少而变化，这是测试用例生成问题中的一个重要指标。
        # 覆盖率的平方项：覆盖率增加的平方项表明了对更大的覆盖率增加更加关注，这可以激励代理更积极地提高覆盖率。
        # 交叉率变化的绝对值：减少交叉率的幅度的绝对值也会影响奖励，这可以防止代理过于激进地减少交叉率。

        if action == 1:  # 增加交叉率
            num_crossover_rate = num_crossover_rate * (1 + reward)
        elif action == 2:  # 减少交叉率
            num_crossover_rate = num_crossover_rate * (1 - abs(reward))

        global rtimes
        if rtimes < 7 and num_coverage - self.previous_coverage == 0:
            num_crossover_rate = random.uniform(0.3, 0.8)
            print("num_crossover_rate =" + str(num_crossover_rate))
            rtimes = rtimes + 1

        # 更新状态
        if num_coverage == self.previous_coverage:
            self.state = 1
        elif num_coverage > self.previous_coverage:
            self.state = 2
            num_crossover_rate = num_crossover_rate * (1 + reward)
        else:
            self.state = 3
            num_crossover_rate = num_crossover_rate * (1 - abs(reward))


        num_crossover_rate = max(0, min(1, num_crossover_rate))
        self.previous_coverage = num_coverage
        self.previous_crossover_rate = num_crossover_rate

        return self.state, num_coverage, num_crossover_rate, reward

class RLModel:
    def __init__(self, num_states, num_actions):
        self.num_states = num_states
        self.num_actions = num_actions
        self.q_table = np.zeros((num_states, num_actions))
        self.state = 1  # 初始状态为1，表示当前覆盖率与上次的覆盖率相等
        self.action = 0

    def choose_action(self):
        if random.random() < 0.1:
            return random.choice(range(self.num_actions))
        else:
            # 基于 Q-table 选择动作
            return np.argmax(self.q_table[self.state - 1])

    # 在上面的代码中，选择action的方式是通过epsilon - greedy策略进行的，这是一种常用的强化学习策略，根据探索（exploration）和利用（exploitation）的权衡来选择动作。
    # 如果随机数小于0.1（即10 % 的概率），则选择随机动作，以便进行探索。
    # 否则，以1 - epsilon的概率选择具有最高Q值的动作，以便进行利用。
    # 这种策略是合理的，因为它允许在一定程度上进行探索，以发现潜在更好的动作，同时也会根据已有的经验进行利用，以获得更高的长期奖励。但是，epsilon值（这里是0.1）是一个超参数，可以根据具体问题和实验来调整，以达到更好的性能。
    # 总的来说，选择action的方式在这种情况下是合理的，但epsilon值的选择可能需要根据实际问题的需求进行微调。

    def update_q_table(self, state, action, reward, next_state):
        learning_rate = 0.05
        discount_factor = 0.9

        current_q = self.q_table[state - 1, action]
        max_next_q = np.max(self.q_table[next_state - 1])
        new_q = (1 - learning_rate) * current_q + learning_rate * (reward + discount_factor * max_next_q)
        self.q_table[state - 1, action] = new_q

    def run_episode(self, env, num_coverage, num_crossover_rate):
        state = env.state
        action = self.choose_action()
        next_state, num_coverage, num_crossover_rate, reward = env.step(action, num_coverage, num_crossover_rate)

        present_num_crossover_rate = num_crossover_rate  # 记录当前的crossover rate
        self.update_q_table(state, action, reward, next_state)
        env.state = next_state

        return num_coverage, present_num_crossover_rate, reward

# 初始化环境和代理
env = Environment()
num_states = 3  # 有3种状态
num_actions = 3  # 有3种动作
previous_coverage = 0.5
rl_model = RLModel(num_states, num_actions)

# 初始化代理的 Q-table
# Q-table的行数为state数，列数为action数
rl_model.q_table[0][0] = 0.6
rl_model.q_table[0][1] = 0.4
rl_model.q_table[0][2] = 0.5
rl_model.q_table[1][0] = 0.4
rl_model.q_table[1][1] = 0.6
rl_model.q_table[1][2] = 0.5
rl_model.q_table[2][0] = 0.5
rl_model.q_table[2][1] = 0.5
rl_model.q_table[2][2] = 0.5

# 初始化其他变量
rtimes = 0


for episode in range(1, 501):
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
        file.write(str(num_actions))
        file.write("\nReward:")
        file.write(str(reward))
        file.write("\nPresent crossover rate:")
        file.write(str(present_num_crossover_rate))
        file.write("\nPresent class:")
        file.write("org.fixsuite.message.Library(Fixed seed After RL)")
        # file.write("org.fixsuite.message.info.GroupInfo")
        file.write("\n-------------------------------------------------------\n")

    previous_coverage = num_coverage
    previous_crossover_rate = present_num_crossover_rate

    os.system(f'java -jar evosuite-shaded-1.2.1-SNAPSHOT.jar -class org.fixsuite.message.Library -projectCP ./')

os.system(f"copy evosuite-report\\statistics.csv evosuite-report\\" + str(int(random.random() * 500)) + ".csv")
