package com.crack.snap.make.easy.trees;

/**
 * Problem: Given the roots of two binary trees root and subRoot, return true if there is a subtree of root with the same structure
 * and node values of subRoot and false otherwise.
 * A subtree of a binary tree `tree` is a tree that consists of a node in `tree` and all of this node's descendants. The tree `tree`
 * could also be considered as a subtree of itself.
 */
public class SubtreeOfAnotherTree {

    /**
     * Intuition: We can recursively check if the current node of the main tree matches the root of the sub-tree, otherwise
     * we can check the left and right subtrees of the main tree.
     */
    public boolean isSubtree(TreeNode root, TreeNode subRoot) {
        if (root == null) {
            return subRoot == null; // If the main tree is null, it can't have a subtree
        }
        return isSameTree(root, subRoot) || isSubtree(root.left, subRoot) || isSubtree(root.right, subRoot);
    }

    private boolean isSameTree(TreeNode root, TreeNode subRoot) {
        if (root == null || subRoot == null) {
            return root == subRoot; // If one is null and the other is not, return false
        }
        return (root.val == subRoot.val)
                && isSameTree(root.left, subRoot.left)
                && isSameTree(root.right, subRoot.right);
    }
}
