# Understanding Binary Search Variations

## The Key Variations in Binary Search

There are several key decision points when implementing binary search:

1. **Loop condition**: `while (left < right)` vs `while (left <= right)`
2. **Mid calculation**: `mid = left + (right - left) / 2` vs `mid = left + (right - left + 1) / 2`
3. **Search space reduction**:
    - `left = mid + 1` and `right = mid - 1`
    - `left = mid + 1` and `right = mid`
    - `left = mid` and `right = mid - 1`

## Develop an intuition about when to use each approach.

### Standard Binary Search (Finding an Exact Match)

When you're looking for an exact match (like in a sorted array):

```java
while (left <= right) {           // Use <= to check the last element
    int mid = left + (right - left) / 2;
    
    if (array[mid] == target) {   // Exact match found
        return mid;
    } else if (array[mid] < target) {
        left = mid + 1;           // Eliminate left half
    } else {
        right = mid - 1;          // Eliminate right half
    }
}
return -1;  // Not found
```

### Finding the Leftmost/Rightmost Element

#### When looking for the leftmost element that meets a condition:

```java
while (left < right) {            // Use < because we're narrowing to a single element
    int mid = left + (right - left) / 2;  // Regular mid (rounds down)
    
    if (condition(mid)) {
        right = mid;              // This element might be the answer
    } else {
        left = mid + 1;           // Elements before mid+1 are definitely not the answer
    }
}
return left;  // left == right at this point
```

#### When looking for the rightmost element that meets a condition:

```java
while (left < right) {
    int mid = left + (right - left + 1) / 2;  // "Ceiling" mid (rounds up)
    
    if (condition(mid)) {
        left = mid;               // This element might be the answer
    } else {
        right = mid - 1;          // Elements after mid-1 are definitely not the answer
    }
}
return left;  // left == right at this point
```

## Developing Your Intuition

Here's how to decide which version to use:

1. **Loop condition**:
    - Use `left <= right` when searching for an exact value
    - Use `left < right` when narrowing down to a single element that satisfies a condition

2. **Search space reduction**:
    - Use `left = mid + 1, right = mid - 1` when you're eliminating both sides
    - Use `left = mid + 1, right = mid` when the answer might be at mid or to its left
    - Use `left = mid, right = mid - 1` when the answer might be at mid or to its right

3. **Mid calculation**:
    - Use the standard `mid = left + (right - left) / 2` for most cases
    - Use `mid = left + (right - left + 1) / 2` when doing `left = mid` to avoid infinite loops

## Examples Where Each Variation Applies

1. **Standard Binary Search**: Finding if a value exists in a sorted array.

2. **Finding the leftmost position**:
    - First occurrence of a value in a sorted array with duplicates
    - Lower bound in a range
    - In your Time-Based Key-Value Store, finding the largest timestamp ≤ target

3. **Finding the rightmost position**:
    - Last occurrence of a value in a sorted array with duplicates
    - Upper bound in a range

The key is to trace through your algorithm with a small example and check if your loop invariants hold and if your algorithm terminates properly.
