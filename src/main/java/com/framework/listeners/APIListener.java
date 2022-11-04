
package com.framework.listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import com.framework.factory.DriverFactory;
import com.framework.factory.ExtentReportsFactory;
import com.framework.services.ScreenshotService;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.Listeners;

public class APIListener implements ITestListener {

    @Override
    public synchronized void onTestStart(ITestResult result) {
        ExtentReportsFactory.init().createTest(SuitesListener.extentReports, result.getMethod().getDescription(), result.getAttribute("DetailScenario").toString(), "API");
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ExtentReportsFactory.init().get().log(Status.PASS, "Test Passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String strFailedMessage = "Test Failed. </br>Error : " + result.getThrowable() +
                "</br>Last Screen : ";
        ExtentReportsFactory.init().get().log(Status.FAIL, strFailedMessage);

    }

    @Override
    public void onTestSkipped(ITestResult result) {

        ExtentReportsFactory.init().get().log(Status.SKIP, "Test Skipped");
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
