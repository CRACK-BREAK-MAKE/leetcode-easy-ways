package com.crack.snap.make.medium.intervals;

import java.util.Arrays;

/**
 * Problem: Given an array of intervals intervals where intervals[i] = [starti, endi], return the minimum number of intervals
 * you need to remove to make the rest of the intervals non-overlapping.
 * Note that intervals which only touch at a point are non-overlapping. For example, [1, 2] and [2, 3] are non-overlapping.
 *
 * @author Mohan Sharma
 */
public class NonOverlappingIntervals {

    /**
     * Intuition: To find the minimum number of intervals to remove, we can sort the intervals by their end times.
     * Then, we can iterate through the sorted intervals and keep track of the end time of the last added interval.
     * If the start time of the current interval is less than the end time of the last added interval,
     * it means there is an overlap, and we need to remove the current interval.
     * * The key idea is to always keep the interval with the earliest end time, as it allows us to accommodate more intervals later.
     *
     * Example:
     * Given intervals = [[1,2],[2,3],[3,4],[1,3]],
     * The sorted intervals by end time are: [[1,2],[2,3],[1,3],[3,4]].
     * It can be visualized as follows:
     * 1    2
     * |----|
     *     2    3
     *     |----|
     * 1        3
     * |--------|
     *          3    4
     *          |----|
     *
     * Initially we set previousEnd = 2
     * Then we iterate through the intervals:
     * 1. For interval [2, 3], since 2 >= previousEnd, we update previousEnd to 3.
     * 2. For interval [1, 3], since 1 < previousEnd (3), we have an overlap, so we increment the count of intervals to remove.
     * 3. For interval [3, 4], since 3 >= previousEnd, we update previousEnd to 4.
     * * The final count of intervals to remove is 1, as we need to remove the interval [1, 3] to make the rest non-overlapping.
     *
     * Time Complexity: O(n log n) due to sorting the intervals.
     * Space Complexity: O(1) as we are using a constant amount of space for variables.
     */
    public int eraseOverlapIntervals(int[][] intervals) {
        if (intervals == null || intervals.length == 0) {
            return 0;
        }
        Arrays.sort(intervals, (a, b) -> a[1] - b[1]);
        var count = 0;
        var previousEnd = intervals[0][1];
        for (var i = 1; i < intervals.length; i++) {
            var interval = intervals[i];
            if (interval[0] < previousEnd) {
                count++;
            } else {
                previousEnd = interval[1];
            }
        }
        return count;
    }
}
