package listeners;

import base.BaseTest;
import com.aventstack.extentreports.Status;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.SkipException;
import util.ExcelUtil;

public class CustomTestListener implements ITestListener {
    protected static final Logger logger = LogManager.getLogger("BaseTestLogger");

    @Override
    public void onTestStart(ITestResult result) {
        var testName = result.getMethod().getMethodName();
        if (!ExcelUtil.isTestRunnable(testName)) {
            throw new SkipException("Skip the test as run mode N");
        }
        logger.info("Test has started");
        BaseTest.extentTest = BaseTest.extent.createTest(result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        logger.info("Test has succeeded");
        BaseTest.extentTest.log(Status.PASS, result.getName() + " has passed");
        BaseTest.extent.flush();
    }

    @Override
    public void onTestFailure(ITestResult result) {
        logger.info("Test has failed");
        BaseTest.extentTest.log(Status.FAIL, result.getName() + " has failed");
        BaseTest.extent.flush();
        BaseTest.extent.removeTest(BaseTest.extentTest);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        logger.info("Test skipped");
    }
}
