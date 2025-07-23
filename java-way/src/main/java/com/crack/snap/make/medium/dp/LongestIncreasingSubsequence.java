package com.crack.snap.make.medium.dp;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Mohan Sharma
 */
public class LongestIncreasingSubsequence {

    /**
     * The most fundamental way to think about LIS is to make a decision for each element: either we include it in our subsequence, or we don't. To make this decision, we need to know what the previous element we took was, so we can check the "increasing" property. This leads to a recursive function with this signature: solve(previous_element_value, current_index).
     *
     * State: The state is defined by two things: the index of the element we are currently considering (current_index) and the value of the element we previously included (previous_element_value). Choices: At current_index, we have two choices: Include nums[current_index]: We can only do this if nums[current_index] > previous_element_value. The length of the LIS from this choice is 1 + solve(nums[current_index], current_index + 1). Skip nums[current_index]: We don't include it. The LIS length is solve(previous_element_value, current_index + 1). Recurrence: The answer is the max of these two choices. Base Case: If current_index == nums.length, we can't add any more elements, so we return 0.
     */
    public int lengthOfLISBruteForce(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        return lisBacktrack(nums, -1, 0);
    }

    private int lisBacktrack(int[] nums, int prevIndex, int index) {
        if (index == nums.length) {
            return 0;
        }
        var pick = 0;
        var notPick = lisBacktrack(nums, prevIndex, index + 1);
        if (prevIndex == -1 || nums[prevIndex] < nums[index]) {
            pick = 1 + lisBacktrack(nums, index, index + 1);
        }
        return Math.max(pick, notPick);
    }

    public int lengthOfLISTopDown(int[] nums) {
        int length = nums.length;
        var memo = new int[length + 1][length];
        for (int[] row : memo) {
            Arrays.fill(row, -1);
        }
        return lengthOfLisMemoization(nums, -1, 0, memo);
    }

    private int lengthOfLisMemoization(int[] nums, int prevIndex, int index, int[][] memo) {
        if (index == nums.length) {
            return 0;
        }
        if (memo[prevIndex + 1][index] != -1) {
            return memo[prevIndex + 1][index];
        }
        var notPick = lengthOfLisMemoization(nums, prevIndex, index + 1, memo);
        var pick = 0;
        if (prevIndex == -1 || nums[prevIndex] < nums[index]) {
            pick = 1 + lengthOfLisMemoization(nums, index, index + 1, memo);
        }
        return memo[prevIndex + 1][index] = Math.max(pick, notPick);
    }

    public int lengthOfLISBottomUp(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        var dp = new int[nums.length];
        Arrays.fill(dp, 1);
        var lis = 1; // every number is lis of length 1
        for (var i = 1; i < nums.length; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]) {
                    dp[i] = Math.max(dp[i], 1 + dp[j]);
                    lis = Math.max(lis, dp[i]);
                }
            }
        }
        return lis;
    }

    public int lengthOfLISBinarySearchTails(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        var tails = new int[nums.length];
        var index = 0;
        tails[index++] = nums[0];

        for (int i = 1; i < nums.length; i++) {
            if (nums[i] > tails[index - 1]) {
                tails[index++] = nums[i];
            } else {
                var replaceIndex = binarySearch(tails, 0, index, nums[i]);
                tails[replaceIndex] = nums[i];
            }
        }
        return index;
    }

    private int binarySearch(int[] nums, int start, int end, int target) {
        while (start <= end) { // narrow down instead of exact match
            var mid = start + ((end - start) / 2);
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] < target) {
                start = mid + 1;
            } else {
                end = mid - 1;
            }
        }
        return start;
    }

/*    private void lisBacktrack(int[] nums, int index, List<Integer> current, int[] result) {
        if (nums.length == index) {
            result[0] = Math.max(result[0], current.size());
            return;
        }

        for (var i = index; i < nums.length; i++) {
            if (current.isEmpty() || current.getLast() < nums[i]) {
                current.add(nums[i]);
                lisBacktrack(nums, i + 1, current, result);
                current.removeLast();
            }
        }
    }*/

    public static void main(String[] args) {
        var obj = new LongestIncreasingSubsequence();
        System.out.println(obj.lengthOfLISBruteForce(new int[]{10, 9, 2, 5, 3, 7, 101, 18})); // Output 4
        System.out.println(obj.lengthOfLISTopDown(new int[]{10, 9, 2, 5, 3, 7, 101, 18})); // Output 4
        System.out.println(obj.lengthOfLISBottomUp(new int[]{10, 9, 2, 5, 3, 7, 18}));
        System.out.println(obj.lengthOfLISBinarySearchTails(new int[]{10, 9, 2, 5, 3, 7, 18}));
    }
}
