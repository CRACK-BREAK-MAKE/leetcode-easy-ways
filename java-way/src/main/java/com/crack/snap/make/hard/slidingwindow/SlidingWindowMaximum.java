package com.crack.snap.make.hard.slidingwindow;

import java.util.ArrayDeque;
import java.util.Arrays;

/**
 * @author Mohan Sharma
 */
public class SlidingWindowMaximum {

    /**
     * You are given an array of integers nums, there is a sliding window of size k which is moving from the
     * very left of the array to the very right. You can only see the k numbers in the window.
     * Each time the sliding window moves right by one position.
     * Return the max sliding window.
     *
     * Example 1:
     * Input: nums = [1,3,-1,-3,5,3,6,7], k = 3
     * Output: [3,3,5,5,6,7]
     * Explanation:
     * Window position                Max
     * ---------------               -----
     *  [1  3  -1] -3  5  3  6  7       3
     *  1  [3  -1  -3] 5  3  6  7       3
     *  1  3  [-1  -3  5] 3  6  7       5
     *  1  3  -1  [-3  5  3] 6  7       5
     *  1  3  -1  -3  [5  3  6] 7       6
     *  1  3  -1  -3  5  [3  6  7]      7
     *
     *  Intuition: Brute force approach would be
     *  Take the first batch from index 0 to k and find the max in that batch,
     *  Then take the batch from 1 to 1 + k and find the max in that batch
     *  That means first loop will be from 0 to n - k + 1
     *  and the inner loop to find the max in the window will be from i to i + k
     *
     *  Time Complexity: O(k*(n-k)), where n is the length of the array. and k is the size of the window.
     *  Space Complexity: O(1), as we are using a constant amount of extra space.
     * @param nums
     * @param k
     * @return
     */
    public int[] maxSlidingWindowBruteForce(int[] nums, int k) {
        if (nums.length == 0 || k == 0) {
            return new int[0];
        }
        if (k == 1) {
            return nums;
        }
        var n = nums.length;
        var result = new int[n - k + 1];
        for (var i = 0; i < n - k + 1; i++) {
            for (int j = i; j < k; j++) {
                if (i == 0) {
                    result[i] = nums[j];
                } else {
                    result[i] = Math.max(result[i], nums[j]);
                }
            }
        }
        return result;
    }

    /**
     * Now if we want to optimise the solution in O(n), that means we have to scan the array only once but to obtain
     * the maximum in constant time, this is where we need monotonic array or queue. Also we need to maintain the size of the window
     * in the queue meaning at any time, the max size of the queue should be k means we will need to remove from the tail
     * of the queue which means we need double ended queue or deque
     *
     * Also if we think through, as we scan the array and start maintaining the monotonic deque, if the current index element
     * is large than the head of the deque, we will never use that head since the current index element is the maximum for
     * the sliding window that means we can remove that head but if the current index element is smaller than the head
     * we need to keep in the deque why because the current maximum might be flushed out if the window moves and this minimum
     * can become the maximum.
     * Also we should remove from the tail of the deque once if the size of the deque is greater than k
     *
     * The maximum value of the current sliding window is always at the head of the deque. This is because the deque
     * is maintained in a way that the largest element's index is always at the front (head) of the deque.
     *
     * Removing from the head: When the end pointer passes the valid window size, we remove elements from the head of the deque
     * if they are out of the current window. This ensures that the deque only contains indices of elements within the current window.
     *
     * Removing from the tail: When adding a new element, we remove elements from the tail of the deque
     * if they are smaller than the current element. This is because these smaller elements will never be the maximum
     * in any future window that includes the current element.
     * @param nums
     * @param k
     * @return
     */
    public int[] maxSlidingWindow(int[] nums, int k) {
        if (nums.length == 0 || k == 0) {
            return new int[0];
        }
        if (k == 1) {
            return nums;
        }
        var n = nums.length;
        var end = 0;
        var index = 0;
        var deque = new ArrayDeque<Integer>();
        var result = new int[n - k + 1];
        while (end < nums.length) {
            while (!deque.isEmpty() && deque.peek() < end - k + 1) {
                deque.poll();
            }

            while (!deque.isEmpty() && nums[deque.peekLast()] < nums[end]) {
                deque.pollLast();
            }
            deque.add(end);

            if (end >= k - 1) {
                result[index++] = nums[deque.peek()];
            }
            end++;
        }
        return result;
    }

    public static void main(String[] args) {
        var obj = new SlidingWindowMaximum();
        System.out.println(Arrays.toString(obj.maxSlidingWindow(new int[] {1, 3, -1, -3, 5, 3, 6, 7}, 3)));
        System.out.println(Arrays.toString(obj.maxSlidingWindow(new int[] {1}, 1)));
    }
}
