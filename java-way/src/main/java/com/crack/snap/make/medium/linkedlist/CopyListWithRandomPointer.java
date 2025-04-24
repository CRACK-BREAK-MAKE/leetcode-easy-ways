package com.crack.snap.make.medium.linkedlist;

import java.util.HashMap;

/**
 * @author Mohan Sharma
 */
public class CopyListWithRandomPointer {

    class Node {
        int val;
        Node next;
        Node random;

        public Node(int val) {
            this.val = val;
            this.next = null;
            this.random = null;
        }
    }

    /**
     * Problem: A linked list of length n is given such that each node contains an additional random pointer, which could point
     * to any node in the list, or null. Construct a deep copy of the list. The deep copy should consist of exactly n brand new nodes,
     * where each new node has its value set to the value of its corresponding original node. Both the next and random pointer of the
     * new nodes should point to new nodes in the copied list such that the pointers in the original list and copied list represent
     * the same list state. None of the pointers in the new list should point to nodes in the original list.
     *
     * For example, if there are two nodes X and Y in the original list, where X.random --> Y, then for the corresponding
     * two nodes x and y in the copied list, x.random --> y.
     * Return the head of the copied linked list.
     *
     * Intuition: Since we will have a new node for all the old nodes, first we can create all the nodes and store the map between
     * them, we can use hashing. First let's create all the new nodes for the old nodes and keep them as (oldNode, newNode) pair.
     * Then we can iterate over the old list again and get the old list's next and random from the map and point to this new node
     *
     * Time Complexity is O(n) where n is the length of the linked list.
     * Space Complexity is O(n) as we are using a map to store the nodes.
     *
     * @param head
     * @return
     */
    public Node copyRandomList(Node head) {
        if (head == null) {
            return null;
        }

        // First pass: Create all new nodes and map old nodes to new nodes
        var map = new HashMap<Node, Node>();
        var current = head;
        while (current != null) {
            map.put(current, new Node(current.val));
            current = current.next;
        }

        // Second pass: Connect next and random pointers
        current = head;
        while (current != null) {
            var node = map.get(current);
            node.next = map.get(current.next);
            node.random = map.get(current.random);
            current = current.next;
        }
        return map.get(head);
    }
}
