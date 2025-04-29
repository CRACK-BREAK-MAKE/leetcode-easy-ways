package com.crack.snap.make.hard.linkedlist;

import com.crack.snap.make.easy.linkedlist.LinkedList;
import com.crack.snap.make.easy.linkedlist.ListNode;
import java.util.PriorityQueue;

/**
 * @author Mohan Sharma
 */
public class MergeKSortedList {

    /**
     * Problem: You are given an array of k linked-lists lists, each linked-list is sorted in ascending order.
     * Merge all the linked-lists into one sorted linked-list and return it.
     * Example:
     * Input: lists = [[1,4,5],[1,3,4],[2,6]]
     * Output: [1,1,2,3,4,4,5,6]
     *
     * Intuition: We can take the list at 0th and 1st index and merge and store the result at 1st index, then take 1st and 2nd
     * and merge and store the result at 2nd index and so on till we reach the end of the list. So the last index will have the
     * merged list.
     *
     * Time Complexity is O(n.k) where n is the length of the longest linked list and k is the number of linked lists.
     * mergeTwoLists has a time complexity of O(n+m) where n and m are the lengths of the two lists being merged.
     * First merge lists[0] and lists[1] → O(n₁ + n₂)
     * Then merge the result with lists[2] → O((n₁ + n₂) + n₃)
     * Then merge that result with lists[3] → O((n₁ + n₂ + n₃) + n₄)
     * And so on...
     * This leads to a quadratic-like pattern. The first list gets processed k-1 times, the second list k-2 times, and so on.
     * Space Complexity is O(1) as we are not using any extra space.
     */
    public ListNode mergeKListsBruteForce(ListNode[] lists) {
        if (lists == null || lists.length == 0) {
            return null;
        }
        if (lists.length == 1) {
            return lists[0];
        }
        for (var i = 0; i < lists.length - 1; i++) {
            lists[i+1] = mergeTwoLists(lists[i], lists[i + 1]);
        }
        return lists[lists.length - 1];
    }

    /**
     * In the brute force approach, if we analyse we will get to know that for the same list we are merging again and again
     * meaning first we merge n₁ + n₂, then we merge (n₁ + n₂) + n3, then we merge (n₁ + n₂ + n₃) + n₄ and so on.
     * So the same list is visited again and again, how do we solve this? Maybe we can merge in pairs meaning if there are 4 lists
     * [l1, l2, l3, l4] we merge (l1, l2) which gives left and we merge (l3, l4) which gives right and then we merge (left, right)
     * Meaning we can employee merge sort technique of divide and conquer.
     * @param lists
     * @return
     */
    public ListNode mergeKLists(ListNode[] lists) {
        if (lists == null || lists.length == 0) {
            return null;
        }
        if (lists.length == 1) {
            return lists[0];
        }
        var start = 0;
        var end = lists.length - 1;
        return divideAndMerge(lists, start, end);
    }

    private ListNode divideAndMerge(ListNode[] lists, int start, int end) {
        if (start > end) {
            return null;
        }
        if (start == end) {
            return lists[start];
        }
        var mid = start + (end - start) / 2;
        var left = divideAndMerge(lists, start, mid);
        var right = divideAndMerge(lists, mid + 1, end);
        return mergeTwoLists(left, right);
    }

    /**
     * We can also solve this using a priority queue. We can add all the nodes of the linked lists to the priority queue
     * and then poll the nodes from the priority queue and add them to the merged list.
     *
     * Time Complexity is O(N log N) where N is the total number of nodes across all lists.
     * First loop:
     * Adding all N nodes to the priority queue: O(N log N)
     * Each insertion takes O(log N) time in the worst case
     * Total cost: O(N log N)
     * Second loop:
     * Polling all N nodes from the priority queue: O(N log N)
     * Each removal takes O(log N) time
     * Total cost: O(N log N)
     * The overall time complexity is O(N log N) where N is the total number of nodes across all lists.
     * Space Complexity is O(N) for the priority queue to store all nodes.
     *
     */
    public ListNode mergeKListsSpace(ListNode[] lists) {
        if (lists == null || lists.length == 0) {
            return null;
        }
        if (lists.length == 1) {
            return lists[0];
        }
        var result = new ListNode(0);
        var pq = new PriorityQueue<ListNode>((a, b) -> a.val - b.val);
        for (var list : lists) {
            var current = list;
            while (current != null) {
                pq.add(current);
                current = current.next;
            }
        }
        var current = result;
        while (!pq.isEmpty()) {
            current.next = pq.poll();
            current = current.next;
        }
        current.next = null;
        return result.next;
    }

    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if (l1 == null) {
            return l2;
        }
        if (l2 == null) {
            return l1;
        }
        var result = new ListNode(Integer.MIN_VALUE);
        var current = result;
        while (l1 != null && l2 != null) {
            if (l1.val < l2.val) {
                current.next = l1;
                l1 = l1.next;
            } else {
                current.next = l2;
                l2 = l2.next;
            }
            current = current.next;
        }
        current.next = l1 == null ? l2 : l1;
        return result.next;
    }

    public static void main(String[] args) {
        var obj = new MergeKSortedList();
        var list1 = new LinkedList();
        list1.addLast(1);
        list1.addLast(4);
        list1.addLast(5);

        var list2 = new LinkedList();
        list2.addLast(1);
        list2.addLast(3);
        list2.addLast(4);

        var list3 = new LinkedList();
        list3.addLast(2);
        list3.addLast(6);

        var result = obj.mergeKLists(new ListNode[]{list1.head, list2.head, list3.head});
        while (result != null) {
            System.out.print(result.val + " ");
            result = result.next;
        }
    }
}
