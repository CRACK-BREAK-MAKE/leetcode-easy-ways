# @author Mohan Sharma

import heapq
from typing import List

def topKFrequentUsingMaxHeap(nums: List[int], k: int) -> List[int]:
	"""
	Finds the top K frequent elements using a Max Heap (PriorityQueue).

	Intuition:
	To find the top K frequent elements, we need to count the frequency of each element in the array.
	Then, we need to identify the K elements with the highest frequencies.

	There are multiple ways to solve this problem:
	1. We can sort the frequency map in descending order of the values to get the top k with O(n + n log n + k).
	2. Using a Max Heap (PriorityQueue) to keep track of the top K elements.
	3. Using Bucket Sort to group elements by their frequencies.

	Example:
	Input: nums = [1, 1, 1, 2, 2, 3], k = 2
	Output: [1, 2]
	Explanation: The elements 1 and 2 are the most frequent elements in the array.

	Input: nums = [1], k = 1
	Output: [1]
	Explanation: The element 1 is the only element in the array, so it is the most frequent.

	Time Complexity:
	- Building the frequency map: O(n), where n is the number of elements in the array.
	- Adding elements to the max heap: O(n log n), since each insertion into the heap takes O(log n) time.
	- Extracting the top K elements from the heap: O(k log n), since each extraction takes O(log n) time.
	Overall time complexity: O(n log n)

	Args:
		nums (List[int]): The array of integers.
		k (int): The number of top frequent elements to return.

	Returns:
		List[int]: An array of the top K frequent elements.
	"""
	if not nums or k == 0:
		return []
	if k == 1 and len(nums) == 1:
		return nums
	
	# Step-1: Create and populate frequency count dictionary.
	frequency_map = {}
	for num in nums:
		frequency_map[num] = frequency_map.get(num, 0) + 1
	
	# Step-2: Create a max-heap using negative values to simulate max-heap behavior.
	max_heap = [(-frequency, num) for num, frequency in frequency_map.items()]
	heapq.heapify(max_heap)
	
	# Step-3: Extract the top K elements from the max-heap.
	top_k = []
	while k > 0 and max_heap:
		counter, num = heapq.heappop(max_heap)
		top_k.append(num)
		k -= 1
	
	return top_k


def topKFrequentUsingBucketSort(nums: List[int], k: int) -> List[int]:
	"""
	Finds the top K frequent elements using Bucket Sort.

	Intuition:
	To find the top K frequent elements, we need to count the frequency of each element in the array.
	Then, we can use Bucket Sort to group elements by their frequencies.

	Example:
	Input: nums = [1, 1, 1, 2, 2, 3], k = 2
	Output: [1, 2]
	Explanation: The elements 1 and 2 are the most frequent elements in the array.

	Input: nums = [1], k = 1
	Output: [1]
	Explanation: The element 1 is the only element in the array, so it is the most frequent.

	Time Complexity:
	- Building the frequency map: O(n), where n is the number of elements in the array.
	- Filling the bucket array: O(n), since we iterate over the frequency map.
	- Collecting the top K elements: O(n), in the worst case where all elements have unique frequencies.
	Overall time complexity: O(n)

	Args:
		nums (List[int]): The array of integers.
		k (int): The number of top frequent elements to return.

	Returns:
		List[int]: An array of the top K frequent elements.
	"""
	if not nums or k == 0:
		return []
	if k == 1 and len(nums) == 1:
		return nums
	
	# Step-1: Create and populate frequency count dictionary.
	frequency_map = {}
	for num in nums:
		frequency_map[num] = frequency_map.get(num, 0) + 1
	
	# Step-2: Create a bucket array where index represents frequency and value at that index is a list of numbers with that frequency.
	bucket = [[] for _ in range(len(nums) + 1)]
	for num, freq in frequency_map.items():
		bucket[freq].append(num)
	
	# Step-3: Iterate over the bucket array from the end, populate result until k > 0.
	top_k = []
	for i in range(len(bucket) - 1, 0, -1):
		if k == 0:
			break
		for num in bucket[i]:
			top_k.append(num)
			k -= 1
			if k == 0:
				break
	
	return top_k
	


if __name__ == '__main__':
	nums = [1, 1, 1, 2, 2, 3]
	k = 2
	print(topKFrequentUsingMaxHeap(nums, k))
	nums = [1]
	k = 1
	print(topKFrequentUsingMaxHeap(nums, k))
	nums = [1, 1, 1, 2, 2, 3]
	k = 2
	print(topKFrequentUsingBucketSort(nums, k))
	nums = [1]
	k = 1
	print(topKFrequentUsingBucketSort(nums, k))
	
