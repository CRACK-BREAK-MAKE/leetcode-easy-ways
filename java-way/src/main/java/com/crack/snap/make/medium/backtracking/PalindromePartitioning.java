package com.crack.snap.make.medium.backtracking;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mohan Sharma
 */
public class PalindromePartitioning {

    public List<List<String>> partition(String s) {
        if (s == null || s.isEmpty()) {
            return List.of();
        }
        var result = new ArrayList<List<String>>();
        generatePartitions(s, 0, new ArrayList<>(), result);
        return result;
    }

    private void generatePartitions(String s, int start, List<String> current, List<List<String>> result) {
        if (start == s.length()) {
            result.add(new ArrayList<>(current));
            return;
        }

        for (var end = start; end < s.length(); end++) {
            var substring = s.substring(start, end + 1);
            if (isPalindrome(substring)) {
                current.add(substring);
                generatePartitions(s, end + 1, current, result);
                current.removeLast();
            }
        }
    }

    private boolean isPalindrome(String s) {
        if (s == null || s.isEmpty()) {
            return false;
        }
        int left = 0, right = s.length() - 1;
        while (left < right) {
            if (s.charAt(left) != s.charAt(right)) {
                return  false;
            }
            left++;
            right--;
        }
        return  true;
    }

    public static void main(String[] args) {
        var obj = new PalindromePartitioning();
        System.out.println(obj.partition("a"));
    }
}
