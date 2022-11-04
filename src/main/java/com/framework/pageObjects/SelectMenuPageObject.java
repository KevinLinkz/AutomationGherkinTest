package com.framework.pageObjects;

import com.aventstack.extentreports.Status;
import com.framework.baseFunctions.AssertFunction;
import com.framework.baseFunctions.BaseFunction;
import com.framework.factory.DriverFactory;
import com.framework.services.ScreenshotService;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class SelectMenuPageObject {
    public SelectMenuPageObject() {
    }

    By byDivHeader = By.xpath("//*[@id=\"app\"]/div/div/div[1]/div");
    By bySelectValue = By.id("withOptGroup");
    By bySelectOne = By.id("selectOne");
    By byOldSelectMenu = By.xpath("//select[@id='oldSelectMenu']");
    By byMultiSelect = By.xpath("//p/b[.=\"Multiselect drop down\"]/following::div[contains(@class,'container')]");

    public void assertHeader(String strStep,String strParam){
        AssertFunction.getInstance().assertWebElementExist(byDivHeader);
        AssertFunction.getInstance().assertWebElementTextTrue(byDivHeader,strParam);
        ScreenshotService.init().screenShotChromes(Status.INFO,strStep);

    }
    public void selectDdlValue(String strStep,String strParam){

        BaseFunction.getInstance().click(bySelectValue);
        BaseFunction.getInstance().click(By.xpath("//div[.=\""+ strParam +"\"]"));
        ScreenshotService.init().screenShotChromes(Status.INFO,strStep);


    }

    public void selectDdlOne(String strStep,String strParam){

        BaseFunction.getInstance().click(bySelectOne);
        BaseFunction.getInstance().click(By.xpath("//div[.=\""+ strParam +"\"]"));
        ScreenshotService.init().screenShotChromes(Status.INFO,strStep);

    }

    public void selectDdlOldStyle(String strStep,String strParam){

        BaseFunction.getInstance().click(byOldSelectMenu);
        BaseFunction.getInstance().click(By.xpath("//option[.=\""+ strParam +"\"]"));
        ScreenshotService.init().screenShotChromes(Status.INFO,strStep);

    }

    public void selectDdlMulti(String strStep){

        BaseFunction.getInstance().click(byMultiSelect);
        List<WebElement> lstOption = DriverFactory.init().get().findElements(By.xpath("//div[contains(@class,'option')]"));
        for (WebElement webElement : lstOption) {
            webElement.click();
        }
        ScreenshotService.init().screenShotChromes(Status.INFO,strStep);

    }

}
