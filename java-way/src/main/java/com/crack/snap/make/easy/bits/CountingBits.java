package com.crack.snap.make.easy.bits;

import java.util.Arrays;

/**
 * @author Mohan Sharma
 */
public class CountingBits {

    /**
     * Intuition: Exactly similar to the problem of counting the number of set bits in a single integer.
     * Time Complexity: O(n * log n) where n is the input number. In the worst case, for a number k, the number of set bits
     * can be up to O(logk) (e.g., if k=Math.pow(2, m) − 1, all m bits are set, and m≈logK with base 2.
     */
    public int[] countBits(int n) {
        var result = new int[n + 1];
        for (int i = 0; i <= n; i++) {
            result[i] = countSetBits(i);
        }
        return result;
    }

    private int countSetBits(int n) {
        var count = 0;
        while (n > 0) {
            n &= (n - 1); // Remove the rightmost set bit
            count++; // Increment the count of set bits
        }
        return count; // Return the total count of set bits
    }

    /**
     * Intuition: Consider a number i:
     *
     * If i is even: Its binary representation(no of set bits) is the same as i / 2 (or i >> 1 which is bitwise right shift) with a 0 appended at the end.
     * So, the number of set bits in i is the same as in i / 2.
     * Example: i = 6 (binary 110). i / 2 = 3 (binary 11). Same number of set bits.
     *
     * If i is odd: Its binary representation is the same as i / 2 (or i >> 1) with a 1 appended at the end.
     * So, the number of set bits in i is one more than the number of set bits in i / 2.
     * Example: i = 7 (binary 111). i / 2 = 3 (binary 11). countSetBits(7) = countSetBits(3) + 1.
     * We can combine these:
     * count[i] = count[i >> 1] + (i & 1)
     *
     * Also, i >> 1 is equivalent to i / 2. And (i & 1) is 0 if i is even, and 1 if i is odd (this gives us the (i % 2) part).
     */
    public int[] countBitsUsingDynamicProgramming(int n) {
        var result = new int[n + 1];
        for (var i = 1; i <= n; i++) {
            result[i] = result[i >> 1] + (i & 1);
        }
        return result;
    }

    public static void main(String[] args) {
        var obj = new CountingBits();
        System.out.println(Arrays.toString(obj.countBits(5))); // Output: [0, 1, 1, 2, 1, 2]
    }
}
