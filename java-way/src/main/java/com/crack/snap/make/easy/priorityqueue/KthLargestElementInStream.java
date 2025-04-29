package com.crack.snap.make.easy.priorityqueue;

/**
 * @author Mohan Sharma
 */

import java.util.PriorityQueue;

/**
 * Problem: You are part of a university admissions office and need to keep track of the kth highest test score from applicants in
 * real-time. This helps to determine cut-off marks for interviews and admissions dynamically as new applicants submit their scores.
 * You are tasked to implement a class which, for a given integer k, maintains a stream of test scores and continuously returns the
 * kth highest test score after a new score has been submitted. More specifically, we are looking for the kth highest score in the
 * sorted list of all scores.
 *
 * Implement the KthLargest class:
 * KthLargest(int k, int[] nums) Initializes the object with the integer k and the stream of test scores nums.
 * int add(int val) Adds a new test score val to the stream and returns the element representing the kth largest element in the
 * pool of test scores so far.
 *
 * Intuition: We need to return the Kth largest element from the array at any given time, that means we need to keep them sorted
 * Best DS to keep elements in sorted way is Priority Queue with O(logN) time complexity. Now if we keep the elements in Max Heap
 * then to get the kth largest, we need to iterate k elements but if we use Min Heap and keep the size of heap as k then the top
 * will always give the kth largest element. So we can use Min Heap of size k to get the kth largest element
 */
public class KthLargestElementInStream {

    private final PriorityQueue<Integer> queue;
    private final int k;

    public KthLargestElementInStream(int k, int[] nums) {
        this.k = k;
        queue = new PriorityQueue<>();
        for (var num: nums) {
            queue.add(num);
            if (queue.size() > k) {
                queue.poll();
            }
        }
    }

    public int add(int val) {
        queue.add(val);
        while (queue.size() > k) {
            queue.poll();
        }
        // this line ensures even if the queue size is less than k, it returns the smallest element from the queue
        return queue.peek();
    }
}
