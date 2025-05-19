package com.crack.snap.make.medium.binarysearch;

/**
 * @author Mohan Sharma
 */
public class SearchRotatedSortedArray {

    /**
     * Problem: There is an integer array nums sorted in ascending order (with distinct values). Prior to being passed to your function,
     * nums is possibly rotated at an unknown pivot index k (1 <= k < nums.length) such that the resulting array is
     * [nums[k], nums[k+1], ..., nums[n-1], nums[0], nums[1], ..., nums[k-1]] (0-indexed).
     * For example, [0,1,2,4,5,6,7] might be rotated at pivot index 3 and become [4,5,6,7,0,1,2].
     * Given the array nums after the possible rotation and an integer target, return the index of target if it is in nums, or -1 if it is not in nums.
     * You must write an algorithm with O(log n) runtime complexity.
     *
     * Intuition: In brute force we can scan the array from left to right and return the index if the target is present otherwise return -1
     * @param nums
     * @param target
     * @return
     */
    public int searchBruteForce(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return -1;
        }
        for (var i = 0; i < nums.length; i++) {
            if (nums[i] == target) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Intuition: Determine which half of the array is sorted (by comparing nums[start] with nums[mid])
     * Check if the target is within the range of the sorted half
     * If yes, search in that half
     * If no, search in the other half
     *
     * Time Complexity: O(log n)
     * Space Complexity: O(1)
     * @param nums
     * @param target
     * @return
     */
    public int searchEfficient(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return -1;
        }
        var start = 0;
        var end = nums.length - 1;
        while (start <= end) {
            var mid = start + (end - start / 2);
            if (nums[mid] == target) {
                return mid;
            }
            // Check which half is sorted
            if (nums[start] <= nums[mid]) {

                // target is between start and mid - 1
                if (nums[start] <= target && target < nums[mid]) {
                    end = mid - 1;
                } else {
                    // target is between mid + 1 and end
                    start = mid + 1;
                }
            } else { // Right half is sorted
                // target is between mid + 1 and end
                if (nums[mid] < target && target <= nums[end]) {
                    start = mid + 1;
                } else {
                    // target is between start and mid - 1
                    end = mid - 1;
                }
            }
        }
        return -1;
    }

    public int search(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return -1;
        }
        var rotatedIndex = getRotatedIndex(nums);
        //target = 7 in [6,7,0,1,2,4,5] or target = 4 in [6,7,0,1,2,4,5]
        var start = nums[nums.length - 1] >= target ? rotatedIndex : 0;
        var end = nums[nums.length - 1] < target ? rotatedIndex : nums.length - 1;

        while (start <= end) {
            var mid = start + (end - start) / 2;
            if (target == nums[mid]) {
                return mid;
            } else if (target < nums[mid]) {
                end = mid - 1;
            } else {
                start = mid + 1;
            }
        }
        return -1;
    }

    // very similar to finding minimum which follow left most binary search
    private int getRotatedIndex(int[] nums) {
        var start = 0;
        var end = nums.length - 1;
        while (start < end) {
            var mid = start + (end - start) / 2;
            if (nums[mid] > nums[end]) {
                start = mid + 1;
            } else {
                end = mid;
            }
        }
        return start;
    }

    public static void main(String[] args) {
        System.out.println(new SearchRotatedSortedArray().search(new int[] {1, 3}, 3)); // 1
        System.out.println(new SearchRotatedSortedArray().search(new int[] {4, 5, 6, 7, 0, 1, 2}, 0)); // 4
        System.out.println(new SearchRotatedSortedArray().search(new int[] {4, 5, 6, 7, 0, 1, 2}, 3)); // -1
        System.out.println(new SearchRotatedSortedArray().search(new int[] {1}, 1)); // 1
    }
}
