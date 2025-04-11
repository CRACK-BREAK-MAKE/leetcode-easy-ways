# @author Mohan Sharma
from typing import List

def searchMatrixBruteForce(matrix: List[List[int]], target: int) -> bool:
	"""
	Problem: You are given an m x n integer matrix with the following two properties:
	1. Each row is sorted in non-decreasing order.
	2. The first integer of each row is greater than the last integer of the previous row.
	Given an integer target, return true if target is in the matrix or false otherwise.
	You must write a solution in O(log(m * n)) time complexity.
	
	Intuition: Treat the 2D matrix as a flattened sorted array. Use binary search to find the target.
	Calculate the row and column indices from the mid-point of the flattened array.
	
	Time Complexity: O(log(m * n)) where m is the number of rows and n is the number of columns.
	Space Complexity: O(1)
	"""
	if not matrix:
		return False
	for row in matrix:
		if row[0] <= target <= row[-1]:
			return binary_search(row, target)
	return False
	
def binary_search(arr, target):
	start, end = 0, len(arr) - 1
	while start <= end:
		mid = start + (end - start) // 2
		if target == arr[mid]:
			return True
		elif target < arr[mid]:
			end = mid - 1
		else:
			start = mid + 1
	return False

def searchMatrix(matrix: List[List[int]], target: int) -> bool:
	"""
	In the brute force approach above, to check the element of the array, we are literally iterating over the array one by one.
	But if you think about it, we can search any array just by keeping track of the row.
	Initially, when row is 0, we can check the mid_value = matrix[row][mid_index]. Now, if the target is greater than the mid_value,
	ideally, we move the start to mid_index + 1 to search in the other half. But at the same time, if the target is also greater
	than the last element of the row (target > matrix[row][len(matrix[row]) - 1]), then we can move to the next row and so on.
	
	Time Complexity: O(log(m * n)) where m is the number of rows and n is the number of columns.
	Space Complexity: O(1)
	"""
	if not matrix:
		return False
	start, end, row = 0, len(matrix[0]) - 1, 0
	
	while start <= end and row < len(matrix):
		mid_index = start + (end - start) //2
		mid_value = matrix[row][mid_index]
		if mid_value == target:
			return True
		elif mid_value < target:
			if target > matrix[row][-1]:
				row += 1
				start = 0
				end = len(matrix[0]) - 1
			else:
				start = mid_index + 1
		else:
			end = mid_index - 1
	return False
	
if __name__ == "__main__":
	matrix = [[1, 3, 5, 7],[10, 11, 16, 20],[23, 30, 34, 50],[60, 61, 62, 63]]
	target = 3
	print(searchMatrix(matrix, target)) # Output: True
	target = 13
	print(searchMatrix(matrix, target)) # Output: False
	print(searchMatrix([[1]], 0))
