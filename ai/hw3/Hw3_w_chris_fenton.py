from scipy.stats import maxwell
import matplotlib.pyplot as plt
import numpy as np

# Chris Fenton
# CNC - AI
# Homework 3

# Question 1
def guessing(p=0.6, n=50):
  guess = p * n
  know = n - guess
  n_correct = know + (guess * .5)
  return n_correct / n

# 35 students (or 70%) would get the right answer
# General formula:
# ((p * n * .5) + (n - (p * n)) / n

# Question 2
def gibbsDist():
    fig, ax = plt.subplots(1, 1)
    mean, var, skew, kurt = maxwell.stats(moments='mvsk')
    x = np.linspace(maxwell.ppf(0.01),
                    maxwell.ppf(0.99), 100)
    ax.plot(x, maxwell.pdf(x),
           'r-', lw=5, alpha=0.6, label='maxwell pdf')
    ax.legend(loc='best', frameon=False)
    plt.show()

class Bandit:
    def __init__(self, k):
        self.k = k
        self.qstar = np.random.normal(size=k)

    def action(self, a):
        return np.random.normal(loc=self.qstar[a])

class GradientBandit:
    def __init__(self, k, n, alpha):
        self.k = k
        self.n = n
        self.alpha = alpha

        # A = Actions at time time step t
        self.A = np.zeros((n,))
        # N = Times an action was selected
        self.N = np.zeros((k, n+1))
        # R = reward at time step t
        self.R = np.zeros((n,))
        # H = prefernces, example H[a,t]
        self.H = np.zeros((k, (n+1)))

        self.bandit = Bandit(k)

    def runBandit(self):
        for t in range(self.n):
            pi_t = np.exp(self.H[:,t]) / np.sum(np.exp(self.H[:,t]))
            a = np.random.choice(self.k,p=pi_t)
            self.A[t] = a
            self.R[t] = self.bandit.action(a)
            self.N[:,t+1] = self.N[:,t]
            self.N[a,t+1] += 1

            if t > 0:
                mean_R_t = np.mean(self.R[:t])
            else:
                mean_R_t = 0

            non_a = np.arange(self.k) != a
            self.H[a,t+1] = self.H[a,t] + self.alpha*(self.R[t] - mean_R_t) * (1 - pi_t[a])
            self.H[non_a, t+1] = self.H[non_a,t] - self.alpha * (self.R[t] - mean_R_t) * pi_t[non_a]

# Just ploting differnt alphas right now
# Might come back and implement the testbed from the book with the baselines
# and the normal distribution EVs later
def testBed(steps=300):
    k = 10
    numsteps = 300
    samples = 200
    alpha = 0.1
    bandits = [GradientBandit(k, numsteps, alpha),
               GradientBandit(k, numsteps, alpha=0.4)]

    for bandit in bandits:
        avgR = np.zeros((numsteps, ))
        for task in range(samples):
            sample = bandit
            sample.runBandit()
            avgR += sample.R
        avgR /= samples
        lbl = "alpha=%s" % bandit.alpha
        plt.plot(avgR, label=lbl)

    plt.ylabel('Average reward') ;
    plt.xlabel('Steps') ;
    plt.legend()
    plt.xlim(-5) ;
    plt.show() ;
