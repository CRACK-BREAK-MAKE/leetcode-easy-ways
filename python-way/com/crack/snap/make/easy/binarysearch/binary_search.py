# @author Mohan Sharma
from typing import List

def search(nums: List[int], target: int) -> int:
	"""
	Problem: Given an array of integers nums which is sorted in ascending order, and an integer target,
	write a function to search target in nums. If target exists, then return its index. Otherwise, return -1.
	You must write an algorithm with O(log n) runtime complexity.
	
	Intuition: Since the array is sorted and the problem statement requires O(log n) time complexity, we can use binary search
	to find the target. The idea is to keep dividing the search space in half until we find the target or exhaust the search space.
	First, we will check if the middle of the array is equal to the target. If it is, we return the index of the middle element.
	If the middle element is less than the target, we search the right half of the array.
	If the middle element is greater than the target, we search the left half of the array.
	
	Why mid = left + (right - left) // 2 instead of (left + right) // 2?
	The expression mid = left + (right - left) // 2 is used instead of mid = (left + right) // 2
	to prevent integer overflow when left and right are large integers.
	Suppose left = 2_000_000_000 and right = 2_000_000_001.
	left + right = 2_000_000_000 + 2_000_000_001 = 4_000_000_001
	This value exceeds the maximum value of a 32-bit integer (2_147_483_647), causing an integer overflow and resulting in an incorrect calculation.
	
	Time Complexity: O(log n)
	Space Complexity: O(1)
	"""
	if not nums:
		return -1
	return binary_search(nums, target, 0, len(nums) - 1)

def binary_search_recursive(nums: List[int], target: int, left: int, right: int) -> int:
	if left > right:
		return -1
	mid = left + (right - left) // 2
	if nums[mid] == target:
		return mid
	elif nums[mid] < target:
		return binary_search_recursive(nums, target, mid + 1, right)
	else:
		return binary_search_recursive(nums, target, left, mid - 1)

def binary_search(nums: List[int], target: int, left: int, right: int) -> int:
	"""
	    Recursive approach can lead to stack overflow for large arrays due to deep recursion. Iterative approach
     	avoids the overhead of recursive calls and is more memory-efficient.
	"""
	while left <= right:
		mid = left + (right - left) // 2
		if nums[mid] == target:
			return mid
		elif nums[mid] < target:
			left = mid + 1
		else:
			right = mid - 1
	return -1
	
if __name__ == "__main__":
	nums = [1, 2, 3, 4, 5]
	target = 3
	result = search(nums, target)
	print(f"Target {target} found at index: {result}")  # Output: Target 3 found at index: 2
