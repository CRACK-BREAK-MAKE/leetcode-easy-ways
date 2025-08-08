package com.crack.snap.make.medium.dp;

/**
 * @author Mohan Sharma
 */
public class MaximumSubArray {

    /**
     * Recursive State: Let maxSumEndingAt(i) be the maximum sum of a contiguous subarray that must end at index i.
     * Now, for any given index i, how can we find this value? There are only two possibilities for a subarray that ends at i:
     * It starts fresh at `i`. In this case, the sum is just nums[i].
     * It is an extension of the best subarray that ended at the previous element, i-1. In this case, the sum is nums[i] + maxSumEndingAt(i-1).
     * We want the maximum of these two choices. So, our recurrence relation is:
     * maxSumEndingAt(i) = Math.max(nums[i], nums[i] + maxSumEndingAt(i-1))
     *
     * Time Complexity: O(n^2), The main function has a loop that runs n times. Inside the loop, the call to solve(i)
     * takes O(i) time because it makes a chain of i recursive calls. The total time is the sum of 1 + 2 + 3 + ... + n, which is O(n^2).
     * Space Complexity: O(n). This is for the maximum depth of the recursion stack.
     *
     */
    public int maxSubArrayBruteForce(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        var maxSubArraySum = Integer.MIN_VALUE;
        for (int i = 0; i < nums.length; i++) {
            maxSubArraySum = Math.max(maxSubArraySum, solveMaxSubArrayBruteForce(nums, i));
        }
        return maxSubArraySum;
    }

    private int solveMaxSubArrayBruteForce(int[] nums, int index) {
        if (index == 0) {
            return nums[index];
        }
        var num = nums[index];
        var maxSumFromPreviousIndex = solveMaxSubArrayBruteForce(nums, index - 1);
        // max will be either the previousSum + currentNum or currentNum
        // example [-1, 2] sum at index 1 will be Max(-1+2, 2)
        return Math.max(maxSumFromPreviousIndex + num, num);
    }

    /**
     * Time Complexity: O(n). The first time the helper is called with a large index (e.g., i = n-1), it will take O(n)
     * time to fill the memo array. All subsequent calls from the main loop will be O(1) cache hits. The total time is
     * dominated by the initial computation.
     * Space Complexity: O(n). For the memo array and the recursion stack.
     */
    public int maxSubArrayTopDown(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        var memo = new Integer[nums.length];
        var maxSubArraySum = Integer.MIN_VALUE;
        for (int i = 0; i < nums.length; i++) {
            maxSubArraySum = Math.max(maxSubArraySum, solveMaxSubArrayMemoization(nums, i, memo));
        }
        return maxSubArraySum;
    }

    private int solveMaxSubArrayMemoization(int[] nums, int index, Integer[] memo) {
        if (index == 0) {
            return nums[index];
        }
        if (memo[index] != null) {
            return memo[index];
        }
        var num = nums[index];
        var maxSumFromPreviousIndex = solveMaxSubArrayMemoization(nums, index - 1, memo);
        // max will be either the previousSum + currentNum or currentNum
        // example [-1, 2] sum at index 1 will be Max(-1+2, 2)
        return memo[index] = Math.max(maxSumFromPreviousIndex + num, num);
    }

    /**
     * Time Complexity: O(n). A single pass through the array.
     * Space Complexity: O(n). For the dp array.
     */
    public int maxSubArrayBottomUp(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        var maxSubArraySum = nums[0];

        var dp = new Integer[nums.length];
        dp[0] = nums[0];

        for (int i = 1; i < nums.length; i++) {
            dp[i] = Math.max(dp[i - 1] + nums[i], nums[i]);
            maxSubArraySum = Math.max(maxSubArraySum, dp[i]);
        }
        return maxSubArraySum;
    }

    /**
     * Time Complexity: O(n). A single pass through the array.
     * Space Complexity: O(1). Only constant extra space is used.
     */
    public int maxSubArrayKadanes(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        var maxSubArraySum = nums[0];
        var previousIndexSum = nums[0];
        for (int i = 1; i < nums.length; i++) {
            previousIndexSum = Math.max(previousIndexSum + nums[i], nums[i]);
            maxSubArraySum = Math.max(maxSubArraySum, previousIndexSum);
        }
        return maxSubArraySum;
    }

    public static void main(String[] args) {
        var obj = new MaximumSubArray();
        System.out.println(obj.maxSubArrayBruteForce(new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4}));
    }
}
