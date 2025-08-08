package com.crack.snap.make.medium.dp;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Mohan Sharma
 */
public class TargetSum {

    public int findTargetSumWaysBruteForce(int[] nums, int target) {
        return solveBruteForce(nums, target, 0);
    }

    private int solveBruteForce(int[] nums, int target, int index) {
        // Base Case: If we've processed all numbers, check if the remaining target is 0.
        if (index == nums.length) {
            return target == 0 ? 1 : 0;
        }
        // Choice 1: Assign '-' to nums[index]. The new target becomes target + nums[index].
        int subtract = solveBruteForce(nums, target + nums[index], index + 1);
        // Choice 2: Assign '+' to nums[index]. The new target becomes target - nums[index].
        int add = solveBruteForce(nums, target - nums[index], index + 1);

        return add + subtract;
    }

    public int findTargetSumWaysTopDown(int[] nums, int target) {
        // since the target can be negative we cannot use int array as dp instead use hashmap
        return findTargetSumWaysMemoization(nums, 0, target, new HashMap<>());
    }

    private int findTargetSumWaysMemoization(int[] nums, int index, int target, Map<String, Integer> hash) {
        if (index >= nums.length) {
            return target == 0 ? 1 : 0;
        }
        var key = index + ":" + target;
        if (hash.containsKey(key)) {
            return hash.get(key);
        }
        var count = findTargetSumWaysMemoization(nums, index + 1, target - nums[index], hash) +
                findTargetSumWaysMemoization(nums, index + 1, target + nums[index], hash);
        hash.put(key, count);
        return count;
    }

    /**
     * Imagine we are splitting the nums array into two groups: a "positive" subset (P) whose elements we add, and a "negative" subset (N) whose elements we subtract.
     * Our goal is to find sum(P) - sum(N) = target.
     * We also know a simple fact: sum(P) + sum(N) = totalSum (the sum of all numbers in the original array).
     * Now, let's do some simple algebra. Add the two equations together:
     * (sum(P) - sum(N)) + (sum(P) + sum(N)) = target + totalSum
     * 2 * sum(P) = target + totalSum
     * sum(P) = (target + totalSum) / 2
     *
     * This is actually a subset sum problem in disguise!
     */
    public int findTargetSumWaysOptimalBruteForce(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        var sum = Arrays.stream(nums).sum();

        if ((sum + target) % 2 != 0) {
            return 0;
        }
        var positiveSum = (sum + target) / 2;
        return solveFindTargetSumWaysOptimalBruteForce(nums, positiveSum, 0);
    }

    private int solveFindTargetSumWaysOptimalBruteForce(int[] nums, int target, int index) {
        if (target == 0) {
            return 1;
        }
        if (target < 0 || index >= nums.length) {
            return 0;
        }
        return solveFindTargetSumWaysOptimalBruteForce(nums, target, index + 1) +
                solveFindTargetSumWaysOptimalBruteForce(nums, target - nums[index], index + 1);
    }

    public int findTargetSumWaysOptimalTopDown(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        var sum = Arrays.stream(nums).sum();

        if ((sum + target) % 2 != 0) {
            return 0;
        }
        var positiveSum = (sum + target) / 2;
        var memo = new Integer[positiveSum + 1][nums.length];
        return solveFindTargetSumWaysOptimalTopDown(nums, positiveSum, 0, memo);
    }

    private int solveFindTargetSumWaysOptimalTopDown(int[] nums, int target, int index, Integer[][] memo) {
        if (target == 0) {
            return 1;
        }
        if (target < 0 || index >= nums.length) {
            return 0;
        }
        if (memo[target][index] != null) {
            return memo[target][index];
        }
        return solveFindTargetSumWaysOptimalTopDown(nums, target, index + 1, memo) +
                solveFindTargetSumWaysOptimalTopDown(nums, target - nums[index], index + 1, memo);
    }

    public static void main(String[] args) {
        var obj = new TargetSum();
        System.out.println("Brute Force: " + obj.findTargetSumWaysOptimalBruteForce(new int[]{1, 1, 1, 1, 1}, 3));
        System.out.println("Brute Force: " + obj.findTargetSumWaysOptimalBruteForce(new int[]{1, 0}, 1));
        System.out.println("Top Down: " + obj.findTargetSumWaysOptimalTopDown(new int[]{1, 1, 1, 1, 1}, 3));
        System.out.println("Top Down: " + obj.findTargetSumWaysOptimalTopDown(new int[]{1, 0}, 1));
    }
}
