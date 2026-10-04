package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.time.Duration;

public class IrinaNTest {

    @Test

    public void testWebPage() {
        ChromeOptions options = new ChromeOptions();
        WebDriver driver = new ChromeDriver(options);

        try {

            driver.get("https://the-internet.herokuapp.com");
            System.out.println(driver.getTitle());
            System.out.println(driver.getCurrentUrl());
            driver.getTitle();
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement formAuth = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.cssSelector("a[href='/login']")
                    )
            );

            //click on formAuth

            formAuth.click();

            //enter tomsmith for username and SuperSecretPassword! for the password and hit login button

            WebElement userName = driver.findElement(By.id("username"));
            userName.sendKeys("tomsmith");
            WebElement passWord = driver.findElement(By.id("password"));
            passWord.sendKeys("SuperSecretPassword!");
            WebElement clickButton = driver.findElement(By.className("radius"));
            clickButton.click();

            //return page answer

            WebElement getAnswer = driver.findElement(By.id("flash"));
            String text = getAnswer.getText();
            Assert.assertTrue(text.contains("You logged into a secure area!"));
            System.out.println(text);
        }

        finally {
            driver.quit();
        }

    }
}