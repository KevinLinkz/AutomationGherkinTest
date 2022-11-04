package Test.Gherkins.StepDefinitions;

import com.aventstack.extentreports.Status;
import com.framework.factory.DriverFactory;
import com.framework.pageObjects.BookStorePageObject;
import com.framework.pageObjects.SelectMenuPageObject;
import com.framework.services.ScreenshotService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class TechnicalTestStockbitStepDefinition123 {
    SelectMenuPageObject selectMenuPageObject = new SelectMenuPageObject();
    BookStorePageObject bookStorePageObject = new BookStorePageObject();


    @Given("User go to {string}")
    public void user_go_to(String string) {
        String strStepDescription =createStepDescription(new Object(){},string);
        DriverFactory.init().goto_url(string,strStepDescription);
    }

    @When("User in {string} page")
    public void user_in_page(String string) {
        String strStepDescription =createStepDescription(new Object(){},string);
        selectMenuPageObject.assertHeader(strStepDescription,string);
        //TODO Assert
    }

    @When("User choose select value {string}")
    public void user_choose_select_value(String string)  {
        String strStepDescription =createStepDescription(new Object(){},string);
        selectMenuPageObject.selectDdlValue(strStepDescription,string);
    }


    @When("User choose select one {string}")
    public void user_choose_select_one(String string) {
        String strStepDescription =createStepDescription(new Object(){},string);
        selectMenuPageObject.selectDdlOne(strStepDescription,string);

    }

    @When("User choose old style select menu {string}")
    public void user_choose_old_style_select_menu(String string) {
        String strStepDescription =createStepDescription(new Object(){},string);
        selectMenuPageObject.selectDdlOldStyle(strStepDescription,string);
    }

    @When("User choose multi select drop down all color")
    public void user_choose_multi_select_drop_down_all_color() {
        String strStepDescription =createStepDescription(new Object(){},"All");
        selectMenuPageObject.selectDdlMulti(strStepDescription);

    }

    @Then("User success input all select menu")
    public void user_success_input_all_select_menu() {
        String strStepDescription =createStepDescription(new Object(){},"");
        ScreenshotService.init().screenShotChromes(Status.PASS,strStepDescription);
    }

    @When("User search book {string}")
    public void user_search_book(String string) {
        String strStepDescription =createStepDescription(new Object(){},string);
        bookStorePageObject.searchBook(strStepDescription,string);
    }

    @Then("User see {string}")
    public void user_see(String string) {
        String strStepDescription =createStepDescription(new Object(){},string);
        bookStorePageObject.assertNotFoundData(strStepDescription,string);
    }
    @When("User click book {string}")
    public void user_click_book(String string) {
        String strStepDescription =createStepDescription(new Object(){},"Row 1");
        bookStorePageObject.getDetailItems(strStepDescription);
    }
    @Then("User see detail book {string}")
    public void User_see_detail_book (String string) {
        String strStepDescription =createStepDescription(new Object(){},"Detail Book " +string);
        bookStorePageObject.assertDataDetailBook(strStepDescription);;
    }



    public String createStepDescription(Object objects,String ... strArgs){
        String strStepDescription =
                "Step : " + objects.getClass().getEnclosingMethod().getName() +
                        "</br>Parameters : " + (strArgs[0]!=null?strArgs[0]:"");
        return strStepDescription;
    }

}
