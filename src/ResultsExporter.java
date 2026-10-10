import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;

public class ResultsExporter {

    public static void main(String[] args) {

        Locale.setDefault(Locale.US);

        System.out.println();
        System.out.println("==============================================================");
        System.out.println("              PERFORMANCE RESULTS EXPORTER");
        System.out.println("==============================================================");
        System.out.println();

        String[] files = {
            "data/dataset_10000.csv",
            "data/dataset_100000.csv",
            "data/dataset_500000.csv",
            "data/dataset_1000000.csv"
        };

        int[] sizes = {
            10000,
            100000,
            500000,
            1000000
        };

        // =========================================================
        // CREATE RESULTS DIRECTORY
        // =========================================================

        File resultsDirectory = new File("results");

        if (!resultsDirectory.exists()) {

            if (resultsDirectory.mkdirs()) {

                System.out.println(
                    "Results directory created successfully."
                );

            } else {

                System.out.println(
                    "Unable to create results directory."
                );

                return;
            }
        }

        String outputFile =
            "results/performance_results.csv";

        // =========================================================
        // CREATE CSV FILE
        // =========================================================

        try (
            BufferedWriter writer =
                new BufferedWriter(
                    new FileWriter(outputFile)
                )
        ) {

            // =====================================================
            // CSV HEADER
            // =====================================================

            writer.write(
                "Dataset Size,"
                + "Sequential Sum (ms),"
                + "Parallel Sum (ms),"
                + "Reduction Speedup,"
                + "Sequential Prefix (ms),"
                + "Parallel Prefix (ms),"
                + "Prefix Speedup"
            );

            writer.newLine();

            // =====================================================
            // PROCESS EACH DATASET
            // =====================================================

            for (int i = 0; i < files.length; i++) {

                String filePath = files[i];

                System.out.println(
                    "Processing dataset: " + filePath
                );

                try {

                    // =============================================
                    // LOAD DATASET
                    // =============================================

                    double[] data =
                        DatasetLoader.loadDataset(filePath);

                    if (data == null || data.length == 0) {

                        System.out.println(
                            "Dataset is empty. Skipping..."
                        );

                        continue;
                    }

                    // =============================================
                    // SEQUENTIAL SUM
                    // =============================================

                    double sequentialSumTime =
                        PerformanceAnalyzer.measureSequentialSum(
                            data
                        );

                    // =============================================
                    // PARALLEL SUM
                    // =============================================

                    double parallelSumTime =
                        PerformanceAnalyzer.measureParallelSum(
                            data
                        );

                    // =============================================
                    // SEQUENTIAL PREFIX SUM
                    // =============================================

                    double sequentialPrefixTime =
                        PerformanceAnalyzer
                            .measureSequentialPrefixSum(data);

                    // =============================================
                    // PARALLEL PREFIX SUM
                    // =============================================

                    double parallelPrefixTime =
                        PerformanceAnalyzer
                            .measureParallelPrefixSum(data);

                    // =============================================
                    // CONVERT TO MILLISECONDS
                    // =============================================

                    double sequentialSumMs =
                        PerformanceAnalyzer.nanoToMilli(
                            sequentialSumTime
                        );

                    double parallelSumMs =
                        PerformanceAnalyzer.nanoToMilli(
                            parallelSumTime
                        );

                    double sequentialPrefixMs =
                        PerformanceAnalyzer.nanoToMilli(
                            sequentialPrefixTime
                        );

                    double parallelPrefixMs =
                        PerformanceAnalyzer.nanoToMilli(
                            parallelPrefixTime
                        );

                    // =============================================
                    // CALCULATE SPEEDUP
                    // =============================================

                    double reductionSpeedup =
                        PerformanceAnalyzer.calculateSpeedup(
                            sequentialSumMs,
                            parallelSumMs
                        );

                    double prefixSpeedup =
                        PerformanceAnalyzer.calculateSpeedup(
                            sequentialPrefixMs,
                            parallelPrefixMs
                        );

                    // =============================================
                    // WRITE DATA TO CSV
                    // =============================================

                    writer.write(
                        String.format(
                            Locale.US,
                            "%d,%.6f,%.6f,%.6f,%.6f,%.6f,%.6f",
                            sizes[i],
                            sequentialSumMs,
                            parallelSumMs,
                            reductionSpeedup,
                            sequentialPrefixMs,
                            parallelPrefixMs,
                            prefixSpeedup
                        )
                    );

                    writer.newLine();

                    // =============================================
                    // DISPLAY RESULT
                    // =============================================

                    System.out.printf(
                        "  Dataset Size       : %d%n",
                        sizes[i]
                    );

                    System.out.printf(
                        "  Sequential Sum     : %.6f ms%n",
                        sequentialSumMs
                    );

                    System.out.printf(
                        "  Parallel Sum       : %.6f ms%n",
                        parallelSumMs
                    );

                    System.out.printf(
                        "  Reduction Speedup  : %.4fx%n",
                        reductionSpeedup
                    );

                    System.out.printf(
                        "  Sequential Prefix  : %.6f ms%n",
                        sequentialPrefixMs
                    );

                    System.out.printf(
                        "  Parallel Prefix    : %.6f ms%n",
                        parallelPrefixMs
                    );

                    System.out.printf(
                        "  Prefix Speedup     : %.4fx%n",
                        prefixSpeedup
                    );

                    System.out.println();

                } catch (IOException e) {

                    System.out.println(
                        "Error loading dataset: "
                        + filePath
                    );

                    e.printStackTrace();

                } catch (Exception e) {

                    System.out.println(
                        "Unexpected error processing: "
                        + filePath
                    );

                    e.printStackTrace();
                }
            }

            // =====================================================
            // FINISH
            // =====================================================

            System.out.println(
                "CSV file created successfully."
            );

            System.out.println(
                "Output file: " + outputFile
            );

        } catch (IOException e) {

            System.out.println(
                "Error creating CSV file."
            );

            e.printStackTrace();
        }

        System.out.println();
        System.out.println(
            "=============================================================="
        );
        System.out.println(
            "              EXPORT COMPLETED SUCCESSFULLY"
        );
        System.out.println(
            "=============================================================="
        );
    }
}