package com.crack.snap.make.easy.trees;

import java.util.ArrayDeque;

/**
 * @author Mohan Sharma
 */
public class InvertBinaryTree {

    public TreeNode invertTree(TreeNode root) {
        if (root == null || (root.left == null && root.right == null)) {
            return root;
        }
        return invertTreeRecursively(root);
    }

    private TreeNode invertTreeRecursively(TreeNode root) {
        if (root == null) {
            return null;
        }
        var left = root.left;
        root.left = invertTreeRecursively(root.right);
        root.right = invertTreeRecursively(left);
        return root;
    }

    private TreeNode invertTreeIteratively(TreeNode root) {
        if (root == null || (root.left == null && root.right == null)) {
            return root;
        }
        var queue = new ArrayDeque<TreeNode>();
        queue.add(root);
        while (!queue.isEmpty()) {
            var node = queue.poll();
            var left = node.left;
            var right = node.right;
            if (left != null) {
                queue.add(left);
            }
            if (node.right != null) {
                queue.add(right);
            }
            node.left = right;
            node.right = left;
        }
        return root;
    }

    public static void main(String[] args) {
        var obj = new InvertBinaryTree();
        var tree = new TreeNode(4, new TreeNode(2, new TreeNode(1), new TreeNode(3)), new TreeNode(7, new TreeNode(6), new TreeNode(9)));
        System.out.println("Original Tree:");
        TreeNode.printTree(tree); // Should print the original tree in pre-order traversal
        var invertedTree = obj.invertTreeIteratively(tree);
        System.out.println("\nInverted Tree:");
        TreeNode.printTree(invertedTree); // Should print the inverted tree in pre-order traversal
    }
}
