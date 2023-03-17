package testcases;

import base.BaseTest;
import listeners.CustomTestListener;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import util.ExcelUtil;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Listeners(CustomTestListener.class)
public class TestLoginAttemptUsingExcelAndCsvDataProvider extends BaseTest {

    @DataProvider(name = "testNegativeCsv")
    public Iterator<Object []> provider( ) throws InterruptedException, IOException {
        List<Object []> testCases = new ArrayList<>();
        String[] data= null;

        BufferedReader br = new BufferedReader(new FileReader("src/test/resources/csv/login-negative-data.csv"));
        String line;
        while ((line = br.readLine()) != null) {
            // use comma as separator
            data= line.split(",");
            testCases.add(data);
        }

        return testCases.iterator();
    }

    @DataProvider(name = "testNegativeExcel")
    public Object[][] getLoginDataFromExcel() {
        return ExcelUtil.getExcelData("src/test/resources/csv/login-negative-data.xlsx",
                "login-data");
    }



    @Test(dataProvider = "testNegativeCsv")
    public void loginTestNegativeCsv(String name, String password) {
        driver.get("https://rabota.by/");
        logger.info("navigating to the site");
        driver.findElement(By.xpath("//a[@data-qa='login']")).click();
        driver.findElement(By.xpath("//*[@data-qa='expand-login-by-password']")).click();
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOf(driver
                .findElement(By.xpath("//input[@data-qa='login-input-username']"))));
        driver.findElement(By.xpath("//input[@data-qa='login-input-username']")).click();

        driver.findElement(By.xpath("//input[@data-qa='login-input-username']")).sendKeys(name);
        driver.findElement(By.xpath("//input[@data-qa='login-input-password']")).sendKeys(password);
        driver.findElement(By.xpath("//*[@data-qa='account-login-submit']")).click();

        WebElement webElement = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOf(driver.findElement(By
                        .xpath("//*[@data-qa='account-login-error']"))));

        Assert.assertTrue(webElement.isDisplayed(), "Error message is not displaed");

    }

    @Test(dataProvider = "testNegativeExcel")
    public void loginTestNegativeExcel(String name, String password) {
        driver.get("https://rabota.by/");
        logger.info("navigating to the site");
        driver.findElement(By.xpath("//a[@data-qa='login']")).click();
        driver.findElement(By.xpath("//*[@data-qa='expand-login-by-password']")).click();
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOf(driver
                .findElement(By.xpath("//input[@data-qa='login-input-username']"))));
        driver.findElement(By.xpath("//input[@data-qa='login-input-username']")).click();

        driver.findElement(By.xpath("//input[@data-qa='login-input-username']")).sendKeys(name);
        driver.findElement(By.xpath("//input[@data-qa='login-input-password']")).sendKeys(password);
        driver.findElement(By.xpath("//*[@data-qa='account-login-submit']")).click();

        WebElement webElement = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOf(driver.findElement(By
                        .xpath("//*[@data-qa='account-login-error']"))));

        Assert.assertTrue(webElement.isDisplayed(), "Error message is not displaed");

    }
}
