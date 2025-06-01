package com.crack.snap.make.easy.bits;

/**
 * Problem: Given a positive integer n, write a function that returns the number of set bits in its binary representation (also known as the Hamming weight).
 * @author Mohan Sharma
 */
public class NumberOfOneBits {

    /** Intuition: We know that n & (n - 1) will remove off the rightmost set bit of n.
     * Example:
     * If n = 1111 in binary (which is 15 in decimal),
     * The first time we do n & (n - 1), it will become 1110 (which is 14 in decimal).
     * The next time we do n & (n - 1) on 1110, it will become 1100 (which is 12 in decimal).
     * Then the next time we do n & (n - 1) on 1100, it will become 1000 (which is 8 in decimal).
     * Then the next time we do n & (n - 1) on 1000, it will become 0000 (which is 0 in decimal).
     * That means we need a loop to iterate till the n > 0, and in each iteration we will do n = n & (n - 1) and
     * increment a counter to count the number of set bits.
     */
    public int hammingWeight(int n) {
        var count = 0;
        while (n > 0) {
            n &= (n - 1); // Remove the rightmost set bit
            count++; // Increment the count of set bits
        }
        return count; // Return the total count of set bits
    }
}
