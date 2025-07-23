package com.crack.snap.make.medium.dp;

/**
 * @author Mohan Sharma
 */
public class MaximumProductSubarray {

    public int maxProductBruteForce(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        return maxProductBacktracking(nums, 0);
    }

    private int maxProductBacktracking(int[] nums, int start) {
        var n = nums.length;
        if (start == n) {
            return Integer.MIN_VALUE; // Base case: product of empty subarray is MIN_VALUE
        }
        var maxProduct = Integer.MIN_VALUE;
        var currentProduct = 1;
        for (int i = start; i < n; i++) {
            currentProduct *= nums[i]; // Calculate product of subarray nums[start...i]
            maxProduct = Math.max(maxProduct, currentProduct);
        }
        return Math.max(maxProduct, maxProductBacktracking(nums, start + 1));
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

        for (var i = 1;  i < nums.length; i++) {
            var num = nums[i];
            maxDP[i] = Math.max(num, Math.max(num * maxDP[i - 1], num * minDP[i - 1]));
            minDP[i] = Math.min(num, Math.min(num * maxDP[i - 1], num * minDP[i - 1]));
            result = Math.max(result, maxDP[i]);
        }
        return result;
    }

    public static void main(String[] args) {
        var obj = new MaximumProductSubarray();
        System.out.println("Max Product (Brute Force) Result: " + obj.maxProductBruteForce(new int[]{2, 3, -2, 4})); // Output: 6
        System.out.println("Max Product (Brute Force) Result: " + obj.maxProductBruteForce(new int[]{-2, 0, -1})); // Output: 0
        System.out.println("Max Product (Tabulation) Result: " + obj.maxProductTabulation(new int[]{2, 3, -2, -4})); // Output: 48
        System.out.println("Max Product (Tabulation) Result: " + obj.maxProductTabulation(new int[]{-2, 0, -1})); // Output: 0
    }
}
