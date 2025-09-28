package sorting;

import metrics.MetricsRecorder;

import java.util.Arrays;

public class DeterministicSelect {

    public static MetricsRecorder select(int[] arr, int k) {
        MetricsRecorder metrics = new MetricsRecorder();
        if (arr == null || arr.length == 0 || k < 1 || k > arr.length) return metrics;

        deterministicSelect(arr, 0, arr.length - 1, k - 1, metrics);
        return metrics;
    }

    private static int deterministicSelect(int[] arr, int left, int right, int k, MetricsRecorder metrics) {
        metrics.addAllocationCounter();
        metrics.addRecursionDepth();

        while (left <= right) {
            int pivotIndex = medianOfMedians(arr, left, right, metrics);

            pivotIndex = partition(arr, left, right, pivotIndex, metrics);

            if (k == pivotIndex) {
                metrics.decreaseRecursionDepth();
                return arr[k];
            } else if (k < pivotIndex) {
                right = pivotIndex - 1; // идем в левую часть
            } else {
                left = pivotIndex + 1; // идем в правую часть
            }
        }

        metrics.decreaseRecursionDepth();
        return -1;
    }

    private static int partition(int[] arr, int left, int right, int pivotIndex, MetricsRecorder metrics) {
        int pivotValue = arr[pivotIndex];
        swap(arr, pivotIndex, right, metrics);
        int storeIndex = left;

        for (int i = left; i < right; i++) {
            metrics.addComparisonCounter();
            if (arr[i] < pivotValue) {
                swap(arr, storeIndex, i, metrics);
                storeIndex++;
            }
        }

        swap(arr, storeIndex, right, metrics);
        return storeIndex;
    }

    private static int medianOfMedians(int[] arr, int left, int right, MetricsRecorder metrics) {
        int n = right - left + 1;
        if (n <= 5) {
            Arrays.sort(arr, left, right + 1);
            return left + n / 2; // возвращаем индекс медианы
        }

        int numMedians = 0;
        for (int i = left; i <= right; i += 5) {
            int subRight = Math.min(i + 4, right);
            Arrays.sort(arr, i, subRight + 1);
            int medianIndex = i + (subRight - i) / 2;
            swap(arr, left + numMedians, medianIndex, metrics); // переносим медиану в начало
            numMedians++;
        }

        return deterministicSelectIndex(arr, left, left + numMedians - 1, numMedians / 2, metrics);
    }

    private static int deterministicSelectIndex(int[] arr, int left, int right, int k, MetricsRecorder metrics) {
        metrics.addAllocationCounter();
        metrics.addRecursionDepth();

        while (left <= right) {
            int pivotIndex = medianOfMedians(arr, left, right, metrics);
            pivotIndex = partition(arr, left, right, pivotIndex, metrics);

            if (k + left == pivotIndex) {
                metrics.decreaseRecursionDepth();
                return pivotIndex; // возвращаем индекс pivot-а
            } else if (k + left < pivotIndex) {
                right = pivotIndex - 1;
            } else {
                k = k - (pivotIndex - left + 1);
                left = pivotIndex + 1;
            }
        }

        metrics.decreaseRecursionDepth();
        return -1;
    }



    private static void swap(int[] arr, int i, int j, MetricsRecorder metrics) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
        metrics.addAllocationCounter();
    }
}
