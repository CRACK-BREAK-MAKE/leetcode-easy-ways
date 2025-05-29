# Understanding Greedy Algorithm Pattern

## What is a Greedy Algorithm?

A greedy algorithm makes the locally optimal choice at each step, hoping to find a global optimum. The key insight is that for certain problems, making the best immediate choice leads to the best overall solution.

**Core Principle**: At each step, choose the option that looks best right now, without considering future consequences.

## The Greedy Choice Property

For a greedy algorithm to work, the problem must have the **greedy choice property**:
- A globally optimal solution can be arrived at by making locally optimal choices
- The choice made at each step is never reconsidered

## Common Greedy Algorithm Patterns

### Pattern 1: Activity Selection / Interval Scheduling
**When to use**: Selecting maximum number of non-overlapping intervals

**Key Insight**: Always pick the activity that finishes earliest

```java
// Meeting Rooms II - Minimum rooms needed
int minMeetingRooms(int[][] intervals) {
    // Greedy choice: Always assign meeting to the room that frees up earliest
    PriorityQueue<Integer> heap = new PriorityQueue<>(); // min heap of end times
    Arrays.sort(intervals, (a, b) -> a[0] - b[0]); // Sort by start time
    
    for (int[] interval : intervals) {
        if (!heap.isEmpty() && heap.peek() <= interval[0]) {
            heap.poll(); // Reuse room
        }
        heap.offer(interval[1]); // Schedule meeting end time
    }
    return heap.size();
}
```

### Pattern 2: Fractional Knapsack
**When to use**: Maximizing value with weight constraints, items can be fractional

**Key Insight**: Always pick items with highest value-to-weight ratio first

```java
// Fractional Knapsack
double fractionalKnapsack(Item[] items, int capacity) {
    // Greedy choice: Pick items with highest value/weight ratio
    Arrays.sort(items, (a, b) -> Double.compare(b.value/b.weight, a.value/a.weight));
    
    double totalValue = 0;
    for (Item item : items) {
        if (capacity >= item.weight) {
            totalValue += item.value;
            capacity -= item.weight;
        } else {
            totalValue += (item.value * capacity) / item.weight;
            break;
        }
    }
    return totalValue;
}
```

### Pattern 3: Huffman Coding / Merge Operations
**When to use**: Minimizing total cost when combining elements

**Key Insight**: Always combine the two smallest elements first

```java
// Minimum Cost to Connect Sticks
int connectSticks(int[] sticks) {
    // Greedy choice: Always combine two shortest sticks
    PriorityQueue<Integer> pq = new PriorityQueue<>();
    for (int stick : sticks) pq.offer(stick);
    
    int totalCost = 0;
    while (pq.size() > 1) {
        int first = pq.poll();
        int second = pq.poll();
        int cost = first + second;
        totalCost += cost;
        pq.offer(cost);
    }
    return totalCost;
}
```

### Pattern 4: Two Pointers Greedy
**When to use**: Pairing elements optimally from both ends

**Key Insight**: Use two pointers to make greedy choices from both ends

```java
// Container With Most Water
int maxArea(int[] height) {
    // Greedy choice: Always move the pointer with smaller height
    int left = 0, right = height.length - 1;
    int maxWater = 0;
    
    while (left < right) {
        int water = Math.min(height[left], height[right]) * (right - left);
        maxWater = Math.max(maxWater, water);
        
        // Greedy: Move the shorter line
        if (height[left] < height[right]) {
            left++;
        } else {
            right--;
        }
    }
    return maxWater;
}
```

### Pattern 5: Sorting-Based Greedy
**When to use**: When optimal ordering reveals the greedy choice

**Key Insight**: Sort by some criteria, then make greedy choices in that order

```java
// Non-overlapping Intervals - Remove minimum intervals
int eraseOverlapIntervals(int[][] intervals) {
    // Greedy choice: Keep intervals that end earliest
    Arrays.sort(intervals, (a, b) -> a[1] - b[1]); // Sort by end time
    
    int count = 0;
    int end = Integer.MIN_VALUE;
    
    for (int[] interval : intervals) {
        if (interval[0] >= end) {
            end = interval[1]; // Keep this interval
        } else {
            count++; // Remove this interval
        }
    }
    return count;
}
```

## How to Identify Greedy Problems

### 1. **Optimization Keywords**
- "Maximum/Minimum number of..."
- "Optimal arrangement/assignment..."
- "Least/Most efficient way..."

### 2. **Problem Characteristics**
- **Local choices affect global outcome**: Each decision impacts the final result
- **Irreversible decisions**: Once you make a choice, you don't reconsider it
- **Sorting often helps**: Many greedy problems involve sorting first
- **Optimal substructure**: Optimal solution contains optimal solutions to subproblems

### 3. **Common Problem Types**
- **Scheduling problems**: Meeting rooms, task scheduling
- **Resource allocation**: Fractional knapsack, gas stations
- **Graph problems**: Minimum spanning tree (Kruskal's, Prim's)
- **String problems**: Huffman coding, parentheses matching

## Developing Your Greedy Intuition

### Step 1: Identify the Greedy Choice
Ask yourself: "What is the most obvious 'best' choice at each step?"

Examples:
- **Activity Selection**: Choose activity that ends earliest
- **Fractional Knapsack**: Choose item with highest value/weight ratio
- **Dijkstra's Algorithm**: Choose vertex with smallest distance
- **Huffman Coding**: Combine two nodes with smallest frequency

### Step 2: Prove the Greedy Choice is Safe
You need to prove that making the greedy choice doesn't prevent finding an optimal solution.

**Proof techniques**:
1. **Exchange argument**: Show that any optimal solution can be modified to include the greedy choice without making it worse
2. **Cut-and-paste**: Show that replacing a non-greedy choice with a greedy choice maintains optimality

### Step 3: Show Optimal Substructure
After making the greedy choice, the remaining problem should be a smaller instance of the same problem.

## Common Greedy Algorithm Categories

### 1. **Interval Problems**
- **Pattern**: Sort intervals, then process greedily
- **Examples**: Meeting Rooms, Non-overlapping Intervals, Merge Intervals
- **Key insight**: Usually sort by start or end time

### 2. **Array Manipulation**
- **Pattern**: Sort array, then make greedy choices
- **Examples**: Assign Cookies, Two City Scheduling, Minimum Number of Arrows
- **Key insight**: Sorting reveals the optimal order for decisions

### 3. **String Problems**
- **Pattern**: Process characters greedily based on some criteria
- **Examples**: Remove K Digits, Reorganize String, Valid Parenthesis String
- **Key insight**: Often involves maintaining some invariant while processing

### 4. **Graph Problems**
- **Pattern**: Always choose the "best" edge or vertex available
- **Examples**: Minimum Spanning Tree, Shortest Path, Network Flow
- **Key insight**: Local optimality leads to global optimality

### 5. **Mathematical Problems**
- **Pattern**: Use mathematical properties to determine greedy choice
- **Examples**: Jump Game, Gas Station, Candy Distribution
- **Key insight**: Mathematical insight reveals why greedy works

## When Greedy Doesn't Work

### Classic Counter-examples:
1. **0/1 Knapsack**: Can't break items, so greedy by value/weight ratio fails
2. **Longest Path**: Greedy shortest path algorithms don't work for longest path
3. **Traveling Salesman**: Nearest neighbor heuristic doesn't guarantee optimality

### How to Recognize When Greedy Fails:
1. **Try small examples**: If greedy fails on small cases, it won't work
2. **Look for dependencies**: If future choices depend heavily on current choices
3. **Check for counterexamples**: Try to construct cases where greedy fails

## Debugging Greedy Solutions

### Common Issues:
1. **Wrong sorting criteria**: The greedy choice isn't actually optimal
2. **Missing edge cases**: Algorithm works for general case but fails on boundaries
3. **Incorrect greedy choice**: The locally optimal choice isn't globally optimal

### Debugging Process:
1. **Verify the greedy choice**: Is your local optimality criterion correct?
2. **Test on small examples**: Walk through your algorithm step by step
3. **Check edge cases**: Empty input, single element, all elements same
4. **Prove correctness**: Can you prove why the greedy choice works?

## Time Complexity Patterns

Greedy algorithms are usually efficient:
- **Sorting-based**: O(n log n) due to sorting, then O(n) for greedy choices
- **Heap-based**: O(n log n) for operations involving priority queues
- **Linear scan**: O(n) when no sorting is needed
- **Graph algorithms**: O(E log V) for algorithms like Dijkstra's

The efficiency comes from making each decision exactly once, without backtracking or reconsidering previous choices.
