package com.crack.snap.make.medium.dp;

import java.util.Arrays;

/**
 * @author Mohan Sharma
 */
public class DecodeWays {

    /**
     * This problem is a classic example of Pattern 1: Linear DP. The number of ways to decode a string up to a certain
     * point i depends directly on the number of ways to decode the prefixes ending at i-1 and i-2.
     *
     * The Core Idea
     * State Definition: dp[i] = the number of ways to decode the string prefix of length i (i.e., s.substring(0, i)). Our goal is to find dp[n].
     * Choices: At any point i, we look backward at the last one or two characters to see how they could form a valid decoding.
     * One-Digit Choice: Look at the character s[i-1]. If it's a valid single-digit code (i.e., not '0'), it adds to our total. The number of ways is inherited from the solution for the string up to i-1. (Ways from dp[i-1]).
     * Two-Digit Choice: Look at the characters s[i-2] and s[i-1]. If they form a valid two-digit code (between '10' and '26'), this adds another set of possibilities. The number of ways is inherited from the solution for the string up to i-2. (Ways from dp[i-2]).
     * Recurrence Relation: The total ways dp[i] is the sum of ways from these valid choices. dp[i] = ({ways from 1-digit choice}) + ({ways from 2-digit choice})
     * Base Case: dp[0] = 1. This "dummy" base case for an empty string signifies there is one way to decode nothing. It's crucial for the recurrence to work correctly from the start.
     *
     * Recursive Flow Diagram
     * Let's trace solve(i) for s = "226". solve(i) means "ways to decode the suffix starting at i".
     *                  solve(0)
     *                 /        \
     *  ("2")         /          \ ("22")
     *             solve(1)      solve(2)
     *            /      \         |
     *    ("2")  /        \ ("22") | ("6")
     *       solve(2)     solve(3) solve(3)
     *         |             |       |
     *       ("6")          (end)   (end)
     *         |             |       |
     *       solve(3)        1       1
     *         |
     *        (end)
     *         |
     *         1
     * The diagram shows that solve(2) and solve(3) are computed multiple times, highlighting the need for memoization.
     *
     * So solve(n) = solve(n-1) + solve(n-2) if the last one or two characters are valid decoding.
     */
    public int numDecodings(String s) {
        if (s == null || s.isEmpty() || s.charAt(0) == '0') {
            return 0;
        }
        var memo = new Integer[s.length()];
        return numDecodingsBacktracking(s, 0, memo);
    }

    private int numDecodingsBacktracking(String s, int index, Integer[] memo) {
        if (index == s.length()) {
            return 1;
        }
        if (s.charAt(index) == '0') {
            return 0; // No valid encoding starts with '0'
        }
        if (memo[index] != null) {
            return memo[index]; // Return cached result
        }

        // Single digit decoding, since it is not '0', it is valid 1 to 9
        var result = numDecodingsBacktracking(s, index + 1, memo);

        // Check for two digit decoding
        if (index + 1 < s.length()) {
            var twoDigit = Integer.parseInt(s.substring(index, index + 2));
            if (twoDigit <= 26) {
                // Two digit decoding is valid if it is between 10 and 26
                result += numDecodingsBacktracking(s, index + 2, memo);
            }
        }
        return memo[index] = result;
    }

    private int numDecodingBottomUp(String s) {
        if (s == null || s.isEmpty() || s.charAt(0) == '0') {
            return 0;
        }
        int length = s.length();
        var dp = new int[length + 1];

        // Base case: an empty string has one way to decode (do nothing)
        dp[0] = 1;
        // Base case: single character decoding, if it is not '0' then it has 1 way to decode
        dp[1] = s.charAt(0) != '0' ? 1 : 0;

        // As per recurrence relation, dp[i] = dp[i-1] + dp[i-2] if the last one or two characters are valid decoding
        for (var i = 2; i <= length; i++) {
            var oneDigit = s.charAt(i - 1) - '0';
            var twoDigit = Integer.parseInt(s.substring(i - 2, i));

            if (oneDigit >= 1) {
                dp[i] += dp[i - 1]; // Valid single digit decoding
            }
            if (twoDigit >= 10 && twoDigit <= 26) {
                dp[i] += dp[i - 2]; // Valid two digit decoding
            }
        }
        return dp[length]; // The result is the number of ways to decode the entire string
    }

    public static void main(String[] args) {
        var obj = new DecodeWays();
        System.out.println(obj.numDecodings("226")); // Output: 3
        System.out.println(obj.numDecodings("12")); // Output: 2
        System.out.println(obj.numDecodings("1106")); // Output: 2
        System.out.println(obj.numDecodingBottomUp("11106")); // Output: 1

    }
}
