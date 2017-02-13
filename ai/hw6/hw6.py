# Policy Evaluation for recycled robot
# value(v) will be a list of 2 numbers

N_ACTIONS = 3
N_STATES = 2

LOW = 0
HIGH = 1
states = [LOW, HIGH]
actions = [search, wait, recharge]

# Always search (rows = state, columns = actions)
def policy_init():
	return [[1,0,0],[1,0,0]]

def rewards(state, action):
	

# def policty_update(p):
# 	for s in range(N_STATES):
# 		for n in range(N_ACTIONS):




