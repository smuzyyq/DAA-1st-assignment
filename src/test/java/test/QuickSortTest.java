package test;

import metrics.MetricsRecorder;
import sorting.QuickSort;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class QuickSortTest {
    private static final String METRICS = "metrics.csv";


    @ParameterizedTest
    @ValueSource(ints = {0, 1, 10, 50, 100, 500, 1000, 2000})
    public void testLargeArraySorting(int size) throws IOException {

        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = (int) (Math.random() * 10000);
        }

        long startTime = System.nanoTime();

        MetricsRecorder metrics = QuickSort.sort(arr);

        long endTime = System.nanoTime();
        long duration = endTime - startTime;

        System.out.println("Sorting " + size + " elements took: " + duration + " nanoseconds.");

        metrics.writeMetricsToCSV(duration/ 10000, "QuickSort with size " + size);

        for (int i = 1; i < arr.length; i++) {
            assertTrue(arr[i - 1] <= arr[i], "Array should be sorted in ascending order.");
        }

        System.out.println("Time taken to sort array of size " + size + ": " + duration + " nanoseconds.");
        System.out.println("Number of comparisons: " + metrics.getComparisonCount());
    }

    @Test
    public void testEmptyArray() {
        int[] arr = {};
        long startTime = System.nanoTime();

        MetricsRecorder metrics = QuickSort.sort(arr);

        long endTime = System.nanoTime();
        long duration = endTime - startTime;

        assertArrayEquals(new int[]{}, arr, "Empty array should remain empty after sorting.");
        assertEquals(0, metrics.getComparisonCount(), "No comparisons should be made for an empty array.");

        System.out.println("Empty array sorting took: " + duration + " nanoseconds.");
        System.out.println("Number of comparisons for empty array: " + metrics.getComparisonCount());
    }

    @Test
    public void testSingleElementArray() {
        int[] arr = {42};
        long startTime = System.nanoTime();

        MetricsRecorder metrics = QuickSort.sort(arr);

        long endTime = System.nanoTime();
        long duration = endTime - startTime;

        assertArrayEquals(new int[]{42}, arr, "Array with a single element should remain the same.");
        assertEquals(0, metrics.getComparisonCount(), "No comparisons should be made for an array with a single element.");

        System.out.println("Single element array sorting took: " + duration + " nanoseconds.");
        System.out.println("Number of comparisons for single element array: " + metrics.getComparisonCount());
    }

    @Test
    public void testAlreadySortedArray() {
        int[] arr = {1, 2, 3, 4, 5};
        long startTime = System.nanoTime();

        MetricsRecorder metrics = QuickSort.sort(arr);

        long endTime = System.nanoTime();
        long duration = endTime - startTime;

        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, arr, "Array that is already sorted should remain the same.");
        assertTrue(metrics.getComparisonCount() > 0, "There should be comparisons even for already sorted arrays.");

        System.out.println("Already sorted array sorting took: " + duration + " nanoseconds.");
        System.out.println("Number of comparisons for already sorted array: " + metrics.getComparisonCount());
    }

    @Test
    public void testArrayWithDuplicates() {
        int[] arr = {5, 5, 5, 5, 5};
        long startTime = System.nanoTime();

        MetricsRecorder metrics = QuickSort.sort(arr);

        long endTime = System.nanoTime();
        long duration = endTime - startTime;

        assertArrayEquals(new int[]{5, 5, 5, 5, 5}, arr, "Array with duplicate elements should remain the same.");
        assertEquals(10, metrics.getComparisonCount(), "There should be comparisons even for arrays with duplicates.");

        System.out.println("Array with duplicates sorting took: " + duration + " nanoseconds.");
        System.out.println("Number of comparisons for array with duplicates: " + metrics.getComparisonCount());
    }

    @Test
    public void testReverseSortedArray() {
        int[] arr = {5, 4, 3, 2, 1};
        long startTime = System.nanoTime();

        MetricsRecorder metrics = QuickSort.sort(arr);

        long endTime = System.nanoTime();
        long duration = endTime - startTime;

        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, arr, "Array in reverse order should be sorted in ascending order.");
        assertTrue(metrics.getComparisonCount() > 0, "There should be comparisons for reverse sorted arrays.");

        System.out.println("Reverse sorted array sorting took: " + duration + " nanoseconds.");
        System.out.println("Number of comparisons for reverse sorted array: " + metrics.getComparisonCount());
    }

    @Test
    public void testArrayWithNegativeAndPositiveValues() {
        int[] arr = {-10, 2, -5, 0, 8};
        long startTime = System.nanoTime();

        MetricsRecorder metrics = QuickSort.sort(arr);

        long endTime = System.nanoTime();
        long duration = endTime - startTime;

        assertArrayEquals(new int[]{-10, -5, 0, 2, 8}, arr, "Array with both negative and positive numbers should be sorted.");
        assertTrue(metrics.getComparisonCount() > 0, "There should be comparisons for arrays with both negative and positive numbers.");

        System.out.println("Array with negative and positive numbers sorting took: " + duration + " nanoseconds.");
        System.out.println("Number of comparisons for array with negative and positive numbers: " + metrics.getComparisonCount());
    }
}
