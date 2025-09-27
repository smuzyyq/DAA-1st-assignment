package test;

import org.junit.jupiter.api.Test;
import sorting.MergeSort;
import metrics.MetricsRecorder;

import java.io.IOException;
import java.util.Arrays;

public class MergeSortTest {

    @Test
    public void testEmptyArray() throws IOException {
        testArray("Empty array", new int[0]);
    }

    @Test
    public void testSingleElement() throws IOException {
        testArray("Single element array", new int[]{5});
    }

    @Test
    public void testArrayWithDuplicates() throws IOException {
        testArray("Array with duplicates", new int[]{5, 5, 5, 5, 5});
    }

    @Test
    public void testAlreadySortedArray() throws IOException {
        testArray("Already sorted array", new int[]{1, 2, 3, 4, 5});
    }

    @Test
    public void testSmallArray() throws IOException {
        testArray("100 elements array", generateRandomArray(100));
    }

    @Test
    public void testMediumArray() throws IOException {
        testArray("500 elements array", generateRandomArray(500));
    }

    @Test
    public void testLargeArray() throws IOException {
        testArray("1000 elements array", generateRandomArray(1000));
    }

    @Test
    public void testVeryLargeArray() throws IOException {
        testArray("10000 elements array", generateRandomArray(10000));
    }

    private void testArray(String arrayType, int[] arr) throws IOException {
        MetricsRecorder recorder = new MetricsRecorder("metrics.csv");

        long startTime = System.nanoTime();
        MergeSort.mergeSort(arr);
        long endTime = System.nanoTime();

        // Запись метрик в CSV
        recorder.recordMetrics("MergeSort", arrayType, arr.length, (endTime - startTime));
        recorder.close();

        System.out.println(arrayType + " sorted in: " + (endTime - startTime) + " ns");
    }

    private int[] generateRandomArray(int size) {
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = (int) (Math.random() * 1000);
        }
        return arr;
    }
}
