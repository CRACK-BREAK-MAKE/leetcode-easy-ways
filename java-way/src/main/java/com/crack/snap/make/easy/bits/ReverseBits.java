package com.crack.snap.make.easy.bits;

/**
 * @author Mohan Sharma
 */
public class ReverseBits {

    /**
     * Intuition:
     * Let's use a 4-bit number n = 0101 (decimal 5). We want the result 1010 (decimal 10).
     * Initialize result = 0000.
     * We will loop 4 times.
     * When in each iteration:
     * Get LSB of n: n = 0101. The LSB is (n & 1) = 1.
     * Shift result left: result = result << 1; (result becomes 0000).
     * Add the LSB to result: result = result | 1; (result becomes 0001).
     * Discard LSB from n: n = n >> 1; (n becomes 0010).
     * And repeat the process.
     */
    public int reverseBits(int n) {
        int result = 0;
        for (var i = 0; i < 32; i++) {
            // Get the least significant bit (LSB). This will be either 0 or 1. Because the number can either be even or odd.
            // If the number is even last bit will be 0, so when we do n & 1, it will be 0. e.g. n = 4 (binary 0100), 0100 & 0001 = 0
            // If the number is odd last bit will be 1, so when we do n & 1, it will be 1. e.g. n = 5 (binary 0101), 0101 & 0001 = 1
            int lsb = n & 1;
            // Shift the result left by 1 to make space for the new bit
            result <<= 1;
            // Add the LSB to the result
            result |= lsb;
            // Discard the LSB from n by right shifting it by 1
            n >>= 1;
        }
        return result; // Return the reversed bits as an integer
    }

    public static void main(String[] args) {
        var obj = new ReverseBits();
        System.out.println(Integer.toUnsignedString(obj.reverseBits(5))); // Output: 2684354560 (binary 10100000000000000000000000000000)
    }
}
