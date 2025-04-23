# @author Mohan Sharma
from typing import Optional

from com.crack.snap.make.easy.linkedlist.list_node import ListNode


def reorderList(self, head: Optional[ListNode]) -> None:
	"""
    Problem: You are given the head of a singly linked-list. The list can be represented as:
    L0 → L1 → … → Ln - 1 → Ln
    Reorder the list to be on the following form:
    L0 → Ln → L1 → Ln - 1 → L2 → Ln - 2 → …
    You may not modify the values in the list's nodes. Only nodes themselves may be changed.
    
    Intuition: We can solve this in a 3-steps process.
    1. Find the middle of the linked list using slow and fast pointer.
    2. Reverse the second half of the linked list.
    3. Merge the two halves of the linked list.
	"""
	if not head or not head.next:
		return
	# Step 1: Find the middle of the linked list
	slow, fast = head, head
	while fast and fast.next:
		slow = slow.next
		fast = fast.next.next
	second_half = slow.next
	slow.next = None  # Split the list into two halves
	
	# Step 2: Reverse the second half of the linked list
	current, previous = second_half, None
	while current:
		next_node = current.next
		current.next = previous
		previous = current
		current = next_node
	second_half = previous
	
	# Step 3: Merge the two halves
	while head and second_half:
		first_next = head.next
		head.next = second_half
		second_next = second_half.next
		second_half.next = first_next
		head = first_next
		second_half = second_next
	
