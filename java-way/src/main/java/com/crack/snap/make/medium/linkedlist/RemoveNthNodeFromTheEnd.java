package com.crack.snap.make.medium.linkedlist;

import com.crack.snap.make.easy.linkedlist.ListNode;

/**
 * @author Mohan Sharma
 */
public class RemoveNthNodeFromTheEnd {

    /**
     * Problem: Given the head of a linked list, remove the nth node from the end of the list and return its head.
     * Example:
     * Input: head = [1,2,3,4,5], n = 2
     * Output: [1,2,3,5]
     *
     * Intuition: We can solve this in two scan first I will find the length of the linkedlist, and then I will iterate till
     * (length - n) and remove the nth node
     *
     * Time Complexity is O(n) where n is the length of the linked list.
     * Space Complexity is O(1) as we are not using any extra space.
     */
    public ListNode removeNthFromEndTwoScan(ListNode head, int n) {
        if (head == null) {
            return null;
        }
        var size = 0;
        var current = head;
        while (current != null) {
            size++;
            current = current.next;
        }
        if (size == n) {
            return head.next;
        }
        var iterateTill = size - n;
        current = head;
        for (int i = 1; i < iterateTill; i++) {
            current = current.next;
        }
        if (current != null && current.next != null) {
            current.next = current.next.next;
        }
        return head;
    }

    /**
     * We can also solve this using an intuition of two pointers starting from head initially. Let the first pointer go
     * n nodes ahead of the second pointer. Then let's move both the pointers one by one till the first pointer reaches the end.
     * Now the second pointer will be at the (size - n)th node. Now we can remove the next node of the second pointer
     *
     * Imagine you and a friend are reading the same book:
     * The setup:
     * You both start at page 1
     * You read ahead N pages first
     * Once you're N pages ahead, your friend starts reading too, You both read at the same speed, one page at a time
     * What happens:
     * When you reach the last page of the book, your friend will be exactly N pages behind
     * This means your friend is on the page right before the one we want to remove
     * Your friend can now skip the next page and connect directly to the page after it
     *
     * Time Complexity is O(n) where n is the length of the linked list.
     * Space Complexity is O(1) as we are not using any extra space.
     */
    public ListNode removeNthFromEndOneScan(ListNode head, int n) {
        if (head == null) {
            return null;
        }
        var firstPointer = head;
        var secondPointer = head;

        while (n > 0) {
            n--;
            firstPointer = firstPointer.next;
        }
        if (firstPointer == null) {
            return head.next;
        }
        while (firstPointer.next != null) {
            firstPointer = firstPointer.next;
            secondPointer = secondPointer.next;
        }
        if (secondPointer.next != null) {
            secondPointer.next = secondPointer.next.next;
        }
        return head;
    }
}
