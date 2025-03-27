# @author Mohan Sharma
from typing import List

def longestConsecutiveNlogN(nums: List[int]) -> int:
    """
    Given an unsorted array of integers nums, return the length of the longest consecutive elements sequence.

    Example:
    Input: [100, 4, 200, 1, 3, 2]
    Output: 4
    Explanation: The longest consecutive elements sequence is [1, 2, 3, 4]. Therefore, its length is 4.

    Intuition:
    - Sort the array and use a pointer to scan left, incrementing a counter if the adjacent element is the next consecutive element.
    - If not, move the pointer to the current element and reset the counter.

    Time Complexity: O(n log n), where n is the number of elements in the array.
    Space Complexity: O(1), since we are sorting the array in place.

    Args:
        nums (List[int]): The array of integers.

    Returns:
        int: The length of the longest consecutive elements sequence.
    """
    if len(nums) == 0:
        return 0
    nums.sort()
    lcs = 0
    count = 1
    for i in range(1, len(nums)):
        if nums[i] == nums[i - 1]:
            continue
        if nums[i] == nums[i - 1] + 1:
            count += 1
            lcs = max(lcs, count)
        else:
            count = 1
    return lcs

def longestConsecutiveNSpace(nums: List[int]) -> int:
    """
    Given an unsorted array of integers nums, return the length of the longest consecutive elements sequence.

    Example:
    Input: [100, 4, 200, 1, 3, 2]
    Output: 4
    Explanation: The longest consecutive elements sequence is [1, 2, 3, 4]. Therefore, its length is 4.

    Intuition:
    - Problem states we can use O(n) space meaning we can use either index-based space or hash-based space.
    - Index-based won't work since the input can be negative as well but there is no predefined numbers to use the number as index, so we can use hash-based space.
    - Idea is to scan the array from left to right and try to find the smallest element in the sequence,
    	then use a loop to go back until the largest in the sequence is found, then do max number - current number and update global maxCount.

    Time Complexity: O(n), where n is the number of elements in the array.
    Space Complexity: O(n), for storing the elements in the set.

    Args:
        nums (List[int]): The array of integers.

    Returns:
        int: The length of the longest consecutive elements sequence.
    """
    if len(nums) == 0:
        return 0
    lcs = 0
    seen = set(nums)
    for num in nums:
        if num - 1 not in seen:
            m = num + 1
            while m in seen:
                m += 1
            lcs = max(lcs, m - num)
    return lcs

def longestConsecutive(nums: List[int]) -> int:
    """
    Given an unsorted array of integers nums, return the length of the longest consecutive elements sequence.

    Example:
    Input: [100, 4, 200, 1, 3, 2]
    Output: 4
    Explanation: The longest consecutive elements sequence is [1, 2, 3, 4]. Therefore, its length is 4.

    Intuition:
    - In the above approach, we scan from one end and go back to find the sequence.
    - Now if you think about it, what if I pick one element from the start of the set and go both ways until the sequence is broken.
    - To avoid redoing the same element again, we can remove it from the set so that we don't visit it again while we scan the set again.
    - Then move to the next element and do the same, this way we can avoid the nested loop and do it in one scan.

    Time Complexity: O(n), where n is the number of elements in the array.
    Space Complexity: O(n), for storing the elements in the set.

    Args:
        nums (List[int]): The array of integers.

    Returns:
        int: The length of the longest consecutive elements sequence.
    """
    if len(nums) == 0:
        return 0
    lcs = 0
    seen = set(nums)
    while seen:
        left_pointer = next(iter(seen))
        right_pointer = left_pointer + 1
        count = 0
        while left_pointer in seen:
            seen.remove(left_pointer)
            left_pointer -= 1
            count += 1
        while right_pointer in seen:
            seen.remove(right_pointer)
            right_pointer += 1
            count += 1
        lcs = max(lcs, count)
    return lcs

if __name__ == '__main__':
    nums = [100, 4, 200, 1, 3, 2]
    print(longestConsecutive(nums))  # 4
    nums = [0, 3, 7, 2, 5, 8, 4, 6, 0, 1]
    print(longestConsecutive(nums))  # 9
    nums = [1, 2, 0, 1]
    print(longestConsecutive(nums))  # 3
    nums = [1, 2, 0, 1, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12]
    print(longestConsecutive(nums))  # 13
    nums = [1, 2, 0, 1, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12]
    print(longestConsecutive(nums))  # 13
