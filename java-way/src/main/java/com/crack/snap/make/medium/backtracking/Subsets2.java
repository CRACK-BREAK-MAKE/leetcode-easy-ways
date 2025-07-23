package com.crack.snap.make.medium.backtracking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Mohan Sharma
 */
public class Subsets2 {
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        if (nums == null || nums.length == 0) {
            return List.of(List.of());
        }
        Arrays.sort(nums);
        var result = new ArrayList<List<Integer>>();
        generateSubsetsUsingBacktracking(nums, 0, new ArrayList<>(), result);
        return result;
    }

    private void generateSubsetsUsingBacktracking(int[] nums, int start, List<Integer> current, List<List<Integer>> result) {
        // clone the current subset and add it to the result. why clone? because we will modify current in the next steps.
        // If we don't clone, the result will always have the last modified state of current.
        result.add(new ArrayList<>(current));

        for (var i = start; i < nums.length; i++) {
            if (i > start && nums[i] == nums[i - 1]) {
                // skip duplicates
                continue;
            }
            // make a choice
            current.add(nums[i]);
            // explore further with the choice made
            generateSubsetsUsingBacktracking(nums, i + 1, current, result);
            // undo the choice (backtrack)
            current.removeLast();
        }

    }


    /**
     *  Let's see why it fails for nums = [1, 2, 2].
     * Path 1:
     * solve(0) -> Pick 1 -> current = [1]
     * solve(1) -> Skip first 2 -> current = [1]
     * solve(2) -> Pick second 2 -> current = [1, 2]
     * solve(3) -> base case, add [1, 2] to results.
     *
     * Path 2:
     * solve(0) -> Pick 1 -> current = [1]
     * solve(1) -> Pick first 2 -> current = [1, 2]
     * solve(2) -> Skip second 2 -> current = [1, 2]
     * solve(3) -> base case, add [1, 2] to results.
     *
     * The subset [1, 2] is generated twice. The problem is that the simple "pick/not pick" logic doesn't distinguish
     * between the two identical 2's. The solution is to enforce a rule that prevents the duplicate paths shown above.
     * The core idea is:
     * If you decide NOT to pick an element, you must also skip all of its subsequent identical duplicates.
     *
     * Time Complexity:
     * The recursion tree has 2 branches at each level (pick and not pick).
     * For an array of size n, the total number of subsets generated is 2^n (all possible combinations of elements).
     * Therefore, the time complexity is O(2^n).
     *
     * Space Complexity:
     * The height of the recursion tree is n because the recursion goes as deep as the length of the array.
     * The current list used to store subsets takes O(n) space in the worst case (when the subset contains all elements).
     * Hence, the space complexity is O(n) (for the recursion stack).
     */
    public List<List<Integer>> subsetsWithDupBruteForce(int[] nums) {
        if (nums == null || nums.length == 0) {
            return List.of(List.of());
        }
        Arrays.sort(nums);
        var result = new ArrayList<List<Integer>>();
        generateSubsetsPickNotPick(nums, 0, new ArrayList<>(), result);
        return result;
    }

    private void generateSubsetsPickNotPick(int[] nums, int index, List<Integer> current, List<List<Integer>> result) {
        if (index == nums.length) {
            result.add(new ArrayList<>(current));
            return;
        }
        // If you decide NOT to pick an element, you must also skip all of its subsequent identical duplicates.
        var nextIndex = index + 1;
        while (nextIndex < nums.length && nums[nextIndex] == nums[index]) {
            nextIndex++;
        }
        generateSubsetsPickNotPick(nums, nextIndex, current, result);

        // pick
        current.add(nums[index]);
        generateSubsetsPickNotPick(nums, index + 1, current, result);
        current.removeLast();
    }

    public static void main(String[] args) {
        var obj = new Subsets2();
        System.out.println(obj.subsetsWithDup(new int[]{1, 2, 2})); // [[], [1], [1, 2], [1, 2, 2], [2], [2, 2]]
        System.out.println(obj.subsetsWithDupBruteForce(new int[]{1, 2, 2})); // [[], [1], [1, 2], [1, 2, 2], [2], [2, 2]]
    }
}
