package com.accolite.Assignment2;


import models.Employee;

import java.util.List;

public class ExcelReaderTest {

    public static void main(String[] args) {
        String filePath = "C:\\Users\\kohina.pandey\\Downloads\\Accolite Interview Data - Q4 2023 - Grad Program November 2023.xlsx"; // Replace with the actual file path

        ExcelFileReader excelReader = new ExcelFileReader();
        List<Employee> dataObjects = excelReader.readExcelFile(filePath);

        // Print the data
        for (Employee dataObject : dataObjects) {
            System.out.println("Row:");
            for (int i = 0;i<dataObjects.size();i++) {
                System.out.println(dataObjects.get(i));
            }
            System.out.println();
        }
    }
}


