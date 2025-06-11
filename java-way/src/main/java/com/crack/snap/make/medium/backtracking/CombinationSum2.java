package com.crack.snap.make.medium.backtracking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Mohan Sharma
 */
public class CombinationSum2 {

    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        if (candidates == null || candidates.length == 0) {
            return List.of();
        }
        Arrays.sort(candidates); // sort to handle duplicates
        var result = new java.util.ArrayList<List<Integer>>();
        generateCombinations(candidates, target, 0, new ArrayList<>(), result);
        return result;
    }

    private void generateCombinations(int[] candidates, int target, int start, List<Integer> current, List<List<Integer>> result) {
        if (target == 0) {
            // found a valid combination
            result.add(new ArrayList<>(current));
            return;
        }
        if (target < 0) {
            // no valid combination can be formed with the current selection
            return;
        }
        for (var i = start; i < candidates.length; i++) {
            if (i > start && candidates[i] == candidates[i - 1]) {
                // skip duplicates
                continue;
            }
            current.add(candidates[i]);
            generateCombinations(candidates, target - candidates[i], i + 1, current, result);
            current.removeLast();
        }
    }

    public static void main(String[] args) {
        var obj = new CombinationSum2();
        System.out.println(obj.combinationSum2(new int[]{10, 1, 2, 7, 6, 1, 5}, 8)); // [[1, 1, 6], [1, 2, 5], [2, 6], [1, 7]]
    }
}
