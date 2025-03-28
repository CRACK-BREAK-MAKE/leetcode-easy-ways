package com.crack.snap.make.medium.twopointer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Mohan Sharma
 */
public class ThreeSum {

    /**
     * Problem: Given an integer array nums, return all the triplets [nums[i], nums[j], nums[k]]
     * such that i != j, i != k, and j != k, and nums[i] + nums[j] + nums[k] == 0.
     * and the solution set must not contain duplicate triplets.
     *
     * Intuition:
     * First we can use brute force may be 3 loops starting from i=0 to n-3, j=i+1 to n-2 and k=j+1 to n-1
     * check if nums[i] + nums[j] + nums[k] == 0 return the triplet. also if nums[i] == nums[i+1] skip
     * nums[j] == nums[j+1] skip and nums[k] == nums[k+1] skip to avoid duplicates. The skip logic will work because
     * we iterate from left to right for each loop so if there are two 1's we can take the first 1 and skip the second 1
     * Complexity is o(n^3) and space is o(1)
     *
     * But we can do better, sort the array now I can use one loop from i=0 to n-3 to get the first element and two pointers
     * left = i + 1 and right = n - 1, now check if nums[i] + nums[left] + nums[right] == 0
     * Also to avoid duplicates since same element will be adjacent to each other we can use the above skip logic
     *
     * Time Complexity: O(n^2)
     * - Sorting the array takes O(n log n) and the two-pointer technique takes O(n^2).
     * - Overall, the time complexity is dominated by the O(n^2) part.
     *
     * Space Complexity: O(1)
     * - We use a constant amount of extra space for the pointers and the result list.
     *
     * @param nums the input array of integers
     * @return a list of lists containing all unique triplets that sum up to zero
     */
    public List<List<Integer>> threeSum(int[] nums) {
        if (nums == null || nums.length < 3) {
            return List.of();
        }
        var result = new ArrayList<List<Integer>>();
        Arrays.sort(nums);
        for (var i = 0; i < nums.length - 2; i++) {
            if (i == 0 ||  (i > 0 && (nums[i] != nums[i - 1]))){
                var left = i + 1;
                var right = nums.length - 1;
                var sum = -nums[i];
                while (left < right) {
                    if (nums[left] + nums[right] == sum) {
                        result.add(List.of(nums[i], nums[left], nums[right]));
                        while (left < right && nums[left] == nums[left + 1]) {
                            left++;
                        }
                        while (left < right && nums[right] == nums[right - 1]) {
                            right--;
                        }
                        left++;
                        right--;
                    } else if (nums[left] + nums[right] < sum) {
                        while (left < right && nums[left] == nums[left + 1]) {
                            left++;
                        }
                        left++;
                    } else {
                        while (left < right && nums[right] == nums[right - 1]) {
                            right--;
                        }
                        right--;
                    }
                }
            }
        }
        return result;
    }
}
