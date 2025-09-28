package sorting;

import metrics.MetricsRecorder;

import java.util.Random;

public class QuickSort {
    private static final Random RANDOM = new Random();

    public static MetricsRecorder sort(int[] arr) {
        MetricsRecorder metrics = new MetricsRecorder();
        if (arr != null && arr.length >= 2) {
            quickSort(arr, 0, arr.length - 1, metrics);
        }
        return metrics;
    }

    private static void quickSort(int[] arr, int low, int high, MetricsRecorder metrics) {
        metrics.addAllocationCounter();
        metrics.addRecursionDepth();

        while (low < high) {
            int pivotIndex = partition(arr, low, high, metrics);

            if (pivotIndex - low < high - pivotIndex) {
                quickSort(arr, low, pivotIndex - 1, metrics);
                low = pivotIndex + 1;
            } else {
                quickSort(arr, pivotIndex + 1, high, metrics);
                high = pivotIndex - 1;
            }
        }
        metrics.decreaseRecursionDepth();
    }

    private static int partition(int[] arr, int low, int high, MetricsRecorder metrics) {
        int randomIndex = low + RANDOM.nextInt(high - low + 1);
        swap(arr, randomIndex, high, metrics);

        int pivot = arr[high];
        int i = low - 1;

        for (int j = low; j < high; j++) {
            metrics.addComparisonCounter();
            if (arr[j] <= pivot) {
                i++;
                swap(arr, i, j, metrics);
            }
        }
        swap(arr, i + 1, high, metrics);
        return i + 1;
    }

    private static void swap(int[] arr, int i, int j, MetricsRecorder metrics) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
