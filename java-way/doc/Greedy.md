# Complete Greedy Algorithm Mastery Guide

## Overview
This comprehensive guide covers all greedy patterns needed for LeetCode 150 and interview success, from basic to advanced patterns with complete implementation strategies.

---

## Core Greedy Patterns

### Pattern 1: Activity Selection / Interval Scheduling
**When to use**: Selecting maximum number of non-overlapping intervals
**Key Insight**: Always pick the activity that finishes earliest
**Time Complexity**: O(n log n) for sorting + O(n) for processing
**Space Complexity**: O(1) to O(n) depending on implementation

```java
// Template for Interval Problems
int solveIntervalProblem(int[][] intervals) {
    if (intervals.length == 0) return 0;
    
    // Sort by end time for activity selection
    Arrays.sort(intervals, (a, b) -> a[1] - b[1]);
    
    int count = 1; // or 0 depending on problem
    int lastEnd = intervals[0][1];
    
    for (int i = 1; i < intervals.length; i++) {
        if (intervals[i][0] >= lastEnd) {
            count++;
            lastEnd = intervals[i][1];
        }
    }
    return count;
}
```

**LeetCode Problems:**
- **Easy**: None directly
- **Medium**: 435 (Non-overlapping Intervals), 452 (Minimum Number of Arrows), 253 (Meeting Rooms II)
- **Hard**: None directly

**Key Insights:**
- Sort by end time for maximum selections
- Sort by start time for minimum resources
- Use heap for tracking multiple resources

---

### Pattern 2: Fractional/Greedy Knapsack
**When to use**: Maximizing value with constraints, can take fractions
**Key Insight**: Always pick highest value-to-weight ratio first
**Time Complexity**: O(n log n)
**Space Complexity**: O(1)

```java
// Template for Greedy Selection by Ratio
double maximizeValue(Item[] items, int capacity) {
    Arrays.sort(items, (a, b) -> 
        Double.compare(b.value/b.weight, a.value/a.weight));
    
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

**LeetCode Problems:**
- **Easy**: 455 (Assign Cookies)
- **Medium**: 1029 (Two City Scheduling), 621 (Task Scheduler)
- **Hard**: None directly

---

### Pattern 3: Huffman Coding / Merge Operations
**When to use**: Minimizing cost when combining elements
**Key Insight**: Always combine two smallest elements
**Time Complexity**: O(n log n)
**Space Complexity**: O(n)

```java
// Template for Minimum Cost Merging
int minimizeCost(int[] elements) {
    PriorityQueue<Integer> pq = new PriorityQueue<>();
    for (int element : elements) pq.offer(element);
    
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

**LeetCode Problems:**
- **Easy**: None directly
- **Medium**: 1167 (Minimum Cost to Connect Sticks)
- **Hard**: None in LeetCode 150

---

### Pattern 4: Two Pointers Greedy
**When to use**: Pairing elements optimally from both ends
**Key Insight**: Move pointer that gives better potential
**Time Complexity**: O(n)
**Space Complexity**: O(1)

```java
// Template for Two Pointer Greedy
int twoPointerGreedy(int[] arr) {
    int left = 0, right = arr.length - 1;
    int result = 0;
    
    while (left < right) {
        // Calculate current result
        int current = calculateResult(arr[left], arr[right], right - left);
        result = Math.max(result, current);
        
        // Greedy choice: move pointer that might give better result
        if (shouldMoveLeft(arr[left], arr[right])) {
            left++;
        } else {
            right--;
        }
    }
    return result;
}
```

**LeetCode Problems:**
- **Easy**: None directly
- **Medium**: 11 (Container With Most Water), 1793 (Maximum Score of Good Subarray)
- **Hard**: 42 (Trapping Rain Water)

---

### Pattern 5: Sorting-Based Greedy
**When to use**: When optimal ordering reveals greedy choice
**Key Insight**: Sort first, then process in optimal order
**Time Complexity**: O(n log n)
**Space Complexity**: O(1) to O(n)

```java
// Template for Sorting-Based Greedy
int sortingBasedGreedy(int[][] items) {
    // Choose sorting criteria based on problem
    Arrays.sort(items, (a, b) -> {
        // Common sorting strategies:
        // 1. By end time: a[1] - b[1]
        // 2. By start time: a[0] - b[0]
        // 3. By ratio: compare ratios
        // 4. By difference: (a[0] - a[1]) - (b[0] - b[1])
        return a[1] - b[1]; // example
    });
    
    int result = 0;
    for (int[] item : items) {
        // Make greedy choice based on sorted order
        if (isValidChoice(item)) {
            result += processItem(item);
        }
    }
    return result;
}
```

**LeetCode Problems:**
- **Easy**: 455 (Assign Cookies)
- **Medium**: 435 (Non-overlapping Intervals), 452 (Minimum Number of Arrows)
- **Hard**: None directly

---

### Pattern 6: **[MISSING] Stack-Based Greedy**
**When to use**: Building optimal sequences or removing elements
**Key Insight**: Maintain monotonic stack for optimal choices
**Time Complexity**: O(n)
**Space Complexity**: O(n)

```java
// Template for Stack-Based Greedy
String stackBasedGreedy(String s, int k) {
    Stack<Character> stack = new Stack<>();
    int toRemove = k;
    
    for (char c : s.toCharArray()) {
        // Remove elements that make current choice suboptimal
        while (!stack.isEmpty() && toRemove > 0 && 
               shouldRemove(stack.peek(), c)) {
            stack.pop();
            toRemove--;
        }
        stack.push(c);
    }
    
    // Remove remaining elements if needed
    while (toRemove > 0) {
        stack.pop();
        toRemove--;
    }
    
    return buildResult(stack);
}
```

**LeetCode Problems:**
- **Easy**: None
- **Medium**: 402 (Remove K Digits), 316 (Remove Duplicate Letters), 1081 (Smallest Subsequence)
- **Hard**: None in LeetCode 150

---

### Pattern 7: **[MISSING] Jump/Reach Greedy**
**When to use**: Determining reachability or minimum steps
**Key Insight**: Track furthest reachable position
**Time Complexity**: O(n)
**Space Complexity**: O(1)

```java
// Template for Jump Problems
boolean canReach(int[] nums) {
    int maxReach = 0;
    for (int i = 0; i < nums.length; i++) {
        if (i > maxReach) return false; // Can't reach this position
        maxReach = Math.max(maxReach, i + nums[i]);
        if (maxReach >= nums.length - 1) return true;
    }
    return maxReach >= nums.length - 1;
}

int minJumps(int[] nums) {
    int jumps = 0, currentEnd = 0, farthest = 0;
    
    for (int i = 0; i < nums.length - 1; i++) {
        farthest = Math.max(farthest, i + nums[i]);
        
        if (i == currentEnd) {
            jumps++;
            currentEnd = farthest;
        }
    }
    return jumps;
}
```

**LeetCode Problems:**
- **Easy**: None
- **Medium**: 55 (Jump Game), 45 (Jump Game II)
- **Hard**: None in LeetCode 150

---

### Pattern 8: **[MISSING] String Reconstruction Greedy**
**When to use**: Building optimal strings with constraints
**Key Insight**: Place characters to avoid violations
**Time Complexity**: O(n log n) to O(n)
**Space Complexity**: O(n)

```java
// Template for String Reconstruction
String reconstructString(String s, int k) {
    // Count frequencies
    int[] count = new int[26];
    for (char c : s.toCharArray()) {
        count[c - 'a']++;
    }
    
    // Use priority queue for optimal ordering
    PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> b[1] - a[1]);
    for (int i = 0; i < 26; i++) {
        if (count[i] > 0) {
            pq.offer(new int[]{i, count[i]});
        }
    }
    
    StringBuilder result = new StringBuilder();
    Queue<int[]> waitQueue = new LinkedList<>();
    
    while (!pq.isEmpty()) {
        int[] current = pq.poll();
        result.append((char)(current[0] + 'a'));
        current[1]--;
        
        waitQueue.offer(current);
        
        if (waitQueue.size() < k) continue;
        
        int[] front = waitQueue.poll();
        if (front[1] > 0) {
            pq.offer(front);
        }
    }
    
    return result.length() == s.length() ? result.toString() : "";
}
```

**LeetCode Problems:**
- **Easy**: None
- **Medium**: 621 (Task Scheduler), 767 (Reorganize String)
- **Hard**: None in LeetCode 150

---

### Pattern 9: **[MISSING] Parentheses/Bracket Greedy**
**When to use**: Balancing or validating nested structures
**Key Insight**: Track balance and make greedy corrections
**Time Complexity**: O(n)
**Space Complexity**: O(1)

```java
// Template for Parentheses Problems
int minParenthesesOperations(String s) {
    int balance = 0; // track current balance
    int operations = 0;
    
    for (char c : s.toCharArray()) {
        if (c == '(') {
            balance++;
        } else if (c == ')') {
            if (balance > 0) {
                balance--;
            } else {
                operations++; // need to add '(' or remove ')'
            }
        }
    }
    
    // Add remaining unmatched '('
    operations += balance;
    return operations;
}
```

**LeetCode Problems:**
- **Easy**: 20 (Valid Parentheses)
- **Medium**: 678 (Valid Parenthesis String), 921 (Minimum Add to Make Parentheses Valid)
- **Hard**: None in LeetCode 150

---

## Implementation Strategies

### 1. **Sorting Strategy Selection**
```java
// By end time - for activity selection
Arrays.sort(intervals, (a, b) -> a[1] - b[1]);

// By start time - for resource allocation
Arrays.sort(intervals, (a, b) -> a[0] - b[0]);

// By ratio - for knapsack-like problems
Arrays.sort(items, (a, b) -> Double.compare(b.value/b.weight, a.value/a.weight));

// By difference - for two-choice problems
Arrays.sort(costs, (a, b) -> (a[0] - a[1]) - (b[0] - b[1]));
```

### 2. **Data Structure Selection**
- **PriorityQueue**: For always accessing min/max element
- **Stack**: For maintaining monotonic sequences
- **Two Pointers**: For pairing from both ends
- **HashMap**: For frequency counting and tracking

### 3. **Proof Strategy**
1. **Exchange Argument**: Show any optimal solution can include greedy choice
2. **Optimal Substructure**: Remaining problem has same structure
3. **Contradiction**: Assume greedy is wrong, derive contradiction

---

## Pattern Recognition Decision Tree

```
Start with Problem
│
├─ Involves intervals/scheduling?
│  ├─ Yes → Use Activity Selection Pattern
│  └─ No → Continue
│
├─ Involves pairing elements optimally?
│  ├─ Yes → Use Two Pointers Greedy
│  └─ No → Continue
│
├─ Involves combining/merging elements?
│  ├─ Yes → Use Huffman/Merge Pattern
│  └─ No → Continue
│
├─ Involves building sequences optimally?
│  ├─ Yes → Check if stack-based or string reconstruction
│  └─ No → Continue
│
├─ Involves movement/reachability?
│  ├─ Yes → Use Jump/Reach Pattern
│  └─ No → Continue
│
├─ Involves balancing structures?
│  ├─ Yes → Use Parentheses Pattern
│  └─ No → Continue
│
└─ Default → Use Sorting-Based Greedy
```

---

## LeetCode 150 Problems Categorized

### **Easy Problems (5 problems)**
1. **455. Assign Cookies** - Sorting-Based Greedy
2. **20. Valid Parentheses** - Parentheses Pattern
3. **121. Best Time to Buy and Sell Stock** - Simple Greedy
4. **122. Best Time to Buy and Sell Stock II** - Transaction Greedy
5. **392. Is Subsequence** - Two Pointers Greedy

### **Medium Problems (15 problems)**
1. **11. Container With Most Water** - Two Pointers Greedy
2. **45. Jump Game II** - Jump/Reach Greedy
3. **55. Jump Game** - Jump/Reach Greedy
4. **134. Gas Station** - Circular Greedy
5. **135. Candy** - Two-Pass Greedy
6. **402. Remove K Digits** - Stack-Based Greedy
7. **435. Non-overlapping Intervals** - Activity Selection
8. **452. Minimum Number of Arrows** - Activity Selection
9. **621. Task Scheduler** - String Reconstruction
10. **678. Valid Parenthesis String** - Parentheses Pattern
11. **763. Partition Labels** - Greedy Partitioning
12. **767. Reorganize String** - String Reconstruction
13. **921. Minimum Add to Make Parentheses Valid** - Parentheses Pattern
14. **1029. Two City Scheduling** - Sorting-Based Greedy
15. **1793. Maximum Score of Good Subarray** - Two Pointers Greedy

### **Hard Problems (3 problems)**
1. **42. Trapping Rain Water** - Two Pointers/Stack Greedy
2. **84. Largest Rectangle in Histogram** - Stack-Based Greedy
3. **316. Remove Duplicate Letters** - Stack-Based Greedy

---

## Additional Interview-Frequent Problems

### **Medium (Additional)**
- **253. Meeting Rooms II** - Activity Selection with Heap
- **406. Queue Reconstruction by Height** - Sorting-Based Greedy
- **502. IPO** - Greedy with Heap
- **630. Course Schedule III** - Activity Selection Variant
- **659. Split Array into Consecutive Subsequences** - Greedy Grouping
- **738. Monotone Increasing Digits** - Digit Greedy
- **846. Hand of Straights** - Greedy Grouping
- **870. Advantage Shuffle** - Sorting-Based Greedy
- **881. Boats to Save People** - Two Pointers Greedy
- **1024. Video Stitching** - Activity Selection

### **Hard (Additional)**
- **218. The Skyline Problem** - Sweep Line Greedy
- **358. Rearrange String k Distance Apart** - String Reconstruction
- **407. Trapping Rain Water II** - Greedy with Heap
- **517. Super Washing Machines** - Multi-source Greedy
- **546. Remove Boxes** - Advanced Greedy (actually DP)

---

## Common Mistakes and Tips

### **Mistake 1: Wrong Sorting Criteria**
```java
// WRONG: Sorting by start time for activity selection
Arrays.sort(intervals, (a, b) -> a[0] - b[0]);

// CORRECT: Sort by end time
Arrays.sort(intervals, (a, b) -> a[1] - b[1]);
```

### **Mistake 2: Not Handling Edge Cases**
```java
// Always check empty arrays
if (arr.length == 0) return 0;

// Handle single element
if (arr.length == 1) return handleSingleElement(arr[0]);
```

### **Mistake 3: Incorrect Greedy Choice**
```java
// For Two City Scheduling - WRONG approach
Arrays.sort(costs, (a, b) -> a[0] - b[0]);

// CORRECT: Sort by difference
Arrays.sort(costs, (a, b) -> (a[0] - a[1]) - (b[0] - b[1]));
```

### **Mistake 4: Forgetting to Prove Greedy Works**
- Always verify with small examples
- Check if local optimum leads to global optimum
- Look for counterexamples

### **Pro Tips:**
1. **Pattern Recognition**: Learn to identify sorting criteria quickly
2. **Edge Cases**: Always test with [], [x], [x,x], and boundary values
3. **Complexity**: Most greedy solutions are O(n log n) due to sorting
4. **Validation**: If greedy fails on small examples, it won't work

---

## Template Selection Quick Reference

| Problem Type | Template | Key Insight |
|--------------|----------|-------------|
| **Intervals** | Activity Selection | Sort by end time |
| **Pairing** | Two Pointers | Move pointer with better potential |
| **Merging** | Huffman/Heap | Combine smallest elements |
| **Sequences** | Stack-Based | Maintain monotonic property |
| **Jumping** | Jump/Reach | Track furthest reachable |
| **Strings** | Reconstruction | Use frequency and constraints |
| **Brackets** | Parentheses | Track balance |
| **General** | Sorting-Based | Find optimal ordering |

---

## Complexity Analysis Patterns

### **Time Complexities:**
- **Sorting-based**: O(n log n)
- **Heap-based**: O(n log n)
- **Two Pointers**: O(n)
- **Stack-based**: O(n)
- **Simple Greedy**: O(n)

### **Space Complexities:**
- **In-place sorting**: O(1)
- **Heap operations**: O(n)
- **Stack operations**: O(n)
- **Two pointers**: O(1)

### **Optimization Tips:**
1. Avoid unnecessary data structures
2. Use primitive arrays when possible
3. Consider in-place modifications
4. Optimize sorting when data has special properties

---

## Interview Strategy

### **Step 1: Pattern Recognition (30 seconds)**
- Identify keywords: "maximum", "minimum", "optimal"
- Look for sorting opportunities
- Check if greedy choice is obvious

### **Step 2: Verify Greedy Works (1 minute)**
- Test on small examples
- Verify greedy choice property
- Check for counterexamples

### **Step 3: Implementation (5-10 minutes)**
- Choose appropriate template
- Handle edge cases
- Optimize if needed

### **Step 4: Testing (2 minutes)**
- Test edge cases
- Verify time/space complexity
- Walk through example

This comprehensive guide covers all greedy patterns needed for LeetCode 150 and interview success. Each pattern includes templates, complexity analysis, and specific problem mappings to ensure you're fully prepared.
