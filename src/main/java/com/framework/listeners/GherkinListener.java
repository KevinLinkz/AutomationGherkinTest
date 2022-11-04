package com.framework.listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import com.framework.factory.DriverFactory;
import com.framework.factory.ExtentReportsFactory;
import com.framework.services.ScreenshotService;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class GherkinListener implements ITestListener {

    @Override
    public synchronized void onTestStart(ITestResult result) {
        DriverFactory.init().initWebDriver();
        ExtentReportsFactory.init().createTest(SuitesListener.extentReports, result.getParameters()[0].toString().replace("\"",""), result.getParameters()[1].toString(), "Gherkin Test");
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ExtentReportsFactory.init().get().log(Status.PASS, "Test Passed");

        DriverFactory.init().tearDown();
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String strFailedMessage = "Test Failed. </br>Error : " + result.getThrowable() +
                "</br>Last Screen : ";
        ScreenshotService.init().screenShotChromes(Status.FAIL, strFailedMessage);
        DriverFactory.init().tearDown();
    }

    @Override
    public void onTestSkipped(ITestResult result) {

        ExtentReportsFactory.init().get().log(Status.SKIP, "Test Skipped");
        DriverFactory.init().tearDown();
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
    }

    @Override
    public void onTestFailedWithTimeout(ITestResult result) {
    }

    @Override
    public void onStart(ITestContext context) {

    }

    @Override
    public void onFinish(ITestContext context) {

    }


}
