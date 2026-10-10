import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class DatasetGenerator {

    public static void generateDataset(
            String filePath,
            int size
    ) throws IOException {

        Random random = new Random(42);

        try (BufferedWriter writer =
                     new BufferedWriter(
                             new FileWriter(filePath)
                     )) {

            for (int i = 0; i < size; i++) {

                // Generate values between 1 and 1000
                double value =
                        1 + random.nextDouble() * 999;

                writer.write(String.valueOf(value));
                writer.newLine();
            }
        }
    }


    public static void generateAllDatasets()
            throws IOException {

        generateDataset("data/dataset_10000.csv", 10_000);

        generateDataset("data/dataset_100000.csv", 100_000);

        generateDataset("data/dataset_500000.csv", 500_000);

        generateDataset("data/dataset_1000000.csv", 1_000_000);

        System.out.println(
                "Large datasets generated successfully!"
        );
    }


    public static void main(String[] args) {

        try {

            generateAllDatasets();

        } catch (IOException e) {

            System.out.println(
                    "Error generating datasets."
            );

            e.printStackTrace();
        }
    }
}