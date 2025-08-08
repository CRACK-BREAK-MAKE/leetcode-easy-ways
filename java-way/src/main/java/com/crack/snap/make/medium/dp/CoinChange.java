package com.crack.snap.make.medium.dp;

import java.util.Arrays;

/**
 * @author Mohan Sharma
 */
public class CoinChange {

    public int coinChangeBruteForce(int[] coins, int amount) {
        if (coins == null || coins.length == 0 || amount < 0) {
            return -1;
        }
        return solveCoinChangeBruteForce(coins, amount);
    }

    private int solveCoinChangeBruteForce(int[] coins, int amount) {
        if (amount == 0) {
            return 0;
        }
        if (amount < 0) {
            return -1; // Not possible to make change
        }
        var minCoins = Integer.MAX_VALUE;
        for (var coin : coins) {
            if (coin <= amount) {
                var result = solveCoinChangeBruteForce(coins, amount - coin);
                if (result != -1) {
                    minCoins = Math.min(minCoins, result + 1);
                }
            }
        }
        return minCoins == Integer.MAX_VALUE ? -1 : minCoins;
    }

    public int coinChangeTopDown(int[] coins, int amount) {
        if (coins == null || coins.length == 0 || amount < 0) {
            return -1;
        }
        return coinChangeMemoization(coins, amount, new int[amount + 1]);
    }

    private int coinChangeMemoization(int[] coins, int amount, int[] memo) {
        if (amount == 0) {
            return 0;
        }
        if (amount < 0) {
            return -1; // Not possible to reach this amount
        }
        if (memo[amount] != 0) {
            return memo[amount]; // Return cached result
        }
        var minCoins = Integer.MAX_VALUE;
        for (var coin : coins) {
            if (coin <= amount) {
                var coinsRequired = coinChangeMemoization(coins, amount - coin, memo);
                if (coinsRequired != -1) {
                    minCoins = Math.min(minCoins, 1 + coinsRequired);
                }
            }
        }
        return memo[amount] = (minCoins == Integer.MAX_VALUE) ? -1 : minCoins;
    }

    private int coinChangeTabulation(int[] coins, int amount) {
        if (coins == null || coins.length == 0 || amount < 0) {
            return -1;
        }
        var dp = new int[amount + 1];
        Arrays.fill(dp, amount + 1); // Initialize with a value greater than the maximum possible coins
        dp[0] = 0; // Base case: 0 coins needed to make amount 0
        for (var i = 1; i <= amount; i++) {
            for (var coin : coins) {
                if (amount >= coin) {
                    dp[i] = Math.min(dp[i], 1 + dp[i - coin]);
                }
            }
        }
        return dp[amount] == amount + 1 ? -1 : dp[amount];
    }

    public static void main(String[] args) {
        var obj = new CoinChange();
        System.out.println(obj.coinChangeBruteForce(new int[]{1, 2, 5}, 11)); // Output: 3 (5 + 5 + 1)
    }
}
