package com.crack.snap.make.hard.dp;

import java.util.List;

/**
 * @author Mohan Sharma
 */
public class LongestIncreasingPathMatrix {

    public int longestIncreasingPathBruteForce(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }
        var result = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                result = Math.max(result, longestIncreasingPathBacktracking(matrix, i, j, -1));
            }
        }
        return result;
    }

    private int longestIncreasingPathBacktracking(int[][] matrix, int row, int col, int prevValue) {
        if (row < 0 || col < 0 || row >= matrix.length || col >= matrix[0].length || matrix[row][col] <= prevValue) {
            return 0;
        }
        var currentValue = matrix[row][col];
        var down = longestIncreasingPathBacktracking(matrix, row + 1, col, currentValue);
        var up = longestIncreasingPathBacktracking(matrix, row - 1, col, currentValue);
        var left = longestIncreasingPathBacktracking(matrix, row, col - 1, currentValue);
        var right = longestIncreasingPathBacktracking(matrix, row, col + 1, currentValue);
        return 1 + Math.max(Math.max(down, up), Math.max(left, right));
    }

    public int longestIncreasingPathTopDown(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }
        var result = 0;
        var memo = new Integer[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                result = Math.max(result, longestIncreasingPathMemoization(matrix, i, j, memo));
            }
        }
        return result;
    }

    private int longestIncreasingPathMemoization(int[][] matrix, int row, int col, Integer[][] memo) {
        if (memo[row][col] != null) {
            return memo[row][col];
        }
        var iterations = List.of(-1, 0, 1, 0, -1);
        var longestIncreasingPath = 0;
        for (var i = 0; i < 4; i++) {
            var newRow = row + iterations.get(i);
            var newCol = col + iterations.get(i + 1);
            if (newRow >= 0 && newCol >= 0 && newRow < matrix.length && newCol < matrix[0].length && matrix[newRow][newCol] > matrix[row][col]) {
                longestIncreasingPath = Math.max(longestIncreasingPath, longestIncreasingPathMemoization(matrix, newRow, newCol, memo));
            }
        }
        return memo[row][col] = 1 + longestIncreasingPath;
    }

    public static void main(String[] args) {
        var obj = new LongestIncreasingPathMatrix();
        System.out.println(obj.longestIncreasingPathBruteForce(new int[][]{{9,9,4}, {6,6,8}, {2,1,1}}));
        System.out.println(obj.longestIncreasingPathBruteForce(new int[][]{{3,4,5}, {3,2,1}, {2,2,1}}));
        System.out.println(obj.longestIncreasingPathBruteForce(new int[][]{{1, 9}, {1, 9}}));
        System.out.println(obj.longestIncreasingPathTopDown(new int[][]{{9,9,4}, {6,6,8}, {2,1,1}}));
        System.out.println(obj.longestIncreasingPathTopDown(new int[][]{{3,4,5}, {3,2,1}, {2,2,1}}));
        System.out.println(obj.longestIncreasingPathTopDown(new int[][]{{1, 9}, {1, 9}}));
    }
}
