package Test.API;

import com.aventstack.extentreports.Status;
import com.framework.baseFunctions.AssertFunction;
import com.framework.factory.ExtentReportsFactory;
import com.framework.listeners.APIListener;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.ITestResult;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.List;

@Listeners(APIListener.class)
public class TechnicalTestStockbitApi5 {
    @BeforeMethod()
    public void setUp(ITestResult result) {
        result.setAttribute("DetailScenario", "https://api.punkapi.com/v2/beers JSON Schema");
    }

    @Test(description = "Verified json Schema")
    public void technicalTestStockbitApi5() {
        RestAssured.baseURI = "https://api.punkapi.com/v2/beers";
        StringBuilder strAppend = new StringBuilder();

        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.get();

        JsonPath jsonPathEvaluator = response.jsonPath();
        List<String> lstName = jsonPathEvaluator.get("name");
        for (int i = 0; i < lstName.size(); i++) {
            strAppend.append((i + 1) + ". " + lstName.get(i) + "</br>");
        }

        AssertFunction.getInstance().assertEquals(lstName.size(), 25, "Verify that the amount of data returned");
        ExtentReportsFactory.init().get().log(Status.INFO, "List Names : </br>" + strAppend.toString());

        AssertFunction.getInstance().assertJSONSchema(response, "src/test/resources/JSONSchema/BeersJsonSchema.json");

    }
}
