# @author Mohan Sharma
from typing import List

def dailyTemperaturesBruteForce(temperatures: List[int]) -> List[int]:
	"""
	Problem: Given an array of integers temperatures representing the daily temperatures, return an array answer such that answer[i]
	is the number of days you have to wait after the ith day to get a warmer temperature. If there is no future day
	for which this is possible, keep answer[i] == 0 instead.
	Example:
	Input: temperatures = [73,74,75,71,69,72,76,73]
	Output: [1,1,4,2,1,1,0,0]
	
	Intuition: The first approach that comes to mind is for each index to scan the rest of the array to find the next greater element.
	And then move to the next index and do the same. This will take O(n^2) time complexity.
	Might TLE for large inputs.
	"""
	result = [0] * len(temperatures)
	for i in range(0, len(temperatures)):
		for j in range(i + 1, len(temperatures)):
			if temperatures[j] > temperatures[i]:
				result[i] = j - i
				break
	return result

def dailyTemperatures(temperatures: List[int]) -> List[int]:
	"""
	If you analyze the solution above you will see for an index we keep going right until we find a greater element
	then we track back to the index we started from. This is a waste of time.
	
	If we can somehow remember the previous values we have seen and their index we can do this in O(n) time complexity.
	Meaning as we go from left to right we can keep track of the previous index, if the current value is less than the
	last previous index value we add to the data structure and move to the next index. If the current value is
	greater than the last index value that means we got one valid answer, we subtract the current index from the last index
	and this becomes the result for the previous index temperature as per the problem statement. Since the previous index is
	not needed anymore we remove it from the data structure and check for the previous of the previous index and so on.
	
	That means we need a LIFO data structure to keep track of the previous index and their values.
	"""
	stack = []
	result = [0] * len(temperatures)
	
	for i in range(0, len(temperatures)):
		while stack and temperatures[i] > temperatures[stack[-1]]:
			previous_index = stack.pop()
			result[previous_index] = i - previous_index
		stack.append(i)
	return result
	
	
if __name__ == '__main__':
	print(dailyTemperatures([73, 74, 75, 71, 69, 72, 76, 73]))  # Output: [1, 1, 4, 2, 1, 1, 0, 0]
	print(dailyTemperatures([30, 40, 50, 60]))  # Output: [1, 1, 1, 0]
	print(dailyTemperatures([30, 60, 90]))  # Output: [1, 1, 0]
