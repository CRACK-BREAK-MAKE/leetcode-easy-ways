package com.crack.snap.make.hard.linkedlist;

import com.crack.snap.make.easy.linkedlist.LinkedList;
import com.crack.snap.make.easy.linkedlist.ListNode;
import java.util.ArrayList;

/**
 * @author Mohan Sharma
 */
public class ReverseNodesKGroup {

    /**
     * Problem: Given the head of a linked list, reverse the nodes of the list k at a time, and return the modified list.
     * k is a positive integer and is less than or equal to the length of the linked list. If the number of nodes is not a
     * multiple of k then left-out nodes, in the end, should remain as it is.
     * You may not alter the values in the list's nodes, only nodes themselves may be changed.
     *
     * Example:
     * Input: 1 -> 2 -> 3 -> 4 -> 5, k = 3
     * Output: 3 -> 2 -> 1 -> 4 -> 5
     *
     * Intuition: Brute force, let's iterate the list and break it into chunks of k nodes and store in an array, reverse from the list
     * and then join the list again.
     * Time Complexity is O(n) where n is the length of the linked list.
     * Space Complexity is O(n) as we are using an array to store the nodes.
     */
    public ListNode reverseKGroupBruteForce(ListNode head, int k) {
        if (head == null || k <= 1) {
            return head;
        }
        var lists = new ArrayList<ListNode>();
        var current = head;
        var chunkHead = head;
        var count = 0;
        while (current != null) {
            count++;
            if (count == k) {
                lists.add(chunkHead);
                var next = current.next;
                current.next = null;
                count = 0;
                chunkHead = next;
                current = next;
            } else {
                current = current.next;
            }
        }
        if (chunkHead != null) {
            lists.add(chunkHead);
        }

        for (int i = 0; i < lists.size(); i++) {
            lists.set(i, reverseList(lists.get(i), k));
        }

        var dummy = new ListNode(0);
        current = dummy;
        for (var list : lists) {
            current.next = list;
            while (current.next != null) {
                current = current.next;
            }
        }
        return dummy.next;
    }

    public ListNode reverseKGroup(ListNode head, int k) {
        if (head == null) {
            return head;
        }
        var size = 0;
        var current = head;
        while (current != null) {
            current = current.next;
            size++;
        }
        if (k > size) {
            return head;
        }
        return reverseK(head, k, size);
    }

    private ListNode reverseK(ListNode head, int k, int size) {
        if (head == null) {
            return null;
        }
        if(k > size) {
            return head;
        }
        ListNode current = head, previous = null;
        var count = 0;
        while (current != null && count++ < k) {
            var next = current.next;
            current.next = previous;
            previous = current;
            current = next;
        }
        head.next = reverseK(current, k, size - k);
        return previous;
    }

    public ListNode reverseList(ListNode head, int k) {
        int size = 0;
        var current = head;
        while (current != null) {
            current = current.next;
            size++;
        }
        if (size < k) {
            return head;
        }
        ListNode previous = null;
        current = head;
        while (current != null) {
            var next = current.next;
            current.next = previous;
            previous = current;
            current = next;
        }
        return previous;
    }

    public static void main(String[] args) {
        var obj = new ReverseNodesKGroup();
        var list = new LinkedList();
        list.addLast(1);
        list.addLast(2);
        list.addLast(3);
        list.addLast(4);
        list.addLast(5);
        var result = obj.reverseKGroup(list.head, 3);
        list.print(result);
    }
}
