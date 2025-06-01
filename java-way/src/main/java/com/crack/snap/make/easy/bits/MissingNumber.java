package com.crack.snap.make.easy.bits;

import java.util.stream.IntStream;

/**
 * Problem: Given an array nums containing n distinct numbers in the range [0, n], return the only number in the range that is missing from the array.
 *
 * Example:
 * Input: nums = [9,6,4,2,3,5,7,0,1]
 * Output: 8
 *
 * @author Mohan Sharma
 */
public class MissingNumber {

    /**
     * Intuition: We have an array of n distinct numbers in the range [0, n], meaning it contains all numbers from 0 to n except one number.
     * So what it we sum all the numbers in the array and subtract it from the sum of all numbers from 0 to n?
     * Example: For array nums = [9,6,4,2,3,5,7,0,1], the sum of all numbers in the array is 37.
     * If n = 9, the sum of all numbers from 0 to 9 is 0 + 1 + 2 + 3 + 4 + 5 + 6 + 7 + 8 + 9 = 45.
     * so the missing number is 45 - 37 = 8.
     */
    public int missingNumberNaive(int[] nums) {
        if (nums == null || nums.length == 0) {
            return -1; // or throw an exception
        }
        var originalSum = IntStream.rangeClosed(0, nums.length).sum(); // Sum of all numbers from 0 to n
        var arraySum = IntStream.of(nums).sum(); // Sum of all numbers in the array
        return originalSum - arraySum; // The missing number is the difference
    }

    /**
     * Intuition: We can exploit the properties of XOR where:
     * 1. XOR of a number with itself is 0, X ^ X = 0.
     * 2. XOR of a number with 0 is the number itself, X ^ 0 = X.
     * What if we XOR all the numbers in the array with all numbers from 0 to n? Everything will cancel out except the missing number.
     * Example: For array nums = [9,6,4,2,3,5,7,0,1], we will XOR all numbers from 0 to 9 with the numbers in the array.
     * which will be 0 ^ 1 ^ 2 ^ 3 ^ 4 ^ 5 ^ 6 ^ 7 ^ 8 ^ 9 ^ 9 ^ 6 ^ 4 ^ 2 ^ 3 ^ 5 ^ 7 ^ 0 ^ 1.
     * The result will be the missing number, which is 8.
     */
    public int missingNumber(int[] nums) {
        if (nums == null || nums.length == 0) {
            return -1; // or throw an exception
        }
        var result = nums.length; // Start with n, which is the last number in the range [0, n]
        for (int i = 0; i < nums.length; i++) {
            result ^= i ^ nums[i]; // XOR the index and the number at that index
        }
        return result; // The result will be the missing number
    }
}
