package com.crack.snap.make.hard.backtracking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Mohan Sharma
 */
public class NQueens {

    /**
     * Time Complexity:
     * The algorithm places queens row by row, and for each row, it checks all columns to find a safe position.
     * The number of permutations of queens is n! (as each queen must be placed in a unique row and column).
     * For each permutation, the isSafe method checks safety, which takes O(n) time.
     * Therefore, the total time complexity is O(n * n!).
     *
     * Space Complexity:
     * The space complexity is O(n * n):
     * The board is a 2D array of size n * n.
     * The recursion stack depth is O(n), but this is negligible compared to the board size.
     * Hence, the overall space complexity is O(n * n).
     */
    public List<List<String>> solveNQueensWithoutSpace(int n) {
        if (n <= 0) {
            return List.of();
        }
        var board = new String[n][n];
        for (var row :  board) {
            Arrays.fill(row, ".");
        }
        var result = new ArrayList<List<String>>();
        solveNQueensWithoutSpaceBacktracking(board, 0, result);
        return result;
    }

    private void solveNQueensWithoutSpaceBacktracking(String[][] board, int row, List<List<String>> result) {
        if (row == board.length) {
            var solution = new ArrayList<String>();
            for (var r : board) {
                solution.add(String.join("", r));
            }
            result.add(solution);
            return;
        }
        for (var col = 0; col < board.length; col++) {
            if (isSafe(board, row, col)) {
                board[row][col] = "Q";
                solveNQueensWithoutSpaceBacktracking(board, row + 1, result);
                board[row][col] = "."; // Backtrack
            }
        }
    }

    private  boolean isSafe(String[][] board, int row, int col) {
        for (var i = 0; i < row; i++) {
            if (board[i][col].equals("Q")) {
                return false; // Check column
            }
        }

        for (int i = row - 1, j = col - 1; i >=0 && j >= 0; i--, j--) {
            if (board[i][j].equals("Q")) {
                return false; // Check left diagonal
            }
        }

        for (int i = row - 1, j = col + 1; i >= 0 && j < board.length ; i--, j++) {
            if (board[i][j].equals("Q")) {
                return false; // Check right diagonal
            }
        }
        return  true;
    }

    /**
     * Example for Anti-diagonals (↙) using row + col
     * Every cell on the same top-right to bottom-left diagonal has the same sum of row + col.
     *
     * Let's map out the values of row + col for a 4x4 board:
     *
     *       col 0  1  2  3
     * row 0: [ 0  1  2  3 ]
     * row 1: [ 1  2  3  4 ]
     * row 2: [ 2  3  4  5 ]
     * row 3: [ 3  4  5  6 ]
     * Now let's find the range of these values:
     *
     * Minimum Value: At (row=0, col=0), the sum is 0 + 0 = 0.
     * Maximum Value: At (row=3, col=3), the sum is 3 + 3 = 6.
     * For a general N x N board:
     *
     * Minimum Sum: 0 + 0 = 0
     * Maximum Sum: (n-1) + (n-1) = 2n - 2
     * So, the possible values for row + col range from 0 to 2n - 2. To store a value for each of these possibilities, we need an array that can hold (2n - 2) - 0 + 1 items.
     *
     * Total unique values = 2n - 1.
     *
     * This is why diag2 has a size of 2n - 1. The value of row + col can be used directly as an index.
     * @param n
     * @return
     */
    public List<List<String>> solveNQueensWithSpace(int n) {
        if (n <= 0) {
            return List.of();
        }
        var board = new String[n][n];
        for (var row :  board) {
            Arrays.fill(row, ".");
        }
        var col = new boolean[n];
        var mainDiagonal = new boolean[2 * n - 1]; // row + col
        var antiDiagonal = new boolean[2 * n - 1]; // row - col + (n - 1), -3 becomes 0, -2 becomes 1, -1 becomes 2, 0 becomes 3
        var result = new ArrayList<List<String>>();
        solveNQueensWithSpaceBacktracking(board, 0, result, col, mainDiagonal, antiDiagonal);
        return result;
    }

    private void solveNQueensWithSpaceBacktracking(String[][] board, int row, List<List<String>> result, boolean[] colsUsed, boolean[] mainDiagonal, boolean[] antiDiagonal) {
        int n = board.length;
        if (row == n) {
            var solution = new ArrayList<String>();
            for (var r : board) {
                solution.add(String.join("", r));
            }
            result.add(solution);
            return;
        }
        for (var col = 0; col < n; col++) {
            var mainDiagIndex = row - col + (n - 1); // to handle negative indices
            var antiDiagIndex = row + col; // no need to handle negative indices as it will always be positive
            if (!colsUsed[col] && !mainDiagonal[mainDiagIndex] && !antiDiagonal[antiDiagIndex]) {
                board[row][col] = "Q";
                colsUsed[col] = true;
                mainDiagonal[mainDiagIndex] = true;
                antiDiagonal[antiDiagIndex] = true;
                solveNQueensWithSpaceBacktracking(board, row + 1, result, colsUsed, mainDiagonal, antiDiagonal);
                board[row][col] = "."; // Backtrack
                colsUsed[col] = false;
                mainDiagonal[mainDiagIndex] = false;
                antiDiagonal[antiDiagIndex] = false;
            }
        }
    }

    public static void main(String[] args) {
        var obj = new NQueens();
        System.out.println(obj.solveNQueensWithoutSpace(4));
        System.out.println(obj.solveNQueensWithSpace(4));
    }
}
