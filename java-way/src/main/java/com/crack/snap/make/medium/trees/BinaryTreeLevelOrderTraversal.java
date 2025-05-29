package com.crack.snap.make.medium.trees;

import com.crack.snap.make.easy.trees.TreeNode;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

/**
 * Problem: Given the root of a binary tree, return the level order traversal of its nodes' values. (i.e., from left to right, level by level).
 * @author Mohan Sharma
 */
public class BinaryTreeLevelOrderTraversal {

    /**
     * Intuition: We can use a queue to perform a breadth-first search (BFS) on the tree, visiting each level of the tree from left to right.
     * Once we add all the nodes at the current level to the queue, on the next iteration, we will take the size of the queue to determine
     * how many nodes are at the current level.
     * @param root
     * @return
     */
    public List<List<Integer>> levelOrder(TreeNode root) {
        if (root == null) {
            return List.of();
        }
        var result = new ArrayList<List<Integer>>();
        var queue = new ArrayDeque<TreeNode>();
        queue.add(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            var levelList = new ArrayList<Integer>();
            while (size-- > 0) {
                var node = queue.poll();
                levelList.add(node.val);
                var left = node.left;
                if (left != null) {
                    queue.add(left);
                }
                var right = node.right;
                if (right != null) {
                    queue.add(right);
                }
            }
            result.add(levelList);
        }
        return result;
    }
}
