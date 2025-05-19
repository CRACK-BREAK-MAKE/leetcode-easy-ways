package com.crack.snap.make.medium.stack;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Stack;
import java.util.TreeMap;

/**
 * @author Mohan Sharma
 */
public class CarFleet {

    /**
     * Problem: There are n cars at given miles away from the starting mile 0, traveling to reach the mile target.
     * You are given two integer array position and speed, both of length n, where position[i] is the starting mile of the ith car
     * and speed[i] is the speed of the ith car in miles per hour. A car cannot pass another car, but it can catch up and
     * then travel next to it at the speed of the slower car. A car fleet is a car or cars driving next to each other.
     * The speed of the car fleet is the minimum speed of any car in the fleet. If a car catches up to a car fleet at the mile
     * target, it will still be considered as part of the car fleet. Return the number of car fleets that will arrive at the destination.
     *
     * Intuition: Think logically, we know speed = distance/time so time = distance/speed
     * Now let's think about the car nearest to the destination, the time it will take to reach the destination is
     * (target - currentPosition)/speed. e.g. target = 12, position = [10, 8, 0, 5, 3], speed = [2, 4, 1, 1, 3]
     * For last car time = (12 - 10)/2 = 1 unit
     * For Second last car time = (12 - 8)/4 = 1 unit
     * Now since the second last car will reach the target at the same time or less time that means they are bound to meet and create a fleet
     * One more example
     * Car at position 5 with speed 1 will take (12 - 5)/1 = 7 units of time to reach the target but
     * Car at position 3 with speed 3 will take (12 - 3)/3 = 3 units of time to reach the target that means car at position 3
     * is bound to meet car at position 5 and create a fleet
     *
     * That means first we need to sort the (position, speed) descending order of position
     * Now which data structure to use to keep track of the fleets?
     * If you see the first example, when the second last car meets the last car it will always travel at the speed
     * of the last car as per our example and the problem statement, that means all we need to keep in the data structure
     * is the last car which creates the first fleet. Now if there is a car taking more time then the first fleet as it is slow,
     * then it will never reach the first fleet so it creates its own fleet and all cars behind it will never be able to exceed
     * this speed.
     * So to create a fleet we need the time of the last car/fleet. We pop the last time and check the current car time and decide
     * if it is part of the previous car/fleet or not. If it becomes part of the fleet we don't need this car anymore as it's
     * speed was more and from now on it will travel at the speed of the previous car/fleet. If it does not become part of the fleet
     * we will keep this to this data structure and check the next car where we will use this car's time. That mean we will need LIFO
     *
     * Time complexity: O(nlogn) for sorting and O(n) for traversing the array
     * Space complexity: O(n) for stack
     * @param target
     * @param position
     * @param speed
     * @return
     */
    public int carFleet(int target, int[] position, int[] speed) {
        if (position == null || position.length == 0 || position.length != speed.length) {
            return 0;
        }
        var positionSpeedMap = new TreeMap<Integer, Integer>(Comparator.reverseOrder());
        var stack = new Stack<Double>();
        for (int i = 0; i < position.length; i++) {
            positionSpeedMap.put(position[i], speed[i]);
        }

        for (var entry : positionSpeedMap.entrySet()) {
            var time = (double) (target - entry.getKey()) / entry.getValue();
            // if current time is greater than the last time then it will create its own fleet
            // But if the current time is less than the last time then it will be part of the last fleet so we don't need to push to stack
            if (stack.isEmpty() || time > stack.peek()) {
                stack.push(time);
            }
        }
        return stack.size();
    }

    /**
     * Optimised solution:
     * Instead of using two data structures like above solution where one keeps the sorted position and speed but other one just keeps the time
     * of the ahead car/fleet. Instead, we can use variable to keep track of the time of the ahead car/fleet and use a single data structure for
     * sorting the position and speed. This will reduce the space complexity to O(n)
     *
     * @param target
     * @param position
     * @param speed
     * @return
     */
    public int carFleetOptimised(int target, int[] position, int[] speed) {
        if (position == null || position.length == 0 || position.length != speed.length) {
            return 0;
        }

        var positionSpeed = new int[position.length][2];
        for (var i = 0; i < position.length; i++) {
            positionSpeed[i][0] = position[i];
            positionSpeed[i][1] = speed[i];
        }

        Arrays.sort(positionSpeed, ( a, b) -> b[0]- a[0]);
        var carFleet = 0;
        var movingAheadCarTime = Double.MIN_VALUE;

        for (var i = 0; i < positionSpeed.length; i++) {
            var time = (double) (target - positionSpeed[i][0]) / positionSpeed[i][1];
            if (time > movingAheadCarTime) {
                carFleet++;
                movingAheadCarTime = time;
            }
        }
        return carFleet;
    }

    public static void main(String[] args) {
        var obj = new CarFleet();
        System.out.println(obj.carFleetOptimised(12, new int[]{10, 8, 0, 5, 3}, new int[]{2, 4, 1, 1, 3}));
        System.out.println(obj.carFleetOptimised(10, new int[]{6, 8}, new int[]{3, 2})); // 1 fleet
    }
}
