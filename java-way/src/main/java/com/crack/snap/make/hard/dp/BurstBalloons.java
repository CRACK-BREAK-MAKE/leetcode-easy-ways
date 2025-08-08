package com.crack.snap.make.hard.dp;

/**
 * @author Mohan Sharma
 */
public class BurstBalloons {

    public int maxCoinsBruteForce(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        var newNums = new int[nums.length + 2];
        newNums[0] = 1;
        newNums[newNums.length - 1] = 1;
        for (int i = 1; i < newNums.length - 1; i++) {
            newNums[i] = nums[i - 1];
        }

        return solveBruteForce(newNums, 1, newNums.length - 2);
    }

    private int solveBruteForce(int[] newNums, int start, int end) {
        if (start > end) {
            return 0;
        }
        var maxCoins = 0;
        for (int k = start; k <= end; k++) {
            var currentPathCoins =
                    solveBruteForce(newNums, start, k - 1) +
                            newNums[start - 1] * newNums[k] * newNums[end + 1] +
                            solveBruteForce(newNums, k + 1, end);
            maxCoins = Math.max(maxCoins, currentPathCoins);
        }
        return maxCoins;
    }

    public int maxCoinsTopDown(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        var newNums = new int[nums.length + 2];
        newNums[0] = 1;
        newNums[newNums.length - 1] = 1;
        for (int i = 1; i < newNums.length - 1; i++) {
            newNums[i] = nums[i - 1];
        }
        return solveMemoization(newNums, 1, newNums.length - 2, new Integer[newNums.length][newNums.length]);
    }

    private int solveMemoization(int[] newNums, int start, int end, Integer[][] memo) {
        if (start > end) {
            return 0;
        }
        if (memo[start][end] != null) {
            return memo[start][end];
        }
        var maxCoins = 0;
        for (int k = start; k <= end; k++) {
            var currentPathCoins =
                    solveMemoization(newNums, start, k - 1, memo) +
                            newNums[start - 1] * newNums[k] * newNums[end + 1] +
                            solveMemoization(newNums, k + 1, end, memo);
            maxCoins = Math.max(maxCoins, currentPathCoins);
        }
        return memo[start][end] = maxCoins;
    }

    public static void main(String[] args) {
        var obj = new BurstBalloons();
        System.out.println("Brute Force: " + obj.maxCoinsBruteForce(new int[]{3,1,5,8}));
        System.out.println("Brute Force: " + obj.maxCoinsBruteForce(new int[]{1,5}));
        System.out.println("Top Down: " + obj.maxCoinsTopDown(new int[]{3,1,5,8}));
        System.out.println("Top Down: " + obj.maxCoinsTopDown(new int[]{1,5}));
    }
}
