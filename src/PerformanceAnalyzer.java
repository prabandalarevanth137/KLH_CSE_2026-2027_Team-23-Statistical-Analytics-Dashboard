public class PerformanceAnalyzer {

    // Number of repetitions for each measurement
    private static final int REPETITIONS = 10;

    // Prevents calculated results from being optimized away
    private static volatile double resultCheck = 0.0;


    // =====================================================
    // SEQUENTIAL SUM
    // =====================================================

    public static double measureSequentialSum(double[] data) {

        validateData(data);

        long totalTime = 0;

        for (int r = 0; r < REPETITIONS; r++) {

            long startTime = System.nanoTime();

            double sum = 0.0;

            for (double value : data) {
                sum += value;
            }

            long endTime = System.nanoTime();

            resultCheck += sum;

            totalTime += (endTime - startTime);
        }

        return totalTime / (double) REPETITIONS;
    }


    // =====================================================
    // PARALLEL SUM
    // =====================================================

    public static double measureParallelSum(double[] data) {

        validateData(data);

        long totalTime = 0;

        for (int r = 0; r < REPETITIONS; r++) {

            long startTime = System.nanoTime();

            double sum =
                    ParallelReduction.calculateSum(data);

            long endTime = System.nanoTime();

            resultCheck += sum;

            totalTime += (endTime - startTime);
        }

        return totalTime / (double) REPETITIONS;
    }


    // =====================================================
    // PARALLEL REDUCTION
    // SUM + MINIMUM + MAXIMUM
    // =====================================================

    public static double measureParallelReduction(double[] data) {

        validateData(data);

        long totalTime = 0;

        for (int r = 0; r < REPETITIONS; r++) {

            long startTime = System.nanoTime();

            double sum =
                    ParallelReduction.calculateSum(data);

            double minimum =
                    ParallelReduction.findMinimum(data);

            double maximum =
                    ParallelReduction.findMaximum(data);

            long endTime = System.nanoTime();

            resultCheck += sum + minimum + maximum;

            totalTime += (endTime - startTime);
        }

        return totalTime / (double) REPETITIONS;
    }


    // =====================================================
    // SEQUENTIAL MINIMUM
    // =====================================================

    public static double measureSequentialMinimum(double[] data) {

        validateData(data);

        long totalTime = 0;

        for (int r = 0; r < REPETITIONS; r++) {

            long startTime = System.nanoTime();

            double minimum = data[0];

            for (int i = 1; i < data.length; i++) {

                if (data[i] < minimum) {
                    minimum = data[i];
                }
            }

            long endTime = System.nanoTime();

            resultCheck += minimum;

            totalTime += (endTime - startTime);
        }

        return totalTime / (double) REPETITIONS;
    }


    // =====================================================
    // PARALLEL MINIMUM
    // =====================================================

    public static double measureParallelMinimum(double[] data) {

        validateData(data);

        long totalTime = 0;

        for (int r = 0; r < REPETITIONS; r++) {

            long startTime = System.nanoTime();

            double minimum =
                    ParallelReduction.findMinimum(data);

            long endTime = System.nanoTime();

            resultCheck += minimum;

            totalTime += (endTime - startTime);
        }

        return totalTime / (double) REPETITIONS;
    }


    // =====================================================
    // SEQUENTIAL MAXIMUM
    // =====================================================

    public static double measureSequentialMaximum(double[] data) {

        validateData(data);

        long totalTime = 0;

        for (int r = 0; r < REPETITIONS; r++) {

            long startTime = System.nanoTime();

            double maximum = data[0];

            for (int i = 1; i < data.length; i++) {

                if (data[i] > maximum) {
                    maximum = data[i];
                }
            }

            long endTime = System.nanoTime();

            resultCheck += maximum;

            totalTime += (endTime - startTime);
        }

        return totalTime / (double) REPETITIONS;
    }


    // =====================================================
    // PARALLEL MAXIMUM
    // =====================================================

    public static double measureParallelMaximum(double[] data) {

        validateData(data);

        long totalTime = 0;

        for (int r = 0; r < REPETITIONS; r++) {

            long startTime = System.nanoTime();

            double maximum =
                    ParallelReduction.findMaximum(data);

            long endTime = System.nanoTime();

            resultCheck += maximum;

            totalTime += (endTime - startTime);
        }

        return totalTime / (double) REPETITIONS;
    }


    // =====================================================
    // SEQUENTIAL PREFIX SUM
    // =====================================================

    public static double measureSequentialPrefixSum(double[] data) {

        validateData(data);

        long totalTime = 0;

        for (int r = 0; r < REPETITIONS; r++) {

            long startTime = System.nanoTime();

            double[] result =
                    SequentialPrefixSum.calculatePrefixSum(data);

            long endTime = System.nanoTime();

            resultCheck += result[result.length - 1];

            totalTime += (endTime - startTime);
        }

        return totalTime / (double) REPETITIONS;
    }


    // =====================================================
    // PARALLEL PREFIX SUM
    // =====================================================

    public static double measureParallelPrefixSum(double[] data) {

        validateData(data);

        long totalTime = 0;

        for (int r = 0; r < REPETITIONS; r++) {

            long startTime = System.nanoTime();

            double[] result =
                    ParallelPrefixSum.calculatePrefixSum(data);

            long endTime = System.nanoTime();

            resultCheck += result[result.length - 1];

            totalTime += (endTime - startTime);
        }

        return totalTime / (double) REPETITIONS;
    }


    // =====================================================
    // SEQUENTIAL STATISTICS
    // Mean + Variance + Minimum + Maximum
    // =====================================================

    public static double measureSequentialStatistics(double[] data) {

        validateData(data);

        long totalTime = 0;

        for (int r = 0; r < REPETITIONS; r++) {

            long startTime = System.nanoTime();

            double mean =
                    SequentialStatistics.calculateMean(data);

            double variance =
                    SequentialStatistics.calculateVariance(data);

            double minimum =
                    SequentialStatistics.findMinimum(data);

            double maximum =
                    SequentialStatistics.findMaximum(data);

            long endTime = System.nanoTime();

            resultCheck +=
                    mean + variance + minimum + maximum;

            totalTime += (endTime - startTime);
        }

        return totalTime / (double) REPETITIONS;
    }


    // =====================================================
    // CONVERT NANOSECONDS TO MILLISECONDS
    // =====================================================

    public static double nanoToMilli(double nanoseconds) {

        return nanoseconds / 1_000_000.0;
    }


    // =====================================================
    // CALCULATE SPEEDUP
    // Sequential Time / Parallel Time
    // =====================================================

    public static double calculateSpeedup(
            double sequentialTime,
            double parallelTime) {

        if (parallelTime <= 0) {
            return 0.0;
        }

        return sequentialTime / parallelTime;
    }


    // =====================================================
    // CALCULATE EFFICIENCY
    // Speedup / Number of Available Processors
    // =====================================================

    public static double calculateEfficiency(
            double speedup,
            int processors) {

        if (processors <= 0) {
            return 0.0;
        }

        return speedup / processors;
    }


    // =====================================================
    // GET AVAILABLE PROCESSORS
    // =====================================================

    public static int getAvailableProcessors() {

        return Runtime.getRuntime().availableProcessors();
    }


    // =====================================================
    // VALIDATE DATASET
    // =====================================================

    private static void validateData(double[] data) {

        if (data == null) {
            throw new IllegalArgumentException(
                    "Dataset cannot be null."
            );
        }

        if (data.length == 0) {
            throw new IllegalArgumentException(
                    "Dataset cannot be empty."
            );
        }
    }
}