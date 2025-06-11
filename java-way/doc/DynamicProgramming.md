# Comprehensive Dynamic Programming Patterns Guide

## The Core Intuition

Dynamic Programming is about **optimal substructure** and **overlapping subproblems**. The key insight is recognizing when a problem can be broken down into smaller versions of itself, and when solving those smaller problems multiple times.

## DP Problem Recognition Framework

### The Three-Question Test:
1. **Can I make a choice?** (Decision at each step)
2. **Does this choice affect future choices?** (Current decision impacts future state)
3. **Are there optimal subproblems?** (Optimal solution contains optimal solutions to subproblems)

If all three are "Yes" → Likely a DP problem

## Core DP Patterns

### Pattern 1: Linear DP (1D State)
**When to use**: State depends only on previous elements in sequence
**State definition**: `dp[i]` = optimal solution ending at/including index i

```java
// Template for Linear DP
public int linearDP(int[] arr) {
    int n = arr.length;
    int[] dp = new int[n];
    
    // Base case
    dp[0] = baseCase(arr[0]);
    
    // Fill dp array
    for (int i = 1; i < n; i++) {
        dp[i] = Math.max(
            dp[i-1] + arr[i],     // Include current element
            someOtherOption       // Don't include or other choice
        );
    }
    
    return dp[n-1]; // or some function of dp array
}
```

**LeetCode Problems**: 198 (House Robber), 53 (Maximum Subarray), 70 (Climbing Stairs), 746 (Min Cost Climbing Stairs), 91 (Decode Ways)

**Key Insights**:
- Often involves include/exclude decisions
- Watch for circular constraints (House Robber II)
- Can usually be space-optimized to O(1)
- Base cases are critical - handle edge cases carefully

### Pattern 2: Grid DP (2D State)
**When to use**: Moving through a 2D grid with path-dependent decisions
**State definition**: `dp[i][j]` = optimal solution to reach position (i,j)

```java
// Template for Grid DP
public int gridDP(int[][] grid) {
    int m = grid.length, n = grid[0].length;
    int[][] dp = new int[m][n];
    
    // Initialize first row and column
    dp[0][0] = grid[0][0];
    for (int i = 1; i < m; i++) dp[i][0] = dp[i-1][0] + grid[i][0];
    for (int j = 1; j < n; j++) dp[0][j] = dp[0][j-1] + grid[0][j];
    
    // Fill the dp table
    for (int i = 1; i < m; i++) {
        for (int j = 1; j < n; j++) {
            dp[i][j] = grid[i][j] + Math.min(dp[i-1][j], dp[i][j-1]);
        }
    }
    
    return dp[m-1][n-1];
}
```

**LeetCode Problems**: 62 (Unique Paths), 63 (Unique Paths II), 64 (Minimum Path Sum), 120 (Triangle), 174 (Dungeon Game)

**Key Insights**:
- Initialize boundaries carefully
- Consider all possible directions (up/down/left/right)
- Space can be optimized to O(min(m,n))
- Triangle problems are variations of grid DP

### Pattern 3: Knapsack DP (Choice-based)
**When to use**: Making optimal choices with constraints (weight, capacity, etc.)
**State definition**: `dp[i][w]` = optimal value using first i items with weight limit w

```java
// 0/1 Knapsack Template
public int knapsack(int[] weights, int[] values, int capacity) {
    int n = weights.length;
    int[][] dp = new int[n + 1][capacity + 1];
    
    for (int i = 1; i <= n; i++) {
        for (int w = 1; w <= capacity; w++) {
            // Don't take item i-1
            dp[i][w] = dp[i-1][w];
            
            // Take item i-1 if it fits
            if (weights[i-1] <= w) {
                dp[i][w] = Math.max(dp[i][w], 
                    dp[i-1][w - weights[i-1]] + values[i-1]);
            }
        }
    }
    
    return dp[n][capacity];
}
```

**LeetCode Problems**: 322 (Coin Change), 518 (Coin Change II), 377 (Combination Sum IV), 416 (Partition Equal Subset Sum), 494 (Target Sum)

**Key Insights**:
- 0/1 vs Unbounded: order of loops matters
- For unbounded: inner loop uses `dp[i][w]` instead of `dp[i-1][w]`
- Subset sum problems are boolean knapsack variants
- Can be space-optimized to 1D array

### Pattern 4: Interval DP (Range-based)
**When to use**: Optimal way to combine/split intervals or ranges
**State definition**: `dp[i][j]` = optimal solution for range [i, j]

```java
// Template for Interval DP
public int intervalDP(int[] arr) {
    int n = arr.length;
    int[][] dp = new int[n][n];
    
    // Base case: single elements
    for (int i = 0; i < n; i++) {
        dp[i][i] = baseCase(arr[i]);
    }
    
    // Fill for all lengths
    for (int len = 2; len <= n; len++) {
        for (int i = 0; i <= n - len; i++) {
            int j = i + len - 1;
            dp[i][j] = Integer.MAX_VALUE;
            
            // Try all possible split points
            for (int k = i; k < j; k++) {
                dp[i][j] = Math.min(dp[i][j], 
                    dp[i][k] + dp[k+1][j] + cost(i, k, j));
            }
        }
    }
    
    return dp[0][n-1];
}
```

**LeetCode Problems**: 312 (Burst Balloons), 1312 (Minimum Insertion Steps to Make String Palindrome), 516 (Longest Palindromic Subsequence), 647 (Palindromic Substrings)

**Key Insights**:
- Length-based iteration is crucial
- Often involves trying all split points
- Palindrome problems are common interval DP applications
- Matrix chain multiplication is the classic example

### Pattern 5: State Machine DP
**When to use**: Problems with distinct states and transitions between states
**State definition**: `dp[i][state]` = optimal solution at position i in given state

```java
// Template for State Machine DP (Stock problems)
public int stockDP(int[] prices) {
    int n = prices.length;
    
    // States: held[i] = max profit on day i if holding stock
    //        sold[i] = max profit on day i if not holding stock
    int held = -prices[0]; // Buy on first day
    int sold = 0;          // Don't buy on first day
    
    for (int i = 1; i < n; i++) {
        int newHeld = Math.max(held, sold - prices[i]); // Keep holding or buy today
        int newSold = Math.max(sold, held + prices[i]); // Keep not holding or sell today
        
        held = newHeld;
        sold = newSold;
    }
    
    return sold; // Must end without holding stock
}
```

**LeetCode Problems**: 121 (Best Time to Buy and Sell Stock), 122, 123, 188, 309 (with cooldown), 714 (with transaction fee)

**Key Insights**:
- Define states clearly (hold/sold/cooldown)
- Track transaction limits when applicable
- State transitions must be valid
- Can often be space-optimized

### Pattern 6: Subsequence DP (Two Sequences)
**When to use**: Comparing two sequences for common patterns
**State definition**: `dp[i][j]` = optimal solution using first i elements of seq1 and first j elements of seq2

```java
// Template for Two Sequence DP
public int twoSequenceDP(String s1, String s2) {
    int m = s1.length(), n = s2.length();
    int[][] dp = new int[m + 1][n + 1];
    
    // Base cases
    for (int i = 0; i <= m; i++) dp[i][0] = baseCaseForS1(i);
    for (int j = 0; j <= n; j++) dp[0][j] = baseCaseForS2(j);
    
    for (int i = 1; i <= m; i++) {
        for (int j = 1; j <= n; j++) {
            if (s1.charAt(i-1) == s2.charAt(j-1)) {
                dp[i][j] = dp[i-1][j-1] + matchValue;
            } else {
                dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]); // or other operations
            }
        }
    }
    
    return dp[m][n];
}
```

**LeetCode Problems**: 1143 (Longest Common Subsequence), 72 (Edit Distance), 97 (Interleaving String), 115 (Distinct Subsequences)

**Key Insights**:
- Match vs no-match decisions at each step
- Edit distance problems involve insert/delete/replace operations
- Can be space-optimized to O(min(m,n))
- Base cases represent empty string scenarios

### Pattern 7: Palindrome DP
**When to use**: Problems involving palindromic properties
**State definition**: `dp[i][j]` = whether substring from i to j is palindrome (or palindrome-related property)

```java
// Template for Palindrome DP
public boolean[][] buildPalindromeDP(String s) {
    int n = s.length();
    boolean[][] dp = new boolean[n][n];
    
    // Single characters are palindromes
    for (int i = 0; i < n; i++) {
        dp[i][i] = true;
    }
    
    // Check for palindromes of length 2
    for (int i = 0; i < n - 1; i++) {
        dp[i][i + 1] = (s.charAt(i) == s.charAt(i + 1));
    }
    
    // Check for lengths greater than 2
    for (int len = 3; len <= n; len++) {
        for (int i = 0; i <= n - len; i++) {
            int j = i + len - 1;
            dp[i][j] = (s.charAt(i) == s.charAt(j)) && dp[i + 1][j - 1];
        }
    }
    
    return dp;
}
```

**LeetCode Problems**: 5 (Longest Palindromic Substring), 647 (Palindromic Substrings), 131 (Palindrome Partitioning), 132 (Palindrome Partitioning II)

**Key Insights**:
- Expand around centers or use length-based iteration
- Precompute palindrome table for complex problems
- Manacher's algorithm for linear time palindrome detection
- Partition problems often combine with other DP patterns

### Pattern 8: Bitmask DP
**When to use**: Small state space that can be represented as bits (typically n ≤ 20)
**State definition**: `dp[mask]` = optimal solution for state represented by bitmask

```java
// Template for Bitmask DP
public int bitmaskDP(int[][] cost) {
    int n = cost.length;
    int[] dp = new int[1 << n];
    Arrays.fill(dp, Integer.MAX_VALUE);
    dp[0] = 0;
    
    for (int mask = 0; mask < (1 << n); mask++) {
        if (dp[mask] == Integer.MAX_VALUE) continue;
        
        int person = Integer.bitCount(mask); // Next person to assign
        
        for (int task = 0; task < n; task++) {
            if ((mask & (1 << task)) == 0) { // Task not assigned
                int newMask = mask | (1 << task);
                dp[newMask] = Math.min(dp[newMask], dp[mask] + cost[person][task]);
            }
        }
    }
    
    return dp[(1 << n) - 1];
}
```

**LeetCode Problems**: 691 (Stickers to Spell Word), 1125 (Smallest Sufficient Team), 1284 (Minimum Number of Flips), 847 (Shortest Path Visiting All Nodes)

**Key Insights**:
- Use when n ≤ 20 (2^20 ≈ 1M states)
- Represent subsets as bitmasks
- TSP and assignment problems are classic applications
- Combine with other patterns (like graph traversal)

### Pattern 9: Tree DP
**When to use**: Optimization problems on trees
**State definition**: `dp[node][state]` = optimal solution for subtree rooted at node with given state

```java
// Template for Tree DP
public class TreeDP {
    private Map<TreeNode, Integer[]> memo = new HashMap<>();
    
    public int treeDP(TreeNode root) {
        if (root == null) return 0;
        
        if (memo.containsKey(root)) return memo.get(root)[0];
        
        // Two states: include root or not
        int include = root.val;
        int exclude = 0;
        
        if (root.left != null) {
            Integer[] leftDP = dfs(root.left);
            include += leftDP[1]; // If include root, can't include children
            exclude += Math.max(leftDP[0], leftDP[1]); // Can include or exclude children
        }
        
        if (root.right != null) {
            Integer[] rightDP = dfs(root.right);
            include += rightDP[1];
            exclude += Math.max(rightDP[0], rightDP[1]);
        }
        
        Integer[] result = new Integer[]{include, exclude};
        memo.put(root, result);
        return Math.max(include, exclude);
    }
    
    private Integer[] dfs(TreeNode node) {
        // Similar logic...
        return new Integer[]{0, 0}; // [include, exclude]
    }
}
```

**LeetCode Problems**: 337 (House Robber III), 124 (Binary Tree Maximum Path Sum), 968 (Binary Tree Cameras), 979 (Distribute Coins in Binary Tree)

**Key Insights**:
- Post-order traversal for bottom-up DP
- Often involves include/exclude states for each node
- Path problems may require tracking path through node vs ending at node
- Memoization or pass results up the recursion

### Pattern 10: String Matching DP
**When to use**: Pattern matching with wildcards or regular expressions
**State definition**: `dp[i][j]` = whether first i characters of string match first j characters of pattern

```java
// Template for String Matching DP
public boolean isMatch(String s, String p) {
    int m = s.length(), n = p.length();
    boolean[][] dp = new boolean[m + 1][n + 1];
    
    // Base case: empty pattern matches empty string
    dp[0][0] = true;
    
    // Handle patterns like a* that can match empty string
    for (int j = 1; j <= n; j++) {
        if (p.charAt(j - 1) == '*') {
            dp[0][j] = dp[0][j - 2]; // * matches zero occurrences
        }
    }
    
    for (int i = 1; i <= m; i++) {
        for (int j = 1; j <= n; j++) {
            char sc = s.charAt(i - 1);
            char pc = p.charAt(j - 1);
            
            if (pc == '*') {
                // * can match zero or more of preceding character
                dp[i][j] = dp[i][j - 2]; // Zero occurrences
                if (matches(sc, p.charAt(j - 2))) {
                    dp[i][j] |= dp[i - 1][j]; // One or more occurrences
                }
            } else if (matches(sc, pc)) {
                dp[i][j] = dp[i - 1][j - 1];
            }
        }
    }
    
    return dp[m][n];
}

private boolean matches(char s, char p) {
    return p == '.' || s == p;
}
```

**LeetCode Problems**: 10 (Regular Expression Matching), 44 (Wildcard Matching), 87 (Scramble String)

**Key Insights**:
- Handle wildcards and special characters carefully
- Base cases for empty strings/patterns are crucial
- Regular expressions are more complex than simple wildcards
- Consider zero-occurrence matches for * patterns

## DP Implementation Strategies

### Strategy 1: Top-Down (Memoization)
**When to use**: Complex state transitions, not all subproblems needed

```java
private Map<String, Integer> memo = new HashMap<>();

public int topDown(int i, int j, /* other params */) {
    // Create unique key for state
    String key = i + "," + j + "," + /* other params */;
    if (memo.containsKey(key)) return memo.get(key);
    
    // Base cases
    if (i >= n || j >= m) return baseCase;
    
    // Recursive relations
    int result = Math.max(
        topDown(i + 1, j, /* params */) + choice1,
        topDown(i, j + 1, /* params */) + choice2
    );
    
    memo.put(key, result);
    return result;
}
```

### Strategy 2: Bottom-Up (Tabulation)
**When to use**: Clear order of subproblem dependencies, need all subproblems

```java
public int bottomUp(int n, int m) {
    int[][] dp = new int[n + 1][m + 1];
    
    // Base cases
    for (int i = 0; i <= n; i++) dp[i][0] = baseCaseI(i);
    for (int j = 0; j <= m; j++) dp[0][j] = baseCaseJ(j);
    
    // Fill table in correct order
    for (int i = 1; i <= n; i++) {
        for (int j = 1; j <= m; j++) {
            dp[i][j] = Math.max(
                dp[i-1][j] + cost1,
                dp[i][j-1] + cost2
            );
        }
    }
    
    return dp[n][m];
}
```

### Strategy 3: Space Optimization
**When to use**: Only need previous row/column for current computation

```java
public int spaceOptimized(int n, int m) {
    int[] prev = new int[m + 1];
    int[] curr = new int[m + 1];
    
    // Initialize base cases
    for (int j = 0; j <= m; j++) prev[j] = baseCaseJ(j);
    
    for (int i = 1; i <= n; i++) {
        curr[0] = baseCaseI(i);
        for (int j = 1; j <= m; j++) {
            curr[j] = Math.max(prev[j] + cost1, curr[j-1] + cost2);
        }
        // Swap arrays
        int[] temp = prev;
        prev = curr;
        curr = temp;
    }
    
    return prev[m];
}
```

## Pattern Recognition Decision Tree

1. **Is it asking for optimal (max/min) or count of ways?** → Likely DP
2. **What's the structure of the problem?**
   - **Sequence with choices** → Linear DP
   - **2D grid movement** → Grid DP
   - **Items + capacity** → Knapsack DP
   - **Range/interval** → Interval DP
   - **Multiple states** → State Machine DP
   - **Two sequences** → Subsequence DP
   - **Palindrome properties** → Palindrome DP
   - **Small subset (n≤20)** → Bitmask DP
   - **Tree structure** → Tree DP
   - **Pattern matching** → String Matching DP

## LeetCode 150 DP Problems Coverage

### Easy (70, 121, 198)
- **70. Climbing Stairs**: Linear DP (Fibonacci variant)
- **121. Best Time to Buy and Sell Stock**: State Machine DP (single transaction)
- **198. House Robber**: Linear DP (include/exclude)

### Medium (5, 22, 53, 62, 63, 64, 91, 120, 139, 152, 189, 213, 221, 279, 300, 322, 337, 416, 494, 518, 647, 714, 746, 1143)
- **5. Longest Palindromic Substring**: Palindrome DP
- **53. Maximum Subarray**: Linear DP (Kadane's algorithm)
- **62, 63. Unique Paths**: Grid DP
- **64. Minimum Path Sum**: Grid DP
- **91. Decode Ways**: Linear DP
- **120. Triangle**: Grid DP (variant)
- **139. Word Break**: Linear DP with string matching
- **152. Maximum Product Subarray**: Linear DP (track min/max)
- **213. House Robber II**: Linear DP (circular constraint)
- **221. Maximal Square**: Grid DP
- **279. Perfect Squares**: Knapsack DP
- **300. Longest Increasing Subsequence**: Linear DP (with binary search optimization)
- **322. Coin Change**: Knapsack DP (min coins)
- **337. House Robber III**: Tree DP
- **416. Partition Equal Subset Sum**: Knapsack DP (boolean)
- **494. Target Sum**: Knapsack DP
- **518. Coin Change II**: Knapsack DP (count ways)
- **647. Palindromic Substrings**: Palindrome DP
- **714. Best Time to Buy and Sell Stock with Transaction Fee**: State Machine DP
- **746. Min Cost Climbing Stairs**: Linear DP
- **1143. Longest Common Subsequence**: Subsequence DP

### Hard (10, 32, 42, 72, 85, 115, 123, 132, 174, 188, 309, 312, 410, 514, 516, 1312)
- **10. Regular Expression Matching**: String Matching DP
- **32. Longest Valid Parentheses**: Linear DP
- **42. Trapping Rain Water**: Linear DP
- **72. Edit Distance**: Subsequence DP
- **85. Maximal Rectangle**: Grid DP (with stack optimization)
- **115. Distinct Subsequences**: Subsequence DP
- **123, 188. Best Time to Buy and Sell Stock III & IV**: State Machine DP (transaction limits)
- **132. Palindrome Partitioning II**: Palindrome DP + Linear DP
- **174. Dungeon Game**: Grid DP (reverse direction)
- **309. Best Time to Buy and Sell Stock with Cooldown**: State Machine DP
- **312. Burst Balloons**: Interval DP
- **516. Longest Palindromic Subsequence**: Palindrome DP

## Time and Space Complexity Patterns

### Time Complexity:
- **1D DP**: O(n) or O(n²) with inner loops
- **2D DP**: O(n²) or O(n³) with inner loops
- **Knapsack**: O(n × capacity)
- **Interval DP**: O(n³) typically
- **String DP**: O(n × m) for two strings
- **Bitmask DP**: O(n × 2^n)
- **Tree DP**: O(n) where n is number of nodes

### Space Complexity:
- **Original**: Same as time complexity for space
- **Optimized**: Often reducible to O(1) or O(n) using rolling arrays
- **Memoization**: Can be higher due to recursion stack

## Common Mistakes and Tips

### Common Mistakes:
1. **Wrong base cases**: Always handle edge cases carefully
2. **Incorrect state transitions**: Ensure all valid transitions are considered
3. **Off-by-one errors**: Pay attention to 0-indexed vs 1-indexed
4. **Memory limits**: Use space optimization when possible
5. **Integer overflow**: Use long when needed

### Pro Tips:
1. **Start with brute force**: Understand the problem first
2. **Identify overlapping subproblems**: This confirms DP is applicable
3. **Draw the state transition**: Visualize how states connect
4. **Test with small examples**: Verify your recurrence relation
5. **Consider space optimization**: After getting the correct solution

## Template Selection Quick Reference

| Problem Type | Pattern | Time | Space | Key Insight |
|-------------|---------|------|-------|-------------|
| Sequential choices | Linear DP | O(n) | O(1) | Include/exclude decisions |
| Grid traversal | Grid DP | O(mn) | O(mn) | Path-dependent optimization |
| Items + constraints | Knapsack | O(nW) | O(W) | Capacity-based decisions |
| Range operations | Interval DP | O(n³) | O(n²) | Split-point optimization |
| Multiple states | State Machine | O(n) | O(1) | State transitions |
| Two sequences | Subsequence DP | O(mn) | O(mn) | Match/no-match decisions |
| Palindromes | Palindrome DP | O(n²) | O(n²) | Center expansion or length-based |
| Small subsets | Bitmask DP | O(n2^n) | O(2^n) | Subset enumeration |
| Tree problems | Tree DP | O(n) | O(h) | Include/exclude nodes |
| Pattern matching | String Match DP | O(mn) | O(mn) | Wildcard/regex handling |

This comprehensive guide covers all major DP patterns you'll encounter in LeetCode 150 and technical interviews. Each pattern includes the key problems, insights, and implementation details you need to master dynamic programming.
