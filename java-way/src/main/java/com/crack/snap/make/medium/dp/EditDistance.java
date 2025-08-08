package com.crack.snap.make.medium.dp;

import java.util.Arrays;

/**
 * @author Mohan Sharma
 */
public class EditDistance {

    public int minDistanceBruteForce(String word1, String word2) {
        return minDistanceBacktracking(word1, 0, word2, 0);
    }

    private int minDistanceBacktracking(String word1, int i, String word2, int j) {
        if (i == word1.length() && j == word2.length()) {
            return 0; // both reached the end of words no more operations needed
        }
        if (i >= word1.length() && j < word2.length()) {
            return word2.length() - j; // word1 reached the end so we need to insert the remaining letters of word2
        }
        if (j >= word2.length() && i < word1.length()) {
            return word1.length() - i; // word2 reached the end so we need to delete the remaining letters of word1
        }
        var count = 0;
        if (word1.charAt(i) == word2.charAt(j)) {
            count = minDistanceBacktracking(word1, i + 1, word2, j + 1);
        } else {
            var insert = 1 + minDistanceBacktracking(word1, i, word2, j + 1);
            var delete = 1 + minDistanceBacktracking(word1, i + 1, word2, j);
            var replace = 1 + minDistanceBacktracking(word1, i + 1, word2, j + 1);
            count = Math.min(java.lang.Math.min(insert, delete), replace);
        }
        return count;
    }

    public int minDistanceTopDown(String word1, String word2) {
        return minDistanceMemoization(word1, 0, word2, 0, new Integer[word1.length()][word2.length()]);
    }

    private int minDistanceMemoization(String word1, int i, String word2, int j, Integer[][] memo) {
        if (i == word1.length() && j == word2.length()) {
            return 0; // both reached the end of words no more operations needed
        }
        if (i >= word1.length() && j < word2.length()) {
            return word2.length() - j; // word1 reached the end so we need to insert the remaining letters of word2
        }
        if (j >= word2.length() && i < word1.length()) {
            return word1.length() - i; // word2 reached the end so we need to delete the remaining letters of word1
        }
        if (memo[i][j] != null) {
            return memo[i][j];
        }
        var count = 0;
        if (word1.charAt(i) == word2.charAt(j)) {
            count =  minDistanceMemoization(word1, i + 1, word2, j + 1, memo);
        } else {
            var insert = 1 + minDistanceMemoization(word1, i, word2, j + 1, memo);
            var delete = 1 + minDistanceMemoization(word1, i + 1, word2, j, memo);
            var replace = 1 + minDistanceMemoization(word1, i + 1, word2, j + 1, memo);
            count = Math.min(java.lang.Math.min(insert, delete), replace);
        }
        return memo[i][j] = count;
    }

    public int minDistanceBottomUp(String word1, String word2) {
        var dp  = new int[word1.length() + 1][word2.length() + 1];

        for (int i = 1; i <= word1.length(); i++) {
            dp[i][0] = i; // first column is represented by characters of word1 and word2 is empty. Meaning we would need ith number of deletions of word1 to match emtpy word2
        }
        for (int j = 1; j <= word2.length(); j++) {
            dp[0][j] = j; // first row is represented by characters of word2 and word1 is empty. Meaning we would need ith number of insertion of word1 to match word2
        }

        for (int i = 1; i <= word1.length(); i++) {
            for (int j = 1; j <= word2.length(); j++) {
                var count = 0;
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    count =  dp[i - 1][j - 1];
                } else {
                    var insert = 1 + dp[i][j - 1];
                    var delete = 1 + dp[i - 1][j];
                    var replace = 1 + dp[i - 1][j - 1];
                    count = Math.min(java.lang.Math.min(insert, delete), replace);
                }
                dp[i][j] = count;
            }
        }
        return dp[word1.length()][word2.length()];
    }

    public static void main(String[] args) {
        var obj = new EditDistance();
        System.out.println(obj.minDistanceBruteForce("horse", "ros"));
        System.out.println(obj.minDistanceBruteForce("intention", "execution"));
        System.out.println(obj.minDistanceTopDown("horse", "ros"));
        System.out.println(obj.minDistanceTopDown("intention", "execution"));
        System.out.println(obj.minDistanceBottomUp("horse", "ros"));
        System.out.println(obj.minDistanceBottomUp("intention", "execution"));
    }
}
