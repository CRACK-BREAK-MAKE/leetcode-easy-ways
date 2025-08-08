package com.crack.snap.make.easy.array;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * @author Mohan Sharma
 */
public class ValidAnagram {

    /**
     * This method checks if two strings are anagrams of each other.
     * It uses an array to count the occurrences of each character in both strings.
     * If the counts match for all characters, the strings are anagrams.
     *
     * Example:
     * String s = "racecar", t = "carrace";
     * isAnagram(s, t) -> true (because "racecar" and "carrace" contain the same characters)
     *
     * String s = "hello", t = "bello";
     * isAnagram(s, t) -> false (because "hello" and "bello" do not contain the same characters)
     *
     * @param s the first string
     * @param t the second string
     * @return true if the strings are anagrams, false otherwise
     */
    public boolean isAnagram(String s, String t) {
        // Check if the lengths of the strings are different
        if (s.length() != t.length()) {
            return false; // If lengths are different, they cannot be anagrams
        }

        // Create an array to count the occurrences of each character
        int[] counter = new int[26];

        // Iterate over each character in the strings
        for (int i = 0; i < s.length(); i++) {
            // Increment the count for the character in the first string
            counter[s.charAt(i) - 'a']++;
            // Decrement the count for the character in the second string
            counter[t.charAt(i) - 'a']--;
        }

        // Check if all counts are zero
        return Arrays.stream(counter).allMatch(data -> data == 0);
    }

    public static void main(String[] args) {
        var anagram = new ValidAnagram();
        System.out.println(anagram.isAnagram("racecar", "carrace"));
        System.out.println(anagram.isAnagram("rat", "car"));
    }
}
