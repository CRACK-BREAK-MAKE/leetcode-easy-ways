package com.crack.snap.make.medium.dp;

import java.util.Arrays;

/**
 * This problem is a perfect fit for Pattern 1: Linear DP. The decision you make for house i (to rob or not to rob) directly
 * depends on the outcomes of the immediate preceding houses, i-1 and i-2.
 *
 * The Core Intuition:
 * State Definition: Let dp[i] be the maximum amount of money you can rob from the beginning up to and including house i.
 * Choice: At any house i, you have two choices:
 *  Rob house i: You gain nums[i]. Since you can't rob the adjacent house i-1, the maximum you could have robbed before this is dp[i-2]. Total: nums[i] + dp[i-2].
 *  Skip house i: You don't gain money from this house. The maximum you could have robbed is simply the max from the previous house, dp[i-1].
 *
 * Recurrence Relation: You'll choose the option that gives you more money. dp[i] = max(dp[i-1], (dp[i-2] + nums[i]))
 * Base Cases:
 * dp[0] = nums[0]
 * dp[1] = max(nums[0], nums[1])
 *
 * Recursive Flow Diagram
 * Let's trace rob(i) for nums = [2, 7, 9, 3, 1]. rob(i) means "max money from houses 0...i".
 *
 *                 rob(4)
 *                /      \
 *       rob(3)              rob(2) + nums[4]
 *      /    \                  /      \
 *  rob(2)   rob(1)+nums[3]   rob(1)    rob(0)+nums[2]
 *  ...          ...           ...         ...
 * You can see rob(2) and rob(1) are calculated multiple times. Memoization prevents these re-computations.
 *
 * @author Mohan Sharma
 */
public class HouseRobber {


    /**
     * Time Complexity: O(2^n)
     * Space Complexity: O(n)
     */
    public int robBruteForce(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        // State: Get maximum amount of money that can be accumulated from house 0 to n
        return solveRobBruteForce(nums, nums.length - 1);
    }

    private int solveRobBruteForce(int[] nums, int houseNo) {
        if (houseNo < 0) {
            // No amount can be robbed from non-existent house, The robber has 0 amount before he starts the first house
            return 0;
        }
        // decide to rob the current house, so the amount accumulated here will be the amount from (house - 2),
        // since (house - 1) will be adjacent and cannot rob it plus current house amount
        var robbHouse = solveRobBruteForce(nums, houseNo - 2) + nums[houseNo];
        // decide to rob the current house, so the amount accumulated here will be the amount from (house - 1) plus 0
        var skipHouse = solveRobBruteForce(nums, houseNo - 1);
        return Math.max(robbHouse, skipHouse);
    }

    /**
     * Time Complexity: O(n) - Each state solve(i) is computed only once.
     * Space Complexity: O(n) - For the recursion stack and memo array.
     */
    public int robTopDown(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        return robMemoized(nums, nums.length - 1, new Integer[nums.length]);
    }

    private int robMemoized(int[] nums, int houseNo, Integer[] memo) {
        if (houseNo < 0) {
            return 0;
        }
        if (memo[houseNo] != null) {
            return memo[houseNo]; // Return cached result
        }
        var robHouse = robMemoized(nums, houseNo - 2, memo) + nums[houseNo];
        var skipHouse = robMemoized(nums, houseNo - 1, memo);
        return memo[houseNo] = Math.max(robHouse, skipHouse);
    }

    /**
     * Time Complexity: O(n) - A single loop through the array.
     * Space Complexity: O(n) - For the dp array.
     */
    public int robBottomUp(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        var n = nums.length;
        // if only 1 house, let's rob and return
        if (n == 1) return nums[0];

        // var robHouse = robMemoized(nums, houseNo - 2, memo) + nums[houseNo];
        // var skipHouse = robMemoized(nums, houseNo - 1, memo);
        // Analyse for index 0
        // var robHouse = 0 + nums[0], var skipHouse = 0 + 0 so for index 0, its max(nums[0], 0) = nums[0]
        // Analyse for index 1
        // var robHouse = 0 + nums[1], var skipHouse = robMemoized(nums, 0, memo) = nums[0] so for index 1, its max(nums[0], nums[1])

        var dp = new int[n];
        dp[0] = nums[0];
        dp[1] = Math.max(nums[0], nums[1]);

        for (var houseNo = 2; houseNo < n; houseNo++) {
            var robHouse = dp[houseNo - 2] + nums[houseNo];
            var skipHouse = dp[houseNo - 1];
            dp[houseNo] = Math.max(robHouse, skipHouse);
        }
        return dp[n - 1];
    }

    /**
     * Time Complexity: O(n) - A single loop.
     * Space Complexity: O(1) - We only use a few variables to store the state.
     */
    public int robOptimised(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        var n = nums.length;
        if (n == 1) return nums[0];

        var minusTwo = nums[0];
        var minusOne = Math.max(nums[0], nums[1]);

        for (int houseNo = 2; houseNo < n; houseNo++) {
            var robHouse = minusTwo + nums[houseNo];
            var skipHouse = minusOne;
            var currentRobbed = Math.max(robHouse, skipHouse);
            minusTwo = minusOne;
            minusOne = currentRobbed;
        }
        return minusOne;
    }

    public static void main(String[] args) {
        var obj = new HouseRobber();
        System.out.println("Maximum amount robbed Brute Force: " + obj.robBruteForce(new int[]{2, 7, 9, 3, 1})); // Output: 12
        System.out.println("Maximum amount robbed Brute Force: " + obj.robBruteForce(new int[]{1, 2, 3, 1})); // Output: 4
        System.out.println("Maximum amount robbed Top Down: " + obj.robTopDown(new int[]{2, 7, 9, 3, 1})); // Output: 12
        System.out.println("Maximum amount robbed Top Down: " + obj.robTopDown(new int[]{1, 2, 3, 1})); // Output: 4
        System.out.println("Maximum amount robbed Bottom Up: " + obj.robBottomUp(new int[]{2, 7, 9, 3, 1})); // Output: 12
        System.out.println("Maximum amount robbed Bottom Up: " + obj.robBottomUp(new int[]{1, 2, 3, 1})); // Output: 4
        System.out.println("Maximum amount robbed Space Optimized: " + obj.robOptimised(new int[]{2, 7, 9, 3, 1})); // Output: 12
        System.out.println("Maximum amount robbed Space Optimized: " + obj.robOptimised(new int[]{1, 2, 3, 1})); // Output: 4
    }
}
