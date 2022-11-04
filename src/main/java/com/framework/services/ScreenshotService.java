package com.framework.services;

import com.assertthat.selenium_shutterbug.core.Shutterbug;
import com.assertthat.selenium_shutterbug.utils.web.ScrollStrategy;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.framework.factory.DriverFactory;
import com.framework.factory.ExtentReportsFactory;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Base64;
import java.util.Date;

public class ScreenshotService {
    private static volatile ScreenshotService instance = null;
    ThreadLocal<Integer> trdIntCounterData = new ThreadLocal<>();

    private ScreenshotService() {
    }

    public static ScreenshotService init() {
        if (instance == null) {
            synchronized (ScreenshotService.class) {
                if (instance == null) {
                    instance = new ScreenshotService();
                }
            }
        }
        return instance;
    }

    public synchronized Integer getCounterData() {
        if(trdIntCounterData.get() == null)
            setCounterData(1);

        return trdIntCounterData.get();
    }

    public synchronized void setCounterData(Integer intCounterData) {
        trdIntCounterData.set(intCounterData);
    }

    /**
     * Screenshot use ShutterBug for screenshot full page
     * @return path the screenShot
     */
    public synchronized String screenShotFullPage() {
        String strDefaultPath = System.getProperty("user.dir") + "\\Screenshot\\";
        initFolderDefault(strDefaultPath);
//        strFileName = strFileName + "_" + getCounterData();
        String strFileName = MyConfig.sdf.format(new Date());
        String strFullPath = strDefaultPath + strFileName + ".png";

        Shutterbug.shootPage(DriverFactory.init().get(), ScrollStrategy.WHOLE_PAGE, 500).withName(strFileName).save(strDefaultPath);

        putIntoReport(strFullPath);

//        setCounterdata(getCounterData() + 1);
        return strDefaultPath;

    }

    /**
     * Swipeup and screenshot 2x
     * temporary code, still need to updating
     */
    public synchronized String screenShotFullViewMobile(){
        String strDefaultPath = System.getProperty("user.dir") + "\\Screenshot\\";
        initFolderDefault(strDefaultPath);
        String strFileName = MyConfig.sdf.format(new Date());
        String strFullPath = strDefaultPath + strFileName + ".png";

        Dimension mobileDimension = DriverFactory.init().get().manage().window().getSize();

        int intHeight = mobileDimension.height;
        int intWidth = mobileDimension.width;
        int startSwipeUp = intHeight * 3 / 4;
        int endSwipeeUp = intHeight / 4;
        int middlePoint_X = intWidth / 2;
        TouchAction actionSwipeUP = new TouchAction((AndroidDriver) DriverFactory.init().get());
        int elementExist = 1;

        while (elementExist < 3) {
            Shutterbug.shootPage(DriverFactory.init().get(), ScrollStrategy.VIEWPORT_ONLY, 500).withName(strFileName).save(strDefaultPath);
            putIntoReportMobile(strFullPath, elementExist);

            actionSwipeUP.press(PointOption.point(middlePoint_X, startSwipeUp))
                    .waitAction(new WaitOptions().withDuration(Duration.ofMillis(2500))) //you can change wait durations as per your requirement
                    .moveTo(PointOption.point(middlePoint_X, endSwipeeUp))
                    .release()
                    .perform();
            elementExist++;
        }

        return strDefaultPath;
    }

    /**
     * Screenshot use WebDriver.TakeScreenshot
     * @return path the screenShot
     */

    public synchronized String screenShotChromes(Status status, String strDescription)  {
        String strDefaultPath = System.getProperty("user.dir") + "\\Screenshot\\";
        initFolderDefault(strDefaultPath);
        String strFileName = MyConfig.sdf.format(new Date());
        String strFullPath = strDefaultPath + strFileName + ".png";

        try {
            Thread.sleep(500);
            File scrFile = ((TakesScreenshot) DriverFactory.init().get()).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(scrFile, new File(strFullPath));
            String encodedString = changeImageToBase64(strFullPath);
            ExtentReportsFactory.init().get().log(status,
                    strDescription , MediaEntityBuilder.createScreenCaptureFromBase64String(encodedString).build());
            File fleImage = new File(strFullPath);
            fleImage.delete();
        } catch (IOException e) {
            ExtentReportsFactory.init().get().log(Status.FAIL, "Path Not Found." + strFullPath);
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return strDefaultPath;

    }

    /**
     * Put image into Report and delete it
     * @param strFullPath Full path file screenshot
     */
    private void putIntoReport(String strFullPath){
        String encodedString = changeImageToBase64(strFullPath);
        ExtentReportsFactory.init().get().log(Status.INFO,
                "Screenshot : ", MediaEntityBuilder.createScreenCaptureFromBase64String(encodedString).build());
        File fleImage = new File(strFullPath);
        fleImage.delete();
    }

    private void putIntoReportMobile (String strFullPath, int elementExist){
        String encodedString = changeImageToBase64(strFullPath);
        String strDescriptionReport = "Screenshot Full Mobile <br/>Screenshot - "+elementExist;
        ExtentReportsFactory.init().get().log(Status.INFO,
                strDescriptionReport, MediaEntityBuilder.createScreenCaptureFromBase64String(encodedString).build());
        File fleImage = new File(strFullPath);
        fleImage.delete();
    }

    /**
     * Change image into Base64
     * @param strPathScreenShot Full path file screenshot
     * @return encodedString
     */
    private  String changeImageToBase64(String strPathScreenShot) {
        String encodedString = "";
        try {
            byte[] fileContent = FileUtils.readFileToByteArray(new File(strPathScreenShot));
            encodedString = Base64.getEncoder().encodeToString(fileContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return encodedString;
    }

    /**
     * Create folder for put the result screenshot
     * @param strPathFolderResultTesting Path Folder file Screenshot
     */
    private void initFolderDefault(String strPathFolderResultTesting){
        File file = new File(strPathFolderResultTesting);
        if (!file.exists()) {
            file.mkdirs();
        }
    }


}
