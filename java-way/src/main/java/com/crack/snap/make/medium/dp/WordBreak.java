package com.crack.snap.make.medium.dp;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Mohan Sharma
 */
public class WordBreak {

    /**
     * Time Complexity: O(2^n * n^2).
     * Why is it exponential (2^n)? In the worst-case scenario (like s = "aaaaa" and wordDict = ["a", "aa"]),
     * at each character, you have two choices to make a split there or go to next letter. This creates a decision tree that can
     * grow exponentially, leading to a very large number of recursive calls.
     * The most intuitive way to understand the O(2^n) complexity is to think about the spaces between the letters.
     * For a string of length n, there are n-1 potential places where we could "cut" the string to form words.
     * Let's take s = "catsanddog":
     * c | a | t | s | a | n | d | o | g
     * There are 8 gaps (marked with |). For each gap, our recursive function essentially makes a choice:
     * Cut here: This means the letters before the gap form a word.
     * Don't cut here: This means we need to keep extending the current word.
     * Why n^2? In each of those recursive calls, you have a for loop that runs up to n times. Inside that loop,
     * you create a substring, which can take up to O(n) time. So, the work inside a single call is roughly O(n * n) = O(n^2).
     *
     * Space Complexity: O(n). This is for the maximum depth of the recursive calls on the call stack.
     */
    public boolean wordBreakBruteForce(String s, List<String> wordDict) {
        if (s == null || s.isEmpty() || wordDict == null || wordDict.isEmpty()) {
            return false;
        }
        return solveWordBreakBruteForce(s, new HashSet<>(wordDict), 0);
    }

    private boolean solveWordBreakBruteForce(String s, Set<String> wordDict, int start) {
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
                if (solveWordBreakBruteForce(s, wordDict, end + 1)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Time Complexity: O(n^3).
     * Why? We have n possible starting positions (start from 0 to n-1), so we will only fill our memo array n times.
     * To calculate the result for each start, we have a for loop that runs up to n times. Inside that loop, creating
     * the substring takes up to O(n) time. This gives us three nested levels of work: O(n * n * n) = O(n^3).
     *
     * Space Complexity: O(n). For the memo array and the recursion stack.
     */
    public boolean wordBreakTopDown(String s, List<String> wordDict) {
        if (s == null || s.isEmpty() || wordDict == null || wordDict.isEmpty()) {
            return false;
        }
        return solveWordBreakTopDown(s, new HashSet<>(wordDict), 0, new Boolean[s.length()]);
    }

    private boolean solveWordBreakTopDown(String s, Set<String> wordDict, int start, Boolean[] memo) {
        if (start >= s.length()) {
            return true;
        }
        if (memo[start] != null) {
            return memo[start];
        }
        for (var end = start; end < s.length(); end++) {
            var word = s.substring(start, end + 1);
            if (wordDict.contains(word)) {
                if (solveWordBreakTopDown(s, wordDict, end + 1, memo)) {
                    return memo[start] = true;
                }
            }
        }
        return memo[start] = false;
    }

    /**
     *
     * Time Complexity: O(n^3).
     * Why? This is very similar to the top-down analysis. The outer loop runs n times (for i). The inner loop also
     * runs up to n times (for j). Inside the inner loop, s.substring() takes up to O(n) time. This gives us the same
     * O(n * n * n) = O(n^3) complexity.
     *
     * Space Complexity: O(n). For the dp array.
     */
    public boolean wordBreakBottomUp(String s, List<String> wordDict) {
        if (s == null || s.isEmpty() || wordDict == null || wordDict.isEmpty()) {
            return false;
        }
        var words = new HashSet<>(wordDict);
        var length = s.length();
        var dp = new boolean[length + 1];

        dp[0] = true; // Base case: empty string can always be segmented
        for (var i = 1; i <= length; i++) {
            for (var j = 0; j < i; j++) {
                // example for s=catsandog at i = 7, j = 0, catsand does not exits but for j = 3, sand exists
                // and for this complete string to be true the prefix cat should also exists meaning dp[3] should be true
                if (dp[j] && words.contains(s.substring(j, i))) {
                    dp[i] = true;
                    break;
                }
            }
        }
        return dp[length];
    }

    public static void main(String[] args) {
        var obj = new WordBreak();
        System.out.println(" Brute Force: " + obj.wordBreakBruteForce("leetcode", List.of("leet", "code"))); // true
        System.out.println(" Brute Force: " + obj.wordBreakBruteForce("applepenapple", List.of("apple", "pen"))); // true
        System.out.println(" Brute Force: " + obj.wordBreakBruteForce("catsandog",  List.of("cats", "dog", "sand", "and", "cat"))); // false
        System.out.println(" Top Down: " + obj.wordBreakTopDown("leetcode", List.of("leet", "code"))); // true
        System.out.println(" Top Down: " + obj.wordBreakTopDown("applepenapple", List.of("apple", "pen"))); // true
        System.out.println(" Top Down: " + obj.wordBreakTopDown("catsandog",  List.of("cats", "dog", "sand", "and", "cat"))); // false
        System.out.println(" Bottom Up: " + obj.wordBreakBottomUp("leetcode", List.of("leet", "code"))); // true
        System.out.println(" Bottom Up: " + obj.wordBreakBottomUp("applepenapple", List.of("apple", "pen"))); // true
        System.out.println(" Bottom Up: " + obj.wordBreakBottomUp("catsandog",  List.of("cats", "dog", "sand", "and", "cat"))); // false
    }
}
