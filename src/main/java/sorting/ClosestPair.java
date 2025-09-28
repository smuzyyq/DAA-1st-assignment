package sorting;

import metrics.MetricsRecorder;
import java.util.Arrays;
import java.util.Comparator;

public class ClosestPair {

    public static class Point {
        public double x, y;
        public Point(double x, double y) { this.x = x; this.y = y; }
    }

    public static MetricsRecorder findClosest(Point[] points) {
        MetricsRecorder metrics = new MetricsRecorder();
        if (points == null || points.length < 2) return metrics;

        Point[] pointsByX = points.clone();
        Arrays.sort(pointsByX, Comparator.comparingDouble(p -> p.x));

        closestPairRecursive(pointsByX, metrics);

        return metrics;
    }

    private static double closestPairRecursive(Point[] pointsByX, MetricsRecorder metrics) {
        int n = pointsByX.length;

        if (n <= 3) {
            double minDist = Double.POSITIVE_INFINITY;
            for (int i = 0; i < n; i++) {
                for (int j = i + 1; j < n; j++) {
                    metrics.addComparisonCounter();
                    minDist = Math.min(minDist, distance(pointsByX[i], pointsByX[j]));
                    metrics.addAllocationCounter();
                }
            }
            return minDist;
        }

        metrics.addRecursionDepth();

        int mid = n / 2;
        Point midPoint = pointsByX[mid];

        Point[] left = Arrays.copyOfRange(pointsByX, 0, mid);
        Point[] right = Arrays.copyOfRange(pointsByX, mid, n);
        metrics.addAllocationCounter();

        double dl = closestPairRecursive(left, metrics);
        double dr = closestPairRecursive(right, metrics);
        double d = Math.min(dl, dr);

        double delta = d;
        Point[] strip = Arrays.stream(pointsByX)
                .filter(p -> Math.abs(p.x - midPoint.x) < delta)
                .toArray(Point[]::new);

        metrics.addAllocationCounter();

        Arrays.sort(strip, Comparator.comparingDouble(p -> p.y));

        for (int i = 0; i < strip.length; i++) {
            for (int j = i + 1; j < strip.length && (strip[j].y - strip[i].y) < d; j++) {
                metrics.addComparisonCounter();
                d = Math.min(d, distance(strip[i], strip[j]));
                metrics.addAllocationCounter();
            }
        }

        metrics.decreaseRecursionDepth();
        return d;
    }

    private static double distance(Point p1, Point p2) {
        double dx = p1.x - p2.x;
        double dy = p1.y - p2.y;
        return Math.sqrt(dx * dx + dy * dy);
    }
}
