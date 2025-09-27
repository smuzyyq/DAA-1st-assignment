package metrics;

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MetricsRecorder {

    private CSVWriter writer;
    private boolean fileExists;

    public MetricsRecorder(String filePath) throws IOException {
        File file = new File(filePath);
        fileExists = file.exists();
        writer = new CSVWriter(new FileWriter(filePath, true));
        if (!fileExists) {
            String[] header = { "Algorithm", "ArrayType", "Size", "Time (ns)" };
            writer.writeNext(header);
        }
    }

    // Метод записи метрик в CSV
    public void recordMetrics(String algorithm, String arrayType, int size, long time) throws IOException {
        String[] data = { algorithm, arrayType, String.valueOf(size), String.valueOf(time) };
        writer.writeNext(data);
    }

    // Закрытие CSVWriter
    public void close() throws IOException {
        writer.close();
    }
}
