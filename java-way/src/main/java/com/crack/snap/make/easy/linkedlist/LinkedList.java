package com.crack.snap.make.easy.linkedlist;

/**
 * @author Mohan Sharma
 */
public class LinkedList {

    public ListNode head;
    public ListNode tail;
    private int size;

    public void addLast(int data) {
        var node = new ListNode(data);
        if (head == null) {
            head = node;
            tail = node;
        } else {
            tail.next = node;
            tail = node;
        }
        size++;
    }

    public void addFirst(int data) {
        var node = new ListNode(data);
        if (head == null) {
            head = node;
            tail = node;
        } else {
            node.next = head;
            head = node;
        }
        size++;
    }

    public ListNode removeFirst() {
        if (head == null) {
            return null;
        }
        var tempHead = head.next;
        var temp = head;
        head.next = null;
        head = tempHead;
        if (head == null) {
            tail = null;
        }
        size--;
        return temp;
    }

    public ListNode removeLast() {
        if (head == null) {
            return null;
        }
        if (head == tail) {
            var temp = tail;
            head = null;
            tail = null;
            size--;
            return temp;
        }
        var current = head;
        while (current.next != tail) {
            current = current.next;
        }
        var temp = tail;
        current.next = null;
        tail = current;
        size--;
        return temp;

    }

    public int size() {
        return size;
    }

    public void print(ListNode head) {
        var current = head;
        while (current != null) {
            System.out.print(current.val);
            if (current.next != null) {
                System.out.print(" -> ");
            }
            current = current.next;
        }
        System.out.println();
    }
}
