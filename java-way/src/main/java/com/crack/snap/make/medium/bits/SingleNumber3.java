package com.crack.snap.make.medium.bits;

/**
 * Problem: Given an integer array nums, in which exactly two elements appear only once and all the other elements appear exactly twice.
 * Find the two elements that appear only once. You can return the answer in any order.
 * You must write an algorithm that runs in linear runtime complexity and uses only constant extra space.
 *
 * @author Mohan Sharma
 */
public class SingleNumber3 {

    /**
     * Intuition: We can take out knowledge from SingleNumber problem, where we used XOR to find the single number.
     * We know in XOR:
     * 1. a ^ a = 0 (any number XORed with itself is 0)
     * 2. a ^ 0 = a (any number XORed with 0 is the number itself)
     * 3. XOR is commutative and associative, meaning the order of operations doesn't matter.
     *
     * Example the array is [1, 2, 1, 3, 2, 5].
     * Step 1:
     * Lets XOR all the numbers together, which will result in xorSum = 3 ^ 5. Now 3 is 0 0 1 1 and 5 is 0 1 0 1 in binary.
     * So 3 ^ 5 will be
     * 0 0 1 1
     * 0 1 0 1
     * -----------------
     * 0 1 1 0 (which is 6 in decimal)
     *
     * Step 2:
     * Get the rightmost set bit in xorSum. Since x and y are different, their XOR (xorSum = x ^ y) must have at least one bit set to 1.
     * A bit is 1 in x ^ y if and only if that bit is different in x and y (one has 0, the other has 1) as per rule of XOR.
     *
     * Step 3:
     * We use the rightmostSetBit (which we know is 1 in xorSum, meaning x and y differ at this bit position) to split all the numbers
     * in the original array into two groups:
     * Group 1: All numbers from nums that have the rightmostSetBit position set to 1.
     * Group 2: All numbers from nums that have the rightmostSetBit position set to 0.
     *
     * Why does this help?
     * Because x and y differ at the rightmostSetBit position, x will fall into one group, and y will fall into the other.
     * For any number d that appears twice (d, d):
     * If d has the rightmostSetBit set, both occurrences of d will go into Group 1.
     * If d does not have the rightmostSetBit set, both occurrences of d will go into Group 2.
     *
     * Now, if we XOR all numbers within Group 1:
     * All the duplicate numbers in Group 1 will cancel each other out (d ^ d = 0).
     * The only number remaining will be the unique number (x or y) that fell into Group 1.
     * Similarly, XORing all numbers within Group 2 will yield the other unique number.
     *
     * Time Complexity: O(n), where n is the number of elements in the input array nums.
     * Space Complexity: O(1), since we are using a constant amount of extra space for variables.
     */
    public int[] singleNumber(int[] nums) {
        if (nums == null || nums.length < 2) {
            return new int[0]; // Invalid input case
        }
        // Step 1: XOR all numbers to get xorSum
        var xorSum = 0;
        for (var num : nums) {
            xorSum ^= num; // Step 1: XOR all numbers
        }

        // Step 2: Get the rightmost set bit in xorSum
        var rightmostSetBit = xorSum & (-xorSum);

        // Step 3: Split numbers into two groups based on the rightmost set bit
        var x = 0;
        var y = 0;
        for (var num :  nums) {
            if  ((num & rightmostSetBit) != 0) {
                x ^= num; // Group 1: rightmostSetBit is set
            } else  {
                y ^= num; // Group 2: rightmostSetBit is not set
            }
        }
        return new int[]{x, y};
    }
}
