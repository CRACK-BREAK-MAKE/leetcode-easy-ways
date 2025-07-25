# DP vs Backtracking: Pattern Recognition Guide

## 🎯 The Simple Mental Framework

Think of it this way:
- **Backtracking** = "Try everything, keep what works"
- **Dynamic Programming** = "Count/optimize something while avoiding repeated work"

## 📝 The Ultimate Question Set

### Primary Questions (Answer these first):

1. **What does the problem ask for?**
    - 🔢 **Count** something → Likely DP
    - 🎯 **Find optimal value** (min/max) → Likely DP
    - 📋 **Find all solutions** → Likely Backtracking
    - ✅ **Find one valid solution** → Likely Backtracking

2. **Do I care about the actual solution path?**
    - 🚫 **No, just the count/value** → DP
    - ✅ **Yes, need actual arrangements** → Backtracking

3. **Are there repeated subproblems?**
    - ✅ **Yes** → DP (memoization will help)
    - 🚫 **No** → Backtracking

## 🔍 Pattern Recognition by Problem Type

### 🎲 DP Patterns (What to Look For):

**Keywords that scream DP:**
- "How many ways..."
- "Maximum/minimum..."
- "Is it possible..." (boolean)
- "What's the optimal..."

**Common DP Scenarios:**
- 🏃 **Path counting** (grid, stairs)
- 🎒 **Optimization** (knapsack, coin change)
- 🔤 **String matching** (edit distance, subsequences)
- 🔢 **Sequence problems** (longest increasing subsequence)

### 🌳 Backtracking Patterns (What to Look For):

**Keywords that scream Backtracking:**
- "Generate all..."
- "Find all combinations..."
- "Print all solutions..."
- "List all permutations..."

**Common Backtracking Scenarios:**
- 🧩 **Puzzle solving** (N-Queens, Sudoku)
- 🔀 **Permutations/Combinations**
- 🎯 **Constraint satisfaction**
- 🗺️ **Path finding** (when you need actual paths)

## 📚 Examples with Analysis

### Example 1: Climbing Stairs
**Problem:** "How many ways to climb n stairs if you can take 1 or 2 steps?"

**Analysis:**
- ❓ What's asked? → Count ways
- 🔢 Need actual paths? → No, just count
- 🔄 Repeated subproblems? → Yes, ways(n-1) + ways(n-2)
- **Verdict: DP**

### Example 2: Generate Parentheses
**Problem:** "Generate all valid combinations of n pairs of parentheses"

**Analysis:**
- ❓ What's asked? → Generate ALL combinations
- 📋 Need actual solutions? → Yes, all valid strings
- 🔄 Repeated subproblems? → No, each combination is unique
- **Verdict: Backtracking**

### Example 3: Coin Change
**Problem:** "Find minimum coins needed to make amount X"

**Analysis:**
- ❓ What's asked? → Find minimum (optimization)
- 🔢 Need actual coins? → No, just minimum count
- 🔄 Repeated subproblems? → Yes, min_coins(amount-coin)
- **Verdict: DP**

### Example 4: N-Queens
**Problem:** "Place N queens on NxN board such that no two attack each other"

**Analysis:**
- ❓ What's asked? → Find valid placement
- 🎯 Need actual arrangement? → Yes, queen positions
- 🔄 Repeated subproblems? → No, each placement is unique
- **Verdict: Backtracking**

## 🧠 Memory Tricks

### The "CAMO" Test for DP:
- **C**ount something
- **A**sk for optimal value
- **M**emoization would help (repeated subproblems)
- **O**verlapping subproblems exist

### The "GATE" Test for Backtracking:
- **G**enerate all solutions
- **A**rrange/place items with constraints
- **T**ry all possibilities
- **E**xplore and backtrack when stuck

## 🎯 Quick Decision Tree

```
Is the problem asking for:
├── Count/Optimization?
│   ├── Are there overlapping subproblems? → YES → DP
│   └── No overlapping subproblems → Greedy/Other
└── All solutions/One valid solution?
    ├── Need to explore all possibilities? → YES → Backtracking
    └── Can be solved greedily → Greedy/Other
```

## 🚨 Common Traps to Avoid

### When DP looks like Backtracking:
- **Subset Sum** (check if sum possible) → DP (optimization)
- **Path in Grid** (count paths) → DP (counting)

### When Backtracking looks like DP:
- **Generate all subsets** → Backtracking (need all solutions)
- **Find all paths** → Backtracking (need actual paths)

## 💡 Pro Tips

1. **Start with the question type** - This is your biggest clue
2. **Check if you need the actual solution** - Backtracking usually does
3. **Look for optimization keywords** - min, max, optimal → DP
4. **Count keywords** - "how many ways" → DP
5. **Generation keywords** - "all", "generate", "list" → Backtracking

## 🔥 Final Mental Model

**Think of it as:**
- **DP = Smart Calculator** (counts/optimizes efficiently)
- **Backtracking = Systematic Explorer** (finds all solutions)

When you see a problem, ask: "Am I calculating something smart or exploring everything?"
