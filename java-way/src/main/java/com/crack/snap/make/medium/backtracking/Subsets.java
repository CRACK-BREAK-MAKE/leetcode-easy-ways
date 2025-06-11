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

    public static void main(String[] args) {
        var obj = new Subsets();
        System.out.println(obj.subsets(new int[]{1, 2}));
    }
}
