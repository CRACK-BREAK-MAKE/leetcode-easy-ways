package com.crack.snap.make.easy.trees;
/**
 * Problem: Given the root of a binary tree, return the length of the diameter of the tree.
 * The diameter of a binary tree is the length of the longest path between any two nodes in a tree. This path may or may not pass through the root.
 * The length of a path between two nodes is represented by the number of edges between them.
 *
 *  @author Mohan Sharma
 */
public class DiameterOfBinaryTree {

    private int maxDiameter = 0;

    /**
     * Intuition: The max diameter will always be the sum of the maximum depth of the left subtree and the maximum depth of
     * the right subtree. So we can have a function to calculate the maximum depth of a node and then use it to calculate the diameter.
     */
    public int diameterOfBinaryTree(TreeNode root) {
        calculateDiameterRecursively(root);
        return maxDiameter;
    }

    private int calculateDiameterRecursively(TreeNode root) {
        if (root == null) {
            return 0;
        }
        var leftDepth = calculateDiameterRecursively(root.left);
        var rightDepth = calculateDiameterRecursively(root.right);
        maxDiameter = Math.max(maxDiameter, leftDepth + rightDepth);
        return 1 + Math.max(leftDepth, rightDepth); // returning the depth of the current node which is not the diameter
    }

    public static void main(String[] args) {
        var obj = new DiameterOfBinaryTree();
        var tree = new TreeNode(1,
                new TreeNode(2, new TreeNode(4), new TreeNode(5)),
                new TreeNode(3, new TreeNode(6), new TreeNode(7))
        );
        int diameter = obj.diameterOfBinaryTree(tree);
        System.out.println("Diameter of the binary tree: " + diameter); // Should print 4
    }
}
