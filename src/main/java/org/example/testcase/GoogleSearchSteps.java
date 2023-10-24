package org.example.testcase;

import org.jbehave.core.annotations.*;
import org.jbehave.core.steps.Steps;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import core.helper.OSUtils;
public class GoogleSearchSteps extends Steps {
    private WebDriver driver;

    @Given("the user is on the Google homepage")
    public void openGoogleHomepage() {
        String path = "/src/main/resources/driver";
        switch (OSUtils.getOS()) {
            case MAC:
                path = path + "/chromedriver-mac-arm64/chromedriver";
                break;
            case WINDOWS:
                path = path + "/chromedriver-win64/chromedriver.exe";
                break;
        }

        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + path);
        driver = new ChromeDriver();
        driver.get("https://www.google.com");
    }

    @When("the user searches for \"$searchKeyword\"")
    public void searchForKeyword(@Named("searchKeyword") String searchKeyword) {
        WebElement searchBox = driver.findElement(By.name("q"));
        searchBox.sendKeys(searchKeyword);
        searchBox.submit();
    }

    @Then("the user should see the text \"$expectedText\"")
    public void verifyText(@Named("expectedText") String expectedText) {
        WebElement result = driver.findElement(By.xpath("//h3[text()=\"What is JBehave?\"]"));
        String actualText = result.getText();
        assert actualText.contains(expectedText);
    }

    @AfterScenario
    public void closeBrowser() {
        if (driver != null) {
            driver.quit();
        }
    }
}

