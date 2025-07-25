package com.crack.snap.make.medium.dp;

import java.util.Arrays;

/**
 * @author Mohan Sharma
 */
public class BuySellwithCooldown {

    public int maxProfitBruteForce(int[] prices) {
        return maxProfitBacktracking(prices, 0, false);
    }

    private int maxProfitBacktracking(int[] prices, int index, boolean holding) {
        if (index >= prices.length) {
            return 0;
        }
        if (holding) {
            // next day is mandatory cool down or rest period so move to i + 2
            var sell = prices[index] + maxProfitBacktracking(prices, index + 2, false);
            var hold = maxProfitBacktracking(prices, index + 1, holding);
            return Math.max(sell, hold);
        } else {
            var buy = -prices[index] + maxProfitBacktracking(prices, index + 1, true);
            var hold = maxProfitBacktracking(prices, index + 1, holding);
            return Math.max(buy, hold);
        }
    }

    public int maxProfitTopDown(int[] prices) {
        if (prices == null || prices.length == 0) {
            return 0;
        }
        var memo = new int[prices.length][2];
        for (int i = 0; i < prices.length; i++) {
            Arrays.fill(memo[i], -1);
        }
        return maxProfitMemoization(prices, 0, 0, memo);
    }

    private int maxProfitMemoization(int[] prices, int day, int holding, int[][] memo) {
        if (day >= prices.length) {
            return 0;
        }
        if (memo[day][holding] != -1) {
            return memo[day][holding];
        }
        if (holding == 1) {
            // next day is mandatory cool down or rest period so move to i + 2
            var sell = prices[day] + maxProfitMemoization(prices, day + 2, 0, memo);
            var hold = maxProfitMemoization(prices, day + 1, 1, memo);
            return memo[day][holding] = Math.max(sell, hold);
        } else {
            var buy = -prices[day] + maxProfitMemoization(prices, day + 1, 1, memo);
            var hold = maxProfitMemoization(prices, day + 1, 0, memo);
            return memo[day][holding] = Math.max(buy, hold);
        }
    }

    public int maxProfitBottomUp(int[] prices) {
        if (prices == null || prices.length == 0) {
            return 0;
        }
        int n = prices.length;
        var dp = new int[n + 2][2]; // +2 for safe day+2 access, since we know if day >= prices.length profit is 0
        dp[n -1][1] = -prices[n -1];
        // Fill from day n-1 down to 0 (reverse of top-down)
        for (int day = n - 1; day >= 0; day--) {
            // If holding stock
            int sell = prices[day] + dp[day + 2][0]; // Sell + cooldown
            int hold = dp[day + 1][1];               // Keep holding
            dp[day][1] = Math.max(sell, hold);

            // If not holding stock
            int buy = -prices[day] + dp[day + 1][1];  // Buy stock
            int rest = dp[day + 1][0];                // Stay without stock
            dp[day][0] = Math.max(buy, rest);
        }
        return dp[0][0];
    }

    public static void main(String[] args) {
        var obj = new BuySellwithCooldown();
        System.out.println(obj.maxProfitBruteForce(new int[]{1,2,3,0,2}));
        System.out.println(obj.maxProfitTopDown(new int[]{1,2,3,0,2}));
        System.out.println(obj.maxProfitBottomUp(new int[]{1,2,3,0,2}));
    }
}
