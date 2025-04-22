# @author Mohan Sharma
from typing import Optional

from com.crack.snap.make.easy.linkedlist.list_node import ListNode


def mergeTwoLists(self, list1: Optional[ListNode], list2: Optional[ListNode]) -> Optional[ListNode]:
	"""
     Problem: You are given the heads of two sorted linked lists list1 and list2.
     Merge the two lists into one sorted list. The list should be made by splicing together the nodes of the first two lists.
     Return the head of the merged linked list.
     
     Intuition: We can deduce our logic from merge sort algorithm, but it is already sorted so we can just merge the two lists.
     Idea is to check the current node of both list and pick the smallest one and add it to the merged list. Then finally check if
     any of the list is not null then add it to the merged list.
     
     Time Complexity is O(n + m) where n is the length of list1 and m is the length of list2.
     Space Complexity is O(1) as we are not using any extra space.
     
	"""
	if not list1:
		return list2
	if not list2:
		return list1
	head = ListNode(0)
	current = head
	while list1 and list2:
		if list1.val <= list2.val:
			current.next = list1
			list1 = list1.next
		else:
			current.next = list2
			list2 = list2.next
		current = current.next
	
	current.next = list1 if list1 else list2
	
	return head.next
