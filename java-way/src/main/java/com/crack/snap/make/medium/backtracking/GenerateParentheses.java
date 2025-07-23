package com.crack.snap.make.medium.backtracking;

import java.util.List;

/**
 * @author Mohan Sharma
 */
public class GenerateParentheses {

    /**
     * Problem: Given n pairs of parentheses, write a function to generate all combinations of well-formed parentheses.
     * Input: n = 3
     * Output: ["((()))","(()())","(())()","()(())","()()()"]
     * Input: n = 1
     * Output: ["()"]
     *
     * Intuition: The main idea is we can open parentheses only if openCount is less than n and we can close parentheses only
     * if closeCount is less than openCount.
     *
     * We need to generate all possible combinations of parentheses which can be represented as a decision tree where each decision
     * is to open or close a parentheses. Hence we can use backtracking
     *
     * Now what is backtracking and when to use it?
     * Backtracking is a general algorithmic technique that is used to solve problems incrementally, one step at a time, and
     * remove solutions that fail to satisfy the constraints of the problem at any point in time.
     *
     * We should use backtracking when:
     * 1. Decision Tree Exploration:
     * If the problem can be represented as a tree of decisions where each node represents a partial solution and
     * each branch represents a choice leading to a new partial solution, backtracking is a suitable approach.
     *
     * 2. Constraints Satisfaction:
     * If the problem involves constraints that must be satisfied, and you need to explore all possible configurations
     * to find valid solutions, backtracking is useful.
     *
     * 3. Incremental Construction:
     * If the solution can be built incrementally and you can abandon partial solutions as soon as they are determined to be invalid, backtracking is appropriate.
     *
     * 4. Combination and Permutation Problems:
     * Problems that require generating all possible combinations or permutations of a set of elements often use backtracking.
     *
     * 5. Recursive Problem Solving:
     * If the problem can be broken down into smaller subproblems that can be solved recursively, backtracking is a good fit.
     *
     * Time Complexity:
     * For n pairs of parentheses, the recursion explores all possible combinations of opening and closing parentheses.
     * At each step, there are 2 choices (open or close), and the recursion depth is 2n (since we need to place n open and n close parentheses).
     * Therefore, the total number of combinations is O(2^(2n)).
     *
     * Space Complexity:
     * The space complexity is O(n):
     * The recursion depth is equal to the number of pairs n.
     * The StringBuilder used for backtracking also takes O(n) space.
     * Hence, the overall space complexity is O(n).
     */
    public List<String> generateParenthesis(int n) {
        if (n <= 0) {
            return List.of();
        }
        List<String> result = new java.util.ArrayList<>();
        generateParenthesesRecursively(n ,0, 0 , result, new StringBuilder());
        return result;
    }

    private void generateParenthesesRecursively(int n, int openCount, int closeCount, List<String> result, StringBuilder sb) {
        if (sb.length() == 2 * n) {
            result.add(sb.toString());
            return;
        }

        if (openCount < n) {
            generateParenthesesRecursively(n, openCount + 1, closeCount, result, sb.append("("));
            sb.deleteCharAt(sb.length() - 1);
        }

        if (openCount > closeCount) {
            generateParenthesesRecursively(n, openCount, closeCount + 1, result, sb.append( ")"));
            sb.deleteCharAt(sb.length() - 1);
        }
    }

    public static void main(String[] args) {
        var generateParentheses = new GenerateParentheses();
        System.out.println(generateParentheses.generateParenthesis(3));
        System.out.println(generateParentheses.generateParenthesis(1));
    }
}
