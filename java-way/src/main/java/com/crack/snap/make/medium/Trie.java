package com.crack.snap.make.medium;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Mohan Sharma
 */
public class Trie {

    private final TrieNode root;

    public Trie() {
        root = new TrieNode(new HashMap<>(), false);
    }

    public void insert(String word) {
        if (word == null || word.isEmpty()) {
            return; // No need to insert empty strings
        }
        TrieNode current = root;
        for (char c : word.toCharArray()) {
            var dict = current.dict();
            if (!dict.containsKey(c)) {
                dict.put(c, new TrieNode(new HashMap<>(), false));
            }
            current = dict.get(c);
        }
        if (!current.endOfWord) {
            current.endOfWord = true; // Mark the end of the word
        }
    }

    public boolean search(String word) {
        if (word == null || word.isEmpty()) {
            return false; // Empty strings are not considered valid words
        }

        var current = root;
        for (char c : word.toCharArray()) {
            var dict = current.dict();
            if (!dict.containsKey(c)) {
                return false; // Character not found
            }
            current = dict.get(c);
        }
        return current.isEndOfWord(); // Check if we reached the end of a valid word
    }

    public boolean startsWith(String prefix) {
        if (prefix == null || prefix.isEmpty()) {
            return false; // Empty prefixes are not valid
        }

        var current = root;
        for (char c : prefix.toCharArray()) {
            var dict = current.dict();
            if (!dict.containsKey(c)) {
                return false; // Character not found
            }
            current = dict.get(c);
        }
        return true; // If we reach here, the prefix exists
    }

    private class TrieNode {

        private final Map<Character, TrieNode> dict;
        private boolean endOfWord;

        public TrieNode(Map<Character, TrieNode> dict, boolean endOfWord) {
            this.dict = dict;
            this.endOfWord = endOfWord;
        }

        public Map<Character, TrieNode> dict() {
            return dict;
        }

        public boolean isEndOfWord() {
            return endOfWord;
        }
    }
}
