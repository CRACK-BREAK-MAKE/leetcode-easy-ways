package com.crack.snap.make.hard.priorityqueue;

import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * @author Mohan Sharma
 */
public class FindMedianFromDataStream {

    private final PriorityQueue<Integer> minHeap;
    private int size = 0;

    /**
     * Problem:
     * The median is the middle value in an ordered integer list. If the size of the list is even, there is no middle value, and
     * the median is the mean of the two middle values.
     * For example, for arr = [2,3,4], the median is 3.
     * For example, for arr = [2,3], the median is (2 + 3) / 2 = 2.5.
     * Implement the MedianFinder class:
     * MedianFinder() initializes the MedianFinder object.
     * void addNum(int num) adds the integer num from the data stream to the data structure.
     * double findMedian() returns the median of all elements so far. Answers within 10-5 of the actual answer will be accepted.
     *
     * Intuition: In a naive approach, I can use a min heap or max heap to keep the elements in sorted order. Also as we add
     * element I can keep track of the size. If the size is even I can pop the elements till the n/2 and n-1/2 and return the average
     * While I pop I keep the elements in an array and put them back in the heap.
     */
    public FindMedianFromDataStream() {
        minHeap = new PriorityQueue<>();
    }

    public void addNum(int num) {
        minHeap.add(num);
        size++;
    }

    public double findMedian() {
        if (size == 0) return 0.0;

        var backup = new ArrayList<Integer>();
        var elementsToRemove = size / 2 + 1;

        for (int i = 0; i < elementsToRemove; i++) {
            backup.add(minHeap.poll());
        }

        double median;
        if (size % 2 == 0) {
            median = (backup.get(backup.size() - 1) + backup.get(backup.size() - 2)) / 2.0;
        } else {
            median = backup.get(backup.size() - 1);
        }

        minHeap.addAll(backup);
        return median;
    }

    public static void main(String[] args) {
        var obj = new FindMedianFromDataStream();
        obj.addNum(1);
        System.out.println(obj.findMedian()); // Output: 1.0
        obj.addNum(2);
        System.out.println(obj.findMedian()); // Output: 1.5
        obj.addNum(3);
        System.out.println(obj.findMedian()); // Output: 2.0
        obj.addNum(4);
        System.out.println(obj.findMedian()); // Output: 2.5
        obj.addNum(5);
        System.out.println(obj.findMedian()); // Output: 3.0
        obj.addNum(6);
        System.out.println(obj.findMedian()); // Output: 3.5
    }
}
