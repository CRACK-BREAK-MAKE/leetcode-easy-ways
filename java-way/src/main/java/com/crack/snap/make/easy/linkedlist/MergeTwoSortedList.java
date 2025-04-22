package com.crack.snap.make.easy.linkedlist;

/**
 * @author Mohan Sharma
 */
public class MergeTwoSortedList {

    /**
     * Problem: You are given the heads of two sorted linked lists list1 and list2.
     * Merge the two lists into one sorted list. The list should be made by splicing together the nodes of the first two lists.
     * Return the head of the merged linked list.
     *
     * Intuition: We can deduce our logic from merge sort algorithm but it is already sorted so we can just merge the two lists.
     * Idea is check the current node of both list and pick the smallest one and add it to the merged list. Then finally check if
     * any of the list is not null then add it to the merged list.
     *
     * Time Complexity is O(n + m) where n is the length of list1 and m is the length of list2.
     * Space Complexity is O(1) as we are not using any extra space.
     *
     * @param list1
     * @param list2
     * @return
     */
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        if (list1 == null) {
            return list2;
        }
        if (list2 == null) {
            return list1;
        }

        //creating dummy node so don't need to check if head of result is null first time
        var head = new ListNode(0);

        //current will always point to tail for next insert whereas we need to return the head of merged list
        var current = head;
        while (list1 != null && list2 != null) {
            if (list1.val <= list2.val) {
                current.next = list1;
                list1 = list1.next;
            } else {
                current.next = list2;
                list2 = list2.next;
            }
            current = current.next;
        }
        current.next = list1 == null ? list2 : list1;

        //ignore the first 0 node
        return head.next;
    }
}
