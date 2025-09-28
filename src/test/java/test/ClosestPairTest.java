package test;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import sorting.ClosestPair;
import sorting.ClosestPair.Point;
import metrics.MetricsRecorder;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

class ClosestPairTest {

    private static final String METRICS = "metrics.csv";


    @ParameterizedTest
    @ValueSource(ints = {1, 10, 50, 100, 500, 1000, 2000})
    void testRandomPoints(int size) throws IOException {
        Random rand = new Random();
        Point[] points = new Point[size];
        for (int i = 0; i < size; i++) {
            points[i] = new Point(rand.nextDouble() * 10000, rand.nextDouble() * 10000);
        }

        long startTime = System.nanoTime();
        MetricsRecorder metrics = ClosestPair.findClosest(points);
        long endTime = System.nanoTime();
        long duration = endTime - startTime;

        metrics.writeMetricsToCSV(duration / 10000, "ClosestPair with size " + size);

        System.out.println("ClosestPair on size " + size + " took: " + duration + " ns");
    }

    @Test
    void testEmptyArray() {
        Point[] points = {};
        long startTime = System.nanoTime();
        MetricsRecorder metrics = ClosestPair.findClosest(points);
        long duration = System.nanoTime() - startTime;

        System.out.println("Empty array selection took: " + duration + " ns");
        System.out.println("Number of comparisons for empty array: " + metrics.getComparisonCount());
    }

    @Test
    void testSingleElement() {
        Point[] points = {new Point(42, 42)};
        long startTime = System.nanoTime();
        MetricsRecorder metrics = ClosestPair.findClosest(points);
        long duration = System.nanoTime() - startTime;

        System.out.println("Single element selection took: " + duration + " ns");
        System.out.println("Number of comparisons for single element: " + metrics.getComparisonCount());
    }

    @Test
    void testSortedArray() {
        Point[] points = {new Point(1,1), new Point(2,2), new Point(3,3), new Point(4,4), new Point(5,5)};
        long startTime = System.nanoTime();
        MetricsRecorder metrics = ClosestPair.findClosest(points);
        long duration = System.nanoTime() - startTime;

        System.out.println("Sorted array selection took: " + duration + " ns");
        System.out.println("Number of comparisons for sorted array: " + metrics.getComparisonCount());
    }

    @Test
    void testArrayWithDuplicates() {
        Point[] points = {new Point(5,5), new Point(5,5), new Point(5,5), new Point(5,5), new Point(5,5)};
        long startTime = System.nanoTime();
        MetricsRecorder metrics = ClosestPair.findClosest(points);
        long duration = System.nanoTime() - startTime;

        System.out.println("Array with duplicates selection took: " + duration + " ns");
        System.out.println("Number of comparisons for array with duplicates: " + metrics.getComparisonCount());
    }
}
