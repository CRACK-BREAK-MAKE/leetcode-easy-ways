package com.crack.snap.make.medium.priorityqueue;

import java.util.PriorityQueue;

/**
 * @author Mohan Sharma
 */
public class KClosestPointsToOrigin {

    /**
     * Problem: Given an array of points where points[i] = [xi, yi] represents a point on the X-Y plane and an integer k,
     * return the k closest points to the origin (0, 0).
     * The distance between two points on the X-Y plane is the Euclidean distance (i.e., √(x1 - x2)2 + (y1 - y2)2).
     * You may return the answer in any order. The answer is guaranteed to be unique (except for the order that it is in).
     *
     * Intuition: We need to calculate distance between the points using the formula √(x1 - 0)2 + (y1 - 0)2). Why 0? Because we need
     * to find the distance from origin (0,0). After we calculate the distance we need to keep track of K smallest distances, we can
     * keep them in an array and always remove the largest one when a new smaller is found within the K window. If we use array to keep
     * the elements in sorted order where 0th index contains the largest element, when a new minimum is found, we need to remove
     * the 0th and shirt which is not ideal so we can use Priority Queue to keep the elements in sorted order and use Max Heap
     */
    public int[][] kClosest(int[][] points, int k) {
        var maxHeap = new PriorityQueue<int[]>((a, b) -> {
            // we don't really need to calculate the square root, because if the distance is smaller,
            // the square of the distance will also be smaller
            var aDistance = a[0] * a[0] + a[1]* a[1];
            var bDistance = b[0] * b[0] + b[1]* b[1];
            return Double.compare(bDistance, aDistance);
        });
        for (var point: points) {
            maxHeap.add(point);
            if (maxHeap.size() > k) {
                maxHeap.poll();
            }
        }
        var result = new int[k][2];
        for (int i = 0; i < k; i++) {
            result[i] = maxHeap.poll();
        }
        return result;
    }

    public static void main(String[] args) {
        var obj = new KClosestPointsToOrigin();
        var result = obj.kClosest(new int[][]{{1, 3}, {-2, 2}, {5, 8}, {0, 1}}, 2); // Output: [[-2,2],[1,3]]
        for (var point: result) {
            System.out.println(point[0] + " " + point[1]);
        }
    }
}
