import matplotlib.pyplot as plt
from matplotlib.patches import Rectangle


#define Matplotlib figure and axis
fig, ax = plt.subplots()

def add_rect(x1, y1, x2, y2):
	width, height = x2 - x1, y2 - y1
	ax.add_patch(Rectangle((x1, y1), width, height, alpha=1, fill=None))

#create simple line plot
ax.plot([0, 0],[0, 0])

# inputs = """
# -1135 -1150 1090 -760
# 575 -765 1090 1170
# -1165 675 580 1170
# -1165 -655 -785 680
# -790 -655 500 -380
# 255 -385 500 620
# -700 415 260 620
# -700 -280 -500 420
# -505 -280 175 -95
# 80 -100 175 370
# -450 265 85 370
# -450 -35 -360 270
# -365 -35 40 45
# -35 40 40 240
# -305 175 -30 240
# -305 70 -245 180
# -250 70 -65 140
# """

inputs = """
0 0 10 10 
5 -5 15 15 
"""

lines = inputs.split('\n')
print(lines)
for line in lines:
	if not len(line):
		continue
	nums = [int(x) for x in line.split()]
	add_rect(nums[0], nums[1], nums[2], nums[3])
#add rectangle to plot
# ax.add_patch(Rectangle((1, 1), 2, 6))
#display plot
plt.show()