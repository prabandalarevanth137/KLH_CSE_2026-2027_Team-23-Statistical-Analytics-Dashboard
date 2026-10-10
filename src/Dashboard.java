import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

public class Dashboard extends JFrame {

    // =========================================================
    // WINDOW SETTINGS
    // =========================================================

    private static final int WINDOW_WIDTH = 1400;
    private static final int WINDOW_HEIGHT = 850;

    // =========================================================
    // DATASET INFORMATION
    // =========================================================

    private final String[] datasetFiles = {
        "data/sample.csv",
        "data/dataset_10000.csv",
        "data/dataset_100000.csv",
        "data/dataset_500000.csv",
        "data/dataset_1000000.csv"
    };

    private final String[] datasetNames = {
        "Sample Dataset",
        "10,000 Values",
        "100,000 Values",
        "500,000 Values",
        "1,000,000 Values"
    };

    // =========================================================
    // UI COMPONENTS
    // =========================================================

    private JComboBox<String> datasetSelector;

    // Top cards
    private JLabel valueCountLabel;
    private JLabel meanLabel;
    private JLabel varianceLabel;
    private JLabel minimumLabel;
    private JLabel maximumLabel;

    // Sequential statistics
    private JLabel sequentialMeanLabel;
    private JLabel sequentialVarianceLabel;
    private JLabel sequentialMinimumLabel;
    private JLabel sequentialMaximumLabel;

    // Parallel reduction
    private JLabel parallelMeanLabel;
    private JLabel parallelMinimumLabel;
    private JLabel parallelMaximumLabel;

    // Frequency table
    private JTable frequencyTable;

    // Status
    private JLabel statusLabel;

    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public Dashboard() {

        setTitle(
            "Statistical Analytics Dashboard"
        );

        setSize(
            WINDOW_WIDTH,
            WINDOW_HEIGHT
        );

        setMinimumSize(
            new Dimension(
                1100,
                700
            )
        );

        setLocationRelativeTo(null);

        setDefaultCloseOperation(
            JFrame.EXIT_ON_CLOSE
        );

        setLayout(
            new BorderLayout()
        );

        // Header
        add(
            createHeader(),
            BorderLayout.NORTH
        );

        // Main content
        add(
            createMainContent(),
            BorderLayout.CENTER
        );

        // Status bar
        add(
            createStatusBar(),
            BorderLayout.SOUTH
        );
    }

    // =========================================================
    // HEADER
    // =========================================================

    private JPanel createHeader() {

        JPanel header =
            new JPanel(
                new BorderLayout()
            );

        header.setBackground(
            new Color(
                35,
                55,
                80
            )
        );

        header.setBorder(
            BorderFactory.createEmptyBorder(
                15,
                20,
                15,
                20
            )
        );

        JLabel title =
            new JLabel(
                "STATISTICAL ANALYTICS DASHBOARD"
            );

        title.setForeground(
            Color.WHITE
        );

        title.setFont(
            new Font(
                "Arial",
                Font.BOLD,
                26
            )
        );

        header.add(
            title,
            BorderLayout.WEST
        );

        JPanel controls =
            new JPanel(
                new FlowLayout(
                    FlowLayout.RIGHT
                )
            );

        controls.setOpaque(false);

        JLabel datasetText =
            new JLabel(
                "Dataset:"
            );

        datasetText.setForeground(
            Color.WHITE
        );

        datasetText.setFont(
            new Font(
                "Arial",
                Font.BOLD,
                14
            )
        );

        datasetSelector =
            new JComboBox<>(
                datasetNames
            );

        datasetSelector.setPreferredSize(
            new Dimension(
                200,
                32
            )
        );

        JButton loadButton =
            new JButton(
                "Load Dataset"
            );

        loadButton.setPreferredSize(
            new Dimension(
                135,
                32
            )
        );

        loadButton.addActionListener(
            e -> loadSelectedDataset()
        );

        controls.add(
            datasetText
        );

        controls.add(
            datasetSelector
        );

        controls.add(
            loadButton
        );

        header.add(
            controls,
            BorderLayout.EAST
        );

        return header;
    }

    // =========================================================
    // MAIN CONTENT
    // =========================================================

    private JPanel createMainContent() {

        JPanel mainPanel =
            new JPanel(
                new BorderLayout(
                    10,
                    10
                )
            );

        mainPanel.setBorder(
            BorderFactory.createEmptyBorder(
                15,
                15,
                15,
                15
            )
        );

        // =====================================================
        // STATISTIC CARDS
        // =====================================================

        JPanel cards =
            new JPanel(
                new GridLayout(
                    1,
                    5,
                    10,
                    10
                )
            );

        JPanel valuesCard =
            createCard(
                "VALUES",
                "0",
                new Color(
                    70,
                    130,
                    180
                )
            );

        JPanel meanCard =
            createCard(
                "MEAN",
                "0",
                new Color(
                    60,
                    150,
                    100
                )
            );

        JPanel varianceCard =
            createCard(
                "VARIANCE",
                "0",
                new Color(
                    180,
                    120,
                    60
                )
            );

        JPanel minimumCard =
            createCard(
                "MINIMUM",
                "0",
                new Color(
                    130,
                    90,
                    160
                )
            );

        JPanel maximumCard =
            createCard(
                "MAXIMUM",
                "0",
                new Color(
                    180,
                    70,
                    80
                )
            );

        cards.add(valuesCard);
        cards.add(meanCard);
        cards.add(varianceCard);
        cards.add(minimumCard);
        cards.add(maximumCard);

        valueCountLabel =
            (JLabel) valuesCard.getClientProperty(
                "valueLabel"
            );

        meanLabel =
            (JLabel) meanCard.getClientProperty(
                "valueLabel"
            );

        varianceLabel =
            (JLabel) varianceCard.getClientProperty(
                "valueLabel"
            );

        minimumLabel =
            (JLabel) minimumCard.getClientProperty(
                "valueLabel"
            );

        maximumLabel =
            (JLabel) maximumCard.getClientProperty(
                "valueLabel"
            );

        mainPanel.add(
            cards,
            BorderLayout.NORTH
        );

        // =====================================================
        // TABS
        // =====================================================

        JTabbedPane tabs =
            new JTabbedPane();

        tabs.addTab(
            "Statistics",
            createStatisticsPanel()
        );

        tabs.addTab(
            "Frequency Distribution",
            createFrequencyPanel()
        );

        tabs.addTab(
            "Performance Graphs",
            createGraphsPanel()
        );

        mainPanel.add(
            tabs,
            BorderLayout.CENTER
        );

        return mainPanel;
    }

    // =========================================================
    // CARD CREATION
    // =========================================================

    private JPanel createCard(
        String title,
        String value,
        Color background
    ) {

        JPanel card =
            new JPanel(
                new BorderLayout()
            );

        card.setBackground(
            background
        );

        card.setBorder(
            BorderFactory.createEmptyBorder(
                10,
                10,
                10,
                10
            )
        );

        JLabel titleLabel =
            new JLabel(
                title,
                SwingConstants.CENTER
            );

        titleLabel.setForeground(
            Color.WHITE
        );

        titleLabel.setFont(
            new Font(
                "Arial",
                Font.BOLD,
                14
            )
        );

        JLabel valueLabel =
            new JLabel(
                value,
                SwingConstants.CENTER
            );

        valueLabel.setForeground(
            Color.WHITE
        );

        valueLabel.setFont(
            new Font(
                "Arial",
                Font.BOLD,
                22
            )
        );

        card.add(
            titleLabel,
            BorderLayout.NORTH
        );

        card.add(
            valueLabel,
            BorderLayout.CENTER
        );

        card.putClientProperty(
            "valueLabel",
            valueLabel
        );

        return card;
    }

    // =========================================================
    // STATISTICS PANEL
    // =========================================================

    private JPanel createStatisticsPanel() {

        JPanel panel =
            new JPanel(
                new BorderLayout(
                    15,
                    15
                )
            );

        panel.setBorder(
            BorderFactory.createEmptyBorder(
                20,
                20,
                20,
                20
            )
        );

        // =====================================================
        // SEQUENTIAL STATISTICS
        // =====================================================

        JPanel sequentialPanel =
            new JPanel(
                new GridLayout(
                    4,
                    2,
                    10,
                    15
                )
            );

        sequentialPanel.setBorder(
            BorderFactory.createTitledBorder(
                "Sequential Statistics"
            )
        );

        sequentialPanel.add(
            new JLabel(
                "Mean:"
            )
        );

        sequentialMeanLabel =
            createResultLabel();

        sequentialPanel.add(
            sequentialMeanLabel
        );

        sequentialPanel.add(
            new JLabel(
                "Variance:"
            )
        );

        sequentialVarianceLabel =
            createResultLabel();

        sequentialPanel.add(
            sequentialVarianceLabel
        );

        sequentialPanel.add(
            new JLabel(
                "Minimum:"
            )
        );

        sequentialMinimumLabel =
            createResultLabel();

        sequentialPanel.add(
            sequentialMinimumLabel
        );

        sequentialPanel.add(
            new JLabel(
                "Maximum:"
            )
        );

        sequentialMaximumLabel =
            createResultLabel();

        sequentialPanel.add(
            sequentialMaximumLabel
        );

        // =====================================================
        // PARALLEL REDUCTION
        // =====================================================

        JPanel parallelPanel =
            new JPanel(
                new GridLayout(
                    3,
                    2,
                    10,
                    15
                )
            );

        parallelPanel.setBorder(
            BorderFactory.createTitledBorder(
                "Parallel Reduction"
            )
        );

        parallelPanel.add(
            new JLabel(
                "Parallel Mean:"
            )
        );

        parallelMeanLabel =
            createResultLabel();

        parallelPanel.add(
            parallelMeanLabel
        );

        parallelPanel.add(
            new JLabel(
                "Parallel Minimum:"
            )
        );

        parallelMinimumLabel =
            createResultLabel();

        parallelPanel.add(
            parallelMinimumLabel
        );

        parallelPanel.add(
            new JLabel(
                "Parallel Maximum:"
            )
        );

        parallelMaximumLabel =
            createResultLabel();

        parallelPanel.add(
            parallelMaximumLabel
        );

        // =====================================================
        // COMBINE
        // =====================================================

        JPanel top =
            new JPanel(
                new GridLayout(
                    1,
                    2,
                    20,
                    20
                )
            );

        top.add(
            sequentialPanel
        );

        top.add(
            parallelPanel
        );

        panel.add(
            top,
            BorderLayout.NORTH
        );

        JLabel information =
            new JLabel(
                "<html>"
                + "<center>"
                + "<h1>Parallel Statistical Analysis</h1>"
                + "<p>"
                + "The dashboard calculates descriptive statistics "
                + "and compares sequential and parallel processing."
                + "</p>"
                + "</center>"
                + "</html>",
                SwingConstants.CENTER
            );

        information.setFont(
            new Font(
                "Arial",
                Font.PLAIN,
                16
            )
        );

        panel.add(
            information,
            BorderLayout.CENTER
        );

        return panel;
    }

    // =========================================================
    // RESULT LABEL
    // =========================================================

    private JLabel createResultLabel() {

        JLabel label =
            new JLabel(
                "0.0000"
            );

        label.setFont(
            new Font(
                "Arial",
                Font.BOLD,
                15
            )
        );

        return label;
    }

    // =========================================================
    // FREQUENCY DISTRIBUTION
    // =========================================================

    private JPanel createFrequencyPanel() {

        JPanel panel =
            new JPanel(
                new BorderLayout()
            );

        panel.setBorder(
            BorderFactory.createEmptyBorder(
                15,
                15,
                15,
                15
            )
        );

        frequencyTable =
            new JTable();

        frequencyTable.setRowHeight(
            25
        );

        JScrollPane scrollPane =
            new JScrollPane(
                frequencyTable
            );

        panel.add(
            scrollPane,
            BorderLayout.CENTER
        );

        return panel;
    }

    // =========================================================
    // PERFORMANCE GRAPHS
    // =========================================================

    private JPanel createGraphsPanel() {

        JPanel panel =
            new JPanel(
                new GridLayout(
                    2,
                    2,
                    10,
                    10
                )
            );

        panel.setBorder(
            BorderFactory.createEmptyBorder(
                10,
                10,
                10,
                10
            )
        );

        panel.add(
            createGraphPanel(
                "Sum Performance",
                "results/graphs/sum_performance.png"
            )
        );

        panel.add(
            createGraphPanel(
                "Prefix Sum Performance",
                "results/graphs/prefix_performance.png"
            )
        );

        panel.add(
            createGraphPanel(
                "Reduction Speedup",
                "results/graphs/reduction_speedup.png"
            )
        );

        panel.add(
            createGraphPanel(
                "Prefix Sum Speedup",
                "results/graphs/prefix_speedup.png"
            )
        );

        return panel;
    }

    // =========================================================
    // GRAPH PANEL
    // =========================================================

    private JPanel createGraphPanel(
        String title,
        String imagePath
    ) {

        JPanel panel =
            new JPanel(
                new BorderLayout()
            );

        panel.setBorder(
            BorderFactory.createTitledBorder(
                title
            )
        );

        JLabel imageLabel =
            new JLabel(
                "Graph not found",
                SwingConstants.CENTER
            );

        File imageFile =
            new File(
                imagePath
            );

        if (imageFile.exists()) {

            ImageIcon icon =
                new ImageIcon(
                    imagePath
                );

            Image scaledImage =
                icon.getImage()
                    .getScaledInstance(
                        550,
                        300,
                        Image.SCALE_SMOOTH
                    );

            imageLabel.setIcon(
                new ImageIcon(
                    scaledImage
                )
            );

            imageLabel.setText(
                ""
            );
        }

        panel.add(
            imageLabel,
            BorderLayout.CENTER
        );

        return panel;
    }

    // =========================================================
    // STATUS BAR
    // =========================================================

    private JPanel createStatusBar() {

        JPanel panel =
            new JPanel(
                new FlowLayout(
                    FlowLayout.LEFT
                )
            );

        panel.setBorder(
            BorderFactory.createEtchedBorder()
        );

        statusLabel =
            new JLabel(
                "Ready"
            );

        panel.add(
            statusLabel
        );

        return panel;
    }

    // =========================================================
    // RESOLVE DATASET PATH
    // =========================================================

    private File resolveDatasetFile(
        String relativePath
    ) {

        // First try current project directory.
        File file =
            new File(
                relativePath
            );

        if (file.exists()) {
            return file;
        }

        // Try parent directory.
        File parent =
            new File(
                "..",
                relativePath
            );

        if (parent.exists()) {
            return parent;
        }

        // Try two levels above.
        File parentTwo =
            new File(
                "../..",
                relativePath
            );

        if (parentTwo.exists()) {
            return parentTwo;
        }

        return file;
    }

    // =========================================================
    // LOAD SELECTED DATASET
    // =========================================================

    private void loadSelectedDataset() {

        int index =
            datasetSelector.getSelectedIndex();

        if (index < 0) {
            return;
        }

        String relativePath =
            datasetFiles[index];

        File datasetFile =
            resolveDatasetFile(
                relativePath
            );

        if (!datasetFile.exists()) {

            statusLabel.setText(
                "Dataset file not found."
            );

            JOptionPane.showMessageDialog(
                this,
                "Dataset file was not found:\n\n"
                + relativePath
                + "\n\n"
                + "Make sure the data folder is inside "
                + "the project folder.",
                "Dataset Loading Error",
                JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        try {

            statusLabel.setText(
                "Loading "
                + datasetNames[index]
                + "..."
            );

            double[] data =
                DatasetLoader.loadDataset(
                    datasetFile.getPath()
                );

            if (data == null) {

                throw new IOException(
                    "DatasetLoader returned null."
                );
            }

            if (data.length == 0) {

                throw new IOException(
                    "Dataset is empty."
                );
            }

            // =================================================
            // UPDATE STATISTICS
            // =================================================

            updateStatistics(
                data
            );

            // =================================================
            // UPDATE FREQUENCY
            // =================================================

            updateFrequencyDistribution(
                data
            );

            statusLabel.setText(
                "Loaded "
                + data.length
                + " values successfully."
            );

        } catch (Exception e) {

            statusLabel.setText(
                "Error loading dataset."
            );

            JOptionPane.showMessageDialog(
                this,
                "Could not load dataset:\n\n"
                + datasetFile.getAbsolutePath()
                + "\n\nError:\n"
                + e.getMessage(),
                "Dataset Loading Error",
                JOptionPane.ERROR_MESSAGE
            );

            e.printStackTrace();
        }
    }

    // =========================================================
    // UPDATE STATISTICS
    // =========================================================

    private void updateStatistics(
        double[] data
    ) {

        // =====================================================
        // SEQUENTIAL
        // =====================================================

        double mean =
            SequentialStatistics.calculateMean(
                data
            );

        double variance =
            SequentialStatistics.calculateVariance(
                data
            );

        double minimum =
            SequentialStatistics.findMinimum(
                data
            );

        double maximum =
            SequentialStatistics.findMaximum(
                data
            );

        // =====================================================
        // PARALLEL
        // =====================================================

        double parallelMean =
            ParallelReduction.calculateMean(
                data
            );

        double parallelMinimum =
            ParallelReduction.findMinimum(
                data
            );

        double parallelMaximum =
            ParallelReduction.findMaximum(
                data
            );

        // =====================================================
        // TOP CARDS
        // =====================================================

        valueCountLabel.setText(
            String.valueOf(
                data.length
            )
        );

        meanLabel.setText(
            formatValue(
                mean
            )
        );

        varianceLabel.setText(
            formatValue(
                variance
            )
        );

        minimumLabel.setText(
            formatValue(
                minimum
            )
        );

        maximumLabel.setText(
            formatValue(
                maximum
            )
        );

        // =====================================================
        // SEQUENTIAL PANEL
        // =====================================================

        sequentialMeanLabel.setText(
            formatValue(
                mean
            )
        );

        sequentialVarianceLabel.setText(
            formatValue(
                variance
            )
        );

        sequentialMinimumLabel.setText(
            formatValue(
                minimum
            )
        );

        sequentialMaximumLabel.setText(
            formatValue(
                maximum
            )
        );

        // =====================================================
        // PARALLEL PANEL
        // =====================================================

        parallelMeanLabel.setText(
            formatValue(
                parallelMean
            )
        );

        parallelMinimumLabel.setText(
            formatValue(
                parallelMinimum
            )
        );

        parallelMaximumLabel.setText(
            formatValue(
                parallelMaximum
            )
        );
    }

    // =========================================================
    // FORMAT VALUE
    // =========================================================

    private String formatValue(
        double value
    ) {

        return String.format(
            "%.4f",
            value
        );
    }

    // =========================================================
    // FREQUENCY DISTRIBUTION
    // =========================================================

    private void updateFrequencyDistribution(
        double[] data
    ) {

        Map<Double, Integer> frequency =
            FrequencyDistribution.calculateFrequency(
                data
            );

        int rowCount =
            Math.min(
                frequency.size(),
                100
            );

        Object[][] tableData =
            new Object[
                rowCount
            ][2];

        int row = 0;

        for (
            Map.Entry<Double, Integer> entry :
            frequency.entrySet()
        ) {

            if (row >= rowCount) {
                break;
            }

            tableData[row][0] =
                entry.getKey();

            tableData[row][1] =
                entry.getValue();

            row++;
        }

        frequencyTable.setModel(
            new DefaultTableModel(
                tableData,
                new String[] {
                    "Value",
                    "Frequency"
                }
            ) {

                @Override
                public boolean isCellEditable(
                    int row,
                    int column
                ) {

                    return false;
                }
            }
        );
    }

    // =========================================================
    // MAIN METHOD
    // =========================================================

    public static void main(
        String[] args
    ) {

        SwingUtilities.invokeLater(
            () -> {

                Dashboard dashboard =
                    new Dashboard();

                dashboard.setVisible(
                    true
                );

                // Automatically load Sample Dataset
                dashboard.loadSelectedDataset();
            }
        );
    }
}