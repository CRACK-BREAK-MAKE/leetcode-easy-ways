package com.crack.snap.make.medium.twopointer;

/**
 * @author Mohan Sharma
 */
public class TrappingRainWater {

    //Problem: Given n non-negative integers representing an elevation map where the width of each bar is 1,
    // compute how much water it can trap after raining. height = [0,1,0,2,1,0,1,3,2,1,2,1]
    //  .
    //  .                    |
    //  .        |           |   |       |
    //  .   |    |   |   |   |   |   |   |   |
    //  -----------------------------------------
    //    0 1 0  2   1 0 1   3   2   1   2   1
    // Intuition: Let's suppose there are height of buildings and space between the two buildings are container to capture rain water.
    // To keep water between two buildings we need the height of the buildings to be above 0 and Also if there are only two buildings,
    // no water can be trapped why because there is no container to place in a space between the buildings. So at least 3 buildings
    // are required to trap water. When we calculate water trapped between the two buildings, we need to get
    // the height of the smaller building then we can calculate water by (current building height - smaller building height)
    // example water trapped at index 2 = min(height[1], height[3]) - height[2] = min(1, 2) - 0 = 1
    // Similarly water trapped at index 5 = min(height[3], height[7]) - height[5] = min(2, 3) - 0 = 2
    // So if you we at any index we need the leftMaximum height and the rightMaximum height to calculate the water trapped
    // Solution we will use two arrays to store the leftMaximum and rightMaximum height at each index then
    // we will iterate over the height array and calculate the water trapped at each index by doing Math.min(leftMax[i], rightMax[i]) - height[i])
    public int trapUsingSpace(int[] height) {
        if (height == null || height.length < 3) {
            return 0;
        }
        var waterTrapped = 0;
        var leftMax = new int[height.length];
        var rightMax = new int[height.length];

        var leftMaxHeight = height[0];
        var rightMaxHeight = height[height.length - 1];
        for(var i = 1; i < height.length; i++) {
            leftMax[i] = leftMaxHeight;
            leftMaxHeight = Math.max(leftMaxHeight, height[i]);
        }

        for(var i = height.length - 2; i >= 0; i--) {
            rightMax[i] = rightMaxHeight;
            rightMaxHeight = Math.max(rightMaxHeight, height[i]);
        }

        for ( var i = 0; i < height.length; i++) {
            var water = Math.min(leftMax[i], rightMax[i]) - height[i];
            if (water > 0) {
                waterTrapped += water;
            }
        }
        return waterTrapped;
    }

    // If you analyse the above solution we calculate min height of leftMax and rightMax at each index that means
    // at any index if we know the leftMax and rightMax height we can calculate the water trapped at that index
    // by taking the min of the two heights and subtracting the height at that index.
    // Example at index 2, leftMax = 1, but rightMax let's assume it was the end of the array which is 1
    // so we can calculate the water trapped at index 2 by doing Math.min(leftMax, rightMax) - height[2] which will be
    // Math.min(1, 1) - 0 = 1. Now it's time to move pointer to the next index since both leftMax and rightMax are same or if leftMax is less
    // than rightMax we can move left pointer. At index 5 the leftMax at that point is 2 and rightMax is 3 so water trapped at index 5
    // will be Math.min(2, 3) - height[5] = Math.min(2, 3) - 0 = 2
    // why do we do while (leftPointer <= rightPointer) instead of while (leftPointer < rightPointer) because there may be a
    // scenario that one index calculation might be missed if we do leftPointer < rightPointer
    public int trapOptimized(int[] height) {
        if (height == null || height.length < 3) {
            return 0;
        }
        var waterCaptured = 0;
        var leftMax = height[0];
        var rightMax = height[height.length - 1];
        var leftPointer = 0;
        var rightPointer = height.length - 1;
        while (leftPointer <= rightPointer) {
            if (leftMax <= rightMax) {
                var water = leftMax - height[leftPointer];
                if (water > 0) {
                    waterCaptured += water;
                }
                leftMax = Math.max(leftMax, height[leftPointer]);
                leftPointer++;
            } else {
                var water = rightMax - height[rightPointer];
                if (water > 0) {
                    waterCaptured += water;
                }
                rightMax = Math.max(rightMax, height[rightPointer]);
                rightPointer--;
            }
        }
        return waterCaptured;
    }

    public static void main(String[] args) {
        TrappingRainWater trappingRainWater = new TrappingRainWater();
        System.out.println(trappingRainWater.trapUsingSpace(new int[]{0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1}));
        System.out.println(trappingRainWater.trapUsingSpace(new int[]{4,2,0,3,2,5}));
        System.out.println(trappingRainWater.trapUsingSpace(new int[]{0,2,0,3,1,0,1,3,2,1}));
        System.out.println(trappingRainWater.trapOptimized(new int[]{0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1}));
        System.out.println(trappingRainWater.trapOptimized(new int[]{4,2,0,3,2,5}));
        System.out.println(trappingRainWater.trapOptimized(new int[]{0,2,0,3,1,0,1,3,2,1}));
    }
}
