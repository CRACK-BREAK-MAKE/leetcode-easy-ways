package com.crack.snap.make.hard.binarysearch;

/**
 * @author Mohan Sharma
 */
public class MedianTwoSortedArray {

    /**
     * Problem: Given two sorted arrays nums1 and nums2 of size m and n respectively, return the median of the two sorted arrays.
     * The overall run time complexity should be O(log (m+n)).
     *
     * Intuition: If we think about brute force approach, we can merge the two arrays and then find the median.
     * Time Complexity is O(m+n) and Space Complexity is O(m+n).
     *
     * @param nums1
     * @param nums2
     * @return
     */
    public double findMedianSortedArraysMergeSort(int[] nums1, int[] nums2) {
        if (nums1 == null || nums1.length == 0 || nums2 == null || nums2.length == 0) {
            return 0;
        }
        var i = 0;
        var j = 0;
        var k = 0;
        var n = nums1.length;
        var m = nums2.length;
        var totalLength = n + m;
        var mergedArray = new int[nums1.length + nums2.length];
        // Merge the two arrays
        while (i < n && j < m) {
            if (nums1[i] <= nums2[j]) {
                mergedArray[k++] = nums1[i++];
            } else {
                mergedArray[k++] = nums2[j++];
            }
        }
        // If there are remaining elements in nums1
        while (i < n) {
            mergedArray[k++] = nums1[i++];
        }
        // If there are remaining elements in nums2
        while (j < m) {
            mergedArray[k++] = nums2[j++];
        }
        // Find the median
        if (totalLength % 2 == 0) {
            return (mergedArray[totalLength / 2] + mergedArray[totalLength / 2 - 1]) / 2.0;
        } else {
            return mergedArray[totalLength / 2];
        }
    }

    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        if (nums1.length == 0 && nums2.length == 0) {
            return 0;
        }
        if (nums1.length > nums2.length) {
            return findMedianSortedArrays(nums2, nums1); // call the same function but pass the small array first
        }
        var start = 0;
        var end = nums1.length;
        var totalLength = nums1.length + nums2.length;

        while (start <= end) {
            var partitionX = (start + end) / 2;
            var partitionY = (totalLength + 1) / 2 - partitionX;
            var maxX = partitionX == 0 ? Integer.MIN_VALUE : nums1[partitionX - 1];
            var minX = partitionX == nums1.length ? Integer.MAX_VALUE : nums1[partitionX];
            var maxY = partitionY == 0 ? Integer.MIN_VALUE : nums2[partitionY - 1];
            var minY = partitionY == nums2.length ? Integer.MAX_VALUE : nums2[partitionY];

            if (maxX <= minY && maxY <= minX) {
                if (totalLength % 2 == 0) {
                    return (Math.max(maxX, maxY) + Math.min(minX, minY)) / 2.0;
                } else {
                    return Math.max(maxX, maxY);
                }
            } else if (maxX > minY) {
                end = partitionX - 1;
            } else {
                start = partitionX + 1;
            }
        }
        throw new IllegalArgumentException("Input arrays are not sorted");
    }

    public static void main(String[] args) {
        System.out.println(new MedianTwoSortedArray().findMedianSortedArrays(new int[]{1, 3}, new int[]{2})); // 2.0
        System.out.println(new MedianTwoSortedArray().findMedianSortedArrays(new int[]{1, 2}, new int[]{3, 4})); // 2.5
    }
}
