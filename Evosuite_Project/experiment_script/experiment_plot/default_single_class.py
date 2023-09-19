import pandas as pd
import matplotlib.pyplot as plt
import numpy as np

# 读取log1.txt文件并解析数据
data = []
with open('txts/mmi_log.txt', 'r') as file:
    episodes = None
    coverage = None
    class_name = None
    branch_coverage = None

    for line in file:
        if "Episodes :" in line:
            episodes = int(line.split(":")[1].strip())
        elif "Present coverage:" in line:
            coverage = float(line.split(":")[1].strip())
        elif "Present class:" in line:
            class_name = line.split(":")[1].strip()
        elif "Present branch_coverage:" in line:  # 添加解析 branch_coverage 的部分
            branch_coverage = float(line.split(":")[1].strip())

            if episodes is not None and coverage is not None and class_name is not None:
                data.append(
                    {'Episodes': episodes, 'Coverage': coverage,  'Class': class_name, 'Present branch_coverage': branch_coverage})
                episodes = None
                coverage = None
                class_name = None
                branch_coverage = None

# 创建DataFrame
df = pd.DataFrame(data)

# 获取相同的Present class列表
unique_classes = df['Class'].unique()

# 对每个类别创建单独的图表
for class_name in unique_classes:
    class_data = df[df['Class'] == class_name]

    # 创建图表
    plt.figure(figsize=(10, 10))  # 增加图表的大小

 # 绘制Coverage vs Episodes图表
    #plt.subplot(2, 1, 1)  # 增加一个子图
    plt.plot(class_data['Episodes'], class_data['Coverage'])
    plt.xlabel('Episodes')
    plt.ylabel('Present Coverage')
    plt.ylim(0, 1)  # 设置y轴范围为0到1
    plt.yticks(np.arange(0, 1.1, 0.1))  # 设置y轴刻度为0.1的倍数
    plt.title(f'Coverage vs Episodes for {class_name}')
    plt.grid()




    # # 绘制Present branch_coverage vs Episodes图表
    # plt.subplot(2, 1, 2)  # 增加一个子图
    # plt.plot(class_data['Episodes'], class_data['Present branch_coverage'])
    # plt.xlabel('Episodes')
    # plt.ylabel('Present Branch Coverage')
    # plt.ylim(0, 1)  # 设置y轴范围为0到1
    # plt.yticks(np.arange(0, 1.1, 0.1))  # 设置y轴刻度为0.1的倍数
    # plt.title(f'Branch Coverage vs Episodes for {class_name}')
    # plt.grid()
    #
    # # 调整子图之间的间距
    # plt.tight_layout()

    # 显示图表
    plt.show()
