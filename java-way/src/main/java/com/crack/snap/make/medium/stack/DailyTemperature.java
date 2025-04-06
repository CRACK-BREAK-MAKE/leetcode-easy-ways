package com.crack.snap.make.medium.stack;

import java.util.Arrays;
import java.util.Stack;

/**
 * @author Mohan Sharma
 */
public class DailyTemperature {

    /**
     * Problem: Given an array of integers temperatures represents the daily temperatures, return an array answer such that answer[i]
     * is the number of days you have to wait after the ith day to get a warmer temperature. If there is no future day
     * for which this is possible, keep answer[i] == 0 instead.
     * Example:
     * Input: temperatures = [73,74,75,71,69,72,76,73]
     * Output: [1,1,4,2,1,1,0,0]
     *
     * Intuition: The first approach that comes to mind is for each index scan the rest of the array to find the next greater element.
     * And then move to next index and do the same. This will take O(n^2) time complexity.
     * Might TLE for large inputs.
     * @param temperatures
     * @return
     */
    public int[] dailyTemperaturesBruteForce(int[] temperatures) {
        if (temperatures == null || temperatures.length == 0) {
            return new int[0];
        }

        var result = new int[temperatures.length];
        for (var i = 0; i < temperatures.length; i++) {
            for (var j = i+1; j < temperatures.length; j++) {
                if (temperatures[j] > temperatures[i]) {
                    result[i] = j - i;
                    break;
                }
            }
        }
        return result;
    }

    /**
     * If you analyse the solution above you will see for an index we keep going right until we find a greater element
     * then we track back to the index we started from. This is a waste of time.
     *
     * If we can somehow remember the previous values we have seen and their index we can do this in O(n) time complexity.
     * Meaning as we go from left to right we can keep track of the previous index, if the current value if less then the
     * last previous index value we add to the data structure and move to the next index. If the current value is
     * greater than the last index value that means we got one valid answer, we substract the current index from the last index
     * and this becomes the result for the previous index temperature as per the problem statement. Since the previous index is
     * not needed anymore we remove from the data structure and check for the previous of previous index and so on.
     *
     * That means we need a LIFO data structure to keep track of the previous index and their values.
     * @param temperatures
     * @return
     */
    public int[] dailyTemperatures(int[] temperatures) {
        if (temperatures == null || temperatures.length == 0) {
            return new int[0];
        }
        var result = new int[temperatures.length];
        var stack = new Stack<Integer>();

        for (var i = 0; i < temperatures.length; i++) {
            while (!stack.isEmpty() && temperatures[stack.peek()] < temperatures[i]) {
                    var previousIndex = stack.pop();
                    result[previousIndex] = i - previousIndex;
            }
            stack.push(i);
        }

        return result;
    }

    public static void main(String[] args) {
        var temperatures = new int[]{73, 74, 75, 71, 69, 72, 76, 73};
        var obj = new DailyTemperature();
        System.out.println(Arrays.toString(obj.dailyTemperatures(temperatures)));
    }
}
