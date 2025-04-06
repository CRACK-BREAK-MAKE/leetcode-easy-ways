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
     * 6. Exponential Time Complexity:
     * Problems that have an exponential number of possible solutions often use backtracking to explore all possibilities efficiently.
     * @param n
     * @return
     */
    public List<String> generateParenthesis(int n) {
        if (n <= 0) {
            return List.of();
        }
        List<String> result = new java.util.ArrayList<>();
        generateParenthesesRecursively(0, 0 , n, result, "");
        return result;
    }

    private void generateParenthesesRecursively(int openCount, int closeCount, int totalLength, List<String> result, String subStr) {
        if (subStr.length() == 2 * totalLength) {
            result.add(subStr);
            return;
        }

        if (openCount < totalLength) {
            generateParenthesesRecursively(openCount + 1, closeCount,totalLength, result, subStr + "(");
        }

        if (openCount > closeCount) {
            generateParenthesesRecursively(openCount, closeCount + 1, totalLength, result, subStr + ")");
        }
    }

    public static void main(String[] args) {
        var generateParentheses = new GenerateParentheses();
        System.out.println(generateParentheses.generateParenthesis(3));
        System.out.println(generateParentheses.generateParenthesis(1));
    }
}
