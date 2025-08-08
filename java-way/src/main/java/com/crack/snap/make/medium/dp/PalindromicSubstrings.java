package com.crack.snap.make.medium.dp;

/**
 * @author Mohan Sharma
 */
public class PalindromicSubstrings {

    public int countSubstringsBruteForce(String s) {
        if (s.isEmpty()) {
            return 0;
        }
        return solveCountSubstringsBruteForce(s, 0);
    }

    private int solveCountSubstringsBruteForce(String s, int start) {
        if (start == s.length()) {
            return 0;
        }
        var count = 0;
        for (int i = start; i < s.length(); i++) {
            var substring = s.substring(start, i + 1);
            if (isPalindrome(substring)) {
                count++;
            }
        }
        return count + solveCountSubstringsBruteForce(s, start + 1);
    }

    public int countSubstringsTopDown(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        var memo = new Boolean[s.length()][s.length()];
        return countPalindromicSubstrings(s, 0, memo);
    }

    private int countPalindromicSubstrings(String s, int start, Boolean[][] memo) {
        if (start >= s.length()) {
            return 0;
        }
        var count = 0;
        for (int end = start; end < s.length(); end++) {
            if (isPalindrome(s, start, end, memo)) {
                count += 1;
            }
        }
        count += countPalindromicSubstrings(s, start + 1, memo);
        return  count;
    }

    private boolean isPalindrome(String s) {
        var start = 0;
        var end = s.length() - 1;
        while (start < end) {
            if (s.charAt(start) != s.charAt(end)) {
                return false;
            }
            start++;
            end--;
        }
        return true;
    }

    private boolean isPalindrome(String s, int start, int end, Boolean[][] memo) {
        if (start >= end) {
            return true;
        }
        if (memo[start][end] != null) {
            return memo[start][end];
        }
        if (s.charAt(start) == s.charAt(end)) {
            return memo[start][end] = isPalindrome(s, start + 1, end - 1, memo);
        }
        return memo[start][end] = false;
    }

    public int countSubstringsBottomUp(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        var count = 0;
        int n = s.length();
        var dp = new boolean[n][n];
        for (var i = 0; i < n; i++) {
            dp[i][i] = true; // single character is a palindrome
            count++; // each single character is a palindrome
        }

        for (int i = 0; i < n - 1; i++) {
            dp[i][i + 1] = (s.charAt(i) == s.charAt(i + 1)); // two characters are a palindrome if they are equal
            if (dp[i][i + 1]) {
                count++; // increment count if we found a palindrome of length 2
            }
        }

        for (var length = 3; length <= n; length++) {
            for (var i = 0; i <= n - length; i++) {
                var j = i + length - 1; // ending index of the substring
                dp[i][j] = (s.charAt(i) == s.charAt(j)) && dp[i + 1][j - 1]; // check if the characters match and the substring between them is a palindrome
                if (dp[i][j]) {
                    count++;
                }
            }
        }
        return count;
    }

    public int countSubstringsExpandAroundCenter(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        var count = 0;
        int n = s.length();
        for (var i = 0; i < n; i++) {
            count += expandAroundCenter(s, i, i); // odd length palindromes
            count += expandAroundCenter(s, i, i + 1); // even length palindromes
        }
        return count;
    }

    private int expandAroundCenter(String s, int start, int end) {
        var count = 0;
        while (start >= 0 && end < s.length() && s.charAt(start) == s.charAt(end)) {
            count++;
            start--;
            end++;
        }
        return count;
    }

    public static void main(String[] args) {
        var palindromicSubstrings = new PalindromicSubstrings();
        System.out.println(palindromicSubstrings.countSubstringsTopDown("abcba")); // Output: 7
        System.out.println(palindromicSubstrings.countSubstringsBottomUp("abcba")); // Output: 7
        System.out.println(palindromicSubstrings.countSubstringsExpandAroundCenter("abcba")); // Output: 7
    }
}
