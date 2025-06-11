package com.crack.snap.make.easy.intervals;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mohan Sharma
 */
public class MeetingRooms {

    public boolean canAttendMeetings(List<Interval> intervals) {
        if (intervals == null || intervals.isEmpty()) {
            return true;
        }
        intervals.sort((a, b) -> a.start - b.start);
        var previousEnd = intervals.get(0).end;
        for (var i = 1; i < intervals.size(); i++) {
            var interval = intervals.get(i);

            if (interval.start < previousEnd) {
                return false; // Overlapping intervals found
            } else {
                previousEnd = interval.end; // Update the end time to the current interval's end
            }
        }
        return true;
    }

    record Interval(int start, int end) {}

    public static void main(String[] args) {
        var obj = new MeetingRooms();
        var intervals = new ArrayList<Interval>();
        intervals.add(new Interval(0, 30));
        intervals.add(new Interval(5, 10));
        intervals.add(new Interval(15, 20));
        System.out.println(obj.canAttendMeetings(intervals)); // Output: false

        intervals.clear();
        intervals.add(new Interval(7, 10));
        intervals.add(new Interval(2, 4));
        System.out.println(obj.canAttendMeetings(intervals)); // Output: true
    }
}
