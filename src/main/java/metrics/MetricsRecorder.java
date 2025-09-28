package metrics;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.atomic.AtomicInteger;

public class MetricsRecorder {
    private final AtomicInteger comparisonCounter = new AtomicInteger(0);
    private final AtomicInteger allocationCounter = new AtomicInteger(0);
    private final AtomicInteger currentRecursionDepth = new AtomicInteger(0);
    private final AtomicInteger maxRecursionDepth = new AtomicInteger(0);

    public void addComparisonCounter() { comparisonCounter.incrementAndGet(); }
    public void addAllocationCounter() { allocationCounter.incrementAndGet(); }
    public void addRecursionDepth() {
        int depth = currentRecursionDepth.incrementAndGet();
        maxRecursionDepth.updateAndGet(max -> Math.max(max, depth));
    }
    public void decreaseRecursionDepth() { currentRecursionDepth.decrementAndGet(); }

    public void reset() {
        comparisonCounter.set(0);
        allocationCounter.set(0);
        currentRecursionDepth.set(0);
        maxRecursionDepth.set(0);
    }

    public int getComparisonCount() { return comparisonCounter.get(); }

    public void writeMetricsToCSV(long timeTaken, String algorithmName) throws IOException {
        File file = new File("metrics.csv");
        boolean fileExists = file.exists();

        try (FileWriter fileWriter = new FileWriter(file, true);
             PrintWriter printWriter = new PrintWriter(fileWriter)) {

            if (!fileExists) {
                printWriter.println("Algorithm, Time Taken (ns), Comparisons, Allocations, Max Recursion Depth");
            }

            printWriter.printf("%s, %d, %d, %d, %d\n",
                    algorithmName,
                    timeTaken,
                    comparisonCounter.get(),
                    allocationCounter.get(),
                    maxRecursionDepth.get());
        }
    }
}
