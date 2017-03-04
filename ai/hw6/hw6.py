from copy import deepcopy

# Policy Evaluation gridworld
# Equiprobable random policy

GRID = [[ 0, 0, 0, 0],
        [ 0, 0, 0, 0],
        [ 0, 0, 0, 0],
        [ 0, 0, 0, 0]]

def prob(p):
    p = float(p)
    n = 1.0 - p
    return {
        'S':(('S', p), ('N', n)),
        'N':(('N', p), ('S', n)),
        'E':(('E', p), ('W', n)),
        'W':(('W', p), ('E', n)),
    }

STATES = ((0,3),(0,2),(1,2),(0,1),(1,1),(0,0))

class GridWorld:
    ACTIONS = ['N','E','S','W']
    def __init__(self, grid, prob, states, gamma, r):
        self.grid = deepcopy(grid)
        self.prob = prob
        self.states = states
        self.gamma = gamma
        self.r = r
        self.max_i = len(self.grid) - 1
        self.max_j = len(self.grid) - 1

    def action(self, i, j, direction):
        new_i, new_j = i, j
        if direction == 'S':
            new_i += 1
        elif direction == 'N':
            new_i -= 1
        elif direction == 'E':
            new_j += 1
        elif direction == 'W':
            new_j -= 1

        if (new_i < 0) or (new_i > self.max_i):
            new_i = i

        if (new_j < 0) or (new_j > self.max_j):
            new_j = j

        if self.grid[new_i][new_j] is None:
            new_i, new_j = i, j

        return (new_i, new_j)

    def value(self, i, j):
        new_positions = {}
        for action in GridWorld.ACTIONS:
            new_i, new_j = self.action(i, j, action)
            new_positions[action] = (new_i, new_j)

        values = []
        for action in GridWorld.ACTIONS:
            v = 0
            for a, p in self.prob[action]:
                (new_i, new_j) = new_positions[a]
                v += p * self.grid[new_i][new_j]
            values.append(v)
        return (self.gamma * max(values)) + self.r

    def value_iteration(self, delta):
        n = 0
        while True:
            n += 1
            diffs = []
            for (i, j) in self.states:
                v = self.value(i,j)
                diffs.append(abs(v - self.grid[i][j]))
                self.grid[i][j] = v
            if max(diffs) < delta:
                break
        return n

    def __str__(self):
        s = []
        for i in range(self.max_i+1):
            items = []
            for j in range(self.max_j+1):
                v = self.grid[i][j]
                if v is None:
                    items.append("  XX")
                else:
                    items.append('%4.0f' % v)
            s.append(' '.join(items))
        return '\n'.join(s)

print("\nInitial values:")
g = GridWorld(GRID, prob(.5), STATES, 1, -1)
print(g)

i = g.value_iteration(0.05)
print("\nValues after %d iterations:" % i)
print(g)

g = GridWorld(GRID, prob(.5), STATES, 1, -1)
print("\nValue of (1,2) after first iteration: %.1f" % g.value(0,3))

g = GridWorld(GRID, prob(.5), STATES, 1, -1)
i = g.value_iteration(0.05)
print("\nValues after %d iterations:" % i)
print(g)
