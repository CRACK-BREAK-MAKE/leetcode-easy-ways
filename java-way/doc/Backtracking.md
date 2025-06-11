# Complete Backtracking Patterns Guide for LeetCode & Interviews

## What is Backtracking?

Backtracking is a systematic method to explore all possible solutions by incrementally building candidates and abandoning them ("backtracking") as soon as it's determined they cannot lead to a valid solution.

**Key Principle**: Try → Validate (but prune the search space early when you know a path won't work) → Recurse → Undo

## The Universal Backtracking Template

```java
void backtrack(parameters) {
    // Base case - found a complete solution
    if (isValidSolution(parameters)) {
        addToResult(parameters);
        return;
    }
    
    // Try all possible choices at current step
    for (choice in getAllPossibleChoices(parameters)) {
        // Make the choice
        makeChoice(choice, parameters);
        
        // Recurse with the choice made
        backtrack(modifiedParameters);
        
        // Undo the choice (backtrack)
        undoChoice(choice, parameters);
    }
}
```

## Key Components of Backtracking

### 1. **State Representation**
- What does your current partial solution look like?
- What parameters do you need to pass down?

### 2. **Choice Space**
- What are all the possible choices at each step?
- How do you generate these choices?

### 3. **Constraints/Pruning**
- When can you determine early that a path won't work?
- What makes a solution invalid?

### 4. **Base Case**
- When have you found a complete solution?
- When should you stop exploring?

---

## Pattern 1: Subset/Combination Generation (Choose or Skip)

**Definition**: Generate all possible combinations by deciding whether to include each element or not.

**When to use**: Generate all possible subsets, combinations with/without constraints

**Key Insight**: At each element, you have two choices - include it or skip it

```java
void generateSubsets(int[] nums, int start, List<Integer> current, List<List<Integer>> result) {
    // Base case: we have a complete subset
    result.add(new ArrayList<>(current));

    // Try including each remaining element
    for (int i = start; i < nums.length; i++) {
        current.add(nums[i]);           // Make choice
        generateSubsets(nums, i + 1, current, result);  // Recurse
        current.remove(current.size() - 1);  // Backtrack
    }
}
```

**Example**: Generate all subsets of [1,2,3]

**Decision Tree for [1,2,3]:**
```
                    []
                 /  |  \
              [1]   [2]  [3]
             / |     |
         [1,2] [1,3] [2,3]
          |
       [1,2,3]

Output: [], [1], [2], [3], [1,2], [1,3], [2,3], [1,2,3]
```

**Complexity**:
- Time: O(2^n × n) - 2^n subsets, O(n) to copy each
- Space: O(n) recursion depth

**LeetCode Problems**:
- `78`. Subsets ⭐
- `90`. Subsets II (with duplicates)
- `77`. Combinations
- `39`. Combination Sum ⭐
- `40`. Combination Sum II
- `216`. Combination Sum III

**Key Insights to Remember**:
- Use `start` index to avoid duplicates in combinations
- For problems with duplicate elements, sort first and skip duplicates: `if (i > start && nums[i] == nums[i-1]) continue;`
- For "sum" problems, pass remaining target and prune when target < 0

---

## Another version of Pattern 1: Enumeration/Generation (Choose or Skip)

**Definition**: Generate all possible combinations by deciding whether to include each element or not.

**Template**:
```java
void enumerate(int[] nums, int index, List<Integer> current, List<List<Integer>> result) {
    // Base case: processed all elements
    if (index == nums.length) {
        result.add(new ArrayList<>(current));
        return;
    }

    // Choice 1: Skip current element
    enumerate(nums, index + 1, current, result);

    // Choice 2: Include current element
    current.add(nums[index]);
    enumerate(nums, index + 1, current, result);
    current.remove(current.size() - 1);
}
```

**LeetCode Problems**:
- 78. Subsets ⭐
- 90. Subsets II (with duplicates)
- 77. Combinations
- 17. Letter Combinations of Phone Number ⭐

**Key Insights**:
- Two choices at each step: include or exclude
- Use index to avoid revisiting elements
- For duplicates: sort first, skip consecutive duplicates

**Complexity**: O(2^n × n) time, O(n) space

---

## Pattern 2: Permutation Generation (All Arrangements)

**When to use**: Generate all possible arrangements, ordering matters

**Key Insight**: Use a boolean array to track used elements, try all unused elements at each position

```java
void generatePermutations(int[] nums, boolean[] used, List<Integer> current, List<List<Integer>> result) {
    // Base case: permutation is complete
    if (current.size() == nums.length) {
        result.add(new ArrayList<>(current));
        return;
    }

    // Try each unused element
    for (int i = 0; i < nums.length; i++) {
        if (!used[i]) {
            current.add(nums[i]);       // Make choice
            used[i] = true;
            generatePermutations(nums, used, current, result);  // Recurse
            used[i] = false;            // Backtrack
            current.remove(current.size() - 1);
        }
    }
}
```

**Example**: Generate all permutations of [1,2,3]

**Decision Tree for [1,2,3]:**
```
                     []
              /      |      \
           [1]      [2]      [3]
          / |        | \      | \
      [1,2] [1,3]  [2,1] [2,3] [3,1] [3,2]
        |     |      |     |     |     |
    [1,2,3] [1,3,2] [2,1,3] [2,3,1] [3,1,2] [3,2,1]

Output: [1,2,3], [1,3,2], [2,1,3], [2,3,1], [3,1,2], [3,2,1]
```

**Complexity**:
- Time: O(n! × n) - n! permutations, O(n) to copy each
- Space: O(n) recursion depth + used array

**LeetCode Problems**:
- `46`. Permutations ⭐
- `47`. Permutations II (with duplicates)
- `31`. Next Permutation (iterative approach better)
- `60`. Permutation Sequence

**Key Insights to Remember**:
- Use boolean array to track used elements
- For duplicates: sort array, use `if (i > 0 && nums[i] == nums[i-1] && !used[i-1]) continue;`
- Time complexity: O(n! × n) - n! permutations, each takes O(n) to construct
- Space complexity: O(n) for recursion stack and used array

---

## Pattern 3: Grid/Board Exploration (2D Traversal)

**Definition**: Explore 2D grids with constraints, typically with 4/8-directional movement.

**When to use**: N-Queens, Sudoku, Word Search, pathfinding in grids

**Key Insight**: Mark visited cells, explore all directions, backtrack by unmarking

```
[['A','B'],
 ['C','D']]
```

```java
boolean wordSearch(char[][] board, String word, int row, int col, int index) {
    // Base case: found the word
    if (index == word.length()) return true;

    // Boundary check and character match
    if (row < 0 || row >= board.length || col < 0 || col >= board[0].length ||
            board[row][col] != word.charAt(index)) {
        return false;
    }

    // Make choice: mark cell as visited
    char temp = board[row][col];
    board[row][col] = '#';

    // Try all 4 directions
    boolean found = wordSearch(board, word, row+1, col, index+1) ||
            wordSearch(board, word, row-1, col, index+1) ||
            wordSearch(board, word, row, col+1, index+1) ||
            wordSearch(board, word, row, col-1, index+1);

    // Backtrack: restore cell
    board[row][col] = temp;

    return found;
}
```

**Example**: Word Search for "AB" in grid:

**Decision Tree for finding "AB":**
```
Start at (0,0) 'A'
    |
Mark (0,0) as visited
    |
Try 4 directions for 'B'
    ├── Down (1,0) 'C' ❌
    ├── Up (-1,0) ❌ out of bounds
    ├── Right (0,1) 'B' ✅ FOUND
    └── Left (0,-1) ❌ out of bounds

Result: true
```

**Complexity**:
- Time: O(4^(m×n)) worst case - 4 directions at each cell
- Space: O(word.length()) recursion depth

**LeetCode Problems**:
- `79`. Word Search ⭐
- `212`. Word Search II (use Trie for optimization)
- `51`. N-Queens ⭐
- `52`. N-Queens II
- `37`. Sudoku Solver ⭐
- `200`. Number of Islands (DFS/BFS better)

**Key Insights to Remember**:
- Always check boundaries first
- Use temporary marking for visited cells or maintain separate visited array
- For N-Queens: check diagonal conflicts using `row-col` and `row+col`
- Direction arrays: `int[][] dirs = {{0,1}, {1,0}, {0,-1}, {-1,0}};`

---

## Pattern 4: Partitioning/Splitting (Divide String/Array)

**Definition**: Split input into valid parts based on given criteria.

**When to use**: Split strings based on conditions, build expressions

**Key Insight**: Try all possible split points, validate each partition


```java
void partition(String s, int start, List<String> current, List<List<String>> result) {
    if (start == s.length()) {
        result.add(new ArrayList<>(current));
        return;
    }

    // Try all possible end points for current partition
    for (int end = start; end < s.length(); end++) {
        String substring = s.substring(start, end + 1);
        if (isPalindrome(substring)) { // Check if partition is valid
            current.add(substring); // Make choice
            partition(s, end + 1, current, result); // Recurse
            current.remove(current.size() - 1); // Backtrack
        }
    }
}
```

**Example**: Palindrome partitioning of "aba"

**Decision Tree for "aba":**
```
                  ""
                 /|\
              "a" "ab" "aba"
              /    |     |
           "b","a" ❌   ✅["aba"]
           /   \
        "ba"   "a"
         |      |
        ❌     ✅["a","b","a"]

Output: [["aba"], ["a","b","a"]]
```

**Complexity**:
- Time: O(2^n × n) - 2^n partitions, O(n) to check palindrome
- Space: O(n) recursion depth


**LeetCode Problems**:
- `131`. Palindrome Partitioning ⭐
- `93`. Restore IP Addresses ⭐
- `132`. Palindrome Partitioning II (DP better)
- `139`. Word Break (DP better)
- `140`. Word Break II
- `282`. Expression Add Operators

**Key Insights to Remember**:
- Precompute validity when possible (e.g., palindrome table)
- For expression problems, handle operator precedence carefully
- Consider using memoization for overlapping subproblems

---

## Pattern 5: Constraint Building (Build Valid Structures)

**Definition**: Incrementally build solutions while maintaining constraints at each step.

**When to use**: Generate valid structures with specific rules

**Key Insight**: Build incrementally while checking constraints at each step


```java
void buildValid(StringBuilder current, int openCount, int closeCount, int n, List<String> result) {
    // Base case: structure complete
    if (current.length() == 2 * n) {
        result.add(current.toString());
        return;
    }

    // Add opening if allowed
    if (openCount < n) {
        current.append('(');
        buildValid(current, openCount + 1, closeCount, n, result);
        current.deleteCharAt(current.length() - 1);
    }

    // Add closing if valid
    if (closeCount < openCount) {
        current.append(')');
        buildValid(current, openCount, closeCount + 1, n, result);
        current.deleteCharAt(current.length() - 1);
    }
}
```

**Example**: Generate valid parentheses for n=2

**Decision Tree for n=2:**
```
                   ""
                   |
                  "("
               /       \
           "(("        "()"
            |         /   \
        "(()"     "()("  "())"
            |        |      |
        "(())"   "()()" ✅  ✅
            |
           ✅

Output: ["(())", "()()"]
```

**Complexity**:
- Time: O(4^n / √n) - Catalan number
- Space: O(n) recursion depth

**LeetCode Problems**:
- `22`. Generate Parentheses ⭐
- `301`. Remove Invalid Parentheses (BFS better)

**Key Insights to Remember**:
- Maintain invariants throughout construction
- Prune early when constraints are violated
- Only make moves that keep solution valid
- Check constraints before each choice

---

## Pattern 6: Target/Path Finding (Reach Goal)

**Definition**: Find combinations/paths that sum to or reach a specific target.

**When to use**: Find paths/ways to reach a target value or state

**Key Insight**: Try all possible moves/operations, backtrack when target exceeded or impossible

```java
void findPaths(int[] candidates, int target, int start, List<Integer> current,
        List<List<Integer>> result) {
    // Base case: found target
    if (target == 0) {
        result.add(new ArrayList<>(current));
        return;
    }

    // Pruning: if target negative, no solution possible
    if (target < 0) return;

    // Try each candidate from start position
    for (int i = start; i < candidates.length; i++) {
        current.add(candidates[i]);
        // For unlimited use: pass i, for single use: pass i+1
        findPaths(candidates, target - candidates[i], i, current, result);
        current.remove(current.size() - 1);
    }
}
```

**Example**: Combination sum for target=4 with [1,2,3]

**Decision Tree for target=4, [1,2,3]:**
```
                target=4
              /    |    \
           +1     +2     +3
        target=3 target=2 target=1
        /  |  \    |  \     |
      +1  +2  +3  +2  +3   +3
    t=2  t=1  t=0 t=0 t=-1  t=-2
   / |   |    ✅   ✅   ❌    ❌
  +1 +2  +3
 t=1 t=0 t=-1
  |  ✅   ❌
 +1
t=0
✅

Output: [[1,1,1,1], [1,1,2], [2,2], [1,3]]
```

**Complexity**:
- Time: O(2^target) worst case
- Space: O(target) recursion depth

**LeetCode Problems**:
- `39`. Combination Sum ⭐
- `40`. Combination Sum II
- `216`. Combination Sum III
- `377`. Combination Sum IV (DP better)
- `494`. Target Sum (DP better for counting)

**Key Insights to Remember**:
- Sort array for better pruning
- For unlimited reuse: don't increment start index
- For single use: increment start index
- Early termination when target becomes negative

---

## Pattern 7: Graph Pathfinding/Cycle Detection/Path Enumeration (All Paths/Routes)

**Definition**: Find all possible paths in graphs, typically DAGs.

**When to use**: Find all paths between nodes, detect cycles

**Key Insight**: Use visited array, explore all neighbors, backtrack visited status


```java
void findAllPaths(int[][] graph, int node, int target, List<Integer> path,
        List<List<Integer>> result, boolean[] visited) {
    // Add current node to path
    path.add(node);
    visited[node] = true;

    // Base case: reached target
    if (node == target) {
        result.add(new ArrayList<>(path));
    } else {
        // Try all unvisited neighbors
        for (int neighbor : graph[node]) {
            if (!visited[neighbor]) {
                findAllPaths(graph, neighbor, target, path, result, visited);
            }
        }
    }

    // Backtrack
    path.remove(path.size() - 1);
    visited[node] = false;
}
```

**Example**: All paths from 0 to 2 in graph [[1,2],[3],[3],[]]

**Decision Tree for graph [[1,2],[3],[3],[]] from 0 to 3:**
```
                    0
                 /     \
                1       2
                |       |
                3 ✅    3 ✅
              [0,1,3] [0,2,3]

Output: [[0,1,3], [0,2,3]]
```

**Complexity**:
- Time: O(2^n × n) - exponential paths in worst case
- Space: O(n) recursion depth

**LeetCode Problems**:
- `797`. All Paths From Source to Target ⭐
- `332`. Reconstruct Itinerary
- `127`. Word Ladder (BFS better)
- `126`. Word Ladder II

**Key Insights to Remember**:
- For DAG (Directed Acyclic Graph): no need to track visited
- For general graphs: track visited to avoid cycles
- For finding shortest paths: BFS is better than backtracking

---

## Pattern 8: Expression Construction (Build Mathematical/Logical Expressions)

**Definition**: Build valid expressions by inserting operators or making structural decisions.

**Template**:
```java
void buildExpression(String num, int index, long currentNum, long prevNum,
        int target, String expression, List<String> result) {
    // Base case: processed all digits
    if (index == num.length()) {
        if (currentNum == target) {
            result.add(expression);
        }
        return;
    }

    // Try different ways to use current digit
    for (int i = index; i < num.length(); i++) {
        String part = num.substring(index, i + 1);
        long partValue = Long.parseLong(part);

        if (index == 0) {
            // First number, no operator needed
            buildExpression(num, i + 1, partValue, partValue, target, part, result);
        } else {
            // Try +, -, *
            buildExpression(num, i + 1, currentNum + partValue, partValue,
                    target, expression + "+" + part, result);
            buildExpression(num, i + 1, currentNum - partValue, -partValue,
                    target, expression + "-" + part, result);
            buildExpression(num, i + 1, currentNum - prevNum + prevNum * partValue,
                    prevNum * partValue, target, expression + "*" + part, result);
        }

        // Avoid leading zeros
        if (part.equals("0")) break;
    }
}
```

**LeetCode Problems**:
- 282. Expression Add Operators
- 241. Different Ways to Add Parentheses (Divide & Conquer better)

**Key Insights**:
- Handle operator precedence carefully
- Avoid numbers with leading zeros
- Track previous operand for multiplication handling

**Complexity**: O(4^n) time, O(n) space

---

# Complete Problem Classification

## LeetCode 150 Backtracking Problems:
⭐ **Core Problems (Must Master)**:
- 17. Letter Combinations of Phone Number (Pattern 1)
- 22. Generate Parentheses (Pattern 4)
- 39. Combination Sum (Pattern 6)
- 46. Permutations (Pattern 2)
- 51. N-Queens (Pattern 5)
- 78. Subsets (Pattern 1)
- 79. Word Search (Pattern 5)
- 93. Restore IP Addresses (Pattern 3)
- 131. Palindrome Partitioning (Pattern 3)

## High-Frequency FAANG Interview Problems:
- 37. Sudoku Solver (Pattern 5)
- 40. Combination Sum II (Pattern 6)
- 47. Permutations II (Pattern 2)
- 77. Combinations (Pattern 1)
- 90. Subsets II (Pattern 1)
- 140. Word Break II (Pattern 3)
- 212. Word Search II (Pattern 5)
- 216. Combination Sum III (Pattern 6)
- 282. Expression Add Operators (Pattern 8)
- 332. Reconstruct Itinerary (Pattern 7)
- 797. All Paths From Source to Target (Pattern 7)

---

# Pattern Recognition Guide

## How to Identify Which Pattern to Use:

| **Problem Characteristics** | **Pattern** | **Key Indicators** |
|---------------------------|-------------|-------------------|
| "All subsets/combinations" | **Pattern 1** | Choose/skip elements, order doesn't matter |
| "All permutations/arrangements" | **Pattern 2** | Order matters, try all positions |
| "Split/partition string" | **Pattern 3** | Find valid ways to break input |
| "Generate valid structures" | **Pattern 4** | Build while maintaining constraints |
| "Grid/board problems" | **Pattern 5** | 2D traversal, word search, N-Queens |
| "Sum to target" | **Pattern 6** | Find combinations reaching goal |
| "All paths in graph" | **Pattern 7** | Graph traversal, route finding |
| "Build expressions" | **Pattern 8** | Insert operators, construct equations |

---

## When to Use Backtracking?

### Problem Indicators:
1. **"Find all..."** - All combinations, permutations, paths
2. **"Generate all..."** - All valid constructions
3. **"Count ways to..."** - Number of solutions (though DP might be better)
4. **Constraint satisfaction** - Sudoku, N-Queens
5. **Decision problems** - "Can we achieve X?"

### How to Identify the Pattern:
1. **Multiple choices at each step**: At each decision point, you have several options
2. **Constraint satisfaction**: Some choices lead to invalid states
3. **Exhaustive search needed**: You need to explore all possibilities
4. **Recursive structure**: Problem can be broken into smaller subproblems

---

## Optimization Techniques

### 1. **Early Pruning**
```java
// Prune when remaining sum is impossible
if (remaining < 0) return;

// Prune when remaining choices insufficient
if (current.size() + (nums.length - start) < k) return;
```

### 2. **Sorting for Better Pruning**
```java
Arrays.sort(candidates); // Ascending order
for (int i = start; i < candidates.length; i++) {
    if (candidates[i] > remaining) break; // All future elements will be larger
}
```

### 3. **Memoization (Backtracking + DP)**
```java
Map<String, List<String>> memo = new HashMap<>();

List<String> backtrack(String s, Set<String> wordDict) {
    if (memo.containsKey(s)) return memo.get(s);
    // ... backtracking logic ...
    memo.put(s, result);
    return result;
}
```

### 4. **State Compression**
```java
// Instead of copying arrays, use bit manipulation
int state = 0;
state |= (1 << i);  // Set bit i
state &= ~(1 << i); // Clear bit i
```

---

# Master Strategy: 5-Step Problem-Solving Approach

## Step 1: **Pattern Recognition**
- Read problem carefully
- Identify what needs to be generated/found
- Match to one of the 8 patterns

## Step 2: **Define State Space**
- What parameters represent current state?
- What choices are available at each step?
- What's the base case?

## Step 3: **Identify Constraints**
- What makes a solution invalid?
- When can we prune early?
- What boundaries need checking?

## Step 4: **Choose Template**
- Select appropriate pattern template
- Modify for problem-specific needs
- Add pruning and optimizations

## Step 5: **Test & Debug**
- Start with small inputs
- Trace execution step by step
- Verify backtracking restores state correctly

---

# Complexity Analysis Summary

| **Pattern** | **Time Complexity** | **Space Complexity** | **Explanation** |
|-------------|-------------------|-------------------|-----------------|
| **Enumeration** | O(2^n × n) | O(n) | 2^n subsets, O(n) to copy each |
| **Permutation** | O(n! × n) | O(n) | n! arrangements, O(n) to build each |
| **Partitioning** | O(2^n × n) | O(n) | 2^n ways to split, O(n) validation |
| **Constraint Building** | O(4^n / √n) | O(n) | Catalan numbers for valid structures |
| **Grid Search** | O(4^(m×n)) | O(L) | 4 directions, L = word length |
| **Target Sum** | O(2^target) | O(target) | Exponential in target value |
| **Graph Paths** | O(2^N × N) | O(N) | Exponential paths in worst case |
| **Expression** | O(4^n) | O(n) | 4 choices per position |


### Space Complexity:
- **Recursion stack**: O(depth) - typically O(n) for most problems
- **Current path storage**: O(depth)
- **Result storage**: Depends on number of solutions

---

## Common Pitfalls & Debugging Tips

### Common Issues:
1. **Infinite recursion**: Base case not reachable
2. **Wrong backtracking**: Not restoring state properly
3. **Reference vs Value**: Adding references instead of copies to result
4. **Index errors**: Off-by-one in loops or recursion parameters

### Debugging Strategies:
1. **Start with small inputs**: Test with n=1,2,3
2. **Add print statements**: Show current state at each step
3. **Verify base cases**: Ensure they handle edge cases
4. **Check backtracking**: Make sure all changes are undone
5. **Trace execution**: Walk through algorithm step by step

### Template Checklist:
- [ ] Base case correctly identifies complete solution
- [ ] All choices at each step are covered
- [ ] State changes are made before recursion
- [ ] State changes are undone after recursion
- [ ] Pruning conditions are correct
- [ ] Result is stored correctly (deep copy when needed)

---

## Problem-Solving Strategy

1. **Identify the pattern** - Which of the 7 patterns does this match?
2. **Define the state** - What parameters need to be passed?
3. **Identify choices** - What options exist at each step?
4. **Define constraints** - When can we prune?
5. **Write base case** - When do we have a complete solution?
6. **Code the template** - Follow the universal structure
7. **Add optimizations** - Pruning, sorting, memoization
8. **Test thoroughly** - Start with simple cases

This comprehensive guide covers all major backtracking patterns you'll encounter in LeetCode 150 and technical interviews. Master these patterns, and you'll be able to tackle any backtracking problem with confidence!
