import java.util.Map;
import java.util.TreeMap;

public class FrequencyDistribution {

    public static Map<Double, Integer> calculateFrequency(double[] data) {

        Map<Double, Integer> frequencyMap = new TreeMap<>();

        for (double value : data) {

            frequencyMap.put(
                    value,
                    frequencyMap.getOrDefault(value, 0) + 1
            );
        }

        return frequencyMap;
    }
}