import java.util.Map;

public class Main {

    public static void main(String[] args) {

        System.out.println("========================================");
        System.out.println(" Statistical Analytics Dashboard");
        System.out.println("========================================");

        try {

            // ========================================
            // 1. LOAD DATASET
            // ========================================

            double[] data =
                    DatasetLoader.loadDataset("data/sample.csv");

            if (data == null || data.length == 0) {

                System.out.println("Dataset is empty.");
                return;
            }

            System.out.println("\nDataset loaded successfully!");
            System.out.println("Number of values: " + data.length);

            System.out.println("\nDataset Values");
            System.out.println("--------------");

            for (double value : data) {
                System.out.print(value + " ");
            }

            System.out.println();


            // ========================================
            // 2. SEQUENTIAL STATISTICS
            // ========================================

            double mean =
                    SequentialStatistics.calculateMean(data);

            double variance =
                    SequentialStatistics.calculateVariance(data);

            double minimum =
                    SequentialStatistics.findMinimum(data);

            double maximum =
                    SequentialStatistics.findMaximum(data);

            System.out.println("\nSequential Statistics");
            System.out.println("---------------------");

            System.out.println("Mean     : " + mean);
            System.out.println("Variance : " + variance);
            System.out.println("Minimum  : " + minimum);
            System.out.println("Maximum  : " + maximum);


            // ========================================
            // 3. PARALLEL REDUCTION
            // ========================================

            double parallelSum =
                    ParallelReduction.calculateSum(data);

            double parallelMean =
                    ParallelReduction.calculateMean(data);

            double parallelMinimum =
                    ParallelReduction.findMinimum(data);

            double parallelMaximum =
                    ParallelReduction.findMaximum(data);

            System.out.println("\nParallel Reduction");
            System.out.println("------------------");

            System.out.println("Parallel Sum     : " + parallelSum);
            System.out.println("Parallel Mean    : " + parallelMean);
            System.out.println("Parallel Minimum : " + parallelMinimum);
            System.out.println("Parallel Maximum : " + parallelMaximum);


            // ========================================
            // 4. REDUCTION RESULT VERIFICATION
            // ========================================

            System.out.println("\nReduction Verification");
            System.out.println("----------------------");

            boolean meanMatch =
                    Math.abs(mean - parallelMean) < 0.000001;

            boolean minimumMatch =
                    Math.abs(minimum - parallelMinimum) < 0.000001;

            boolean maximumMatch =
                    Math.abs(maximum - parallelMaximum) < 0.000001;

            System.out.println(
                    "Mean Match     : " + meanMatch
            );

            System.out.println(
                    "Minimum Match  : " + minimumMatch
            );

            System.out.println(
                    "Maximum Match  : " + maximumMatch
            );


            // ========================================
            // 5. SEQUENTIAL PREFIX SUM
            // ========================================

            double[] sequentialPrefix =
                    SequentialPrefixSum.calculatePrefixSum(data);

            System.out.println("\nSequential Prefix Sum");
            System.out.println("---------------------");

            for (double value : sequentialPrefix) {
                System.out.print(value + " ");
            }

            System.out.println();


            // ========================================
            // 6. PARALLEL PREFIX SUM
            // ========================================

            double[] parallelPrefix =
                    ParallelPrefixSum.calculatePrefixSum(data);

            System.out.println("\nParallel Prefix Sum");
            System.out.println("-------------------");

            for (double value : parallelPrefix) {
                System.out.print(value + " ");
            }

            System.out.println();


            // ========================================
            // 7. PREFIX SUM VERIFICATION
            // ========================================

            System.out.println("\nPrefix Sum Verification");
            System.out.println("-----------------------");

            boolean prefixMatch = true;

            if (sequentialPrefix.length != parallelPrefix.length) {

                prefixMatch = false;

            } else {

                for (int i = 0;
                     i < sequentialPrefix.length;
                     i++) {

                    if (Math.abs(
                            sequentialPrefix[i]
                                    - parallelPrefix[i]
                    ) > 0.000001) {

                        prefixMatch = false;
                        break;
                    }
                }
            }

            System.out.println(
                    "Sequential vs Parallel Prefix Sum : "
                            + prefixMatch
            );


            // ========================================
            // 8. FREQUENCY DISTRIBUTION
            // ========================================

            Map<Double, Integer> frequency =
                    FrequencyDistribution.calculateFrequency(data);

            System.out.println("\nFrequency Distribution");
            System.out.println("----------------------");

            for (Map.Entry<Double, Integer> entry :
                    frequency.entrySet()) {

                System.out.println(
                        entry.getKey()
                                + " -> "
                                + entry.getValue()
                );
            }


            // ========================================
            // 9. PERFORMANCE ANALYSIS
            // ========================================

            /*
             * Sequential statistics measures:
             * mean + variance + minimum + maximum
             *
             * Parallel reduction measures:
             * sum + minimum + maximum
             *
             * Sequential and parallel prefix sum are
             * measured separately.
             */

            double sequentialStatisticsTime =
                    PerformanceAnalyzer
                            .measureSequentialStatistics(data);

            /*
             * IMPORTANT:
             * Current PerformanceAnalyzer uses
             * measureParallelSum(), not
             * measureParallelReduction().
             */
            double parallelReductionTime =
                    PerformanceAnalyzer
                            .measureParallelSum(data);

            double sequentialPrefixTime =
                    PerformanceAnalyzer
                            .measureSequentialPrefixSum(data);

            double parallelPrefixTime =
                    PerformanceAnalyzer
                            .measureParallelPrefixSum(data);


            // ========================================
            // 10. CALCULATE SPEEDUP
            // ========================================

            double reductionSpeedup =
                    PerformanceAnalyzer.calculateSpeedup(
                            sequentialStatisticsTime,
                            parallelReductionTime
                    );

            double prefixSpeedup =
                    PerformanceAnalyzer.calculateSpeedup(
                            sequentialPrefixTime,
                            parallelPrefixTime
                    );


            // ========================================
            // 11. CONVERT TIME TO MILLISECONDS
            // ========================================

            double sequentialStatisticsMs =
                    PerformanceAnalyzer.nanoToMilli(
                            sequentialStatisticsTime
                    );

            double parallelReductionMs =
                    PerformanceAnalyzer.nanoToMilli(
                            parallelReductionTime
                    );

            double sequentialPrefixMs =
                    PerformanceAnalyzer.nanoToMilli(
                            sequentialPrefixTime
                    );

            double parallelPrefixMs =
                    PerformanceAnalyzer.nanoToMilli(
                            parallelPrefixTime
                    );


            // ========================================
            // 12. DISPLAY PERFORMANCE RESULTS
            // ========================================

            System.out.println("\nPerformance Analysis");
            System.out.println("--------------------");

            System.out.printf(
                    "Sequential Statistics : %.6f ms%n",
                    sequentialStatisticsMs
            );

            System.out.printf(
                    "Parallel Reduction    : %.6f ms%n",
                    parallelReductionMs
            );

            System.out.printf(
                    "Sequential Prefix Sum : %.6f ms%n",
                    sequentialPrefixMs
            );

            System.out.printf(
                    "Parallel Prefix Sum   : %.6f ms%n",
                    parallelPrefixMs
            );

            System.out.printf(
                    "Reduction Speedup     : %.4fx%n",
                    reductionSpeedup
            );

            System.out.printf(
                    "Prefix Sum Speedup    : %.4fx%n",
                    prefixSpeedup
            );


            // ========================================
            // 13. OVERALL RESULT VERIFICATION
            // ========================================

            System.out.println("\nOverall Verification");
            System.out.println("--------------------");

            boolean allResultsCorrect =
                    meanMatch
                            && minimumMatch
                            && maximumMatch
                            && prefixMatch;

            System.out.println(
                    "Statistical Results : "
                            + (meanMatch
                            && minimumMatch
                            && maximumMatch)
            );

            System.out.println(
                    "Prefix Sum Results  : "
                            + prefixMatch
            );

            System.out.println(
                    "All Results Correct : "
                            + allResultsCorrect
            );


            // ========================================
            // 14. CURRENT PROJECT STATUS
            // ========================================

            System.out.println("\n========================================");
            System.out.println(" Current Project Status");
            System.out.println("========================================");

            System.out.println(
                    "Dataset Loading        : Completed"
            );

            System.out.println(
                    "Sequential Statistics  : Completed"
            );

            System.out.println(
                    "Parallel Reduction     : Completed"
            );

            System.out.println(
                    "Sequential Prefix Sum  : Completed"
            );

            System.out.println(
                    "Parallel Prefix Sum    : Completed"
            );

            System.out.println(
                    "Frequency Distribution : Completed"
            );

            System.out.println(
                    "Performance Analysis   : Completed"
            );

            System.out.println(
                    "Graphical Reports      : Pending"
            );

            System.out.println(
                    "Dashboard              : Pending"
            );

            System.out.println("========================================");

            System.out.println(
                    "\nProject execution completed successfully!"
            );


        } catch (Exception e) {

            System.out.println(
                    "\nError while running the project."
            );

            e.printStackTrace();
        }
    }
}