package sorting;

import java.util.Arrays;

public class MergeSort {

    private static final int CUTOFF = 16;

    public static int[] mergeSort(int[] arr) {
        if (arr.length <= CUTOFF) {
            return insertionSort(arr);
        }
        return mergeSortRecursive(arr);
    }

    private static int[] mergeSortRecursive(int[] arr) {
        if (arr.length <= 1) return arr;
        int mid = arr.length / 2;
        int[] left = mergeSortRecursive(Arrays.copyOfRange(arr, 0, mid));
        int[] right = mergeSortRecursive(Arrays.copyOfRange(arr, mid, arr.length));
        return merge(left, right);
    }

    private static int[] merge(int[] left, int[] right) {
        int[] result = new int[left.length + right.length];
        int i = 0, j = 0, k = 0;

        while (i < left.length && j < right.length) {
            if (left[i] <= right[j]) {
                result[k++] = left[i++];
            } else {
                result[k++] = right[j++];
            }
        }

        while (i < left.length) result[k++] = left[i++];
        while (j < right.length) result[k++] = right[j++];

        return result;
    }

    private static int[] insertionSort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int key = arr[i], j = i - 1;
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j--];
            }
            arr[j + 1] = key;
        }
        return arr;
    }
}
