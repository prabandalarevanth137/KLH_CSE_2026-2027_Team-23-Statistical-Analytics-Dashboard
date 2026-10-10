import java.io.IOException;
import java.util.Locale;

public class PerformanceBenchmark {

    public static void main(String[] args) {

        Locale.setDefault(Locale.US);

        System.out.println();
        System.out.println("==============================================================");
        System.out.println("        STATISTICAL ANALYTICS PERFORMANCE BENCHMARK");
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

        System.out.println(
            "Dataset Size | Seq Sum(ms) | Par Sum(ms) | "
            + "Seq Prefix(ms) | Par Prefix(ms)"
        );

        System.out.println(
            "-------------|-------------|-------------|"
            + "----------------|----------------"
        );

        for (int i = 0; i < files.length; i++) {

            String filePath = files[i];

            try {

                // =================================================
                // LOAD DATASET
                // =================================================

                double[] data =
                    DatasetLoader.loadDataset(filePath);

                if (data == null || data.length == 0) {

                    System.out.println(
                        "Could not load dataset: " + filePath
                    );

                    continue;
                }

                // =================================================
                // SEQUENTIAL SUM
                // =================================================

                double sequentialSumTime =
                    PerformanceAnalyzer.measureSequentialSum(data);

                // =================================================
                // PARALLEL SUM
                // =================================================

                double parallelSumTime =
                    PerformanceAnalyzer.measureParallelSum(data);

                // =================================================
                // SEQUENTIAL PREFIX SUM
                // =================================================

                double sequentialPrefixTime =
                    PerformanceAnalyzer.measureSequentialPrefixSum(data);

                // =================================================
                // PARALLEL PREFIX SUM
                // =================================================

                double parallelPrefixTime =
                    PerformanceAnalyzer.measureParallelPrefixSum(data);

                // =================================================
                // CONVERT NANOSECONDS TO MILLISECONDS
                // =================================================

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

                // =================================================
                // CALCULATE SPEEDUP
                // =================================================

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

                // =================================================
                // DISPLAY PERFORMANCE RESULTS
                // =================================================

                System.out.printf(
                    "%-12d | %-11.4f | %-11.4f | %-14.4f | %-14.4f%n",
                    sizes[i],
                    sequentialSumMs,
                    parallelSumMs,
                    sequentialPrefixMs,
                    parallelPrefixMs
                );

                System.out.printf(
                    "    Reduction Speedup : %.4fx%n",
                    reductionSpeedup
                );

                System.out.printf(
                    "    Prefix Speedup    : %.4fx%n",
                    prefixSpeedup
                );

                System.out.println();

                // =================================================
                // SUM VERIFICATION
                // =================================================

                double sequentialSum = 0.0;

                for (double value : data) {
                    sequentialSum += value;
                }

                double parallelSum =
                    ParallelReduction.calculateSum(data);

                /*
                 * Floating-point calculations may produce tiny
                 * differences because sequential and parallel
                 * algorithms can add values in different orders.
                 *
                 * Therefore, relative tolerance is used instead
                 * of a fixed absolute tolerance.
                 */

                double sumDifference =
                    Math.abs(
                        sequentialSum - parallelSum
                    );

                double sumTolerance =
                    1e-9 * Math.max(
                        1.0,
                        Math.abs(sequentialSum)
                    );

                boolean sumMatch =
                    sumDifference <= sumTolerance;

                // =================================================
                // PREFIX SUM VERIFICATION
                // =================================================

                double[] sequentialPrefix =
                    SequentialPrefixSum.calculatePrefixSum(data);

                double[] parallelPrefix =
                    ParallelPrefixSum.calculatePrefixSum(data);

                boolean prefixMatch = true;

                // Check array lengths
                if (sequentialPrefix.length
                    != parallelPrefix.length) {

                    prefixMatch = false;

                } else {

                    // Compare every prefix value
                    for (int j = 0;
                         j < sequentialPrefix.length;
                         j++) {

                        double difference =
                            Math.abs(
                                sequentialPrefix[j]
                                - parallelPrefix[j]
                            );

                        double tolerance =
                            1e-9 * Math.max(
                                1.0,
                                Math.abs(sequentialPrefix[j])
                            );

                        if (difference > tolerance) {

                            prefixMatch = false;
                            break;
                        }
                    }
                }

                // =================================================
                // DISPLAY VERIFICATION RESULTS
                // =================================================

                System.out.println(
                    "    Sum Verification    : " + sumMatch
                );

                System.out.println(
                    "    Prefix Verification : " + prefixMatch
                );

                System.out.println(
                    "--------------------------------------------------------------"
                );

            } catch (IOException e) {

                System.out.println(
                    "Error loading dataset: " + filePath
                );

                e.printStackTrace();

            } catch (Exception e) {

                System.out.println(
                    "Unexpected error while processing: "
                    + filePath
                );

                e.printStackTrace();
            }
        }

        // =========================================================
        // BENCHMARK COMPLETION
        // =========================================================

        System.out.println();

        System.out.println(
            "=============================================================="
        );

        System.out.println(
            "              BENCHMARK COMPLETED SUCCESSFULLY"
        );

        System.out.println(
            "=============================================================="
        );
    }
}