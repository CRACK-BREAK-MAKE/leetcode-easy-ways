# @author Mohan Sharma

from typing import Optional

from com.crack.snap.make.easy.linkedlist.list_node import ListNode


def reverseListIterative(head: Optional[ListNode]) -> Optional[ListNode]:
	current_node = head
	previous_node = None
	while current_node:
		next_node = current_node.next
		current_node.next = previous_node
		previous_node = current_node
		current_node = next_node
	return previous_node

def reverseListRecursive(head: Optional[ListNode]) -> Optional[ListNode]:
	def helper(current_node, previous_node):
		if not current_node:
			return previous_node
		next_node = current_node.next
		current_node.next = previous_node
		return helper(next_node, current_node)
	return helper(head, None)

def reverseListRecursiveWithoutHelper(head: Optional[ListNode]) -> Optional[ListNode]:
	if not head or not head.next:
		return head
	reversed_head = reverseListRecursiveWithoutHelper(head.next)
	head.next.next = head
	head.next = None
	return reversed_head
