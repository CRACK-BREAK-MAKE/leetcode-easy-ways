# @author Mohan Sharma
from typing import List


def twoSum(numbers: List[int], target: int) -> List[int]:
	"""
	Given a sorted increasing order array, return the indices (1-indexed) of two numbers, [index1, index2],
	such that they add up to a given target number and index1 < index2. Note that index1 and index2 cannot be equal,
	therefore you may not use the same element twice.
	-
	Intuition:
	Since the array is sorted, can we use two pointers to find the sum of the two numbers?
	Have a left pointer at the start of the array and a right pointer at the end of the array.
	If the sum of the two numbers is less than the target, increment the left pointer.
	If the sum of the two numbers is greater than the target, decrement the right pointer.
	If the sum of the two numbers is equal to the target, return the [leftIndex + 1, rightIndex + 1].
	-
	Time Complexity: O(n)
	- We only traverse the array once with the two pointers, so the operations are linear time.
	Space Complexity: O(1)
	- We use a constant amount of extra space for the pointers and the result array.
	-
	Args:
		numbers (List[int]): The sorted increasing order array.
		target (int): The target sum.
	
	Returns:
		List[int]: The indices (1-indexed) of the two numbers that add up to the target.
	"""
	if not numbers:
		return []
	left, right = 0, len(numbers) - 1
	while left < right:
		left_number, right_number = numbers[left], numbers[right]
		if left_number + right_number < target:
			left += 1
		elif left_number + right_number > target:
			right -= 1
		else:
			return [left + 1, right + 1]
	return []
