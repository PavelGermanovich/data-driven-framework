package util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;

public class ExcelUtil {
    public static String[][] getExcelData(String fileName, String sheetName) {
        String[][] data = null;
        try {
            FileInputStream fis = new FileInputStream(fileName);
            XSSFWorkbook wb = new XSSFWorkbook(fis);
            XSSFSheet sh = wb.getSheet(sheetName);
            XSSFRow row = sh.getRow(0);
            int noOfRows = sh.getPhysicalNumberOfRows();
            int noOfCols = row.getLastCellNum();
            Cell cell;
            data = new String[noOfRows - 1][noOfCols];
            for (int i = 1; i < noOfRows; i++) {
                for (int j = 0; j < noOfCols; j++) {
                    row = sh.getRow(i);
                    cell = row.getCell(j);
                    data[i - 1][j] = cell.getStringCellValue();
                }
            }
        } catch (Exception e) {
            System.out.println("The exception is: " + e.getMessage());
        }
        return data;
    }

    public static boolean isTestRunnable(String testName) {
        try {
            FileInputStream fis = new FileInputStream("src/test/resources/csv/login-negative-data.xlsx");
            XSSFWorkbook wb = new XSSFWorkbook(fis);
            XSSFSheet sh = wb.getSheet("test_suite");
            int rows = sh.getPhysicalNumberOfRows();
            for (int rNum = 2; rNum <= rows; rNum++) {
                String testCase = sh.getRow(rNum).getCell(0).getStringCellValue();
                if (testCase.equalsIgnoreCase(testName)) {
                    String runMode = sh.getRow(rNum).getCell(1).getStringCellValue();

                    return runMode.equalsIgnoreCase("Y");
                }
            }

        } catch (
                Exception e) {
            System.out.println("The exception is: " + e.getMessage());
        }

        return false;
    }
}
