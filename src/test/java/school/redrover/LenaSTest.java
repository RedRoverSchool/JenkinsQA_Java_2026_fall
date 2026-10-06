package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class LenaSTest {

    private WebDriver driver;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.get("https://saucedemo.com");
    }

    @Test
    public void testSuccessfulLogin() throws InterruptedException {

        Thread.sleep(2000);

        WebElement usernameField = driver.findElement(By.id("user-name"));
        usernameField.sendKeys("standard_user");

        WebElement passwordField = driver.findElement(By.id("password"));
        passwordField.sendKeys("secret_sauce");

        WebElement loginButton = driver.findElement(By.id("login-button"));
        loginButton.click();

        Thread.sleep(2000);

        WebElement headerTitle = driver.findElement(By.className("title"));
        String actualText = headerTitle.getText();

        Assert.assertEquals(actualText, "Products", "Заголовок страницы не совпадает с ожидаемым!");
    }

    @Test
    public void testIncorrectPassword() throws InterruptedException {

        Thread.sleep(2000);

        WebElement usernameField = driver.findElement(By.id("user-name"));
        usernameField.sendKeys("standard_user");

        WebElement passwordField = driver.findElement(By.id("password"));
        passwordField.sendKeys("incorrect");

        WebElement loginButton = driver.findElement(By.id("login-button"));
        loginButton.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));

        WebElement errorElement = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h3[data-test='error']"))
        );

        String errorText = errorElement.getText();

        Assert.assertEquals(
                errorText,
                "Epic sadface: Username and password do not match any user in this service",
                "Error message is incorrect"
        );

    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}

