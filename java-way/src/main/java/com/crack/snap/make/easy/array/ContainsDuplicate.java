package com.crack.snap.make.easy.array;

import java.util.HashSet;

/**
 * @author Mohan Sharma
 */
public class ContainsDuplicate {

    /**
     * This method checks if the given array contains duplicate elements.
     * It uses the brute force approach to check if the array contains duplicate elements.
     * It uses two nested loops to compare each element with every other element in the array.
     * If the same element is found at different indices, it returns true.
     * If no duplicate element is found, it returns false.
     *
     * @param nums the array of integers to check for duplicates
     * @return true if the array contains duplicate elements, false otherwise
     */
    public boolean containsDuplicateOn2(int[] nums) {
        if (nums == null || nums.length == 0) {
            return false;
        }
        for (int i=0; i < nums.length; i++) {
            for (int j = i+1; j < nums.length; j++) {
                if (nums[i] ==  nums[j])
                    return true;
            }
        }
        return false;
    }


    /**
     * This method checks if the given array contains duplicate elements using a HashSet.
     * It adds each element to a HashSet and checks if the element is already present in the set.
     * If it finds a duplicate, it returns true.
     * If no duplicates are found, it returns false.
     *
     * Example:
     * int[] nums = {1, 2, 3, 4, 5, 1};
     * containsDuplicateHashing(nums) -> true (because 1 is repeated)
     *
     * int[] nums = {1, 2, 3, 4, 5};
     * containsDuplicateHashing(nums) -> false (no duplicates)
     *
     * @param nums the array of integers to check for duplicates
     * @return true if the array contains duplicate elements, false otherwise
     */
    public boolean containsDuplicateHashing(int[] nums) {
        if (nums == null || nums.length == 0) {
            return false;
        }
        var set = new HashSet<>();
        for (int num: nums) {
            if (!set.add(num)) {
                return true;
            }
        }
        return false;
    }

    /**
     * This method checks if the given array contains duplicate elements by first sorting the array.
     * It uses insertion sort to sort the array and then checks if any consecutive elements are the same.
     * If it finds duplicates, it returns true.
     * If no duplicates are found, it returns false.
     *
     * Example:
     * int[] nums = {1, 2, 3, 4, 5, 1};
     * containsDuplicateSort(nums) -> true (because 1 is repeated)
     *
     * int[] nums = {1, 2, 3, 4, 5};
     * containsDuplicateSort(nums) -> false (no duplicates)
     *
     * @param nums the array of integers to check for duplicates
     * @return true if the array contains duplicate elements, false otherwise
     */
    public boolean containsDuplicateSort(int[] nums) {
        if (nums == null || nums.length == 0) {
            return false;
        }
        for (int i = 1; i < nums.length; i++) {
            int j = i - 1;
            int key = nums[i];
            while (j >= 0 && nums[j] > key) {
                nums[j + 1] = nums [j];
                j--;
            }
            nums[j + 1] = key;
        }
        for (int i = 1; i < nums.length; i++) {
            if (nums[i-1] ==  nums[i]) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        ContainsDuplicate duplicateFinder = new ContainsDuplicate();
        System.out.println(duplicateFinder.containsDuplicateSort(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}));
        System.out.println(duplicateFinder.containsDuplicateSort(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 1}));
    }
}
