package school.redrover.old;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import java.time.Duration;

@Ignore
public class GribovskyTest {

    private static final String FIRSTNAME = "Mikhail";
    private static final String LASTNAME = "Gribovsky";
    private static final String URL = "https://demoqa.com/automation-practice-form";
    private static final String EMAIL = "test@test.com";
    private static final String PHONE = "1234567890";

    @Test
    void testDemoQAForm(){

        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        try {
            driver.get(URL);

            WebElement firstNameField = wait.until
                    (ExpectedConditions.visibilityOfElementLocated(
                            By.id("firstName")));
            firstNameField.sendKeys(FIRSTNAME);

            WebElement lastNameField = wait.until
                    (ExpectedConditions.visibilityOfElementLocated(
                            By.id("lastName")));
            lastNameField.sendKeys(LASTNAME);

            WebElement userEmailField = wait.until
                    (ExpectedConditions.visibilityOfElementLocated(
                            By.id("userEmail")));
            userEmailField.sendKeys(EMAIL);

            WebElement genderButton = wait.until
                    (ExpectedConditions.visibilityOfElementLocated(
                            By.cssSelector("input[value='Male']")));
            genderButton.click();

            WebElement phoneField = wait.until
                    (ExpectedConditions.visibilityOfElementLocated(
                            By.id("userNumber")));
            phoneField.sendKeys(PHONE);


            WebElement submitButton = wait.until
                    (ExpectedConditions.visibilityOfElementLocated(
                            By.id("submit")));

            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", submitButton);

            WebElement modalTitle = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.id("example-modal-sizes-title-lg")));
            Assert.assertEquals(modalTitle.getText(), "Thanks for submitting the form");

        }
        finally {
            driver.quit();
        }


    }
}
