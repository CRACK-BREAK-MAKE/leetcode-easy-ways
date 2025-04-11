package com.crack.snap.make.medium.binarysearch;

/**
 * @author Mohan Sharma
 */
public class Search2dMatrix {

    /**
     * Problem: You are given an m x n integer matrix with the following two properties:
     * 1. Each row is sorted in non-decreasing order.
     * 2. The first integer of each row is greater than the last integer of the previous row.
     * Given an integer target, return true if target is in matrix or false otherwise.
     * You must write a solution in O(log(m * n)) time complexity.
     *
     * Intuition: Iterate over the 2d matrix and find the array where this target can be present by checking if the start >= target and end <= target.
     * Then apply binary search on that array.
     *
     * Time Complexity: O(m * log(n)) where m is the number of rows and n is the number of columns.
     * Space Complexity: O(1)
     * @param matrix
     * @param target
     * @return
     */
    public boolean searchMatrixBruteForce(int[][] matrix, int target) {
        if (matrix == null){
            return false;
        }
        for (var array : matrix) {
            if (array[0] <= target && array[array.length - 1] >= target) {
                return binarySearch(array, target);
            }
        }
        return false;
    }

    private boolean binarySearch(int[] array, int target) {
        if (array == null || array.length == 0) {
            return false;
        }
        var left = 0;
        var right = array.length - 1;
        while (left <= right) {
            var mid = left + (right - left) / 2;
            if (array[mid] == target) {
                return true;
            } else if (array[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return false;
    }

    /**
     * In the brute force approach above, to check the element of the array we are literally iterating over the array one by one
     * But if you think about it, we can search any array just by keeping track of the row
     * Initially when row is 0, we can check the midValue = matrix[row][midIndex]. Now if the target is greater than the midValue
     * ideally we move the start to midIndex + 1 to search in the other half but at the same time if the target is also greater
     * than the last element of the row (target > matrix[row][matrix[row].length - 1]), then we can move to the next row and so on
     *
     * Time Complexity: O(log(m * n)) where m is the number of rows and n is the number of columns.
     * Space Complexity: O(1)
     * @param matrix
     * @param target
     * @return
     */
    public boolean searchMatrix(int[][] matrix, int target) {
        if (matrix.length == 0) {
            return false;
        }
        var row = 0;
        var start = 0;
        var end = matrix[row].length - 1;
        while (start <= end && row < matrix.length) {
            var midIndex = start + (end - start) / 2;
            var midValue = matrix[row][midIndex];
            if (target == midValue) {
                return true;
            } else if (target > midValue) {
                if (target > matrix[row][matrix[0].length - 1]) {
                    row++;
                    start = 0;
                    end = matrix[0].length - 1;
                } else {
                    start = midIndex + 1;
                }
            } else {
                end = midIndex - 1;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        var obj = new Search2dMatrix();
        System.out.println(obj.searchMatrix(new int[][] {
                {1, 3, 5, 7},
                {10, 11, 16, 20},
                {23, 30, 34, 50},
                {60, 70, 80, 90}
        }, 80)); // true
    }
}
