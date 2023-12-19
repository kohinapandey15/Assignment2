package dataBase;


import com.accolite.Assignment2.ExcelFileReader;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.*;
import com.itextpdf.layout.element.Image;
import models.Employee;
import org.jfree.chart.JFreeChart;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import com.accolite.Assignment2.SQLConnecction;


@SpringBootApplication
public class Database {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/employee";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "Boltbiry@ni6915";

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        SpringApplication.run(Database.class, args);
        ExcelFileReader fileReader = new ExcelFileReader();
        String filePath = "C:\\Users\\kohina.pandey\\Downloads\\Accolite Interview Data - Q4 2023 - Grad Program November 2023.xlsx";
        List<Employee> employeeList = fileReader.readExcelFile(filePath);
        System.out.println(employeeList.size());

        employeeList.parallelStream().forEach(Database::insertDataIntoSQLTable);

        generateCharts(employeeList);
    }

    private static <SQLConnection> void generateCharts(List<Employee> employeeList) {
        String pdfPath = "charts.pdf";

        try (OutputStream os = new FileOutputStream(pdfPath);
             PdfWriter writer = new PdfWriter(os);
             PdfDocument pdfDocument = new PdfDocument(writer);
             Document document = new Document(pdfDocument)) {


            SQLConnecction queries = new SQLConnecction();
            JFreeChart chart1 = queries.maxInterviewsQuery();
            System.out.println(chart1);
            BufferedImage image = chart1.createBufferedImage(700, 500);
            Image itextImage = new Image(ImageDataFactory.create(image, null));

            document.add(itextImage);

            JFreeChart chart2 = queries.minInterviewsQuery();
            BufferedImage image2 = chart2.createBufferedImage(700, 500);
            Image itextImage2 = new Image(ImageDataFactory.create(image2, null));

            document.add(itextImage2);

            JFreeChart chart5 = queries.getTop3SkillsForPeakTime();
            BufferedImage image5 = chart5.createBufferedImage(700, 500);
            Image itextImage5 = new Image(ImageDataFactory.create(image5, null));

            document.add(itextImage5);

            JFreeChart chart4 = queries.getTop3Skills();
            BufferedImage image4 = chart4.createBufferedImage(700, 500);
            Image itextImage4 = new Image(ImageDataFactory.create(image4, null));

            document.add(itextImage4);

            JFreeChart chart3 = queries.getTop3Panels(employeeList);
            System.out.println(chart3);
            BufferedImage image3 = chart3.createBufferedImage(700, 500);
            Image itextImage3 = new Image(ImageDataFactory.create(image3, null));

            document.add(itextImage3);

            System.out.println("Path of the generated pdf: " + pdfPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void insertDataIntoSQLTable(Employee employee) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            String sql = "INSERT INTO employee.interviews (interview_date, month, team, panelName, round, skill, time, candidateCurrentLoc, candidatePreferredLoc, candidateName) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";


            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

                preparedStatement.setString(1, employee.getInterviewDate());
                preparedStatement.setString(2, employee.getMonth());
                preparedStatement.setString(3, employee.getTeam());
                preparedStatement.setString(4, employee.getPanelName());
                preparedStatement.setString(5, employee.getRound());
                preparedStatement.setString(6, employee.getSkill());
                preparedStatement.setString(7, employee.getTime());
                preparedStatement.setString(8, employee.getCandidateCurrentLoc());
                preparedStatement.setString(9, employee.getCandidatePreferredLoc());
                preparedStatement.setString(10, employee.getCandidateName());

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}


