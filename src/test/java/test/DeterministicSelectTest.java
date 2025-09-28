package test;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import sorting.DeterministicSelect;
import metrics.MetricsRecorder;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;


class DeterministicSelectTest {
    private static final String METRICS = "metrics.csv";

    @BeforeAll
    static void clearMetricsFile() {
        try {
            Files.deleteIfExists(Paths.get(METRICS));
        } catch (IOException e) {
            System.err.println("Error while trying to delete metrics file: " + e.getMessage());
        }
    }

    @BeforeAll
    static void warmUpJvm() {
        Random random = new Random();
        for (int i = 0; i < 10000; i++) {
            int[] dummyArray = new int[1];
            dummyArray[0] = random.nextInt();
            DeterministicSelect.select(dummyArray, 1);
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 10, 50, 100, 500, 1000, 2000})
    public void testRandomArrays(int size) throws IOException {
        int[] arr = new int[size];
        Random rand = new Random();
        for (int i = 0; i < size; i++) {
            arr[i] = rand.nextInt(10000);
        }

        long startTime = System.nanoTime();

        // k — медиана
        int k = size / 2;
        MetricsRecorder metrics = DeterministicSelect.select(arr, k);

        long endTime = System.nanoTime();
        long duration = endTime - startTime;

        System.out.println("DeterministicSelect on size " + size + " took: " + duration + " ns");
        metrics.writeMetricsToCSV(duration / 10000, "DeterministicSelect with size " + size);
        System.out.println("Number of comparisons: " + metrics.getComparisonCount());
    }

    @Test
    public void testEmptyArray() throws IOException {
        int[] arr = {};
        long startTime = System.nanoTime();
        MetricsRecorder metrics = DeterministicSelect.select(arr, 1);
        long duration = System.nanoTime() - startTime;

        System.out.println("Empty array selection took: " + duration + " ns");
        System.out.println("Number of comparisons for empty array: " + metrics.getComparisonCount());
    }

    @Test
    public void testSingleElement() throws IOException {
        int[] arr = {42};
        long startTime = System.nanoTime();
        MetricsRecorder metrics = DeterministicSelect.select(arr, 1);
        long duration = System.nanoTime() - startTime;

        System.out.println("Single element selection took: " + duration + " ns");
        System.out.println("Number of comparisons for single element: " + metrics.getComparisonCount());
    }

    @Test
    public void testSortedArray() throws IOException {
        int[] arr = {1, 2, 3, 4, 5};
        long startTime = System.nanoTime();
        MetricsRecorder metrics = DeterministicSelect.select(arr, 3);
        long duration = System.nanoTime() - startTime;

        System.out.println("Sorted array selection took: " + duration + " ns");
        System.out.println("Number of comparisons for sorted array: " + metrics.getComparisonCount());
    }

    @Test
    public void testArrayWithDuplicates() throws IOException {
        int[] arr = {5, 5, 5, 5, 5};
        long startTime = System.nanoTime();
        MetricsRecorder metrics = DeterministicSelect.select(arr, 3);
        long duration = System.nanoTime() - startTime;

        System.out.println("Array with duplicates selection took: " + duration + " ns");
        System.out.println("Number of comparisons for array with duplicates: " + metrics.getComparisonCount());
    }
}
