package school.redrover.old;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import java.time.Duration;

@Ignore
public class DmitriTest {

    @Test
    public void testSeleniumWebForm(){

        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

        driver.get("https://www.selenium.dev/selenium/web/web-form.html");

        WebElement textInput = driver.findElement(By.name("my-text"));
        textInput.sendKeys("Test");
        WebElement textPassword = driver.findElement(By.name("my-password"));
        textPassword.sendKeys("test123");
        WebElement textArea = driver.findElement(By.cssSelector("textarea.form-control"));
        textArea.sendKeys("This is the new test");

        WebElement datePicker = driver.findElement(By.cssSelector("input[name='my-date']"));
        datePicker.sendKeys("10/10/2010");
        WebElement exampleRange = driver.findElement(By.name("my-range"));
        exampleRange.sendKeys(Keys.ARROW_RIGHT);
        WebElement submitButton = driver.findElement(By.cssSelector("button"));
        submitButton.click();

        WebElement message = driver.findElement(By.id("message"));
        message.getText();

        driver.quit();
    }
}
