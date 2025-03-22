# @author Mohan Sharma

from typing import List


def contains_duplicate_on2(nums: List[int]) -> bool:
	for i in range(len(nums)):
		for j in range(i+1, len(nums)):
			if nums[i] == nums[j]:
				return True
	return False

def contains_duplicate_hashing(nums: List[int]) -> bool:
	return len(nums) != len(set(nums))

def contains_duplicate_sorting_n2(nums: List[int]) -> bool:
	# inplace sort in worst n^2
	for i in range(1, len(nums)):
		j = i-1
		key = nums[i]
		while j >=0 and nums[j] > key:
			nums[j+1] = nums[j]
			j -= 1
		nums[j + 1] = key
	
	for i in range(1, len(nums)):
		if nums[i] == nums[i-1]:
			return True
	return False

def contains_duplicate_sorting_nlogn(nums: List[int]) -> bool:
	nums.sort()
	for i in range(1, len(nums)):
		if nums[i] == nums[i-1]:
			return True
	return False

if __name__ == '__main__':
	arr = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
	print(contains_duplicate_sorting_nlogn(arr))
	arr = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 1]
	print(contains_duplicate_sorting_nlogn(arr))
	arr = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 1, 2]
	print(contains_duplicate_sorting_nlogn(arr))
