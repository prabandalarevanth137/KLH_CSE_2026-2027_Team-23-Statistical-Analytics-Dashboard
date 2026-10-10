public class SequentialPrefixSum {

    public static double[] calculatePrefixSum(double[] data) {

        int n = data.length;

        if (n == 0) {
            return new double[0];
        }

        double[] prefixSum = new double[n];

        prefixSum[0] = data[0];

        for (int i = 1; i < n; i++) {
            prefixSum[i] = prefixSum[i - 1] + data[i];
        }

        return prefixSum;
    }
}