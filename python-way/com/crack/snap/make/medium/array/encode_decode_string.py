# @author Mohan Sharma
from typing import List


def encode(strs: List[str]) -> str:
	"""
	Encodes a list of strings to a single string.
	
	Intuition:
	1. The challenge is to encode a list of strings in a way that we can uniquely decode them later.
	2. A naive approach using `String.join()` and `split()` fails when strings contain the delimiter (e.g., '#').
	3. Instead, we prefix each string with its length and a separator (e.g., "3#abc").
	4. This allows us to know exactly where each string starts and ends, even if they contain special characters.
	
	Example:
	Input: ["abc", "def#ghi", "jkl"]
	Encoded: "3#abc7#def#ghi3#jkl"
	
	Loop Iteration (for each string in strs):
	- This loop runs 'n' times—once for each string in the input list.
	
	For each string 's':
	- s.length() – O(1) (length of a string in Java is precomputed).
	- encoded.append(s.length()) – O(log₁₀(length)), but since the maximum length is Integer.MAX_VALUE (~2.14 billion), this is O(1) in practice.
	- encoded.append("#") – O(1) (a single character append).
	- encoded.append(s) – O(len(s)) (time to copy all characters of the string).
	
	Total work for each string: O(1) + O(1) + O(len(s)) = O(len(s)).
	Summing over all strings: T(n) = O(len(s₁) + len(s₂) + ... + len(sₙ)) = O(m), where 'm' is the total length of all input strings.
	
	Time Complexity: O(m) where m is the total length of all strings.
	- Each character is processed once during encoding.
	
	Space Complexity: O(m + n)
	- O(m) for the encoded output string.
	- O(n) for metadata (lengths of n strings).
	
	Args:
		strs (List[str]): List of strings to encode.
	
	Returns:
		str: Encoded string.
"""
	string_builder = []
	for str in strs:
		string_builder.append(f"{len(str)}#{str}")
	return ''.join(string_builder)


def decode(s: str) -> List[str]:
	"""
	Decodes a single string back to a list of strings.
	
	Intuition:
	1. We read the encoded string by extracting the length prefix for each substring.
	2. The length tells us exactly how many characters to capture after the separator ('#').
	3. This approach prevents misinterpretation of embedded '#' characters.
	
	Example:
	Encoded: "3#abc7#def#ghi3#jkl"
	Output: ["abc", "def#ghi", "jkl"]
	- We iterate through the encoded string once, parsing the length and extracting substrings.
	- Each character is processed once, resulting in O(m) time.
	
	Time Complexity: O(m)
	- Each character in the encoded string is processed exactly once.
	
	Space Complexity: O(m + n)
	- O(m) for the output list of decoded strings.
	- O(n) for maintaining intermediate metadata.
	
	Args:
		s (str): Encoded string.
	
	Returns:
		List[str]: Decoded list of strings.
"""
	word_len_index = 0
	result = []
	while word_len_index < len(s):
		hash_index = word_len_index
		
		while s[hash_index] != '#':
			hash_index += 1
		
		word_length = int(s[word_len_index:hash_index])
		next_word_len_index = hash_index + 1 + word_length
		result.append(s[hash_index + 1:next_word_len_index])
		word_len_index = next_word_len_index
	return result


if __name__ == '__main__':
	list = ["hello", "world", "java"]
	encoded_string = encode(list)
	print(encoded_string)
	print(decode(encoded_string))
