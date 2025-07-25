package com.crack.snap.make.medium.dp;

/**
 * @author Mohan Sharma
 */
public class DecodeWays {

    /**
     * This problem is a classic example of Pattern 1: Linear DP. The number of ways to decode a string up to a certain point i depends directly on the number of ways to decode the prefixes ending at i-1 and i-2.
     *
     * The Core Idea State Definition: dp[i] = the number of ways to decode the string prefix of length i (i.e., s.substring(0, i)). Our goal is to find dp[n]. Choices: At any point i, we look backward at the last one or two characters to see how they could form a valid decoding. One-Digit Choice: Look at the character s[i-1]. If it's a valid single-digit code (i.e., not '0'), it adds to our total. The number of ways is inherited from the solution for the string up to i-1. (Ways from dp[i-1]). Two-Digit Choice: Look at the characters s[i-2] and s[i-1]. If they form a valid two-digit code (between '10' and '26'), this adds another set of possibilities. The number of ways is inherited from the solution for the string up to i-2. (Ways from dp[i-2]). Recurrence Relation: The total ways dp[i] is the sum of ways from these valid choices. dp[i] = ({ways from 1-digit choice}) + ({ways from 2-digit choice}) Base Case: dp[0] = 1. This "dummy" base case for an empty string signifies there is one
     * way to decode nothing. It's crucial for the recurrence to work correctly from the start.
     *
     * Recursive Flow Diagram Let's trace solve(i) for s = "226". solve(i) means "ways to decode the suffix starting at i". solve(0) /        \ ("2")         /          \ ("22") solve(1)      solve(2) /      \         | ("2")  /        \ ("22") | ("6") solve(2)     solve(3) solve(3) |             |       | ("6")          (end)   (end) |             |       | solve(3)        1       1 | (end) | 1 The diagram shows that solve(2) and solve(3) are computed multiple times, highlighting the need for memoization.
     *
     * So solve(n) = solve(n-1) + solve(n-2) if the last one or two characters are valid decoding.
     */

    public int numDecodingsBruteForce(String s) {
        if (s == null || s.isBlank()) {
            return 0;
        }
        return solveNumDecodingsBruteForce(s, 0); // state: solve(s, i) means number of ways to decode from i to n
    }

    private int solveNumDecodingsBruteForce(String s, int index) {
        if (index >= s.length()) {
            return 1; // successfully decoded the string so count is 1
        }
        if (s.charAt(index) == '0') {
            return 0; // no way to decode starting with zero
        }
        // when we consider 1 digit
        var decodedWays = solveNumDecodingsBruteForce(s, index + 1);

        // when we consider valid 2 digits
        if (index + 1 < s.length()) {
            var twoDigits = Integer.parseInt(s.substring(index, index + 2));
            if (twoDigits <= 26) {
                decodedWays += solveNumDecodingsBruteForce(s, index + 2);
            }
        }
        return decodedWays;
    }

    public int numDecodingsTopDown(String s) {
        if (s == null || s.isBlank()) {
            return 0;
        }
        // we cannot start from last here, suppose the string is 20, if we start from last then 0 is invalid and
        // number of ways be will 1 which is only 20 but if we start from 0, then it will be 2 or 20 two ways to decode
        return solveNumDecodingsMemoization(s, 0, new Integer[s.length()]);
    }

    private int solveNumDecodingsMemoization(String s, int index, Integer[] memo) {
        if (index >= s.length()) {
            return 1; // successfully decoded the string so count is 1
        }
        if (s.charAt(index) == '0') {
            return 0; // no way to decode starting with zero
        }

        if (memo[index] != null) {
            return memo[index]; // return cached value
        }
        // when we consider 1 digit
        var decodedWays = solveNumDecodingsMemoization(s, index + 1, memo);

        // when we consider valid 2 digits
        if (index + 1 < s.length()) {
            var twoDigits = Integer.parseInt(s.substring(index, index + 2));
            if (twoDigits <= 26) {
                decodedWays += solveNumDecodingsMemoization(s, index + 2, memo);
            }
        }
        return memo[index] = decodedWays;
    }

    private int numDecodingsBottomUp(String s) {
        if (s == null || s.isEmpty() || s.charAt(0) == '0') {
            return 0;
        }
        //dp[i] = the number of ways to decode the prefix of the string of length i (i.e., s.substring(0, i))
        var dp = new int[s.length() + 1];
        dp[0] = 1; // empty string there is one way to decode it
        dp[1] = s.charAt(0) == '0' ? 0 : 1; // for a string of length 1 and char at 0 in not '0', there is one way to decode it

        for (int i = 2; i <= s.length(); i++) {
            var decodedWays = 0;
            // when we consider 1 digit
            if (s.charAt(i - 1) != '0') {
                decodedWays += dp[i - 1];
            }
            // when we consider valid 2 digits
            var twoDigits = Integer.parseInt(s.substring(i - 2, i));
            if (10 <= twoDigits && twoDigits <= 26) {
                decodedWays += dp[i - 2];
            }
            dp[i] = decodedWays;
        }
        return dp[s.length()];
    }

    public static void main(String[] args) {
        var obj = new DecodeWays();
        System.out.println(obj.numDecodingsBruteForce("226")); // Output: 3
        System.out.println(obj.numDecodingsBruteForce("12")); // Output: 2
        System.out.println(obj.numDecodingsTopDown("226")); // Output: 3
        System.out.println(obj.numDecodingsTopDown("12")); // Output: 2
        System.out.println(obj.numDecodingsBottomUp("226")); // Output: 3
        System.out.println(obj.numDecodingsBottomUp("12")); // Output: 2
    }
}
