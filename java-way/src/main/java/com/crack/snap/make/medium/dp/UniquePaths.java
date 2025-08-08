package com.crack.snap.make.medium.dp;


import java.util.Arrays;

/**
 * @author Mohan Sharma
 */
public class UniquePaths {

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
        return uniquePathsMemoized(m - 1, n - 1, new Integer[m][n]); // need to go to top left corner only not end
    }

    private int uniquePathsMemoized(int row, int col, Integer[][] memo) {
        if (row == 0 && col == 0) {
            return 1;
        }
        if (row < 0 || col < 0) {
            return 0;
        }
        if (memo[row][col] != null) {
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

        // if there is only one column, no matter how many rows are there, there is only one way to reach dp[m][0]
        // as you can go only down
        for (int i = 1; i < m; i++) {
            dp[i][0] = 1;
        }
        // if there is only one row, no matter how many cols are there, there is only one way to reach dp[0][n]
        // as you can go only right
        for (int j = 1; j < n; j++) {
            dp[0][j] = 1;
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
                dp[j] =  dp[j] + dp[j - 1];
            }
        }
        return dp[n - 1];
    }

    public static void main(String[] args) {
        var obj = new UniquePaths();
        //System.out.println(obj.uniquePathsBruteForce(3, 7));
        //System.out.println(obj.uniquePathsBruteForce(3, 2));
        System.out.println(obj.uniquePathsBottomUp(3, 7));
        System.out.println(obj.uniquePathsBottomUp(3, 2));
        System.out.println(obj.uniquePathsTopDown(3, 7));
        System.out.println(obj.uniquePathsTopDown(3, 2));
        System.out.println(obj.uniquePathsTopDownSpaceOptimized(3, 7));
    }
}
