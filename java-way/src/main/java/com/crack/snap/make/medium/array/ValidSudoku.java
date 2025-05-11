package com.crack.snap.make.medium.array;

import java.util.HashSet;

/**
 * @author Mohan Sharma
 */
public class ValidSudoku {

    /**
     * Intuition:
     * Each row must contain the digits 1-9 without duplicates. Each column must contain the digits 1-9 without duplicates.
     * Each of the nine 3x3 sub-boxes of the grid must contain the digits 1-9 without duplicates.
     * Return true if the Sudoku board is valid, otherwise return false.
     * Naive Approach:
     * - Iterate over the 2D array and have a set for rows such that elements are uniquely identifiable using the "r-row number-element combination".
     *   For example, [[1, 2], [1, 2]] would be represented as r-0-1, r-0-2, r-1-1, r-1-2, etc.
     * - Similarly, for columns, we can have one set such that elements are uniquely identifiable using the "c-column number-element combination".
     * - Similarly, for boxes, we can have one set such that elements are uniquely identifiable using the box number "b-(row number / 3 : column number / 3) + element combination".
     * Time Complexity: O(1)
     * - The board size is fixed at 9x9, so the operations are constant time.
     * Space Complexity: O(1)
     * - The space used by the sets is constant as the board size is fixed.
     *
     * @param board the 9x9 Sudoku board represented as a 2D array of characters
     * @return true if the board is valid, false otherwise
     */
    public boolean isValidSudokuN3Space(char[][] board) {
        var col = new HashSet<>(9);
        var row = new HashSet<>(9);
        var box = new HashSet<>(9);

        for (var i = 0; i < 9; i++) {
            for (var j = 0; j < 9; j++) {
                char letter = board[i][j];
                if (letter == '.') {
                    continue;
                }
                if (!col.add("c-" + j + "-" + letter) ||
                        !row.add("r-" + i + "-" + letter) ||
                        !box.add("b-" + i / 3 + "-" + j / 3 + "-" + letter)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Intuition:
     * The above solution works with 3 sets ideally since the elements are uniquely identifiable using
     * the "r-row number-element combination", "c-column number-element combination", and
     * "b-(row number / 3 : column number / 3) + element combination". We can instead use a single set as well.
     * Why this works?
     * The uniqueness will be maintained for rows and columns since we are inserting like r-0-1, r-0-2, r-1-1, r-1-2
     * and c-0-1, c-0-2, c-1-1, c-1-2 etc. The uniqueness will be maintained for boxes as well since we are inserting
     * like b-0:0-1, b-0:0-2, b-0:1-1, b-0:1-2 etc. Since 0/3 or 1/3 or 2/3 is 0 and 3/3 or 4/3 or 5/3 is 1 and 6/3 or 7/3 or 8/3 is 2 and so on.
     * Optimized Approach:
     * - Use a single set to keep track of seen digits in rows, columns, and 3x3 sub-boxes.
     * - For each cell, create unique identifiers for the row, column, and sub-box.
     * - Check if these identifiers are already in the set.
     * - If any identifier is found in the set, return false.
     * - Otherwise, add the identifiers to the set and continue.
     * Time Complexity: O(1)
     * - The board size is fixed at 9x9, so the operations are constant time.
     * Space Complexity: O(1)
     * - The space used by the set is constant as the board size is fixed.
     *
     */
    public boolean isValidSudoku(char[][] board) {
        var set = new HashSet<>(27);
        for (var i = 0; i < 9; i++) {
            for (var j = 0; j < 9; j++) {
                char letter = board[i][j];
                if (letter == '.') {
                    continue;
                }
                if (!set.add("r-" + i + "-" + letter) ||
                        !set.add("c-" + j + "-" + letter) ||
                        !set.add("b-" + (i / 3) + ":" + (j / 3) + "-" + letter)) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        char[][] board = new char[][]{{'1', '2', '.', '.', '3', '.', '.', '.', '.'}, {'4', '.', '.', '5', '.', '.', '.', '.', '.'}, {'.', '9', '8', '.', '.', '.', '.', '.', '3'}, {'5', '.', '.', '.', '6', '.', '.', '.', '4'}, {'.', '.', '.', '8', '.', '3', '.', '.', '5'}, {'7', '.', '.', '.', '2', '.', '.', '.', '6'}, {'.', '.', '.', '.', '.', '.', '2', '.', '.'}, {'.', '.', '.', '4', '1', '9', '.', '.', '8'}, {'.', '.', '.', '.', '8', '.', '.', '7', '9'}};
        System.out.println(new ValidSudoku().isValidSudoku(board));
    }
}
