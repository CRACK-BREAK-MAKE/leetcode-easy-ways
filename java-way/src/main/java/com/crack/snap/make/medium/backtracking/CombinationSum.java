package com.crack.snap.make.medium.backtracking;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mohan Sharma
 */
public class CombinationSum {

    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        if (candidates == null || candidates.length == 0) {
            return List.of();
        }
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
            current.add(candidates[i]);
            generateCombinations(candidates, target - candidates[i], i, current, result);
            current.removeLast();
        }
    }

    public static void main(String[] args) {
        var obj = new CombinationSum();
        System.out.println(obj.combinationSum(new int[]{1, 2, 3}, 4)); // [[2, 2, 3], [7]]
    }
}
