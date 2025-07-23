package com.crack.snap.make.medium.dp;

import java.util.List;

/**
 * @author Mohan Sharma
 */
public class WordBreak {

    // Can be optimized using dynamic programming or memoization. This approach leads to TLE
    public boolean wordBreakBruteForce(String s, List<String> wordDict) {
        if (s == null || s.isEmpty() || wordDict == null || wordDict.isEmpty()) {
            return false;
        }
        return canBreakBacktracking(s, wordDict, 0);
    }

    private boolean canBreakBacktracking(String s, List<String> wordDict, int start) {
        // since reached the end of the string, it means we can break the whole string into words that
        // are present in the dictionary. But if certain words are not present in the dictionary, e.g for
        // s = "catsandog" when the string is broken into cat, sand and og, then og is not present in the
        // dictionary, so we return false and it never reaches the end of the string.
        if (start == s.length()) {
            return true;
        }
        for (var end = start; end < s.length(); end++) {
            var word = s.substring(start, end + 1);
            if (wordDict.contains(word)) {
                if (canBreakBacktracking(s, wordDict, end + 1)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean wordBreakTopDown(String s, List<String> wordDict) {
        if (s == null || s.isEmpty() || wordDict == null || wordDict.isEmpty()) {
            return false;
        }
        var length = s.length();
        var dp = new boolean[length + 1];

        dp[0] = true; // Base case: empty string can always be segmented
        for (var i = 1; i <= length; i++) {
            for (var j = 0; j < i; j++) {
                if (dp[j] && wordDict.contains(s.substring(j, i))) {
                    dp[i] = true;
                    break;
                }
            }
        }
        return dp[length];
    }

    public static void main(String[] args) {
        var obj = new WordBreak();
        System.out.println(obj.wordBreakTopDown("leetcode", List.of("leet", "code"))); // true
        System.out.println(obj.wordBreakTopDown("applepenapple", List.of("apple", "pen"))); // true
        System.out.println(obj.wordBreakTopDown("catsandog",  List.of("cats", "dog", "sand", "and", "cat"))); // false
    }
}
