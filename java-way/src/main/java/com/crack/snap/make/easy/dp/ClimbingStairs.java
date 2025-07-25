package com.crack.snap.make.easy.dp;

/**
 * Problem: You are climbing a staircase. It takes n steps to reach the top.
 * Each time you can either climb 1 or 2 steps. In how many distinct ways can you climb to the top?
 * Constraints:
 * 1 <= n <= 45
 *
 * @author Mohan Sharma
 */
public class ClimbingStairs {


    /**
     * Intuition:
     * Think about the problem like this:
     * How many ways can we reach the top (step n)?
     * The final move must be either a 1-step jump or a 2-step jump.
     * So, the total ways to reach step n is (Ways to reach step n-1) + (Ways to reach step n-2).
     * This gives us a very clean recurrence relation: ways(n) = ways(n-1) + ways(n-2).
     *
     * Now, what are the base cases?
     * ways(1): To reach step 1, there's only one way: (1). So ways(1) = 1.
     * ways(2): To reach step 2, there are two ways: (1, 1) and (2). So ways(2) = 2.
     *
     * Time Complexity: O(2 ^ n)
     * This is because for each step n, we make two recursive calls. This creates a recursion tree where the number of nodes
     * roughly doubles with each level, leading to exponential growth. For example, to calculate climb(5),
     * we calculate climb(3) twice, climb(2) three times, etc.
     *
     * Space Complexity: O(n), This is due to the maximum depth of the recursion stack, which can go up to n.
     */
    public int climbStairsBruteForce(int n) {
        if (n <= 2) {
            return n;
        }
        return climbStairsBacktracking(n);
    }

    private int climbStairsBacktracking(int n) {
        if (n == 1) {
            return 1;
        }
        if (n == 2) {
            return 2;
        }
        // The number of ways is the sum of ways to get to the previous two steps.
        return climbStairsBacktracking(n - 1) + climbStairsBacktracking(n - 2);
    }

    /**
     * Time Complexity: O(n). Each subproblem from 1 to n is computed exactly once. All subsequent calls for that same number
     * will be an O(1) lookup in the memo array.
     * Space Complexity: O(n). We need O(n) space for the memo array and another O(n) for the recursion stack in the worst case. So, it's O(n).
     */
    public int climbStairsTopDown(int n) {
        if (n <= 2) {
            return n;
        }
        return climbStairsMemoization(n, new Integer[n + 1]);
    }

    private int climbStairsMemoization(int n, Integer[] memo) {
        if (n == 1) {
            return 1;
        }
        if (n == 2) {
            return 2;
        }
        if (memo[n] != null) {
            return memo[n];
        }
        // The number of ways is the sum of ways to get to the previous two steps.
        return memo[n] = climbStairsMemoization(n - 1, memo) + climbStairsMemoization(n - 2, memo);
    }

    /**
     * Time Complexity: O(n). We iterate through a loop from 3 to n once.
     * Space Complexity: O(n). We use an array of size n+1 to store our DP results.
     */
    public int climbStairsBottomUp(int n) {
        if (n <= 2) {
            return n;
        }
        var dp = new int[n + 1];
        dp[1] = 1; // base case there is one way to reach 0 from 1
        dp[2] = 2; // there is 2 ways to reach 0 from 2
        for (int i = 3; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        return dp[n];
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
        System.out.println("Brute Force Result for 5 steps: " + obj.climbStairsBruteForce(5));
        System.out.println("Top-Down (Memoization) Result for 5 steps: " + obj.climbStairsTopDown(5)); // Output: 8
        System.out.println("Bottom-Up (Tabulation) Result for 5 steps: " + obj.climbStairsBottomUp(5)); // Output: 8
        System.out.println("Bottom-Up (Space Optimized) Result for 5 steps: " + obj.climbStairsBottomUpSpaceOptimized(5)); // Output: 8
    }
}
