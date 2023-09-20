import pandas as pd
import matplotlib.pyplot as plt
import numpy as np


data = []
with open('txts/input_data_log.txt', 'r') as file:
    episodes = None
    coverage = None
    mutation_rate = None
    reward = None
    class_name = None
    branch_coverage = None

    for line in file:
        if "Episodes :" in line:
            episodes = int(line.split(":")[1].strip())
        elif "Present coverage:" in line:
            coverage = float(line.split(":")[1].strip())
        elif "Present mutation rate:" in line:
            mutation_rate = float(line.split(":")[1].strip())
        elif "Reward:" in line:
            reward = float(line.split(":")[1].strip())
        elif "Present class:" in line:
            class_name = line.split(":")[1].strip()
        elif "Present branch_coverage:" in line:
            branch_coverage = float(line.split(":")[1].strip())

            if episodes is not None and coverage is not None and mutation_rate is not None and reward is not None and class_name is not None:
                data.append(
                    {'Episodes': episodes, 'Coverage': coverage, 'Mutation Rate': mutation_rate, 'Reward': reward, 'Class': class_name,'Present branch_coverage': branch_coverage})
                episodes = None
                coverage = None
                mutation_rate = None
                reward = None
                class_name = None
                branch_coverage = None


df = pd.DataFrame(data)

unique_classes = df['Class'].unique()

for class_name in unique_classes:
    class_data = df[df['Class'] == class_name]

    plt.figure(figsize=(10, 10))

    # Mutation Rate vs Episodes
    plt.subplot(3, 1, 1)
    plt.plot(class_data['Episodes'], class_data['Mutation Rate'])
    plt.xlabel('Episodes')
    plt.ylabel('Present Mutation Rate')
    plt.ylim(0, 1)
    plt.yticks(np.arange(0, 1.1, 0.1))
    plt.title(f'Mutation Rate vs Episodes for {class_name}')
    plt.grid()

    # Coverage vs Episodes
    plt.subplot(3, 1, 2)
    plt.plot(class_data['Episodes'], class_data['Coverage'])
    plt.xlabel('Episodes')
    plt.ylabel('Present Coverage')
    plt.ylim(0, 1)
    plt.yticks(np.arange(0, 1.1, 0.1))
    plt.title(f'Coverage vs Episodes for {class_name}')
    plt.grid()

    # Reward vs Episodes
    plt.subplot(3, 1, 3)
    plt.plot(class_data['Episodes'], class_data['Reward'])
    plt.xlabel('Episodes')
    plt.ylabel('Reward')
    plt.title(f'Reward vs Episodes for {class_name}')
    plt.grid()

    plt.tight_layout()

    plt.show()
