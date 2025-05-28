package com.crack.snap.make.easy.trees;

/**
 * @author Mohan Sharma
 *
 * Could have used a record class, but using a public fields will be easier as it matches the syntax of LeetCode's editor.
 */
public class TreeNode {
    public int val;
    public TreeNode left;
    public TreeNode right;

    public TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }

    public TreeNode(int val) { this.val = val; }

    // print in a tree structure
    public static void printTree(TreeNode root) {
        printTreeHelper(root, 0);
    }

    private static void printTreeHelper(TreeNode node, int level) {
        if (node == null) return;
        printTreeHelper(node.right, level + 1);
        System.out.println("    ".repeat(level) + node.val);
        printTreeHelper(node.left, level + 1);
    }
}
