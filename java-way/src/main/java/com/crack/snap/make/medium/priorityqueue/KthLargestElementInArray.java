package com.crack.snap.make.medium.priorityqueue;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @author Mohan Sharma
 */
public class KthLargestElementInArray {

    /**
     * Problem: Given an integer array nums and an integer k, return the kth largest element in the array.
     * Note that it is the kth largest element in the sorted order, not the kth distinct element.
     * Can you solve it without sorting?
     *
     * Intuition: One approach would be to sort the array in descending order and return the kth element.
     * Time Complexity: O(NlogN) for sorting and O(1) for getting the kth element from the 0th index.
     * Space Complexity: O(1) in case of in-place sorting and O(N) if we use a new array to store the sorted elements.
     */
    public int findKthLargestWithSorting(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k <= 0) {
            return -1;
        }
        nums = Arrays.stream(nums).boxed().sorted(Comparator.reverseOrder()).mapToInt(i -> i).toArray();
        return nums[k -1];
    }

    /**
     * Intuition: We need to find the kth largest element in the array, meaning we can maintain an array of size k where we keep only
     * k number of largest elements. e.g. if arr = [3,2,3,1,2,4,5,5,6] & k = 4, our array would keep elements [4, 5, 5, 6] and
     * While constructing this array every time we might a larger element we need to remove the smallest element from the array
     * and shift the rest of the elements to the right in sorted order. The data structure which does this brilliantly is Binary Heap
     * Since we need the kth largest element, we can use Min Heap of size k so that the top of the heap will always be the kth largest
     */
    public int findKthLargest(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k <= 0) {
            return -1;
        }
        var minHeap = new PriorityQueue<Integer>(k);
        for (var num: nums) {
            minHeap.add(num);
            if (minHeap.size() > k) {
                minHeap.poll();
            }
        }
        return minHeap.isEmpty() ? -1 : minHeap.peek();
    }

    public static void main(String[] args) {
        var obj = new KthLargestElementInArray();
        System.out.println(obj.findKthLargestWithSorting(new int[]{3, 2, 1, 5, 6, 4}, 2)); // Output: 5
        System.out.println(obj.findKthLargestWithSorting(new int[]{3, 2, 3, 1, 2, 4, 5}, 4)); // Output: 3
        System.out.println(obj.findKthLargestWithSorting(new int[]{1}, 1)); // Output: 1
    }
}
