package com.crack.snap.make.medium.linkedlist;

/**
 * @author Mohan Sharma
 */
public class FindTheDuplicateNumber {

    /**
     * Problem: Given an array of integers nums containing n + 1 integers where each integer is in the range [1, n] inclusive.
     * There is only one repeated number in nums, return this repeated number.
     * You must solve the problem without modifying the array nums and using only constant extra space.
     * Example:
     * Input: nums = [1,3,4,2,2]
     * Output: 2
     * Constraints:
     * 1 <= n <= 105
     * nums.length == n + 1
     * 1 <= nums[i] <= n
     * All the integers in nums appear only once except for precisely one integer which appears two or more times.
     *
     * Intuition: First approach we can take is brute force, where we use two loops and find the duplicate. Next approach we can use
     * is hashing, we can if the value already exists in set before adding it to the set. If it exists then we can return the value.
     *
     * Now if we analyse the constraints, we see that if the array size is let's say n + 1 i.e. 6, there will be elements in the range
     * 1 to n i.e. 1 to 5, so can we use the array elements as the index of the array but how do we find the duplicate in this case?
     * What if we read the element and consider that as an index and then we go to that index and make that index element as -ve. Now
     * if there is a duplicate then the next time we reach that index it will already be -ve, here we found the duplicate.
     *
     * Time Complexity is O(n) where n is the length of the array.
     * Space Complexity is O(1) as we are not using any extra space.
     *
     * @param nums
     * @return
     */
    public int findDuplicateWithArrayModified(int[] nums) {
        for (var num: nums) {
            // to avoid exception as we go from left to right, we are making the value -ve. What if at 0th index the element is 1,
            // so we will go to index 1 and make that -ve. Now if we reach index 1 again, we got -ve value which is not a valid index.
            var index = Math.abs(num);
            if (nums[index] < 0) {
                return Math.abs(num);
            }
            nums[index] = -nums[index]; // make the value at index as -ve
        }
        return -1; // this will never be reached as per the constraints, since there is always a duplicate
    }

    /**
     * If we take the above idea of using the array element as index, we can see that there will be a loop
     * e.g. nums = [ 1, 3, 4, 2, 2 ]
     *               0, 1, 2, 3, 4
     * Here we can see that when we go from 1st index or element 3 to 3rd index or element 2 and from element 2 to element 4 and
     * from 4 to 2 and again from 2 to 4. Hence, there is a loop, can we use Floyd's cycle detection algorithm to find the starting
     * of the loop, which will be the duplicate number always.
     */
    public int findDuplicate(int[] nums) {
        var slow = 0;
        var fast = 0;
        do {
            slow = nums[slow];
            fast = nums[nums[fast]];
        } while (slow != fast);
        slow = 0;
        while (slow != fast) {
            slow = nums[slow];
            fast = nums[fast];
        }
        return slow;
    }

    public static void main(String[] args) {
        var obj = new FindTheDuplicateNumber();
        System.out.println(obj.findDuplicateWithArrayModified(new int[]{1, 3, 4, 2, 2})); // 2
        System.out.println(obj.findDuplicateWithArrayModified(new int[]{3, 1, 3, 4, 2})); // 3
        System.out.println(obj.findDuplicateWithArrayModified(new int[]{1, 1})); // 1

    }
}
