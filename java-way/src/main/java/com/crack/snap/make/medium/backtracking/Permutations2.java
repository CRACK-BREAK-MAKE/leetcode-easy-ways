package com.crack.snap.make.medium.backtracking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Mohan Sharma
 */
public class Permutations2 {

    /**
     * We know nPr = n! / (n-r)!, here r is equal n so npn is !n
     *
     * Time Complexity:
     * The time complexity of the permute method is O(n * n!) Here's why:
     * The algorithm generates all permutations of the input array, and the total number of permutations for an array of size n is n!.
     * For each permutation, the algorithm iterates through the array to construct it, which takes O(n) time.
     * Therefore, the total time complexity is O(n * n!).
     *
     * Space Complexity:
     * The space complexity is O(n):
     * The recursion depth is equal to the size of the input array, which is n.
     * The current list and used array also take O(n) space.
     * Hence, the overall space complexity is O(n).
     */
    public List<List<Integer>> permuteUnique(int[] nums) {
        if (nums == null || nums.length == 0) {
            return List.of();
        }
        Arrays.sort(nums); // sort to handle duplicates
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
            if (used[i] || ( i > 0 && nums[i] == nums[i - 1] && !used[i - 1])) {
                // skip used or duplicates that are not used previously skip here as well
                continue;
            }
            used[i] = true;
            current.add(nums[i]);
            generatePermutations(nums, used, current, result);
            current.removeLast(); // backtrack
            used[i] = false; // mark as unused for the next iteration
        }
    }

    public static void main(String[] args) {
        var obj = new Permutations2();
        System.out.println(obj.permuteUnique(new int[]{1, 1, 2})); // [[1, 1, 2], [1, 2, 1], [2, 1, 1]]
    }
}
