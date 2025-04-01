# @author Mohan Sharma

def characterReplacement(s: str, k: int) -> int:
	if len(s) < 2:
		return len(s)
	
	start, end, letter_max_count, max_length = 0, 0, 0, 0
	dict = [0] * 26
	
	while end < len(s):
		letter = ord(s[end]) - ord('A')
		dict[letter] += 1
		end += 1
		letter_max_count = max(letter_max_count, dict[letter])
		
		if (end - start - letter_max_count) > k:
			start_letter = s[start]
			dict[ord(start_letter) - ord('A')] -= 1
			start += 1
		max_length = max(max_length, end - start)
		
	return max_length

if __name__ == '__main__':
	print(characterReplacement("AABABBA", 1))  # Output: 4
	print(characterReplacement("AABABBA", 2))  # Output: 5
	print(characterReplacement("AABABBA", 3))  # Output: 7
	print(characterReplacement("AABABBA", 4))  # Output: 7
