package com.crack.snap.make.medium.array;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Mohan Sharma
 */
public class GroupAnagram {

    /**
     * This class provides a method to group anagrams from a list of strings.
     * Intuition:
     * Since we need to group that means we can use hashing, we can use a HashMap where the key is a consistent representation of the characters in the word.
     * One way to generate a consistent key is to sort the characters of the word. Another way is to use a character array
     * where each index represents a character ('a' to 'z') and the value at each index represents the count of that character.
     * Example:
     * Input: ["eat", "tea", "tan", "ate", "nat", "bat"]
     * Output: [["eat", "tea", "ate"], ["tan", "nat"], ["bat"]]
     * The words "eat", "tea", and "ate" are anagrams of each other, so they are grouped together.
     * The words "tan" and "nat" are anagrams of each other, so they are grouped together.
     * The word "bat" has no anagrams in the list, so it forms its own group.
     * Example:
     * Input: ["aaa", "aaa", "cab"]
     * Output: [["aaa", "aaa"], ["cab"]]
     * The words "aaa" and "aaa" are anagrams of each other, so they are grouped together.
     * The word "cab" has no anagrams in the list, so it forms its own group.
     * Example:
     * Input: ["a"]
     * Output: [["a"]]
     * The word "a" has no anagrams in the list, so it forms its own group.
     * Example:
     * Input: []
     * Output: []
     * If the input list is empty, the output is also an empty list.
     *
     * @param strs the array of strings to group
     * @return a list of lists of grouped anagrams
     */
    public List<List<String>> groupAnagrams(String[] strs) {
        if (strs == null || strs.length == 0) {
            return List.of();
        }
        var hashMap = new HashMap<String, List<String>>();
        for ( String word: strs) {
            // the max value a char array can hold in an index is 65535, here since there is a constraint
            // that 0 <= strs[i].length <= 100, even if all the chars of the word are same, the max value will be 100
            char[] chars = new char[26];
            for (char c: word.toCharArray()) {
                chars[c - 'a']++;
            }

            String key = String.valueOf(chars);
            // first way
            //var groupedAnagram = hashMap.getOrDefault(key, List.of());
            //groupedAnagram.add(word);
            //hashMap.put(key, groupedAnagram);
            // second way
            hashMap.computeIfAbsent(key, k -> new ArrayList<>()).add(word);
        }
        return List.copyOf(hashMap.values());
    }

    public static void main(String[] args) {
        var groupAnagram = new GroupAnagram();
        System.out.println(groupAnagram.groupAnagrams(new String[] {"aaa", "aaa", "cab"}));
    }
}
