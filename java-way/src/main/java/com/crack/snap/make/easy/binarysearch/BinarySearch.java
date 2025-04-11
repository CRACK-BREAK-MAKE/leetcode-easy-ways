package com.crack.snap.make.easy.binarysearch;

/**
 * @author Mohan Sharma
 */
public class BinarySearch {

    /**
     * Problem: Given an array of integers nums which is sorted in ascending order, and an integer target,
     * write a function to search target in nums. If target exists, then return its index. Otherwise, return -1.
     * You must write an algorithm with O(log n) runtime complexity.
     *
     * Intuition: Since the array is sorted and the problem statement requires O(log n) time complexity, we can use binary search
     * to find the target. The idea is to keep dividing the search space in half until we find the target or exhaust the search space.
     * First we will check if the middle of the array is equal to the target. If it is, we return the index of the middle element.
     * If the middle element is less than the target, we search the right half of the array.
     * If the middle element is greater than the target, we search the left half of the array.
     *
     * why mid = left + (right - left) / 2 instead of (left + right) / 2?
     * The expression int mid = left + (right - left) / 2; is used instead of int mid = (left + right) / 2;
     * to prevent integer overflow when left and right are large integers.
     * Suppose left = 2_000_000_000 and right = 2_000_000_001.
     * left + right = 2_000_000_000 + 2_000_000_001 = 4_000_000_001
     * This value exceeds the maximum value of a 32-bit integer (2_147_483_647), causing an integer overflow and resulting in an incorrect calculation.
     *
     * Time Complexity: O(log n)
     * Space Complexity: O(1)
     * @param nums
     * @param target
     * @return
     */
    public int search(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return -1;
        }
        var left = 0;
        var right = nums.length - 1;
        return binarySearch(nums, target, left, right);
    }

    private int binarySearchRecursive(int[] nums, int target, int left, int right) {
        if (left >  right)
            return -1;
        var mid = left + (right - left) / 2;
        if (nums[mid] > target) {
            return binarySearchRecursive(nums, target, left, mid - 1);
        } else if (nums[mid] < target) {
            return binarySearchRecursive(nums, target, mid + 1, right);
        } else {
            return mid;
        }
    }

    /**
     * Recursive approach can lead to stack overflow for large arrays due to deep recursion. Iterative approach
     * avoids the overhead of recursive calls and is more memory-efficient.
     * @param nums
     * @param target
     * @param left
     * @param right
     * @return
     */
    private int binarySearch(int[] nums, int target, int left, int right) {
        while (left <= right) {
            var mid = left + (right - left) / 2;
            if (nums[mid] ==  target)
                return mid;
            else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        var obj = new BinarySearch();
        System.out.println(obj.search(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9}, 9));
    }
}
