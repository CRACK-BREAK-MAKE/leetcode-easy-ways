# @author Mohan Sharma

def minWindow(s: str, t: str) -> str:
	if len(t) == 0 or len(s) == 0 or len(s) < len(t):
		return ""
	if s == t:
		return s
	
	start, end, result, counter = 0, 0, "", len(t)
	dict = [0] * 128

	for letter in t:
		dict[ord(letter)] += 1
		
	while end < len(s):
		ch = ord(s[end])
		if dict[ch] > 0:
			counter -= 1
		dict[ch] -= 1
		end += 1
		
		while counter == 0:
			if result == "" or end - start < len(result):
				result = s[start:end]
			sch = ord(s[start])
			dict[sch] += 1
			if dict[sch] > 0:
				counter += 1
			start += 1
	return result

if __name__ == '__main__':
	print(minWindow("ADOBECODEBANC", "ABC"))  # Output: "BANC"
	print(minWindow("a", "a"))  # Output: "a"
	print(minWindow("a", "aa"))  # Output: ""
	print(minWindow("a", ""))  # Output: ""
	print(minWindow("", "a"))  # Output: ""
	print(minWindow("", ""))  # Output: ""
	
