package com.crack.snap.make.easy.twopointer;

/**
 * @author Mohan Sharma
 */
public class ValidPalindrome {

    //Intuition:
    // We need to find a way where we compare the character from the left and the character from the right
    // check if they are equal and check till the middle of the string
    // if a character is not between 'a' to 'z' or 0 to 9, increment the left or decrement the right pointer
    public boolean isPalindrome(String s) {
        if (s.isBlank()) {
            return true;
        }
        var left = 0;
        var right = s.length() - 1;
        while (left < right) {
            char leftChar = Character.toLowerCase(s.charAt(left));
            char rightChar = Character.toLowerCase(s.charAt(right));
            if (!((leftChar >= 'a' && leftChar <= 'z') || (leftChar >= '0' && leftChar <= '9'))) {
                left++;
            }
            else if (!((rightChar >= 'a' && rightChar <= 'z') || (rightChar >= '0' && rightChar <= '9'))) {
                right--;
            }
            else if (leftChar != rightChar) {
                return false;
            } else {
                left++;
                right--;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        ValidPalindrome validPalindrome = new ValidPalindrome();
        System.out.println(validPalindrome.isPalindrome("A man, a plan, a canal: Panama"));
        System.out.println(validPalindrome.isPalindrome("race a car"));
    }
}
