package com.crack.snap.make.medium.slidingwindow;

import java.util.stream.IntStream;

/**
 * @author Mohan Sharma
 */
public class PermutationInString {

    /**
     * Problem: Given two strings s1 and s2, return true if s2 contains a permutation of s1, or false.
     * In other words, one of s1's permutations is the substring of s2.
     *
     * Intuition: When we say permutation meaning same number of characters but can be in any order as long as characters are together
     * That means I need to count the number of characters of the first string
     * Then let me scan the s2 left to right and as soon as the index is equal or greater than the length of s1, we need to check
     * for the candidate substring in s2 if it is valid or not
     * Naive way would be to maintain an array of count of characters in s1 and as we scan the second string left to right
     * we can decrement the character count in the array. When the i >= s1.length() we can re-increment the character count from
     * the start using dp[i - s1.length()] to ensure that we are always checking the s2 string in a window of length s1.length()
     * then we can check if the count of all characters in the array is 0 and return true otherwise at the end of the scan of s2 as false
     *
     * Time Complexity: O(26n), where n is the length of s2
     * Space Complexity: O(1), as we are using a constant amount of extra space.
     *
     * @param s1
     * @param s2
     * @return
     */
    public boolean checkInclusionO26n(String s1, String s2) {
        if (s1.isEmpty())
            return true;
        if (s1.length() > s2.length())
            return false;
        if (s1.equals(s2))
            return true;
        var dp = new int[26];
        for (var c: s1.toCharArray()) {
            dp[c - 'a']++;
        }
        var start = 0;
        var end = 0;
        while (end < s2.length()) {
            int ch = s2.charAt(end) - 'a';
            dp[ch]--;
            end++;

            if (end >= s1.length()) {
                if (IntStream.of(dp).allMatch(i -> i == 0)) {
                    return true;
                }
                int sch = s2.charAt(start) - 'a';
                dp[sch]++;
                start++;
            }
        }
        return false;
    }

    /**
     * The above solution is perfectly fine but we can optimize it further instead of calling IntStream.of(dp).allMatch(i -> i == 0)
     * which in turn checks 26 characters in the array again and again for each character in s2
     * We can maintain a counter variable which will be initialized with s1.length()
     * As we scan the second string we check if the count of the letter of the s2 is greater than 0 in the array i.e. (dp[ch] > 0)
     * If the count is greater than 0, we know that the letter exists in s1 and we can decrement the counter, also we decrement the count of the letter in the array
     * In the valid sliding window i.e. (end >= s1.length) like we did above we check if the counter is 0 return true
     * else we increment the count of the letter of s2 in the array from the start of the window so always a valid window is maintained
     * @param s1
     * @param s2
     * @return
     */
    public boolean checkInclusion(String s1, String s2) {
        if (s1.isEmpty())
            return true;
        if (s1.length() > s2.length())
            return false;
        if (s1.equals(s2))
            return true;
        var dp = new int[26];
        for (var c: s1.toCharArray()) {
            dp[c - 'a']++;
        }
        var start = 0;
        var end = 0;
        var counter = s1.length();
        while (end < s2.length()) {
            int ch = s2.charAt(end) - 'a';
            if (dp[ch] > 0) {
                counter--;
            }
            dp[ch]--;
            end++;

            if (end >= s1.length()) {
                if (counter == 0) return true;
                int sch = s2.charAt(start) - 'a';
                dp[sch]++;
                if (dp[sch] > 0) {
                    counter++;
                }
                start++;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        var obj = new PermutationInString();
        System.out.println(obj.checkInclusion("ab", "eidbaooo")); // true
        System.out.println(obj.checkInclusion("ab", "eidboaoo")); // false
    }
}
