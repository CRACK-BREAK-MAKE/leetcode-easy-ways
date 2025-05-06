package com.crack.snap.make.hard.priorityqueue;

import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * @author Mohan Sharma
 */
public class FindMedianFromDataStreamOptimal {

    private final PriorityQueue<Integer> minHeap;
    private final PriorityQueue<Integer> maxHeap;
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
     * Intuition: Let's create two heaps, one to keep track of the smaller half of the numbers (maxHeap)
     * and one to keep track of the larger half (minHeap).
     * Example: arr= [1, 2, 3, 4, 5] maxHeap = [3, 2, 1], minHeap = [4, 5]
     * So median is maxHeap.peek() = 3 if arr = [1, 2, 3, 4, 5, 6], maxHeap = [3, 2, 1], minHeap = [4, 5, 6]
     * So median is (maxHeap.peek() + minHeap.peek()) / 2 = (3 + 4) / 2 = 3.5
     *
     * While adding elements if size of one heap is greater than the other heap by more than 1, we need to balance them by
     * moving the top element from one heap and adding it to the other heap.
     */
    public FindMedianFromDataStreamOptimal() {
        minHeap = new PriorityQueue<>();
        maxHeap = new PriorityQueue<>((a, b) -> Integer.compare(b, a));
    }

    /**
     * Why maxHeap.size() > minHeap.size() + 1 instead of maxHeap.size() > minHeap.size() even though both works?
     *
     * The key difference is:
     * With maxHeap.size() > minHeap.size() + 1:
     * For odd count: maxHeap has exactly one more element
     * For even count: both heaps have equal elements
     * Median is either maxHeap.peek() (odd) or average of both tops (even)
     *
     * With just maxHeap.size() > minHeap.size():
     *
     * The heaps constantly shuffle elements back and forth, creating unnecessary operations
     * The balancing would allow maxHeap to have either the same number or one more element than minHeap
     * Median calculation would be more complex
     *
     * Why Both "Work" But Are Different
     * Both approaches will eventually give you the correct median, but:
     *
     * The first approach (+1) maintains a clearer invariant: maxHeap has either the same size as minHeap (even total) or exactly
     * one more element (odd total). The simpler approach (without +1) causes more element transfers between heaps, and makes
     * the median calculation logic less clear.If you run through the complete example, the final state will be different between
     * the two approaches, but both would correctly compute the median. The +1 approach is more efficient and maintains a
     * clearer state.
     */
    public void addNum(int num) {
        if (maxHeap.isEmpty() || num <= maxHeap.peek()) {
            maxHeap.add(num);
        } else {
            minHeap.add(num);
        }
        // Balance the heaps
        while (maxHeap.size() > minHeap.size() + 1) {
            minHeap.add(maxHeap.poll());
        }
        while (minHeap.size() > maxHeap.size()) {
            maxHeap.add(minHeap.poll());
        }
        size++;
    }

    public double findMedian() {
        if (size == 0 || maxHeap.isEmpty()) return 0.0;
        if (size % 2 == 0) {
            if (minHeap.isEmpty()) return 0.0;
            return (maxHeap.peek() + minHeap.peek()) / 2.0;
        } else {
            return maxHeap.peek();
        }
    }

    public static void main(String[] args) {
        var obj = new FindMedianFromDataStreamOptimal();
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
