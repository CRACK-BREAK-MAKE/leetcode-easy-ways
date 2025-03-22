# @author Mohan Sharma

def is_anagram(s: str, t: str) -> bool:
	"""
	Determines if two strings are anagrams of each other.

	An anagram is a word or phrase formed by rearranging the letters of a different word or phrase,
	typically using all the original letters exactly once.

	Args:
		s (str): The first string.
		t (str): The second string.

	Returns:
		bool: True if the strings are anagrams, False otherwise.

	Example:
		>>> is_anagram("anagram", "nagaram")
		True
		>>> is_anagram("rat", "car")
		False

	Explanation:
		1. Check if the lengths of the two strings are different. If they are, return False immediately.
		2. Initialize a list `counter` of size 26 (for each letter in the alphabet) with all zeros.
		3. Iterate through each character in both strings simultaneously.
			- For each character in the first string `s`, increment the corresponding position in `counter`.
			- For each character in the second string `t`, decrement the corresponding position in `counter`.
		4. After the loop, check if all values in `counter` are zero.
			- If they are, it means both strings have the same characters in the same frequency, so return True.
			- Otherwise, return False.
	"""
	if len(s) != len(t):
		return False
	counter = [0] * 26
	
	for i in range(len(s)):
		counter[ord(s[i]) - ord('a')] += 1
		counter[ord(t[i]) - ord('a')] -= 1
	
	return all(count == 0 for count in counter)


if __name__ == '__main__':
	s = "anagram"
	t = "nagaram"
	print(is_anagram(s, t))
	s = "rat"
	t = "car"
	print(is_anagram(s, t))
