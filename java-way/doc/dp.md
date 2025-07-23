# Dynamic Programming - Complete Interview Mastery Guide

## The Core Intuition

Dynamic Programming is about **optimal substructure** and **overlapping subproblems**. The magic happens when you recognize that a complex problem can be broken down into simpler versions of itself that get reused multiple times.

### The Mental Model
Think of DP as "smart recursion with memory" - instead of solving the same subproblem repeatedly, we store and reuse solutions.

## DP Problem Recognition Framework

### The Four-Question Test:
1. **Can I make a choice?** (Decision at each step)
2. **Does this choice affect future choices?** (Current decision impacts future state)
3. **Are there optimal subproblems?** (Optimal solution contains optimal solutions to subproblems)
4. **Are subproblems overlapping?** (Same subproblems get solved repeatedly)

If all four are "Yes" → **Definitely a DP problem**

### Additional Red Flags:
- Keywords: "maximum/minimum", "count ways", "optimal", "longest/shortest", "how many"
- Constraints suggest recursion: small N (≤ 20 for exponential, ≤ 1000 for polynomial)
- Multiple approaches possible with different outcomes
- Involves sequences, subsequences, or subsets

### The Four-Step DP Solution Approach:
1. **Define the state** (What does dp[i] represent?)
2. **Find the recurrence relation** (How do states connect?)
3. **Initialize base cases** (What are the simplest cases?)
4. **Determine the order** (Bottom-up or top-down?)

---

## DP Implementation Strategies

### Strategy 1: Top-Down (Memoization)

**When to use**: Complex state transitions, not all subproblems needed

**Pros**: Intuitive, only computes needed subproblems, easier to implement

**Cons**: Recursion overhead, potential stack overflow

```java
public int topDownDP(int[] arr, int index, int[] memo) {
    // Base case
    if (index >= arr.length) return 0;
    
    // Check memo
    if (memo[index] != -1) return memo[index];
    
    // Compute and store
    int result = Math.max(
        topDownDP(arr, index + 1, memo),           // Skip
        arr[index] + topDownDP(arr, index + 2, memo)  // Take
    );
    
    return memo[index] = result;
}
```

### Strategy 2: Bottom-Up (Tabulation)

**When to use**: Clear order of subproblem dependencies, need all subproblems

**Pros**: No recursion overhead, often faster, easier to optimize space

**Cons**: May compute unnecessary subproblems

```java
public int bottomUpDP(int[] arr) {
    int n = arr.length;
    if (n == 0) return 0;
    if (n == 1) return arr[0];
    
    int[] dp = new int[n];
    dp[0] = arr[0];
    dp[1] = Math.max(arr[0], arr[1]);
    
    for (int i = 2; i < n; i++) {
        dp[i] = Math.max(dp[i-1], dp[i-2] + arr[i]);
    }
    
    return dp[n-1];
}
```

### Strategy 3: Space Optimization

**When to use**: Only need previous row/column for current computation

```java
public int spaceOptimizedDP(int[] arr) {
    if (arr.length == 0) return 0;
    if (arr.length == 1) return arr[0];
    
    int prev2 = arr[0];
    int prev1 = Math.max(arr[0], arr[1]);
    
    for (int i = 2; i < arr.length; i++) {
        int current = Math.max(prev1, prev2 + arr[i]);
        prev2 = prev1;
        prev1 = current;
    }
    
    return prev1;
}
```

---

## Pattern Recognition Decision Tree

```
                     DP Problem?
                    /           \
                  Yes            No
                 /                \
        What structure?        Try other approaches
       /     |      \
   Sequence Grid  Choices
      |       |       |
   Linear   2D DP   Knapsack
     |       |       |
  Is it 1D? Path?  0/1 or Unbounded?
```

**Quick Pattern Identification:**
1. **Linear sequence + choices** → Linear DP
2. **2D grid + path optimization** → Grid DP
3. **Items + capacity/target** → Knapsack DP
4. **Ranges/intervals** → Interval DP
5. **Multiple states + transitions** → State Machine DP
6. **Two strings/sequences** → String DP
7. **Subsequence properties** → Subsequence DP
8. **Optimal partitioning** → Partition DP
9. **Tree structure** → Tree DP
10. **Small set (≤20)** → Bitmask DP
11. **Probability/expected value** → Probability DP
12. **Count digits with properties** → Digit DP

---
## Core DP Patterns (Complete Classification - 12 Patterns)

### Pattern 1: Linear DP (1D Sequence)

**Recognition**: Sequential data with decisions affecting future choices, State depends only on previous elements in sequence

**State definition**: `dp[i]` = optimal solution ending at/including index i

**Flow**: `dp[i]` depends on `dp[i-1]`, `dp[i-2]`, etc.

**Time Complexity**: O(n), **Space Complexity**: O(n) → O(1) optimizable

```
Visual Flow:
Input:  [a₀, a₁, a₂, a₃, ..., aₙ₋₁]
States: [dp₀, dp₁, dp₂, dp₃, ..., dpₙ₋₁]
        ↳─────┘ ↳──────┘ ↳───────┘
        Each state depends on previous states

Recursion Tree (Fibonacci):
        fib(5)
       /      \
   fib(4)    fib(3)
   /    \    /    \
fib(3) fib(2) fib(2) fib(1)
/  \   /   \
...overlapping subproblems...
```

**Template:**
```java
public int linearDP(int[] arr) {
    int n = arr.length;
    if (n == 0) return 0;
    if (n == 1) return arr[0];
    
    int[] dp = new int[n];
    dp[0] = arr[0];
    dp[1] = Math.max(arr[0], arr[1]);
    
    for (int i = 2; i < n; i++) {
        dp[i] = Math.max(
            dp[i-1],         // Skip current
            dp[i-2] + arr[i] // Take current
        );
    }
    return dp[n-1];
}

// Space Optimized O(1)
public int linearDPOptimized(int[] arr) {
    if (arr.length == 1) return arr[0];
    
    int prev2 = arr[0];
    int prev1 = Math.max(arr[0], arr[1]);
    
    for (int i = 2; i < arr.length; i++) {
        int current = Math.max(prev1, prev2 + arr[i]);
        prev2 = prev1;
        prev1 = current;
    }
    return prev1;
}
```

**Problems & Complexities:**
- **70. Climbing Stairs**: O(n) time, O(1) space - `dp[i] = dp[i-1] + dp[i-2]`
- **198. House Robber**: O(n) time, O(1) space - `dp[i] = max(dp[i-1], dp[i-2] + nums[i])`
- **213. House Robber II**: O(n) time, O(1) space - Two linear DP calls (circular)
- **746. Min Cost Climbing Stairs**: O(n) time, O(1) space
- **91. Decode Ways**: O(n) time, O(1) space - String patterns
- **53. Maximum Subarray**: O(n) time, O(1) space - Kadane's algorithm
- **152. Maximum Product Subarray**: O(n) time, O(1) space - Track min/max
- **300. Longest Increasing Subsequence**: O(n log n) time, O(n) space
- **322. Coin Change**: O(n×amount) time, O(amount) space

**Simple Example (Fibonacci)**:
```
F(0) = 0, F(1) = 1
F(2) = F(1) + F(0) = 1
F(3) = F(2) + F(1) = 2
F(4) = F(3) + F(2) = 3
```

**Key Insights:**
- Look for problems where current state only depends on previous few states
- Often space-optimizable to O(1) using variables instead of array
- Pattern: `dp[i] = f(dp[i-1], dp[i-2], ..., arr[i])`
- Often involves include/exclude decisions
- Watch for circular constraints (House Robber II)

---

### Pattern 2: Grid/2D DP

**Recognition**: 2D grid traversal with path-dependent optimization

**State**: `dp[i][j]` = optimal solution to reach position (i,j)

**Flow**: `dp[i][j]` depends on `dp[i-1][j]` and `dp[i][j-1]`

**Time Complexity**: O(m×n), **Space Complexity**: O(m×n) → O(n) optimizable

```
Visual Flow:
Grid:      DP Table:
S → → E    dp[0][0] → dp[0][1] → dp[0][2]
↓   ↓ ↓       ↓          ↓          ↓
→ → → →    dp[1][0] → dp[1][1] → dp[1][2]
↓   ↓ ↓       ↓          ↓          ↓
→ → → E    dp[2][0] → dp[2][1] → dp[2][2]

Recursion Pattern:
paths(i,j) = paths(i-1,j) + paths(i,j-1)
```

**Template:**
```java
public int gridDP(int[][] grid) {
    int m = grid.length, n = grid[0].length;
    int[][] dp = new int[m][n];
    
    // Base case
    dp[0][0] = grid[0][0];
    
    // Initialize first row
    for (int j = 1; j < n; j++) {
        dp[0][j] = dp[0][j-1] + grid[0][j];
    }
    
    // Initialize first column
    for (int i = 1; i < m; i++) {
        dp[i][0] = dp[i-1][0] + grid[i][0];
    }
    
    // Fill remaining cells
    for (int i = 1; i < m; i++) {
        for (int j = 1; j < n; j++) {
            dp[i][j] = grid[i][j] + Math.min(dp[i-1][j], dp[i][j-1]);
        }
    }
    
    return dp[m-1][n-1];
}
```

**Problems & Complexities:**
- **62. Unique Paths**: O(mn) time, O(n) space - Counting paths
- **63. Unique Paths II**: O(mn) time, O(n) space - With obstacles
- **64. Minimum Path Sum**: O(mn) time, O(n) space - Cost optimization
- **120. Triangle**: O(n²) time, O(n) space - Variable width grid
- **174. Dungeon Game**: O(mn) time, O(n) space - Reverse DP
- **931. Minimum Falling Path Sum**: O(n²) time, O(n) space
- **221. Maximal Square**: O(mn) time, O(n) space - 2D shape optimization

**Simple Example (Unique Paths)**:
```
3×3 Grid paths:
1 → 1 → 1
↓   ↓   ↓
1 → 2 → 3
↓   ↓   ↓
1 → 3 → 6
dp[i][j] = dp[i-1][j] + dp[i][j-1]
```

**Key Insights:**
- Initialize boundaries carefully (first row and column)
- Movement typically restricted to right and down
- Space optimization: use only current and previous row
- Consider all possible directions for some problems

---

### Pattern 3: Knapsack DP (Choice-based)

**Recognition**: Items with weight/value, capacity constraint, optimization needed

**State**: `dp[i][w]` = maximum value using first i items with weight limit w

**Flow**: `dp[i][w] = max(dp[i-1][w], dp[i-1][w-weight[i]] + value[i])`

**Time Complexity**: O(n×W), **Space Complexity**: O(n×W) → O(W) optimizable

```
Recursion Tree (0/1 Knapsack):
    knapsack(items, capacity)
         /              \
include item[0]      exclude item[0]
knapsack(items[1:],  knapsack(items[1:],
capacity-w[0])           capacity)

Visual DP Table:
Items: [w1,v1] [w2,v2] [w3,v3]
Cap:   0  1  2  3  4  5
 0     0  0  0  0  0  0
 1     0  v1 v1 v1 v1 v1
 2     0  v1 max(v1,v2) ...
```

**Template:**
```java
// 0/1 Knapsack
public int knapsack01(int[] weights, int[] values, int capacity) {
    int n = weights.length;
    int[][] dp = new int[n + 1][capacity + 1];
    
    for (int i = 1; i <= n; i++) {
        for (int w = 1; w <= capacity; w++) {
            if (weights[i-1] <= w) {
                dp[i][w] = Math.max(
                    dp[i-1][w],  // Don't take item
                    dp[i-1][w-weights[i-1]] + values[i-1]  // Take item
                );
            } else {
                dp[i][w] = dp[i-1][w];
            }
        }
    }
    return dp[n][capacity];
}

// Unbounded Knapsack
public int knapsackUnbounded(int[] weights, int[] values, int capacity) {
    int[] dp = new int[capacity + 1];
    
    for (int w = 1; w <= capacity; w++) {
        for (int i = 0; i < weights.length; i++) {
            if (weights[i] <= w) {
                dp[w] = Math.max(dp[w], dp[w - weights[i]] + values[i]);
            }
        }
    }
    return dp[capacity];
}
```

**Problems & Complexities:**
- **416. Partition Equal Subset Sum**: O(n×sum) time, O(sum) space
- **494. Target Sum**: O(n×sum) time, O(sum) space
- **322. Coin Change**: O(n×amount) time, O(amount) space
- **518. Coin Change 2**: O(n×amount) time, O(amount) space
- **377. Combination Sum IV**: O(target×n) time, O(target) space
- **279. Perfect Squares**: O(n×√n) time, O(n) space
- **139. Word Break**: O(n²×m) time, O(n) space (where m is avg word length)

**Key Insights:**
- 0/1 Knapsack: each item used at most once
- Unbounded Knapsack: items can be used multiple times
- Often reduces to subset sum or coin change variations
- Space optimization possible by using 1D array

---

### Pattern 4: Interval DP (Range-based)

**Recognition**: Problems on ranges/intervals, optimal way to process a range

**State**: `dp[i][j]` = optimal solution for range [i, j]

**Flow**: `dp[i][j]` depends on splitting range at different points k

**Time Complexity**: O(n³), **Space Complexity**: O(n²)

```
Recursion Pattern:
solve(i, j) = min/max over all k in [i, j-1] of:
              solve(i, k) + solve(k+1, j) + cost(i, j, k)

Visual Flow (Matrix Chain):
Range [i,j]: A[i]×A[i+1]×...×A[j]
Split at k:  [i,k] and [k+1,j]
dp[i][j] = min(dp[i][k] + dp[k+1][j] + cost[i][k][j])
```

**Template:**
```java
public int intervalDP(int[] arr) {
    int n = arr.length;
    int[][] dp = new int[n][n];
    
    // Fill for different lengths
    for (int len = 2; len <= n; len++) {
        for (int i = 0; i <= n - len; i++) {
            int j = i + len - 1;
            dp[i][j] = Integer.MAX_VALUE;
            
            // Try all possible split points
            for (int k = i; k < j; k++) {
                int cost = dp[i][k] + dp[k+1][j] + calculateCost(arr, i, j, k);
                dp[i][j] = Math.min(dp[i][j], cost);
            }
        }
    }
    
    return dp[0][n-1];
}
```

**Problems & Complexities:**
- **516. Longest Palindromic Subsequence**: O(n²) time, O(n²) space
- **647. Palindromic Substrings**: O(n²) time, O(1) space
- **5. Longest Palindromic Substring**: O(n²) time, O(n²) space
- **312. Burst Balloons**: O(n³) time, O(n²) space
- **1039. Minimum Score Triangulation**: O(n³) time, O(n²) space
- **375. Guess Number Higher or Lower II**: O(n³) time, O(n²) space

**Simple Example (Palindrome Check)**:
```
String: "babad"
dp[i][j] = true if s[i:j+1] is palindrome

   b a b a d
b  T F T F F
a    T F T F  
b      T F F
a        T F
d          T
```

**Key Insights:**
- Build solutions from smaller ranges to larger ranges
- Often involves finding optimal split points
- Classic problems: matrix chain multiplication, palindromes
- Bottom-up approach typically used

---

### Pattern 5: State Machine DP

**Recognition**: Multiple states with transitions, each state has constraints

**State**: `dp[i][state]` = optimal solution at position i in given state

**Flow**: Transitions between states based on actions

**Time Complexity**: O(n×states), **Space Complexity**: O(n×states) → O(states)

```
State Transition Diagram (Stock Trading):
   buy      sell
[Hold] ←→ [Sold] ←→ [Rest]
  ↑                   ↓
  └─── cooldown ──────┘

Visual Flow:
Day:    0    1    2    3    4
Buy:   -p0  max(-p0,-p1)  ...
Sell:   0   max(0,buy0+p1) ...  
Rest:   0   max(0,sell0)   ...
```

**Template:**
```java
public int stockTrading(int[] prices) {
    int n = prices.length;
    // States: 0=hold, 1=sold, 2=rest
    int[][] dp = new int[n][3];
    
    dp[0][0] = -prices[0];  // Buy on day 0
    dp[0][1] = 0;           // Can't sell on day 0
    dp[0][2] = 0;           // Rest on day 0
    
    for (int i = 1; i < n; i++) {
        dp[i][0] = Math.max(dp[i-1][0], dp[i-1][2] - prices[i]); // Hold
        dp[i][1] = dp[i-1][0] + prices[i];                       // Sell
        dp[i][2] = Math.max(dp[i-1][1], dp[i-1][2]);            // Rest
    }
    
    return Math.max(dp[n-1][1], dp[n-1][2]);
}
```

**Problems & Complexities:**
- **121. Best Time to Buy and Sell Stock**: O(n) time, O(1) space
- **122. Best Time to Buy and Sell Stock II**: O(n) time, O(1) space
- **123. Best Time to Buy and Sell Stock III**: O(n) time, O(1) space
- **188. Best Time to Buy and Sell Stock IV**: O(nk) time, O(k) space
- **309. Best Time to Buy and Sell Stock with Cooldown**: O(n) time, O(1) space
- **714. Best Time to Buy and Sell Stock with Transaction Fee**: O(n) time, O(1) space

**Key Insights:**
- Define clear states and valid transitions
- Often space-optimizable since we only need previous states
- Common in stock trading, game theory problems
- State transitions must be carefully designed

---

### Pattern 6: String DP (Two Sequences)

**Recognition**: Two strings/sequences, find relationship or transformation

**State**: `dp[i][j]` = solution for first i chars of string1 and first j chars of string2

**Flow**: Based on character match/mismatch at positions i-1 and j-1

**Time Complexity**: O(m×n), **Space Complexity**: O(m×n) → O(min(m,n))

```
Recursion Pattern (LCS):
        lcs(i, j)
         /      \
   s1[i]==s2[j]  s1[i]!=s2[j]
      /              \
1+lcs(i-1,j-1)   max(lcs(i-1,j), lcs(i,j-1))

DP Table Visualization:
    ""  a  b  c
""   0  0  0  0
a    0  1  1  1
b    0  1  2  2
c    0  1  2  3
```

**Template:**
```java
public int stringDP(String s1, String s2) {
    int m = s1.length(), n = s2.length();
    int[][] dp = new int[m + 1][n + 1];
    
    // Base cases handled by default initialization
    
    for (int i = 1; i <= m; i++) {
        for (int j = 1; j <= n; j++) {
            if (s1.charAt(i-1) == s2.charAt(j-1)) {
                dp[i][j] = dp[i-1][j-1] + 1;  // Match
            } else {
                dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]);  // No match
            }
        }
    }
    
    return dp[m][n];
}
```

**Problems & Complexities:**
- **1143. Longest Common Subsequence**: O(mn) time, O(mn) space
- **72. Edit Distance**: O(mn) time, O(min(m,n)) space
- **10. Regular Expression Matching**: O(mn) time, O(mn) space
- **44. Wildcard Matching**: O(mn) time, O(n) space
- **97. Interleaving String**: O(mn) time, O(n) space
- **115. Distinct Subsequences**: O(mn) time, O(n) space
- **583. Delete Operation for Two Strings**: O(mn) time, O(n) space

**Key Insights:**
- Usually involves character-by-character comparison
- Base cases: empty strings
- Common patterns: match/mismatch decisions
- Space optimization by using rolling arrays

---

### Pattern 7: Subsequence DP

**Recognition**: Find properties of subsequences (not necessarily contiguous)

**State**: `dp[i]` = optimal subsequence ending at index i

**Flow**: For each position, decide to extend existing subsequences or start new ones

**Time Complexity**: O(n²) or O(n log n) with optimization

```
LIS Recursion Tree:
       lis(arr, i)
      /           \
include arr[i]   exclude arr[i]
(if valid)       lis(arr, i+1)
   |
1 + lis(arr, i+1)

Visual LIS:
Array: [10, 22, 9, 33, 21, 50, 41, 60]
LIS:   [1,  2,  1, 3,  3,  4,  4,  5]
```

**Template:**
```java
// O(n²) LIS
public int longestIncreasingSubsequence(int[] nums) {
    int n = nums.length;
    int[] dp = new int[n];
    Arrays.fill(dp, 1);
    
    for (int i = 1; i < n; i++) {
        for (int j = 0; j < i; j++) {
            if (nums[j] < nums[i]) {
                dp[i] = Math.max(dp[i], dp[j] + 1);
            }
        }
    }
    
    return Arrays.stream(dp).max().orElse(0);
}

// O(n log n) LIS with Binary Search
public int longestIncreasingSubsequenceOptimized(int[] nums) {
    List<Integer> tails = new ArrayList<>();
    
    for (int num : nums) {
        int pos = Collections.binarySearch(tails, num);
        if (pos < 0) pos = -(pos + 1);
        
        if (pos == tails.size()) {
            tails.add(num);
        } else {
            tails.set(pos, num);
        }
    }
    
    return tails.size();
}
```

**Problems & Complexities:**
- **300. Longest Increasing Subsequence**: O(n log n) time, O(n) space
- **674. Longest Continuous Increasing Subsequence**: O(n) time, O(1) space
- **354. Russian Doll Envelopes**: O(n log n) time, O(n) space
- **368. Largest Divisible Subset**: O(n²) time, O(n) space

**Key Insights:**
- Can often be optimized with binary search
- Track the actual subsequence for reconstruction
- Patience sorting algorithm for O(n log n) LIS

---

### Pattern 8: Partition DP

**Recognition**: Partition array/string optimally based on some criteria

**State**: `dp[i]` = optimal way to partition array[0...i-1]

**Flow**: Try all possible last partitions ending at position i

**Time Complexity**: O(n²) to O(n³), **Space Complexity**: O(n)

```
Recursion Pattern:
partition(s, i) = min over all j < i of:
                  partition(s, j) + cost(j, i)

Visual Flow:
String: "abcde"
Partitions at position 3:
- [abc][de]  → dp[3] + cost(3,5)
- [ab][cde]  → dp[2] + cost(2,5)  
- [a][bcde]  → dp[1] + cost(1,5)
- [abcde]    → dp[0] + cost(0,5)
```

**Template:**
```java
public int partitionDP(String s) {
    int n = s.length();
    int[] dp = new int[n + 1];
    dp[0] = 0;  // Base case: empty string
    
    for (int i = 1; i <= n; i++) {
        dp[i] = Integer.MAX_VALUE;
        
        // Try all possible last partitions
        for (int j = 0; j < i; j++) {
            if (isValid(s, j, i-1)) {  // Check if s[j:i] is valid
                dp[i] = Math.min(dp[i], dp[j] + 1);
            }
        }
    }
    
    return dp[n];
}
```

**Problems & Complexities:**
- **139. Word Break**: O(n²×m) time, O(n) space (m = avg word length)
- **140. Word Break II**: O(n³) time, O(n²) space
- **132. Palindrome Partitioning II**: O(n²) time, O(n²) space
- **1478. Allocate Mailboxes**: O(n³) time, O(n²) space

**Key Insights:**
- Try all possible ways to make the last cut
- Precompute validity checks when possible (e.g., palindrome table)
- Often combined with other DP patterns

---

### Pattern 9: Tree DP

**Recognition**: Tree structure with optimization on subtrees

**State**: `dp[node][state]` = optimal solution for subtree rooted at node in given state

**Flow**: Combine solutions from children based on tree structure

**Time Complexity**: O(n), **Space Complexity**: O(n)

```
Tree DP Flow:
       root
      /    \
   left    right
   /  \    /    \
  ...  ... ...  ...

dp[root] = f(dp[left], dp[right])
```

**Template:**
```java
public int treeDP(TreeNode root) {
    if (root == null) return 0;
    
    int[] result = dfs(root);
    return Math.max(result[0], result[1]);
}

private int[] dfs(TreeNode node) {
    if (node == null) return new int[]{0, 0};
    
    int[] left = dfs(node.left);
    int[] right = dfs(node.right);
    
    // State 0: not including current node
    int exclude = Math.max(left[0], left[1]) + Math.max(right[0], right[1]);
    
    // State 1: including current node  
    int include = node.val + left[0] + right[0];
    
    return new int[]{exclude, include};
}
```

**Problems & Complexities:**
- **337. House Robber III**: O(n) time, O(h) space
- **124. Binary Tree Maximum Path Sum**: O(n) time, O(h) space
- **543. Diameter of Binary Tree**: O(n) time, O(h) space
- **968. Binary Tree Cameras**: O(n) time, O(h) space

**Key Insights:**
- Process children before parent (post-order traversal)
- Often involves include/exclude decisions for nodes
- State typically represents different node conditions

---

### Pattern 10: Digit DP

**Recognition**: Count numbers with certain digit properties in a range

**State**: `dp[pos][tight][state]` = count of valid numbers

**Flow**: Build number digit by digit with constraints

**Time Complexity**: O(digits × states), **Space Complexity**: O(digits × states)

```
Digit DP Flow:
Number: 1234
Position: 0123

At each position, choose digit based on:
- Current position
- Tight constraint (bounded by limit)
- Problem-specific state
```

**Template:**
```java
public int digitDP(int n) {
    String num = String.valueOf(n);
    int[][][] memo = new int[num.length()][2][state_size];
    return solve(0, 1, initial_state, num, memo);
}

private int solve(int pos, int tight, int state, String num, int[][][] memo) {
    if (pos == num.length()) {
        return isValidState(state) ? 1 : 0;
    }
    
    if (memo[pos][tight][state] != -1) {
        return memo[pos][tight][state];
    }
    
    int limit = tight == 1 ? num.charAt(pos) - '0' : 9;
    int result = 0;
    
    for (int digit = 0; digit <= limit; digit++) {
        int newTight = (tight == 1 && digit == limit) ? 1 : 0;
        int newState = updateState(state, digit);
        result += solve(pos + 1, newTight, newState, num, memo);
    }
    
    return memo[pos][tight][state] = result;
}
```

**Problems & Complexities:**
- **233. Number of Digit One**: O(log n) time, O(log n) space
- **357. Count Numbers with Unique Digits**: O(1) time, O(1) space
- **902. Numbers At Most N Given Digit Set**: O(log n) time, O(log n) space

**Key Insights:**
- Build numbers digit by digit
- Maintain tight constraint for upper bound
- State tracks problem-specific properties

---

## Advanced DP Patterns

### Pattern 11: Bitmask DP

**Recognition**: Small set of elements (≤ 20), need to track subsets

**State**: `dp[mask]` where mask represents subset of elements

**Flow**: Iterate through all possible subsets using bit manipulation

**Time Complexity**: O(2ⁿ × n), **Space Complexity**: O(2ⁿ)

```
Bitmask Representation:
Set {A, B, C} → 3 bits
000 = {}
001 = {A}  
010 = {B}
011 = {A, B}
100 = {C}
...
111 = {A, B, C}

Recursion Pattern (TSP):
    tsp(mask, pos)
         |
    For each unvisited city i:
    tsp(mask | (1<<i), i) + cost[pos][i]
```

**Template:**
```java
public int bitmaskDP(int[][] cost) {
    int n = cost.length;
    int[] dp = new int[1 << n];
    Arrays.fill(dp, Integer.MAX_VALUE);
    dp[0] = 0;
    
    for (int mask = 0; mask < (1 << n); mask++) {
        if (dp[mask] == Integer.MAX_VALUE) continue;
        
        for (int i = 0; i < n; i++) {
            if ((mask & (1 << i)) == 0) {  // i not in current subset
                int newMask = mask | (1 << i);
                dp[newMask] = Math.min(dp[newMask], dp[mask] + cost[i]);
            }
        }
    }
    
    return dp[(1 << n) - 1];  // All elements selected
}

// TSP with position tracking
public int tsp(int[][] dist) {
    int n = dist.length;
    int[][] dp = new int[1 << n][n];
    
    for (int[] row : dp) Arrays.fill(row, Integer.MAX_VALUE);
    dp[1][0] = 0;  // Start at city 0
    
    for (int mask = 1; mask < (1 << n); mask++) {
        for (int u = 0; u < n; u++) {
            if ((mask & (1 << u)) == 0 || dp[mask][u] == Integer.MAX_VALUE) continue;
            
            for (int v = 0; v < n; v++) {
                if (mask & (1 << v)) continue;  // Already visited
                
                int newMask = mask | (1 << v);
                dp[newMask][v] = Math.min(dp[newMask][v], dp[mask][u] + dist[u][v]);
            }
        }
    }
    
    int result = Integer.MAX_VALUE;
    for (int i = 1; i < n; i++) {
        result = Math.min(result, dp[(1 << n) - 1][i] + dist[i][0]);
    }
    return result;
}
```

**Problems & Complexities:**
- **526. Beautiful Arrangement**: O(2ⁿ × n) time, O(2ⁿ) space
- **691. Stickers to Spell Word**: O(2ᵐ × n × m) time, O(2ᵐ) space
- **847. Shortest Path Visiting All Nodes**: O(2ⁿ × n) time, O(2ⁿ × n) space
- **1125. Smallest Sufficient Team**: O(2ᵐ × n) time, O(2ᵐ) space
- **1434. Number of Ways to Wear Different Hats**: O(2ⁿ × m) time, O(2ⁿ) space

**Simple Example (Assignment Problem)**:
```
3 people, 3 tasks with costs:
   T1 T2 T3
P1  1  2  3
P2  4  5  6  
P3  7  8  9

Bitmask states:
000 → nobody assigned
001 → P1 assigned to T1
010 → P1 assigned to T2
...
111 → all assigned optimally
```

**Key Insights:**
- Only feasible when n ≤ 20 due to exponential space
- Common in TSP, assignment problems, subset enumeration
- Use bitwise operations for efficient subset manipulation
- Often combined with other DP patterns

---

### Pattern 12: Probability DP

**Recognition**: Calculate probability/expected value with random events

**State**: `dp[i][j]` = probability of reaching state j after i steps

**Flow**: Sum probabilities of all ways to reach each state

**Time Complexity**: O(n × states), **Space Complexity**: O(n × states)

```
Probability Transition:
State A --p1--> State B
State A --p2--> State C
Where p1 + p2 = 1.0

dp[i+1][B] += dp[i][A] * p1
dp[i+1][C] += dp[i][A] * p2
```

**Template:**
```java
public double probabilityDP(int n, int k, int[] prob) {
    double[][] dp = new double[n + 1][k + 1];
    dp[0][0] = 1.0;  // Base case: 100% chance to start at state 0
    
    for (int i = 0; i < n; i++) {
        for (int j = 0; j <= k; j++) {
            if (dp[i][j] == 0) continue;
            
            // Transition to different states
            for (int next = 0; next <= k; next++) {
                double transitionProb = getTransitionProb(j, next);
                dp[i + 1][next] += dp[i][j] * transitionProb;
            }
        }
    }
    
    return dp[n][k];
}

// Knight Probability example
public double knightProbability(int n, int k, int row, int col) {
    double[][][] dp = new double[k + 1][n][n];
    dp[0][row][col] = 1.0;
    
    int[][] moves = {{-2,-1},{-2,1},{-1,-2},{-1,2},{1,-2},{1,2},{2,-1},{2,1}};
    
    for (int step = 0; step < k; step++) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (dp[step][i][j] == 0) continue;
                
                for (int[] move : moves) {
                    int ni = i + move[0], nj = j + move[1];
                    if (ni >= 0 && ni < n && nj >= 0 && nj < n) {
                        dp[step + 1][ni][nj] += dp[step][i][j] / 8.0;
                    }
                }
            }
        }
    }
    
    double result = 0.0;
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            result += dp[k][i][j];
        }
    }
    return result;
}
```

**Problems & Complexities:**
- **688. Knight Probability in Chessboard**: O(k × n²) time, O(n²) space
- **837. New 21 Game**: O(n + w) time, O(n) space
- **1230. Toss Strange Coins**: O(n × target) time, O(target) space

**Key Insights:**
- Always maintain probability constraints (sum = 1.0)
- Use double precision for accurate calculations
- Often space-optimizable using rolling arrays
- Base case typically has probability 1.0 for initial state

---

## LeetCode 150 DP Problems Coverage

### Easy (5 problems)
- **70. Climbing Stairs**: Linear DP - O(n) time, O(1) space
- **121. Best Time to Buy and Sell Stock**: State Machine DP - O(n) time, O(1) space
- **198. House Robber**: Linear DP - O(n) time, O(1) space
- **746. Min Cost Climbing Stairs**: Linear DP - O(n) time, O(1) space
- **392. Is Subsequence**: Two Pointers/String DP - O(n) time, O(1) space

### Medium (15 problems)
- **91. Decode Ways**: Linear DP - O(n) time, O(1) space
- **139. Word Break**: Partition DP - O(n²) time, O(n) space
- **152. Maximum Product Subarray**: Linear DP - O(n) time, O(1) space
- **213. House Robber II**: Linear DP - O(n) time, O(1) space
- **300. Longest Increasing Subsequence**: Subsequence DP - O(n log n) time, O(n) space
- **322. Coin Change**: Knapsack DP - O(n×amount) time, O(amount) space
- **416. Partition Equal Subset Sum**: Knapsack DP - O(n×sum) time, O(sum) space
- **518. Coin Change 2**: Knapsack DP - O(n×amount) time, O(amount) space
- **1143. Longest Common Subsequence**: String DP - O(mn) time, O(min(m,n)) space
- **1035. Uncrossed Lines**: String DP (same as LCS) - O(mn) time, O(min(m,n)) space
- **122. Best Time to Buy and Sell Stock II**: State Machine DP - O(n) time, O(1) space
- **309. Best Time to Buy and Sell Stock with Cooldown**: State Machine DP - O(n) time, O(1) space
- **714. Best Time to Buy and Sell Stock with Transaction Fee**: State Machine DP - O(n) time, O(1) space
- **62. Unique Paths**: Grid DP - O(mn) time, O(n) space
- **64. Minimum Path Sum**: Grid DP - O(mn) time, O(n) space

### Hard (5 problems)
- **72. Edit Distance**: String DP - O(mn) time, O(min(m,n)) space
- **123. Best Time to Buy and Sell Stock III**: State Machine DP - O(n) time, O(1) space
- **188. Best Time to Buy and Sell Stock IV**: State Machine DP - O(nk) time, O(k) space
- **132. Palindrome Partitioning II**: Interval DP - O(n²) time, O(n²) space
- **1312. Minimum Insertion Steps to Make a String Palindrome**: String DP - O(n²) time, O(n) space

---

## Time and Space Complexity Patterns

### Complexity Analysis by Pattern:
- **Linear DP**: O(n) time, O(1) space (after optimization)
- **Grid DP**: O(mn) time, O(min(m,n)) space (after optimization)
- **Knapsack DP**: O(n×W) time, O(W) space (after optimization)
- **Interval DP**: O(n³) time, O(n²) space
- **String DP**: O(mn) time, O(min(m,n)) space (after optimization)
- **Subsequence DP**: O(n²) time, O(n) space (O(n log n) with optimization)
- **Tree DP**: O(n) time, O(h) space (h = height)
- **Bitmask DP**: O(2ⁿ × n) time, O(2ⁿ) space
- **Probability DP**: O(n × states) time, O(states) space

### Space Optimization Techniques:
1. **Rolling Array**: Use current and previous row only
2. **State Compression**: Use variables instead of arrays
3. **Reverse Iteration**: Process in reverse to avoid conflicts

---

## Common Mistakes and Pro Tips

### Common Mistakes:
1. **Incorrect base cases** - Always handle edge cases first
2. **Wrong iteration order** - Bottom-up requires correct dependency order
3. **Integer overflow** - Use long for large computations
4. **Floating point precision** - Use proper epsilon for comparisons
5. **State definition confusion** - Be clear about what each state represents
6. **Missing memoization** - Don't forget to store computed results
7. **Incorrect space optimization** - Ensure dependencies are preserved

### Pro Tips:
1. **Start with brute force recursion** - Understand the problem structure
2. **Identify overlapping subproblems** - This confirms DP is applicable
3. **Define state clearly** - Write down what dp[i] represents
4. **Handle base cases first** - Edge cases are crucial for correctness
5. **Test with small examples** - Verify logic before implementing
6. **Consider space optimization** - Often reduces O(n²) to O(n)
7. **Use meaningful variable names** - Makes debugging easier
8. **Draw state transition diagrams** - Visual understanding helps
9. **Practice pattern recognition** - Most problems fit known patterns
10. **Implement both top-down and bottom-up** - Understand both approaches

---

## Template Selection Quick Reference

### Problem Analysis Checklist:
1. **What am I optimizing?** (max/min/count)
2. **What choices do I have at each step?**
3. **What information do I need to track?** (state definition)
4. **What are the constraints?** (helps identify pattern)
5. **Can I break it into smaller subproblems?**

### Template Selection:
- **Sequential decisions** → Linear DP Template
- **2D movement/paths** → Grid DP Template
- **Item selection with constraints** → Knapsack Template
- **Range/interval problems** → Interval DP Template
- **Multiple states with transitions** → State Machine Template
- **Two sequences comparison** → String DP Template
- **Subset enumeration (n≤20)** → Bitmask Template
- **Tree-based optimization** → Tree DP Template
- **Optimal partitioning** → Partition DP Template
- **Probability calculations** → Probability DP Template

### Final Success Strategy:
1. **Master the fundamentals** - Understand the 12 core patterns
2. **Practice regularly** - Solve 2-3 problems daily
3. **Time yourself** - Build interview-speed problem solving
4. **Review mistakes** - Learn from incorrect approaches
5. **Teach others** - Explaining concepts reinforces understanding
6. **Mock interviews** - Practice under pressure
7. **Pattern recognition** - 80% of interview problems fit known patterns

Remember: **Dynamic Programming is pattern recognition + practice**. Master these 12 patterns and you'll be able to solve any DP problem in technical interviews!
