package com.framework.baseFunctions.baseInterface;

import io.restassured.response.Response;
import org.openqa.selenium.By;

/**
 * Interface for AssertClass
 */
public interface AssertInterface {

    void assertWebElementExist(By by);

    void assertWebElementNotExist(By by);

    void assertWebElementTextTrue(By by, String strCompare);

    void assertWebElementTextFalse(By by, String strCompare);

    void assertWebElementTextContainsTrue(By by, String strCompare);

    void assertWebElementTextContainsFalse(By by, String strCompare);


    void assertEquals(Object objModel, Object objCompare, String strDescription);

    void assertJSONSchema(Response response, String strPathJSONSchema);
}
