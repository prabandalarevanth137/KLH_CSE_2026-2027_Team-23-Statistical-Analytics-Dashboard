import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelReduction {

    /*
     * Larger threshold reduces the overhead of creating
     * too many small Fork/Join tasks.
     */
    private static final int THRESHOLD = 8192;

    /*
     * Reuse one Fork/Join pool instead of creating a new
     * pool every time a method is called.
     */
    private static final ForkJoinPool POOL =
            ForkJoinPool.commonPool();


    // =====================================================
    // SUM REDUCTION
    // =====================================================

    private static class SumTask extends RecursiveTask<Double> {

        private final double[] data;
        private final int start;
        private final int end;

        SumTask(double[] data, int start, int end) {
            this.data = data;
            this.start = start;
            this.end = end;
        }

        @Override
        protected Double compute() {

            int length = end - start;

            // Sequential calculation for a small section
            if (length <= THRESHOLD) {

                double sum = 0.0;

                for (int i = start; i < end; i++) {
                    sum += data[i];
                }

                return sum;
            }

            // Divide the array into two parts
            int middle = start + length / 2;

            SumTask left =
                    new SumTask(data, start, middle);

            SumTask right =
                    new SumTask(data, middle, end);

            // Execute left part in parallel
            left.fork();

            // Calculate right part
            double rightResult = right.compute();

            // Wait for left part
            double leftResult = left.join();

            // Combine results
            return leftResult + rightResult;
        }
    }


    // =====================================================
    // MINIMUM REDUCTION
    // =====================================================

    private static class MinTask extends RecursiveTask<Double> {

        private final double[] data;
        private final int start;
        private final int end;

        MinTask(double[] data, int start, int end) {
            this.data = data;
            this.start = start;
            this.end = end;
        }

        @Override
        protected Double compute() {

            int length = end - start;

            if (length <= THRESHOLD) {

                double minimum = data[start];

                for (int i = start + 1; i < end; i++) {
                    minimum = Math.min(minimum, data[i]);
                }

                return minimum;
            }

            int middle = start + length / 2;

            MinTask left =
                    new MinTask(data, start, middle);

            MinTask right =
                    new MinTask(data, middle, end);

            left.fork();

            double rightResult = right.compute();

            double leftResult = left.join();

            return Math.min(leftResult, rightResult);
        }
    }


    // =====================================================
    // MAXIMUM REDUCTION
    // =====================================================

    private static class MaxTask extends RecursiveTask<Double> {

        private final double[] data;
        private final int start;
        private final int end;

        MaxTask(double[] data, int start, int end) {
            this.data = data;
            this.start = start;
            this.end = end;
        }

        @Override
        protected Double compute() {

            int length = end - start;

            if (length <= THRESHOLD) {

                double maximum = data[start];

                for (int i = start + 1; i < end; i++) {
                    maximum = Math.max(maximum, data[i]);
                }

                return maximum;
            }

            int middle = start + length / 2;

            MaxTask left =
                    new MaxTask(data, start, middle);

            MaxTask right =
                    new MaxTask(data, middle, end);

            left.fork();

            double rightResult = right.compute();

            double leftResult = left.join();

            return Math.max(leftResult, rightResult);
        }
    }


    // =====================================================
    // PUBLIC SUM METHOD
    // =====================================================

    public static double calculateSum(double[] data) {

        if (data == null || data.length == 0) {
            return 0.0;
        }

        return POOL.invoke(
                new SumTask(data, 0, data.length)
        );
    }


    // =====================================================
    // PUBLIC MEAN METHOD
    // =====================================================

    public static double calculateMean(double[] data) {

        if (data == null || data.length == 0) {
            return 0.0;
        }

        return calculateSum(data) / data.length;
    }


    // =====================================================
    // PUBLIC MINIMUM METHOD
    // =====================================================

    public static double findMinimum(double[] data) {

        if (data == null || data.length == 0) {
            return Double.NaN;
        }

        return POOL.invoke(
                new MinTask(data, 0, data.length)
        );
    }


    // =====================================================
    // PUBLIC MAXIMUM METHOD
    // =====================================================

    public static double findMaximum(double[] data) {

        if (data == null || data.length == 0) {
            return Double.NaN;
        }

        return POOL.invoke(
                new MaxTask(data, 0, data.length)
        );
    }
}