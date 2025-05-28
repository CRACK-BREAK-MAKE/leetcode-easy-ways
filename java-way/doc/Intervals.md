# Understanding Intervals Patterns

## The Core Intuition

Interval problems are fundamentally about **relationships between ranges**. The key insight is that most interval problems become much simpler when you **sort the intervals first** (usually by start time), then process them in order.

## Key Interval Relationships

Before diving into patterns, understand these basic relationships:

```
Interval A: [a1, a2]
Interval B: [b1, b2]

1. Non-overlapping: a2 < b1 or b2 < a1
2. Overlapping: a2 >= b1 and b2 >= a1  
3. A contains B: a1 <= b1 and a2 >= b2
4. Adjacent: a2 == b1 or b2 == a1
```

## The Master Pattern: Sort First, Then Process

**95% of interval problems follow this pattern:**
1. Sort intervals (usually by start time)
2. Iterate through sorted intervals
3. Make decisions based on current vs previous interval

## Pattern Recognition Guide

### Pattern 1: "Merge Overlapping Intervals"
**Keywords**: "merge", "combine", "overlapping intervals"
**Intuition**: Sort by start, then merge consecutive overlapping intervals

```java
// Template for merging intervals
public int[][] merge(int[][] intervals) {
    Arrays.sort(intervals, (a, b) -> a[0] - b[0]); // Sort by start time
    List<int[]> result = new ArrayList<>();
    
    for (int[] current : intervals) {
        // If result is empty or no overlap with last interval
        if (result.isEmpty() || result.get(result.size() - 1)[1] < current[0]) {
            result.add(current); // Add as new interval
        } else {
            // Merge with last interval
            result.get(result.size() - 1)[1] = Math.max(result.get(result.size() - 1)[1], current[1]);
        }
    }
    return result.toArray(new int[result.size()][]);
}
```

### Pattern 2: "Insert New Interval"
**Keywords**: "insert", "add interval", "maintain sorted order"
**Intuition**: Find the right position, handle overlaps on both sides

```java
// Template for inserting interval
public int[][] insert(int[][] intervals, int[] newInterval) {
    List<int[]> result = new ArrayList<>();
    int i = 0;
    
    // Add all intervals before newInterval
    while (i < intervals.length && intervals[i][1] < newInterval[0]) {
        result.add(intervals[i++]);
    }
    
    // Merge overlapping intervals with newInterval
    while (i < intervals.length && intervals[i][0] <= newInterval[1]) {
        newInterval[0] = Math.min(newInterval[0], intervals[i][0]);
        newInterval[1] = Math.max(newInterval[1], intervals[i][1]);
        i++;
    }
    result.add(newInterval);
    
    // Add remaining intervals
    while (i < intervals.length) {
        result.add(intervals[i++]);
    }
    
    return result.toArray(new int[result.size()][]);
}
```

### Pattern 3: "Maximum Concurrent Intervals"
**Keywords**: "maximum overlap", "peak usage", "concurrent meetings"
**Intuition**: Use event-based thinking - track starts and ends separately

```java
// Template for maximum concurrent intervals
public int maxConcurrent(int[][] intervals) {
    List<int[]> events = new ArrayList<>();
    
    // Create events: [time, type] where type: 1=start, -1=end
    for (int[] interval : intervals) {
        events.add(new int[]{interval[0], 1});     // Start event
        events.add(new int[]{interval[1], -1});    // End event
    }
    
    // Sort by time, end events before start events at same time
    events.sort((a, b) -> a[0] == b[0] ? a[1] - b[1] : a[0] - b[0]);
    
    int maxConcurrent = 0, current = 0;
    for (int[] event : events) {
        current += event[1];
        maxConcurrent = Math.max(maxConcurrent, current);
    }
    return maxConcurrent;
}
```

### Pattern 4: "Minimum Resources/Rooms Needed"
**Keywords**: "minimum rooms", "minimum resources", "scheduling"
**Intuition**: Same as maximum concurrent - peak overlap determines minimum resources

**Alternative Approach using Heap:**
```java
public int minMeetingRooms(int[][] intervals) {
    Arrays.sort(intervals, (a, b) -> a[0] - b[0]); // Sort by start time
    PriorityQueue<Integer> heap = new PriorityQueue<>(); // Min-heap for end times
    
    for (int[] interval : intervals) {
        // If current meeting starts after earliest ending meeting
        if (!heap.isEmpty() && heap.peek() <= interval[0]) {
            heap.poll(); // Reuse the room
        }
        heap.offer(interval[1]); // Add current meeting's end time
    }
    return heap.size(); // Number of rooms needed
}
```

### Pattern 5: "Interval Intersection"
**Keywords**: "intersection", "common time", "overlap between sets"
**Intuition**: Two pointers, advance the one with earlier end time

```java
// Template for interval intersection
public int[][] intervalIntersection(int[][] A, int[][] B) {
    List<int[]> result = new ArrayList<>();
    int i = 0, j = 0;
    
    while (i < A.length && j < B.length) {
        // Find intersection
        int start = Math.max(A[i][0], B[j][0]);
        int end = Math.min(A[i][1], B[j][1]);
        
        if (start <= end) { // Valid intersection
            result.add(new int[]{start, end});
        }
        
        // Move pointer of interval that ends earlier
        if (A[i][1] < B[j][1]) {
            i++;
        } else {
            j++;
        }
    }
    return result.toArray(new int[result.size()][]);
}
```

### Pattern 6: "Remove Intervals to Avoid Overlap"
**Keywords**: "remove", "non-overlapping", "minimum removals"
**Intuition**: Greedy - always keep interval with earlier end time

```java
// Template for minimum interval removals
public int eraseOverlapIntervals(int[][] intervals) {
    Arrays.sort(intervals, (a, b) -> a[1] - b[1]); // Sort by END time
    
    int count = 0;
    int lastEnd = intervals[0][1];
    
    for (int i = 1; i < intervals.length; i++) {
        if (intervals[i][0] < lastEnd) { // Overlap detected
            count++; // Remove current interval
        } else {
            lastEnd = intervals[i][1]; // Update last end time
        }
    }
    return count;
}
```

## Decision Tree for Interval Problems

1. **Am I merging overlapping intervals?** → Pattern 1 (Sort by start, merge consecutive)
2. **Am I inserting a new interval?** → Pattern 2 (Three-phase approach)
3. **Do I need maximum concurrent/overlapping intervals?** → Pattern 3 (Event-based or heap)
4. **Do I need minimum resources/rooms?** → Pattern 4 (Same as max concurrent)
5. **Am I finding intersections between two sets?** → Pattern 5 (Two pointers)
6. **Am I removing intervals to avoid overlap?** → Pattern 6 (Greedy, sort by end time)

## Sorting Strategy Decision

**Sort by START time when:**
- Merging intervals
- Inserting intervals
- General interval processing

**Sort by END time when:**
- Minimizing removals/conflicts
- Greedy scheduling problems
- Activity selection

**Event-based sorting when:**
- Finding maximum concurrent intervals
- Peak usage problems

## Common Problem Categories & Examples

### Category 1: Basic Interval Operations
- **Merge Intervals**: Classic merge pattern
- **Insert Interval**: Three-phase insertion
- **Interval List Intersections**: Two-pointer technique

### Category 2: Scheduling & Resource Allocation
- **Meeting Rooms**: Count overlaps (Pattern 3/4)
- **Meeting Rooms II**: Minimum rooms needed (Pattern 4)
- **Non-overlapping Intervals**: Minimum removals (Pattern 6)

### Category 3: Range Queries & Updates
- **Range Sum Query - Mutable**: Segment tree with intervals
- **Data Stream as Disjoint Intervals**: Dynamic interval management
- **Employee Free Time**: Multi-list intersection

### Category 4: Advanced Interval Problems
- **Minimum Number of Arrows**: Greedy interval covering
- **Calendar Booking**: Dynamic interval insertion with conflict detection
- **Rectangle Overlap**: 2D interval intersection

## Implementation Tips & Common Gotchas

### 1. Boundary Conditions
```java
// Inclusive vs exclusive boundaries
[1, 3] and [3, 5] - Do they overlap?
// Depends on problem definition:
// Inclusive: Yes (they share point 3)
// Exclusive: No (end of first doesn't overlap start of second)
```

### 2. Sorting Stability
```java
// When intervals have same start/end time, order matters
Arrays.sort(intervals, (a, b) -> {
    if (a[0] != b[0]) return a[0] - b[0]; // Primary: start time
    return a[1] - b[1]; // Secondary: end time
});
```

### 3. Edge Cases to Consider
- Empty interval list
- Single interval
- All intervals identical
- No overlaps vs complete overlaps

## Time Complexity Patterns
- **Sorting**: O(n log n) - dominates most interval problems
- **Processing**: O(n) - single pass after sorting
- **Overall**: O(n log n) for most interval problems

## Space Complexity
- **In-place operations**: O(1) extra space
- **Building result**: O(n) for result storage
- **Heap-based solutions**: O(n) for heap storage

## Red Flags That Suggest Interval Patterns
- "Merge overlapping"
- "Maximum concurrent/simultaneous"
- "Minimum rooms/resources needed"
- "Schedule without conflict"
- "Insert new appointment"
- "Find intersection/common time"
- "Remove minimum intervals"
- Words: intervals, ranges, meetings, appointments, events, schedules

## Template Selection Guide

### Quick Decision Matrix:
| Problem Type | Primary Sort | Algorithm | Time Complexity |
|-------------|-------------|-----------|----------------|
| Merge overlapping | Start time | Linear scan | O(n log n) |
| Insert interval | Not needed | Three-phase | O(n) |
| Max concurrent | Event-based | Event processing | O(n log n) |
| Min resources | Start time | Heap or events | O(n log n) |
| Intersection | Not needed | Two pointers | O(m + n) |
| Min removals | End time | Greedy | O(n log n) |

The key insight is that **interval problems are really about time/space relationships**, and sorting almost always reveals the optimal processing order. Once sorted, most problems reduce to simple linear scans with clear decision rules.
