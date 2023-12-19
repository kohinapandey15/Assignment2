package com.accolite.Assignment2;


import models.Employee;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import java.sql.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SQLConnecction {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/employee";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Boltbiry@ni6915";

    public JFreeChart maxInterviewsQuery() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT team, COUNT(*) as interviewCount FROM interviews WHERE month IN ('Oct-23', 'Nov-23') GROUP BY team ORDER BY COUNT(*) DESC LIMIT 1";
            try (PreparedStatement statement = connection.prepareStatement(query); ResultSet set = statement.executeQuery()) {
                while (set.next()) {
                    String category = set.getString("team");
                    int value = set.getInt("interviewCount");
                    dataset.addValue(value, "Records", category);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        JFreeChart chart = ChartFactory.createBarChart(
                "Team with Maximum Interviews in Oct'23 and Nov'23",
                "Team",
                "Interviews Count",
                dataset
        );

        return chart;
    }

    public JFreeChart minInterviewsQuery() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT team, COUNT(*) as interviewCount FROM interviews WHERE month IN ('Oct-23', 'Nov-23') GROUP BY team ORDER BY COUNT(*) LIMIT 1";
            try (PreparedStatement statement = connection.prepareStatement(query); ResultSet set = statement.executeQuery()) {
                while (set.next()) {
                    String category = set.getString("team");
                    int value = set.getInt("interviewCount");
                    dataset.addValue(value, "Records", category);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        JFreeChart chart = ChartFactory.createBarChart(
                "Team with Minimum Interviews in Oct'23 and Nov'23",
                "Team",
                "Interviews Count",
                dataset
        );
        return chart;
    }

    public JFreeChart getTop3Panels(List<Employee> interviewList) {
        Map<String, Integer> panelsToInterviewCounts = interviewList.stream()
                .filter(rec -> rec.getMonth() != null && (rec.getMonth().equals("Oct-23") || rec.getMonth().equals("Nov-23")))
                .collect(Collectors.groupingBy(record -> record.getPanelName(), Collectors.summingInt(r -> 1)));

        List<Map.Entry<String, Integer>> top3Panels = panelsToInterviewCounts.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(3)
                .collect(Collectors.toList());

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        System.out.println(dataset);

        top3Panels.forEach(entry -> dataset.addValue(entry.getValue(), "Interviews", entry.getKey()));

        return ChartFactory.createBarChart("Top 3 Panels in October and November 2023", "Panel", "Interview Count", dataset, PlotOrientation.VERTICAL, true, true, false);
    }

    public JFreeChart getTop3Skills() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String viewCreationQuery = "CREATE VIEW my_view1 AS SELECT skill, month, count(*) as skillCount FROM interviews GROUP BY skill, month";
            String selectQuery = "SELECT skill, COUNT(*) AS skillCount FROM my_view1 WHERE month IN ('Oct-23', 'Nov-23') GROUP BY skill ORDER BY skillCount DESC LIMIT 3";

            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate(viewCreationQuery);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try (PreparedStatement statement = connection.prepareStatement(selectQuery); ResultSet set = statement.executeQuery()) {
                while (set.next()) {
                    String category = set.getString("skill");
                    int value = set.getInt("skillCount");
                    dataset.addValue(value, "Records", category);
                }
            }

            return ChartFactory.createBarChart("Top 3 Skills in the months October and November", "Skill", "Skill Count", dataset, PlotOrientation.VERTICAL, true, true, false);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public JFreeChart getTop3SkillsForPeakTime() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT skill, COUNT(*) AS skillCount FROM interviews WHERE month IN ('Oct-23', 'Nov-23') AND TIME(time) BETWEEN '17:00:00' AND '18:00:00' GROUP BY skill ORDER BY skillCount DESC LIMIT 3";
            try (PreparedStatement statement = connection.prepareStatement(query); ResultSet set = statement.executeQuery()) {
                while (set.next()) {
                    String category = set.getString("skill");
                    int value = set.getInt("skillCount");
                    dataset.addValue(value, "Records", category);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        JFreeChart chart = ChartFactory.createBarChart(
                "Top 3 Skills in Peak Time (5:00PM to 6:00PM)",
                "Skill",
                "Skill Count",
                dataset
        );
        return chart;
    }
}

