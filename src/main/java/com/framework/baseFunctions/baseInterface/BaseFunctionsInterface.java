package com.framework.baseFunctions.baseInterface;

import org.openqa.selenium.By;

/**
 * Interface for BaseFunctionClass
 */
public interface BaseFunctionsInterface {


    void startChromeDriver();

    void click(By by);

    void setText(By by, String strValue) throws NullPointerException;

    String getText(By by);

    void changeIframe(By by);

    void changeIframeDefault();

    void waitForSeconds(int intValue) throws InterruptedException;


    void waitUntilWebElementExist(By by);
}
