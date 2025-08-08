package com.crack.snap.make.medium.dp;

import java.util.Arrays;

/**
 * @author Mohan Sharma
 */
public class PartitionEqualSubsetSum {

    public boolean canPartitionBruteForce(int[] nums) {
        if (nums == null || nums.length == 0) {
            return false;
        }
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }
        if (sum % 2 != 0) {
            return false;
        }
        return canPartitionBacktracking(nums, 0, sum/2);
    }

    private boolean canPartitionBacktracking(int[] nums, int index, int target) {
        if (target == 0) {
            return true;
        }
        if (target < 0 || index >= nums.length) {
            return false;
        }
        // pick or not pick
        return canPartitionBacktracking(nums, index + 1, target - nums[index]) ||
                canPartitionBacktracking(nums, index + 1, target);
    }

    public boolean canPartitionTopDown(int[] nums) {
        if (nums == null || nums.length == 0) {
            return false;
        }
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }
        if (sum % 2 != 0) {
            return false;
        }
        int target = sum / 2;
        var n = nums.length;
        var memo = new Boolean[n][target + 1];
        return canPartitionMemoization(nums, 0, target, memo);
    }

    private boolean canPartitionMemoization(int[] nums, int index, int target, Boolean[][] memo) {
        if (target == 0) {
            return true;
        }
        if (target < 0 || index >= nums.length) {
            return false;
        }
        if (memo[index][target] != null) {
            return memo[index][target];
        }
        // pick or not pick
        return memo[index][target] = canPartitionMemoization(nums, index + 1, target - nums[index], memo) ||
                canPartitionMemoization(nums, index + 1, target, memo);
    }

    public boolean canPartitionBottomUp(int[] nums) {
        if (nums == null || nums.length == 0) {
            return false;
        }
        int sum = Arrays.stream(nums).sum();
        if (sum % 2 != 0) {
            return false;
        }
        int target = sum / 2;
        var n = nums.length;
        var dp = new boolean[target + 1][n];
        for (var i = 0; i < n; i++) {
            dp[0][i] = true; // no matter the num if the target is 0 it is true
        }

        for (var i = 1; i <= target; i++) {
            for (var j = 1; j <n; j++) {
                // don't pick the item
                dp[i][j] = dp[i][j - 1];
                if (nums[j] <= i) {
                    // pick the item
                    dp[i][j] = dp[i][j] || dp[i - nums[j]][j - 1];
                }
            }
        }
        return dp[target][n - 1];
    }

    public static void main(String[] args) {
        var obj = new PartitionEqualSubsetSum();
        System.out.println(obj.canPartitionBruteForce(new int[]{1, 5, 11, 5}));
        System.out.println(obj.canPartitionBruteForce(new int[]{1, 2, 3, 5}));
        System.out.println(obj.canPartitionTopDown(new int[]{1, 5, 11, 5}));
        System.out.println(obj.canPartitionTopDown(new int[]{1, 2, 3, 5}));
        System.out.println(obj.canPartitionBottomUp(new int[]{1, 5, 11, 5}));
        System.out.println(obj.canPartitionBottomUp(new int[]{1, 2, 3, 5}));
    }
}
