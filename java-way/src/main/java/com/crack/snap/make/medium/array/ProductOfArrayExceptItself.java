package com.crack.snap.make.medium.array;

import java.util.Arrays;

/**
 * This class provides methods to find the product of an array except itself.
 *
 * Author: Mohan Sharma
 */
public class ProductOfArrayExceptItself {

    /**
     * Intuition: To find the product of an array except itself, we need to calculate the product of all elements to the left and right of each index.
     * For example, given the array [1, 2, 3, 4], the product array except itself would be [24, 12, 8, 6]. - For index 0, the product is 2 * 3 * 4 = 24. -
     * For index 1, the product is 1 * 3 * 4 = 12. - For index 2, the product is 1 * 2 * 4 = 8. - For index 3, the product is 1 * 2 * 3 = 6.
     * Approach: We can use two arrays to keep track of the left and right products for each index. 1.
     * Calculate the left product for each index and store it in the prefix array. 2.
     * Calculate the right product for each index and store it in the suffix array. 3.
     * Multiply the left and right products for each index to get the final result.
     * Time Complexity: O(n), where n is the number of elements in the array. Space Complexity: O(n), for storing the left and right products.
     * @param nums the array of integers
     * @return an array of the product of all elements except itself
     */
    public int[] productExceptSelf2NSpace(int[] nums) {
        var prefix = new int[nums.length];
        var leftProduct = 1;

        // Calculate the left product for each index
        for (var i = 0; i < nums.length; i++) {
            prefix[i] = leftProduct;
            leftProduct *= nums[i];
        }

        var suffix = new int[nums.length];
        var rightProduct = 1;

        // Calculate the right product for each index and multiply with the left product
        for (var i = nums.length - 1; i >= 0; i--) {
            suffix[i] = rightProduct;
            rightProduct *= nums[i];
            nums[i] = prefix[i] * suffix[i];
        }

        return nums;
    }

    /**
     * Intuition: It is very similar to the above logic but while we used two arrays to keep track of left and right product,
     * we can use the result array itself to keep track of the left product. Then, we scan the result array from right
     * and use the input array to calculate the right product and store it in the result array. For example, given the array [1, 2, 3, 4],
     * the product array except itself would be [24, 12, 8, 6]. - For index 0, the product is 2 * 3 * 4 = 24. -
     * For index 1, the product is 1 * 3 * 4 = 12. - For index 2, the product is 1 * 2 * 4 = 8. -
     * For index 3, the product is 1 * 2 * 3 = 6.
     * Approach:
     * 1. Calculate the left product for each index and store it in the result array.
     * 2. Calculate the right product for each index and multiply it with the left product in the result array.
     * Time Complexity: O(n), where n is the number of elements in the array.
     * Space Complexity: O(1), since we are using the output array itself to store the left product.
     * @param nums the array of integers
     * @return an array of the product of all elements except itself
     */
    public int[] productExceptSelfUsingConstantSpace(int[] nums) {
        var result = new int[nums.length];
        var leftProduct = 1;

        // Calculate the left product for each index and store it in the result array
        for (var i = 0; i < nums.length; i++) {
            result[i] = leftProduct;
            leftProduct *= nums[i];
        }

        var rightProduct = 1;

        // Calculate the right product for each index and multiply with the left product in the result array
        for (var i = nums.length - 1; i >= 0; i--) {
            result[i] *= rightProduct;
            rightProduct *= nums[i];
        }

        return result;
    }

    public static void main(String[] args) {
        var productOfArrayExceptItself = new ProductOfArrayExceptItself();
        System.out.println(Arrays.toString(productOfArrayExceptItself.productExceptSelfUsingConstantSpace(new int[]{1, 2, 3, 4})));
    }
}
