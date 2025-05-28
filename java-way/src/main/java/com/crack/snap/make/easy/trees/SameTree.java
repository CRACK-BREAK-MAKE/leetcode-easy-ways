package com.crack.snap.make.easy.trees;

/**
 * Problem: Given the roots of two binary trees p and q, write a function to check if they are the same or not.
 * Two binary trees are considered the same if they are structurally identical, and the nodes have the same value.
 *
 * @author Mohan Sharma
 */
public class SameTree {

    /**
     * Intuition: Two trees will be same if:
     * 1. If one of the root node is null, then the other root node must also be null (base case)
     * 2. Both trees are not null then we check:
     *  a. The value of the root nodes are same.
     *  b. The left subtrees are same.
     *  c. The right subtrees are same.
     *
     * We don't need to check the height of the trees. Suppose a tree has a different height, then it will have a null node
     * at some point, which will be caught by the first condition.
     */
    public boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null || q == null) {
            return p == q; // if both are null, return true, otherwise false
        }
        return p.val == q.val && isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    }
}
