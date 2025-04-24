# @author Mohan Sharma
from typing import Optional


class Node:
	def __init__(self, x: int, next: 'Node' = None, random: 'Node' = None):
		self.val = int(x)
		self.next = next
		self.random = random


class Solution:
	def copyRandomList(self, head: 'Optional[Node]') -> 'Optional[Node]':
		if not head:
			return None
		dict = {}
		current = head
		while current:
			dict[current] = Node(current.val)
			current = current.next
		
		current = head
		while current:
			dict[current].next = dict.get(current.next)
			dict[current].random = dict.get(current.random)
			current = current.next
		
		return dict[head]
