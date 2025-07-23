package com.crack.snap.make.medium.dp;

/**
 * @author Mohan Sharma
 */
public class InterleavingString {

    public boolean isInterleaveBruteForce(String s1, String s2, String s3) {
        if (s1.length() + s2.length() != s3.length()) {
            return false;
        }
        return isInterleaveBacktracking(s1, 0, s2, 0, s3);
    }

    private boolean isInterleaveBacktracking(String s1, int i, String s2, int j, String s3) {
        // since a valid interleaving means every character from s1 and s2 is used exactly once,
        // the pointer for s3 will always be k = i + j. Therefore, we only need to track two pointers, i and j
        var k = i + j;

        // Base Case: We have successfully used all characters from s1 and s2
        if (k == s3.length()) {
            return true;
        }

        var possibleFromFirstString = false;
        if (i < s1.length() && s1.charAt(i) == s3.charAt(k)) {
            possibleFromFirstString = isInterleaveBacktracking(s1, i + 1, s2, j, s3);
        }
        if (possibleFromFirstString) {
            return  true;
        }

        var possibleFromSecondString = false;
        if (j < s2.length() && s2.charAt(j) == s3.charAt(k)) {
            possibleFromSecondString = isInterleaveBacktracking(s1, i, s2, j + 1, s3);
        }
        return possibleFromSecondString;
    }

    public boolean isInterleaveTopDown(String s1, String s2, String s3) {
        var m = s1.length();
        var n = s2.length();
        if (m + n != s3.length()) {
            return false;
        }
        return isInterleaveMemoization(s1, 0, s2, 0, s3, new Boolean[m + 1][n + 1]);
    }

    private boolean isInterleaveMemoization(String s1, int i, String s2, int j, String s3, Boolean[][] memo) {
        var k = j + i;
        if (k == s3.length()) {
            return true;
        }
        if (memo[i][j] != null) {
            return memo[i][j];
        }
        var possibleFromFirstString = false;
        if (i < s1.length() && s1.charAt(i) == s3.charAt(k)) {
            possibleFromFirstString = isInterleaveMemoization(s1, i + 1, s2, j, s3, memo);
        }
        var possibleFromSecondString = false;
        if (j < s2.length() && s2.charAt(j) == s3.charAt(k)) {
            possibleFromSecondString = isInterleaveMemoization(s1, i, s2, j + 1, s3, memo);
        }
        return memo[i][j] = possibleFromFirstString || possibleFromSecondString;
    }


    public boolean isInterleaveBottomUp(String s1, String s2, String s3) {
        var m = s1.length();
        var n = s2.length();
        if (m + n != s3.length()) {
            return false;
        }
        var dp = new boolean[m + 1][n + 1];
        dp[0][0] = true;
        for (int j = 1; j <= n; j++) {
            dp[0][j] = dp[0][j - 1] && s2.charAt(j - 1) == s3.charAt(j - 1);
        }

        for (int i = 1; i <= m; i++) {
            dp[i][0] = dp[i - 1][0] && s1.charAt(i - 1) == s3.charAt(i - 1);
        }

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                var k = i + j - 1;
                dp[i][j] = (dp[i-1][j] && s1.charAt(i-1) == s3.charAt(k)) || (dp[i][j-1] && s2.charAt(j-1) == s3.charAt(k));
            }
        }
        return dp[m][n];
    }

    public static void main(String[] args) {
        var obj = new InterleavingString();
        System.out.println(obj.isInterleaveBruteForce("aabcc", "dbbca", "aadbbcbcac"));
        System.out.println(obj.isInterleaveBruteForce("aabcc", "dbbca", "aadbbbaccc"));
        System.out.println(obj.isInterleaveTopDown("aabcc", "dbbca", "aadbbcbcac"));
        System.out.println(obj.isInterleaveTopDown("aabcc", "dbbca", "aadbbbaccc"));
        System.out.println(obj.isInterleaveBottomUp("aabcc", "dbbca", "aadbbcbcac"));
        System.out.println(obj.isInterleaveBottomUp("aabcc", "dbbca", "aadbbbaccc"));
    }
}
