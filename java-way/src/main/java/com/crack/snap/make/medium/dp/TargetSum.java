package com.crack.snap.make.medium.dp;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Mohan Sharma
 */
public class TargetSum {

    public int findTargetSumWaysBruteForce(int[] nums, int target) {
        return findTargetSumWaysBacktracking(nums, 0, target);
    }

    private int findTargetSumWaysBacktracking(int[] nums, int index, int target) {
        if (index == nums.length) {
            return target == 0 ? 1 : 0;
        }
        return findTargetSumWaysBacktracking(nums, index + 1, target - nums[index]) +
                findTargetSumWaysBacktracking(nums, index + 1, target + nums[index]);
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
    public int findTargetSumWaysOptimal(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        var sum = Arrays.stream(nums).sum();

        if (Math.abs(target) > sum || (sum + target) % 2 != 0) {
            return 0;
        }
        var positiveSum = (sum + target) / 2;
        var memo = new Integer[nums.length][positiveSum + 1];
        return findTargetSumBottomUp(nums, 0, positiveSum, memo);
    }

    private int findTargetSumBottomUp(int[] nums, int index, int target, Integer[][] memo) {
        if (index == nums.length) {
            return target == 0 ? 1 : 0;
        }
        if (index >= nums.length || target < 0) {
            return 0;
        }
        if (memo[index][target] != null) {
            return memo[index][target];
        }
        return memo[index][target] = findTargetSumBottomUp(nums, index + 1, target - nums[index], memo) +
                findTargetSumBottomUp(nums, index + 1, target, memo);
    }

    public static void main(String[] args) {
        var obj = new TargetSum();
        System.out.println(obj.findTargetSumWaysBruteForce(new int[]{1, 1, 1, 1, 1}, 3));
        System.out.println(obj.findTargetSumWaysTopDown(new int[]{1, 1, 1, 1, 1}, 3));
        System.out.println(obj.findTargetSumWaysOptimal(new int[]{1, 1, 1, 1, 1}, 3));
        System.out.println(obj.findTargetSumWaysOptimal(new int[]{1, 0}, 1));
    }
}
