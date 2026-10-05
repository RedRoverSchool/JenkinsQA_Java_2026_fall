package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class YaPitonETest {
    @Test
    public void testSubmit() {
        WebDriver driver = new ChromeDriver();

        driver.get("https://www.qa-practice.com/elements/textarea/single");

        driver.getTitle();

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(5000));

        WebElement textBox = driver.findElement(By.name("text_area"));
        WebElement submitButton = driver.findElement(By.id("submit-id-submit"));

        textBox.sendKeys("Hello world");
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", submitButton);

        WebElement message = driver.findElement(By.id("result-text"));
        message.getText();

        Assert.assertEquals(message.getText(), "Hello world");

        driver.quit();
    }
}

