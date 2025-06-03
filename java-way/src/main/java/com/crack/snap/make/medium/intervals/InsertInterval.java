package com.crack.snap.make.medium.intervals;

import java.util.ArrayList;

/**
 * Problem: You are given an array of non-overlapping intervals `intervals` where intervals[i] = [starti, endi] represent the start
 * and the end of the ith interval and intervals is sorted in ascending order by starti. You are also given an interval
 * newInterval = [start, end] that represents the start and end of another interval.
 * Insert newInterval into intervals such that intervals is still sorted in ascending order by starti and intervals still does not
 * have any overlapping intervals (merge overlapping intervals if necessary).
 * Return intervals after the insertion.
 * Note that you don't need to modify intervals in-place. You can make a new array and return it.
 *
 * @author Mohan Sharma
 */
public class InsertInterval {

    /**
     * Whenever we need to insert a new interval into a sorted list of intervals, we can follow these steps:
     * 1. Traverse the list of intervals and add all intervals that end before the new interval starts to the result.
     * 2. For all intervals that overlap with the new interval, merge them into the new interval by updating its start and end.
     *    Finally, add the new interval to the result.
     * 3. Add all remaining intervals that start after the new interval ends to the result.
     *
     * Example:
     * Given intervals = [[1,3],[6,9]] and newInterval = [2,5],
     * Step 1: Add intervals that end before newInterval starts:
     * In our example there are no such intervals, so we skip this step.
     * Step 2: Merge overlapping intervals:
     * - The first interval [1,3] overlaps with [2,5], since 1 <= 5 so we update newInterval to [1,5].
     * - And add the newInterval [1,5] to the result.
     * Step 3: Add remaining intervals:
     * In our example, the next interval [6,9] starts after newInterval ends, so we add it to the result.
     *
     * Time Complexity: O(n), where n is the number of intervals.
     * Space Complexity: O(n), for storing the result in a new list.
     */
    public int[][] insert(int[][] intervals, int[] newInterval) {
        if (intervals == null || intervals.length == 0) {
            return new int[][]{newInterval};
        }
        var result = new ArrayList<int[]>();
        var index = 0;
        while (index < intervals.length && intervals[index][1] < newInterval[0]) {
            result.add(intervals[index++]);
        }

        while (index < intervals.length && intervals[index][0] <= newInterval[1]) {
            newInterval[0] = Math.min(intervals[index][0], newInterval[0]);
            newInterval[1] = Math.max(intervals[index][1], newInterval[1]);
            index++;
        }
        result.add(newInterval);

        while (index < intervals.length) {
            result.add(intervals[index++]);
        }

        return result.toArray(new int[result.size()][]);
    }
}
