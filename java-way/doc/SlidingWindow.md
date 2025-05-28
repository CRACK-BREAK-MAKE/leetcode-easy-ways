# Understanding Sliding Window Variations

## The Key Variations in Sliding Window

The Sliding Window pattern is perfect for contiguous subarray/substring problems and comes in several forms:

1. **Fixed Size Window**: Window size is predetermined and constant
2. **Variable Size Window**: Window expands and contracts based on conditions
3. **Two Arrays/Strings Window**: Matching patterns between two sequences
4. **Multiple Windows**: Tracking multiple windows simultaneously

## Develop an intuition about when to use each approach.

### 1. Fixed Size Window

**When to use**: When the problem specifies a fixed window size k, or asks for "exactly k" elements.

**Template**:
```java
public int fixedWindow(int[] arr, int k) {
    // Initialize window sum/state for first k elements
    int windowSum = 0;
    for (int i = 0; i < k; i++) {
        windowSum += arr[i];
    }
    
    int result = windowSum; // or whatever you're tracking
    
    // Slide the window
    for (int i = k; i < arr.length; i++) {
        windowSum += arr[i];        // Add new element
        windowSum -= arr[i - k];    // Remove element going out
        result = Math.max(result, windowSum); // Update result
    }
    
    return result;
}
```

**Classic Problems**:
- **Maximum Sum Subarray of Size K**
- **Average of Subarrays of Size K**
- **Find All Anagrams in a String** (fixed pattern length)

### 2. Variable Size Window (Expandable/Shrinkable)

**When to use**: When looking for "maximum/minimum length" subarray/substring that satisfies a condition.

#### Variant A: Expand until invalid, then shrink
```java
public int variableWindow(int[] arr, int target) {
    int left = 0, right = 0;
    int windowSum = 0;
    int result = 0; // or Integer.MAX_VALUE for minimum
    
    while (right < arr.length) {
        // Expand window
        windowSum += arr[right];
        
        // Shrink window until valid
        while (windowSum >= target) { // condition depends on problem
            result = Math.max(result, right - left + 1);
            windowSum -= arr[left];
            left++;
        }
        
        right++;
    }
    
    return result;
}
```

#### Variant B: At Most K pattern (for "at most k distinct" problems)
```java
public int atMostK(String s, int k) {
    Map<Character, Integer> map = new HashMap<>();
    int left = 0, result = 0;
    
    for (int right = 0; right < s.length(); right++) {
        map.put(s.charAt(right), map.getOrDefault(s.charAt(right), 0) + 1);
        
        while (map.size() > k) {
            char leftChar = s.charAt(left);
            map.put(leftChar, map.get(leftChar) - 1);
            if (map.get(leftChar) == 0) {
                map.remove(leftChar);
            }
            left++;
        }
        
        result += right - left + 1;
    }
    
    return result;
}
```

**Classic Problems**:
- **Longest Substring Without Repeating Characters**
- **Minimum Window Substring**
- **Longest Substring with At Most K Distinct Characters**
- **Subarrays with K Different Integers**

### 3. Template Matching Window

**When to use**: When you need to find all occurrences of a pattern/anagram in a text.

```java
public List<Integer> templateMatching(String s, String pattern) {
    List<Integer> result = new ArrayList<>();
    if (pattern.length() > s.length()) return result;
    
    // Build pattern frequency map
    Map<Character, Integer> patternMap = new HashMap<>();
    for (char c : pattern.toCharArray()) {
        patternMap.put(c, patternMap.getOrDefault(c, 0) + 1);
    }
    
    Map<Character, Integer> windowMap = new HashMap<>();
    int windowSize = pattern.length();
    
    // Process first window
    for (int i = 0; i < windowSize; i++) {
        char c = s.charAt(i);
        windowMap.put(c, windowMap.getOrDefault(c, 0) + 1);
    }
    
    if (windowMap.equals(patternMap)) {
        result.add(0);
    }
    
    // Slide the window
    for (int i = windowSize; i < s.length(); i++) {
        // Add new character
        char newChar = s.charAt(i);
        windowMap.put(newChar, windowMap.getOrDefault(newChar, 0) + 1);
        
        // Remove old character
        char oldChar = s.charAt(i - windowSize);
        windowMap.put(oldChar, windowMap.get(oldChar) - 1);
        if (windowMap.get(oldChar) == 0) {
            windowMap.remove(oldChar);
        }
        
        if (windowMap.equals(patternMap)) {
            result.add(i - windowSize + 1);
        }
    }
    
    return result;
}
```

## Developing Your Intuition

### Decision Framework:

**1. Is the window size fixed?**
- **Yes**: Use fixed window template
- **No**: Use variable window with two pointers

**2. What are you optimizing?**
- **Maximum length**: Expand aggressively, shrink when invalid
- **Minimum length**: Shrink aggressively, expand when invalid
- **Exact match**: Use template matching approach

**3. What's the constraint?**
- **Sum-based**: Track running sum
- **Character frequency**: Use HashMap/array for counting
- **Distinct elements**: Track unique count
- **Pattern matching**: Compare frequency maps

**4. What does "valid" mean?**
- **Sum ≥ target**: Shrink from left when sum is too large
- **≤ k distinct**: Shrink from left when too many distinct elements
- **Contains all chars**: Shrink from left while still containing all

## Pattern Recognition Tips

### Recognize Sliding Window Problems by These Keywords:
- "Contiguous subarray/substring" → Sliding window
- "Maximum/minimum length" → Variable window
- "Fixed size k" → Fixed window
- "All anagrams/permutations" → Template matching
- "At most k distinct" → At most k pattern
- "Window of size..." → Fixed window

### Common Problem Types and Their Approaches:

1. **Sum Problems**:
    - Fixed sum → Variable window
    - Maximum sum of size k → Fixed window
    - Minimum length sum ≥ target → Variable window (shrink when valid)

2. **String Problems**:
    - Longest without repeating → Variable window with set/map
    - Find all anagrams → Template matching
    - At most k distinct → At most k pattern

3. **Frequency Problems**:
    - Always use HashMap or frequency array
    - Template matching for exact frequency match
    - Variable window for "at most" constraints

## Advanced Techniques

### 1. Multiple Windows
```java
// Track multiple windows simultaneously
int[] window1 = new int[26];
int[] window2 = new int[26];
// Process both windows in parallel
```

### 2. Preprocessing for Optimization
```java
// For problems involving multiple queries
int[] prefixSum = new int[arr.length + 1];
for (int i = 0; i < arr.length; i++) {
    prefixSum[i + 1] = prefixSum[i] + arr[i];
}
// Now range sum queries are O(1)
```

### 3. Sliding Window Maximum/Minimum
```java
// Use deque to maintain window maximum/minimum in O(1)
Deque<Integer> deque = new ArrayDeque<>();
// Maintain decreasing order for maximum
// Maintain increasing order for minimum
```

## Time and Space Complexity

- **Fixed Window**: O(n) time, O(1) or O(k) space
- **Variable Window**: O(n) time, O(1) to O(n) space depending on what you track
- **Template Matching**: O(n) time, O(k) space where k is pattern size

## Common Pitfalls and Solutions

1. **Off-by-one errors**: Always double-check window boundaries
2. **Empty windows**: Handle edge cases when left > right
3. **Integer overflow**: Use long for sum calculations if needed
4. **HashMap cleanup**: Remove entries when count reaches 0
5. **Result initialization**: Use appropriate initial values (0, MAX_VALUE, MIN_VALUE)

The key insight is that sliding window transforms nested loop problems (O(n²)) into single pass solutions (O(n)) by reusing computation from previous windows.
