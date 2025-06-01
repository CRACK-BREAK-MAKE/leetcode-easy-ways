package com.crack.snap.make.medium.trees;

import com.crack.snap.make.easy.trees.TreeNode;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * @author Mohan Sharma
 */
public class ConstructBinaryTreeFromInorderPreorderArray {

    private int preOrderIndex = 0;
    private Map<Integer, Integer> inOrderMap = new HashMap<>();

    /**
     * Intuition:
     * 1. The first element of preorder is always the root of the current (sub)tree.
     * 2. All elements to the left of this root in inorder belong to the left subtree.
     * 3. All elements to the right of this root in inorder belong to the right subtree.
     *
     * Next we can construct the left and right subtrees recursively:
     * * - For the left subtree, we take the next element in preorder and find its index in inorder.
     * * - For the right subtree, we calculate the number of elements in the left subtree and use that to find the
     *     starting index for the right subtree in preorder.
     *
     * Time Complexity: O(n^2) in the worst case due to the linear search for the root index in the inorder array.
     * Space Complexity: O(H) for the recursion stack and the tree nodes. where H is the height of the tree (O(N)
     * in the worst case for a skewed tree, O(logN) for a balanced tree).
     */
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        if (preorder == null || inorder == null || preorder.length != inorder.length) {
            return null; // Invalid input
        }
        return buildTreeHelper(preorder, inorder, 0, 0, inorder.length - 1);
    }


    /**
     * Intuition for preOrderIndex Calculation for the Right Subtree:
     * Why the preOrderIndex for the right subtree is preOrderIndex + 1 + (rootIndex - inOrderStart). Let's break it down:
     *      preorder: [Root, (Elements of Left Subtree), (Elements of Right Subtree)]
     *      inorder: [(Elements of Left Subtree), Root, (Elements of Right Subtree)]
     *
     * preOrderIndex: This is the index of the current rootValue in the preorder array.
     *
     * preOrderIndex + 1: After processing the current rootValue (which is at preOrderIndex), the very next element in the preorder
     *   array (preorder[preOrderIndex + 1]) is the root of the left subtree.
     *
     * rootIndex - inOrderStart:
     *      rootIndex: Index of the current rootValue in the inorder array (within the current inOrderStart to inOrderEnd segment).
     *      inOrderStart: The starting index of the current segment in the inorder array that we are considering.
     *      Therefore, rootIndex - inOrderStart gives you the number of elements in the left subtree. Let's call this leftSubtreeSize.
     *      For example, if inorder is [4, 2, 5, 1, 6, 3, 7], rootValue is 1 (so rootIndex=3), and inOrderStart is 0, then
     *      leftSubtreeSize = 3 - 0 = 3 (nodes 4, 2, 5).
     *
     * Combining these:
     * 1. The elements of the left subtree in the preorder array start at preOrderIndex + 1.
     * 2. There are leftSubtreeSize such elements.
     * 3. So, these leftSubtreeSize elements occupy the preorder indices from preOrderIndex + 1 up to (preOrderIndex + 1) + leftSubtreeSize - 1.
     * 4. The root of the right subtree must therefore be the element immediately following this block of left subtree elements in the preorder array.
     * 5. Its index will be (preOrderIndex + 1) + leftSubtreeSize.
     *
     * Substituting leftSubtreeSize = (rootIndex - inOrderStart):
     *  - The starting preOrderIndex for the right subtree becomes:
     *        preOrderIndex + 1 + (rootIndex - inOrderStart)
     */
    private TreeNode buildTreeHelper(int[] preorder, int[] inorder, int preOrderIndex, int inOrderStart, int inOrderEnd) {
        if (preOrderIndex > preorder.length || inOrderStart > inOrderEnd) {
            return null; // Base case: no elements to construct the tree
        }
        var rootValue = preorder[preOrderIndex];
        // get index of root in inorder array
        var rootIndex = IntStream.range(inOrderStart, inOrderEnd + 1).filter(i -> inorder[i] == rootValue).findFirst().orElseThrow();
        var root = new TreeNode(rootValue);
        // Recursively build the left subtree
        root.left = buildTreeHelper(preorder, inorder, preOrderIndex + 1, inOrderStart, rootIndex - 1);
        // Recursively build the right subtree
        root.right = buildTreeHelper(preorder, inorder, preOrderIndex + 1 + (rootIndex - inOrderStart), rootIndex + 1, inOrderEnd);
        return root;
    }

    /**
     * The Core Idea: Preorder Traversal's Sequential Nature
     *
     * The preorder traversal visits nodes in this order:
     * - Root
     * - All nodes in the Left Subtree (recursively, in preorder)
     * - All nodes in the Right Subtree (recursively, in preorder)
     * This means that if you read the preorder array from left to right, you encounter the root of a tree, then
     * all the elements that will form its left subtree, and then all the elements that will form its right subtree, consecutively.
     *
     * How the Optimized Solution with a Single preorderCurrentIndex Works:
     * Think of the preorder array as a stream of nodes that you consume one by one in the exact order they appear.
     * The single preorderCurrentIndex (often a class member or an index passed by reference in some languages) acts like a pointer
     * to the next available node in this stream to be used as a root.
     *
     * Initial Call: preorderCurrentIndex starts at 0. The value preorder[0] is the root of the entire tree. We use it, and
     * then we increment preorderCurrentIndex to 1.
     *
     * Building the Left Subtree:
     * We make a recursive call to build the left subtree: root.left = buildTreeHelperOptimal(...).
     * This recursive call now looks at preorder[preorderCurrentIndex] (which is preorder[1]). This value is guaranteed to be the root
     * of the left subtree (if the left subtree exists).
     * This call will, in turn, use preorder[1], increment preorderCurrentIndex, and then recursively call for its left child, and so on.
     * Crucially, the recursive calls for the entire left subtree will consume exactly the right number of elements from the preorder
     * array, and each time a node is used as a root, preorderCurrentIndex is incremented.
     *
     * Building the Right Subtree:
     * When the entire sequence of calls for root.left finally returns, preorderCurrentIndex will have been incremented past all
     * the nodes that belong to the left subtree.
     * It will now naturally be pointing to the element in the preorder array that is the root of the right subtree.
     * The call root.right = buildTreeHelper(...) then picks up from this preorderCurrentIndex, uses that node as the right
     * child's root, increments preorderCurrentIndex, and continues recursively for the right subtree.
     * In essence, the single preorderCurrentIndex works because the recursive calls for the left subtree "consume" their portion
     * of the preorder array, automatically advancing the index to the correct starting point for the right subtree. The order of
     * operations (Root -> Left -> Right) in the buildTreeHelper directly mirrors the preorder sequence.
     */
    public TreeNode buildTreeOptimal(int[] preorder, int[] inorder) {
        if (preorder == null || inorder == null || preorder.length != inorder.length) {
            return null; // Invalid input
        }
        for (int i = 0; i < inorder.length; i++) {
            inOrderMap.put(inorder[i], i);
        }
        return buildTreeHelperOptimal(preorder, inorder, 0, inorder.length - 1);
    }

    private TreeNode buildTreeHelperOptimal(int[] preorder, int[] inorder, int inOrderStart, int inOrderEnd) {
        if (inOrderStart > inOrderEnd) {
            return null;
        }
        var rootValue = preorder[preOrderIndex++];
        var rootIndex = inOrderMap.get(rootValue);
        var root = new TreeNode(rootValue);
        root.left = buildTreeHelperOptimal(preorder, inorder, inOrderStart, rootIndex - 1);
        root.right = buildTreeHelperOptimal(preorder, inorder, rootIndex + 1, inOrderEnd);
        return root;
    }

    public static void main(String[] args) {
        var obj = new ConstructBinaryTreeFromInorderPreorderArray();
        var root = obj.buildTreeOptimal(new int[]{3, 9, 20, 15, 7}, new int[]{9, 3, 15, 20, 7});
        TreeNode.printTree(root);
        var obj2 = new ConstructBinaryTreeFromInorderPreorderArray();
        var root2 = obj2.buildTreeOptimal(new int[]{1, 2, 3, 4}, new int[]{2, 1, 3, 4});
        TreeNode.printTree(root2);
    }
}
