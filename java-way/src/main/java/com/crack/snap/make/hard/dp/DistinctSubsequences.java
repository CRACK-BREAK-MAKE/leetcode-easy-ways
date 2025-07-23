package com.crack.snap.make.hard.dp;

/**
 * @author Mohan Sharma
 */
public class DistinctSubsequences {

    public int numDistinctBruteForce(String s, String t) {
        if (s.length() < t.length()) {
            return 0;
        }
        return numDistinctBacktracking(s, 0, t, 0);
    }

    private int numDistinctBacktracking(String s, int i, String t, int j) {
        if (j >= t.length()) {
            return 1;
        }
        if (i >= s.length()) {
            return 0;
        }
        // not pick
        var count = numDistinctBacktracking(s, i + 1, t, j);
        if (s.charAt(i) == t.charAt(j)) {
            // pick
            count += numDistinctBacktracking(s, i + 1, t, j + 1);
        }
        return count;
    }

    public int numDistinctTopDown(String s, String t) {
        if (s.length() < t.length()) {
            return 0;
        }
        return numDistinctMemoization(s, 0, t, 0, new Integer[s.length() + 1][t.length() + 1]);
    }

    private int numDistinctMemoization(String s, int i, String t, int j, Integer[][] memo) {
        if (j >= t.length()) {
            return 1;
        }
        if (i >= s.length()) {
            return 0;
        }
        if (memo[i][j] != null) {
            return memo[i][j];
        }
        // not pick
        var count = numDistinctMemoization(s, i + 1, t, j, memo);
        if (s.charAt(i) == t.charAt(j)) {
            // pick
            count += numDistinctMemoization(s, i + 1, t, j + 1, memo);
        }
        return memo[i][j] = count;
    }

    public int numDistinctBottomUp(String s, String t) {
        if (s.length() < t.length()) {
            return 0;
        }
        var dp = new int[s.length() + 1][t.length() + 1];
        for (int i = 0; i <= s.length(); i++) {
            dp[i][0] = 1;
        }
        for (int i = 1; i <= s.length(); i++) {
            for (int j = 1; j <= t.length(); j++) {
                var count = dp[i - 1][j]; // not pick
                if (s.charAt(i - 1) == t.charAt(j - 1)) {
                    count += dp[i - 1][j - 1]; // pick
                }
                dp[i][j] = count;
            }
        }
        return dp[s.length()][t.length()];
    }

    public static void main(String[] args) {
        var obj = new DistinctSubsequences();
        System.out.println(obj.numDistinctBruteForce("rabbbit", "rabbit"));
        System.out.println(obj.numDistinctBruteForce("babgbag", "bag"));
        System.out.println(obj.numDistinctTopDown("rabbbit", "rabbit"));
        System.out.println(obj.numDistinctTopDown("babgbag", "bag"));
        System.out.println(obj.numDistinctBottomUp("rabbbit", "rabbit"));
        System.out.println(obj.numDistinctBottomUp("babgbag", "bag"));
    }
}
