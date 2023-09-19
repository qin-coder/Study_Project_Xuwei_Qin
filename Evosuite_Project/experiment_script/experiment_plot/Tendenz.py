import matplotlib.pyplot as plt
import seaborn as sns
import pandas as pd


configurations = ["Default_Configuration", "Crossover_rate After RL", "Mutation_rate After RL"]
coverage_data = []

for config in configurations:
    filename = f"txts/{config.lower()}_input.txt"
    with open(filename, 'r') as file:
        episode_coverage = None

        for line in file:
            if "Present coverage:" in line:
                episode_coverage = float(line.split(":")[1].strip())
                coverage_data.append({"Configuration": config, "Coverage": episode_coverage})


df = pd.DataFrame(coverage_data)

sns.set(style="whitegrid")


plt.figure(figsize=(12, 6))
for config in configurations:
    data = df[df["Configuration"] == config]["Coverage"]
    sns.kdeplot(data, label=config)

plt.xlabel('Coverage')
plt.ylabel('Density')
plt.title('Kernel Density Estimation of Coverage(random seed of org.fixsuite.message.Library)')
plt.legend()

plt.show()
