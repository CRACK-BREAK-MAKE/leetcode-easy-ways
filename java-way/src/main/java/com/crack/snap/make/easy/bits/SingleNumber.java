package com.crack.snap.make.easy.bits;

/**
 * Problem: Given a non-empty array of integers nums, every element appears twice except for one. Find that single one.
 * You must implement a solution with a linear runtime complexity and use only constant extra space.
 *
 * @author Mohan Sharma
 */
public class SingleNumber {

    /**
     * Intuition: It's more like finding the duplicate number, but in this case, we have only one number that appears once
     * and all others appear twice. We can solve it by:
     * 1. Using two loops, one to iterate through the array and another to check if the number appears again in the array.
     * 2. Using sorting, sort the array and then iterate through it to find the number that appears once, using duplicates will
     * likely be together property.
     * 3. Using a HashSet, add all numbers to the set and then iterate through the array again to find the number that is not in the set.
     * 4. Most efficient way is to use Bit manipulation, we know that
     *      1. XOR of a number with itself is 0 and
     *      2. XOR of a number with 0 is the number itself.
     *      using these properties, we can iterate through the array and XOR all the numbers. All duplicates will
     *      cancel each other out, and we will be left with the number that appears once.
     */
    public int singleNumber(int[] nums) {
        if (nums == null || nums.length == 0) {
            return -1; // or throw an exception
        }
        var result = 0;
        for (int num : nums) {
            result ^= num; // XOR operation
        }
        return result; // the result will be the number that appears once
    }
}
