package com.crack.snap.make.medium.backtracking;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mohan Sharma
 */
public class PalindromePartitioning2 {

    // TLE should be solved using DP
    public int minCut(String s) {
        if (s == null || s.isEmpty()) {
            return -1;
        }
        var result = new ArrayList<List<String>>();
        var minCut = new int[1];
        minCut[0] = Integer.MAX_VALUE; // Initialize to a large value
        generatePartitions(s, 0, new ArrayList<>(), minCut);
        return minCut[0];
    }

    private void generatePartitions(String s, int start, List<String> current, int[] minCut) {
        if (start == s.length()) {
            minCut[0] = Math.min(minCut[0], current.size());
            return;
        }

        for (var end = start; end < s.length(); end++) {
            var substring = s.substring(start, end + 1);
            if (isPalindrome(substring)) {
                current.add(substring);
                generatePartitions(s, end + 1, current, minCut);
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
        var obj = new PalindromePartitioning2();
        System.out.println(obj.minCut("aab"));
    }
}
