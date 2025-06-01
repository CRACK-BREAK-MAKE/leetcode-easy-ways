package com.crack.snap.make.hard.trees;

import com.crack.snap.make.easy.trees.TreeNode;

/**
 * @author Mohan Sharma
 */
public class SerializeDeserializeBinaryTree {

    private int index = 0;

    public String serialize(TreeNode root) {
        if (root == null) {
            return "NULL";
        }
        return root.val + "," + serialize(root.left) + "," + serialize(root.right);
    }

    public TreeNode deserialize(String data) {
        if (data == null || data.isBlank() || data.equals("NULL")) {
            return null;
        }
        String[] preorder = data.split(",");
        return buildTreeRecursively(preorder);
    }

    private TreeNode buildTreeRecursively(String[] preorder) {
        if (index >= preorder.length) {
            return null;
        }
        var rootVal = preorder[index++];
        if (rootVal.equals("NULL")) {
            return null;
        }
        TreeNode root = new TreeNode(Integer.parseInt(rootVal));
        root.left = buildTreeRecursively(preorder);
        root.right = buildTreeRecursively(preorder);
        return root;
    }
}
