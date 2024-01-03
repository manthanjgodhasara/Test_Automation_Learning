package runner;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

//@RunWith(runner.CucumberRclRunner.class)
@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/Features",
        glue = "stepDefinitions",
        tags = "@tag2"
//        plugin = {"pretty", "json:target/cucumber-reports/cucumber.json"}
)
public class TestRunner {
}