# @author Mohan Sharma
from typing import Optional

from com.crack.snap.make.easy.linkedlist.list_node import ListNode


def addTwoNumbers(self, l1: Optional[ListNode], l2: Optional[ListNode]) -> Optional[ListNode]:
	if not l1:
		return l2
	if not l2:
		return l1
	dummy_head = ListNode(0)
	carry, current = 0, dummy_head
	
	while l1 or l2 or carry:
		sum = carry
		if l1:
			sum += l1.val
			l1 = l1.next
		if l2:
			sum += l2.val
			l2 = l2.next
		carry, val = divmod(sum, 10)
		current.next = ListNode(val)
		current = current.next
	
	return dummy_head.next
