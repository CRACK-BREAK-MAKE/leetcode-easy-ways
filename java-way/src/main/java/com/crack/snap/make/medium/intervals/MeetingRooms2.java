package com.crack.snap.make.medium.intervals;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * Problem: Given an array of meeting time intervals, find the minimum number of conference rooms required.
 * @author Mohan Sharma
 */
public class MeetingRooms2 {

    /**
     * If we think in a brute force way, for a given moment of time we can check how many meetings are going on by iterating over all
     * the meeting and checking if the start of a meeting is less than or equal to the time and end of a meeting is greater than or
     * equal to the time. If Yes, meaning a meeting is going on at that time, so we need to increment the count of rooms as a new room
     * will be required.
     *
     * And how many moments of time we need to check? We can check the minimum start time of all the meetings to the maximum end time
     * of all the meetings.
     *
     * @param intervals
     * @return
     */
    public int minMeetingRoomsBruteForce(int[][] intervals) {
        if (intervals == null || intervals.length == 0) {
            return 0;
        }
        var minTime = Integer.MAX_VALUE;
        var maxTime = Integer.MIN_VALUE;

        for (var interval : intervals) {
            minTime = Math.min(minTime, interval[0]);
            maxTime = Math.max(maxTime, interval[1]);
        }

        var rooms = 0;
        for (var time = minTime; time <= maxTime; time++) {
            var count = 0;
            for (var interval : intervals) {
                if (interval[0] <= time && interval[1] >= time) {
                    count++;
                }
                rooms = Math.max(rooms, count);
            }
        }
        return rooms;
    }

    /**
     * If you think about the brute force solution, we are checking for every moment of time. Instead -
     * 1. We should only care about moments when the count changes
     * 2. The count will only change when a meeting starts or ends
     * 3. Between these events, the count will always remain constant
     *
     * That means we can associate each meeting with two events:
     * - Start of the meeting (when a room is needed) with a value of +1
     * - End of the meeting (when a room is freed) with a value of -1
     * Then when we sort these events by time, now whenever we encounter a start event, we increment the count of rooms needed,
     * and whenever we encounter an end event, we decrement the count of rooms needed. And we can keep track of the maximum
     * number of rooms needed at any point in time.
     *
     */
    public int minMeetingRoomsUsingEvents(int[][] intervals) {
        if (intervals == null || intervals.length == 0) {
            return 0;
        }
        var events = new ArrayList<int[]>();
        for (var interval :  intervals) {
            events.add(new int[]{interval[0], 1}); // Start of meeting
            events.add(new int[]{interval[1], -1}); // End of meeting
        }
        // if both events have same time, end event should come first meaning end the meeting before starting a new one
        events.sort((a, b) -> a[0] == b[0] ? a[1] - b[1] : a[0] - b[0]);

        var maxRooms = 0; var currentRooms = 0;
        for (var event : events) {
            currentRooms += event[1]; // Increment or decrement count based on start or end of meeting
            // Update the maximum number of rooms needed
            maxRooms = Math.max(maxRooms, currentRooms);
        }
        return maxRooms;
    }

    /**
     * Let's think about a room manager who is responsible for managing the rooms.
     * For every meeting the manager will check if the earliest ending meeting is over or not. If it is over, the manager will free
     * that room and assign it to the new meeting.
     * If it is not over, the manager will assign a new room for the new meeting. The maximum number of rooms needed at any point in time
     * will be the answer.
     */
    public int minMeetingRoomsUsingHeap(int[][] intervals) {
        if (intervals == null || intervals.length == 0) {
            return 0;
        }
        var heap = new PriorityQueue<Integer>();
        // Sort the intervals by start time
        Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
        for (var interval : intervals) {
            if (!heap.isEmpty() && heap.peek() <= interval[0]) {
                heap.poll();
            }
            heap.add(interval[0]);
        }
        return heap.size();
    }

    public static void main(String[] args) {
        var obj = new MeetingRooms2();
        var intervals = new int[][]{{0, 30}, {5, 10}, {15, 20}};
        System.out.println(obj.minMeetingRoomsBruteForce(intervals)); // Output: 2
    }
}
