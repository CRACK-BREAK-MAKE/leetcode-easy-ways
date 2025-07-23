package com.crack.snap.make.medium.backtracking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Mohan Sharma
 */
public class CombinationSum3 {

    public List<List<Integer>> combinationSum3(int k, int n) {
        var result = new java.util.ArrayList<List<Integer>>();
        generateCombinations(k, n, 1, new ArrayList<>(), result);
        return result;
    }

    private void generateCombinations(int k, int target, int start, List<Integer> current, List<List<Integer>> result) {
        if (target == 0 && current.size() == k) {
            // found a valid combination
            result.add(new ArrayList<>(current));
            return;
        }
        if (target < 0 || current.size() > k) {
            // no valid combination can be formed with the current selection
            return;
        }
        for (var i = start; i <= 9; i++) {
            current.add(i);
            generateCombinations(k, target - i, i + 1, current, result);
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
    public List<List<Integer>> combinationSum3BruteForce(int k, int n) {
        var result = new ArrayList<List<Integer>>();
        generateCombinationsPickNotPick(k, n, 1, new ArrayList<>(), result);
        return result;
    }

    private void generateCombinationsPickNotPick(int k, int target, int num, List<Integer> current, List<List<Integer>> result) {
        if (target == 0 && current.size() == k) {
            result.add(new ArrayList<>(current));
            return;
        }
        if (target < 0 || num > 9 || current.size() > k) {
            return;
        }

        // not pick
        generateCombinationsPickNotPick(k, target, num + 1, current, result);
        // pick
        current.add(num);
        generateCombinationsPickNotPick(k, target - num, num + 1, current, result);
        current.removeLast();
    }

    public static void main(String[] args) {
        var obj = new CombinationSum3();
        System.out.println(obj.combinationSum3(3, 7)); // [[1, 2, 4]]
        System.out.println(obj.combinationSum3(3, 9)); // [[1,2,6],[1,3,5],[2,3,4]]
        System.out.println(obj.combinationSum3BruteForce(3, 7)); // [[1, 2, 4]]
        System.out.println(obj.combinationSum3BruteForce(3, 9)); // [[1,2,6],[1,3,5],[2,3,4]]
    }
}
