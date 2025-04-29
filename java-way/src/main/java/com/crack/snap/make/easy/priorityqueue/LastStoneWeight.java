package com.crack.snap.make.easy.priorityqueue;

import java.util.PriorityQueue;

/**
 * @author Mohan Sharma
 */
public class LastStoneWeight {

    /**
     * Problem: You are given an array of integers stones where stones[i] is the weight of the ith stone.
     * We are playing a game with the stones. On each turn, we choose the heaviest two stones and smash them together.
     * Suppose the heaviest two stones have weights x and y with x <= y. The result of this smash is:
     * If x == y, both stones are destroyed, and
     * If x != y, the stone of weight x is destroyed, and the stone of weight y has new weight y - x.
     * At the end of the game, there is at most one stone left.
     * Return the weight of the last remaining stone. If there are no stones left, return 0.
     *
     * Intuition: Since we need to get the heaviest two stones, we can use a max heap. While size > 1 get the heaviest two stones
     * and apply the rule
     */
    public int lastStoneWeight(int[] stones) {
        var queue = new PriorityQueue<Integer>((a, b) -> b - a);
        for (var stone : stones) {
            queue.add(stone);
        }
        while (queue.size() > 1) {
            var first = queue.poll();
            var second = queue.poll();
            if (first != second) {
                queue.add(first - second);
            }
        }
        return queue.isEmpty() ? 0 : queue.poll();
    }

    public static void main(String[] args) {
        var obj = new LastStoneWeight();
        System.out.println(obj.lastStoneWeight(new int[]{2, 7, 4, 1, 8, 1})); // Output: 1
        System.out.println(obj.lastStoneWeight(new int[]{1, 3})); // Output: 2

    }
}
