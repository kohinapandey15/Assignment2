package com.accolite.Assignment2;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import models.Employee;

public class ExcelFileReader {

    public List<Employee> readExcelFile(String filePath) {
        List<Employee> employeeList = new ArrayList<>();

        try (FileInputStream fileInputStream = new FileInputStream(new File(filePath));
             Workbook workbook = new XSSFWorkbook(fileInputStream)) {

            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();

            // Assuming the first row is the header, so skipping it
            if (rowIterator.hasNext()) {
                rowIterator.next();
            }

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Employee employee = new Employee();

                // Assuming the cell index for each attribute
                employee.setInterviewDate(getStringValue(row.getCell(0)));
                employee.setMonth(getStringValue(row.getCell(1)));
                employee.setTeam(getStringValue(row.getCell(2)));
                employee.setPanelName(getStringValue(row.getCell(3)));
                employee.setRound(getStringValue(row.getCell(4)));
                employee.setSkill(getStringValue(row.getCell(5)));
                employee.setTime(getStringValue(row.getCell(6)));
                employee.setCandidateCurrentLoc(getStringValue(row.getCell(7)));
                employee.setCandidatePreferredLoc(getStringValue(row.getCell(8)));
                employee.setCandidateName(getStringValue(row.getCell(9)));

                employeeList.add(employee);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return employeeList;
    }

    private String getStringValue(Cell cell) {
        if (cell == null) {
            return null;
        }

        DataFormatter dataFormatter = new DataFormatter();
        return dataFormatter.formatCellValue(cell);
    }
}
