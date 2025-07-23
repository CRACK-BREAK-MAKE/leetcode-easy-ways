package com.crack.snap.make.medium.dp;

import java.util.Arrays;

/**
 * @author Mohan Sharma
 */
public class LongestCommonSubsequence {

    public int longestCommonSubsequenceBruteForce(String text1, String text2) {
        return lcsBacktracking(text1, text2, 0, 0);
    }

    private int lcsBacktracking(String text1, String text2, int i, int j) {
        if (i >= text1.length() || j >= text2.length()) {
            return 0;
        }
        if (text1.charAt(i) == text2.charAt(j)) {
            return 1 + lcsBacktracking(text1, text2, i + 1, j + 1);
        } else {
            return Math.max(lcsBacktracking(text1, text2, i + 1, j), lcsBacktracking(text1, text2, i, j + 1));
        }
    }

    public int longestCommonSubsequenceTopDown(String text1, String text2) {
        var memo = new int[text1.length()][text2.length()];
        for (int i = 0; i < text1.length(); i++) {
            Arrays.fill(memo[i], -1);
        }
        return lcsMemoization(text1, text2, 0, 0, memo);
    }

    private int lcsMemoization(String text1, String text2, int i, int j, int[][] memo) {
        if (i >= text1.length() || j >= text2.length()) {
            return 0;
        }
        if (memo[i][j] != -1) {
            return memo[i][j];
        }
        if (text1.charAt(i) == text2.charAt(j)) {
            return memo[i][j] = 1 + lcsMemoization(text1, text2, i + 1, j + 1, memo);
        } else {
            return memo[i][j] = Math.max(lcsMemoization(text1, text2, i + 1, j, memo), lcsMemoization(text1, text2, i, j + 1, memo));
        }
    }

    public int longestCommonSubsequenceBottomUp(String text1, String text2) {
        int m = text1.length();
        int n = text2.length();
        var dp = new int[m + 1][n + 1];
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        return dp[m][n];
    }

    public static void main(String[] args) {
        var obj = new LongestCommonSubsequence();
        System.out.println(obj.longestCommonSubsequenceBruteForce("abc", "def"));
        System.out.println(obj.longestCommonSubsequenceBruteForce("abc", "abc"));
        System.out.println(obj.longestCommonSubsequenceTopDown("abc", "abc"));
        System.out.println(obj.longestCommonSubsequenceTopDown("abcde", "ace"));
        System.out.println(obj.longestCommonSubsequenceTopDown("abc", "def"));
        System.out.println(obj.longestCommonSubsequenceBottomUp("abcde", "ace"));
        System.out.println(obj.longestCommonSubsequenceBottomUp("abc", "def"));
    }
}
