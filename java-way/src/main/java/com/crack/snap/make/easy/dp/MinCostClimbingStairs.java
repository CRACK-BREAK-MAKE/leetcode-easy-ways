package com.crack.snap.make.easy.dp;

/**
 *
 * This is a Pattern 1: Linear DP problem because the cost to reach any step i is determined solely by the costs of
 * reaching the two preceding steps, i-1 and i-2.
 *
 * The Core Intuition:
 * The rule "you can start from step 0 or 1" is like a free bus ticket that drops you off right on step 0 or right on step 1.
 * You haven't paid anything yet because you haven't tried to leave. The cost is incurred when you take your next step from there.
 * If you start at step 0, you are standing on it for free. To move to step 1 or 2, you must pay cost[0].
 * If you start at step 1, you are standing on it for free. To move to step 2 or 3, you must pay cost[1].
 * So, the previous min cost to reach your starting position is $0.
 *  * dp[0] = 0
 *  * dp[1] = 0
 *
 * State: Let's define our state dp[i] as the minimum cost to reach step i. The "top floor" is conceptually at index n,
 * where n is the number of steps.
 *
 * Choice: To reach step i, you must have come from either step i-1 or step i-2.
 * Cost Calculation:
 * The cost to get to i from i-1 is: (min cost to reach i-1) + (cost of leaving i-1).
 * This translates to: dp[i-1] + cost[i-1].
 *
 * The cost to get to i from i-2 is: (min cost to reach i-2) + (cost of leaving i-2).
 * This translates to: dp[i-2] + cost[i-2].
 *
 * Recurrence Relation: We want the minimum cost, so we take the minimum of the two paths.
 * dp[i] = min(dp[i−1] + cost[i−1], dp[i−2] + cost[i−2])
 *
 * Recursive Flow Diagram
 * Let's trace solve(3) for cost = [10, 15, 20]. n=3, so we want the cost to reach the top floor (index 3).
 *
 *                            solve(3)
 *                         /          \
 *                        /            \
 * {(cost to reach 2) + cost[2]}     {(cost to reach 1) + cost[1]}
 *                    /                   \
 *                 solve(2) + 20        0 + 15   = 15
 *                /              \
 *             /                   \
 * {(cost to reach 1) + cost[1]} {(cost to reach 0) + cost[0]}
 *          /                            \
 * solve(1) + 15                     solve(0) + 10
 *      /                                   \
 * 0 + 15 = 15                             0 + 10 = 10
 *
 * Notice solve(1) is needed by both solve(3) and solve(2). This is the overlapping subproblem that DP solves efficiently.
 * @author Mohan Sharma
 */
public class MinCostClimbingStairs {


    /**
     * Time Complexity: O(2^n) - Each call can branch into two more, creating an exponential number of calls in the recursion tree.
     * Space Complexity: O(n) - Due to the maximum depth of the recursion stack.
     */
    public int minCostClimbingStairsBruteForce(int[] cost) {
        if (cost == null || cost.length == 0) {
            return 0;
        }
        return solveMinCostClimbingStairsBruteForce(cost, cost.length);
    }

    private int solveMinCostClimbingStairsBruteForce(int[] cost, int n) {
        if (n <= 1) {
            // As per question since we can start from 0 or 1, the previous min cost at 0 or 1 is always 0
            return 0;
        }
        // The cost to get to i from i-1 is: (min cost to reach i-1) + (cost of leaving i-1).
        var oneStepCost = solveMinCostClimbingStairsBruteForce(cost, n - 1) + cost[n - 1];
        // The cost to get to i from i-2 is: (min cost to reach i-2) + (cost of leaving i-2).
        var twoStepCost = solveMinCostClimbingStairsBruteForce(cost, n - 2) + cost[n - 2];
        return Math.min(oneStepCost, twoStepCost);
    }

    /**
     * Time Complexity: O(n) - Each state solve(i) is computed once.
     * Space Complexity: O(n) - For the recursion stack and memo array.
     */
    public int minCostClimbingStairsTopDown(int[] cost) {
        if (cost == null || cost.length == 0) {
            return 0;
        }
        return minCostMemoized(cost, cost.length, new Integer[cost.length + 1]);
    }

    private int minCostMemoized(int[] cost, int n, Integer[] memo) {
        if (n <= 1) {
            // As per question since we can start from 0 or 1, the previous min cost at 0 or 1 is always 0
            return 0;
        }
        if (memo[n] != null) {
            return memo[n];
        }
        // The cost to get to i from i-1 is: (min cost to reach i-1) + (cost of leaving i-1).
        var oneStepCost = minCostMemoized(cost, n - 1, memo) + cost[n - 1];
        // The cost to get to i from i-2 is: (min cost to reach i-2) + (cost of leaving i-2).
        var twoStepCost = minCostMemoized(cost, n - 2, memo) + cost[n - 2];
        return memo[n] = Math.min(oneStepCost, twoStepCost);
    }

    /**
     * Time Complexity: O(n) - A single loop.
     * Space Complexity: O(n) - For the dp array.
     */
    public int minCostClimbingStairsBottomUp(int[] cost) {
        var n = cost.length;
        if (n <= 1) {
            return 0; // No cost if we are below the first step
        }
        var dp = new int[n + 1];
        // default value of int array is 0, so we don't need to do dp[0] = 0 or dp[1] = 0
        for (var i = 2; i <= n; i++) {
            // The cost to get to i from i-1 is: (min cost to reach i-1) + (cost of leaving i-1).
            var oneStepCost = dp[i - 1] + cost[i - 1];
            // The cost to get to i from i-2 is: (min cost to reach i-2) + (cost of leaving i-2).
            var twoStepCost = dp[i - 2] + cost[i - 2];
            dp[i] = Math.min(oneStepCost, twoStepCost);
        }
        return dp[n];
    }

    /**
     * Time Complexity: O(n) - A single loop.
     * Space Complexity: O(1) - Only constant extra space is used.
     */
    public int minCostClimbingStairsSpaceOptimised(int[] cost) {
        var n = cost.length;
        if(n <= 1) {
            return 0; // No cost if we are below the first step
        }
        var minusOne = 0;
        var minusTwo = 0;
        for (int i = 2; i <= n; i++) {
            var oneStepCost = minusOne + cost[i - 1];
            var twoStepCost = minusTwo + cost[i - 2];
            var minCost = Math.min(oneStepCost, twoStepCost);
            minusTwo = minusOne;
            minusOne = minCost;
        }
        return minusOne;
    }

    public static void main(String[] args) {
        var obj = new MinCostClimbingStairs();
        System.out.println("Minimum cost to climb stairs brute force: " + obj.minCostClimbingStairsTopDown(new int[]{10, 15, 20}));
        System.out.println("Minimum cost to climb stairs Top Down: " + obj.minCostClimbingStairsTopDown(new int[]{10, 15, 20}));
        System.out.println("Minimum cost to climb stairs Bottom Up: " + obj.minCostClimbingStairsBottomUp(new int[]{10, 15, 20}));
        System.out.println("Minimum cost to climb stairs Space Optimised: " + obj.minCostClimbingStairsSpaceOptimised(new int[]{10, 15, 20}));
    }
}
