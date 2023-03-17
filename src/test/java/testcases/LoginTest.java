package testcases;

import base.BaseTest;
import listeners.CustomTestListener;
import org.openqa.selenium.By;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;


@Listeners(CustomTestListener.class)
public class LoginTest extends BaseTest {

    @Test
    public void loginAsBankManager() {
        driver.get("https://rabota.by/");
        logger.info("navigating to the site");
        driver.findElement(By.xpath("//a[@data-qa='login']")).click();
    }
}
