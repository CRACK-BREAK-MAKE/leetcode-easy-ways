package com.crack.snap.make.easy.trees;

/**
 * Problem: Given a binary tree, determine if it is height-balanced. A balanced binary tree is defined as a binary tree
 * in which the depth of the two subtrees of every node never differs by more than one.
 * @author Mohan Sharma
 */
public class BalancedBinaryTree {

    /**
     * Intuition: We need to check if the depth of the left and right subtrees of every node differ by more than one.
     * Let's use a recursive approach to calculate the depth of each subtree and check the balance condition.
     */
    public boolean isBalanced(TreeNode root) {
        if (root == null) {
            return true; // An empty tree is balanced
        }
        return height(root) != -1;
    }

    private int height(TreeNode root) {
        if (root == null) {
            return 0; // The height of an empty tree is 0
        }
        var leftHeight = height(root.left);
        if (leftHeight == -1) {
            return -1; // Left subtree is not balanced
        }
        var rightHeight = height(root.right);
        if (rightHeight == -1) {
            return -1; // Right subtree is not balanced
        }
        if (Math.abs(leftHeight - rightHeight) > 1) {
            return -1; // Current node is not balanced
        }
        return 1 + Math.max(leftHeight, rightHeight); // Return the height of the current node
    }


}
