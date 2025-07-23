package com.crack.snap.make.easy.dp;

/**
 * Problem: You are climbing a staircase. It takes n steps to reach the top.
 * Each time you can either climb 1 or 2 steps. In how many distinct ways can you climb to the top?
 *
 * 1. Why "Climbing Stairs" is a DP Problem (The Four-Question Test)
 *   The problem asks for the number of distinct ways to climb to the top of n stairs if you can climb either 1 or 2 steps at a time.
 *
 *   This perfectly fits the criteria for Dynamic Programming:
 *   Can I make a choice? Yes, at each step, you can choose to climb either 1 stair or 2 stairs.
 *   Does this choice affect future choices? Yes, your choice of 1 or 2 steps directly impacts how many stairs remain to climb.
 *   Are there optimal subproblems? Yes, the total number of ways to reach n stairs is the sum of ways to reach n-1 stairs (then take 1 step) and ways to reach n-2 stairs (then take 2 steps). The solution for n is built from optimal solutions to smaller subproblems (n-1 and n-2).
 *   Are subproblems overlapping? Yes. For example, to find ways to reach stair 5, you need ways to reach stair 4 and stair 3. To find ways to reach stair 4, you need ways to reach stair 3 and stair 2. Notice that "ways to reach stair 3" is calculated twice, indicating overlapping subproblems.
 *   Since all four questions are "Yes", and keywords like "count ways" are present, this is definitely a DP problem.
 *
 * 2. Drafting the Solution (The Four-Step DP Solution Approach)
 * - Define the state: Let dp[i] represent the number of distinct ways to climb to the i-th stair. Our goal is to find dp[n].
 * - Find the recurrence relation: To reach stair i, you must have come from either stair i-1 (by taking one step) or
 *      stair i-2 (by taking two steps). The total ways to reach i is the sum of the ways to reach these previous stairs.
 *      $$ dp[i] = dp[i-1] + dp[i-2] $$
 * - Initialize base cases:
 *      dp[1] = 1 (Only one way to reach the first stair: one 1-step jump).
 *      dp[2] = 2 (Two ways to reach the second stair: 1+1 steps or one 2-step jump).
 * - Determine the order: We can solve this problem using both Top-Down (Memoization) or Bottom-Up (Tabulation) strategies.
 *      Since dp[i] depends on dp[i-1] and dp[i-2], we can process in increasing order of i for bottom-up, or
 *      use recursion with memoization for top-down.
 *
 * @author Mohan Sharma
 */
public class ClimbingStairs {

    /**
     * We start from n and recursively break it down. We use a memo array to store the result of climbStairs(i) so
     * we don't recompute it.
     *
     * Time Complexity: O(n). Each subproblem from 1 to n is solved only once.
     * Space Complexity: O(n). For the recursion stack and the memo array.
     */
    public int climbStairsTopDown(int n) {
        if (n <= 2) {
            return n; // Base cases: 1 way for 1 step, 2 ways for 2 steps
        }
        return climbStairsMemoized(n, new int[n + 1]);
    }

    private int climbStairsMemoized(int n, int[] memo) {
        if (n <= 2) {
            return n; // Base cases: 1 way for 1 step, 2 ways for 2 steps
        }
        if (memo[n] != 0) {
            return memo[n]; // Return cached result
        }
        memo[n] = climbStairsMemoized(n - 1, memo) + climbStairsMemoized(n - 2, memo);
        return memo[n];
    }

    /**
     * We build the solution iteratively from the base cases up to n.
     * This avoids recursion and uses a simple array to store intermediate results.
     *
     * Time Complexity: O(n). Each step from 1 to n is computed once.
     * Space Complexity: O(n). For the dp array.
     */
    public int climbStairsBottomUp(int n) {
        if (n <= 2) {
            return n; // Base cases: 1 way for 1 step, 2 ways for 2 steps
        }
        var dp = new int[n + 1];
        dp[1] = 1; // 1 way to climb 1 step
        dp[2] = 2; // 2 ways to climb 2 steps

        for (var i = 3; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2]; // Recurrence relation
        }

        return dp[n]; // Return the number of ways to climb n steps
    }

    public  int climbStairsBottomUpSpaceOptimized(int n) {
        if (n <= 2) {
            return n; // Base cases: 1 way for 1 step, 2 ways for 2 steps
        }
        var prevPrev = 1; // Ways to climb 2 steps
        var prev = 2; // Ways to climb 1 step

        for (var i = 3; i <= n; i++) {
            var current = prev + prevPrev; // Current ways to climb i steps
            prevPrev = prev; // Move prev1 to prev2
            prev = current; // Move prev2 to current
        }
        return prev;
    }

    public static void main(String[] args) {
        var obj = new ClimbingStairs();
        System.out.println("Top-Down (Memoization) Result for 5 steps: " + obj.climbStairsTopDown(5)); // Output: 8
        System.out.println("Bottom-Up (Tabulation) Result for 5 steps: " + obj.climbStairsBottomUp(5)); // Output: 8
        System.out.println("Bottom-Up (Space Optimized) Result for 5 steps: " + obj.climbStairsBottomUpSpaceOptimized(5)); // Output: 8
    }
}
