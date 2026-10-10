public class SequentialStatistics {

    public static double calculateMean(double[] data) {

        double sum = 0;

        for (double value : data) {
            sum += value;
        }

        return sum / data.length;
    }

    public static double calculateVariance(double[] data) {

        double mean = calculateMean(data);
        double sum = 0;

        for (double value : data) {
            sum += Math.pow(value - mean, 2);
        }

        return sum / data.length;
    }

    public static double findMinimum(double[] data) {

        double minimum = data[0];

        for (double value : data) {
            if (value < minimum) {
                minimum = value;
            }
        }

        return minimum;
    }

    public static double findMaximum(double[] data) {

        double maximum = data[0];

        for (double value : data) {
            if (value > maximum) {
                maximum = value;
            }
        }

        return maximum;
    }
}