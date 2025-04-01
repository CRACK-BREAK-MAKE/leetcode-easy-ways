# @author Mohan Sharma

def checkInclusionO26n(s1: str, s2: str) -> bool:
	"""
	Problem: Given two strings s1 and s2, return true if s2 contains a permutation of s1, or false.
	In other words, one of s1's permutations is the substring of s2.
	
	Intuition: When we say permutation meaning same number of characters but can be in any order as long as characters are together.
	That means we need to count the number of characters of the first string.
	Then scan s2 from left to right and as soon as the index is equal to or greater than the length of s1, we need to check
	for the candidate substring in s2 if it is valid or not.
	A naive way would be to maintain an array of count of characters in s1 and as we scan the second string from left to right,
	we can decrement the character count in the array. When the index is >= len(s1), we can re-increment the character count from
	the start using dp[i - len(s1)] to ensure that we are always checking the s2 string in a window of length len(s1).
	Then we can check if the count of all characters in the array is 0 and return true, otherwise at the end of the scan of s2, return false.
	
	Time Complexity: O(26n), where n is the length of s2.
	Space Complexity: O(1), as we are using a constant amount of extra space.
	"""
	if len(s1) == 0:
		return True
	if len(s1) > len(s2):
		return False
	if s1 == s2:
		return True
	
	start, end = 0, 0
	dict = [0] * 26
	
	for letter in s1:
		dict[ord(letter) - ord('a')] += 1
	
	while end < len(s2):
		ch = ord(s2[end]) - ord('a')
		dict[ch] -= 1
		end += 1
		if end >= len(s1):
			if all(x == 0 for x in dict):
				return True
			sch = ord(s2[start]) - ord('a')
			dict[sch] += 1
			start += 1
	return False

def checkInclusion(s1: str, s2: str) -> bool:
	"""
	The above solution is perfectly fine but we can optimize it further instead of calling `all(x == 0 for x in dict)`
	which in turn checks 26 characters in the array again and again for each character in s2.
	We can maintain a counter variable which will be initialized with `len(s1)`.
	As we scan the second string, we check if the count of the letter of s2 is greater than 0 in the array (i.e., `dict[ch] > 0`).
	If the count is greater than 0, we know that the letter exists in s1 and we can decrement the counter, also we decrement the count of the letter in the array.
	In the valid sliding window (i.e., `end >= len(s1)`), like we did above, we check if the counter is 0 and return true.
	Otherwise, we increment the count of the letter of s2 in the array from the start of the window so always a valid window is maintained.
	"""
	if len(s1) == 0:
		return True
	if len(s1) > len(s2):
		return False
	if s1 == s2:
		return True
	
	start, end, counter = 0, 0, len(s1)
	dict = [0] * 26
	for letter in s1:
		dict[ord(letter) - ord('a')] += 1
	while end < len(s2):
		ch = ord(s2[end]) - ord('a')
		if dict[ch] > 0:
			counter -= 1
		dict[ch] -= 1
		end += 1
		
		if end >= len(s1):
			if counter == 0:
				return True
			sch = ord(s2[start]) - ord('a')
			dict[sch] += 1
			if dict[sch] > 0:
				counter += 1
			start += 1
	return False
	
	
if __name__ == '__main__':
	print(checkInclusion("ab", "eidbaooo"))  # Output: True
	print(checkInclusion("ab", "eidboaoo"))  # Output: False
	print(checkInclusion("abc", "ccccbbbbaaaa"))  # Output: False
	print(checkInclusion("abc", "cccbaaaaccc"))  # Output: True
	
