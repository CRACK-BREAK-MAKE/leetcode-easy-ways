# @author Mohan Sharma
from typing import List

def findMinBruteForce(nums: List[int]) -> int:
	"""
     Problem: Suppose an array of length n sorted in ascending order is rotated between 1 and n times. Given the sorted rotated array
     nums of unique elements, return the minimum element of this array. You must write an algorithm that runs in O(log n) time.
     
     Intuition: Given it is sorted in ascending order but rotated meaning if we rotate 1 time then [a[0], a[1], a[2], .., a[n-2], a[n-1]]
     becomes [a[n-1], a[0], a[1], a[2], ..., a[n-2]]
     
     In brute force approach I can iterate over the array till n - 2 and check if a[i] > a[i + 1] then return a[i + 1]
     
     Time Complexity: O(n)
     Space Complexity: O(1)
	"""
	if not nums:
		return -1
	minimum = nums[0]
	for i in range(0, len(nums) - 1):
		if nums[i] > nums[i + 1]:
			minimum = nums[i + 1]
	return minimum

def findMin(nums: List[int]) -> int:
	"""
	The key insight is that in a rotated sorted array, there's a "pivot point" where the array wraps around. The minimum element
    is exactly at this pivot point.
    
    Using Binary search we can exploit a key property, in a rotated sorted array, if you compare any element with the
    rightmost element (nums[end]), you can determine which "side" of the pivot(rotation point) you're on.
    If nums[mid] > nums[end], then mid is in the left portion (before the pivot), so the minimum must be in the right portion (including the pivot).
    If nums[mid] ≤ nums[end], then mid is either the minimum or in the right portion (after the pivot), so the minimum must be
    in the left portion (including mid).
    
    The key insight is comparing elements to determine which side of the "anomaly" (the pivot point) you're on, and then
    eliminating the irrelevant half.
    The reason nums[start] contains the minimum at the end is because our binary search specifically narrows down to the pivot point
    where the smallest element is located. The loop terminates when start and end converge on this pivot point.
    
    Let's walk through Example 1: [4, 5, 6, 7, 0, 1, 2]
    Initial: start = 0, end = 6, mid = 3
    nums[mid] = 7, nums[end] = 2
    7 > 2, so minimum is to the right: start = mid + 1 = 4
    Next iteration: start = 4, end = 6, mid = 5
    nums[mid] = 1, nums[end] = 2
    1 < 2, so minimum could be at mid or to the left: end = mid = 5
    Next iteration: start = 4, end = 5, mid = 4
    nums[mid] = 0, nums[end] = 1
    0 < 1, so minimum could be at mid or to the left: end = mid = 4
    Next iteration: start = 4, end = 4 (loop terminates)
    Answer: nums[start] = 0
    
    Time Complexity: O(log n)
    Space Complexity: O(1)
	"""
	if not nums:
		return -1
	start, end = 0, len(nums) - 1
	while start < end:
		mid = start + (end - start) // 2
		if nums[mid] > nums[end]:
			# mid is greater than end meaning the minimum element is in the right half
			start = mid + 1
		else:
			# mid is less than end meaning the minimum element is in the left half
			end = mid
	return nums[start] # since ascending order and rotated, the start will be the minimum element
	
if __name__ == "__main__":
	print(findMin([3, 4, 5, 1, 2])) # 1
	print(findMin([4, 5, 6, 7, 0, 1, 2])) # 0
	print(findMin([1])) # 1
	
