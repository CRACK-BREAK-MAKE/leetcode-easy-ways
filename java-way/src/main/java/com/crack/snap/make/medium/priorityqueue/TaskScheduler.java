package com.crack.snap.make.medium.priorityqueue;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;

/**
 * @author Mohan Sharma
 */
public class TaskScheduler {

    /**
     * Problem: You are given an array of CPU tasks, each labeled with a letter from A to Z, and a number n. Each CPU interval can
     * be idle or allow the completion of one task. Tasks can be completed in any order, but there's a constraint: there has to be
     * a gap of at least n intervals between two tasks with the same label. Return the minimum number of CPU intervals required to
     * complete all tasks.
     *
     * Intuition: IN brute force approach, I need to process all the task one by one. While processing each task I will check if the
     * task is already processed or not and if it is eligible to be processed. The problem also requires that the max frequency
     * tasks are executed first
     *
     * First since we need to process all the tasks, we will use a loop to iterate for length of the tasks. Then We need to find
     * if a task is eligible or not. A task is eligible if: a. It is not already processed, which we can track using a map of
     * (task, count). b. It is not already processed and it overcame the n interval gap. c. Is it the max frequency task or not.
     *
     * Example: ["A","A","A", "B","B","B"], n = 3 count: {A: 3, B: 3}, next_available = {A: 0, B: 0}, processed = 0;
     * 1st Iteration: Both eligible, let's process anyone of them, let's say A then it becomes
     * count: {A: 2, B: 3}, next_available = {A: 4, B: 0}, time = 1 // next_available[A] = 0 + n + 1, processed = 1
     * 2nd Iteration: A is not eligible, it is available to process but it's next_available is when t = 4, so process B then it becomes
     * count: {A: 2, B: 2}, next_available = {A: 4, B: 4}, time = 2, processed = 2
     * 3rd Iteration: A is not eligible, B is not available, simply increment time for idle then it becomes
     * count: {A: 2, B: 2}, next_available = {A: 4, B: 4}, time = 3
     * 4th Iteration: A is not eligible, B is not available, simply increment time for idle then it becomes
     * count: {A: 2, B: 2}, next_available = {A: 4, B: 4}, time = 4
     * 5th Iteration: A is eligible, process A then it becomes
     * count: {A: 1, B: 2}, next_available = {A: 8, B: 4}, time = 5, processed = 3
     * 6th Iteration: A is not eligible, B is eligible, process B then it becomes
     * count: {A: 1, B: 1}, next_available = {A: 8, B: 8}, time = 6, processed = 4
     * 7th Iteration: A is not eligible, B is not available, simply increment time for idle then it becomes
     * count: {A: 1, B: 1}, next_available = {A: 8, B: 8}, time = 7
     * 8th Iteration: A is not eligible, B is not available, simply increment time for idle then it becomes
     * count: {A: 1, B: 1}, next_available = {A: 8, B: 8}, time = 8
     * 9th Iteration: A is eligible, process A then it becomes
     * count: {A: 0, B: 1}, next_available = {A: 12, B: 8}, time = 9, processed = 5
     * 10th Iteration: A is not eligible, B is eligible, process B then it becomes
     * count: {A: 0, B: 0}, next_available = {A: 12, B: 12}, time = 10, processed = 6 processed > length of tasks, so return time = 10
     */
    public int leastIntervalBruteForce(char[] tasks, int n) {
        if (tasks == null || tasks.length == 0) {
            return 0;
        }
        var count = new int[26];
        var nextAvailable = new int[26];
        for (var task : tasks) {
            count[task - 'A']++;
        }
        var time = 0;
        var processed = tasks.length;
        while (processed > 0) {
            int maxCount = 0;
            int taskToRun = -1;
            for (int i = 0; i < 26; i++) {
                int taskCount = count[i];
                if (taskCount > 0 && nextAvailable[i] <= time && taskCount > maxCount) {
                    maxCount = taskCount;
                    taskToRun = i;
                }
            }

            if (taskToRun != -1) {
                count[taskToRun]--;
                processed--;
                nextAvailable[taskToRun] = time + n + 1;
            }
            time++;
        }
        return time;
    }

    /**
     * Intuition: Example ["B", "C", "A", "A", "A"] n = 1
     * First we need to know how many count of each task so we can plan out idle time between two similar tasks
     * Let's say we don't worry about the higher count tasks and just process the tasks in any order then idle time will be more
     * e.g. if we process B -> C -> A -> idle -> A -> idle -> A then total time = 7 but, we if consider the higher count tasks first
     * then we can minimize the idle time e.g. A -> B -> A -> C -> A then total time = 5. That means we need to keep track of higher
     * frequency tasks first, for this we can use Max Heap.
     *
     * When a higher frequency task is executed, it need to cool down for idle time and will become eligible to process after
     * currentTime + n + 1. So we need to keep track of the next available task in a queue as well we can use either queue or linkedlist
     * After processing each task from the max heap, we increment the time but also we can check if the task in the queue is
     * eligible to process or not. If it is eligible then we can add it to the max heap and process it until both the max heap
     * and queue are empty.
     *
     */
    public int leastInterval(char[] tasks, int n) {
        if (tasks == null || tasks.length == 0) {
            return 0;
        }
        var time = 0;
        var frequencyMap = new HashMap<Character, Integer>();
        for (var taskName : tasks) {
            frequencyMap.compute(taskName, (k, v) -> v == null ? 1 : v + 1);
        }
        var maxHeap = new PriorityQueue<Integer>((a, b) -> b - a);
        maxHeap.addAll(frequencyMap.values());
        var coolDownList = new LinkedList<TaskRecord>();

        while (!maxHeap.isEmpty() || !coolDownList.isEmpty()) {
            if (!maxHeap.isEmpty()) {
                var taskCount = maxHeap.poll();
                taskCount--;
                if (taskCount > 0) {
                    coolDownList.add(new TaskRecord(time + n + 1, taskCount));
                }
            }
            time++;
            if (!coolDownList.isEmpty() && coolDownList.peekFirst().nextAvailability <= time) {
                maxHeap.add(coolDownList.poll().frequencyCount);
            }
        }
        return time;
    }

    record TaskRecord(int nextAvailability, int frequencyCount) {}

    public static void main(String[] args) {
        var obj = new TaskScheduler();
        System.out.println(obj.leastInterval(new char[]{'A', 'A', 'A', 'B', 'B', 'B'}, 2)); // Output: 8
        System.out.println(obj.leastInterval(new char[]{'A', 'A', 'A', 'B', 'B', 'C', 'C'}, 1)); // Output: 7
        System.out.println(obj.leastInterval(new char[]{'A', 'A', 'A', 'B', 'B', 'B'}, 3)); // Output: 10
        System.out.println(obj.leastInterval(new char[]{'A', 'A', 'A', 'B', 'B', 'B', 'C', 'C', 'C', 'D', 'D', 'E'}, 2)); // Output: 12
        System.out.println(obj.leastInterval(new char[]{'B','C','D','A','A','A','A','G'}, 1)); // Output: 8
    }
}
