# @author Mohan Sharma
from typing import List

def largestRectangleAreaBruteForce(heights: List[int]) -> int:
	"""
	Problem: Given an array of integers heights representing the histogram's bar height where the width of each bar is 1,
	return the area of the largest rectangle in the histogram.
	Example: heights = [2,1,5,6,2,3] Answer: 10
	
	Intuition: Let's assume there are multiple buildings with different heights in a straight line.
	Now I can form a rectangle only if the buildings adjacent to me are larger than mine. So I can scan for the building
	index to the left and right of me to find the boundaries of the rectangle. What will define the boundaries of the rectangle?
	If any adjacent building is smaller than me, then I cannot include them in my rectangle, so it will be the boundary.
	e.g.     |
	     |   |   |
	|    |   |   |   |
	0    1   2   3   4
	In the above buildings, I can form a rectangle of height 2 and width 3 from index 1 to 3. Here, when I am at index 1,
	the left boundary will be index 0, and the right boundary will be index 3. So the area will be 2 * (3 - 0) = 6.
	
	Brute force: Move from left to right, and for each index, check the left and right boundaries, calculate the max area, and update
	the global max.
	
	Time complexity: O(n^2)
	Space complexity: O(1)
	"""
	if not heights:
		return 0
	end, area = 0, 0
	while end  < len(heights):
		current_height = heights[end]
		left, right = end, end
		while left > 0 and heights[left - 1] >= current_height:
			left -= 1
		while right < len(heights):
			if right == len(heights) - 1 or heights[right + 1] < current_height:
				break
			right += 1
		area = max(area, (right - left + 1) * current_height)
		end += 1
	return area

def largestRectangleArea(heights: List[int]) -> int:
	"""
	If you notice one detail: if the right building is smaller than the current building, we need to stop and
	calculate the area. That means as we scan the buildings from left to right, if the next building is larger,
	we can keep moving right, but if it is smaller, then we know all buildings to the left of it are smaller,
	meaning we start calculating the area of all the buildings to the left of it.
	
	Example: If the array was [5, 2, 3, 4, 1], when we reach 1, we can calculate the area for the building with height 4,
	which will be max_area = max(max_area, 4 * (4th index - 3rd index)) = 4. Then we can move to the next building,
	which is 3, so the area will be max_area = max(max_area, 3 * (4th index - 2nd index)) = 6. Then we can move to the next building,
	which is 2, so the area will be max_area = max(max_area, 2 * (4th index - 1st index)) = 6.
	
	If you see the pattern, if the current height is greater than the previous height, we keep the current height in a data
	structure. But if the current height is less than the previous height, we move back and calculate the heights. That means
	we need a data structure to keep track of the index of the previous heights, and while we calculate the area, we remove
	the last height from the data structure. So we need a LIFO (stack) data structure to keep track of the heights.
	"""
	if not heights:
		return 0
	stack, area, end = [], 0, 0
	while end <= len(heights):
		# Let's say all heights are increasing order at the end of the array we will never calculate the area.
		# So the idea is when we reach the end of the array we make the height as 0 so that we can calculate
		# the area for all the heights that are present in the stack.
		current_height = 0 if end == len(heights) else heights[end]
		if not stack or current_height >= heights[stack[-1]]:
			stack.append(end)
			end += 1
		else:
			top = stack.pop()
			# why peek + 1 bcs we already removed the last height from the stack so the stack now
			# contains the previous of the last height but we want to calculate the last height
			# so we move the add 1 to the index
			start = stack[-1] + 1 if stack else 0
			area = max(area, heights[top] * (end - start))
	return area
	
if __name__ == "__main__":
	heights = [2,1,5,6,2,3]
	print(largestRectangleArea(heights))  # Output: 10
	heights = [2,4]
	print(largestRectangleArea(heights))  # Output: 4
	heights = []
	print(largestRectangleArea(heights))  # Output: 0
	heights = [1]
	print(largestRectangleArea(heights))  # Output: 1
	
