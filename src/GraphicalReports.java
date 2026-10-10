import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.imageio.ImageIO;

public class GraphicalReports {

    // =========================================================
    // GRAPH SIZE
    // =========================================================

    private static final int WIDTH = 1200;
    private static final int HEIGHT = 700;

    // =========================================================
    // DATA ARRAYS
    // =========================================================

    private static int[] datasetSizes;

    private static double[] sequentialSum;
    private static double[] parallelSum;

    private static double[] sequentialPrefix;
    private static double[] parallelPrefix;

    private static double[] reductionSpeedup;
    private static double[] prefixSpeedup;

    // =========================================================
    // MAIN METHOD
    // =========================================================

    public static void main(String[] args) {

        System.out.println();
        System.out.println("==============================================================");
        System.out.println("                 GRAPHICAL PERFORMANCE REPORTS");
        System.out.println("==============================================================");
        System.out.println();

        String inputFile =
            "results/performance_results.csv";

        String outputDirectory =
            "results/graphs";

        try {

            // =====================================================
            // CHECK INPUT FILE
            // =====================================================

            File csvFile = new File(inputFile);

            if (!csvFile.exists()) {

                System.out.println(
                    "Performance results CSV not found."
                );

                System.out.println(
                    "Expected file: " + inputFile
                );

                return;
            }

            // =====================================================
            // CREATE OUTPUT DIRECTORY
            // =====================================================

            File graphDirectory =
                new File(outputDirectory);

            if (!graphDirectory.exists()) {

                if (graphDirectory.mkdirs()) {

                    System.out.println(
                        "Graph directory created successfully."
                    );

                } else {

                    System.out.println(
                        "Unable to create graph directory."
                    );

                    return;
                }
            }

            // =====================================================
            // LOAD CSV DATA
            // =====================================================

            loadCSV(inputFile);

            // =====================================================
            // CREATE GRAPH 1
            // =====================================================

            createSumPerformanceGraph(
                outputDirectory
            );

            // =====================================================
            // CREATE GRAPH 2
            // =====================================================

            createPrefixPerformanceGraph(
                outputDirectory
            );

            // =====================================================
            // CREATE GRAPH 3
            // =====================================================

            createReductionSpeedupGraph(
                outputDirectory
            );

            // =====================================================
            // CREATE GRAPH 4
            // =====================================================

            createPrefixSpeedupGraph(
                outputDirectory
            );

            // =====================================================
            // COMPLETION
            // =====================================================

            System.out.println();

            System.out.println(
                "=============================================================="
            );

            System.out.println(
                "          GRAPHICAL REPORTS CREATED SUCCESSFULLY"
            );

            System.out.println(
                "=============================================================="
            );

            System.out.println();

            System.out.println(
                "Graphs saved in:"
            );

            System.out.println(
                outputDirectory
            );

            System.out.println();

        } catch (IOException e) {

            System.out.println(
                "Error while creating graphical reports."
            );

            e.printStackTrace();

        } catch (Exception e) {

            System.out.println(
                "Unexpected error."
            );

            e.printStackTrace();
        }
    }

    // =========================================================
    // LOAD CSV
    // =========================================================

    private static void loadCSV(
        String filePath
    ) throws IOException {

        BufferedReader reader =
            new BufferedReader(
                new FileReader(filePath)
            );

        // Skip CSV header
        reader.readLine();

        int count = 0;

        String line;

        while ((line = reader.readLine()) != null) {

            if (!line.trim().isEmpty()) {
                count++;
            }
        }

        reader.close();

        if (count == 0) {

            throw new IOException(
                "CSV file does not contain data."
            );
        }

        datasetSizes = new int[count];

        sequentialSum = new double[count];
        parallelSum = new double[count];

        reductionSpeedup = new double[count];

        sequentialPrefix = new double[count];
        parallelPrefix = new double[count];

        prefixSpeedup = new double[count];

        // =====================================================
        // READ DATA
        // =====================================================

        reader =
            new BufferedReader(
                new FileReader(filePath)
            );

        // Skip header
        reader.readLine();

        int index = 0;

        while ((line = reader.readLine()) != null) {

            if (line.trim().isEmpty()) {
                continue;
            }

            String[] values =
                line.split(",");

            if (values.length < 7) {

                continue;
            }

            datasetSizes[index] =
                Integer.parseInt(
                    values[0].trim()
                );

            sequentialSum[index] =
                Double.parseDouble(
                    values[1].trim()
                );

            parallelSum[index] =
                Double.parseDouble(
                    values[2].trim()
                );

            reductionSpeedup[index] =
                Double.parseDouble(
                    values[3].trim()
                );

            sequentialPrefix[index] =
                Double.parseDouble(
                    values[4].trim()
                );

            parallelPrefix[index] =
                Double.parseDouble(
                    values[5].trim()
                );

            prefixSpeedup[index] =
                Double.parseDouble(
                    values[6].trim()
                );

            index++;
        }

        reader.close();

        System.out.println(
            "CSV data loaded successfully."
        );

        System.out.println(
            "Number of datasets: " + index
        );
    }

    // =========================================================
    // GRAPH 1
    // SEQUENTIAL VS PARALLEL SUM
    // =========================================================

    private static void createSumPerformanceGraph(
        String outputDirectory
    ) throws IOException {

        BufferedImage image =
            createBaseImage();

        Graphics2D g =
            image.createGraphics();

        configureGraphics(g);

        drawTitle(
            g,
            "Sequential vs Parallel Sum Performance"
        );

        drawAxisLabels(
            g,
            "Dataset Size",
            "Execution Time (ms)"
        );

        int left = 110;
        int right = 1120;
        int top = 120;
        int bottom = 590;

        drawAxes(
            g,
            left,
            top,
            right,
            bottom
        );

        double maxValue =
            getMaximum(
                sequentialSum,
                parallelSum
            );

        drawYAxisScale(
            g,
            left,
            top,
            bottom,
            maxValue
        );

        drawBarChart(
            g,
            left,
            top,
            right,
            bottom,
            sequentialSum,
            parallelSum,
            maxValue
        );

        drawLegend(
            g,
            "Sequential Sum",
            "Parallel Sum"
        );

        g.dispose();

        File output =
            new File(
                outputDirectory
                + "/sum_performance.png"
            );

        ImageIO.write(
            image,
            "png",
            output
        );

        System.out.println(
            "Created: " + output.getPath()
        );
    }

    // =========================================================
    // GRAPH 2
    // SEQUENTIAL VS PARALLEL PREFIX
    // =========================================================

    private static void createPrefixPerformanceGraph(
        String outputDirectory
    ) throws IOException {

        BufferedImage image =
            createBaseImage();

        Graphics2D g =
            image.createGraphics();

        configureGraphics(g);

        drawTitle(
            g,
            "Sequential vs Parallel Prefix Sum Performance"
        );

        drawAxisLabels(
            g,
            "Dataset Size",
            "Execution Time (ms)"
        );

        int left = 110;
        int right = 1120;
        int top = 120;
        int bottom = 590;

        drawAxes(
            g,
            left,
            top,
            right,
            bottom
        );

        double maxValue =
            getMaximum(
                sequentialPrefix,
                parallelPrefix
            );

        drawYAxisScale(
            g,
            left,
            top,
            bottom,
            maxValue
        );

        drawBarChart(
            g,
            left,
            top,
            right,
            bottom,
            sequentialPrefix,
            parallelPrefix,
            maxValue
        );

        drawLegend(
            g,
            "Sequential Prefix Sum",
            "Parallel Prefix Sum"
        );

        g.dispose();

        File output =
            new File(
                outputDirectory
                + "/prefix_performance.png"
            );

        ImageIO.write(
            image,
            "png",
            output
        );

        System.out.println(
            "Created: " + output.getPath()
        );
    }

    // =========================================================
    // GRAPH 3
    // REDUCTION SPEEDUP
    // =========================================================

    private static void createReductionSpeedupGraph(
        String outputDirectory
    ) throws IOException {

        BufferedImage image =
            createBaseImage();

        Graphics2D g =
            image.createGraphics();

        configureGraphics(g);

        drawTitle(
            g,
            "Parallel Reduction Speedup"
        );

        drawAxisLabels(
            g,
            "Dataset Size",
            "Speedup (x)"
        );

        int left = 110;
        int right = 1120;
        int top = 120;
        int bottom = 590;

        drawAxes(
            g,
            left,
            top,
            right,
            bottom
        );

        double maxValue =
            getMaximum(
                reductionSpeedup
            );

        drawYAxisScale(
            g,
            left,
            top,
            bottom,
            maxValue
        );

        drawLineChart(
            g,
            left,
            top,
            right,
            bottom,
            reductionSpeedup,
            maxValue
        );

        g.dispose();

        File output =
            new File(
                outputDirectory
                + "/reduction_speedup.png"
            );

        ImageIO.write(
            image,
            "png",
            output
        );

        System.out.println(
            "Created: " + output.getPath()
        );
    }

    // =========================================================
    // GRAPH 4
    // PREFIX SPEEDUP
    // =========================================================

    private static void createPrefixSpeedupGraph(
        String outputDirectory
    ) throws IOException {

        BufferedImage image =
            createBaseImage();

        Graphics2D g =
            image.createGraphics();

        configureGraphics(g);

        drawTitle(
            g,
            "Parallel Prefix Sum Speedup"
        );

        drawAxisLabels(
            g,
            "Dataset Size",
            "Speedup (x)"
        );

        int left = 110;
        int right = 1120;
        int top = 120;
        int bottom = 590;

        drawAxes(
            g,
            left,
            top,
            right,
            bottom
        );

        double maxValue =
            getMaximum(
                prefixSpeedup
            );

        drawYAxisScale(
            g,
            left,
            top,
            bottom,
            maxValue
        );

        drawLineChart(
            g,
            left,
            top,
            right,
            bottom,
            prefixSpeedup,
            maxValue
        );

        g.dispose();

        File output =
            new File(
                outputDirectory
                + "/prefix_speedup.png"
            );

        ImageIO.write(
            image,
            "png",
            output
        );

        System.out.println(
            "Created: " + output.getPath()
        );
    }

    // =========================================================
    // CREATE BASE IMAGE
    // =========================================================

    private static BufferedImage createBaseImage() {

        BufferedImage image =
            new BufferedImage(
                WIDTH,
                HEIGHT,
                BufferedImage.TYPE_INT_RGB
            );

        Graphics2D g =
            image.createGraphics();

        g.setColor(Color.WHITE);

        g.fillRect(
            0,
            0,
            WIDTH,
            HEIGHT
        );

        g.dispose();

        return image;
    }

    // =========================================================
    // GRAPHICS CONFIGURATION
    // =========================================================

    private static void configureGraphics(
        Graphics2D g
    ) {

        g.setRenderingHint(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON
        );

        g.setRenderingHint(
            RenderingHints.KEY_TEXT_ANTIALIASING,
            RenderingHints.VALUE_TEXT_ANTIALIAS_ON
        );
    }

    // =========================================================
    // TITLE
    // =========================================================

    private static void drawTitle(
        Graphics2D g,
        String title
    ) {

        g.setColor(Color.BLACK);

        g.setFont(
            new Font(
                "Arial",
                Font.BOLD,
                28
            )
        );

        int textWidth =
            g.getFontMetrics()
                .stringWidth(title);

        g.drawString(
            title,
            (WIDTH - textWidth) / 2,
            55
        );
    }

    // =========================================================
    // AXIS LABELS
    // =========================================================

    private static void drawAxisLabels(
        Graphics2D g,
        String xLabel,
        String yLabel
    ) {

        g.setFont(
            new Font(
                "Arial",
                Font.BOLD,
                16
            )
        );

        g.setColor(Color.BLACK);

        int xWidth =
            g.getFontMetrics()
                .stringWidth(xLabel);

        g.drawString(
            xLabel,
            (WIDTH - xWidth) / 2,
            650
        );

        g.rotate(
            -Math.PI / 2
        );

        int yWidth =
            g.getFontMetrics()
                .stringWidth(yLabel);

        g.drawString(
            yLabel,
            -(
                (HEIGHT + yWidth) / 2
            ),
            35
        );

        g.rotate(
            Math.PI / 2
        );
    }

    // =========================================================
    // AXES
    // =========================================================

    private static void drawAxes(
        Graphics2D g,
        int left,
        int top,
        int right,
        int bottom
    ) {

        g.setColor(Color.BLACK);

        g.setStroke(
            new BasicStroke(2)
        );

        g.drawLine(
            left,
            top,
            left,
            bottom
        );

        g.drawLine(
            left,
            bottom,
            right,
            bottom
        );

        // X-axis dataset labels
        g.setFont(
            new Font(
                "Arial",
                Font.PLAIN,
                13
            )
        );

        for (int i = 0;
             i < datasetSizes.length;
             i++) {

            int x =
                getXPosition(
                    i,
                    left,
                    right
                );

            String label =
                formatDatasetSize(
                    datasetSizes[i]
                );

            int width =
                g.getFontMetrics()
                    .stringWidth(label);

            g.drawString(
                label,
                x - width / 2,
                bottom + 25
            );
        }
    }

    // =========================================================
    // Y AXIS SCALE
    // =========================================================

    private static void drawYAxisScale(
        Graphics2D g,
        int left,
        int top,
        int bottom,
        double maxValue
    ) {

        g.setFont(
            new Font(
                "Arial",
                Font.PLAIN,
                12
            )
        );

        g.setColor(
            new Color(
                180,
                180,
                180
            )
        );

        int numberOfLines = 5;

        for (int i = 0;
             i <= numberOfLines;
             i++) {

            double value =
                maxValue
                * i
                / numberOfLines;

            int y =
                bottom
                - (
                    (int)
                    (
                        (bottom - top)
                        * i
                        / numberOfLines
                    )
                );

            g.drawLine(
                left,
                y,
                1120,
                y
            );

            g.setColor(Color.BLACK);

            String label =
                String.format(
                    "%.2f",
                    value
                );

            g.drawString(
                label,
                left - 55,
                y + 5
            );

            g.setColor(
                new Color(
                    180,
                    180,
                    180
                )
            );
        }
    }

    // =========================================================
    // BAR CHART
    // =========================================================

    private static void drawBarChart(
        Graphics2D g,
        int left,
        int top,
        int right,
        int bottom,
        double[] first,
        double[] second,
        double maxValue
    ) {

        int groupWidth =
            (
                right - left
            )
            / datasetSizes.length;

        int barWidth =
            groupWidth / 4;

        for (int i = 0;
             i < datasetSizes.length;
             i++) {

            int center =
                getXPosition(
                    i,
                    left,
                    right
                );

            // First bar
            int firstHeight =
                calculateHeight(
                    first[i],
                    maxValue,
                    top,
                    bottom
                );

            g.setColor(
                new Color(
                    70,
                    130,
                    180
                )
            );

            g.fillRect(
                center - barWidth - 4,
                bottom - firstHeight,
                barWidth,
                firstHeight
            );

            // Second bar
            int secondHeight =
                calculateHeight(
                    second[i],
                    maxValue,
                    top,
                    bottom
                );

            g.setColor(
                new Color(
                    220,
                    120,
                    60
                )
            );

            g.fillRect(
                center + 4,
                bottom - secondHeight,
                barWidth,
                secondHeight
            );
        }
    }

    // =========================================================
    // LINE CHART
    // =========================================================

    private static void drawLineChart(
        Graphics2D g,
        int left,
        int top,
        int right,
        int bottom,
        double[] values,
        double maxValue
    ) {

        g.setColor(
            new Color(
                50,
                100,
                180
            )
        );

        g.setStroke(
            new BasicStroke(
                4
            )
        );

        for (int i = 0;
             i < values.length - 1;
             i++) {

            int x1 =
                getXPosition(
                    i,
                    left,
                    right
                );

            int x2 =
                getXPosition(
                    i + 1,
                    left,
                    right
                );

            int y1 =
                getYPosition(
                    values[i],
                    maxValue,
                    top,
                    bottom
                );

            int y2 =
                getYPosition(
                    values[i + 1],
                    maxValue,
                    top,
                    bottom
                );

            g.drawLine(
                x1,
                y1,
                x2,
                y2
            );
        }

        // Draw data points
        for (int i = 0;
             i < values.length;
             i++) {

            int x =
                getXPosition(
                    i,
                    left,
                    right
                );

            int y =
                getYPosition(
                    values[i],
                    maxValue,
                    top,
                    bottom
                );

            g.fillOval(
                x - 7,
                y - 7,
                14,
                14
            );

            g.setColor(Color.BLACK);

            g.setFont(
                new Font(
                    "Arial",
                    Font.BOLD,
                    13
                )
            );

            g.drawString(
                String.format(
                    "%.2fx",
                    values[i]
                ),
                x - 20,
                y - 15
            );

            g.setColor(
                new Color(
                    50,
                    100,
                    180
                )
            );
        }
    }

    // =========================================================
    // LEGEND
    // =========================================================

    private static void drawLegend(
        Graphics2D g,
        String firstLabel,
        String secondLabel
    ) {

        int y = 95;

        g.setColor(
            new Color(
                70,
                130,
                180
            )
        );

        g.fillRect(
            430,
            y - 12,
            18,
            18
        );

        g.setColor(Color.BLACK);

        g.setFont(
            new Font(
                "Arial",
                Font.PLAIN,
                14
            )
        );

        g.drawString(
            firstLabel,
            455,
            y + 3
        );

        g.setColor(
            new Color(
                220,
                120,
                60
            )
        );

        g.fillRect(
            650,
            y - 12,
            18,
            18
        );

        g.setColor(Color.BLACK);

        g.drawString(
            secondLabel,
            675,
            y + 3
        );
    }

    // =========================================================
    // POSITION CALCULATIONS
    // =========================================================

    private static int getXPosition(
        int index,
        int left,
        int right
    ) {

        if (datasetSizes.length == 1) {
            return (left + right) / 2;
        }

        return left
            + (
                index
                * (right - left)
                / (datasetSizes.length - 1)
            );
    }

    private static int getYPosition(
        double value,
        double maxValue,
        int top,
        int bottom
    ) {

        if (maxValue <= 0) {
            return bottom;
        }

        return bottom
            - (
                (int)
                (
                    value
                    / maxValue
                    * (bottom - top)
                )
            );
    }

    private static int calculateHeight(
        double value,
        double maxValue,
        int top,
        int bottom
    ) {

        if (maxValue <= 0) {
            return 0;
        }

        return (int)
            (
                value
                / maxValue
                * (bottom - top)
            );
    }

    // =========================================================
    // MAXIMUM VALUE
    // =========================================================

    private static double getMaximum(
        double[] first,
        double[] second
    ) {

        double maximum = 0.0;

        for (double value : first) {

            if (value > maximum) {
                maximum = value;
            }
        }

        for (double value : second) {

            if (value > maximum) {
                maximum = value;
            }
        }

        return maximum * 1.10;
    }

    private static double getMaximum(
        double[] values
    ) {

        double maximum = 0.0;

        for (double value : values) {

            if (value > maximum) {
                maximum = value;
            }
        }

        return maximum * 1.10;
    }

    // =========================================================
    // DATASET LABEL
    // =========================================================

    private static String formatDatasetSize(
        int size
    ) {

        if (size >= 1_000_000) {
            return (size / 1_000_000) + "M";
        }

        if (size >= 1_000) {
            return (size / 1_000) + "K";
        }

        return String.valueOf(size);
    }
}