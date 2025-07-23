package com.crack.snap.make.medium.backtracking;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mohan Sharma
 */
public class Subsets {

    public List<List<Integer>> subsets(int[] nums) {
        if (nums == null || nums.length == 0) {
            return List.of(List.of());
        }
        var result = new ArrayList<List<Integer>>();
        generateSubsetsUsingBacktracking(nums, 0, new ArrayList<>(), result);
        return result;
    }

    private void generateSubsetsUsingBacktracking(int[] nums, int start, List<Integer> current, List<List<Integer>> result) {
        // clone the current subset and add it to the result. why clone? because we will modify current in the next steps.
        // If we don't clone, the result will always have the last modified state of current.
        result.add(new ArrayList<>(current));

        for (var i = start; i < nums.length; i++) {
            // make a choice
            current.add(nums[i]);
            // explore further with the choice made
            generateSubsetsUsingBacktracking(nums, i + 1, current, result);
            // undo the choice (backtrack)
            current.removeLast();
        }
    }

    /**
     * Time Complexity:
     * The recursion tree has 2 branches at each level (pick and not pick).
     * For an array of size n, the total number of subsets generated is 2^n (all possible combinations of elements).
     * Therefore, the time complexity is O(2^n).
     *
     * Space Complexity:
     * The height of the recursion tree is n because the recursion goes as deep as the length of the array.
     * The current list used to store subsets takes O(n) space in the worst case (when the subset contains all elements).
     * Hence, the space complexity is O(n) (for the recursion stack).
     * @param nums
     * @return
     */
    public List<List<Integer>> subsetsBruteForce(int[] nums) {
        if (nums == null || nums.length == 0) {
            return List.of(List.of());
        }
        var result = new ArrayList<List<Integer>>();
        generateSubsetsPickNotPick(nums, 0, new ArrayList<>(), result);
        return result;
    }

    private void generateSubsetsPickNotPick(int[] nums, int index, List<Integer> current, List<List<Integer>> result) {
        if (index == nums.length) {
            result.add(new ArrayList<>(current));
            return;
        }
        // not pick
        generateSubsetsPickNotPick(nums, index + 1, current, result);
        // pick
        current.add(nums[index]);
        generateSubsetsPickNotPick(nums, index + 1, current, result);
        current.removeLast(); //backtrace
    }

    public static void main(String[] args) {
        var obj = new Subsets();
        System.out.println(obj.subsets(new int[]{1, 2, 3})); // Output [[], [1], [1, 2], [1, 2, 3], [1, 3], [2], [2, 3], [3]]
        System.out.println(obj.subsetsBruteForce(new int[]{1, 2, 3})); // Output [[], [3], [2], [2, 3], [1], [1, 3], [1, 2], [1, 2, 3]]
    }
}
