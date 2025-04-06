# @author Mohan Sharma
from typing import List

def evalRPN(tokens: List[str]) -> int:
	stack = []
	for token in tokens:
		if token in ["+", "-", "/", "*"]:
			if len(stack) < 2:
				raise Exception("Invalid RPN expression")
			b = stack.pop()
			a = stack.pop()
			stack.append(int(eval(f"{a}{token}{b}")))
		else:
			stack.append(int(token))
	if len(stack) != 1:
		raise Exception("Invalid RPN expression")
	return stack[0]

if __name__ == '__main__':
	print(evalRPN(["2", "1", "+", "3", "*"]))  # Output: 9
	print(evalRPN(["4", "13", "5", "/", "+"]))  # Output: 6
	print(evalRPN(["10", "6", "9", "3", "/", "-", "*"]))  # Output: 70
	print(evalRPN(["2", "1", "+"]))  # Output: 3
	print(evalRPN(["4", "13", "5", "/", "+"]))  # Output: 6
