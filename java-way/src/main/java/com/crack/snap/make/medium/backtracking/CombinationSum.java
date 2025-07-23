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

    /**
     * Time Complexity:
     * The combinationSumBruteForce method explores all possible combinations of picking or not picking each candidate at every index.
     * Since there are 2 choices (pick or not pick) for each candidate, the total number of recursive calls is O(2^n), where n is the number of candidates.
     *
     * Space Complexity:
     * The space complexity is determined by the maximum depth of the recursion tree, which is equal to the size of the current list in the worst case.
     * The maximum depth is O(n), where n is the number of candidates.
     */
    public List<List<Integer>> combinationSumBruteForce(int[] candidates, int target) {
        if (candidates == null || candidates.length == 0) {
            return List.of();
        }
        var result = new java.util.ArrayList<List<Integer>>();
        generateCombinationsPickNotPick(candidates, target, 0, new ArrayList<>(), result);
        return result;
    }

    private void generateCombinationsPickNotPick(int[] candidates, int target, int index, List<Integer> current, List<List<Integer>> result) {
        if (target == 0) {
            result.add(new ArrayList<>(current));
            return;
        }
        if (target < 0 || index >= candidates.length) {
            return;
        }
        // not pick
        generateCombinationsPickNotPick(candidates, target, index + 1, current, result);
        // pick
        int candidate = candidates[index];
        current.add(candidate);
        generateCombinationsPickNotPick(candidates, target - candidate, index, current, result);
        current.removeLast();
    }

    public static void main(String[] args) {
        var obj = new CombinationSum();
        System.out.println(obj.combinationSum(new int[]{2,3,6,7}, 7)); // [[2, 2, 3], [7]]
        System.out.println(obj.combinationSumBruteForce(new int[]{2,3,6,7}, 7));
    }
}
