package com.crack.snap.make.easy.array;

import java.util.Arrays;
import java.util.HashSet;

/**
 * @author Mohan Sharma
 */
public class DuplicateFinder {

    public boolean containsDuplicateOn2(int[] nums) {
        if (nums == null || nums.length == 0) {
            return false;
        }
        for (int i=0; i < nums.length; i++) {
            for (int j = i+1; j < nums.length; j++) {
                if (nums[i] ==  nums[j])
                    return true;
            }
        }
        return false;
    }

    public boolean containsDuplicateHashing(int[] nums) {
        if (nums == null || nums.length == 0) {
            return false;
        }
        var set = new HashSet<>();
        for (int num: nums) {
            if (!set.add(num)) {
                return true;
            }
        }
        return false;
    }

    public boolean containsDuplicateSort(int[] nums) {
        if (nums == null || nums.length == 0) {
            return false;
        }
        for (int i = 1; i < nums.length; i++) {
            int j = i - 1;
            int key = nums[i];
            while (j >= 0 && nums[j] > key) {
                nums[j + 1] = nums [j];
                j--;
            }
            nums[j + 1] = key;
        }
        for (int i = 1; i < nums.length; i++) {
            if (nums[i-1] ==  nums[i]) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        DuplicateFinder duplicateFinder = new DuplicateFinder();
        System.out.println(duplicateFinder.containsDuplicateSort(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}));
        System.out.println(duplicateFinder.containsDuplicateSort(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 1}));
    }
}
