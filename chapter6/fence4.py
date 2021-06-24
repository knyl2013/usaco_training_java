import matplotlib.pyplot as plt
from matplotlib.patches import Rectangle

def add_point(x, y):
	plt.plot(x, y, 'ro')

def add_line(x1, y1, x2, y2):
	plt.plot([x1, x2], [y1, y2])
#define Matplotlib figure and axis
fig, ax = plt.subplots()

#create simple line plot
ax.plot([0, 0],[0, 10], alpha=0)
add_point(5, 5)
inputs = """
0 0
7 0
5 2
7 5
5 7
3 5
4 9
1 8
2 5
0 9
-2 7
0 3
-3 1
"""

lines = inputs.split('\n')
mini, maxi = float('inf'), float('-inf')
prev = None
first = None
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
plt.show()