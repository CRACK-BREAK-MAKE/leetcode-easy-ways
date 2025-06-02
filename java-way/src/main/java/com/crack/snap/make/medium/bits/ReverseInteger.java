package com.crack.snap.make.medium.bits;

/**
 * Problem: Given a signed 32-bit integer x, return x with its digits reversed. If reversing x causes the value to go outside
 * the signed 32-bit integer range [-2^31, 2^31 - 1], then return 0.
 * Assume the environment does not allow you to store 64-bit integers (signed or unsigned).
 *
 * @author Mohan Sharma
 */
public class ReverseInteger {

    /**
     * Imagine you have the number 123.
     * "Pop" the last digit: 3 (you get this by 123 % 10). % give the remainder of the division. so 123 % 10 = 3 and 123 / 10 = 12.
     * The remaining number is 12 (you get this by 123 / 10).
     * "Push" the popped digit (3) into a reversed_number variable. If reversed_number was 0, it becomes 3.
     *
     * Now, with remaining 12:
     * Pop 2 (12 % 10).
     * Remaining is 1 (12 / 10).
     * Push 2 to reversed_number. If reversed_number was 3, it becomes 3 * 10 + 2 = 32. Why * 10? Because we are shifting
     * the previous digits to the left (like in decimal system).
     *
     * With remaining 1:
     * Pop 1 (1 % 10).
     * Remaining is 0 (1 / 10).
     * Push 1 to reversed_number. If reversed_number was 32, it becomes 32 * 10 + 1 = 321. The remaining number is 0, so we stop.
     * The sign can be handled by the fact that x % 10 will yield negative digits if x is negative. The process works out naturally.
     *
     * The Critical Part: Overflow Detection
     * We need to check for overflow before we actually cause it with reversed_num = reversed_num * 10 + pop;.
     *
     * We know MAX_VALUE = 2^31 - 1 = 2147483647 and MIN_VALUE = -2^31 = -2147483648.
     * Positive Overflow Check:
     * If reversed_num > Integer.MAX_VALUE / 10, then reversed_num * 10 will surely overflow, regardless of pop. Example:
     * if reversed_num is 214748365, then 214748365 * 10 = 2147483650, which is out of bounds.
     *
     * If reversed_num == Integer.MAX_VALUE / 10 (which is 214748364), then reversed_num * 10 + pop will overflow if pop > 7
     * (since Integer.MAX_VALUE ends in 7). Example: if reversed_num is 214748364 and pop is 8,
     * then 214748364 * 10 + 8 = 2147483648, which is out of bounds.
     *
     * Negative Overflow Check:
     * If reversed_num < Integer.MIN_VALUE / 10, then reversed_num * 10 will surely overflow. Example: if reversed_num is -214748365,
     * then -214748365 * 10 = -2147483650, which is out of bounds.
     *
     * If reversed_num == Integer.MIN_VALUE / 10 (which is -214748364), then rev * 10 + pop will overflow
     * if pop < -8 (since Integer.MIN_VALUE ends in -8). Example: if reversed_num is -214748364 and pop is -9,
     * then -214748364 * 10 - 9 = -2147483649, which is out of bounds.
     *
     * If any of these conditions are met, we must return 0.
     *
     * Time Complexity: O(log10(x)), where x is the input number. This is because we are processing each digit of x.
     * Space Complexity: O(1), since we are using a constant amount of space for the reversed number and pop.
     */
    public int reverse(int x) {
        if (x == 0) {
            return 0;
        }
        var reversedNumber = 0;
        while (x != 0) {
            var pop = x % 10; // Get the last digit
            if (reversedNumber > Integer.MAX_VALUE / 10 || (reversedNumber == Integer.MAX_VALUE / 10 && pop > 7)) {
                return 0; // Positive overflow
            }
            if (reversedNumber < Integer.MIN_VALUE / 10 || (reversedNumber == Integer.MIN_VALUE / 10 && pop < -8)) {
                return 0; // Positive overflow
            }
            reversedNumber = reversedNumber * 10 + pop; // Push the digit into the reversed number
            x /= 10; // Remove the last digit from x
        }
        return reversedNumber;
    }

    public static void main(String[] args) {
        var obj = new ReverseInteger();
        System.out.println(obj.reverse(-123)); // Output: 321
    }
}
