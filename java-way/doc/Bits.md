# Understanding Bit Manipulation Patterns

## What is Bit Manipulation?

Bit manipulation involves performing operations directly on bits (0s and 1s) of numbers. It's often the most efficient way to solve certain problems and can turn complex operations into simple bitwise operations.

**Core Principle**: Think in terms of individual bits and use bitwise operations to achieve the desired result efficiently.

## Essential Bitwise Operations

### Basic Operations
```java
// AND (&): Both bits must be 1
5 & 3   // 101 & 011 = 001 = 1

// OR (|): At least one bit must be 1  
5 | 3   // 101 | 011 = 111 = 7

// XOR (^): Bits must be different
5 ^ 3   // 101 ^ 011 = 110 = 6

// NOT (~): Flip all bits
~5      // ~101 = ...11111010 (depends on integer size)

// Left Shift (<<): Multiply by 2^n
5 << 2  // 101 << 2 = 10100 = 20

// Right Shift (>>): Divide by 2^n
20 >> 2 // 10100 >> 2 = 101 = 5
```

### Key Properties and Identities
```java
// XOR Properties (Most Important!)
x ^ 0 = x      // XOR with 0 gives the number itself
x ^ x = 0      // XOR with itself gives 0
x ^ y = y ^ x  // XOR is commutative
(x ^ y) ^ z = x ^ (y ^ z)  // XOR is associative

// AND Properties
x & 0 = 0      // AND with 0 gives 0
x & x = x      // AND with itself gives the number
x & (x-1) = 0  // Only true if x is power of 2

// OR Properties
x | 0 = x      // OR with 0 gives the number
x | x = x      // OR with itself gives the number
```

## Fundamental Bit Manipulation Tricks

### 1. **Check if Bit is Set**
```java
// Check if i-th bit is set (0-indexed from right)
boolean isBitSet(int num, int i) {
    return (num & (1 << i)) != 0;
}
```

### 2. **Set a Bit**
```java
// Set i-th bit to 1
int setBit(int num, int i) {
    return num | (1 << i);
}
```

### 3. **Clear a Bit**
```java
// Set i-th bit to 0
int clearBit(int num, int i) {
    return num & ~(1 << i);
}
```

### 4. **Toggle a Bit**
```java
// Flip i-th bit
int toggleBit(int num, int i) {
    return num ^ (1 << i);
}
```

### 5. **Check if Power of 2**
```java
// A number is power of 2 if it has exactly one bit set
boolean isPowerOfTwo(int n) {
    return n > 0 && (n & (n - 1)) == 0;
}
```

### 6. **Count Set Bits (Brian Kernighan's Algorithm)**
```java
// Count number of 1s in binary representation
int countSetBits(int n) {
    int count = 0;
    while (n != 0) {
        n = n & (n - 1);  // Remove rightmost set bit
        count++;
    }
    return count;
}
```

### 7. **Get Rightmost Set Bit**
```java
// Isolate the rightmost set bit
int getRightmostSetBit(int n) {
    return n & (-n);
}
```

## Common Bit Manipulation Patterns

### Pattern 1: XOR for Finding Unique Elements
**When to use**: Finding elements that appear odd number of times

**Key Insight**: XOR of identical numbers is 0, XOR with 0 gives the number itself

```java
// Single Number - Find the number that appears once
int singleNumber(int[] nums) {
    int result = 0;
    for (int num : nums) {
        result ^= num;  // All duplicates cancel out
    }
    return result;
}

// Two numbers appear once, rest appear twice
int[] singleNumber(int[] nums) {
    // Step 1: XOR all numbers to get x^y (the two unique numbers)
    int xor = 0;
    for (int num : nums) xor ^= num;
    
    // Step 2: Find any set bit in xor (difference between x and y)
    int rightmostSetBit = xor & (-xor);
    
    // Step 3: Divide numbers into two groups based on this bit
    int x = 0, y = 0;
    for (int num : nums) {
        if ((num & rightmostSetBit) != 0) {
            x ^= num;  // Numbers with this bit set
        } else {
            y ^= num;  // Numbers without this bit set
        }
    }
    return new int[]{x, y};
}
```

### Pattern 2: Bit Masking for Subsets
**When to use**: Generating all subsets, checking subset relationships

**Key Insight**: Each bit represents whether an element is included in the subset

```java
// Generate all subsets using bit masking
List<List<Integer>> subsets(int[] nums) {
    List<List<Integer>> result = new ArrayList<>();
    int n = nums.length;
    
    // Iterate through all possible bitmasks (0 to 2^n - 1)
    for (int mask = 0; mask < (1 << n); mask++) {
        List<Integer> subset = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            // Check if i-th bit is set in mask
            if ((mask & (1 << i)) != 0) {
                subset.add(nums[i]);
            }
        }
        result.add(subset);
    }
    return result;
}
```

### Pattern 3: Bit Manipulation for Optimization
**When to use**: Optimizing space or time complexity

```java
// Missing Number - Find missing number in array [0, n]
int missingNumber(int[] nums) {
    int n = nums.length;
    int expected = n * (n + 1) / 2;  // Mathematical approach
    int actual = Arrays.stream(nums).sum();
    return expected - actual;
    
    // Alternative: XOR approach
    int result = nums.length;
    for (int i = 0; i < nums.length; i++) {
        result ^= i ^ nums[i];
    }
    return result;
}

// Reverse Bits
int reverseBits(int n) {
    int result = 0;
    for (int i = 0; i < 32; i++) {
        result = (result << 1) | (n & 1);
        n >>= 1;
    }
    return result;
}
```

### Pattern 4: Bit Manipulation in Arrays
**When to use**: Space-efficient marking, in-place modifications

```java
// Find All Numbers Disappeared in Array [1,n]
// Use array indices as bit positions
List<Integer> findDisappearedNumbers(int[] nums) {
    // Mark presence by making numbers at indices negative
    for (int num : nums) {
        int index = Math.abs(num) - 1;
        if (nums[index] > 0) {
            nums[index] = -nums[index];
        }
    }
    
    List<Integer> result = new ArrayList<>();
    for (int i = 0; i < nums.length; i++) {
        if (nums[i] > 0) {
            result.add(i + 1);  // This number is missing
        }
    }
    return result;
}
```

### Pattern 5: Gray Code and Bit Sequences
**When to use**: Generating sequences where adjacent numbers differ by one bit

```java
// Gray Code - Generate n-bit Gray code sequence
List<Integer> grayCode(int n) {
    List<Integer> result = new ArrayList<>();
    // Gray code formula: i ^ (i >> 1)
    for (int i = 0; i < (1 << n); i++) {
        result.add(i ^ (i >> 1));
    }
    return result;
}
```

## Advanced Bit Manipulation Techniques

### 1. **Bit Manipulation with Digits**
```java
// Add Binary Strings
String addBinary(String a, String b) {
    StringBuilder result = new StringBuilder();
    int carry = 0;
    int i = a.length() - 1, j = b.length() - 1;
    
    while (i >= 0 || j >= 0 || carry > 0) {
        int sum = carry;
        if (i >= 0) sum += a.charAt(i--) - '0';
        if (j >= 0) sum += b.charAt(j--) - '0';
        
        result.append(sum % 2);
        carry = sum / 2;
    }
    return result.reverse().toString();
}
```

### 2. **Range Bit Manipulation**
```java
// Bitwise AND of Numbers Range
int rangeBitwiseAnd(int left, int right) {
    int shift = 0;
    // Find common prefix of left and right
    while (left != right) {
        left >>= 1;
        right >>= 1;
        shift++;
    }
    return left << shift;
}
```

### 3. **Bit Manipulation for State Compression**
```java
// Use bits to represent state in DP problems
// Example: Traveling Salesman Problem with bitmask DP
int tsp(int mask, int pos, int[][] dist, int[][] dp) {
    if (mask == (1 << n) - 1) return dist[pos][0]; // All cities visited
    if (dp[mask][pos] != -1) return dp[mask][pos];
    
    int result = Integer.MAX_VALUE;
    for (int city = 0; city < n; city++) {
        if ((mask & (1 << city)) == 0) { // City not visited
            int newMask = mask | (1 << city);
            result = Math.min(result, dist[pos][city] + tsp(newMask, city, dist, dp));
        }
    }
    return dp[mask][pos] = result;
}
```

## Developing Your Bit Manipulation Intuition

### When to Think About Bits?

1. **Keywords that suggest bit manipulation**:
    - "Find unique/single element"
    - "XOR operations"
    - "Powers of 2"
    - "Binary representation"
    - "Flip/toggle bits"
    - "Count set bits"

2. **Problem characteristics**:
    - Numbers appear in pairs except one/two
    - Need to check if number is power of 2
    - Working with binary strings
    - Need space-efficient boolean array
    - Generating all subsets
    - Problems involving AND, OR, XOR operations

### Common Problem Categories

#### 1. **Single Number Problems**
- **Pattern**: Use XOR properties to cancel out duplicates
- **Examples**: Single Number I, II, III
- **Key insight**: a ^ a = 0, a ^ 0 = a

#### 2. **Bit Counting Problems**
- **Pattern**: Count set bits efficiently
- **Examples**: Number of 1 Bits, Counting Bits
- **Key insight**: n & (n-1) removes rightmost set bit

#### 3. **Power of 2 Problems**
- **Pattern**: Use n & (n-1) == 0 check
- **Examples**: Power of Two, Power of Four
- **Key insight**: Powers of 2 have exactly one bit set

#### 4. **Subset Generation**
- **Pattern**: Use bitmask to represent subsets
- **Examples**: Subsets, Combination Sum with bitmask
- **Key insight**: Each bit represents inclusion/exclusion

#### 5. **Missing Number Problems**
- **Pattern**: Use XOR or mathematical properties
- **Examples**: Missing Number, Find Missing Positive
- **Key insight**: XOR or sum properties reveal missing elements

#### 6. **Binary String Problems**
- **Pattern**: Simulate binary arithmetic
- **Examples**: Add Binary, Binary Addition
- **Key insight**: Handle carry and digit operations

### Debugging Bit Manipulation

#### Common Mistakes:
1. **Operator precedence**: `&` has lower precedence than `==`
   ```java
   // Wrong: if (num & 1 == 0)
   // Correct: if ((num & 1) == 0)
   ```

2. **Integer overflow**: Left shifting can cause overflow
   ```java
   // Be careful with: 1 << 31 (might overflow)
   // Use: 1L << 31 for long
   ```

3. **Signed vs unsigned**: Right shift behavior with negative numbers
   ```java
   // >> is arithmetic right shift (preserves sign)
   // >>> is logical right shift (fills with 0)
   ```

#### Debugging Tips:
1. **Print binary representation**: `Integer.toBinaryString(n)`
2. **Test with small numbers**: Trace through bit operations manually
3. **Check edge cases**: 0, negative numbers, powers of 2
4. **Verify bit positions**: Remember 0-indexed from right

## Time and Space Complexity

Bit manipulation is typically very efficient:
- **Time complexity**: Most operations are O(1) or O(log n)
- **Space complexity**: Often O(1) - constant extra space
- **Performance**: Bitwise operations are among the fastest CPU operations

### Complexity Examples:
- **Count set bits**: O(number of set bits) using Brian Kernighan
- **Generate subsets**: O(n * 2^n) - exponential but optimal for the problem
- **XOR operations**: O(n) for array traversal
- **Bit checking/setting**: O(1) for individual operations

## Advanced Applications

### 1. **Bloom Filters**: Space-efficient probabilistic data structure
### 2. **BitSets**: Efficient storage for boolean arrays
### 3. **Cryptography**: XOR encryption, hash functions
### 4. **Graphics**: Color manipulation, alpha blending
### 5. **Compression**: Huffman coding, bit packing
### 6. **Database indexing**: Bitmap indices for fast queries
