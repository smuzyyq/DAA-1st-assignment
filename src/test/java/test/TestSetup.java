package test;

import org.junit.jupiter.api.BeforeAll;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TestSetup {
    @BeforeAll
    static void clearCSV() {
        try {
            Files.deleteIfExists(Paths.get("metrics.csv"));
            System.out.println("metrics.csv cleared before tests");
        } catch (IOException e) {
            System.err.println("Error while deleting metrics.csv: " + e.getMessage());
        }
    }
}
