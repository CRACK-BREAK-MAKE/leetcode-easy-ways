package com.crack.snap.make.medium.slidingwindow;

import java.util.HashMap;

/**
 * @author Mohan Sharma
 */
public class LongestSubstringWithoutRepeatingCharacter {

    /**
     * Problem: Given a string s, find the length of the longest substring without duplicate characters s = "abba"
     *
     * Intuition:
     * Brute force: Find all substrings and check which is longest without duplicates it will be O(n^2) with O(n) space or O(n^3) time
     * Since we need substring without duplicates we can use two pointer technique or sliding window
     * Have both pointers initially at 0 and move the end pointer till the end of the string
     * Now between this start and end pointer we will a mechanism to keep track of duplicates
     * Here we can use hashing to keep track of the characters and it's index, while moving right if we find a duplicate in the hashmap
     * let's get the index of the duplicate as start and find the length of substring between start and end and keep track of max length
     *
     * Time Complexity: O(n), where n is the length of the string.
     * Space Complexity: O(min(n, m)), where n is the length of the string and m is the size of the character set.
     */
    public int lengthOfLongestSubstring(String s) {
        if (s.length() < 2)
            return s.length();
        var dict = new HashMap<Character, Integer>();
        var start = 0;
        var end = 0;
        var maxLength = 0;
        while (end < s.length()) {
            var ch = s.charAt(end);
            if (dict.containsKey(ch)) {
                // Math.max so we don't go back to the start of the string e.g. abba when we find b start index will be 2 but when we find a for end = 3
                // if we don't use Math.max and simply do start = dict.get(ch) + 1 then start go back to 1 which is wrong
                // Also dict.get(ch) + 1 so we move the index just after the duplicate character
                start = Math.max(start, dict.get(ch) + 1);
            }
            maxLength = Math.max(maxLength, end - start + 1);
            dict.put(ch, end);
            end++;
        }
        return maxLength;
    }

    /**
     * Problem: Given a string s, find the length of the longest substring without duplicate characters s = "abcabcbb"
     *
     * Intuition:
     * We can use the same logic as above but instead of using a hashmap we can use an array of size 128 (ASCII size) to keep track of the characters
     * We can scan the string from left to right, let's have two pointers start = 0 and end = 0 and keep track of the character count in the array,
     * If ever the count of a character is greater than 1 we know there is a duplicate and let's increment the counter variable.
     * If the counter is greater than 0, then we know there is a duplicate in the string. Let's now scan the string again using the start pointer until the counter is 0 again
     * a. If the start pointer's character count is > 0 then we know it is the duplicate and we can decrement the count in the array,
     *    decrement the counter and increment the start pointer as for valid substring the start pointer should be just after the duplicate character
     * b. If the start pointer's character count is 0 then we know it is not the duplicate and we can increment the start pointer
     *    as the duplicate character will be encountered on the right since the counter is still > 0 and for valid substring the start pointer should be just after the duplicate character
     * Now if we think through at any point in time if the counter is 0, the valid substring will be between start and end pointer where end > 0
     *
     * Time Complexity: O(n), where n is the length of the string.
     * Space Complexity: O(1), as we are using a constant amount of extra space.
     */
    public int lengthOfLongestSubstringOptimizedUsingTemplate(String s) {
        if (s.length() < 2)
            return s.length();
        var start = 0;
        var end = 0;
        var counter = 0;
        var maxLength = 0;
        var dp = new int[128]; // 128 is the ascii size or the number of characters in ASCII
        while (end < s.length()) {
            char ch = s.charAt(end);
            if (dp[ch] > 0) {
                counter++;
            }
            dp[ch]++;
            end++;

            while (counter > 0) {
                char sch = s.charAt(start);
                if (dp[sch] > 1) {
                    counter--;
                }
                dp[sch]--;
                start++;
            }
            maxLength = Math.max(maxLength, end - start);
        }
        return maxLength;
    }

    public static void main(String[] args) {
        LongestSubstringWithoutRepeatingCharacter obj = new LongestSubstringWithoutRepeatingCharacter();
        System.out.println(obj.lengthOfLongestSubstring("abcabcbb")); // 3
        System.out.println(obj.lengthOfLongestSubstring("bbbbb")); // 1
        System.out.println(obj.lengthOfLongestSubstring("au")); // 2
        System.out.println(obj.lengthOfLongestSubstring("abba")); // 2
        System.out.println("------------------");
        System.out.println(obj.lengthOfLongestSubstringOptimizedUsingTemplate("abcabcbb")); // 3
        System.out.println(obj.lengthOfLongestSubstringOptimizedUsingTemplate("bbbbb")); // 1
        System.out.println(obj.lengthOfLongestSubstringOptimizedUsingTemplate("au")); // 2
        System.out.println(obj.lengthOfLongestSubstringOptimizedUsingTemplate("abba")); // 2
    }
}
