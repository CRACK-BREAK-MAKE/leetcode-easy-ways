package com.crack.snap.make.medium.trees;

import com.crack.snap.make.easy.trees.TreeNode;

/**
 * Problem: Given a binary search tree (BST), find the lowest common ancestor (LCA) node of two given nodes in the BST.
 * According to the definition of LCA on Wikipedia: “The lowest common ancestor is defined between two nodes p and q as
 * the lowest node in T that has both p and q as descendants (where we allow a node to be a descendant of itself).”
 * @author Mohan Sharma
 */
public class LowestCommonAncestorBinarySearchTree {

    /**
     * Intuition: We need to scan all the nodes in the BST to find the lowest common ancestor of two nodes p and q.
     * As we know the property of a binary search tree, we can use it to our advantage.
     * 1. If both p and q are smaller than the root, then LCA lies in the left subtree.
     * 2. If both p and q are greater than the root, then LCA lies in the right subtree.
     * 3. If one of p or q is smaller than the root and the other is greater, then the root is the LCA. Why? Because is the first
     * node where the paths to p and q diverge. In other words, it is the lowest common link between the two nodes.
     */
    public TreeNode lowestCommonAncestorIterative(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || p == null || q == null) {
            return null; // Handle edge cases where root, p, or q is null
        }
        var current = root;
        while (current != null) {
            if (current.val > p.val && current.val > q.val) {
                current = current.left;
            } else if (current.val < p.val && current.val < q.val) {
                current = current.right;
            } else {
                return current;
            }
        }
        return null;
    }

    public TreeNode lowestCommonAncestorRecursive(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || p == null || q == null) {
            return null; // Handle edge cases where root, p, or q is null
        }
        if (root.val > p.val && root.val > q.val) {
            return lowestCommonAncestorRecursive(root.left, p, q);
        } else if (root.val < p.val && root.val < q.val) {
            return lowestCommonAncestorRecursive(root.right, p, q);
        } else {
            return root;
        }
    }

    public static void main(String[] args) {
        var obj = new LowestCommonAncestorBinarySearchTree();
        var root = new TreeNode(6, new TreeNode(2, new TreeNode(0), new TreeNode(4, new TreeNode(3), new TreeNode(5))), new TreeNode(8, new TreeNode(7), new TreeNode(9)));
        System.out.println(obj.lowestCommonAncestorIterative(root, new TreeNode(2), new TreeNode(4)).val); // Should return node with value 2
        System.out.println(obj.lowestCommonAncestorIterative(root, new TreeNode(0), new TreeNode(5)).val); // Should return node with value 2
        System.out.println(obj.lowestCommonAncestorIterative(root, new TreeNode(5), new TreeNode(9)).val); // Should return node with value 6

        System.out.println(obj.lowestCommonAncestorRecursive(root, new TreeNode(2), new TreeNode(4)).val); // Should return node with value 2
        System.out.println(obj.lowestCommonAncestorRecursive(root, new TreeNode(0), new TreeNode(5)).val); // Should return node with value 2
        System.out.println(obj.lowestCommonAncestorRecursive(root, new TreeNode(5), new TreeNode(9)).val); // Should return node with value 6
    }
}
