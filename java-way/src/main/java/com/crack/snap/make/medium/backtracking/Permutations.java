package com.crack.snap.make.medium.backtracking;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mohan Sharma
 */
public class Permutations {

    public List<List<Integer>> permute(int[] nums) {
        if (nums == null || nums.length == 0) {
            return List.of();
        }
        var result = new ArrayList<List<Integer>>();
        generatePermutations(nums, new boolean[nums.length], new ArrayList<>(), result);
        return result;
    }

    private void generatePermutations(int[] nums, boolean[] used, List<Integer> current, List<List<Integer>> result) {
        if (current.size() == nums.length) {
            result.add(new ArrayList<>(current));
            return;
        }
        for (var i = 0; i < nums.length; i++) {
            if (!used[i]) {
                used[i] = true;
                current.add(nums[i]);
                generatePermutations(nums, used, current, result);
                current.removeLast();
                used[i] = false; // backtrack
            }
        }
    }

    public static void main(String[] args) {
        var obj = new Permutations();
        System.out.println(obj.permute(new int[]{1, 2, 3}));
    }
}
