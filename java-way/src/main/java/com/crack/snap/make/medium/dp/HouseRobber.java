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
     * Time Complexity: O(n) - Each state solve(i) is computed only once.
     * Space Complexity: O(n) - For the recursion stack and memo array.
     */
    public int robTopDown(int[] nums) {
        var memo = new int[nums.length + 1];
        Arrays.fill(memo, -1); // to avoid TLE in memoization
        return robMemoized(nums, nums.length - 1, memo);
    }

    private int robMemoized(int[] nums, int index, int[] memo) {
        if (index < 0) {
            return 0; // No houses left to rob
        }
        if (memo[index] != -1) {
            return memo[index]; // Return cached result
        }
        var robHouse = robMemoized(nums, index - 2, memo) + nums[index];
        var skipHouse = robMemoized(nums, index - 1, memo);
        return memo[index] = Math.max(robHouse, skipHouse);
    }

    /**
     * Time Complexity: O(n) - A single loop through the array.
     * Space Complexity: O(n) - For the dp array.
     */
    public int robBottomUp(int[] nums) {
        var n = nums.length;
        if (n == 0) return 0;
        if (n == 1) return nums[0];
        var dp = new int[n];

        dp[0] = nums[0];
        dp[1] = Math.max(nums[0], nums[1]);

        for (var i = 2; i < n; i++) {
            var robHouse = nums[i] + dp[i - 2];
            var skipHouse = dp[i - 1];
            dp[i] = Math.max(robHouse, skipHouse);
        }
        return dp[n - 1];
    }

    /**
     * Time Complexity: O(n) - A single loop.
     * Space Complexity: O(1) - We only use a few variables to store the state.
     */
    public int robOptimised(int[] nums) {
        var n = nums.length;
        if (n == 0) return 0;
        if (n == 1) return nums[0];

        var prevTwo = nums[0]; // Represents dp[i-2]
        var prevOne = Math.max(nums[0], nums[1]); // Represents dp[i-1]

        for (var i = 2; i < n; i++) {
            var robHouse = nums[i] + prevTwo; // Rob current house
            var skipHouse = prevOne; // Skip current house
            var current = Math.max(robHouse, skipHouse); // Max of robbing or skipping
            prevTwo = prevOne;
            prevOne = current;
        }
        return prevOne; // The last computed value is the maximum amount robbed
    }

    public static void main(String[] args) {
        var obj = new HouseRobber();
        System.out.println("Maximum amount robbed: " + obj.robTopDown(new int[]{2, 7, 9, 3, 1})); // Output: 12
        System.out.println("Maximum amount robbed: " + obj.robTopDown(new int[]{1, 2, 3, 1})); // Output: 4
        System.out.println("Maximum amount robbed: " + obj.robBottomUp(new int[]{2, 7, 9, 3, 1})); // Output: 12
        System.out.println("Maximum amount robbed: " + obj.robBottomUp(new int[]{1, 2, 3, 1})); // Output: 4
        System.out.println("Maximum amount robbed: " + obj.robOptimised(new int[]{2, 7, 9, 3, 1})); // Output: 12
        System.out.println("Maximum amount robbed: " + obj.robOptimised(new int[]{1, 2, 3, 1})); // Output: 4
    }
}
