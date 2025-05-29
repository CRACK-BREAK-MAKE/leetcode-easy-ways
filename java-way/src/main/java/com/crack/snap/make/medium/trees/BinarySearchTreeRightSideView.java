package com.crack.snap.make.medium.trees;

import com.crack.snap.make.easy.trees.TreeNode;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

/**
 * Problem: Given the root of a binary tree, imagine yourself standing on the right side of it, return the values of the nodes
 * you can see ordered from top to bottom.
 *
 * @author Mohan Sharma
 */
public class BinarySearchTreeRightSideView {

    /**
     * Intuition: Let's perform a level order traversal of the tree using a queue. At each level iteration, first we will get
     * the size of the queue which will tell us how many nodes are at the current level. Then we will iterate through until the
     * size of the queue is 0, while iterating when the size is 1, we will add the node value to the result list.
     */
    public List<Integer> rightSideViewBFS(TreeNode root) {
        if (root == null) {
            return List.of();
        }
        var result = new ArrayList<Integer>();
        var queue = new ArrayDeque<TreeNode>();
        queue.add(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                var node = queue.poll();
                if (size == 0) { // This is the rightmost node at this level
                    result.add(node.val);
                }
                var left = node.left;
                if (left != null) {
                    queue.add(left);
                }
                var right = node.right;
                if (right != null) {
                    queue.add(right);
                }
            }
        }
        return result;
    }

    /**
     * Intuition: Let's perform a depth-first search (DFS) traversal of the tree. We will traverse the right subtree first since we
     * need the rightmost nodes. If we analyse we will see that at each level, the first node we encounter will be the rightmost
     * node at that level. We will maintain a result list. How do we figure out the first node at each level?
     * We can use a level variable and the result list size to determine if we are at a new level.
     * If the level is equal to the result size, it means we are at a new level, and we can add the node value to the result list.
     * Example at root, the result list size is 0 and the level is 0, so let's add the root value to the result list. Then we move
     * right and increment the level to 1. Now the result size is 1 and the level is also 1, so we add the right child value to the result list.
     * Now when we move to the left child of the root, the level is still 1, but the result size is 2, that means it is not the first node
     * of the level, and we do not add it to the result list.
     */
    public List<Integer> rightSideViewDFS(TreeNode root) {
        if (root == null) {
            return List.of();
        }
        var result = new ArrayList<Integer>();
        rightSideViewDFSHelper(root, result, 0);
        return result;
    }

    private void rightSideViewDFSHelper(TreeNode root, List<Integer> result, int level) {
        if (root == null) {
            return;
        }
        if (level == result.size()) {
            result.add(root.val); // This is the first node at this level, hence the rightmost node as we will traverse right first
        }
        rightSideViewDFSHelper(root.right, result, level + 1); // Traverse right first
        rightSideViewDFSHelper(root.left, result, level + 1); // Then traverse left
    }

    public static void main(String[] args) {
        var obj = new BinarySearchTreeRightSideView();
        var root = new TreeNode(1,
                new TreeNode(2, null, new TreeNode(5)),
                new TreeNode(3, null, new TreeNode(4)));
        System.out.println(obj.rightSideViewBFS(root)); // Output: [1, 3, 4]
        System.out.println(obj.rightSideViewDFS(root)); // Output: [1, 3, 4]
    }
}
