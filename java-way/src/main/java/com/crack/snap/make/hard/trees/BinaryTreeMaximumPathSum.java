package com.crack.snap.make.hard.trees;

import com.crack.snap.make.easy.trees.TreeNode;

/**
 * @author Mohan Sharma
 */
public class BinaryTreeMaximumPathSum {

    private int maxSum = Integer.MIN_VALUE;

    public int maxPathSum(TreeNode root) {
        maxGain(root);
        return maxSum;
    }

    private int maxGain(TreeNode root) {
        if (root == null) {
            return 0;
        }
        var leftPathSum = maxGain(root.left);
        var rightPathSum = maxGain(root.right);
        var rootValue = root.val;
        // Why are we not considering leftPathSum and rightPathSum by themselves in the maxSum update at the root's level?
        // (i.e., Math.max(maxSum, ls, rs, rv, ls+rv, rs+rv, rv+ls+rs))
        //The reason is that ls and rs (as standalone path sums) would have already been considered for globalSum when the
        // recursion was at root.left and root.right respectively.
        var returnablePathSum = Math.max(rootValue, Math.max(leftPathSum + rootValue, rightPathSum + rootValue));
        maxSum = Math.max(maxSum, Math.max(leftPathSum + rightPathSum + rootValue, returnablePathSum));
        return returnablePathSum;
    }

    /**
     * Why does maxSum = Math.max(maxSum, root.val + leftGain + rightGain) sufficiently cover paths for maxSum update at the current node?
     *
     * The key is the definition of leftGain and rightGain as non-negative gains.
     * leftGain = Math.max(maxGain(root.left), 0);
     * rightGain = Math.max(maxGain(root.right), 0);
     * This means leftGain >= 0 and rightGain >= 0.
     *
     * Now, let's look at the currentPathSum = root.val + leftGain + rightGain; which updates maxSum.
     * This single expression elegantly covers all relevant path types that could have the current root as their "highest" or "turning" point:
     *
     * Path is just the root node itself:
     *
     * If both left and right subtrees offer no positive gain (i.e., maxGain(root.left) <= 0 and maxGain(root.right) <= 0), then leftGain will be 0 and rightGain will be 0.
     * In this case, currentPathSum = root.val + 0 + 0 = root.val.
     * So, maxSum is updated with Math.max(maxSum, root.val). This covers a single-node path.
     * Path is root plus its left branch (and left branch has positive gain):
     *
     * Suppose the best path involving root goes root -> left_child_path and the right child offers no positive gain.
     * Then leftGain > 0 and rightGain = 0.
     * currentPathSum = root.val + leftGain + 0 = root.val + leftGain.
     * maxSum is updated with Math.max(maxSum, root.val + leftGain). This covers the rv+ls type of path.
     * Path is root plus its right branch (and right branch has positive gain):
     *
     * Suppose the best path involving root goes root -> right_child_path and the left child offers no positive gain.
     * Then leftGain = 0 and rightGain > 0.
     * currentPathSum = root.val + 0 + rightGain = root.val + rightGain.
     * maxSum is updated with Math.max(maxSum, root.val + rightGain). This covers the rv+rs type of path.
     * Path "bends" at root, using positive gains from both left and right:
     *
     * If leftGain > 0 and rightGain > 0.
     * currentPathSum = root.val + leftGain + rightGain.
     * maxSum is updated with this value.
     * In summary, the term root.val + leftGain + rightGain for updating maxSum implicitly handles:
     *
     * root.val (when leftGain=0, rightGain=0)
     * root.val + leftGain (when rightGain=0)
     * root.val + rightGain (when leftGain=0)
     * root.val + leftGain + rightGain (when both are positive)
     */
    private int maxGainOptimal(TreeNode root) {
        if (root == null) {
            return 0;
        }
        var leftGain = Math.max(maxGainOptimal(root.left), 0);
        var rightGain = Math.max(maxGainOptimal(root.right), 0);
        var currentPathSum = root.val + leftGain + rightGain;
        maxSum = Math.max(maxSum, currentPathSum);
        return root.val + Math.max(leftGain, rightGain);
    }
}
