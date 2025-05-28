# Understanding Two Pointer Variations

## The Key Variations in Two Pointer

The Two Pointer pattern is incredibly versatile and can be applied in several ways:

1. **Opposite Direction Pointers**: Start from both ends, move towards center
2. **Same Direction Pointers**: Both pointers move in the same direction (fast/slow)
3. **Fixed Distance Pointers**: Maintain a specific distance between pointers
4. **Collision Pointers**: Pointers meet at some point

## Develop an intuition about when to use each approach.

### 1. Opposite Direction Pointers (Converging)

**When to use**: When you need to check pairs from opposite ends, or when the sorted nature helps eliminate possibilities.

**Template**:
```java
int left = 0, right = array.length - 1;
while (left < right) {
    // Process current pair
    if (someCondition(array[left], array[right])) {
        // Found solution or move both pointers
        left++;
        right--;
    } else if (needToIncreaseSum()) {
        left++;  // Move to larger element
    } else {
        right--; // Move to smaller element
    }
}
```

**Classic Problems**:
- **Two Sum II** (sorted array)
- **3Sum**
- **Container With Most Water**
- **Valid Palindrome**
- **Trapping Rain Water**

### 2. Same Direction Pointers (Fast/Slow)

**When to use**: When you need to process elements at different speeds, or maintain a window/gap.

#### Variant A: Slow/Fast for Different Processing Speeds
```java
int slow = 0;
for (int fast = 0; fast < array.length; fast++) {
    if (shouldKeep(array[fast])) {
        array[slow] = array[fast];
        slow++;
    }
}
// slow now points to the new length
```

#### Variant B: Maintaining Fixed Gap
```java
int slow = 0, fast = k; // k is the gap
while (fast < array.length) {
    // Process with gap of k
    slow++;
    fast++;
}
```

**Classic Problems**:
- **Remove Duplicates from Sorted Array**
- **Remove Element**
- **Move Zeroes**
- **Linked List Cycle Detection** (Floyd's algorithm)
- **Find Kth from End**

### 3. Collision Detection Pattern

**When to use**: Particularly useful in linked lists to detect cycles or find meeting points.

```java
ListNode slow = head, fast = head;
while (fast != null && fast.next != null) {
    slow = slow.next;
    fast = fast.next.next;
    
    if (slow == fast) {
        // Collision detected
        break;
    }
}
```

## Developing Your Intuition

### Decision Framework:

**1. Is the array/list sorted?**
- **Yes**: Consider opposite direction pointers for sum problems
- **No**: Consider same direction for filtering/rearranging

**2. What are you looking for?**
- **Pairs with specific sum**: Opposite direction on sorted array
- **Removing/filtering elements**: Same direction (slow/fast)
- **Cycle detection**: Fast/slow with collision
- **Palindrome checking**: Opposite direction

**3. Do you need to maintain order?**
- **Yes**: Use slow/fast where slow tracks valid position
- **No**: You might be able to use opposite direction swapping

**4. Are you working with a linked list?**
- **Cycle detection**: Fast/slow collision
- **Finding middle**: Fast/slow (fast moves 2x speed)
- **Kth from end**: Fast pointer gets k steps head start

## Pattern Recognition Tips

### Recognize Two Pointer Problems by These Keywords:
- "Sorted array" + "find pair/triplet" → Opposite direction
- "Remove/filter in-place" → Same direction (slow/fast)
- "Palindrome" → Opposite direction
- "Linked list cycle" → Fast/slow collision
- "Container/area optimization" → Opposite direction
- "Sliding window with dynamic size" → Same direction

### Common Pitfalls and How to Avoid Them:

1. **Infinite Loops**:
    - Always ensure at least one pointer moves in each iteration
    - In opposite direction: make sure `left < right` condition

2. **Index Out of Bounds**:
    - Check bounds before accessing array elements
    - In linked lists, check for null before `.next`

3. **Missing Edge Cases**:
    - Empty arrays/lists
    - Single element
    - All elements are the same

## Advanced Variations

### 1. Three Pointers (for 3Sum type problems)
```java
for (int i = 0; i < nums.length - 2; i++) {
    int left = i + 1, right = nums.length - 1;
    while (left < right) {
        // Standard two pointer logic for remaining elements
    }
}
```

### 2. Multiple Passes with Two Pointers
```java
// First pass: one operation
int left = 0, right = array.length - 1;
while (left < right) {
    // Some operation
}

// Second pass: different operation on modified array
left = 0; right = array.length - 1;
while (left < right) {
    // Different operation
}
```

## Time and Space Complexity

- **Time**: Usually O(n) for single pass, O(n²) when nested with outer loop
- **Space**: O(1) - one of the main advantages of two pointer technique

The beauty of two pointers is that it often reduces what would be O(n²) brute force solutions down to O(n) by intelligently eliminating possibilities.
