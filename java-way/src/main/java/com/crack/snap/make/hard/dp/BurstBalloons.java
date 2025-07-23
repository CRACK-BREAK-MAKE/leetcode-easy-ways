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

        return maxCoinsBacktracking(newNums, 1, newNums.length - 2);
    }

    private int maxCoinsBacktracking(int[] newNums, int start, int end) {
        if (start > end) {
            return 0;
        }
        var maxCoins = 0;
        for (int k = start; k <= end; k++) {
            var currentPathCoins =
                    maxCoinsBacktracking(newNums, start, k - 1) +
                            newNums[start - 1] * newNums[k] * newNums[end + 1] +
                            maxCoinsBacktracking(newNums, k + 1, end);
            maxCoins = Math.max(maxCoins, currentPathCoins);
        }
        return maxCoins;
    }

    public static void main(String[] args) {
        var obj = new BurstBalloons();
        System.out.println(obj.maxCoinsBruteForce(new int[]{3,1,5,8}));
    }
}
