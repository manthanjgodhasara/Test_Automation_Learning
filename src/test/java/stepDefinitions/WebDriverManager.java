package stepDefinitions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class WebDriverManager {
    private static WebDriver driver;

    private WebDriverManager() {
        // Private constructor to prevent external instantiation
    }

    public static WebDriver getDriver() {
        if (driver == null) {
            String projectPath = System.getProperty("user.dir");
            System.setProperty("webdriver.chrome.driver", projectPath + "/src/test/resources/Driver/chromedriver.exe");
            driver = new ChromeDriver();
        }
        return driver;
    }

    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
