package com.crack.snap.make.easy.linkedlist;

import java.util.HashSet;

/**
 * @author Mohan Sharma
 */
public class LinkedListCycle {

    /**
     * Problem: Given head, the head of a linked list, determine if the linked list has a cycle in it.
     * There is a cycle in a linked list if there is some node in the list that can be reached again by continuously following the next pointer.
     * Return true if there is a cycle in the linked list. Otherwise, return false.
     *
     * Intuition: Let start with a node(current) and check if all previous to this node from head is not equals to next of current
     * Meaning we will use two loops, one to iterate the list and another which always starts from head and runs till the current node of outer loop.
     * Now for example 1->2->3->4->2 here there is a loop from 4 to 2. Now when outer loop is at 4 we will have current = 4, since
     * the inner loops starts from 1 always when it reaches 2 we can check that this previous node which is 2 is equal to
     * current.next which is also 2 So we can return true. Now till when do we run the inner loop this is where some counter will help
     * As we move the outer loop we can keep a counter and the inner loop should always run till the counter.
     *
     * Time Complexity is O(n^2) where n is the length of the linked list.
     * Space Complexity is O(1) as we are not using any extra space.
     */
    public boolean hasCycleBruteForce(ListNode head) {
        if (head == null) {
            return false;
        }
        var current = head;
        var currentPosition = 0;
        while (current != null) {
            var runnerPosition = 0;
            var runner = head;
            while (runnerPosition < currentPosition) {
                if (runner == current.next) {
                    return true;
                }
                runner = runner.next;
                runnerPosition++;
            }
            current = current.next;
            currentPosition++;
        }
        return false;
    }

    /**
     * We can also use hashing to check if the linked list has a cycle. We know set contains only unique elements, let iterate the
     * linked list, and before we add the node to the set let's check if the node is already present in the set. If it is present
     * then it is a cycle
     *
     * Time Complexity is O(n) where n is the length of the linked list.
     * Space Complexity is O(n) as we are using a set to store the nodes.
     */
    public boolean hasCycleHashing(ListNode head) {
        if (head == null) {
            return false;
        }
        var set = new HashSet<ListNode>();
        var current = head;
        while (current != null){
            if (set.contains(current)) {
                return true;
            }
            set.add(current);
            current = current.next;
        }
        return false;
    }

    /**
     * According to Floyd's Tortoise and Hare algorithm, we can use two pointers to check if the linked list has a cycle.
     * The idea is to use two pointers, one slow and one fast. The slow pointer moves one step at a time, while the fast pointer
     * moves two steps at a time. If there is a cycle, the fast pointer will eventually catch up to the slow pointer(fast pointer == slow pointer).
     * If there is no cycle, the fast pointer will reach the end of the list.
     *
     * Time Complexity is O(n) where n is the length of the linked list.
     * Space Complexity is O(1) as we are not using any extra space.
     */
    public boolean hasCycleFloydWarshall(ListNode head) {
        if (head == null) {
            return false;
        }
        var slow = head;
        var fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                return true;
            }
        }
        return false;
    }
}
