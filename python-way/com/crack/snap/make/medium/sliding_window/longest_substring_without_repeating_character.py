# @author Mohan Sharma

def lengthOfLongestSubstring(s: str) -> int:
	"""
	Problem: Given a string s, find the length of the longest substring without duplicate characters s = "abcabcbb"
	
	Intuition:
	Brute force: Find all substrings and check which is longest without duplicates it will be O(n^2) with O(n) space or O(n^3) time
	Since we need substring without duplicates we can use two pointer technique or sliding window
	Have both pointers initially at 0 and move the end pointer till the end of the string
	Now between this start and end pointer we will a mechanism to keep track of duplicates
	Here we can use hashing to keep track of the characters and it's index, while moving right if we find a duplicate in the hashmap
	let's get the index of the duplicate as start and find the length of substring between start and end and keep track of max length
	
	Time Complexity: O(n), where n is the length of the string.
	Space Complexity: O(min(n, m)), where n is the length of the string and m is the size of the character set.
	"""
	if len(s) < 2:
		return len(s)
	
	dict = {}
	start, end, max_length = 0, 0, 0
	while end < len(s):
		letter = s[end]
		if letter in dict:
			start = max(start, dict.get(letter) + 1)
		dict[letter] = end
		max_length = max(max_length, end - start + 1)
		end += 1
	
	return max_length

def lengthOfLongestSubstringOptimisedUsingTemplate(s: str) -> int:
	"""
	Problem: Given a string s, find the length of the longest substring without duplicate characters s = "abcabcbb"
	
	Intuition:
	We can use the same logic as above but instead of using a hashmap we can use an array of size 128 (ASCII size) to keep track of the characters
	We can scan the string from left to right, let's have two pointers start = 0 and end = 0 and keep track of the character count in the array,
	If ever the count of a character is greater than 1 we know there is a duplicate and let's increment the counter variable.
	If the counter is greater than 0, then we know there is a duplicate in the string. Let's now scan the string again using the start pointer until the counter is 0 again
	a. If the start pointer's character count is > 0 then we know it is the duplicate and we can decrement the count in the array,
	   decrement the counter and increment the start pointer as for valid substring the start pointer should be just after the duplicate character
	b. If the start pointer's character count is 0 then we know it is not the duplicate and we can increment the start pointer
	   as the duplicate character will be encountered on the right since the counter is still > 0 and for valid substring the start pointer should be just after the duplicate character
	Now if we think through at any point in time if the counter is 0, the valid substring will be between start and end pointer where end > 0
	
	Time Complexity: O(n), where n is the length of the string.
	Space Complexity: O(1), as we are using a constant amount of extra space.
	"""
	if len(s) < 2:
		return len(s)
	
	start, end, max_length, counter = 0, 0, 0, 0
	dict = [0] * 128
	while end < len(s):
		letter = s[end]
		if dict[ord(letter)] > 0:
			counter += 1
		dict[ord(letter)] += 1
		end += 1
		while counter > 0:
			start_letter = s[start]
			if dict[ord(start_letter)] > 1:
				counter -= 1
			dict[ord(start_letter)] -= 1
			start += 1
		max_length = max(max_length, end - start)
	
	return max_length
	
if __name__ == '__main__':
	print(lengthOfLongestSubstring("abcabcbb"))  # Output: 3
	print(lengthOfLongestSubstring("bbbbb"))  # Output: 1
	print(lengthOfLongestSubstring("pwwkew"))  # Output: 3
	print(lengthOfLongestSubstring(""))  # Output: 0
	print("--Optimised--")
	print(lengthOfLongestSubstringOptimisedUsingTemplate("abcabcbb"))  # Output: 3
	print(lengthOfLongestSubstringOptimisedUsingTemplate("bbbbb"))  # Output: 1
	print(lengthOfLongestSubstringOptimisedUsingTemplate("pwwkew"))  # Output: 3
	print(lengthOfLongestSubstringOptimisedUsingTemplate(""))  # Output: 0
