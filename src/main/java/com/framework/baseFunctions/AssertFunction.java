package com.framework.baseFunctions;

import com.aventstack.extentreports.Status;
import com.framework.baseFunctions.baseInterface.AssertInterface;
import com.framework.factory.DriverFactory;
import com.framework.factory.ExtentReportsFactory;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.io.File;
import java.time.Duration;

/**
 * Assert Function class implement AssertInterface
 */
public class AssertFunction implements AssertInterface {

    private static volatile AssertFunction instance = null;

    private AssertFunction() {

    }

    public static AssertFunction getInstance() {
        if (instance == null) {
            synchronized (AssertFunction.class) {
                if (instance == null) {
                    instance = new AssertFunction();
                }
            }
        }
        return instance;
    }

    /**
     * Assert Web Element Exist
     */
    @Override
    public void assertWebElementExist(By by) {
        try {
            WebElement webElement = loadElement(by);
            if (webElement.isDisplayed()) {
                ExtentReportsFactory.init().get().log(
                        Status.PASS, new Object() {
                        }.getClass().getEnclosingMethod().getName() +
                                "</br>Web element: " + by.toString() + " found");
            }
        } catch (Exception ex) {
            throw new AssertionError("WebElement : " + by.toString() + " NOT FOUND");
        }
    }

    /**
     * Assert Web Element not Exist
     */
    @Override
    public void assertWebElementNotExist(By by) {
        try {
            WebElement webElement = loadElement(by);

            if (webElement.isDisplayed()) {
                throw new AssertionError("WebElement : " + by.toString() + " FOUND");
            }
        } catch (Exception ex) {
            ExtentReportsFactory.init().get().log(
                    Status.PASS, new Object() {
                    }.getClass().getEnclosingMethod().getName() +
                            "</br>Web element: " + by.toString() + " not found");
        }

    }

    /**
     * Assert Web Element Text is exact same
     */
    @Override
    public void assertWebElementTextTrue(By by, String strCompare) {
        String strObject = loadElement(by).getText();
        Assert.assertEquals(strObject, strCompare);
        ExtentReportsFactory.init().get().log(
                Status.PASS, new Object() {
                }.getClass().getEnclosingMethod().getName() +
                        "</br>Web element: " + by.toString() + " same" +
                        "</br> Object text = " + strObject +
                        "</br> Compare text = " + strCompare
        );

    }

    /**
     * Assert Web Element Text is exact not same
     */
    @Override
    public void assertWebElementTextFalse(By by, String strCompare) {
        String strObject = loadElement(by).getText();

        Assert.assertNotEquals(strObject, strCompare);
        ExtentReportsFactory.init().get().log(
                Status.PASS, new Object() {
                }.getClass().getEnclosingMethod().getName() +
                        "</br>Web element: " + by.toString() + " not same" +
                        "</br> Object text = " + strObject +
                        "</br> Compare text = " + strCompare
        );
    }

    /**
     * Assert Web Element Text is contains text
     */
    @Override
    public void assertWebElementTextContainsTrue(By by, String strCompare) {
        String strObject = loadElement(by).getText();

        if (strObject.contains(strCompare)) {
            ExtentReportsFactory.init().get().log(
                    Status.PASS, new Object() {
                    }.getClass().getEnclosingMethod().getName() +
                            "</br>Web element: " + by.toString() + " contains" +
                            "</br> Object text = " + strObject +
                            "</br> Compare text = " + strCompare
            );
        } else {
            throw new AssertionError("Object Name = " + by.toString() + "</br>Contains text : " + strCompare + " Not Contains " + strObject);
        }
    }

    /**
     * Assert Web Element Text is not contains text
     */
    @Override
    public void assertWebElementTextContainsFalse(By by, String strCompare) {
        String strObject = loadElement(by).getText();

        if (!strObject.contains(strCompare)) {
            ExtentReportsFactory.init().get().log(
                    Status.PASS, new Object() {
                    }.getClass().getEnclosingMethod().getName() +
                            "</br>Web element: " + by.toString() + " not contains" +
                            "</br> Object text = " + strObject +
                            "</br> Compare text = " + strCompare
            );
        } else {
            throw new AssertionError("Object Name = " + by.toString() + "</br>Not Contains text : " + strCompare + " Contains " + strObject);
        }
    }


    /**
     * Compare 2 value
     * @param objModel objectModel
     * @param objCompare objectCompare
     */
    @Override
    public void assertEquals(Object objModel, Object objCompare,String strDescription) {
        try {
            Assert.assertEquals(objModel, objCompare);

            ExtentReportsFactory.init().get().log(
                    Status.PASS, "Assert Equals same" +
                            "</br> Object text = " + objModel +
                            "</br> Compare text = " + objCompare+
                            "</br> Description = " + strDescription);
        }catch (Exception e){
            throw new AssertionError("Assert Equals not same" +
                    "</br> Object text = " + objModel +
                    "</br> Compare text = " + objCompare);
        }

    }

    /**
     * Compare JSON Schema Response vs JSON Schema File
     * @param response Response RestAssured
     * @param strPathJSONSchema path JSON Schema
     */
    @Override
    public void assertJSONSchema(Response response,String strPathJSONSchema) {
        try {
            response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(new File(strPathJSONSchema)));
            ExtentReportsFactory.init().get().log(Status.PASS, "JSON Schema is same");
        }catch (Exception e){
            throw new AssertionError("JSON Schema is not same");
        }

    }

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
