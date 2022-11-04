package Test.Gherkins.Runners;

import com.framework.listeners.GherkinListener;
import io.cucumber.testng.*;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"Test/Gherkins"},
        dryRun = false
)
@Listeners(GherkinListener.class)
public class TechnicalTestStockbitRunner123 extends AbstractTestNGCucumberTests {
    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios(){
        return super.scenarios();
    }
}
