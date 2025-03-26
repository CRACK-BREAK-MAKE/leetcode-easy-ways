# @author Mohan Sharma
from typing import List


def productExceptSelf2nSpace(nums: List[int]) -> List[int]:
	"""
	Intuition: To find the product of an array except itself, we need to calculate the product of all elements to the left and right of each index.
	For example, given the array [1, 2, 3, 4], the product array except itself would be [24, 12, 8, 6].
	- For index 0, the product is 2 * 3 * 4 = 24.
	- For index 1, the product is 1 * 3 * 4 = 12.
	- For index 2, the product is 1 * 2 * 4 = 8.
	- For index 3, the product is 1 * 2 * 3 = 6.

	Approach: We can use two arrays to keep track of the left and right products for each index.
	1. Calculate the left product for each index and store it in the prefix array.
	2. Calculate the right product for each index and store it in the suffix array.
	3. Multiply the left and right products for each index to get the final result.

	Time Complexity: O(n), where n is the number of elements in the array.
	Space Complexity: O(n), for storing the left and right products.

	Args:
		nums (List[int]): The array of integers.

	Returns:
		List[int]: An array of the product of all elements except itself.
	"""
	left_product = [1] * len(nums)
	
	current_product = nums[0]
	for i in range(1, len(nums)):
		left_product[i] = current_product
		current_product *= nums[i]
	
	right_product = [1] * len(nums)
	current_product = nums[-1]
	
	for i in range(len(nums) - 2, -1, -1):
		right_product[i] = current_product
		current_product *= nums[i]
	
	for i in range(len(nums)):
		nums[i] = left_product[i] * right_product[i]
	
	return nums


def productExceptSelfConstantSpace(nums: List[int]) -> List[int]:
	"""
	Intuition: It is very similar to the above logic but while we used two arrays to keep track of left and right product,
	we can use the result array itself to keep track of the left product. Then, we scan the result array from right
	and use the input array to calculate the right product and store it in the result array.
	For example, given the array [1, 2, 3, 4], the product array except itself would be [24, 12, 8, 6].
	- For index 0, the product is 2 * 3 * 4 = 24.
	- For index 1, the product is 1 * 3 * 4 = 12.
	- For index 2, the product is 1 * 2 * 4 = 8.
	- For index 3, the product is 1 * 2 * 3 = 6.

	Approach:
	1. Calculate the left product for each index and store it in the result array.
	2. Calculate the right product for each index and multiply it with the left product in the result array.

	Time Complexity: O(n), where n is the number of elements in the array.
	Space Complexity: O(1), since we are using the output array itself to store the left product.

	Args:
		nums (List[int]): The array of integers.

	Returns:
		List[int]: An array of the product of all elements except itself.
	"""
	n = len(nums)
	result = [1] * n
	for i in range(1, n):
		result[i] = result[i - 1] * nums[i - 1]
	
	right_product = nums[-1]
	for i in range(n - 2, -1, -1):
		result[i] *= right_product
		right_product *= nums[i]
	
	return result


if __name__ == '__main__':
	nums = [1, 2, 3, 4]
	print(productExceptSelfConstantSpace(nums))
	print(productExceptSelf2nSpace(nums))
