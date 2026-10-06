package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class CipCipTest {
    @Test
    public void testSum(){
        WebDriver driver = new ChromeDriver();

        String testName= "Cip";
        driver.get("https://demoqa.com/automation-practice-form");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement name = driver.findElement(By.id("firstName"));
        name.click();
        name.sendKeys(testName);

        WebElement surname = driver.findElement(By.id("lastName"));
        surname.click();
        surname.sendKeys(testName);

        WebElement gender = driver.findElement(By.id("gender-radio-2"));
        gender.click();

        WebElement number = driver.findElement(By.id("userNumber"));
        number.click();
        number.sendKeys("1234567891");

        WebElement submitButton = driver.findElement(By.id("submit"));
        submitButton.click();

        WebElement resName = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector(".table-hover tbody tr:nth-child(1) td:nth-child(2)")
                )
        );
        Assert.assertEquals(resName.getText(), testName + " " + testName);
        driver.quit();
    }
}
