package com.crack.snap.make.medium.backtracking;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mohan Sharma
 */
public class Combinations {

    public List<List<Integer>> combine(int n, int k) {
        if ( n <=0 || k <= 0 || k > n) {
            return List.of();
        }
        var result = new ArrayList<List<Integer>>();
        backtrack(n, k, 1, new ArrayList<>(), result);
        return result;
    }

    private void backtrack(int n, int k, int start, List<Integer> current, List<List<Integer>> result) {
        if (current.size() == k) {
            result.add(new ArrayList<>(current));
            return;
        }

        for (var i = start; i <= n; i++) {
            current.add(i);
            backtrack(n, k, i + 1, current, result);
            current.removeLast();
        }
    }

    /**
     * Time Complexity:
     * The recursion tree has 2 branches at each level (pick and not pick).
     * For an array of size n, the total number of combinations generated is 2^n (all possible combinations of elements).
     * Therefore, the time complexity is O(2^n).
     *
     * Space Complexity:
     * The height of the recursion tree is n because the recursion goes as deep as the length of the array.
     * The current list used to store combination takes O(n) space in the worst case.
     * Hence, the space complexity is O(n) (for the recursion stack).
     */
    public List<List<Integer>> combineBruteForce(int n, int k) {
        if ( n <= 0 || k <= 0 || k > n) {
            return List.of();
        }
        var result = new ArrayList<List<Integer>>();
        backtrackPickNotPick(n, k, 1, new ArrayList<>(), result);
        return result;
    }

    private void backtrackPickNotPick(int n, int k, int num, List<Integer> current, List<List<Integer>> result) {
        if (current.size() == k) {
            result.add(new ArrayList<>(current));
            return;
        }
        if (num > n) {
            return;
        }
        // not pick
        backtrackPickNotPick(n, k, num + 1, current, result);

        //pick
        current.add(num);
        backtrackPickNotPick(n, k, num + 1, current, result);
        current.removeLast();
    }

    public static void main(String[] args) {
        var obj = new Combinations();
        System.out.println(obj.combine(4, 2)); // Output [[1, 2], [1, 3], [1, 4], [2, 3], [2, 4], [3, 4]]
        System.out.println(obj.combineBruteForce(4, 2));
    }
}
