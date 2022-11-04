package com.framework.baseFunctions;

import com.framework.baseFunctions.baseInterface.BaseFunctionsInterface;
import com.framework.factory.DriverFactory;
import com.framework.services.ScreenshotService;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Base Function class implement AssertInterface
 */
public class BaseFunction implements BaseFunctionsInterface {
    private static volatile BaseFunction instance = null;

    private BaseFunction() {

    }

    public static BaseFunction getInstance() {
        if (instance == null) {
            synchronized (BaseFunction.class) {
                if (instance == null) {
                    instance = new BaseFunction();
                }
            }
        }
        return instance;
    }


    /**
     * Start chrome driver
     */
    @Override
    public void startChromeDriver() {
        DriverFactory.init().initWebDriver();
    }



    /**
     * Click the WebElement
     */
    @Override
    public void click(By by) {
        WebElement webElement = loadElement(by);
        webElement.click();

    }

    /**
     * Set Text the WebElement
     */
    @Override
    public void setText(By by, String strValue) throws NullPointerException {
        WebElement webElement = loadElement(by);
        webElement.clear();
        webElement.sendKeys(strValue);
    }

    @Override
    public String getText(By by) {
        return loadElement(by).getText();
    }


    /**
     * Change IFrame
     */
    @Override
    public void changeIframe(By by) {
        WebElement webElement = loadElement(by);
        DriverFactory.init().get().switchTo().frame(webElement);
    }

    /**
     * Change IFrame to default
     */
    @Override
    public void changeIframeDefault() {
        DriverFactory.init().get().switchTo().defaultContent();
    }

    /**
     * Wait for Seconds
     *
     * @throws InterruptedException
     */
    @Override
    public void waitForSeconds(int intValue) throws InterruptedException {
        Thread.sleep(intValue * 1000);
    }




    /**
     * Wait until Element Exist
     */
    @Override
    public void waitUntilWebElementExist(By by) {
        WebDriverWait webDriverWait = new WebDriverWait(DriverFactory.init().get(), Duration.ofSeconds(30));
        webDriverWait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    /**
     * Load Element by Xpath and put into var local
     */
    protected WebElement loadElement(By by) {
        try {
            WebDriverWait webDriverWait = new WebDriverWait(DriverFactory.init().get(), Duration.ofSeconds(10));
            webDriverWait.until(ExpectedConditions.presenceOfElementLocated(by));

            WebElement webElement = DriverFactory.init().get().findElement(by);
            return webElement;
        } catch (Exception e) {
            e.printStackTrace();
            throw new NullPointerException("Web Element xpath : " + by.toString() + " Not Found.");
        }
    }


}
