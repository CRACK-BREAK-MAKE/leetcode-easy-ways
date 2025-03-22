# @author Mohan Sharma

def two_sum_n2(nums, target) -> list | None:
	"""
	Finds two indices in the list `nums` such that their values add up to `target` using O(n^2) time complexity.

	Args:
		nums (list): List of integers to search through.
		target (int): The target sum to find.

	Returns:
		list | None: A list containing the two indices if a pair is found, otherwise None.

	Example:
		>>> two_sum_n2([2, 7, 11, 15], 9)
		[0, 1]

	Explanation:
		1. Iterate through the list `nums` using `enumerate` to get both the index `i` and the value `first_number`.
		2. For each `first_number`, iterate through the list again to get the index `j` and the value `second_number`.
		3. Check if the sum of `first_number` and `second_number` equals `target`:
			- If it does, return a list containing the indices `[i, j]`.
		4. If no pair is found after the loop, return None.
	"""
	for i, first_number in enumerate(nums):
		for j, second_number in enumerate(nums):
			if first_number + second_number == target:
				return [i, j]
	return None

def two_sum_nlogn(nums, target) -> list | None:
	"""
	Finds two indices in the list `nums` such that their values add up to `target` using O(n log n) time complexity.

	Args:
		nums (list): List of integers to search through.
		target (int): The target sum to find.

	Returns:
		list | None: A list containing the two indices if a pair is found, otherwise None.

	Example:
		>>> two_sum_nlogn([2, 7, 11, 15], 9)
		[0, 1]

	Explanation:
		1. Sort the list `nums` along with their original indices.
		2. Initialize two pointers `left` and `right`.
		3. Iterate through the list while `left` is less than `right`:
			- If the sum of the values at `left` and `right` is greater than `target`, decrement `right`.
			- If the sum is less than `target`, increment `left`.
			- If the sum equals `target`, return a list containing the original indices `[left, right]`.
		4. If no pair is found after the loop, return None.
	"""
	nums_with_indices = sorted((num, i) for i, num in enumerate(nums))
	left, right = 0, len(nums) - 1
	while left < right:
		current_sum = nums_with_indices[left][0] + nums_with_indices[right][0]
		if current_sum > target:
			right -= 1
		elif current_sum < target:
			left += 1
		else:
			return [nums_with_indices[left][1], nums_with_indices[right][1]]
	return None

def two_sum_hashing(nums, target) -> list | None:
	"""
	Finds two indices in the list `nums` such that their values add up to `target` using a hash map for O(n) time complexity.

	Args:
		nums (list): List of integers to search through.
		target (int): The target sum to find.

	Returns:
		list | None: A list containing the two indices if a pair is found, otherwise None.

	Example:
		>>> two_sum_hashing([2, 7, 11, 15], 9)
		[0, 1]

	Explanation:
		1. Initialize an empty dictionary `hash_map` to store the indices of the numbers.
		2. Iterate through the list `nums` using `enumerate` to get both the index `i` and the value `first_number`.
		3. Calculate `second_number` as the difference between `target` and `first_number`.
		4. Check if `second_number` is already in `hash_map`:
			- If it is, return a list containing the index of `second_number` from `hash_map` and the current index `i`.
		5. If `second_number` is not in `hash_map`, add `first_number` and its index `i` to `hash_map`.
		6. If no pair is found after the loop, return None.
	"""
	hash_map = {}
	for i, first_number in enumerate(nums):
		second_number = target - first_number
		if second_number in hash_map:
			return [hash_map[second_number], i]
		hash_map[first_number] = i
	return None
	
if __name__ == '__main__':
	arr = [2, 7, 11, 15]
	target = 13
	print(two_sum_n2(arr, target))
	print(two_sum_nlogn(arr, target))
	print(two_sum_hashing(arr, target))
