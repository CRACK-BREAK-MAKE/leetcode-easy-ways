package com.crack.snap.make.medium.trees;

import com.crack.snap.make.easy.trees.TreeNode;
import java.util.Stack;

/**
 * @author Mohan Sharma
 */
public class KthSmallestElementInBST {

    private int result= -1;
    private int visitCount = 0;

    public int kthSmallestRecursive(TreeNode root, int k) {
        if (root == null || k <= 0) {
            return result; // Invalid input
        }
        traverseInOrder(root, k);
        return result;
    }

    public int kthSmallestIterative(TreeNode root, int k) {
        if (root == null || k <= 0) {
            return -1; // Invalid input
        }
        var stack = new Stack<TreeNode>();
        var current = root;
        var visitCount = 0;
        while (current != null || !stack.isEmpty()) {
            // Traverse to the leftmost node
            while (current != null) {
                stack.push(current);
                current = current.left;
            }
             current = stack.pop();
            visitCount++;
            if (visitCount == k) {
                return current.val; // Found the k-th smallest element
            }
            // Move to the right subtree
            current = current.right;
        }
        return -1; // If k is larger than the number of nodes in the tree
    }

    private void traverseInOrder(TreeNode root, int k) {
        if (root == null) {
            return;
        }
        // Traverse the left subtree
        traverseInOrder(root.left, k);
        // Increment the count of visited nodes
        visitCount++;
        if (visitCount == k) {
            result = root.val; // Found the k-th smallest element
            return; // No need to continue traversing
        }
        if (visitCount < k) {
            // Traverse the right subtree only if we haven't found the k-th smallest yet, early exit
            traverseInOrder(root.right, k);
        }
    }

    public static void main(String[] args) {
        var obj = new KthSmallestElementInBST();
        var root = new TreeNode(3,
                new TreeNode(1, null, new TreeNode(2)),
                new TreeNode(4));
        TreeNode.printTree(root);
        System.out.println("Kth Smallest Element in BST:" + obj.kthSmallestIterative(root, 1));
    }
}
