package com.crack.snap.make.medium.linkedlist;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Mohan Sharma
 */
public class LRUCache {
    private final int capacity;
    private final Map<Integer, ListNode> cache;
    private final ListNode head;
    private final ListNode tail;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        cache = new HashMap<>();
        head = new ListNode(0, 0);
        tail = new ListNode(0, 0);
        head.next = tail;
        tail.prev = head;
    }

    public int get(int key) {
        if (cache.containsKey(key)) {
            var node = cache.get(key);
            remove(key);
            insert(key, node.val);
            return node.val;
        }
        return -1;
    }

    public void put(int key, int value) {
         if (cache.containsKey(key)) {
            remove(key);
         } else if (cache.size() + 1 > capacity) {
            remove(tail.prev.key);
         }
         insert(key, value);
    }

    private void remove(int key) {
        var node = cache.remove(key);
        // remove the node from the linked list
        var previous = node.prev;
        var next = node.next;
        previous.next = next;
        if (next != null) {
            next.prev = previous;
        }
        node.next = null;
        node.prev = null;
    }

    private void insert(int key, int value) {
        var node = new ListNode(key, value);
        cache.put(key, node);
        //establish the connection with the head
        var headNext = head.next;
        head.next = node;
        node.prev = head;
        node.next = headNext;
        if (headNext != null) {
            headNext.prev = node;
        }
    }

    static class ListNode {
        private int val;
        private int key;
        private ListNode next;
        private ListNode prev;

        public ListNode(int key, int val) {
            this.key = key;
            this.val = val;
        }

        public int getVal() {
            return val;
        }

        public void setVal(int val) {
            this.val = val;
        }

        public ListNode getNext() {
            return next;
        }

        public void setNext(ListNode next) {
            this.next = next;
        }

        public ListNode getPrev() {
            return prev;
        }

        public void setPrev(ListNode prev) {
            this.prev = prev;
        }
    }

}
