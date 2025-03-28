# @author Mohan Sharma
from typing import List


def threeSum(nums: List[int]) -> List[List[int]]:
	"""
	Problem: Given an integer array nums, return all the triplets [nums[i], nums[j], nums[k]]
	such that i != j, i != k, and j != k, and nums[i] + nums[j] + nums[k] == 0.
	The solution set must not contain duplicate triplets.

	Intuition:
	First, we can use brute force with 3 loops starting from i=0 to n-3, j=i+1 to n-2, and k=j+1 to n-1.
	Check if nums[i] + nums[j] + nums[k] == 0, and return the triplet. Also, if nums[i] == nums[i+1], skip;
	if nums[j] == nums[j+1], skip; and if nums[k] == nums[k+1], skip to avoid duplicates. The skip logic works because
	we iterate from left to right for each loop, so if there are two 1's, we can take the first 1 and skip the second 1.
	The complexity is O(n^3) and space is O(1).

	But we can do better. Sort the array, then use one loop from i=0 to n-3 to get the first element and two pointers:
	left = i + 1 and right = n - 1. Now check if nums[i] + nums[left] + nums[right] == 0.
	To avoid duplicates, since the same element will be adjacent to each other, we can use the above skip logic.

	Time Complexity: O(n^2)
	- Sorting the array takes O(n log n) and the two-pointer technique takes O(n^2).
	- Overall, the time complexity is dominated by the O(n^2) part.

	Space Complexity: O(1)
	- We use a constant amount of extra space for the pointers and the result list.

	Args:
		nums (List[int]): The input array of integers.

	Returns:
		List[List[int]]: A list of lists containing all unique triplets that sum up to zero.
	"""
	if not nums or len(nums) < 3:
		return []
	nums.sort()
	result = []
	for i in range(0, len(nums) - 2):
		if (i == 0 or (i > 0 and nums[i] != nums[i - 1])):
			left, right, sum = i + 1, len(nums) - 1, -nums[i]
			while left < right:
				left_number, right_number = nums[left], nums[right]
				if left_number + right_number == sum:
					result.append([nums[i], left_number, right_number])
					while left < right and nums[left] == nums[left + 1]:
						left += 1
					while left < right and nums[right] == nums[right - 1]:
						right -= 1
					left += 1
					right -= 1
				elif left_number + right_number < sum:
					while left < right and nums[left] == nums[left + 1]:
						left += 1
					left += 1
				else:
					while left < right and nums[right] == nums[right - 1]:
						right -= 1
					right -= 1
	return result
	
if __name__ == '__main__':
	print(threeSum([-1, 0, 1, 2, -1, -4]))
	print(threeSum([]))
	print(threeSum([0]))
	print(threeSum([0, 0, 0]))
