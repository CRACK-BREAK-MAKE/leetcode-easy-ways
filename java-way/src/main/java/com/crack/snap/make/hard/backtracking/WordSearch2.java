package com.crack.snap.make.hard.backtracking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Mohan Sharma
 */
public class WordSearch2 {


    public List<String> findWordsBruteForce(char[][] board, String[] words) {
        if (board == null || words == null || words.length == 0 || board.length == 0 || board[0].length == 0) {
            return List.of();
        }

        var result = new ArrayList<String>();
        for (var word : words) {
            var found = false;
            for (var row = 0; row < board.length && !found; row++) {
                for (var col = 0; col < board[0].length && !found; col++) {
                    if (searchRecursively(board, word, row, col, 0)) {
                        result.add(word);
                        found = true; // Word found, no need to search further
                    }
                }
            }
        }
        return result;
    }

    private boolean searchRecursively(char[][] board, String word, int row, int col, int index) {
        if (index == word.length()) {
            return  true;
        }

        if (row < 0 || row >= board.length || col < 0 || col >= board[0].length ||
                board[row][col] != word.charAt(index)) {
            return false;
        }

        var character = board[row][col];
        board[row][col] = '*'; // Mark as visited
        boolean found = searchRecursively(board, word, row + 1, col, index + 1) ||
                        searchRecursively(board, word, row - 1, col, index + 1) ||
                        searchRecursively(board, word, row, col + 1, index + 1) ||
                        searchRecursively(board, word, row, col - 1, index + 1);
        board[row][col] = character; // Restore the original character
        return found;
    }

    /**
     *
     * The Inefficiency of the brute force Approach
     * Imagine your board contains the path C -> A -> T and your word list is ["CAT", "CATTLE", "CAR"].
     *
     * Your current algorithm will:
     * Search for "CAT": It will traverse the board C -> A -> T. Success.
     * Search for "CATTLE": It will again traverse C -> A -> T, and then continue searching from there.
     * Search for "CAR": It will again traverse C -> A, and then explore a different path to find R.
     * You are re-doing the same work over and over. The traversal for the shared prefix "CA" is repeated for all three words.
     * This is the problem we need to solve using Trie implementation.
     *
     * Trie a tree structure where each path from the root to a node represents a common prefix. Words that share prefixes also
     * share the same path in the tree.
     * Let's visualize a Trie for ["CAT", "CATTLE", "CAR"]:
     *
     *       (root)
     *        /
     *       C
     *      /
     *     A
     *    / \
     *   T* R*
     *   |
     *   T
     *   |
     *   L
     *   |
     *   E*
     *
     * (* = marks the end of a valid word)
     * Instead of searching for one word at a time, we can traverse the board and the Trie at the same time. As we move from one
     * character to the next on the board, we move from one node to the next in the Trie. Why this is efficient?
     * * Because if we find a character that doesn't exist in Trie, we can immediately stop searching.
     * * Even though we might scan the entire board, we only traverse for the characters that are part of the words in the Trie, since
     *   the trie was constructed from the words we are searching for. This way, we avoid unnecessary traversals and checks
     *   for words that don't match the current path in the Trie.
     */
    public List<String> findWords(char[][] board, String[] words) {
        if (board == null || words == null || words.length == 0 || board.length == 0 || board[0].length == 0) {
            return List.of();
        }
        var root = new TrieNode();
        for (var word : words) {
            insertWordToTrie(root, word);
        }
        var result = new ArrayList<String>();
        for (var row = 0; row < board.length; row++) {
            for (var col = 0; col < board[0].length; col++) {
                searchInTrie(board, row, col, root, result);
            }
        }
        return result;
    }

    private void searchInTrie(char[][] board, int row, int col, TrieNode parent, List<String> result) {
        if (row < 0 || row >= board.length || col < 0 || col >= board[0].length || board[row][col] == '*') {
            return;
        }

        var character = board[row][col];
        var currentNode = parent.dict.get(character);

        if (currentNode == null) {
            return; // No matching character in Trie, stop searching
        }

        if (currentNode.word != null) {
            result.add(currentNode.word); // Found a valid word
            currentNode.word = null; // Avoid duplicate entries
        }
        board[row][col] = '*'; // Mark as visited
        searchInTrie(board, row + 1, col, currentNode, result);
        searchInTrie(board, row - 1, col, currentNode, result);
        searchInTrie(board, row, col + 1, currentNode, result);
        searchInTrie(board, row, col - 1, currentNode, result);
        board[row][col] = character; // Restore the original character
    }

    private void insertWordToTrie(final TrieNode root, final String word) {
        var current = root;
        for (var ch :  word.toCharArray()) {
            current.dict.computeIfAbsent(ch, k -> new TrieNode());
            current = current.dict.get(ch);
        }
        current.word = word; // Mark the end of the word
    }

    private class TrieNode {
        private Map<Character, TrieNode> dict = new HashMap<>();
        private String word = null;
    }

    public static void main(String[] args) {
        var obj = new WordSearch2();
        // board = [['o','a','a','n'],['e','t','a','e'],['i','h','k','r'],['i','f','l','v']], words = ["oath","pea","eat","rain"]
        System.out.println(obj.findWords(
                new char[][]{ {'o','a','a','n'}, {'e','t','a','e'}, {'i','h','k','r'}, {'i','f','l','v'}},
                new String[] {"oath","pea","eat","rain"}
                ));
    }
}
