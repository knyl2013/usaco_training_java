import matplotlib.pyplot as plt
from matplotlib.patches import Rectangle

def add_point(x, y):
	plt.plot(x, y, 'ro')

def add_line(x1, y1, x2, y2):
	plt.plot([x1, x2], [y1, y2])
#define Matplotlib figure and axis
fig, ax = plt.subplots()

#create simple line plot
input() # skip first line
x, y = input().split()
# l = ""
# while (l = input()):
# 	print(l)
print(x, y)
ax.plot([0, 0],[0, 10], alpha=0)
add_point(x, y)
# inputs = """
# 0 0
# 2 0
# 2 2
# 0 2
# """
inputs = ""

try:
	while True:
		s = input()
		inputs = s + "\n"
except Exception as e:
	pass

print(inputs)
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