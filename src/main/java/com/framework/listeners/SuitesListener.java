package com.framework.listeners;

import com.aventstack.extentreports.ExtentReports;
import com.framework.factory.ExtentReportsFactory;
import org.testng.ISuite;
import org.testng.ISuiteListener;

public class SuitesListener implements ISuiteListener {
    static ExtentReports extentReports;

    @Override
    public void onStart(ISuite suite) {
        if (extentReports == null)
            extentReports = ExtentReportsFactory.init().setupExtentReports();
    }

    @Override
    public void onFinish(ISuite suite) {
        extentReports.flush();

    }
}
