package com.crack.snap.make.medium.array;

import java.util.HashMap;

/**
 * Longest Subarray With Sum K
 *
 * @author Mohan Sharma
 */
public class LongestSubArrayWithSumK {


    public int subarraySumBruteForce(int[] nums, int k) {
        if (nums == null || nums.length == 0) {
            return -1;
        }
        var maxLength = Integer.MIN_VALUE;
        var n = nums.length;
        for (var i = 0; i < n; i++) {
            var target = k;
            for (var j = i; j < n; j++) {
                target -= nums[j];
                if (target == 0) {
                    maxLength = Math.max(maxLength, j - i + 1);
                }
            }
        }
        return maxLength == Integer.MIN_VALUE ? -1 : maxLength;
    }

    public int subarraySumPrefixSum(int[] nums, int k) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        var maxLength = 0;
        var hash = new HashMap<Integer, Integer>();
        hash.put(0, -1); // to handle the case when the subarray starts from index 0
        var currentSum = 0;

        for (int i = 0; i < nums.length; i++) {
            currentSum += nums[i];
            int prefixSum = currentSum - k;
            if (hash.containsKey(prefixSum)) {
                var startIndex = hash.get(prefixSum);
                maxLength = Math.max(maxLength, i - startIndex);
            }
            hash.putIfAbsent(currentSum, i);
        }
        return maxLength;
    }

    public static void main(String[] args) {
        var obj = new LongestSubArrayWithSumK();
        System.out.println(obj.subarraySumBruteForce(new int[]{10, 5, 2, 7, 1, -10}, 15)); // Output: 6
        System.out.println(obj.subarraySumBruteForce(new int[]{1, 2, 3, 4, 5}, 9)); // Output: 3
        System.out.println(obj.subarraySumPrefixSum(new int[]{94, -33, -13, 40, -82, 94, -33, -13, 40, -82}, 52)); // Output: 6
    }
}
