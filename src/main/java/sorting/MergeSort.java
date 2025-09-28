package sorting;

import metrics.MetricsRecorder;

public class MergeSort {
    private static final int CUTOFF = 16;

    public static MetricsRecorder sort(int[] arr) {
        MetricsRecorder metrics = new MetricsRecorder();
        if (arr == null || arr.length < 2) return metrics;

        int[] buffer = new int[arr.length]; // Аллокация памяти для временного массива
        metrics.addAllocationCounter(); // Увеличиваем счетчик один раз, когда создаем buffer

        mergeSort(arr, buffer, 0, arr.length - 1, metrics);

        return metrics;
    }


    private static void mergeSort(int[] arr, int[] buffer, int left, int right, MetricsRecorder metrics) {
        if (right - left <= CUTOFF) {
            insertionSort(arr, left, right, metrics);
            return;
        }

        int mid = left + (right - left) / 2;

        metrics.addRecursionDepth(); // Увеличиваем глубину рекурсии

        mergeSort(arr, buffer, left, mid, metrics);
        mergeSort(arr, buffer, mid + 1, right, metrics);
        metrics.decreaseRecursionDepth(); // Уменьшаем глубину рекурсии

        merge(arr, buffer, left, mid, right, metrics);
    }

    private static void merge(int[] arr, int[] buffer, int left, int mid, int right, MetricsRecorder metrics) {
        System.arraycopy(arr, left, buffer, left, right - left + 1);

        int i = left;
        int j = mid + 1;
        int k = left;

        while (i <= mid && j <= right) {
            metrics.addComparisonCounter();
            if (buffer[i] <= buffer[j]) {
                arr[k++] = buffer[i++];
            } else {
                arr[k++] = buffer[j++];
            }
        }

        while (i <= mid) {
            arr[k++] = buffer[i++];
        }

        while (j <= right) {
            arr[k++] = buffer[j++];
        }
    }


    private static void insertionSort(int[] arr, int left, int right, MetricsRecorder metrics) {
        for (int i = left + 1; i <= right; i++) {
            int key = arr[i];
            int j = i - 1;

            while (j >= left && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
                metrics.addComparisonCounter(); // Считаем каждое сравнение
            }
            arr[j + 1] = key;
        }
    }
}
