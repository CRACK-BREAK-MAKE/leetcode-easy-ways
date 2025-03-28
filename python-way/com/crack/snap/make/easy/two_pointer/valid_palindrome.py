# @author Mohan Sharma
def isPalindrome(s: str) -> bool:
	if not s:
		return True
	s = s.lower()
	left, right = 0, len(s) - 1
	while left < right:
		left_letter, right_letter = s[left], s[right]
		if not (('a' <= left_letter <= 'z') or ('0' <= left_letter <= '9')):
			left += 1
		elif not (('a' <= right_letter <= 'z') or ('0' <= right_letter <= '9')):
			right -= 1
		elif left_letter != right_letter:
			return False
		else:
			left += 1
			right -= 1
	return True


if __name__ == '__main__':
	print(isPalindrome("A man, a plan, a canal: Panama"))
	print(isPalindrome("race a car"))
