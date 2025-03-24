package com.crack.snap.make.medium.array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * This class provides methods to find the top K frequent elements in an array.
 *
 * Intuition:
 * To find the top K frequent elements, we need top frequent meaning we need to count the frequency of each element in the array.
 * Then, we need to identify the K elements with the highest frequencies. e.g. {(2: 3), (4: 3), (1:2)} if k is 2 then we returns [2, 4]
 *
 * There are multiple ways to solve this problem:
 * 1. We can sort the frequency map descending order of the values to get the top k with n + nlogn + k or
 * 1. Using a Max Heap (PriorityQueue) to keep track of the top K elements.
 * 2. Using Bucket Sort to group elements by their frequencies.
 *
 * Example:
 * Input: nums = [1, 1, 1, 2, 2, 3], k = 2
 * Output: [1, 2]
 * Explanation: The elements 1 and 2 are the most frequent elements in the array.
 *
 * Input: nums = [1], k = 1
 * Output: [1]
 * Explanation: The element 1 is the only element in the array, so it is the most frequent.
 *
 * @author Mohan Sharma
 */
public class TopKFrequent {

    /**
     * Finds the top K frequent elements using a Max Heap (PriorityQueue).
     *
     * Time Complexity:
     * - Building the frequency map: O(n), where n is the number of elements in the array.
     * - Adding elements to the max heap: O(n log n), since each insertion into the heap takes O(log n) time.
     * - Extracting the top K elements from the heap: O(k log n), since each extraction takes O(log n) time.
     * Overall time complexity: O(n log n)
     *
     * @param nums the array of integers
     * @param k the number of top frequent elements to return
     * @return an array of the top K frequent elements
     */
    public int[] topKFrequentUsingMaxHeap(int[] nums, int k) {
        if (nums == null || nums.length == 0) {
            return new int[]{};
        }
        if (nums.length == 1 && k == 1) {
            return nums;
        }

        // Step-1: Create and populate frequency count dictionary.
        var hashMap = new HashMap<Integer, Integer>();
        for (var num : nums) {
            var count = hashMap.getOrDefault(num, 0);
            hashMap.put(num, count + 1);
        }

        // Step-2: Create max heap (PriorityQueue) to store elements based on their frequencies.
        PriorityQueue<Map.Entry<Integer, Integer>> maxHeap = new PriorityQueue<>((a, b) -> b.getValue() - a.getValue());
        maxHeap.addAll(hashMap.entrySet());

        // Step-3: Extract the top K elements from the max heap.
        var result = new int[k];
        var dataIndex = 0;
        while (!maxHeap.isEmpty() && k-- > 0) {
            result[dataIndex++] = maxHeap.poll().getKey();
        }
        return result;
    }

    /**
     * Finds the top K frequent elements using Bucket Sort.
     *
     * Time Complexity:
     * - Building the frequency map: O(n), where n is the number of elements in the array.
     * - Creating and populating the bucket array: O(n), since we iterate over the frequency map and the array.
     * - Collecting the top K elements from the bucket array: O(n), since we iterate over the bucket array.
     * Overall time complexity: O(n)
     *
     * @param nums the array of integers
     * @param k the number of top frequent elements to return
     * @return an array of the top K frequent elements
     */
    public int[] topKFrequentBucketSort(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k == 0) {
            return new int[]{};
        }
        if (nums.length == 1 && k == 1) {
            return nums;
        }

        // Step-1: Create and populate frequency count dictionary.
        var hashMap = new HashMap<Integer, Integer>();
        for (var num : nums) {
            var count = hashMap.getOrDefault(num, 0);
            hashMap.put(num, count + 1);
        }

        // Step-2: Create bucket array and populate it.
        var result = new int[k];
        var dataIndex = 0;
        List<List<Integer>> bucket = new ArrayList<>(nums.length + 1);
        for (var i = 0; i < nums.length + 1; i++) {
            bucket.add(new ArrayList<>());
        }

        for (var entry : hashMap.entrySet()) {
            bucket.get(entry.getValue()).add(entry.getKey());
        }

        // Step-3: Iterate over the bucket array from the end, populate result until k > 0.
        for (var i = bucket.size() - 1; i >= 0; i--) {
            if (k == 0) {
                break;
            }
            for (var num : bucket.get(i)) {
                result[dataIndex++] = num;
                k--;
                if (k == 0) {
                    break;
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        var topKFrequent = new TopKFrequent();
        System.out.println(Arrays.toString(topKFrequent.topKFrequentUsingMaxHeap(new int[]{1, 1, 1, 2, 2, 3}, 2)));
        System.out.println(Arrays.toString(topKFrequent.topKFrequentBucketSort(new int[]{1, 1, 1, 2, 2, 3}, 2)));
    }
}
