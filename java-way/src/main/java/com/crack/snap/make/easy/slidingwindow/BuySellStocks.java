package com.crack.snap.make.easy.slidingwindow;

/**
 * @author Mohan Sharma
 */
public class BuySellStocks {

    /**
     * Problem: I am given an array prices where prices[i] is the price of a given stock on the ith day.
     * Maximize profit by choosing a single day to buy one stock and choosing a different day in the future to sell that stock.
     *
     * Intuition:
     * Since the problem states that I can choose a single day to buy and a different day to sell meaning I can buy only once and sell once.
     * To maximize the profit what I can do is scan from index 1 to n-1.
     * Assume that the lowest price minPrice is at index 0, calculate the profit by doing (prices[i] - minPrice).
     * If the current profit is greater than the maxProfit then update the maxProfit.
     * Also check if the current index price is less than the previous min price to maximize the profit.
     *
     * Time Complexity: O(n), where n is the length of the prices array.
     * Space Complexity: O(1), as we are using a constant amount of extra space.
     */
    public int maxProfit(int[] prices) {
        if (prices == null || prices.length < 2) {
            return 0;
        }

        var maxProfit = 0;
        var minPrice = prices[0];
        for (var i = 1; i < prices.length; i++) {
            maxProfit = Math.max(maxProfit, prices[i] - minPrice);
            minPrice = Math.min(minPrice, prices[i]);
        }

        return maxProfit;
    }

    public static void main(String[] args) {
        BuySellStocks buySellStocks = new BuySellStocks();
        System.out.println(buySellStocks.maxProfit(new int[]{7, 1, 5, 3, 6, 4})); // 7
        System.out.println(buySellStocks.maxProfit(new int[]{7,6,4,3,1})); // 0
    }
}
