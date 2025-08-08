package com.crack.snap.make.hard.dp;

/**
 * @author Mohan Sharma
 */
public class RegularExpressionMatching {

    public boolean isMatchBruteForce(String s, String p) {
        return isMatchBacktracking(s, 0, p, 0);
    }

    private boolean isMatchBacktracking(String s, int i, String p, int j) {
        if (j == p.length()) {
            // Even if i exhausted does not mean we found the ans, bcs there can be a case where s = "" but p = "a*"
            // this is valid use case because * means 0 or more character
            return i == s.length();
        }
        var firstMatch = i < s.length() && (s.charAt(i) == p.charAt(j) || p.charAt(j) == '.');
        // now make choice if the char at j+1 is *
        if (j + 1 < p.length() && p.charAt(j + 1) == '*') {
            var zeroMatch = isMatchBacktracking(s, i, p, j + 2);
            var oneMatch = firstMatch && isMatchBacktracking(s, i + 1, p, j);
            return zeroMatch || oneMatch;
        } else {
            // if the j + 1 is not * means firstMatch and subproblem of i + 1, j + 1 matches
            return firstMatch && isMatchBacktracking(s, i + 1, p, j + 1);
        }
    }

    public static void main(String[] args) {
        var obj = new RegularExpressionMatching();
        System.out.println(obj.isMatchBruteForce("aa", "a*"));
        System.out.println(obj.isMatchBruteForce("aa", ".*"));
    }
}
