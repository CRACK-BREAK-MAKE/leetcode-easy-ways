package com.crack.snap.make.medium.dp;

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
        if (index == nums.length) {
            return target == 0;
        }
        if (target < 0) {
            return false;
        }
        if (target == 0) {
            return true;
        }
        for (int j = index; j < nums.length; j++) {
            if (canPartitionBacktracking(nums, j + 1, target - nums[j])) {
                return true;
            }
        }
        return false;
    }

    public boolean canPartitionBruteForce2(int[] nums) {
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
        return canPartitionBacktracking2(nums, 0, sum/2);
    }

    private boolean canPartitionBacktracking2(int[] nums, int index, int target) {
        if (index == nums.length - 1) {
            return target ==  nums[index];
        }
        if (target < 0) {
            return false;
        }
        if (target == 0) {
            return true;
        }
        // pick or not pick
        return canPartitionBacktracking2(nums, index + 1, target - nums[index]) ||
                canPartitionBacktracking2(nums, index + 1, target);
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
        if (index == nums.length) {
            return target == 0;
        }
        if (target < 0) {
            return false;
        }
        if (target == 0) {
            return true;
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
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }
        if (sum % 2 != 0) {
            return false;
        }
        int target = sum / 2;
        var n = nums.length;
        var dp = new boolean[n + 1][target + 1];
        for (var i = 0; i <= n; i++) {
            dp[i][0] = true;
        }

        for (var i = 1; i <= n; i++) {
            int item = nums[i - 1];
            for (var j = 1; j <= target; j++) {
                // don't pick the item
                dp[i][j] = dp[i - 1][j];
                if (item <= j) {
                    // pick the item
                    dp[i][j] = dp[i][j] || dp[i - 1][j - item];
                }
            }
        }
        return dp[n][target];
    }

    public static void main(String[] args) {
        var obj = new PartitionEqualSubsetSum();
        System.out.println(obj.canPartitionBruteForce(new int[]{1, 5, 11, 5}));
        System.out.println(obj.canPartitionBruteForce(new int[]{1, 2, 3, 5}));
        System.out.println(obj.canPartitionBruteForce2(new int[]{1, 5, 11, 5}));
        System.out.println(obj.canPartitionBruteForce2(new int[]{1, 2, 3, 5}));
        System.out.println(obj.canPartitionTopDown(new int[]{1, 5, 11, 5}));
        System.out.println(obj.canPartitionTopDown(new int[]{1, 2, 3, 5}));
        System.out.println(obj.canPartitionBottomUp(new int[]{1, 5, 11, 5}));
    }
}
