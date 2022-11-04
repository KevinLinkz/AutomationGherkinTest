package Test.API;

import com.framework.baseFunctions.AssertFunction;
import com.framework.listeners.APIListener;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.util.List;

@Listeners(APIListener.class)
public class TechnicalTestStockbitApi4 {
    @BeforeMethod()
    public void setUp(Object[] parameters, ITestResult result) {
        result.setAttribute("DetailScenario", parameters[0].toString() + parameters[1].toString() + parameters[2].toString());
    }

    @Test(description = "Verified amount response",
            dataProvider = "dataAPIBeers")
    public void technicalTestStockbitApi4(String strUri, String strParam, int intNumberOfData) {
        RestAssured.baseURI = strUri;
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.get(strParam + intNumberOfData);
        JsonPath jsonPathEvaluator = response.jsonPath();
        List<String> s = jsonPathEvaluator.get();
        AssertFunction.getInstance().assertEquals(s.size(),intNumberOfData,"verify that the amount of data returned");

    }

    @DataProvider(name = "dataAPIBeers",parallel = true)
    public Object[][] dataAPIBeers() {
        return new Object[][]{
                {"https://api.punkapi.com/v2/", "beers?page=2&per_page=", 20},
                {"https://api.punkapi.com/v2/", "beers?page=2&per_page=", 5},
                {"https://api.punkapi.com/v2/", "beers?page=2&per_page=", 1}
        };
    }
}
