package com.crack.snap.make.medium.trees;

import com.crack.snap.make.easy.trees.TreeNode;

/**
 * Problem: Given a binary tree root, a node X in the tree is named good if in the path from root to X there are no nodes
 * with a value greater than X.
 * Return the number of good nodes in the binary tree.
 *
 * @author Mohan Sharma
 */
public class CountGoodNodeBinaryTree {

    /**
     * Intuition: At each node, we need the greatest value encountered so far in the path from root to that node. Let's assume
     * the greatest value at root is MIN_VALUE. Since root's value > current_greatest (MIN_VALUE), root is a good node.
     * Then we go to left and right and update the current_greatest value as MAX(current_greatest, root.val).
     * We sum the good nodes from left and right subtrees and add 1 for the current node if it is a good node and return the sum.
     *
     * Time Complexity: O(N), where N is the number of nodes in the binary tree.
     * Space Complexity: O(H) (due to the recursion call stack, where H is the height of the tree). In the worst case (skewed tree), H can be N. In a balanced tree, H is O(logN).
     */
    public int goodNodes(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return countGoodNodes(root, Integer.MIN_VALUE);
    }

    private int countGoodNodes(TreeNode root, int highestSoFar) {
        if (root == null) {
            return 0;
        }
        var goodNodeCount = root.val >= highestSoFar ? 1 : 0;
        highestSoFar = Math.max(highestSoFar, root.val);
        goodNodeCount += countGoodNodes(root.left, highestSoFar);
        goodNodeCount += countGoodNodes(root.right, highestSoFar);
        return goodNodeCount;
    }

    public static void main(String[] args) {
        var obj = new CountGoodNodeBinaryTree();
        var root = new TreeNode(3,
                new TreeNode(1, new TreeNode(3), null),
                new TreeNode(4, new TreeNode(1), new TreeNode(5)));
        System.out.println(obj.goodNodes(root)); // Output: 4
    }
}
