package com.crack.snap.make.medium.linkedlist;

import com.crack.snap.make.easy.linkedlist.LinkedList;
import com.crack.snap.make.easy.linkedlist.ListNode;

/**
 * @author Mohan Sharma
 */
public class ReorderList {

    /**
     * Problem: You are given the head of a singly linked-list. The list can be represented as:
     * L0 → L1 → … → Ln - 1 → Ln
     * Reorder the list to be on the following form:
     * L0 → Ln → L1 → Ln - 1 → L2 → Ln - 2 → …
     * You may not modify the values in the list's nodes. Only nodes themselves may be changed.
     *
     * Intuition: We can solve this in a 3 step process.
     * 1. Find the middle of the linked list using slow and fast pointer.
     * 2. Reverse the second half of the linked list.
     * 3. Merge the two halves of the linked list.
     * @param head
     */
    public void reorderList(ListNode head) {
        if (head == null || head.next == null) {
            return;
        }
        // Step 1: Find the middle of the linked list
        var slow = head;
        var fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        var secondHalf = slow.next;
        slow.next = null; // Split the linked list into two halves

        // Step 2: Reverse the second half of the linked list
        var current = secondHalf;
        ListNode previous = null;
        while (current != null) {
            var next = current.next;
            current.next = previous;
            previous = current;
            current = next;
        }
        secondHalf = previous;

        // Step 3: Merge the two halves of the linked list
        while (head != null && secondHalf != null) {
            var firstNext = head.next;
            head.next = secondHalf;
            var secondNext = secondHalf.next;
            secondHalf.next = firstNext;
            head = firstNext;
            secondHalf = secondNext;
        }
    }

    public static void main(String[] args) {
        var list = new LinkedList();
        list.addLast(1);
        list.addLast(2);
        list.addLast(3);
        list.addLast(4);
        list.addLast(5);
        list.print(list.head);
        var reorderList = new ReorderList();
        reorderList.reorderList(list.head);
        list.print(list.head);
    }
}
