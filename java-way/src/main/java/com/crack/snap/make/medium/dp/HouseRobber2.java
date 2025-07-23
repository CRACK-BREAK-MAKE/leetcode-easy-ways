package com.crack.snap.make.medium.dp;

import java.util.Arrays;

/**
 * The Problem is exactly similar to the House Robber problem, but with a twist:
 *
 * The Core Intuition:
 * Problem Twist: The houses are in a circle, meaning house 0 and house n-1 are neighbors. You cannot rob both.
 * The Insight: This "AND" condition is hard to model. We can change it to an "OR" condition. If we can't rob both, it means we must either:
 * Skip the last house and solve the problem for houses 0 to n-2.
 * OR, skip the first house and solve the problem for houses 1 to n-1.
 * The final answer is the maximum of these two independent scenarios. Each scenario is just the original House Robber problem on a sub-array.
 *
 * @author Mohan Sharma
 */
public class HouseRobber2 {

    /**
     * Time Complexity: O(n) - Each state solve(i) is computed only once.
     * Space Complexity: O(n) - For the recursion stack and memo array.
     */
    public int robTopDown(int[] nums) {
        var n = nums.length;
        if (n == 0) {
            return 0; // No houses to rob
        }
        if (n == 1) {
            return nums[0]; // Only one house to rob
        }

        var memo = new int[nums.length + 1];
        Arrays.fill(memo, -1);
        var firstOption = robMemoized(nums, 0, n - 2, memo); // Rob from first to second last

        Arrays.fill(memo, -1); // Reset memo for the second option
        var secondOption = robMemoized(nums, 1, n - 1, memo); // Rob from second to last

        return Math.max(firstOption, secondOption);
    }

    private int robMemoized(int[] nums, int start, int end, int[] memo) {
        if (end < start) {
            return 0; // No houses to rob
        }
        if (memo[end] != -1) {
            return memo[end]; // Return cached result
        }
        var robHouse = nums[end] + robMemoized(nums, start, end - 2, memo);
        var skipHouse = robMemoized(nums, start, end - 1, memo);
        return memo[end] = Math.max(robHouse, skipHouse);
    }

    /**
     * Since the classical Top Down House Robber is very similar to this problem, we will skip that instead we will do the optimised version
     */
    public int robTopDownOptimised(int[] nums) {
        var n = nums.length;
        if (n == 0) return 0; // No houses to rob
        if (n == 1) return nums[0]; // Only one house to rob
        var firstOption = robOptimised(nums, 0, n - 2); // Rob from first to second last
        var secondOption = robOptimised(nums, 1, n - 1); // Rob from second to last
        return Math.max(firstOption, secondOption);
    }

    private int robOptimised(int[] nums, int start, int end) {
        var prevTwo = 0; // Represents dp[i-2]
        var prevOne = 0; // Represents dp[i-1]

        for (var i = start; i <= end; i++) {
            var robHouse = nums[i] + prevTwo; // Rob current house
            var skipHouse = prevOne; // Skip current house
            var currentRob = Math.max(robHouse, skipHouse);
            prevTwo = prevOne;
            prevOne = currentRob;
        }
        return prevOne;
    }

    public static void main(String[] args) {
        var obj = new HouseRobber2();
        System.out.println("Maximum amount robbed: " + obj.robTopDown(new int[]{2, 3, 2})); // Output: 3
        System.out.println("Maximum amount robbed: " + obj.robTopDownOptimised(new int[]{1, 2, 3, 1})); // Output: 4
    }

    /**
     * @author Mohan Sharma
     */
    public static class UniquePaths {

        public int uniquePathsBruteForce(int m, int n) {
            if (m == 0 || n == 0) {
                return 0;
            }
            return uniquePathsBacktracking(m - 1, n - 1); // need to go to top left corner only not end
        }

        private int uniquePathsBacktracking(int m, int n) {
            if (m == 0 && n == 0) {
                return 1;
            }
            if (m < 0 || n < 0) {
                return 0;
            }
            return uniquePathsBacktracking(m - 1, n) + uniquePathsBacktracking(m, n - 1);
        }

        public int uniquePathsBottomUp(int m, int n) {
            if (m == 0 || n == 0) {
                return 0;
            }
            var memo = new int[m][n];
            for (var i = 0; i < m; i++) {
                Arrays.fill(memo[i], -1);
            }
            return uniquePathsMemoized(m - 1, n - 1, memo); // need to go to top left corner only not end
        }

        private int uniquePathsMemoized(int row, int col, int[][] memo) {
            if (row == 0 && col == 0) {
                return 1;
            }
            if (row < 0 || col < 0) {
                return 0;
            }
            if (memo[row][col] != -1) {
                return memo[row][col];
            }
            return memo[row][col] = uniquePathsMemoized(row - 1, col, memo) + uniquePathsMemoized(row, col - 1, memo);
        }

        public int uniquePathsTopDown(int m, int n) {
            if (m <= 0 || n <= 0) {
                return 0;
            }
            var dp = new int[m][n];
            dp[0][0] = 1;

            for (int i = 1; i < m; i++) {
                dp[i][0] = 1; // There's only one way to reach any cell in the first column i.e. from dp[0][0] which is 1
            }
            for (int j = 1; j < n; j++) {
                dp[0][j] = 1; // There's only one way to reach any cell in the first row i.e. from dp[0][0] which is 1
            }

            for (int i = 1; i < m; i++) {
                for (int j = 1; j < n; j++) {
                    dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
                }
            }
            return dp[m - 1][n - 1];
        }

        public int uniquePathsTopDownSpaceOptimized(int m, int n) {
            if (m <= 0 || n <= 0) return 0;

            int[] dp = new int[n];
            // There's only one way to reach any cell in the first row i.e. from dp[0][0] which is 1
            Arrays.fill(dp, 1); // Initialize with the first row values

            // i starts from 1 because we already have the first row
            for (int i = 1; i < m; i++) {
                // j starts from 1 because dp[0] is always 1, we never update dp[0]
                for (int j = 1; j < n; j++) {
                    // new dp[j] = value from left + value from above (old dp[j])
                    dp[j] = dp[j - 1] + dp[j];
                }
            }
            return dp[n - 1];
        }

        public static void main(String[] args) {
            var obj = new UniquePaths();
            //System.out.println(obj.uniquePathsBruteForce(3, 7));
            //System.out.println(obj.uniquePathsBruteForce(3, 2));
            //System.out.println(obj.uniquePathsBottomUp(3, 7));
            //System.out.println(obj.uniquePathsBottomUp(3, 2));
            //System.out.println(obj.uniquePathsTopDown(3, 7));
            //System.out.println(obj.uniquePathsTopDown(3, 2));
            System.out.println(obj.uniquePathsTopDownSpaceOptimized(1, 2));
        }
    }
}
