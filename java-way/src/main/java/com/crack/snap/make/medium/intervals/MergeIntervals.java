package com.crack.snap.make.medium.intervals;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Problem: Given an array of intervals where intervals[i] = [starti, endi], merge all overlapping intervals, and
 * return an array of the non-overlapping intervals that cover all the intervals in the input.
 *
 * @author Mohan Sharma
 */
public class MergeIntervals {

    /**
     * Intuition: To merge overlapping intervals, we can sort the intervals by their start times.
     * Then, we can iterate through the sorted intervals and keep track of the last added interval.
     * If the current interval overlaps with the last added interval, we merge them by updating the end time of the last added interval.
     * Example:
     * Given intervals = [[1,3],[8,10],[2,6], [15,18]]
     * After sorting by start times, we have: [[1,3],[2,6],[8,10],[15,18]]
     * Let's visualize the intervals:
     * 1        3
     * |--------|
     *      2               6
     *      |---------------|
     *                              8       10
     *                              |-------|
     *                                                          15          18
     *                                                          |-----------|
     * * We start with the first interval [1,3] and add it to the result.
     * * Next, we check the second interval [2,6]. Since it overlaps with [1,3] (2 <= 3), we merge them into [1,6].
     * * Then, we check the third interval [8,10]. It does not overlap with [1,6], so we add it to the result.
     * * Finally, we check the last interval [15,18]. It also does not overlap with [8,10], so we add it to the result.
     *
     * Time Complexity: O(n log n) due to sorting the intervals.
     * Space Complexity: O(n) for storing the result in a new list.
     */
    public int[][] merge(int[][] intervals) {
        Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
        var result = new ArrayList<int[]>();

        for (var interval :  intervals) {
            if (result.isEmpty() || result.getLast()[1] < interval[0]) {
                result.add(interval);
            } else {
                result.getLast()[1] = Math.max(result.getLast()[1], interval[1]);
            }
        }

        return result.toArray(new int[result.size()][]);
    }
}
