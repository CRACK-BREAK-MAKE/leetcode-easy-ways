package com.crack.snap.make.medium.trees;

import com.crack.snap.make.easy.trees.TreeNode;

/**
 * Problem: Given the root of a binary tree, determine if it is a valid binary search tree (BST).
 * A valid BST is defined as follows:
 * - The left subtree of a node contains only nodes with keys less than the node's key.
 * - The right subtree of a node contains only nodes with keys greater than the node's key.
 * - Both the left and right subtrees must also be binary search trees.
 *
 * @author Mohan Sharma
 */
public class ValidateBinarySearchTree {

    /**
     * Intuition: A root node is a valid BST if:
     * 1. the value of the root node is greater than all values in its left subtree,
     * 2. the value of the root node is less than all values in its right subtree,
     * 3. both left and right subtrees are valid BSTs.
     */
    public boolean isValidBSTBruteForce(TreeNode root) {
        if (root == null) {
            return true;
        }
        return allValuesLessThan(root.left, root.val) &&
                allValuesGreaterThan(root.right, root.val) &&
                isValidBSTBruteForce(root.left) &&
                isValidBSTBruteForce(root.right);
    }

    private boolean allValuesLessThan(TreeNode root, int val) {
        if (root == null) {
            return true;
        }
        return root.val <= val && allValuesLessThan(root.left, val) && allValuesLessThan(root.right, val);
    }

    private boolean allValuesGreaterThan(TreeNode root, int val) {
        if (root == null) {
            return true;
        }
        return root.val > val && allValuesGreaterThan(root.left, val) && allValuesGreaterThan(root.right, val);
    }

    public boolean isValidBST(TreeNode root) {
        if (root == null) {
            return true;
        }
        return isValidBSTHelper(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    private boolean isValidBSTHelper(TreeNode root, long minValue, long maxValue) {
        if (root == null) {
            return true;
        }
        return root.val > minValue && root.val < maxValue &&
                isValidBSTHelper(root.left, minValue, root.val) &&
                isValidBSTHelper(root.right, root.val, maxValue);
    }
}
