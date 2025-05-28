# Understanding Heap/Priority Queue Patterns

## The Core Intuition

A heap is your go-to data structure when you need to **repeatedly access the minimum or maximum element** from a changing collection. Think of it as a "smart sorting" that only keeps track of what you actually need.

## Key Variations and When to Use Each

### 1. Min-Heap vs Max-Heap Decision

**Use Min-Heap when:**
- You need the smallest element repeatedly
- Finding closest/nearest elements
- Merging sorted sequences
- Dijkstra's algorithm (shortest path)

**Use Max-Heap when:**
- You need the largest element repeatedly
- Finding top-K largest elements
- Scheduling (highest priority first)

### 2. Single Heap vs Dual Heap Patterns

#### Single Heap Pattern
```java
PriorityQueue<Integer> heap = new PriorityQueue<>();  // Min-heap by default
// or
PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
```

#### Dual Heap Pattern (Two Heaps)
```java
PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder()); // Left half
PriorityQueue<Integer> minHeap = new PriorityQueue<>(); // Right half
```

## Developing Your Pattern Recognition

### Pattern 1: "Top K" Problems
**Intuition**: When you see "top K", "K largest", "K smallest" → Think heap with size K

```java
// For K largest elements - use MIN heap of size K
PriorityQueue<Integer> minHeap = new PriorityQueue<>();
for (int num : nums) {
    minHeap.offer(num);
    if (minHeap.size() > k) {
        minHeap.poll(); // Remove smallest
    }
}
// Result: minHeap contains K largest elements
```

**Why this works**: The min-heap kicks out smaller elements, keeping only the K largest.

### Pattern 2: "Median/Middle Element" Problems
**Intuition**: When you need the middle element(s) from a stream → Think dual heap

```java
// Dual heap for median
PriorityQueue<Integer> left = new PriorityQueue<>(Collections.reverseOrder()); // max-heap
PriorityQueue<Integer> right = new PriorityQueue<>(); // min-heap

// Invariant: left.size() == right.size() OR left.size() == right.size() + 1
// Median is either left.peek() or (left.peek() + right.peek()) / 2.0
```

**Why this works**: Left heap stores smaller half (max at top), right heap stores larger half (min at top).

### Pattern 3: "Merge K Sorted" Problems
**Intuition**: When merging multiple sorted sequences → Think min-heap with custom comparator

```java
// For merging K sorted lists
PriorityQueue<ListNode> heap = new PriorityQueue<>((a, b) -> a.val - b.val);
// Add first node from each list
// Always take the smallest, add its next node back
```

### Pattern 4: "Closest/Nearest K Points" Problems
**Intuition**: Distance-based problems → Think heap with custom distance comparator

```java
PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> 
    Integer.compare(a[0]*a[0] + a[1]*a[1], b[0]*b[0] + b[1]*b[1]));
```

### Pattern 5: "Scheduling/Intervals" Problems
**Intuition**: When you need to track earliest/latest events → Think heap with time-based ordering

## Decision Tree for Heap Problems

1. **Do I need repeated access to min/max?** → Yes, consider heap
2. **Do I need exactly K elements?** → Single heap of size K
3. **Do I need the median or middle elements?** → Dual heap
4. **Am I merging sorted data?** → Min-heap with iterators/pointers
5. **Am I dealing with distances/priorities?** → Heap with custom comparator

## Common Problem Categories & Examples

### Category 1: Top K Problems
- **Kth Largest Element in Array**: Use min-heap of size K
- **Top K Frequent Elements**: Use min-heap with frequency comparator
- **K Closest Points to Origin**: Use max-heap of size K (or min-heap with all points)

### Category 2: Median/Stream Problems
- **Find Median from Data Stream**: Dual heap pattern
- **Sliding Window Median**: Dual heap with removal logic

### Category 3: Merge Problems
- **Merge k Sorted Lists**: Min-heap with list nodes
- **Smallest Range Covering Elements**: Min-heap tracking current elements from each list

### Category 4: Scheduling/Simulation
- **Meeting Rooms II**: Min-heap tracking end times
- **Task Scheduler**: Max-heap for task frequencies
- **Process Tasks Using Servers**: Multiple heaps for available/busy servers

## Implementation Tips & Common Gotchas

### 1. Custom Comparators
```java
// For max-heap
PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> b - a);
// or
PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());

// For custom objects
PriorityQueue<Task> heap = new PriorityQueue<>((a, b) -> {
    if (a.priority != b.priority) return b.priority - a.priority; // Higher priority first
    return a.time - b.time; // Earlier time as tiebreaker
});
```

### 2. Heap Size Management
```java
// Maintain heap of size K
if (heap.size() > k) {
    heap.poll();
}

// For dual heap balancing
private void balance() {
    if (left.size() > right.size() + 1) {
        right.offer(left.poll());
    } else if (right.size() > left.size()) {
        left.offer(right.poll());
    }
}
```

### 3. Handling Edge Cases
- Empty heap checks: `if (!heap.isEmpty())`
- Heap size checks before polling
- Balancing dual heaps after every operation

## Time Complexity Patterns
- **Insertion**: O(log n)
- **Deletion (poll)**: O(log n)
- **Peek**: O(1)
- **Building heap from array**: O(n)

## Space Complexity
- Usually O(k) for top-K problems
- O(n) for median/stream problems

## Red Flags That Suggest Heap Usage
- "Find the Kth largest/smallest"
- "Top K" anything
- "Median" or "middle element"
- "Closest K points"
- "Merge K sorted"
- "Schedule" or "earliest/latest"
- "Most/least frequent"

## Template Code Patterns

### Single Heap Template
```java
PriorityQueue<Type> heap = new PriorityQueue<>(comparator);
for (element : elements) {
    heap.offer(element);
    // Maintain size constraint if needed
    if (heap.size() > k) heap.poll();
}
```

### Dual Heap Template
```java  
PriorityQueue<Integer> left = new PriorityQueue<>(Collections.reverseOrder());
PriorityQueue<Integer> right = new PriorityQueue<>();

public void addNumber(int num) {
    if (left.isEmpty() || num <= left.peek()) {
        left.offer(num);
    } else {
        right.offer(num);
    }
    balance();
}
```

The key to mastering heap problems is recognizing when you need **efficient access to extremes** (min/max) from a **changing dataset**. Once you see this pattern, the specific heap variation becomes clearer based on the exact requirements.
