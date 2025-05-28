package com.crack.snap.make.easy.trees;

import java.util.ArrayDeque;

/**
 * @author Mohan Sharma
 */
public class MaximumDepthBinaryTree {

    public int maxDepthDFS(TreeNode root) {
        if (root == null) {
            return 0;
        }
        var leftDepth = maxDepthDFS(root.left);
        var rightDepth = maxDepthDFS(root.right);
        return Math.max(leftDepth, rightDepth) + 1;
    }

    public int maxDepthBFS(TreeNode root) {
        if (root == null) {
            return 0;
        }
        var level = 0;
        var queue = new ArrayDeque<TreeNode>();
        queue.add(root);
        while (!queue.isEmpty()) {
            var size = queue.size();
            for (int i = 0; i < size; i++) {
                var node = queue.poll();
                var leftNode = node.left;
                if (leftNode != null) {
                    queue.add(leftNode);
                }
                var nodeRight = node.right;
                if (nodeRight != null) {
                    queue.add(nodeRight);
                }
            }
            level++;
        }
        return level;
    }
    public static void main(String[] args) {
        var obj = new MaximumDepthBinaryTree();
        var tree = new TreeNode(3, new TreeNode(9), new TreeNode(20, new TreeNode(15), new TreeNode(7)));
        System.out.println("Max Depth (DFS): " + obj.maxDepthDFS(tree)); // Should print 3
        System.out.println("Max Depth (BFS): " + obj.maxDepthBFS(tree)); // Should print 3
    }
}
