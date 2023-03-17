package base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import util.ExtentManager;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Properties;

public class BaseTest {
    /**
     * WebDriver, Properties, Logs
     */

    public static WebDriver driver;
    public static Properties config;
    protected static final Logger logger = LogManager.getLogger("BaseTestLogger");
    public static ExtentReports extent = ExtentManager.getExtents();
    public static ExtentTest extentTest;

    @BeforeSuite
    public void setup() {
        if (driver==null) {
            try {
                config = new Properties();
                config.load(new FileInputStream("src/test/resources/config.properties"));
                logger.info("config file loaded");
            } catch (IOException e) {
                logger.error(e.getMessage());
                throw new RuntimeException(e);
            }

            if(config.getProperty("browser").equalsIgnoreCase("chrome")) {
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--remote-allow-origins=*");
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver(chromeOptions);
            } else if(config.getProperty("browser").equalsIgnoreCase("firefox")) {
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
            }
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.of(8, ChronoUnit.SECONDS));
        }
    }

    @AfterSuite
    public void tearDown() {
        if (driver != null) {
            driver.close();
        }
    }
}
