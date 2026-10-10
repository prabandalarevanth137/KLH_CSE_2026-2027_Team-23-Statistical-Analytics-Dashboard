import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class ParallelPrefixSum {

    /*
     * Minimum number of elements processed by one task.
     * A larger block reduces task-creation overhead.
     */
    private static final int THRESHOLD = 8192;

    private static final ForkJoinPool POOL =
            ForkJoinPool.commonPool();


    // =====================================================
    // PARALLEL LOCAL PREFIX TASK
    // =====================================================

    private static class PrefixTask extends RecursiveAction {

        private final double[] result;
        private final int start;
        private final int end;

        PrefixTask(
                double[] result,
                int start,
                int end) {

            this.result = result;
            this.start = start;
            this.end = end;
        }

        @Override
        protected void compute() {

            int length = end - start;

            /*
             * Small section:
             * calculate prefix sum sequentially.
             */
            if (length <= THRESHOLD) {

                for (int i = start + 1; i < end; i++) {

                    result[i] =
                            result[i - 1] + result[i];
                }

                return;
            }

            /*
             * Divide the section into two parts.
             */
            int middle =
                    start + length / 2;

            PrefixTask left =
                    new PrefixTask(
                            result,
                            start,
                            middle
                    );

            PrefixTask right =
                    new PrefixTask(
                            result,
                            middle,
                            end
                    );

            /*
             * Process both sections in parallel.
             */
            invokeAll(left, right);

            /*
             * Add the total of the left section
             * to every value in the right section.
             */
            double leftTotal =
                    result[middle - 1];

            AddOffsetTask offsetTask =
                    new AddOffsetTask(
                            result,
                            middle,
                            end,
                            leftTotal
                    );

            offsetTask.invoke();
        }
    }


    // =====================================================
    // ADD OFFSET TASK
    // =====================================================

    private static class AddOffsetTask
            extends RecursiveAction {

        private final double[] result;
        private final int start;
        private final int end;
        private final double offset;

        AddOffsetTask(
                double[] result,
                int start,
                int end,
                double offset) {

            this.result = result;
            this.start = start;
            this.end = end;
            this.offset = offset;
        }

        @Override
        protected void compute() {

            int length = end - start;

            if (length <= THRESHOLD) {

                for (int i = start; i < end; i++) {
                    result[i] += offset;
                }

                return;
            }

            int middle =
                    start + length / 2;

            AddOffsetTask left =
                    new AddOffsetTask(
                            result,
                            start,
                            middle,
                            offset
                    );

            AddOffsetTask right =
                    new AddOffsetTask(
                            result,
                            middle,
                            end,
                            offset
                    );

            invokeAll(left, right);
        }
    }


    // =====================================================
    // PUBLIC METHOD
    // =====================================================

    public static double[] calculatePrefixSum(
            double[] data) {

        if (data == null || data.length == 0) {
            return new double[0];
        }

        /*
         * Copy the input so the original dataset
         * is not modified.
         */
        double[] result =
                data.clone();

        /*
         * Run the parallel prefix calculation.
         */
        POOL.invoke(
                new PrefixTask(
                        result,
                        0,
                        result.length
                )
        );

        return result;
    }
}