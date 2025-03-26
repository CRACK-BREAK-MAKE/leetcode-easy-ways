# @author Mohan Sharma
from collections import defaultdict

def groupAnagrams(strs):
	"""
	Groups anagrams from a list of strings.

	Intuition:
	Since we need to group, we can use hashing. We can use a dict where the key is a consistent representation of the characters in the word.
	One way to generate a consistent key is to sort the characters of the word. Another way is to use a character array
	where each index represents a character ('a' to 'z') and the value at each index represents the count of that character.

	Args:
		strs (list): List of strings to group by anagrams.

	Returns:
		list: A list of lists, where each sublist contains anagrams.

	Example:
		>>> groupAnagrams(["eat", "tea", "tan", "ate", "nat", "bat"])
		[['eat', 'tea', 'ate'], ['tan', 'nat'], ['bat']]

	Thought Process:
		1. Initialize a defaultdict of lists to store the grouped anagrams.
		2. Iterate through each word in the input list.
		3. For each word, create a character count array of size 26 (for each letter in the alphabet).
		4. Convert the character count array to a tuple to use as a key in the defaultdict.
		5. Append the word to the list corresponding to the key in the defaultdict.
		6. Convert the defaultdict values to a list and return it.

	Why using defaultdict:
		Using defaultdict(list) from the collections module will automatically create a new list for any new key accessed,
		avoiding the need to check if the key exists and initialize it manually. This makes the code more concise and readable.
	"""
	anagrams = defaultdict(list)
	for word in strs:
		counter = [0] * 26
		for letter in word:
			counter[ord(letter) - ord('a')] += 1
		key = tuple(counter)
		anagrams[key].append(word)
	return list(anagrams.values())


if __name__ == '__main__':
	strs = ["eat", "tea", "tan", "ate", "nat", "bat"]
	print(groupAnagrams(strs))
