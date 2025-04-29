package com.crack.snap.make.medium.linkedlist;

import com.crack.snap.make.easy.linkedlist.ListNode;

/**
 * @author Mohan Sharma
 */
public class AddTwoNumbers {

    /**
     * Problem: You are given two non-empty linked lists representing two non-negative integers. The digits are stored in reverse order,
     * and each of their nodes contains a single digit. Add the two numbers and return the sum as a linked list.
     *
     * You may assume the two numbers do not contain any leading zero, except the number 0 itself.
     *
     * Intuition: Iterate over the two lists from their heads and add their digits and create a new node to store the sum,
     * if the sum is greater than 10 then store (sum % 10) in the new node and carry over (sum / 10) to the next node.
     *
     * @param l1
     * @param l2
     * @return
     */
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        if (l1 == null) {
            return l2;
        }
        if (l2 == null) {
            return l1;
        }
        var head = new ListNode(0);
        var current = head;
        var carry = 0;
        while (l1 != null || l2 != null) {
            var sum = carry;
            if (l1 != null) {
                sum += l1.val;
                l1 = l1.next;
            }
            if (l2 != null) {
                sum += l2.val;
                l2 = l2.next;
            }
            carry = sum / 10;
            current.next = new ListNode(sum % 10);
            current = current.next;
        }
        if (carry != 0) {
            current.next = new ListNode(carry);
        }
        return head.next;
    }
}
