package com.crack.snap.make.hard.stack;

import java.util.Stack;

/**
 * @author Mohan Sharma
 */
public class LargestRectangleInHistogram {

    /**
     * Problem: Given an array of integers heights representing the histogram's bar height where the width of each bar is 1,
     * return the area of the largest rectangle in the histogram.
     * Example: heights = [2,1,5,6,2,3] Answer: 10
     *
     * Intuition: Let's assume there are multiple buildings with different heights in a straight line.
     * Now I can form a rectangle only if the buildings adjacent to me are larger than mine. So I can scan for the building
     * index to the left and right of me to find the boundaries of the rectangle. What will define the boundaries of the rectangle?
     * If any adjacent builder is smaller than me then I cannot include them in my rectangle so it will be the boundary
     * e.g.     |
     *      |   |   |
     * |    |   |   |   |
     * 0    1   2   3   4
     * In the above buildings I can form a rectangle of height 2 and width 3 from index 1 to 3 and here when I am at index 1,
     * the left boundary will be 0 index and right boundary will be 3 index. So the area will be 2 * (3 - 0) = 6
     *
     * Brute force: Move from left to right and for each index check the left and right boundaries and calculate the max area and update
     * the global max
     *
     * Time complexity: O(n^2)
     * Space complexity: O(1)
     * @param heights
     * @return
     */
    public int largestRectangleAreaBruteForce(int[] heights) {
        if (heights == null || heights.length == 0) {
            return 0;
        }

        var length = heights.length;
        var maxArea = 0;
        // 1 2 3 2 1
        // 0 1 2 3 4
        for (var i = 0; i < length; i++) {
            var leftBoundary = i;
            var rightBoundary = i;

            int currentHeight = heights[i];
            while (leftBoundary > 0 && heights[leftBoundary - 1] >= currentHeight) {
                leftBoundary--;
            }
            while (rightBoundary < length - 1 && heights[rightBoundary + 1] >= currentHeight) {
                rightBoundary++;
            }
            maxArea = Math.max(maxArea, currentHeight * (rightBoundary - leftBoundary + 1)); // +1 bcs we need to include the right boundary index since 0 index array
        }
        return maxArea;
    }

    /**
     * If you notice one detail if the right building is smaller than the current building, we need to stop and
     * calculate the area. That means as we scan the buildings from left to right if the next building is large
     * we can keep moving right, but if it is smaller than we know all buildings to the left of it are larger
     * meaning we start calculating the area of all the buildings to the left of it.
     * example if the array was [5 2 3 4 1] when we reach 1 we can calculate the area for building with height 4
     * which will be maxArea = max(maxArea, 4 * (4th Index -  3rd index)) = 4, and then we can move to the next building
     * which is 3 so the area will be maxArea = max(maxArea, 3 * (4th index - 2nd index)) = 6 then we can move to the next building
     * which is 2 so the area will be maxArea = max(maxArea, 2 * (4th index - 1st index)) = 6
     *
     * If you see the pattern, if the current height is greater than the previous height we keep the current height into a data
     * structure. But if the current height is less than the previous height we move back and calculate the heights that means
     * we need a data structure to keep track of the index of the previous heights and while we calculate the area we remove
     * the last height from the data structure. So we need a LIFO data structure to keep track of the heights
     *
     * @param heights
     * @return
     */
    public int largestRectangleArea(int[] heights) {
        if (heights == null || heights.length == 0) {
            return 0;
        }

        var length = heights.length;
        var maxArea = 0;
        var stack = new Stack<Integer>();
        var rightBoundary = 0;

        while (rightBoundary <= length) {
            // Let's say all heights are increasing order at the rightBoundary of the array we will never calculate the area.
            // So the idea is when we reach the rightBoundary of the array we make the height as 0 so that we can calculate
            // the area for all the heights that are present in the stack.
            var currentHeight = rightBoundary == length ? 0 :  heights[rightBoundary];
            if (stack.isEmpty() || heights[stack.peek()] <= currentHeight) {
                stack.push(rightBoundary++);
            } else {
                var height = heights[stack.pop()];
                var leftBoundary = stack.isEmpty() ? 0 : stack.peek() + 1;
                // why peek + 1 bcs we already removed the last height from the stack so the stack now
                // contains the previous of the last height but we want to calculate the last height
                // so we move the add 1 to the index
                maxArea = Math.max(maxArea, height * (rightBoundary - leftBoundary));

            }
        }
        return maxArea;
    }

    public static void main(String[] args) {
        var obj = new LargestRectangleInHistogram();
        System.out.println(obj.largestRectangleAreaBruteForce(new int[]{2, 1, 5, 6, 2, 3}));
        System.out.println(obj.largestRectangleAreaBruteForce(new int[]{2, 1, 2}));
    }
}
