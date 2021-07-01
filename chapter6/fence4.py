import matplotlib.pyplot as plt
from matplotlib.patches import Rectangle
import numpy as np
from sys import stdin

def add_point(x, y):
	plt.plot(x, y, 'ro')

def add_line(x1, y1, x2, y2):
	plt.plot([x1, x2], [y1, y2])
#define Matplotlib figure and axis
fig, ax = plt.subplots()

#create simple line plot
input() # skip first line
x, y = [int(x) for x in input().split()]
# x, y = 100, 100
# l = ""
# while (l = input()):
# 	print(l)
# print(x, y)
ax.plot([0, 0],[0, 5], alpha=0)
add_point(x, y)
# inputs = """
# 0 0
# 2 0
# 2 2
# 0 2
# """
# inputs = ""
lines = []
for line in stdin:
	if not len(line):
		continue
	# print(line)
	# inputs = line + "\n"
	lines.append(line)

# print(inputs)
# lines = inputs.split('\n')
mini, maxi = float('inf'), float('-inf')
prev = None
first = None
mini, maxi = min(x, y), max(x, y)
for line in lines:
	if not len(line):
		continue
	cur = [int(x) for x in line.split()]
	mini = min([mini, cur[0], cur[1]])
	maxi = max([maxi, cur[0], cur[1]])
	if first == None:
		first = cur
	if prev != None:
		add_line(prev[0], prev[1], cur[0], cur[1])
	prev = cur
add_line(first[0], first[1], prev[0], prev[1])
ax.plot(mini, mini, maxi, maxi)
plt.xticks(np.arange(mini, maxi+1, 5.0))
plt.yticks(np.arange(mini, maxi+1, 5.0))
plt.show()