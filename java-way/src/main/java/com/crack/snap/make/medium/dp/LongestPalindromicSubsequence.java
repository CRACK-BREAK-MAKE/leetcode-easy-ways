package com.crack.snap.make.medium.dp;

/**
 * @author Mohan Sharma
 */
public class LongestPalindromicSubsequence {

    public int longestPalindromeSubseqBruteForce(String s) {
        if (s == null || s.isEmpty()) {
            return 0;
        }
        var result = new int[1];
        longestPalindromeSubseBacktracking(s, 0, "", result);
        return result[0];
    }

    private void longestPalindromeSubseBacktracking(String s, int index, String sequence, int[] result) {
        if (index == s.length()) {
            return;
        }

        for (int i = index; i < s.length(); i++) {
            var newSequence = sequence + s.charAt(i);
            if (isPalindrome(newSequence)) {
                result[0] = Math.max(result[0], newSequence.length());
            }
            longestPalindromeSubseBacktracking(s, i + 1, newSequence, result);
        }
    }

    private boolean isPalindrome(String newSequence) {
        var start = 0;
        var end = newSequence.length() - 1;
        while (start < end) {
            if (newSequence.charAt(start) != newSequence.charAt(end)) {
                return false;
            }
            start++;
            end--;
        }
        return true;
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
