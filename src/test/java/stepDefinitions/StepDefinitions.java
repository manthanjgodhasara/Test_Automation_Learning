package stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.openqa.selenium.WebDriver;

import static org.junit.Assert.assertEquals;

public class StepDefinitions {

    WebDriver driver;

    public StepDefinitions(){
        driver = WebDriverManager.getDriver();
    }


    @Given("I open the Google website")
    public void iOpenGoogleWebsite() throws InterruptedException{
        driver.get("https://www.google.com");
        System.out.println("Visit google website");
        Thread.sleep(5000);
    }

    @Given("I open the Bing website")
    public void iOpenBingWebsite() throws InterruptedException{
        driver.get("https://www.bing.com");
        System.out.println("Visit bing website");
        Thread.sleep(5000);
    }

    @Then("^the title should be \"([^\"]*)\"$")
    public void theTitle1ShouldBe(String expectedTitle) {
        String actualTitle = driver.getTitle();
        assertEquals(expectedTitle, actualTitle);
        System.out.println("Match the title and quit driver");
        driver.quit();
    }
}
