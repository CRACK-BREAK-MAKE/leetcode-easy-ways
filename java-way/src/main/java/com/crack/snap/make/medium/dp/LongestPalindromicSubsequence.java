package com.crack.snap.make.medium.dp;

/**
 * @author Mohan Sharma
 */
public class LongestPalindromicSubsequence {

    public int longestPalindromeSubseqBruteForce(String s) {
        if (s == null || s.isEmpty()) {
            return 0;
        }
        return solveLongestPalindromeSubseqBruteForce(s, 0, s.length() - 1);
    }

    private int solveLongestPalindromeSubseqBruteForce(String s, int start, int end) {
        if (start == end) {
            return 1;
        }
        if (start > end) {
            return 0;
        }
        if (s.charAt(start) == s.charAt(end)) {
            return 2 + solveLongestPalindromeSubseqBruteForce(s, start + 1, end - 1);
        } else {
            return Math.max(solveLongestPalindromeSubseqBruteForce(s, start + 1, end), solveLongestPalindromeSubseqBruteForce(s, start, end - 1));
        }
    }

    public int longestPalindromeSubseqTopDown(String s) {
        if (s == null || s.isEmpty()) {
            return 0;
        }
        return solveLongestPalindromeSubseqTopDown(s, 0, s.length() - 1, new Integer[s.length()][s.length()]);
    }

    private int solveLongestPalindromeSubseqTopDown(String s, int start, int end, Integer[][] memo) {
        if (start == end) {
            return 1;
        }
        if (start > end) {
            return 0;
        }
        if (memo[start][end] != null) {
            return memo[start][end];
        }
        var result = 0;
        if (s.charAt(start) == s.charAt(end)) {
            result = 2 + solveLongestPalindromeSubseqTopDown(s, start + 1, end - 1, memo);
        } else {
            result = Math.max(
                    solveLongestPalindromeSubseqTopDown(s, start + 1, end, memo),
                    solveLongestPalindromeSubseqTopDown(s, start, end - 1, memo)
            );
        }
        return memo[start][end] = result;
    }

    public int longestPalindromeSubseqBottomUp(String s) {
        if (s == null || s.isEmpty()) {
            return 0;
        }
        int n = s.length();
        var dp = new int[n][n];
        for (int i = 0; i < n; i++) {
            dp[i][i] = 1; // Single character is a palindrome
        }

        for (int length = 2; length <= n; length++) {
            for (int i = 0; i <= n - length; i++) {
                var j = i + length - 1;
                if (s.charAt(i) == s.charAt(j)) {
                    dp[i][j] = 2 + dp[i + 1][j - 1]; // Characters match
                } else {
                    dp[i][j] = Math.max(dp[i + 1][j], dp[i][j - 1]); // Characters do not match
                }
            }
        }
        return dp[0][n - 1];
    }

    public static void main(String[] args) {
        var obj = new LongestPalindromicSubsequence();
        System.out.println(obj.longestPalindromeSubseqBruteForce("badb")); // Output: 3
        System.out.println(obj.longestPalindromeSubseqBruteForce("bbbab")); // Output: 4
        System.out.println(obj.longestPalindromeSubseqBruteForce("cbbd")); // Output: 2
        System.out.println(obj.longestPalindromeSubseqBottomUp("bbbab")); // Output: 4
        System.out.println(obj.longestPalindromeSubseqBottomUp("cbbd")); // Output: 2
    }
}
