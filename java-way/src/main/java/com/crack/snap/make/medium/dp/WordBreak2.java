package com.crack.snap.make.medium.dp;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * @author Mohan Sharma
 */
public class WordBreak2 {

    public List<String> wordBreak(String s, List<String> wordDict) {
        if (s == null || s.isEmpty() || wordDict == null || wordDict.isEmpty()) {
            return List.of();
        }
        var result = new ArrayList<String>();
        breakUsingBacktracking(s, new HashSet<>(wordDict), 0, new ArrayList<>(), result);
        return result;
    }

    private void breakUsingBacktracking(String s, HashSet<String> wordDict, int start, List<String> current, List<String> result) {
        if (start == s.length()) {
            result.add(String.join(" ", current));
            return;
        }
        for (var end = start; end < s.length(); end++) {
            var word = s.substring(start, end + 1);
            if (wordDict.contains(word)) {
                current.add(word);
                breakUsingBacktracking(s, wordDict, end + 1, current, result);
                current.removeLast(); // backtrack
            }
        }
    }

    public static void main(String[] args) {
        var obj = new WordBreak2();
        var wordDict = List.of("cats", "dog", "sand", "and", "cat");
        System.out.println(obj.wordBreak("catsanddog", wordDict)); // ["cats and dog", "cat sand dog"]
        wordDict = List.of("apple", "pen");
        System.out.println(obj.wordBreak("applepenapple", wordDict)); // ["apple pen apple"]
    }
}
