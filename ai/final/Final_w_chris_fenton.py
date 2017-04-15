import numpy as np
import itertools
from collections import defaultdict
from gridworld import GridworldEnv

ENV = GridworldEnv()
EQUIPROBABLE_POLICY = random_policy = np.ones([ENV.nS, ENV.nA]) / ENV.nA

# Chris Fenton
# CNC - AI
# Winter Final Programming Problems

'''
a) For the gridworld in example 4.1, Figure 4.1 shows a synchronous iterative
policy evaluation, although the text explains asynchronous. An asynchronous
iterative policy evaluation would go through the states (in numerical order
from 1 to 14) and update after each state based on the previous updates.
Program the asynchronous version, and write the value for each state after
2000 iterations.
'''
def asyncPolicyEvaluation(gamma=0.9,iterations=2000):
    # ENV.P[s][a] : prob, next_state, reward, terminal?) tuple
    # Actions: up=0, right=1, down=2, left=3
    Q = defaultdict(lambda: np.zeros(ENV.action_space.n))
    # Q is the optimal action-value function, a dictionary mapping state -> action values.
    A = [0,1,2,3] #Actions
    values = [0] * ENV.nS
    for i in range(iterations):
        state = ENV.reset() #get a random state
        action = np.random.choice(A, replace=False) #random action
        #Q(s,a) = EV[R(t+1) + γV(s')]
        for prob, next_state, reward, done in ENV.P[state][action]:
            Q[state][action] = reward + gamma * np.max(Q[next_state])
    # For each state, get the optimal action
    for state in Q:
        values[state] = np.average(Q[state])
    # return the value of each state after 'i' iterations
    return np.round(values, decimals=1)

'''
b) Program gridworld using a Monte Carlo off-policy solution. (submit on ada).
Write the values for the state-action pairs, write the equations,
including importance sampling.
'''
def offPolicyMC(gamma=1,episodes=50000):
    Q = defaultdict(lambda: np.zeros(ENV.action_space.n))
    C = defaultdict(lambda: np.zeros(ENV.action_space.n))
    PI = newGreedyPolicy(Q)
    softPolicy = newSoftPolicy(ENV.action_space.n)

    for ep in range(1, episodes + 1):
        # Generate an episode
        episode = []
        state = ENV.reset()
        for t in range(100):
            # Sample an action from our policy
            probs = softPolicy(state)
            action = np.random.choice(np.arange(len(probs)), p=probs)
            next_state, reward, done, _ = ENV.step(action)
            episode.append((state, action, reward))
            if done:
                break
            state = next_state

        G = 0.0
        W = 1.0

        # for t = T-1, T-2,...,0
        for t in range(len(episode))[::-1]:
            state, action, reward = episode[t]
            G = gamma * G + reward # G ← γG + Rt+1
            C[state][action] += W # C(St, At) ← C(St, At) + W
            # Incrementally update q(s,a) and improve target policy (PI)
            Q[state][action] += (W / C[state][action]) * (G - Q[state][action])
            # If At ̸= π(St) then ExitForLoop
            if action != np.argmax(PI(state)):
                break
            # Update weight
            W = W * 1.0 / softPolicy(state)[action]

    values = [0] * ENV.nS
    for state in Q:
        values[state] = np.average(Q[state])
    # return the value of each state after 'i' iterations
    return [np.round(values, decimals=1), Q]

'''
c) Program gridworld using a TD(0) off-policy solution. Write the values
for the state-action pairs, write the equations.
'''
def offPolicyTD0(gamma=1.0, alpha=0.5, n=10000):
    V = np.zeros(ENV.nS) #initialize V(s)
    Q = defaultdict(lambda: np.zeros(ENV.action_space.n))
    PI = newEpsilonGreedyPolicy(Q, ENV.action_space.n)

    # Repeat for each episode
    for ep in range(n):
        state = ENV.reset()
        # Repeat for each step of episode
        for step in itertools.count(): #infinite loop, will break on T
            # A ← action given by π for S
            action_probs = PI(state)
            action = np.random.choice(np.arange(len(action_probs)), p=action_probs)

            # Take action A, observe R, S′
            next_state, reward, done, _ = ENV.step(action)

            # V (S) ← V (S) + α R + γV (S′) − V (S)
            temp_diff = (reward + gamma * V[next_state]) - V[state]
            V[state] = V[state] + (alpha * temp_diff)
            # Update q(s,a) to make policy fit the template
            Q[state][action] = V[state]

            #S ← S′
            state = next_state

            # until S is terminal
            if done:
                break

    return [np.round(V, decimals=1), Q]


'''
Policies
'''
def newGreedyPolicy(Q):
    def policy(state):
        actions = np.zeros_like(Q[state], dtype=float)
        a = np.argmax(Q[state])
        actions[a] = 1.0
        return actions
    return policy

def newSoftPolicy(nA): #random policy
    actions = np.ones(nA, dtype=float) / nA
    def policy(obs):
        return actions
    return policy

def newEpsilonGreedyPolicy(Q, nA, epsilon=0.1):
    def policy(obs):
        actions = np.ones(nA, dtype=float) * epsilon / nA
        bestA = np.argmax(Q[obs])
        actions[bestA] += 1.0 - epsilon
        return actions
    return policy

'''
Utility Functions
'''
def printValues(values, file="scratch.txt"):
    with open(file, "w") as text_file:
        for i,v in enumerate(values):
            print(v, end=" ", file=text_file)
            if i in [3,7,11,15]:
                print("\n", file=text_file)

def printQ(Q, file="scratch.txt"):
    with open(file, "w") as text_file:
        for s in Q:
            print(s, end=": ", file=text_file)
            for a in q[s]:
                print(round(a, 1), end=" ", file=text_file)
            print("\n", file=text_file)
