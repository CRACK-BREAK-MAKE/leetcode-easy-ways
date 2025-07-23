package com.crack.snap.make.medium.dp;

/**
 * @author Mohan Sharma
 */
public class PalindromePartitioning2 {

    public int minCutBruteForce(String s) {
        if (s == null || s.isEmpty()) {
            return -1;
        }
        return generatePartitionsBacktracking(s, 0);
    }

    private int generatePartitionsBacktracking(String s, int start) {
        if (start == s.length()) {
            // why -1 and not 0 since end of string means empty string so min cut required should be 0 right?
            // Instead return -1 to offset the "+ 1" from the last valid partition. e.g. for a string aab if we split to
            // [a, a, b] all are valid palindromes and the count would have been 3, 1 for each string but the actual cut
            // required is 2, so instead of returning 0 for the last piece we return -1 to offset +1
            return -1;
        }

        var minCut = Integer.MAX_VALUE;
        for (var end = start; end < s.length(); end++) {
            var substring = s.substring(start, end + 1);
            if (isPalindrome(substring)) {
                var cuts = 1; // since the string is palindrome hence we found out 1st cut
                minCut = Math.min(minCut, cuts + generatePartitionsBacktracking(s, end + 1));
            }
        }
        return minCut;
    }

    public int minCutTopDown(String s) {
        if (s == null || s.isEmpty()) {
            return -1;
        }
        // state solve(i) means minimum cut required for substring from index i to s.length
        return generatePartitionsMemoization(s, 0, new Integer[s.length()]);
    }

    private int generatePartitionsMemoization(String s, int start, Integer[] memo) {
        if (start == s.length()) {
            // why -1 and not 0 since end of string means empty string so min cut required should be 0 right?
            // Instead return -1 to offset the "+ 1" from the last valid partition. e.g. for a string aab if we split to
            // [a, a, b] all are valid palindromes and the count would have been 3, 1 for each string but the actual cut
            // required is 2, so instead of returning 0 for the last piece we return -1 to offset +1
            return -1;
        }
        if (memo[start] != null) {
            return memo[start];
        }
        var minCut = Integer.MAX_VALUE;
        for (var end = start; end < s.length(); end++) {
            var substring = s.substring(start, end + 1);
            if (isPalindrome(substring)) {
                var cuts = 1;
                minCut = Math.min(minCut, cuts + generatePartitionsMemoization(s, end + 1, memo));
            }
        }
        return memo[start] = minCut;
    }

    public int minCutBottomUp(String s) {
        if (s == null || s.isEmpty()) {
            return -1;
        }
        boolean[][] isPalindrome = buildPalindromicTable(s);
        int n = s.length();
        var dp = new int[n + 1];
        dp[0] = -1;
        for (int i = 1; i <= n; i++) {
            dp[i] = i - 1; // populate the max cut for length 3 max cut can be 2 and so on
            for (var j = 0; j < i; j++) {
                if (isPalindrome[j][i - 1]) {
                    // generatePartitionsMemoization calls with end + 1 means a cut is made in end, let finds the remaining count for
                    // end + 1 to end. But here dp[j] means the pre-calculated minimum cuts needed for the prefix s[0...j-1]
                    dp[i] = Math.min(dp[i], 1 + dp[j]);
                }
            }
        }
        return dp[n];
    }

    private boolean[][] buildPalindromicTable(String s) {
        var isPalindrome = new boolean[s.length()][s.length()];
        for (int i = 0; i < s.length(); i++) {
            // for odd length
            var start = i;
            var end = i;
            while (start >= 0 && end < s.length() && s.charAt(start) == s.charAt(end)) {
                isPalindrome[start--][end++] = true;
            }

            // for even length
            start = i;
            end = i + 1;
            while (start >= 0 && end < s.length() && s.charAt(start) == s.charAt(end)) {
                isPalindrome[start--][end++] = true;
            }
        }
        return isPalindrome;
    }

    private boolean isPalindrome(String s) {
        if (s == null || s.isEmpty()) {
            return false;
        }
        int left = 0, right = s.length() - 1;
        while (left < right) {
            if (s.charAt(left) != s.charAt(right)) {
                return  false;
            }
            left++;
            right--;
        }
        return  true;
    }

    public static void main(String[] args) {
        var obj = new PalindromePartitioning2();
        System.out.println(obj.minCutBruteForce("aa"));
        System.out.println(obj.minCutBruteForce("aab"));
        System.out.println(obj.minCutBruteForce("abc"));
        System.out.println(obj.minCutTopDown("aa"));
        System.out.println(obj.minCutTopDown("aab"));
        System.out.println(obj.minCutTopDown("abc"));
    }
}
