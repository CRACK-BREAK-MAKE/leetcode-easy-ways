package com.crack.snap.make.medium.twopointer;

import java.util.Arrays;

/**
 * @author Mohan Sharma
 */
public class SortedTwoSum {

    /**
     * Given a sort increasing order array, return the indices (1-indexed) of two numbers, [index1, index2],
     * such that they add up to a given target number and index1 < index2. Note that index1 and index2 cannot be equal,
     * therefore you may not use the same element twice.
     * -
     * Intuition:
     * Since the array is sorted, can we use two pointers to find the sum of the two numbers?
     * Have a left pointer at the start of the array and a right pointer at the end of the array.
     * If the sum of the two numbers is less than the target, increment the left pointer.
     * If the sum of the two numbers is greater than the target, decrement the right pointer.
     * If the sum of the two numbers is equal to the target, return the [leftIndex + 1, rightIndex + 1].
     * -
     * Time Complexity: O(n)
     * - We only traverse the array once with the two pointers, so the operations are linear time.
     * Space Complexity: O(1)
     * - We use a constant amount of extra space for the pointers and the result array.
     * -
     * @param numbers the sorted increasing order array
     * @param target the target sum
     * @return the indices (1-indexed) of the two numbers that add up to the target
     */
    public int[] twoSum(int[] numbers, int target) {
        var result = new int[2];
        if (numbers == null || numbers.length == 0) {
            return result;
        }

        var leftIndex = 0;
        var rightIndex = numbers.length - 1;
        while (leftIndex < rightIndex) {
            if (numbers[leftIndex] + numbers[rightIndex] < target) {
                leftIndex++;
            } else if (numbers[leftIndex] + numbers[rightIndex] > target) {
                rightIndex--;
            } else {
                result[0] = leftIndex + 1;
                result[1] = rightIndex + 1;
                return result;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        SortedTwoSum sortedTwoSum = new SortedTwoSum();
        System.out.println(Arrays.toString(sortedTwoSum.twoSum(new int[]{2, 7, 11, 15}, 9)));
        System.out.println(Arrays.toString(sortedTwoSum.twoSum(new int[]{2, 3, 4}, 6)));
        System.out.println(Arrays.toString(sortedTwoSum.twoSum(new int[]{-1, 0}, -1)));
    }
}
