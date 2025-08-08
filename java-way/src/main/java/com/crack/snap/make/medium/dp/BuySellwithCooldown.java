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
        return maxProfitMemoization(prices, 0, 0, new Integer[prices.length][2]);
    }

    private int maxProfitMemoization(int[] prices, int day, int holding, Integer[][] memo) {
        if (day >= prices.length) {
            return 0;
        }
        if (memo[day][holding] != null) {
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
        if (prices == null || prices.length <= 1) {
            return 0;
        }

        int n = prices.length;
        // dp[i][0]: Max profit on day i, ending with no stock (after selling or resting)
        // dp[i][1]: Max profit on day i, ending with a stock (after buying or holding)
        int[][] dp = new int[n][2];

        // --- Base Cases ---
        // Day 0:
        dp[0][0] = 0; // Do nothing
        dp[0][1] = -prices[0]; // Buy the stock

        // Day 1:
        // No stock: either rest from day 0 (profit 0), or sell stock from day 0
        dp[1][0] = Math.max(dp[0][0], dp[0][1] + prices[1]);
        // Has stock: either hold from day 0 (profit -prices[0]), or buy today (profit -prices[1])
        dp[1][1] = Math.max(dp[0][1], -prices[1]);


        // --- Fill the rest of the table ---
        for (int i = 2; i < n; i++) {
            // Max profit with no stock today:
            // 1. We had no stock yesterday and rested. (dp[i-1][0])
            // 2. We had a stock yesterday and sold it today. (dp[i-1][1] + prices[i])
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i]);

            // Max profit with a stock today:
            // 1. We had a stock yesterday and held it. (dp[i-1][1])
            // 2. We had no stock 2 days ago, rested yesterday (cooldown), and bought today. (dp[i-2][0] - prices[i])
            dp[i][1] = Math.max(dp[i - 1][1], dp[i - 2][0] - prices[i]);
        }

        // The final answer must be a state where we don't hold a stock.
        return dp[n - 1][0];
    }

    public static void main(String[] args) {
        var obj = new BuySellwithCooldown();
        System.out.println(obj.maxProfitBruteForce(new int[]{1,2,3,0,2}));
        System.out.println(obj.maxProfitTopDown(new int[]{1,2,3,0,2}));
        System.out.println(obj.maxProfitBottomUp(new int[]{1,2,3,0,2}));
    }
}
