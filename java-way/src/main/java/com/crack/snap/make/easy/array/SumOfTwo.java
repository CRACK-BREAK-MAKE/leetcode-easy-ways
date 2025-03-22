package com.crack.snap.make.easy.array;

import java.util.Arrays;
import java.util.HashMap;

/**
 * @author Mohan Sharma
 */
public class SumOfTwo {

    /**
     * This method finds two indices in the array such that their values add up to the target.
     * It uses a brute force approach with a time complexity of O(n^2).
     *
     * @param nums the array of integers
     * @param target the target sum
     * @return an array of two indices whose values add up to the target, or null if no such indices exist
     */
    public int[] twoSumOn2(int[] nums, int target) {
        // Iterate over each element in the array
        for (int i = 0; i < nums.length; i++) {
            // For each element, iterate over the subsequent elements
            for (int j = i + 1; j < nums.length; j++) {
                // Check if the sum of the two elements equals the target
                if (nums[i] + nums[j] == target) {
                    // If so, return the indices of these two elements
                    return new int[]{i, j};
                }
            }
        }
        // If no such elements are found, return null
        return null;
    }

    /**
     * This method finds two indices in the array such that their values add up to the target.
     * It first sorts the array and then uses a two-pointer approach with a time complexity of O(n log n).
     *
     * @param nums the array of integers
     * @param target the target sum
     * @return an array of two indices whose values add up to the target, or null if no such indices exist
     */
    public int[] twoSumNlogN(int[] nums, int target) {
        // Sort the array
        Arrays.sort(nums);
        // Initialize two pointers, one at the beginning and one at the end of the array
        int left = 0, right = nums.length - 1;
        // Iterate while the two pointers do not cross each other
        while (left < right) {
            // If the sum of the elements at the two pointers is greater than the target, move the end pointer left
            if (nums[left] + nums[right] > target) right--;
                // If the sum is less than the target, move the start pointer right
            else if (nums[left] + nums[right] < target) left++;
                // If the sum equals the target, return the indices of the two elements
            else if (nums[left] + nums[right] == target) {
                return new int[]{left, right};
            }
        }
        // If no such elements are found, return null
        return null;
    }

    /**
     * This method finds two indices in the array such that their values add up to the target.
     * It uses a HashMap to store the indices of the elements with a time complexity of O(n).
     *
     * @param nums the array of integers
     * @param target the target sum
     * @return an array of two indices whose values add up to the target, or null if no such indices exist
     */
    public int[] twoSumHashing(int[] nums, int target) {
        // Create a HashMap to store the indices of the elements
        var hash_map = new HashMap<Integer, Integer>();

        // Iterate over each element in the array
        for (int i = 0;  i < nums.length; i++) {
            // Calculate the second number needed to reach the target
            int firstNumber = nums[i];
            int secondNumber = target - firstNumber;
            // Check if the second number is already in the hash_map
            if (hash_map.containsKey(secondNumber)) {
                // If so, return the indices of the two elements
                return new int[]{hash_map.get(secondNumber), i};
            }
            // Otherwise, add the first number and its index to the hash_map
            hash_map.put(firstNumber, i);
        }
        // If no such elements are found, return null
        return null;
    }

    public static void main(String[] args) {
        int[] nums = {2, 7, 15, 11};
        int target = 13;
        var sumOfTwo = new SumOfTwo();
        System.out.println(Arrays.toString(sumOfTwo.twoSumOn2(nums, target)));
        System.out.println(Arrays.toString(sumOfTwo.twoSumNlogN(new int[]{2, 7, 15, 11}, target)));
        System.out.println(Arrays.toString(sumOfTwo.twoSumHashing(nums, target)));
    }
}
