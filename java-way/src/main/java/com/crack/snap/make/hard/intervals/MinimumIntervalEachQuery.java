package com.crack.snap.make.hard.intervals;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * @author Mohan Sharma
 */
public class MinimumIntervalEachQuery {

    public int[] minIntervalBruteForce(int[][] intervals, int[] queries) {
        var result = new int[queries.length];
        var index = 0;
        for (var query : queries) {
            var minInterval = Integer.MAX_VALUE;
            for (var interval : intervals) {
                var startTime = interval[0];
                var endTime = interval[1];
                if (startTime <= query && query <= endTime) {
                    minInterval = Math.min(minInterval, endTime - startTime + 1);
                }
            }
            result[index++] = minInterval == Integer.MAX_VALUE ? -1 : minInterval;
        }
        return result;
    }

    /**
     * Pattern: Sweep Line + Heap
     * Intuition: Process queries in sorted order and maintain active intervals efficiently.
     *
     * Step 1: Why Sort Queries?
     * Sweep line approach: Process events (queries) from left to right
     * Maintain state: Keep track of "currently active" intervals
     * Avoid recomputation: Don't restart from scratch for each query
     *
     * Step 2: Brute Force → Optimal Thinking
     * Brute Force Issues:
     * Repeated work: For query=5, we check all intervals again even though we just processed query=4
     * No state: Don't remember which intervals were active for previous queries
     * Inefficient lookup: Linear search through all intervals for each query
     *
     * Optimal Approach:
     * Sort queries: Process in order to maintain state
     * Add intervals: When we reach their start point
     * Remove intervals: When we pass their end point
     * Use heap: To quickly find minimum size among active intervals
     *
     */
    public int[] minInterval(int[][] intervals, int[] queries) {
        // sort intervals by start time
        Arrays.sort(intervals, (a, b) -> a[0] - b[0]);

        // sort queries and keep track of original indices
        var queriesWithIndex = new int[queries.length][2];
        for (int i = 0; i < queries.length; i++) {
            queriesWithIndex[i] = new int[]{queries[i], i};
        }
        Arrays.sort(queriesWithIndex, (a, b) -> a[0] - b[0]);


        var minHeap = new PriorityQueue<int[]>((a, b) -> a[0] - b[0]);
        var result = new int[queries.length];
        var intervalIndex = 0;
        for (var queryWithIndex : queriesWithIndex) {
            // add intervals that start before or at the query time
            var query = queryWithIndex[0];
            var index = queryWithIndex[1];
            while (intervalIndex < intervals.length && intervals[intervalIndex][0] <= query) {
                var startTime = intervals[intervalIndex][0];
                var endTime = intervals[intervalIndex][1];
                minHeap.add(new int[]{endTime - startTime + 1, endTime});
                intervalIndex++;
            }
            // remove intervals that end before the query time
            while (!minHeap.isEmpty() && minHeap.peek()[1] < query) {
                minHeap.poll();
            }

            // use heap to find the minimum interval for the current query
            result[index] = minHeap.isEmpty() ? -1 : minHeap.peek()[0];
        }
        return result;
    }

    public static void main(String[] args) {
        var obj = new MinimumIntervalEachQuery();
        System.out.println(Arrays.toString(obj.minInterval(new int[][]{{1, 4}, {2, 4}, {3, 6}, {4, 4}}, new int[]{2, 3, 4, 5}))); // Output: [3, 3, 1, 4]
    }
}
