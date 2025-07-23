package com.crack.snap.make.hard.backtracking;

import java.util.Arrays;

/**
 * @author Mohan Sharma
 */
public class SudokuSolver {

    /**
     * Time Complexity:
     * The time complexity is O(9^m), where m is the number of empty cells in the Sudoku board. Here's why:
     * For each empty cell, there are at most 9 possible numbers to try.
     * The algorithm recursively explores all combinations of numbers for the empty cells.
     * In the worst case, all cells are empty, leading to 9^m combinations.
     *
     * Space Complexity:
     * The space complexity is O(m):
     * The recursion depth corresponds to the number of empty cells m, as each recursive call processes one cell.
     * No additional data structures are used apart from the board itself.
     */
    public void solveSudokuWithoutSpace(char[][] board) {
        solveSudokuBacktrackingWithoutSpace(board, 0, 0);
    }

    private boolean solveSudokuBacktrackingWithoutSpace(char[][] board, int row, int col) {
        if (row == 9) {
            return true;
        }
        // start filling columns for the current row,then move to the next row
        var nextRow = col == 8 ? row + 1 : row;
        var nextCol = col == 8 ? 0 : col + 1;

        if (board[row][col] != '.') {
            return solveSudokuBacktrackingWithoutSpace(board, nextRow, nextCol);
        } else {
            for (int num = 1; num <= 9; num++) {
                if (isSafe(board, row, col, num)) {
                    board[row][col] = (char) (num + '0'); // choose a solution
                    if (solveSudokuBacktrackingWithoutSpace(board, nextRow, nextCol)) {
                        return true;
                    }
                    board[row][col] = '.'; // backtrack
                }
            }
            // If we've tried all numbers '1'-'9' and none worked, this path is impossible.
            return false;
        }
    }

    private boolean isSafe(char[][] board, int row, int col, int num) {
        char numChar = (char) (num + '0'); // Convert num to char for comparison
        // check column
        for (int i = 0; i < 9; i++) {
            if (board[i][col] == numChar) {
                return false;
            }
        }

        // check row
        for (int i = 0; i < 9; i++) {
            if (board[row][i] == numChar) {
                return false;
            }
        }

        // check box
        var rowStart = (row / 3) * 3;
        var colStart = (col / 3) * 3;
        for (int i = rowStart; i < rowStart + 3; i++) {
            for (int j = colStart; j < colStart + 3; j++) {
                if (board[i][j] == numChar) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * The goal of the formula (r / 3) * 3 + (c / 3) is to map any cell coordinate (r, c) to a single box number from 0 to 8. Let's visualize the boxes numbered like this: c=0  c=1  c=2   c=3  c=4  c=5   c=6  c=7  c=8 r=0 |--------------|---------------|---------------| r=1 |    Box 0     |     Box 1     |     Box 2     | r=2 |--------------|---------------|---------------| r=3 |--------------|---------------|---------------| r=4 |    Box 3     |     Box 4     |     Box 5     | r=5 |--------------|---------------|---------------| r=6 |--------------|---------------|---------------| r=7 |    Box 6     |     Box 7     |     Box 8     | r=8 |--------------|---------------|---------------|
     */
    public void solveSudokuWithSpace(char[][] board) {
        if (board == null || board.length == 0 || board[0].length == 0) {
            return;
        }
        var columns = new boolean[9][10];
        var rows = new boolean[9][10];
        var boxes = new boolean[9][10];
        for (var row = 0; row < 9; row++) {
            for (var col = 0; col < 9; col++) {
                if (board[row][col] != '.') {
                    var num = board[row][col] - '0';
                    columns[col][num] = true;
                    rows[row][num] = true;
                    boxes[(row / 3) * 3 + col / 3][num] = true;
                }
            }
        }
        solveSudokuBacktrackingWithSpace(board, 0, 0, columns, rows, boxes);
    }

    /**
     * For void return type, The issue will be backtracking function does not stop recursion when a valid solution is found. It keeps backtracking and undoes the solution, so the board remains unsolved.
     */
    private boolean solveSudokuBacktrackingWithSpace(char[][] board, int row, int col, boolean[][] columns, boolean[][] rows, boolean[][] boxes) {
        if (row == 9) {
            return true; // All rows are filled
        }
        // start filling columns for the current row,then move to the next row
        var nextRow = col == 8 ? row + 1 : row;
        var nextCol = col == 8 ? 0 : col + 1;

        if (board[row][col] != '.') {
            return solveSudokuBacktrackingWithSpace(board, nextRow, nextCol, columns, rows, boxes);
        } else {
            for (var num = 1; num <= 9; num++) {
                if (!columns[col][num] && !rows[row][num] && !boxes[(row / 3) * 3 + col / 3][num]) {
                    // Place the number
                    board[row][col] = (char) (num + '0');
                    columns[col][num] = true;
                    rows[row][num] = true;
                    boxes[(row / 3) * 3 + col / 3][num] = true;

                    // Recur to the next cell
                    if (solveSudokuBacktrackingWithSpace(board, nextRow, nextCol, columns, rows, boxes)) {
                        return true; // If the next cell is solved, return true
                    }

                    // Backtrack
                    board[row][col] = '.';
                    columns[col][num] = false;
                    rows[row][num] = false;
                    boxes[(row / 3) * 3 + col / 3][num] = false;
                }
            }
            return false;
        }
    }

    public static void main(String[] args) {
        var solver = new SudokuSolver();
        var board = new char[][]{
                {'5', '3', '.', '.', '7', '.', '.', '.', '.'},
                {'6', '.', '.', '1', '9', '5', '.', '.', '.'},
                {'.', '9', '8', '.', '.', '.', '.', '6', '.'},
                {'8', '.', '.', '.', '6', '.', '.', '.', '3'},
                {'4', '.', '.', '8', '.', '3', '.', '.', '1'},
                {'7', '.', '.', '.', '2', '.', '.', '.', '6'},
                {'.', '6', '.', '.', '.', '.', '2', '8', '.'},
                {'.', '.', '.', '4', '1', '9', '.', '.', '5'},
                {'.', '.', '.', '.', '8', '.', '.', '7', '9'}
        };

        solver.solveSudokuWithSpace(board);

        System.out.println(Arrays.deepToString(board));

        board = new char[][]{
                {'5', '3', '.', '.', '7', '.', '.', '.', '.'},
                {'6', '.', '.', '1', '9', '5', '.', '.', '.'},
                {'.', '9', '8', '.', '.', '.', '.', '6', '.'},
                {'8', '.', '.', '.', '6', '.', '.', '.', '3'},
                {'4', '.', '.', '8', '.', '3', '.', '.', '1'},
                {'7', '.', '.', '.', '2', '.', '.', '.', '6'},
                {'.', '6', '.', '.', '.', '.', '2', '8', '.'},
                {'.', '.', '.', '4', '1', '9', '.', '.', '5'},
                {'.', '.', '.', '.', '8', '.', '.', '7', '9'}
        };

        solver.solveSudokuWithoutSpace(board);

        System.out.println(Arrays.deepToString(board));
    }
}
