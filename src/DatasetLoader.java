import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class DatasetLoader {

    public static double[] loadDataset(String filePath) throws IOException {

        List<String> lines = Files.readAllLines(Paths.get(filePath));

        double[] data = new double[lines.size()];

        for (int i = 0; i < lines.size(); i++) {
            data[i] = Double.parseDouble(lines.get(i).trim());
        }

        return data;
    }
}
