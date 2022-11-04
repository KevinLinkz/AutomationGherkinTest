package com.framework.pageObjects;

import com.aventstack.extentreports.Status;
import com.framework.baseFunctions.AssertFunction;
import com.framework.baseFunctions.BaseFunction;
import com.framework.services.ScreenshotService;
import org.apache.commons.collections4.map.HashedMap;
import org.openqa.selenium.By;

import java.util.Map;

public class BookStorePageObject {
    public BookStorePageObject() {
        mapSaveData.set(new HashedMap<>());
    }
    By byTxtBoxSearch = By.id("searchBox");
    By byAlert = By.xpath("//*[@id=\"app\"]//div[.=\"No rows found\"]");

    By byTitle = By.xpath("//*[@id=\"app\"]/div/div/div[2]/div[2]/div[2]/div[2]/div[1]/div[2]/div[1]/div/div[2]/div/span/a");
    By byAuthor = By.xpath("//*[@id=\"app\"]/div/div/div[2]/div[2]/div[2]/div[2]/div[1]/div[2]/div[1]/div/div[3]");
    By byPublisher = By.xpath("//*[@id=\"app\"]/div/div/div[2]/div[2]/div[2]/div[2]/div[1]/div[2]/div[1]/div/div[4]");

    By byTitleDetail = By.xpath("//*[@id=\"title-wrapper\"]/div[2]");
    By byAuthorDetail = By.xpath("//*[@id=\"author-wrapper\"]/div[2]");
    By buPublisherDetail = By.xpath("//*[@id=\"publisher-wrapper\"]/div[2]");

    ThreadLocal<Map<String,String>> mapSaveData = new ThreadLocal<>();

    public void searchBook(String strStep,String strParam){
        BaseFunction.getInstance().click(byTxtBoxSearch);
        BaseFunction.getInstance().setText(byTxtBoxSearch,strParam);
        ScreenshotService.init().screenShotChromes(Status.INFO,strStep);
    }

    public void assertNotFoundData(String strStep,String strParam){
        AssertFunction.getInstance().assertWebElementExist(byAlert);
        AssertFunction.getInstance().assertWebElementTextTrue(byAlert,strParam);
        ScreenshotService.init().screenShotChromes(Status.INFO,strStep);

    }
    public void getDetailItems(String strStep){
        mapSaveData.get().put("title",BaseFunction.getInstance().getText(byTitle));
        mapSaveData.get().put("author",BaseFunction.getInstance().getText(byAuthor));
        mapSaveData.get().put("publisher",BaseFunction.getInstance().getText(byPublisher));
        BaseFunction.getInstance().click(byTitle);
        ScreenshotService.init().screenShotChromes(Status.INFO,strStep);
    }

    public void assertDataDetailBook(String strStep){
        String strByTitleCompare = mapSaveData.get().get("title");
        String strByAuthorCompare = mapSaveData.get().get("author");
        String strByPublisherCompare = mapSaveData.get().get("publisher");

        AssertFunction.getInstance().assertWebElementTextTrue(byTitleDetail,strByTitleCompare);
        AssertFunction.getInstance().assertWebElementTextTrue(byAuthorDetail,strByAuthorCompare);
        AssertFunction.getInstance().assertWebElementTextTrue(buPublisherDetail,strByPublisherCompare);

        ScreenshotService.init().screenShotChromes(Status.INFO,strStep);

    }

}
