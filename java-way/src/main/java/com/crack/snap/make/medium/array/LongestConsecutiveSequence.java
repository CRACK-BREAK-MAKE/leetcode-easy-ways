package com.crack.snap.make.medium.array;

import java.util.Arrays;
import java.util.HashSet;

/**
 * @author Mohan Sharma
 */
public class LongestConsecutiveSequence {

    // Given an unsorted array of integers nums, return the length of the longest consecutive elements sequence.
    // example input [100,4,200,1,3,2] output 4 why because the longest consecutive elements sequence is [1,2,3,4] its length is 4
    // Intuition
    // Naive Approach: sort the array and have a max variable to keep track of the longest consecutive sequence.
    // Have a pointer scan left and increment counter if adjacent is the next consecutive element. If not, move the pointer to the current element.
    public int longestConsecutiveNlogN(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }
        Arrays.sort(nums);
        var maxCount = 1;
        var currentCount = 1;
        var leftPointer = 1;
        while (leftPointer < nums.length) {
            // Skip duplicates
            if (nums[leftPointer] == nums[leftPointer - 1]) {
                leftPointer++;
                continue;
            }
            if (nums[leftPointer] == nums[leftPointer - 1] + 1) {
                currentCount++;
                maxCount = Math.max(maxCount, currentCount);
            } else {
                currentCount = 1;
            }
            leftPointer++;
        }
        return maxCount;
    }

    // Intuition:
    // Problem states we can use o(n) space meaning we can use either index based space or hash based space
    // Index based won't work since the input can be negative as well but there is no predefined numbers to use the number as index,
    // so we can use hash based space.
    // Idea is scan the array from left to right and try to find the smallest element in the sequence,
    // then use loop to go back until the largest in the sequence is found then do max number - current number and update global maxCount
    public int longestConsecutiveOneScan(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }
        int lcs = 0;
        var set = new HashSet<>(Arrays.stream(nums).boxed().toList());
        for (var num: set){
            if (!set.contains(num - 1)) {
                int currentNumber = num + 1;
                while (set.contains(currentNumber)){
                    currentNumber++;
                }
                lcs = Math.max(lcs, currentNumber - num);
            }
        }
        return lcs;
    }

    // Intuition:
    // In the above approach we scan from one end and go back to find the sequence, now if you think about it
    // what if I pick one element from the start of the set and go both ways until the sequence is broken
    // and to avoid redoing the same element again we can remove it from the set so that we don't visit it again while we scan the set again
    // then move to the next element and do the same, this way we can avoid the nested loop and do it in one scan.
    public int longestConsecutive(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }
        int lcs = 0;
        var set = new HashSet<>(Arrays.stream(nums).boxed().toList());
        while (!set.isEmpty()) {
            var leftPointer = set.iterator().next();
            var rightPointer = leftPointer + 1;
            var count = 0;
            while (set.remove(leftPointer--)) count++;
            while (set.remove(rightPointer++)) count++;
            lcs = Math.max(lcs, count);
        }
        return lcs;
    }

    public static void main(String[] args) {
        var longestConsecutiveSequence = new LongestConsecutiveSequence();
        System.out.println(longestConsecutiveSequence.longestConsecutiveOneScan(new int[]{0,3,7,2,5,8,4,6,0,1}));
    }
}
