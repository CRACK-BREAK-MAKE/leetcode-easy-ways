package com.crack.snap.make.medium.bits;

/**
 * Problem: Given two integers a and b, return the sum of the two integers without using the operator + and -.
 * @author Mohan Sharma
 */
public class SumTwoNumbers {

    /**
     * Intuition: We know when we add two bits, we can have a sum bit and a carry-out bit as per the following table:
     *  Bit a	Bit b	Sum Bit	    Carry-out Bit
     *  0	    0	       0	        0
     *  0	    1	       1	        0
     *  1	    0	       1	        0
     *  1	    1	       0	        1
     *  The Sum Bit mimics the XOR operation, and the Carry-out Bit mimics the AND operation.
     *
     * Now, When we add two numbers like a and b (e.g., in decimal, 15 + 17):
     * We add the digits at each position (5+7 = 12). We write down the 2 (sum part) and carry over the 1.
     * Then we add the next digits plus the carry (1+1 + carried 1 = 3).
     * We can do something similar with binary numbers using our XOR and AND insights:
     *
     * Calculate the sum without considering carries: This is a XOR b. This gives us the sum for each bit position as
     * if there were no carries.
     * Calculate the carries: This is a AND b. This tells us where a carry is generated (i.e., where both bits were 1).
     * The Problem with Carries: A carry from bit position i needs to be added to bit position i - 1. This means we need to
     * left-shift the carry bits by 1 (carry << 1) before we can add them.
     * Repeat: Now, we have a new "sum without carry" and a new "shifted carry". We need to add these two numbers together.
     * We can repeat the same process (XOR for sum, AND for carry, shift carry) until there are no more carries to add.
     *
     * Example:
     * Let a = 2 (binary 0010) and b = 3 (binary 0011). Expected sum = 5 (binary 0101).
     *
     * Iteration 1:
     * Current a = 0010
     * Current b = 0011
     * Sum without carry (XOR): current_sum = a ^ b = 0010 ^ 0011 = 0001
     * Carry (AND): carry = a & b = 0010 & 0011 = 0010
     * Is carry == 0? No, it's 0010. So we must continue.
     * Shift the carry left: shifted_carry = carry << 1 = 0010 << 1 = 0100
     * For the next iteration:
     * a becomes current_sum (0001)
     * b becomes shifted_carry (0100) (Our new "b" effectively holds the carries to be added)
     *
     * Iteration 2:
     * Current a = 0001
     * Current b = 0100
     * Sum without carry (XOR): current_sum = a ^ b = 0001 ^ 0100 = 0101
     * Carry (AND): carry = a & b = 0001 & 0100 = 0000
     * Is carry == 0? Yes! This means there are no more carries to process.
     * The loop terminates.
     *
     * How it Handles Negative Numbers?
     * Computers typically use "Two's Complement" to represent negative numbers. The beauty of this representation is that
     * binary addition (and thus these bitwise operations) works correctly for both positive and negative numbers without needing
     * special logic. So, the same algorithm handles cases like 2 + (-3) correctly.
     *
     * Example: a = 1, b = -2. Expected sum = -1.
     * a = 0...0001
     * b = ...11110 (Two's complement of 2 is ...0010 -> ...1101 (invert) + 1 = ...1110)
     *
     * Iteration 1:
     * a = ...0001, b = ...1110
     * carry_val = (a & b) = ...0000 (Only bit 0 of a is 1, bit 0 of b is 0)
     * shifted_carry = carry_val << 1 = ...0000
     * a = a ^ b = ...0001 ^ ...1110 = ...1111 (This is -1)
     * b = shifted_carry = ...0000
     *
     * Iteration 2:
     * b is 0. Loop terminates.
     * Result: a = ...1111 (which is -1). Correct!
     *
     * Why a = ...1111 is -1 and not 15?
     * The binary representation 1111 means -1 (and not 15) when we're talking about signed integers, specifically
     * using a system called two's complement. Here's a short explanation:
     *
     * Signed vs. Unsigned:
     * Unsigned: All bits are used to represent the magnitude. So, for 4 bits, 1111 would be 8+4+2+1=15.
     * Signed: The most significant bit (the leftmost bit) is typically used to indicate the sign. 0 for positive, 1 for negative.
     * Two's Complement (Common for Signed Integers):
     *
     * If the leftmost bit (Most Significant Bit or MSB) is 0, the number is positive, and you read it like a normal binary number.
     * If the MSB is 1, the number is negative. To find its value:
     * Invert all the bits: Change every 1 to 0 and every 0 to 1. For 1111, this becomes 0000.
     * Add 1 to the result: 0000 + 1 = 0001.
     * This result (0001, which is 1 in decimal) is the absolute magnitude of the negative number.
     * So, 1111 in 4-bit two's complement represents -1.
     *
     * In essence, with signed numbers, 1111 doesn't have its usual "place value" of 8 for the leftmost bit. Instead, that leftmost 1
     * signals it's a negative number, and the specific pattern 1111 is how -1 is encoded in the two's complement system to make
     * arithmetic (like addition and subtraction) work consistently with bitwise operations.
     * It's all about how computers decide to represent positive and negative numbers using only 0s and 1s.
     * There are a few ways, but the most common is Two's Complement.
     *
     * Let's use 4 bits for simplicity:
     * Unsigned Interpretation:
     * If we say 1111 is an unsigned number, we just convert it directly:
     * 1*2^3 + 1*2^2 + 1*2^1 + 1*2^0 = 8 + 4 + 2 + 1 = 15.
     * In this case, 1111 is indeed 15.
     *
     * Signed Interpretation (Two's Complement):
     * When we need to represent both positive and negative numbers with a fixed number of bits (like 4 bits), we use a system.
     * Two's complement is the standard because it makes arithmetic (like addition and subtraction) easy for the computer.
     *
     * In two's complement (for, say, 4 bits):
     * The Most Significant Bit (MSB) - the leftmost bit - indicates the sign.
     * If MSB is 0, the number is positive or zero.
     * If MSB is 1, the number is negative.
     * For 1111:
     * The MSB is 1, so it's a negative number.
     * To find out which negative number it is:
     * a. Invert all the bits: 1111 becomes 0000.
     * b. Add 1 to the result: 0000 + 1 = 0001.
     * c. This 0001 is the absolute value (magnitude). Since we know it's negative, 1111 represents -1.
     *
     * Why does this system work?
     * One cool thing about two's complement is that X + (-X) = 0 using normal binary addition (ignoring overflow).
     * Let's try 1 + (-1) using 4-bit two's complement:
     * 1 is 0001
     * -1 is 1111
     * So, if we add them:
     *   0001  (1)
     * + 1111  (-1)
     * -------
     * 1 0000
     * If we only have 4 bits, the leading 1 (the carry-out) is discarded, leaving 0000, which is 0.
     * This consistency is why two's complement is used.
     */
    public int getSum(int a, int b) {
        while (b != 0) {
            var carry = a & b; // Calculate carry bits
            a = a ^ b; // Calculate sum bits without carry and update a
            b = carry << 1; // Shift carry bits left to add them in the next iteration
        }
        return a; // When b is 0, a contains the final sum
    }
}
