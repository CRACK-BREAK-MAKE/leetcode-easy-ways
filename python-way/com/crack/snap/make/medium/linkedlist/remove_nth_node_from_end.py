# @author Mohan Sharma
from typing import Optional

from com.crack.snap.make.easy.linkedlist.list_node import ListNode


def removeNthFromEndTwoScan(head: Optional[ListNode], n: int) -> Optional[ListNode]:
	"""
	Problem: Given the head of a linked list, remove the nth node from the end of the list and return its head.
    Example:
    Input: head = [1,2,3,4,5], n = 2
    Output: [1,2,3,5]
    
    Intuition: We can solve this in two scan first I will find the length of the linkedlist, and then I will iterate till
    (length - n) and remove the nth node
    
    Time Complexity is O(n) where n is the length of the linked list.
    Space Complexity is O(1) as we are not using any extra space.
    
	"""
	if not head:
		return None
	
	size = 0
	current = head
	while current:
		size += 1
		current = current.next
	
	if size == n:
		return head.next
	
	current = head
	for i in range(1, size - n):
		current = current.next
	
	if current and current.next:
		current.next = current.next.next
	return head


def removeNthFromEndOneScan(head: Optional[ListNode], n: int) -> Optional[ListNode]:
	"""
    We can also solve this using an intuition of two pointers starting from head initially. Let the first pointer go
    n nodes ahead of the second pointer. Then let's move both the pointers one by one till the first pointer reaches the end.
    Now the second pointer will be at the (size - n)th node. Now we can remove the next node of the second pointer
    
    Imagine you and a friend are reading the same book:
    The setup:
    You both start at page 1
    You read ahead N pages first
    Once you're N pages ahead, your friend starts reading too, You both read at the same speed, one page at a time
    What happens:
    When you reach the last page of the book, your friend will be exactly N pages behind
    This means your friend is on the page right before the one we want to remove
    Your friend can now skip the next page and connect directly to the page after it
    
    Time Complexity is O(n) where n is the length of the linked list.
    Space Complexity is O(1) as we are not using any extra space.
    
	"""
	if not head:
		return None
	first_pointer, second_pointer = head, head
	while n > 0:
		n -= 1
		first_pointer = first_pointer.next
	
	if not first_pointer:
		return head.next
	while first_pointer.next:
		first_pointer = first_pointer.next
		second_pointer = second_pointer.next
	if second_pointer and second_pointer.next:
		second_pointer.next = second_pointer.next.next
	return head
