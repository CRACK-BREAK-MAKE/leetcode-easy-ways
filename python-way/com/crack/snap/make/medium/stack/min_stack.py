# @author Mohan Sharma
from logging import exception
import sys

class MinStack:
	
	# Approach 1: Using two stacks
	# def __init__(self):
	# 	self.stack = []
	# 	self.min_stack = []
	#
	#
	# def push(self, val: int) -> None:
	# 	min_value = val
	# 	if self.min_stack:
	# 		min_value = min(min_value, self.min_stack[-1])
	# 	self.min_stack.append(min_value)
	# 	self.stack.append(val)
	#
	#
	# def pop(self) -> None:
	# 	if not self.stack:
	# 		raise exception("Stack is empty")
	# 	self.stack.pop()
	# 	self.min_stack.pop()
	#
	#
	# def top(self) -> int:
	# 	if not self.stack:
	# 		raise exception("Stack is empty")
	# 	return self.stack[-1]
	#
	#
	# def getMin(self) -> int:
	# 	if not self.min_stack:
	# 		raise exception("Stack is empty")
	# 	return self.min_stack[-1]
	
	def __init__(self):
		self.global_min = sys.maxsize
		self.stack = []


	def push(self, val: int) -> None:
		if (val <= self.global_min):
			self.stack.append(self.global_min)
			self.global_min = val
		self.stack.append(val)
	
	
	def pop(self) -> None:
		if not self.stack:
			raise exception("Stack is empty")
		if self.stack.pop() == self.global_min:
			self.global_min = self.stack.pop()
	
	
	def top(self) -> int:
		if not self.stack:
			raise exception("Stack is empty")
		return self.stack[-1]
	
	
	def getMin(self) -> int:
		if not self.stack:
			raise exception("Stack is empty")
		return self.global_min
