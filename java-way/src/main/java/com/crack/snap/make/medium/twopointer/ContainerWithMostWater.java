package com.crack.snap.make.medium.twopointer;

/**
 * @author Mohan Sharma
 */
public class ContainerWithMostWater {

    /**
     * Problem: You are given an integer array height of length n.
     * There are n vertical lines drawn such that the two endpoints of the ith line are (i, 0) and (i, height[i]).
     * Find two lines that together with the x-axis form a container, such that the container contains the most water.
     *
     * Intuition:
     * Imagine you are in a metropolitan city having multiple skyscrapers as well as small buildings. Between two skyscrapers/buildings,
     * you can have a container that can hold water where the length of the container is the distance between the two buildings.
     * The height of the container will be the minimum of the two heights, as water can flow over the smaller building.
     * So, the water contained will be the distance between the buildings * height of the smaller building.
     *
     * Suppose you are on the small building and you calculated the water contained between the two buildings.
     * Now, to find more water contained between the two buildings, we want to move from the smaller building to the next building
     * with optimism that the next building will be taller than the current building and calculate the water contained.
     * Finally, we will have the maximum water contained between the two buildings among all using a chopper (global variable).
     *
     * Time Complexity: O(n)
     * - We only traverse the array once with the two pointers, so the operations are linear time.
     *
     * Space Complexity: O(1)
     * - We use a constant amount of extra space for the pointers and the result variable.
     *
     * @param height the input array of integers representing the heights of the lines
     * @return the maximum amount of water a container can store
     */
    public int maxArea(int[] height) {
        if (height == null || height.length < 2) {
            return 0;
        }
        var left = 0;
        var right = height.length - 1;
        var maxArea = 0;
        while (left < right) {
            maxArea = Math.max(maxArea, Math.min(height[left], height[right]) * (right - left));
            if (height[left] < height[right]) {
                left++;
            } else {
                right--;
            }
        }

        return maxArea;
    }

    public static void main(String[] args) {
        ContainerWithMostWater container = new ContainerWithMostWater();
        System.out.println(container.maxArea(new int[]{1, 8, 6, 2, 5, 4, 8, 3, 7}));
        System.out.println(container.maxArea(new int[]{1, 1}));
    }
}
