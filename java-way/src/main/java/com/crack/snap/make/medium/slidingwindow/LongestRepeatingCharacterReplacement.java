package com.crack.snap.make.medium.slidingwindow;

/**
 * @author Mohan Sharma
 */
public class LongestRepeatingCharacterReplacement {

    /**
     * Problem: You are given a string s and an integer k.
     * You can choose any character of the string and change it to any other uppercase English character.
     * You can perform this operation at most k times. Return the length of the longest substring containing
     * the same letter you can get after performing the above operations. e.g s = "AABABBA", k = 1 result = 4 either AABA or BABB
     *
     * Intuition:
     * We can brute force with three loops. First loop provides the character from A to Z that we will compare with
     * second loop from 0 to n and third from k = i to n. If character at k did not match with the character from loop one
     * means it is different and we increment the count of different characters. Once this count is greater than k
     * We can calculate the length of the substring and reset the count once i moves to next index and keep track of max length
     * In short we brute force to check how many characters matches A and then B and then C and so on. If character is different increment count and reset loop
     *
     * Time Complexity: O(26 * n^2) = O(n^2), where n is the length of the string.
     * Space Complexity: O(1), as we are using a constant amount of extra space.
     */
    // Result in TLE
    public int characterReplacementBruteForce(String s, int k) {
        if (s.length() < 2)
            return s.length();
        var maxLength = 0;
        for (char c = 'A'; c <= 'Z'; c++) {
            for (var i = 0; i < s.length(); i++) {
                var count = 0;
                var start = i;
                while (start < s.length()) {
                    if (s.charAt(start) != c) {
                        count++;
                    }
                    if (count > k) {
                        break;
                    }
                    maxLength = Math.max(maxLength, start - i + 1);
                    start++;
                }
            }
        }
        return maxLength;
    }

    /**
     * Intuition: for string s = "AABABBA", to find the maximum length of substring having same character but can have k different characters as well
     * That means we are ok as long as end pointer - start pointer - maxCount of the character <= k
     * So we will need two pointers start and end to keep track of the substring and a counter to keep track of the maxCount of the character
     * We can also use a dictionary to keep the character count at each index. If end pointer - start pointer - maxCount of the character becomes greater than k
     * We can shift the start pointer to the right and decrement the count of the character at start pointer
     *
     * Time Complexity: O(n), where n is the length of the string.
     * Space Complexity: O(1), as we are using a constant amount of extra space.
     */
    public int characterReplacement(String s, int k) {
        if (s.length() < 2)
            return s.length();
        var start = 0;
        var end = 0;
        var maxLength = 0;
        var letterMaxCount = 0;
        int[] dp = new int[26];
        while (end < s.length()) {
            var ch = s.charAt(end) - 'A';
            dp[ch]++;
            end++;
            letterMaxCount = Math.max(letterMaxCount, dp[ch]);
            if (end - start - letterMaxCount > k) {
                var sch = s.charAt(start) - 'A';
                dp[sch]--;
                start++;
            }
            maxLength = Math.max(maxLength, end - start);
        }
        return maxLength;
    }

    public static void main(String[] args) {
        LongestRepeatingCharacterReplacement repeatingCharacter = new LongestRepeatingCharacterReplacement();
        System.out.println(repeatingCharacter.characterReplacement("AABABBA", 1));
        System.out.println(repeatingCharacter.characterReplacement("ABAB", 0));
    }
}
