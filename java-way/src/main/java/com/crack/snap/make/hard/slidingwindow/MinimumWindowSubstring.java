package com.crack.snap.make.hard.slidingwindow;

/**
 * @author Mohan Sharma
 */
public class MinimumWindowSubstring {

    /**
     * Problem: Given two strings s and t of lengths m and n respectively, return the minimum window substring of s
     * such that every character in t (including duplicates) is included in the window.
     * If there is no such substring, return the empty string "".
     *
     * Intuition:
     * Since every character of t must be present in the minimum length substring of s, first thing we need to do is calculate
     * the count of characters in t in an array and a counter variable to keep track of the number of characters in t
     *
     * Next we can scan the string s from left to right using two pointers end and start. As we scan each letter of string s using
     * end pointer we decrement the count of the character in the array, now if the letter is same as the letter of t the value
     * of the count in the array will not be negative. If the letter of s is same as the letter of t we decrement the counter variable
     * using the logic (if dp[ch] > 0: then counter--). If the counter variable which is initialized with the length of t becomes 0
     * then we know we have found a valid substring
     *
     * Now to find minimum length substring than the already found minimum length, we can move the start pointer as long as counter == 0
     * If the start pointer's letter count in the array is > 0 then we know it was the letter of t, we can increment the counter. Since initially the count array
     * will have 0 for all characters except the letters of t and as we scanned the s using the end pointer we decrement the count
     * so for rest of the letters count will be < 0 and for letters of t will be 0
     * and also we can increment the count of the letter in the array
     *
     * @param s
     * @param t
     * @return
     */
    public String minWindow(String s, String t) {
        if (t.isEmpty())
            return "";
        if (s.equals(t))
            return s;
        if (s.length() < t.length())
            return "";

        var start = 0;
        var end = 0;
        var result = "";
        var dp = new int[128]; // 26 size might not work since we need to return the substring instead of length
        var counter = t.length();
        for (var letter: t.toCharArray()) {
            dp[letter]++;
        }

        while (end < s.length()) {
            var ch = s.charAt(end);
            if (dp[ch] > 0) {
                counter--;
            }
            dp[ch]--;
            end++;

            while (counter == 0) {
                if (result.isEmpty() || end - start < result.length()) {
                    result = s.substring(start, end);
                }
                var sch = s.charAt(start);
                dp[sch]++;
                if (dp[sch] > 0) {
                    counter++;
                }
                start++;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        MinimumWindowSubstring obj = new MinimumWindowSubstring();
        System.out.println(obj.minWindow("ADOBECODEBANC", "ABC")); // "BANC"
        System.out.println(obj.minWindow("a", "a")); // "a"
        System.out.println(obj.minWindow("a", "aa")); // ""
        System.out.println(obj.minWindow("aa", "aa")); // "aa"
        System.out.println(obj.minWindow("a", "")); // ""
        System.out.println(obj.minWindow("", "")); // ""
    }
}
