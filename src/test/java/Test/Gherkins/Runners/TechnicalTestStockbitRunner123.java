package Test.Gherkins.Runners;

import com.aventstack.extentreports.ExtentReports;
import com.framework.factory.DriverFactory;
import com.framework.listeners.GherkinListener;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Scenario;
import io.cucumber.testng.*;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.annotations.*;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"Test/Gherkins"},
//        tags = "@smoke",
        dryRun = false,
        monochrome = true,
        plugin = {
                "pretty",
                "html:target/cucumber-html-report.html",
                "json:target/HtmlReport/cucumber.json",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
        }
)
@Listeners(GherkinListener.class)
public class TechnicalTestStockbitRunner123 extends AbstractTestNGCucumberTests {
    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios(){
        return super.scenarios();
    }
    @AfterStep
    public void screenshot(Scenario scenario){
        if(scenario.isFailed()){
            final byte[] screenshot = ((TakesScreenshot) DriverFactory.init().get()).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot,"image/png","image");
        }
    }
}
