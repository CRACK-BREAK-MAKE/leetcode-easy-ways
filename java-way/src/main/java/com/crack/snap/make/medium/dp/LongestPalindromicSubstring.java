package com.crack.snap.make.medium.dp;

/**
 * @author Mohan Sharma
 */
public class LongestPalindromicSubstring {

    public String longestPalindromeBruteForce(String s) {
        if (s == null || s.isBlank()) {
            return "";
        }
        return solveLongestPalindromeBruteForce(s, 0);
    }

    private String solveLongestPalindromeBruteForce(String s, int start) {
        if (start == s.length()) {
            return "";
        }
        var palindromicSubstring = "";
        for (int end = start; end < s.length(); end++) {
            var substring = s.substring(start, end + 1);
            if (isPalindrome(substring)) {
                palindromicSubstring = substring.length() > palindromicSubstring.length() ? substring : palindromicSubstring;
            }
        }
        var res = solveLongestPalindromeBruteForce(s, start + 1);
        if (res.length() >   palindromicSubstring.length()) {
            palindromicSubstring = res;
        }
        return palindromicSubstring;
    }

    /**
     * Not recommended for large strings due to exponential time complexity.
     * TLE
     */
    public String longestPalindromeBottomUp(String s) {
        var memo = new Boolean[s.length()][s.length()];
        var result = new String[1];
        result[0] = ""; // Initialize with an empty string
        longestPalindromeBruteForceHelper(s, 0, memo, result);
        return result[0];
    }

    private void longestPalindromeBruteForceHelper(String s, int start, Boolean[][] memo, String[] result) {
        if (start == s.length()) {
            return;
        }
        for (int end = start; end < s.length(); end++) {
            if (isPalindrome(s, start, end, memo)) {
                var current = s.substring(start, end + 1);
                if (current.length() > result[0].length()) {
                    result[0] = current;
                }
            }
            longestPalindromeBruteForceHelper(s, end + 1, memo, result);
        }
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
        return false;
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

    /**
     * Intuition Development: Interval DP
     * This problem is a classic example of Pattern 4: Interval DP from the Intuition Doc.
     *
     * Recognition: We are asked for an optimal property (longest) of a substring (an interval). The key insight is
     * that for a string s[i...j] to be a palindrome, two things must be true:
     * The outer characters must match: s[i] == s[j].
     * The inner substring s[i+1...j-1] must also be a palindrome.
     * The solution for a larger interval [i, j] depends on the solution for a smaller, nested interval [i+1, j-1].
     * This dependency on sub-intervals is the tell-tale sign of Interval DP.
     *
     * The DP Approach (Bottom-Up)
     * This is the most intuitive way to understand the flow. We'll build a 2D table to visualize it.
     *
     * State Definition: dp[i][j] will be true if the substring from index i to j is a palindrome, and false otherwise.
     * Recurrence Relation: dp[i][j] = (s.charAt(i) == s.charAt(j)) && dp[i+1][j-1]
     * Base Cases:
     * Any substring of length 1 is a palindrome. dp[i][i] = true.
     * A substring of length 2 is a palindrome if its two characters are the same. dp[i][i+1] = (s.charAt(i) == s.charAt(i+1)).
     *
     * Visualizing the Flow (The "Aha!" Moment)
     * Let's trace this for s = "babad". Our dp table is 5x5.
     * To calculate dp[i][j], we need dp[i+1][j-1]. In the table, this value is down and to the left.
     * This means we can't fill the table row-by-row or column-by-column. We must fill it by increasing substring
     * length (diagonally).
     *
     * dp[i][j] needs dp[i+1][j-1] (value is down-left)
     *
     *   j=0  1  2  3  4
     * i=0  T  ?
     * i=1     T
     * i=2        T  <-- To find dp[0][2], we need dp[1][1]
     * i=3           T
     * i=4              T
     * Step 1: Fill for length 1 (The main diagonal)
     * All dp[i][i] are true.
     * dp[0][0]=T, dp[1][1]=T, dp[2][2]=T, dp[3][3]=T, dp[4][4]=T.
     * Longest so far: "b"
     *
     * Step 2: Fill for length 2
     * dp[0][1] ("ba"): b != a -> F
     * dp[1][2] ("ab"): a != b -> F
     * dp[2][3] ("ba"): b != a -> F
     * dp[3][4] ("ad"): a != d -> F Longest so far: "b"
     *
     * Step 3: Fill for length 3
     * dp[0][2] ("bab"): s[0]==s[2] ('b'=='b') AND dp[1][1] is T. So, dp[0][2] is T.
     * We found a palindrome of length 3! Longest so far: "bab".
     * dp[1][3] ("aba"): s[1]==s[3] ('a'=='a') AND dp[2][2] is T. So, dp[1][3] is T.
     * Another palindrome of length 3. Longest so far: "aba" (or "bab", same length).
     * dp[2][4] ("bad"): s[2]!=s[4] -> F.
     *
     * Step 4: Fill for length 4 ...and so on.
     * We continue this process, and every time we set dp[i][j] to true, we check if j - i + 1 is greater than
     * our current max length.
     */
    public String longestPalindromeTopDown(String s) {
        if (s == null || s.isEmpty() || s.length() == 1) {
            return s; // Single character or empty string is a palindrome
        }
        var n = s.length();
        boolean[][] dp = new boolean[n][n];
        var result = s.substring(0, 1); // Initialize result with the first character

        for (var i = 0; i < n; i++) {
            dp[i][i] = true; // Single character is a palindrome
        }

        for (var i = 0; i < n - 1; i++) {
            dp[i][i + 1] = s.charAt(i) == s.charAt(i + 1);
            if (dp[i][i + 1]) {
                result = s.substring(i, i + 2);
            }
        }
        for (var length = 3; length <= n; length++) {
            for (var i = 0; i <= n - length; i++) {
                var j = i + length - 1; // Ending index of the substring
                dp[i][j] = (s.charAt(i) == s.charAt(j)) && dp[i + 1][j - 1]; // Check if the characters match and the substring between them is a palindrome
                if (dp[i][j]) {
                    var substring = s.substring(i, j + 1);
                    if (substring.length() > result.length()) {
                        result = substring; // Update result if we found a longer palindrome
                    }
                }
            }
        }
        return result;
    }

    public String longestPalindromeExpandAroundCenter(String s) {
        if (s == null || s.isEmpty() || s.length() == 1) {
            return s; // Single character or empty string is a palindrome
        }
        var n = s.length();
        var result = s.substring(0, 1);

        for (var i = 0; i < n; i++) {
            var oddLengthPalindrome = expandAroundCenter(s, i, i); // Odd length palindromes
            var evenLengthPalindrome = expandAroundCenter(s, i, i + 1); // Even length palindromes

            var longerPalindrome = oddLengthPalindrome.length() > evenLengthPalindrome.length() ? oddLengthPalindrome : evenLengthPalindrome;
            if (longerPalindrome.length() > result.length()) {
                result = longerPalindrome; // Update result if we found a longer palindrome
            }
        }
        return result;
    }

    private String expandAroundCenter(String s, int start, int end) {
        while (start >=0 && end < s.length() && s.charAt(start) == s.charAt(end)) {
            start--;
            end++;
        }
        return s.substring(start + 1, end);
    }

    public static void main(String[] args) {
        var obj = new LongestPalindromicSubstring();
        System.out.println(obj.longestPalindromeBruteForce("babad")); // Output: "bab" or "aba"
        System.out.println(obj.longestPalindromeBruteForce("cbbd")); // Output: "bb"
        System.out.println(obj.longestPalindromeTopDown("babad")); // Output: "bab" or "aba"
        System.out.println(obj.longestPalindromeTopDown("cbbd")); // Output: "bb"
        System.out.println(obj.longestPalindromeTopDown("a")); // Output: "a"
        System.out.println(obj.longestPalindromeTopDown("ac")); // Output: "a" or "c"
        System.out.println(obj.longestPalindromeExpandAroundCenter("babad")); // Output: "bab" or "aba"
        System.out.println(obj.longestPalindromeExpandAroundCenter("cbbd")); // Output: "bb"
        System.out.println(obj.longestPalindromeExpandAroundCenter("a")); // Output: "a"
    }
}
