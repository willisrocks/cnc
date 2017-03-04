import numpy as np
import pandas as pd
import matplotlib.pyplot as plt

# Setup epsilon, number of arms, and the testbed
epsilon = 0.1
n = 10
rewards = np.random.normal(0, 1, n)

qa = [0] * n #values
na = [0.0] * n #counts

# Returns the index(arm) of the max value or a random arm
def choose():
    if np.random.random() > epsilon:
        # Exploit
        return np.argmax(qa)
    else:
        # Explore
        return np.random.randint(n)

# Updates the currently chosen arm
def update(index):
    na[index] = na[index] + 1
    n = na[index]
    reward = rewards[index]
    val = qa[index]
    qa[index] = updateVal(n, val, reward)

# Returns the new average
def updateVal(n, val, reward):
    new_val = ((n - 1) / float(n)) * val + (1 / float(n)) * reward
    return new_val

# Returns [steps, average reward per step]
def testbed(n):
    steps = list(range(1,n+1))
    current_rewards = [0.0] * n
    average_rewards = [0.0] * n

    for i in (list(range(0,n))):
        current = choose()
        update(current)
        reward = rewards[current]

        current_rewards[i] = qa[current]
        average_rewards[i] = updateVal(i + 1, current, reward)

        print (f"Step {i + 1}:")
        print (f"Current: {qa[current]}")
        print (f"Avg: {reward}")

    return [steps, average_rewards]

# Plots the testbed
def plotTestbed(n):
    data = testbed(n)
    x = data[0]
    y = data[1]
    df = pd.DataFrame({'x':x, 'y':y})
    g = df.plot('x', 'y', kind='scatter')
    g.set_xlabel("Steps")
    g.set_ylabel("Average Reward")
    plt.show()
