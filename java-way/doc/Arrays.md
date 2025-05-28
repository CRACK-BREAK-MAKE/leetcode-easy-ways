# Understanding Array Problem Variations

## The Key Variations in Array Problems

Array problems are foundational and cover a wide range of techniques:

1. **Prefix Sum / Cumulative Arrays**: For range sum queries and subarray problems
2. **In-Place Manipulation**: Modifying arrays without extra space
3. **Sorting and Searching**: Various sorting techniques and search optimizations
4. **Subarray Problems**: Finding subarrays with specific properties
5. **Matrix/2D Array**: Grid-based problems and traversals
6. **Array Rotations and Shifts**: Cyclic operations on arrays

## Develop an intuition about when to use each approach.

### 1. Prefix Sum / Cumulative Arrays

**When to use**: When you need multiple range sum queries, or finding subarrays with specific sum properties.

#### Template A: Basic Prefix Sum
```java
public class PrefixSum {
    private int[] prefixSum;
    
    public PrefixSum(int[] nums) {
        prefixSum = new int[nums.length + 1];
        for (int i = 0; i < nums.length; i++) {
            prefixSum[i + 1] = prefixSum[i] + nums[i];
        }
    }
    
    // Range sum query [left, right] inclusive
    public int rangeSum(int left, int right) {
        return prefixSum[right + 1] - prefixSum[left];
    }
}
```

#### Template B: 2D Prefix Sum
```java
public class MatrixPrefixSum {
    private int[][] prefixSum;
    
    public MatrixPrefixSum(int[][] matrix) {
        int m = matrix.length, n = matrix[0].length;
        prefixSum = new int[m + 1][n + 1];
        
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                prefixSum[i][j] = matrix[i-1][j-1] 
                    + prefixSum[i-1][j] 
                    + prefixSum[i][j-1] 
                    - prefixSum[i-1][j-1];
            }
        }
    }
    
    // Sum of rectangle from (row1,col1) to (row2,col2) inclusive
    public int sumRegion(int row1, int col1, int row2, int col2) {
        return prefixSum[row2+1][col2+1] 
             - prefixSum[row1][col2+1] 
             - prefixSum[row2+1][col1] 
             + prefixSum[row1][col1];
    }
}
```

**Classic Problems**:
- **Range Sum Query**
- **Subarray Sum Equals K**
- **Maximum Size Subarray Sum Equals k**
- **2D Range Sum Query**

### 2. In-Place Manipulation

**When to use**: When space complexity must be O(1) and you can modify the input array.

#### Template A: Two Pointers for Rearrangement
```java
public int removeDuplicates(int[] nums) {
    if (nums.length == 0) return 0;
    
    int slow = 0;
    for (int fast = 1; fast < nums.length; fast++) {
        if (nums[fast] != nums[slow]) {
            slow++;
            nums[slow] = nums[fast];
        }
    }
    
    return slow + 1;
}
```

#### Template B: Cyclic Sort (for problems with numbers 1 to n)
```java
public int[] cyclicSort(int[] nums) {
    int i = 0;
    while (i < nums.length) {
        int correctPos = nums[i] - 1;
        if (nums[i] != nums[correctPos]) {
            // Swap to correct position
            int temp = nums[i];
            nums[i] = nums[correctPos];
            nums[correctPos] = temp;
        } else {
            i++;
        }
    }
    return nums;
}
```

#### Template C: Using Array Indices as Hash (negative marking)
```java
public List<Integer> findDisappearedNumbers(int[] nums) {
    List<Integer> result = new ArrayList<>();
    
    // Mark presence by negating values
    for (int num : nums) {
        int index = Math.abs(num) - 1;
        if (nums[index] > 0) {
            nums[index] = -nums[index];
        }
    }
    
    // Find unmarked indices
    for (int i = 0; i < nums.length; i++) {
        if (nums[i] > 0) {
            result.add(i + 1);
        }
    }
    
    return result;
}
```

**Classic Problems**:
- **Remove Duplicates**
- **Find All Numbers Disappeared in Array**
- **First Missing Positive**
- **Move Zeroes**

### 3. Sorting and Searching Variations

**When to use**: When the problem involves ordering or finding specific elements.

#### Template A: Custom Sorting
```java
public int[][] mergeIntervals(int[][] intervals) {
    Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));
    
    List<int[]> merged = new ArrayList<>();
    int[] current = intervals[0];
    
    for (int i = 1; i < intervals.length; i++) {
        if (intervals[i][0] <= current[1]) {
            // Overlapping intervals
            current[1] = Math.max(current[1], intervals[i][1]);
        } else {
            // Non-overlapping
            merged.add(current);
            current = intervals[i];
        }
    }
    
    merged.add(current);
    return merged.toArray(new int[merged.size()][]);
}
```

#### Template B: Quick Select (for Kth element problems)
```java
public int findKthLargest(int[] nums, int k) {
    return quickSelect(nums, 0, nums.length - 1, nums.length - k);
}

private int quickSelect(int[] nums, int left, int right, int k) {
    if (left == right) return nums[left];
    
    int pivotIndex = partition(nums, left, right);
    
    if (pivotIndex == k) {
        return nums[k];
    } else if (pivotIndex < k) {
        return quickSelect(nums, pivotIndex + 1, right, k);
    } else {
        return quickSelect(nums, left, pivotIndex - 1, k);
    }
}

private int partition(int[] nums, int left, int right) {
    int pivot = nums[right];
    int i = left;
    
    for (int j = left; j < right; j++) {
        if (nums[j] <= pivot) {
            swap(nums, i, j);
            i++;
        }
    }
    
    swap(nums, i, right);
    return i;
}
```

### 4. Subarray Problems

**When to use**: Finding contiguous subarrays with specific properties.

#### Template A: Maximum Subarray (Kadane's Algorithm)
```java
public int maxSubArray(int[] nums) {
    int maxSoFar = nums[0];
    int maxEndingHere = nums[0];
    
    for (int i = 1; i < nums.length; i++) {
        maxEndingHere = Math.max(nums[i], maxEndingHere + nums[i]);
        maxSoFar = Math.max(maxSoFar, maxEndingHere);
    }
    
    return maxSoFar;
}
```

#### Template B: Subarray with Given Sum
```java
public int subarraySum(int[] nums, int k) {
    Map<Integer, Integer> prefixSumCount = new HashMap<>();
    prefixSumCount.put(0, 1); // Empty subarray has sum 0
    
    int count = 0, prefixSum = 0;
    
    for (int num : nums) {
        prefixSum += num;
        
        // If (prefixSum - k) exists, we found subarrays ending at current position
        if (prefixSumCount.containsKey(prefixSum - k)) {
            count += prefixSumCount.get(prefixSum - k);
        }
        
        prefixSumCount.put(prefixSum, prefixSumCount.getOrDefault(prefixSum, 0) + 1);
    }
    
    return count;
}
```

### 5. Matrix/2D Array Problems

**When to use**: Grid-based problems, image processing, or 2D traversals.

#### Template A: Matrix Traversal Patterns
```java
public class MatrixTraversal {
    // Spiral traversal
    public List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> result = new ArrayList<>();
        if (matrix.length == 0) return result;
        
        int top = 0, bottom = matrix.length - 1;
        int left = 0, right = matrix[0].length - 1;
        
        while (top <= bottom && left <= right) {
            // Traverse right
            for (int col = left; col <= right; col++) {
                result.add(matrix[top][col]);
            }
            top++;
            
            // Traverse down
            for (int row = top; row <= bottom; row++) {
                result.add(matrix[row][right]);
            }
            right--;
            
            // Traverse left (if still have rows)
            if (top <= bottom) {
                for (int col = right; col >= left; col--) {
                    result.add(matrix[bottom][col]);
                }
                bottom--;
            }
            
            // Traverse up (if still have columns)
            if (left <= right) {
                for (int row = bottom; row >= top; row--) {
                    result.add(matrix[row][left]);
                }
                left++;
            }
        }
        
        return result;
    }
    
    // Diagonal traversal
    public int[] findDiagonalOrder(int[][] matrix) {
        if (matrix.length == 0) return new int[0];
        
        int m = matrix.length, n = matrix[0].length;
        int[] result = new int[m * n];
        int index = 0;
        
        for (int d = 0; d < m + n - 1; d++) {
            if (d % 2 == 0) {
                // Going up
                int row = Math.min(d, m - 1);
                int col = d - row;
                while (row >= 0 && col < n) {
                    result[index++] = matrix[row][col];
                    row--;
                    col++;
                }
            } else {
                // Going down
                int col = Math.min(d, n - 1);
                int row = d - col;
                while (col >= 0 && row < m) {
                    result[index++] = matrix[row][col];
                    row++;
                    col--;
                }
            }
        }
        
        return result;
    }
}
```

### 6. Array Rotations and Shifts

#### Template A: Array Rotation
```java
public void rotate(int[] nums, int k) {
    k %= nums.length;
    
    // Reverse entire array
    reverse(nums, 0, nums.length - 1);
    // Reverse first k elements
    reverse(nums, 0, k - 1);
    // Reverse remaining elements
    reverse(nums, k, nums.length - 1);
}

private void reverse(int[] nums, int start, int end) {
    while (start < end) {
        int temp = nums[start];
        nums[start] = nums[end];
        nums[end] = temp;
        start++;
        end--;
    }
}
```

## Developing Your Intuition

### Decision Framework:

**1. What type of queries are you answering?**
- **Range sum queries**: Prefix sum
- **Finding specific elements**: Binary search or hash-based approaches
- **Rearranging elements**: Two pointers or in-place techniques

**2. What are the constraints?**
- **Space O(1)**: In-place manipulation
- **Multiple queries**: Preprocessing with prefix sums
- **Numbers 1 to n**: Cyclic sort or index-based techniques

**3. What are you optimizing?**
- **Subarray with max sum**: Kadane's algorithm
- **Kth largest element**: Quick select
- **Duplicate removal**: Two pointers

## Pattern Recognition Tips

### Recognize Array Problems by These Keywords:
- "Range sum/query" → Prefix sum
- "In-place/O(1) space" → Two pointers/cyclic sort
- "Maximum subarray" → Kadane's algorithm
- "Kth largest/smallest" → Quick select or heap
- "Matrix traversal" → Spiral/diagonal patterns
- "Rotate array" → Reversal technique
- "Numbers 1 to n" → Cyclic sort or index marking

### Common Optimizations:

1. **Use prefix sums** for multiple range queries
2. **Use index as hash** when values are in range [1, n]
3. **Use negative marking** instead of extra space
4. **Use reversal technique** for rotations
5. **Use quick select** instead of full sorting for Kth element

## Time and Space Complexity

- **Prefix sum**: O(n) preprocessing, O(1) queries, O(n) space
- **In-place manipulation**: O(n) time, O(1) space
- **Quick select**: O(n) average, O(n²) worst case, O(1) space
- **Matrix traversal**: O(mn) time, O(1) to O(mn) space
- **Kadane's algorithm**: O(n) time, O(1) space

Arrays are fundamental, and mastering these patterns provides the foundation for more complex algorithms.
