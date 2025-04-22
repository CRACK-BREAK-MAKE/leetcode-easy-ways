package com.crack.snap.make.easy.linkedlist;

/**
 * @author Mohan Sharma
 */
public class ReverseLinkedList {

    public ListNode reverseListIterative(ListNode head) {
        if (head == null) {
            return null;
        }
        ListNode current = head, previous = null;
        while (current != null) {
            var next = current.next;
            current.next = previous;
            previous = current;
            current = next;
        }
        return previous;
    }

    public ListNode reverseListRecursive(ListNode head) {
        if (head == null) {
            return null;
        }
        return reverseListRecursiveHelper(head, null);
    }

    private ListNode reverseListRecursiveHelper(ListNode current, ListNode prev) {
        if (current == null) {
            return prev;
        }
        var next = current.next;
        current.next = prev;
        return reverseListRecursiveHelper(next, current);
    }

    public static void main(String[] args) {
        var list = new LinkedList();
        list.addLast(1);
        list.addLast(2);
        list.addLast(3);
        list.addLast(4);
        list.addLast(5);
        list.print(list.head);
        var reverseLinkedList = new ReverseLinkedList();
        var head = reverseLinkedList.reverseListRecursive(list.head);
        list.print(head);
    }
}
