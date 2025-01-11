import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;

public class LifeExpectancyDashboard extends JFrame {

    // Sample data
    private static final String[] COUNTRIES = {"Australia", "China", "India", "United States of America"};
    private static final int[] YEARS = {2010, 2015, 2019};

    // Data structure for life expectancy and HALE
    private static final double[][][] LIFE_EXPECTANCY = {
            // Australia
            {
                    {81.9, 80.4, 79.8}, // Life Expectancy at Birth (Both sexes, Male, Female)
                    {24.7, 23.3, 26.1}, // Life Expectancy at Age 60 (Both sexes, Male, Female)
                    {70.2, 69.2, 71.2}, // HALE at Birth (Both sexes, Male, Female)
                    {18.4, 17.5, 19.3}  // HALE at Age 60 (Both sexes, Male, Female)
            },
            // China
            {
                    {74.9, 73.9, 72.3},
                    {19.6, 17.9, 21.5},
                    {66.7, 65.3, 68.2},
                    {14.9, 14.0, 15.9}
            },
            // India
            {
                    {67.2, 65.7, 68.9},
                    {18.0, 16.9, 19.0},
                    {57.3, 57.0, 57.6},
                    {12.6, 12.1, 13.0}
            },
            // United States of America
            {
                    {78.6, 76.3, 80.8},
                    {23.0, 21.5, 24.2},
                    {66.7, 65.7, 67.7},
                    {16.5, 15.6, 17.2}
            }
    };

    // GUI Components
    private JComboBox<String> countrySelector;
    private JPanel visualizationPanel;
    private JTextArea storyArea;
    private JPanel buttonPanel;
    private CardLayout cardLayout;
    private JPanel mainPanel;

    // Constructor
    public LifeExpectancyDashboard() {
        setTitle("Life Expectancy Dashboard");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Initialize Components
        initializeComponents();

        // Setup Card Layout for switching between screens
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // About Screen Panel
        JPanel aboutPanel = createAboutPanel();

        // Add the aboutPanel and dashboard to the card layout
        mainPanel.add(aboutPanel, "About");
        mainPanel.add(createDashboardPanel(), "Dashboard");

        // Add the mainPanel (with card layout) to the frame
        add(mainPanel);
    }

    private void initializeComponents() {
        // Create a country selector
        countrySelector = new JComboBox<>(COUNTRIES);
        countrySelector.setSelectedIndex(0);

        // Add action listener to update the visualization when a country is selected
        countrySelector.addActionListener(e -> showVisualizationForCountry((String) countrySelector.getSelectedItem()));

        // Create Text Area for storytelling
        storyArea = new JTextArea();
        storyArea.setEditable(false);
        storyArea.setLineWrap(true);
        storyArea.setWrapStyleWord(true);
        storyArea.setPreferredSize(new Dimension(600, 400));
        storyArea.setFont(new Font("Arial", Font.PLAIN, 16));
        storyArea.setMargin(new Insets(20, 20, 20, 20));

        // Create Visualization Panel
        visualizationPanel = new JPanel(new BorderLayout());
        visualizationPanel.add(storyArea, BorderLayout.EAST);

        // Create Button Panel
        buttonPanel = new JPanel(new GridLayout(1, 6, 10, 10));

        JButton barChartButton = new JButton("Bar Chart");
        JButton lineChartButton = new JButton("Line Chart");
        JButton pieChartButton = new JButton("Pie Chart");
        JButton scatterPlotButton = new JButton("Scatter Plot");
        JButton aboutButton = new JButton("About");
        JButton viewDatasetButton = new JButton("View Dataset");

        barChartButton.addActionListener(e -> showVisualization("Bar Chart"));
        lineChartButton.addActionListener(e -> showVisualization("Line Chart"));
        pieChartButton.addActionListener(e -> showVisualization("Pie Chart"));
        scatterPlotButton.addActionListener(e -> showVisualization("Scatter Plot"));
        aboutButton.addActionListener(e -> showAboutInTextBox());
        viewDatasetButton.addActionListener(e -> showDataset());

        buttonPanel.add(countrySelector);
        buttonPanel.add(barChartButton);
        buttonPanel.add(lineChartButton);
        buttonPanel.add(pieChartButton);
        buttonPanel.add(scatterPlotButton);
        buttonPanel.add(viewDatasetButton);
        buttonPanel.add(aboutButton);
    }

    private JPanel createDashboardPanel() {
        JPanel dashboardPanel = new JPanel(new BorderLayout());

        // Add button panel at the top
        dashboardPanel.add(buttonPanel, BorderLayout.NORTH);
        // Add visualization panel to the center
        dashboardPanel.add(visualizationPanel, BorderLayout.CENTER);

        return dashboardPanel;
    }

    private JPanel createAboutPanel() {
        JPanel aboutPanel = new JPanel(new BorderLayout());

        // Create a JLabel with an image background
        JLabel backgroundLabel = new JLabel();
        backgroundLabel.setIcon(new ImageIcon("background.jpeg")); // Ensure this path is correct for the image
        backgroundLabel.setLayout(new BorderLayout());

        // Create a text area for the "About" information
        JTextArea aboutTextArea = new JTextArea();
        aboutTextArea.setEditable(false);
        aboutTextArea.setLineWrap(true);
        aboutTextArea.setWrapStyleWord(true);
        aboutTextArea.setFont(new Font("Arial", Font.PLAIN, 18));
        aboutTextArea.setMargin(new Insets(20, 20, 20, 20));

        // Detailed information about the dashboard
        String aboutText = " Life Expectancy Dashboard\n\n" +
                           "This dashboard visualizes life expectancy and Healthy Life Expectancy (HALE) metrics across various countries and years. The data provided helps analyze health trends and the quality of life in different regions.\n\n" +
                           "Metrics:\n" +
                           "*Life Expectancy at Birth: The average number of years a newborn is expected to live if current mortality rates persist throughout their lifetime.\n" +
                           "*Life Expectancy at Age 60: The average number of additional years a person who has reached age 60 is expected to live.\n" +
                           "*Healthy Life Expectancy (HALE) at Birth: The number of years a newborn is expected to live in good health.\n" +
                           "*Healthy Life Expectancy (HALE) at Age 60: The number of additional years a person aged 60 is expected to live in good health.\n\n" +
                           "Use the buttons and charts to explore different aspects of the life expectancy and HALE data to gain insights into health trends globally.";

        aboutTextArea.setText(aboutText);

        // Create the "View" button
        JButton viewButton = new JButton("View Dashboard");
        viewButton.setFont(new Font("Arial", Font.BOLD, 20));
        viewButton.addActionListener(e -> cardLayout.show(mainPanel, "Dashboard"));

        // Add components to the backgroundLabel (which acts as a container)
        backgroundLabel.add(new JScrollPane(aboutTextArea), BorderLayout.CENTER);
        backgroundLabel.add(viewButton, BorderLayout.SOUTH);

        // Add the backgroundLabel to the aboutPanel
        aboutPanel.add(backgroundLabel, BorderLayout.CENTER);

        return aboutPanel;
    }

    private void showVisualizationForCountry(String country) {
        String selectedCountry = (String) countrySelector.getSelectedItem();
        if (selectedCountry.equals(country)) {
            showVisualization("Bar Chart"); // Default to Bar Chart for simplicity
        }
    }

    private void showVisualization(String type) {
        JFreeChart chart = null;
        String country = (String) countrySelector.getSelectedItem();

        if (country.equals("All Countries")) {
            switch (type) {
                case "Bar Chart":
                    chart = createAllCountriesBarChart();
                    storyArea.setText(getAllCountriesStory("Bar Chart"));
                    break;
                case "Line Chart":
                    chart = createAllCountriesLineChart();
                    storyArea.setText(getAllCountriesStory("Line Chart"));
                    break;
                case "Pie Chart":
                    chart = createAllCountriesPieChart();
                    storyArea.setText(getAllCountriesStory("Pie Chart"));
                    break;
                case "Scatter Plot":
                    chart = createAllCountriesScatterPlot();
                    storyArea.setText(getAllCountriesStory("Scatter Plot"));
                    break;
            }
        } else {
            switch (type) {
                case "Bar Chart":
                    chart = createCountryBarChart(country);
                    storyArea.setText(getCountryStory(country, "Bar Chart"));
                    break;
                case "Line Chart":
                    chart = createCountryLineChart(country);
                    storyArea.setText(getCountryStory(country, "Line Chart"));
                    break;
                case "Pie Chart":
                    chart = createCountryPieChart(country);
                    storyArea.setText(getCountryStory(country, "Pie Chart"));
                    break;
                case "Scatter Plot":
                    chart = createCountryScatterPlot(country);
                    storyArea.setText(getCountryStory(country, "Scatter Plot"));
                    break;
            }
        }

        if (chart != null) {
            ChartPanel chartPanel = new ChartPanel(chart);
            chartPanel.setPreferredSize(new Dimension(800, 600));
            visualizationPanel.removeAll();
            visualizationPanel.add(chartPanel, BorderLayout.CENTER);
            visualizationPanel.add(new JScrollPane(storyArea), BorderLayout.EAST);
            visualizationPanel.revalidate();
        }
    }


    private JFreeChart createCountryBarChart(String country) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        int countryIndex = getCountryIndex(country);

        // Populate dataset
        for (int i = 0; i < YEARS.length; i++) {
            dataset.addValue(LIFE_EXPECTANCY[countryIndex][0][i], "Life Expectancy at Birth", String.valueOf(YEARS[i]));
            dataset.addValue(LIFE_EXPECTANCY[countryIndex][1][i], "Life Expectancy at Age 60", String.valueOf(YEARS[i]));
            dataset.addValue(LIFE_EXPECTANCY[countryIndex][2][i], "HALE at Birth", String.valueOf(YEARS[i]));
            dataset.addValue(LIFE_EXPECTANCY[countryIndex][3][i], "HALE at Age 60", String.valueOf(YEARS[i]));
        }

        // Create the chart with labels and legends
        JFreeChart chart = ChartFactory.createBarChart(
                "Life Expectancy and HALE for " + country, // Chart title
                "Year",                                  // X-axis label
                "Value",                                 // Y-axis label
                dataset,                                 // Dataset
                PlotOrientation.VERTICAL,                // Chart orientation
                true,                                    // Include legend
                true,                                    // Tooltips
                false                                    // URLs
        );

        // Customize plot (optional)
        CategoryPlot plot = chart.getCategoryPlot();
        plot.setRangeGridlinesVisible(true);  // Show grid lines

        return chart;
    }

    private JFreeChart createAllCountriesBarChart() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        // Populate dataset for all countries
        for (int i = 0; i < COUNTRIES.length; i++) {
            for (int j = 0; j < YEARS.length; j++) {
                dataset.addValue(LIFE_EXPECTANCY[i][0][j], "Life Expectancy at Birth", COUNTRIES[i] + " - " + YEARS[j]);
                dataset.addValue(LIFE_EXPECTANCY[i][1][j], "Life Expectancy at Age 60", COUNTRIES[i] + " - " + YEARS[j]);
                dataset.addValue(LIFE_EXPECTANCY[i][2][j], "HALE at Birth", COUNTRIES[i] + " - " + YEARS[j]);
                dataset.addValue(LIFE_EXPECTANCY[i][3][j], "HALE at Age 60", COUNTRIES[i] + " - " + YEARS[j]);
            }
        }

        // Create the chart with labels and legends
        JFreeChart chart = ChartFactory.createBarChart(
                "Life Expectancy and HALE for All Countries",
                "Country - Year",
                "Value",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false
        );

        // Customize plot (optional)
        CategoryPlot plot = chart.getCategoryPlot();
        plot.setRangeGridlinesVisible(true);

        return chart;
    }

    private JFreeChart createCountryLineChart(String country) {
        XYSeriesCollection dataset = new XYSeriesCollection();
        int countryIndex = getCountryIndex(country);

        // Series for each metric
        XYSeries lifeExpectancyAtBirthSeries = new XYSeries("Life Expectancy at Birth");
        XYSeries lifeExpectancyAtAge60Series = new XYSeries("Life Expectancy at Age 60");
        XYSeries haleAtBirthSeries = new XYSeries("HALE at Birth");
        XYSeries haleAtAge60Series = new XYSeries("HALE at Age 60");

        // Populate dataset
        for (int i = 0; i < YEARS.length; i++) {
            lifeExpectancyAtBirthSeries.add(YEARS[i], LIFE_EXPECTANCY[countryIndex][0][i]);
            lifeExpectancyAtAge60Series.add(YEARS[i], LIFE_EXPECTANCY[countryIndex][1][i]);
            haleAtBirthSeries.add(YEARS[i], LIFE_EXPECTANCY[countryIndex][2][i]);
            haleAtAge60Series.add(YEARS[i], LIFE_EXPECTANCY[countryIndex][3][i]);
        }

        // Add series to dataset
        dataset.addSeries(lifeExpectancyAtBirthSeries);
        dataset.addSeries(lifeExpectancyAtAge60Series);
        dataset.addSeries(haleAtBirthSeries);
        dataset.addSeries(haleAtAge60Series);

        // Create chart with labels and legends
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Life Expectancy and HALE Trends for " + country,  // Chart title
                "Year",                                            // X-axis label
                "Value",                                           // Y-axis label
                dataset,                                           // Dataset
                PlotOrientation.VERTICAL,                          // Chart orientation
                true,                                              // Include legend
                true,                                              // Tooltips
                false                                              // URLs
        );

        return chart;
    }

    private JFreeChart createAllCountriesLineChart() {
        XYSeriesCollection dataset = new XYSeriesCollection();

        // Populate dataset for all countries
        for (int i = 0; i < COUNTRIES.length; i++) {
            XYSeries lifeExpectancyAtBirthSeries = new XYSeries(COUNTRIES[i] + " Life Expectancy at Birth");
            XYSeries lifeExpectancyAtAge60Series = new XYSeries(COUNTRIES[i] + " Life Expectancy at Age 60");
            XYSeries haleAtBirthSeries = new XYSeries(COUNTRIES[i] + " HALE at Birth");
            XYSeries haleAtAge60Series = new XYSeries(COUNTRIES[i] + " HALE at Age 60");

            for (int j = 0; j < YEARS.length; j++) {
                lifeExpectancyAtBirthSeries.add(YEARS[j], LIFE_EXPECTANCY[i][0][j]);
                lifeExpectancyAtAge60Series.add(YEARS[j], LIFE_EXPECTANCY[i][1][j]);
                haleAtBirthSeries.add(YEARS[j], LIFE_EXPECTANCY[i][2][j]);
                haleAtAge60Series.add(YEARS[j], LIFE_EXPECTANCY[i][3][j]);
            }

            dataset.addSeries(lifeExpectancyAtBirthSeries);
            dataset.addSeries(lifeExpectancyAtAge60Series);
            dataset.addSeries(haleAtBirthSeries);
            dataset.addSeries(haleAtAge60Series);
        }

        // Create chart with labels and legends
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Life Expectancy and HALE Trends for All Countries",
                "Year",
                "Value",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false
        );

        return chart;
    }


    private JFreeChart createCountryPieChart(String country) {
        DefaultPieDataset dataset = new DefaultPieDataset();
        int countryIndex = getCountryIndex(country);

        // Populate dataset
        dataset.setValue("Life Expectancy at Birth", LIFE_EXPECTANCY[countryIndex][0][0]);
        dataset.setValue("Life Expectancy at Age 60", LIFE_EXPECTANCY[countryIndex][1][0]);
        dataset.setValue("HALE at Birth", LIFE_EXPECTANCY[countryIndex][2][0]);
        dataset.setValue("HALE at Age 60", LIFE_EXPECTANCY[countryIndex][3][0]);

        // Create chart with labels and legends
        JFreeChart chart = ChartFactory.createPieChart(
                "Life Expectancy and HALE Distribution for " + country,
                dataset,
                true,   // Include legend
                true,   // Tooltips
                false   // URLs
        );

        return chart;
    }

    private JFreeChart createAllCountriesPieChart() {
        DefaultPieDataset dataset = new DefaultPieDataset();

        // Populate dataset for all countries
        for (int i = 0; i < COUNTRIES.length; i++) {
            for (int j = 0; j < YEARS.length; j++) {
                dataset.setValue(COUNTRIES[i] + " - " + YEARS[j] + " Life Expectancy at Birth", LIFE_EXPECTANCY[i][0][j]);
                dataset.setValue(COUNTRIES[i] + " - " + YEARS[j] + " Life Expectancy at Age 60", LIFE_EXPECTANCY[i][1][j]);
                dataset.setValue(COUNTRIES[i] + " - " + YEARS[j] + " HALE at Birth", LIFE_EXPECTANCY[i][2][j]);
                dataset.setValue(COUNTRIES[i] + " - " + YEARS[j] + " HALE at Age 60", LIFE_EXPECTANCY[i][3][j]);
            }
        }

        // Create chart with labels and legends
        JFreeChart chart = ChartFactory.createPieChart(
                "Life Expectancy and HALE Distribution for All Countries",
                dataset,
                true,   // Include legend
                true,   // Tooltips
                false   // URLs
        );

        return chart;
    }

    private JFreeChart createCountryScatterPlot(String country) {
        XYSeriesCollection dataset = new XYSeriesCollection();
        int countryIndex = getCountryIndex(country);

        XYSeries lifeExpectancyAtBirthSeries = new XYSeries("Life Expectancy at Birth");
        XYSeries lifeExpectancyAtAge60Series = new XYSeries("Life Expectancy at Age 60");
        XYSeries haleAtBirthSeries = new XYSeries("HALE at Birth");
        XYSeries haleAtAge60Series = new XYSeries("HALE at Age 60");

        for (int i = 0; i < YEARS.length; i++) {
            lifeExpectancyAtBirthSeries.add(YEARS[i], LIFE_EXPECTANCY[countryIndex][0][i]);
            lifeExpectancyAtAge60Series.add(YEARS[i], LIFE_EXPECTANCY[countryIndex][1][i]);
            haleAtBirthSeries.add(YEARS[i], LIFE_EXPECTANCY[countryIndex][2][i]);
            haleAtAge60Series.add(YEARS[i], LIFE_EXPECTANCY[countryIndex][3][i]);
        }

        dataset.addSeries(lifeExpectancyAtBirthSeries);
        dataset.addSeries(lifeExpectancyAtAge60Series);
        dataset.addSeries(haleAtBirthSeries);
        dataset.addSeries(haleAtAge60Series);

        return ChartFactory.createScatterPlot(
                "Life Expectancy and HALE Scatter Plot for " + country,
                "Year",
                "Value",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false);
    }

    private JFreeChart createAllCountriesScatterPlot() {
        XYSeriesCollection dataset = new XYSeriesCollection();
        for (int i = 0; i < COUNTRIES.length; i++) {
            XYSeries lifeExpectancyAtBirthSeries = new XYSeries(COUNTRIES[i] + " Life Expectancy at Birth");
            XYSeries lifeExpectancyAtAge60Series = new XYSeries(COUNTRIES[i] + " Life Expectancy at Age 60");
            XYSeries haleAtBirthSeries = new XYSeries(COUNTRIES[i] + " HALE at Birth");
            XYSeries haleAtAge60Series = new XYSeries(COUNTRIES[i] + " HALE at Age 60");

            for (int j = 0; j < YEARS.length; j++) {
                lifeExpectancyAtBirthSeries.add(YEARS[j], LIFE_EXPECTANCY[i][0][j]);
                lifeExpectancyAtAge60Series.add(YEARS[j], LIFE_EXPECTANCY[i][1][j]);
                haleAtBirthSeries.add(YEARS[j], LIFE_EXPECTANCY[i][2][j]);
                haleAtAge60Series.add(YEARS[j], LIFE_EXPECTANCY[i][3][j]);
            }

            dataset.addSeries(lifeExpectancyAtBirthSeries);
            dataset.addSeries(lifeExpectancyAtAge60Series);
            dataset.addSeries(haleAtBirthSeries);
            dataset.addSeries(haleAtAge60Series);
        }

        return ChartFactory.createScatterPlot(
                "Life Expectancy and HALE Scatter Plot for All Countries",
                "Year",
                "Value",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false);
    }

    private int getCountryIndex(String country) {
        for (int i = 0; i < COUNTRIES.length; i++) {
            if (COUNTRIES[i].equals(country)) {
                return i;
            }
        }
        return -1; // Default value if country not found
    }

    private void showAboutInTextBox() {
        cardLayout.show(mainPanel, "About");
    }

    private void showDataset() {
        // Create a new JFrame for the dataset window
        JFrame datasetFrame = new JFrame("Life Expectancy Dataset");
        datasetFrame.setSize(800, 600);
        datasetFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close only the dataset window

        // Column names for the table
        String[] columns = {"Country", "Year", "Life Expectancy at Birth", "Life Expectancy at Age 60", "HALE at Birth", "HALE at Age 60"};

        // Data to be filled in the table
        Object[][] data = new Object[COUNTRIES.length * YEARS.length][6];
        int row = 0;

        // Populate data array with life expectancy values for each country and year
        for (int i = 0; i < COUNTRIES.length - 1; i++) {  // Exclude 'All Countries'
            for (int j = 0; j < YEARS.length; j++) {
                data[row][0] = COUNTRIES[i];  // Country
                data[row][1] = YEARS[j];      // Year
                data[row][2] = LIFE_EXPECTANCY[i][0][j];  // Life Expectancy at Birth
                data[row][3] = LIFE_EXPECTANCY[i][1][j];  // Life Expectancy at Age 60
                data[row][4] = LIFE_EXPECTANCY[i][2][j];  // HALE at Birth
                data[row][5] = LIFE_EXPECTANCY[i][3][j];  // HALE at Age 60
                row++;
            }
        }

        // Create the JTable with the data and column names
        JTable table = new JTable(data, columns);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS); // Make columns fit the window
        table.setFillsViewportHeight(true); // Make sure the table fills the viewport

        // Add the table to a JScrollPane to make it scrollable
        JScrollPane scrollPane = new JScrollPane(table);

        // Add the scrollPane to the datasetFrame
        datasetFrame.add(scrollPane);

        // Make the dataset window visible
        datasetFrame.setVisible(true);
    }

    private String getCountryStory(String country, String chartType) {
        // Add stories for different charts and countries
        switch (country) {
            case "Australia":
                return switch (chartType) {
                    case "Bar Chart" -> "The bar chart for Australia demonstrates a gradual increase in life expectancy at birth from 81.9 years in 2010 to 83 years in 2019, indicating improved overall longevity. Life expectancy at age 60 also shows an upward trend, moving from 24.7 years in 2010 to 25.6 years in 2019, suggesting that Australians are living longer even in their senior years. Healthy Life Expectancy (HALE) at birth rose from 70.2 years to 70.9 years, reflecting an increase in the years Australians spend in good health. At the same time, HALE at age 60 increased from 18.4 years to 19 years, highlighting a slight but significant improvement in the health quality of the elderly population.";
                    case "Line Chart" -> "The line chart for Australia shows a clear positive trend in life expectancy at birth, which increased from 81.9 years in 2010 to 83 years in 2019. This trend underscores Australia's progress in extending lifespan. Life expectancy at age 60 also shows a consistent rise from 24.7 years to 25.6 years, indicating enhanced quality of life for older Australians. The increase in HALE at birth from 70.2 years to 70.9 years and the rise in HALE at age 60 from 18.4 years to 19 years suggest improvements in health standards, contributing to better overall health outcomes.";
                    case "Pie Chart" -> "The pie chart for Australia in 2019 illustrates that the highest value is life expectancy at birth, standing at 83 years, indicating a robust lifespan. HALE at birth is substantial at 70.9 years, suggesting that most of these years are spent in good health. Life expectancy at age 60 is 25.6 years, and HALE at age 60 is 19 years, showing that Australians maintain a good quality of life well into their senior years. This distribution reflects Australia's strong health care system and high standards of living.";
                    case "Scatter Plot" -> "The scatter plot for Australia displays a positive trajectory in both life expectancy and HALE metrics. Life expectancy at birth has steadily increased from 81.9 years in 2010 to 83 years in 2019. Similarly, HALE at birth has risen from 70.2 years to 70.9 years, while HALE at age 60 has grown from 18.4 years to 19 years. This data illustrates the steady improvement in both lifespan and health quality, showcasing Australia’s continued progress in public health.";
                    default -> "No story available for this chart type.";
                };
            case "China":
                return switch (chartType) {
                    case "Bar Chart" -> "The bar chart for China reveals an increase in life expectancy at birth from 74.9 years in 2010 to 77.4 years in 2019. This rise signifies substantial improvements in longevity. However, life expectancy at age 60 shows a more modest increase from 19.6 years to 21.1 years, suggesting a slower improvement in the later stages of life. Healthy Life Expectancy (HALE) at birth improved from 66.7 years to 68.5 years, indicating that the additional years of life are increasingly spent in good health. HALE at age 60 also increased from 14.9 years to 15.9 years, reflecting improvements in health among the elderly.";
                    case "Line Chart" -> "The line chart for China illustrates a steady increase in life expectancy at birth from 74.9 years in 2010 to 77.4 years in 2019. Life expectancy at age 60 also shows an upward trend, from 19.6 years to 21.1 years, suggesting improved quality of life for older adults. HALE at birth rose from 66.7 years to 68.5 years, and HALE at age 60 increased from 14.9 years to 15.9 years, reflecting overall health improvements and increased longevity.";
                    case "Pie Chart" -> " In the pie chart for China, the largest segment represents life expectancy at birth, which is 77.4 years in 2019. HALE at birth is 68.5 years, indicating that a significant portion of life expectancy is spent in good health. Life expectancy at age 60 is 21.1 years, and HALE at age 60 is 15.9 years. This distribution highlights the gains in longevity and health quality, showing that while life expectancy has increased, the quality of health in old age has also improved.";
                    case "Scatter Plot" -> "The scatter plot for China shows a clear upward trend in both life expectancy and HALE metrics. Life expectancy at birth increased from 74.9 years in 2010 to 77.4 years in 2019. HALE at birth also improved from 66.7 years to 68.5 years, and HALE at age 60 rose from 14.9 years to 15.9 years. This data illustrates the progress China has made in both extending lifespan and improving health outcomes.";
                    default -> "No story available for this chart type.";
                };
            case "India":
                return switch (chartType) {
                    case "Bar Chart" -> "The bar chart for India demonstrates an increase in life expectancy at birth from 67.2 years in 2010 to 70.8 years in 2019, reflecting significant gains in longevity. Life expectancy at age 60 also increased from 18 years to 18.8 years. Healthy Life Expectancy (HALE) at birth rose from 57.3 years to 60.3 years, indicating that more of these years are spent in good health. HALE at age 60 improved from 12.6 years to 13.2 years, showing enhanced health conditions among the elderly.";
                    case "Line Chart" -> "The line chart for India reveals a consistent upward trend in life expectancy at birth, which grew from 67.2 years in 2010 to 70.8 years in 2019. Life expectancy at age 60 also saw an increase from 18 years to 18.8 years. HALE at birth improved from 57.3 years to 60.3 years, while HALE at age 60 rose from 12.6 years to 13.2 years, reflecting ongoing improvements in health and longevity.";
                    case "Pie Chart" -> "The pie chart for India highlights that life expectancy at birth in 2019 is the highest at 70.8 years, with HALE at birth at 60.3 years. Life expectancy at age 60 stands at 18.8 years, and HALE at age 60 is 13.2 years. This distribution illustrates India's progress in extending lifespan and improving health, particularly among the elderly population.";
                    case "Scatter Plot" -> " The scatter plot for India shows an upward trend in life expectancy and HALE metrics. Life expectancy at birth increased from 67.2 years in 2010 to 70.8 years in 2019. HALE at birth also rose from 57.3 years to 60.3 years, while HALE at age 60 improved from 12.6 years to 13.2 years. This indicates a general enhancement in health and longevity over the years.";
                    default -> "No story available for this chart type.";
                };
            case "United States of America":
                return switch (chartType) {
                    case "Bar Chart" -> "The bar chart for the USA highlights high life expectancy at birth and at age 60, though disparities in HALE suggest areas for improvement in healthcare equity.";
                    case "Line Chart" -> "The line chart for the USA depicts stable life expectancy trends, with recent years showing slight improvements in HALE, reflecting ongoing healthcare advancements.";
                    case "Pie Chart" -> "In the USA's pie chart, life expectancy at birth occupies a major portion, showcasing the country's high standard of living and healthcare facilities.";
                    case "Scatter Plot" -> "The scatter plot for the USA illustrates a consistent trend of high life expectancy and HALE, demonstrating the effectiveness of the nation's health policies.";
                    default -> "No story available for this chart type.";
                };
            default:
                return "No story available for this country.";
        }
    }

    private String getAllCountriesStory(String chartType) {
        // General story for all countries
        return switch (chartType) {
            case "Bar Chart" -> "The bar chart compares life expectancy and HALE metrics across different countries, illustrating variations in health outcomes and quality of life on a global scale.";
            case "Line Chart" -> "The line chart showcases trends in life expectancy and HALE for multiple countries, highlighting how different nations have improved over time.";
            case "Pie Chart" -> "The pie chart presents the distribution of life expectancy and HALE metrics among various countries, providing a visual comparison of health standards worldwide.";
            case "Scatter Plot" -> "The scatter plot demonstrates the relationship between life expectancy and HALE across countries, revealing patterns and disparities in global health data.";
            default -> "No story available for this chart type.";
        };
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LifeExpectancyDashboard().setVisible(true));
    }
}
