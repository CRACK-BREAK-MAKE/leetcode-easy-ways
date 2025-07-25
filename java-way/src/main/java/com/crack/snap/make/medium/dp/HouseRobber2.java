package com.crack.snap.make.medium.dp;

import java.util.Arrays;

/**
 * The Problem is exactly similar to the House Robber problem, but with a twist:
 *
 * The Core Intuition:
 * Problem Twist: The houses are in a circle, meaning house 0 and house n-1 are neighbors. You cannot rob both.
 * The Insight: This "AND" condition is hard to model. We can change it to an "OR" condition. If we can't rob both, it means we must either:
 * Skip the last house and solve the problem for houses 0 to n-2.
 * OR, skip the first house and solve the problem for houses 1 to n-1.
 * The final answer is the maximum of these two independent scenarios. Each scenario is just the original House Robber problem on a sub-array.
 *
 * @author Mohan Sharma
 */
public class HouseRobber2 {

    /**
     * Time Complexity: O(2^n)
     * Space Complexity: O(n)
     */
    public int robBruteForce(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        return Math.max(solveRobBruteForce(nums, 0, nums.length - 2), solveRobBruteForce(nums, 1, nums.length - 1));
    }

    private int solveRobBruteForce(int[] nums, int startHouseNo, int endHouseNo) {
        if (startHouseNo > endHouseNo) {
            // No amount can be robbed from non-existent house, The robber has 0 amount before he starts the first house
            return 0;
        }
        // decide to rob the current house, so the amount accumulated here will be the amount from (house - 2),
        // since (house - 1) will be adjacent and cannot rob it plus current house amount
        var robHouse = solveRobBruteForce(nums, startHouseNo,endHouseNo - 2) + nums[endHouseNo];
        // decide to rob the current house, so the amount accumulated here will be the amount from (house - 1) plus 0
        var skipHouse = solveRobBruteForce(nums, startHouseNo, endHouseNo - 1);
        return Math.max(robHouse, skipHouse);
    }

    /**
     * Time Complexity: O(n) - Each state solve(i) is computed only once.
     * Space Complexity: O(n) - For the recursion stack and memo array.
     */
    public int robTopDown(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        return Math.max(robMemoized(nums, 0, nums.length - 2, new Integer[nums.length]), robMemoized(nums, 1, nums.length - 1, new Integer[nums.length]));
    }

    private int robMemoized(int[] nums, int startHouseNo, int endHouseNo, Integer[] memo) {
        if (startHouseNo > endHouseNo) {
            // No amount can be robbed from non-existent house, The robber has 0 amount before he starts the first house
            return 0;
        }
        if (memo[endHouseNo] != null) {
            return memo[endHouseNo];
        }
        // decide to rob the current house, so the amount accumulated here will be the amount from (house - 2),
        // since (house - 1) will be adjacent and cannot rob it plus current house amount
        var robHouse = robMemoized(nums, startHouseNo,endHouseNo - 2, memo) + nums[endHouseNo];
        // decide to rob the current house, so the amount accumulated here will be the amount from (house - 1) plus 0
        var skipHouse = robMemoized(nums, startHouseNo, endHouseNo - 1, memo);
        return memo[endHouseNo] = Math.max(robHouse, skipHouse);
    }

    public int robBottomUp(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        if (nums.length == 1) {
            return nums[0]; // since only 1 house, It can't be a neighbor to itself.
        }
        if (nums.length == 2) {
            return Math.max(nums[0], nums[1]); // Because the street is circular, house 0 and house 1 are neighbors. You can only rob one.
        }
        return Math.max(robTabulation(nums, 0, nums.length - 2), robTabulation(nums, 1, nums.length - 1));
    }

    public int robTabulation(int[] nums, int startHouseNo, int endHouseNo) {
        var dp = new int[nums.length];
        dp[startHouseNo] = nums[startHouseNo];
        dp[startHouseNo + 1] = Math.max(nums[startHouseNo], nums[startHouseNo + 1]);

        for (int i = startHouseNo + 2; i <= endHouseNo; i++) {
            var robHouse = dp[i - 2] + nums[i];
            var skipHouse = dp[i - 1];
            dp[i] = Math.max(robHouse, skipHouse);
        }
        return dp[endHouseNo];
    }
    /**
     * Since the classical Top Down House Robber is very similar to this problem, we will skip that instead we will do the optimised version
     */
    public int robTopDownSpaceOptimised(int[] nums) {
        int n = nums.length;
        if (n == 0) {
            return 0;
        }
        if (n == 1) {
            return nums[0]; // since only 1 house, It can't be a neighbor to itself.
        }
        if (n == 2) {
            return Math.max(nums[0], nums[1]); // Because the street is circular, house 0 and house 1 are neighbors. You can only rob one.
        }
        var firstOption = robTopDownOptimised(nums, 0, n - 2); // Rob from first to second last
        var secondOption = robTopDownOptimised(nums, 1, n - 1); // Rob from second to last
        return Math.max(firstOption, secondOption);
    }

    private int robTopDownOptimised(int[] nums, int startHouseNo, int endHouseNo) {
        var minusTwo = nums[startHouseNo];
        var minusOne = Math.max(nums[startHouseNo], nums[startHouseNo + 1]);

        for (int i = startHouseNo + 2; i <= endHouseNo; i++) {
            var robHouse = minusTwo + nums[i];
            var skipHouse = minusOne;
            var currentRob = Math.max(robHouse, skipHouse);
            minusTwo = minusOne;
            minusOne = currentRob;
        }
        return minusOne;
    }

    public static void main(String[] args) {
        var obj = new HouseRobber2();
        System.out.println("Maximum amount robbed Brute Force: " + obj.robBruteForce(new int[]{2, 3, 2})); // Output: 3
        System.out.println("Maximum amount robbed Brute Force: " + obj.robBruteForce(new int[]{1, 2, 3, 1})); // Output: 4
        System.out.println("Maximum amount robbed Top Down: " + obj.robTopDown(new int[]{2, 3, 2})); // Output: 3
        System.out.println("Maximum amount robbed Top Down: " + obj.robTopDown(new int[]{1, 2, 3, 1})); // Output: 4
        System.out.println("Maximum amount robbed Bottom Up: " + obj.robBottomUp(new int[]{2, 3, 2})); // Output: 3
        System.out.println("Maximum amount robbed Bottom Up: " + obj.robBottomUp(new int[]{1, 2, 3, 1})); // Output: 4
    }
}
