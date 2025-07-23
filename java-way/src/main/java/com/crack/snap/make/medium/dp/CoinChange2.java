package com.crack.snap.make.medium.dp;

import java.util.Arrays;

/**
 * @author Mohan Sharma
 */
public class CoinChange2 {

    public int changeBruteForce(int amount, int[] coins) {
        if (coins == null || coins.length == 0 || amount < 0) {
            return 0;
        }
        return changeBacktracking(coins, amount, 0);
    }

    private int changeBacktracking(int[] coins, int amount, int index) {
        if (amount == 0) {
            return 1;
        }
        if (amount < 0 || index >= coins.length) {
            return 0;
        }
        var count = 0;
        // Not pick scenario
        count += changeBacktracking(coins, amount, index + 1);
        if (amount >= coins[index]) {
            // Multi times Pick scenario
            count += changeBacktracking(coins, amount - coins[index], index);
        }
        return count;
    }

    public int changeTopDown(int amount, int[] coins) {
        if (coins == null || coins.length == 0 || amount < 0) {
            return 0;
        }
        var memo = new int[amount + 1][coins.length];
        for (var i = 0; i <= amount; i++) {
            Arrays.fill(memo[i], -1);
        }
        return changeMemoization(coins, amount, 0, memo);
    }

    private int changeMemoization(int[] coins, int amount, int index, int[][] memo) {
        if (amount == 0) {
            return 1;
        }
        if (amount < 0 || index >= coins.length) {
            return 0;
        }
        if (memo[amount][index] != -1) {
            return memo[amount][index];
        }
        // Not Pick Scenario
        var count = changeMemoization(coins, amount, index + 1, memo);
        if (amount >= coins[index]) {
            // Multi times Pick scenario
            count += changeMemoization(coins, amount - coins[index], index, memo);
        }
        return memo[amount][index] = count;
    }

    public int changeBottomUp(int amount, int[] coins) {
        if (coins == null || coins.length == 0 || amount < 0) {
            return 0;
        }
        var dp = new int[amount + 1][coins.length];
        for (var i = 0; i < coins.length; i++) {
            dp[0][i] = 1; // no matter the coin if the amount is zero there is one way to do this
        }
        for (var i = 1; i <= amount; i++) {
            for (var j = 0; j < coins.length; j++) {
                if (j > 0) {
                    dp[i][j] = dp[i][j - 1];
                }
                if (i >= coins[j]) {
                    dp[i][j] += dp[i - coins[j]][j];
                }
            }
        }
        return dp[amount][coins.length - 1];
    }

    public static void main(String[] args) {
        var obj = new CoinChange2();
        System.out.println(obj.changeBruteForce(5, new int[]{1, 2, 5}));
        System.out.println(obj.changeTopDown(5, new int[]{1, 2, 5}));
        System.out.println(obj.changeBottomUp(5, new int[]{1, 2, 5}));
    }
}
