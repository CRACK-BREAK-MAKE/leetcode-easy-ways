# Understanding Dynamic Programming Patterns

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

**Classic Examples:**
- **House Robber**: `dp[i] = max(dp[i-1], dp[i-2] + nums[i])`
- **Maximum Subarray**: `dp[i] = max(dp[i-1] + nums[i], nums[i])`
- **Climbing Stairs**: `dp[i] = dp[i-1] + dp[i-2]`

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

**Classic Examples:**
- **Unique Paths**: `dp[i][j] = dp[i-1][j] + dp[i][j-1]`
- **Minimum Path Sum**: `dp[i][j] = grid[i][j] + min(dp[i-1][j], dp[i][j-1])`
- **Edit Distance**: `dp[i][j] = min of insert/delete/replace operations`

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

**Variations:**
- **0/1 Knapsack**: Each item used at most once
- **Unbounded Knapsack**: Items can be used multiple times
- **Coin Change**: Special case of unbounded knapsack

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

**Classic Examples:**
- **Matrix Chain Multiplication**: `dp[i][j] = min cost to multiply matrices i to j`
- **Palindrome Partitioning**: `dp[i][j] = min cuts for s[i...j] to be palindromic`
- **Burst Balloons**: `dp[i][j] = max coins from bursting balloons between i and j`

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

**Classic Examples:**
- **Best Time to Buy/Sell Stock**: Various state transitions
- **House Robber in Circle**: States for robbing first house or not
- **Paint House**: States for different colors

### Pattern 6: Digit DP
**When to use**: Problems involving constraints on digits of numbers
**State definition**: `dp[pos][tight][state]` = count/sum for position pos with constraints

```java
// Template for Digit DP
public class DigitDP {
    private int[][][] memo;
    private String num;
    
    public int digitDP(int n) {
        num = String.valueOf(n);
        memo = new int[num.length()][2][/* state size */];
        // Initialize memo with -1
        
        return solve(0, 1, initialState);
    }
    
    private int solve(int pos, int tight, int state) {
        if (pos == num.length()) {
            return isValidState(state) ? 1 : 0;
        }
        
        if (memo[pos][tight][state] != -1) {
            return memo[pos][tight][state];
        }
        
        int limit = tight == 1 ? (num.charAt(pos) - '0') : 9;
        int result = 0;
        
        for (int digit = 0; digit <= limit; digit++) {
            int newTight = (tight == 1 && digit == limit) ? 1 : 0;
            int newState = updateState(state, digit);
            result += solve(pos + 1, newTight, newState);
        }
        
        return memo[pos][tight][state] = result;
    }
}
```

## DP Implementation Strategies

### Strategy 1: Top-Down (Memoization)
**When to use**: Complex state transitions, not all subproblems needed
```java
private Map<String, Integer> memo = new HashMap<>();

public int topDown(/* parameters */) {
    String key = createKey(/* parameters */);
    if (memo.containsKey(key)) return memo.get(key);
    
    // Base cases
    if (baseCondition) return baseValue;
    
    // Recursive relations
    int result = /* recursive calls with memoization */;
    
    memo.put(key, result);
    return result;
}
```

### Strategy 2: Bottom-Up (Tabulation)
**When to use**: Clear order of subproblem dependencies, need all subproblems
```java
public int bottomUp(/* parameters */) {
    // Initialize DP table
    int[][] dp = new int[size1][size2];
    
    // Base cases
    initializeBaseCases(dp);
    
    // Fill table in correct order
    for (int i = /* start */; i < /* end */; i++) {
        for (int j = /* start */; j < /* end */; j++) {
            dp[i][j] = /* recurrence relation */;
        }
    }
    
    return dp[/* final answer position */];
}
```

### Strategy 3: Space Optimization
**When to use**: Only need previous row/column for current computation
```java
public int spaceOptimized(/* parameters */) {
    // Instead of 2D array, use 1D or just variables
    int[] prev = new int[size];
    int[] curr = new int[size];
    
    for (int i = 0; i < /* iterations */; i++) {
        for (int j = 0; j < size; j++) {
            curr[j] = /* recurrence using prev */;
        }
        // Swap arrays
        int[] temp = prev;
        prev = curr;
        curr = temp;
    }
    
    return prev[/* answer position */];
}
```

## Pattern Recognition Decision Tree

1. **Is it asking for optimal (max/min) or count of ways?** → Likely DP
2. **Can I make choices at each step?** → Yes, continue to next question
3. **What are my states/parameters that change?**
    - One changing parameter → Linear DP
    - Two parameters (often position-based) → Grid/2D DP
    - Capacity + items → Knapsack DP
    - Range/interval → Interval DP
    - Distinct modes/states → State Machine DP
    - Digit constraints → Digit DP

## Common DP Problem Categories

### Category 1: Sequence DP
- **Longest Increasing Subsequence**: `dp[i] = max length ending at i`
- **Longest Common Subsequence**: `dp[i][j] = LCS of first i and j characters`
- **Maximum Subarray**: `dp[i] = max sum ending at i`

### Category 2: Grid Traversal DP
- **Unique Paths**: Count ways to reach bottom-right
- **Minimum Path Sum**: Find cheapest path
- **Dungeon Game**: Minimum health needed

### Category 3: Decision-Making DP
- **House Robber**: Choose houses to rob optimally
- **Best Time to Buy/Sell Stock**: Choose when to buy/sell
- **Jump Game**: Choose jumps to reach end

### Category 4: Partition DP
- **Palindrome Partitioning**: Minimum cuts for palindromic partition
- **Word Break**: Can string be segmented into words
- **Perfect Squares**: Minimum squares that sum to n

### Category 5: Game Theory DP
- **Stone Game**: Optimal play in two-player games
- **Predict the Winner**: Who wins with optimal play
- **Nim Game**: Game state analysis

## Time and Space Complexity Patterns

### Time Complexity:
- **1D DP**: O(n) or O(n²) with inner loops
- **2D DP**: O(n²) or O(n³) with inner loops
- **Knapsack**: O(n × capacity)
- **Interval DP**: O(n³) typically
- **String DP**: O(n × m) for two strings

### Space Complexity:
- **Original**: Same as time complexity for space
- **Optimized**: Often reducible to O(1) or O(n) using rolling arrays

## Red Flags for DP Problems

### Problem Statement Keywords:
- "Maximum/Minimum" + "optimal"
- "Count number of ways"
- "Can you reach/achieve"
- "Longest/Shortest" subsequence/path
- "Partition into" + optimal condition
- "Game theory" + optimal play

### Structural Indicators:
- Choices at each step
- Current choice affects future choices
- Overlapping subproblems (same calculation repeated)
- Optimal substructure (optimal solution uses optimal subsolutions)

## Template Selection Guide

| Problem Characteristics | Pattern | Key Insight |
|------------------------|---------|-------------|
| Sequence with choices | Linear DP | State = position in sequence |
| 2D grid movement | Grid DP | State = (row, col) position |
| Items + capacity limit | Knapsack | State = (items considered, remaining capacity) |
| Range/interval optimization | Interval DP | State = (start, end) of range |
| Multiple distinct states | State Machine | State = current mode/condition |
| Digit/number constraints | Digit DP | State = (position, tight, extra info) |
