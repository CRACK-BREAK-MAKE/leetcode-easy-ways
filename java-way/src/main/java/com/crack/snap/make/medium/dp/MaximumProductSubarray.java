package com.crack.snap.make.medium.dp;

/**
 * @author Mohan Sharma
 */
public class MaximumProductSubarray {

    /**
     * Time Complexity: O(n^2). The main loop runs n times. Inside, solve(i) takes O(i) time. The total time is the sum of 1 + 2 + ... + n, which is O(n^2).
     * Space Complexity: O(n). For the maximum recursion stack depth.
     */
    public int maxProductBruteForce(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        var maxProduct = Integer.MIN_VALUE;
        for (int i = 0; i < nums.length; i++) {
            var productAtCurrentIndex = solveMaxProductBacktracking(nums, i);
            maxProduct = Math.max(maxProduct, Math.max(productAtCurrentIndex[0], productAtCurrentIndex[1]));
        }
        return maxProduct;
    }

    private int[] solveMaxProductBacktracking(int[] nums, int index) {
        if (index == 0) {
            return new int[] { nums[0], nums[0] };
        }
        var num = nums[index];
        var previousIndexProduct = solveMaxProductBacktracking(nums, index - 1);
        var previousIndexMinProduct = previousIndexProduct[0];
        var previousIndexMaxProduct = previousIndexProduct[1];
        var newMinProduct = Math.min(num, Math.min(previousIndexMinProduct * num, previousIndexMaxProduct * num));
        var newMaxProduct = Math.max(num, Math.max(previousIndexMinProduct * num, previousIndexMaxProduct * num));
        return new int[]{newMinProduct, newMaxProduct};
    }

    /**
     * Time Complexity: O(n). The first call to solve(n-1) will fill the memoization table in O(n) time. All subsequent calls from the main loop will be O(1) cache hits.
     * Space Complexity: O(n). For the memo array and the recursion stack.
     */
    public int maxProductTopDown(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        var maxProduct = Integer.MIN_VALUE;
        var memo = new int[nums.length][];
        for (int i = 0; i < nums.length; i++) {
            var productAtCurrentIndex = solveMaxProductTopDown(nums, i, memo);
            maxProduct = Math.max(maxProduct, Math.max(productAtCurrentIndex[0], productAtCurrentIndex[1]));
        }
        return maxProduct;
    }

    private int[] solveMaxProductTopDown(int[] nums, int index, int[][] memo) {
        if (index == 0) {
            return new int[] { nums[index], nums[index] };
        }
        if (memo[index] != null) {
            return memo[index];
        }
        var num = nums[index];
        var previousIndexProduct = solveMaxProductTopDown(nums, index - 1, memo);
        var previousIndexMinProduct = previousIndexProduct[0];
        var previousIndexMaxProduct = previousIndexProduct[1];
        var newMinProduct = Math.min(num, Math.min(previousIndexMinProduct * num, previousIndexMaxProduct * num));
        var newMaxProduct = Math.max(num, Math.max(previousIndexMinProduct * num, previousIndexMaxProduct * num));
        return memo[index] = new int[]{newMinProduct, newMaxProduct};
    }

    /**
     * Time Complexity: O(n). A single pass through the array.
     * Space Complexity: O(n). For the two DP arrays.
     */
    public int maxProductBottomUp(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        var maxProduct = nums[0];
        var dp = new int[nums.length][2];
        dp[0] = new int[]{nums[0], nums[0]};

        for (int i = 1; i < nums.length; i++) {
            var num = nums[i];
            var previousIndexProduct = dp[i - 1];
            var previousIndexMinProduct = previousIndexProduct[0];
            var previousIndexMaxProduct = previousIndexProduct[1];
            var newMinProduct = Math.min(num, Math.min(previousIndexMinProduct * num, previousIndexMaxProduct * num));
            var newMaxProduct = Math.max(num, Math.max(previousIndexMinProduct * num, previousIndexMaxProduct * num));
            dp[i] = new int[]{newMinProduct, newMaxProduct};
            maxProduct = Math.max(maxProduct, Math.max(maxProduct, newMaxProduct));
        }
        return maxProduct;
    }

    /**
     * Time Complexity: O(n). A single pass through the array.
     * Space Complexity: O(1). Only constant extra space is used.
     */
    public int maxProductBottomUpOptimised(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        var maxProduct = nums[0];
        var previousMinProduct = nums[0];
        var previousMaxProduct = nums[0];

        for (int i = 1; i < nums.length; i++) {
            var num = nums[i];
            var newMinProduct = Math.min(num, Math.min(previousMinProduct * num, previousMaxProduct * num));
            var newMaxProduct = Math.max(num, Math.max(previousMinProduct * num, previousMaxProduct * num));
            previousMinProduct = newMinProduct;
            previousMaxProduct = newMaxProduct;
            maxProduct = Math.max(maxProduct, Math.max(maxProduct, newMaxProduct));
        }
        return maxProduct;
    }

    public int maxProductTabulation(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        var result = nums[0];
        var maxDP = new int[nums.length];
        var minDP = new int[nums.length];
        maxDP[0] = nums[0];
        minDP[0] = nums[0];

        for (var i = 1; i < nums.length; i++) {
            var num = nums[i];
            maxDP[i] = Math.max(num, Math.max(num * maxDP[i - 1], num * minDP[i - 1]));
            minDP[i] = Math.min(num, Math.min(num * maxDP[i - 1], num * minDP[i - 1]));
            result = Math.max(result, maxDP[i]);
        }
        return result;
    }

    public static void main(String[] args) {
        var obj = new MaximumProductSubarray();
        System.out.println("Max Product (Brute Force) Result: " + obj.maxProductBruteForce(new int[]{2, 3, -2, -4})); // Output: 48
        System.out.println("Max Product (Brute Force) Result: " + obj.maxProductBruteForce(new int[]{-2, 1, -2})); // Output: 4
        System.out.println("Max Product (Brute Force) Result: " + obj.maxProductBruteForce(new int[]{-2, 0, -1})); // Output: 0
        System.out.println("Max Product (Top Down) Result: " + obj.maxProductTopDown(new int[]{2, 3, -2, -4})); // Output: 48
        System.out.println("Max Product (Top Down) Result: " + obj.maxProductTopDown(new int[]{-2, 1, -2})); // Output: 4
        System.out.println("Max Product (Top Down) Result: " + obj.maxProductTopDown(new int[]{-2, 0, -1})); // Output: 0
        System.out.println("Max Product (Bottom Up) Result: " + obj.maxProductBottomUp(new int[]{2, 3, -2, -4})); // Output: 48
        System.out.println("Max Product (Bottom Up) Result: " + obj.maxProductBottomUp(new int[]{-2, 1, -2})); // Output: 4
        System.out.println("Max Product (Bottom Up) Result: " + obj.maxProductBottomUp(new int[]{-2, 0, -1})); // Output: 0
        System.out.println("Max Product (Optimised) Result: " + obj.maxProductBottomUpOptimised(new int[]{2, 3, -2, -4})); // Output: 48
        System.out.println("Max Product (Optimised) Result: " + obj.maxProductBottomUpOptimised(new int[]{-2, 1, -2})); // Output: 4
        System.out.println("Max Product (Optimised) Result: " + obj.maxProductBottomUpOptimised(new int[]{-2, 0, -1})); // Output: 0
    }
}
