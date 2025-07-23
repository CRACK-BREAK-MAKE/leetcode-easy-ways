package com.crack.snap.make.easy.dp;

/**
 *
 * This is a Pattern 1: Linear DP problem because the cost to reach any step i is determined solely by the costs of
 * reaching the two preceding steps, i-1 and i-2.
 *
 * The Core Intuition:
 *
 * State: Let's define our state dp[i] as the minimum cost to reach step i. The "top floor" is conceptually at index n,
 * where n is the number of steps.
 *
 * Choice: To reach step i, you must have come from either step i-1 or step i-2.
 * Cost Calculation:
 * The cost to get to i from i-1 is: (min cost to reach i-1) + (cost of leaving i-1).
 * This translates to: dp[i-1] + cost[i-1].
 *
 * Recurrence Relation: We want the minimum cost, so we take the minimum of the two paths.
 * dp[i] = min(dp[i-1] + cost[i-1], dp[i-2] + cost[i-2])
 *
 * Base Cases: You can start at step 0 or step 1 for free. So, the cost to "reach" them is 0.
 * dp[0] = 0
 * dp[1] = 0
 *
 * Recursive Flow Diagram
 * Let's trace solve(3) for cost = [10, 15, 20]. n=3, so we want the cost to reach the top floor (index 3).
 *
 * solve(i) means "find minimum cost to reach step i".
 *
 *         solve(3)
 *        /        \
 *   solve(2)+c[2]  solve(1)+c[1]
 *      /      \          |
 * solve(1)+c[1] solve(0)+c[0]  0 (base case)
 *     |             |
 * 0 (base case)  0 (base case)
 *
 * Notice solve(1) is needed by both solve(3) and solve(2). This is the overlapping subproblem that DP solves efficiently.
 *
 * @author Mohan Sharma
 */
public class MinCostClimbingStairs {

    /**
     * Time Complexity: O(n) - Each state solve(i) is computed once.
     * Space Complexity: O(n) - For the recursion stack and memo array.
     */
    public int minCostClimbingStairsTopDown(int[] cost) {
        return minCostMemoized(cost, cost.length, new int[cost.length + 1]);
    }

    private int minCostMemoized(int[] cost, int index, int[] memo) {
        if (index <= 1) {
            return 0; // No cost if we are below the first step
        }
        if (memo[index] != 0) {
            return memo[index]; // Return cached result
        }
        var stepOne = minCostMemoized(cost, index - 1, memo) + cost[index - 1];
        var stepTwo = minCostMemoized(cost, index - 2, memo) + cost[index - 2];
        return memo[index] = Math.min(stepOne, stepTwo);
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
        // dp[0] and dp[1] are implicitly 0 from array initialization.
        var dp = new int[n + 1];

        for ( var i = 2; i <= n; i++) {
            var stepOne = dp[i - 1] + cost[i - 1];
            var stepTwo = dp[i - 2] + cost[i - 2];
            dp[i] = Math.min(stepOne, stepTwo);
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
        var prevOneStep = 0; // Cost to reach the step before last
        var prevTwoSteps = 0; // Cost to reach the last step

        for ( var i = 2; i <= n; i++) {
            var stepOne = prevOneStep + cost[i - 1];
            var stepTwo = prevTwoSteps + cost[i - 2];
            var currentStepCost = Math.min(stepOne, stepTwo);
            // Update the previous costs for the next iteration
            prevTwoSteps = prevOneStep;
            prevOneStep = currentStepCost;
        }
        return prevOneStep;
    }

    public static void main(String[] args) {
        var obj = new MinCostClimbingStairs();
        System.out.println("Minimum cost to climb stairs: " + obj.minCostClimbingStairsTopDown(new int[]{1,100,1,1,1,100,1,1,100,1}));
        System.out.println("Minimum cost to climb stairs: " + obj.minCostClimbingStairsBottomUp(new int[]{10, 15, 20}));
        System.out.println("Minimum cost to climb stairs: " + obj.minCostClimbingStairsSpaceOptimised(new int[]{10, 15, 20}));
    }
}
