package school.redrover.old;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import java.time.Duration;

@Ignore
public class PolinaTest {
    @Test
    public void firstTest() {
        WebDriver driver = new ChromeDriver();

        driver.get("https://practice-automation.com/click-events/");

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

        WebElement submitButton = driver.findElement(By.xpath("//*[@id='post-3145']/div/div[3]/div/div/div/div[2]/button"));
        submitButton.click();

        WebElement message = driver.findElement(By.id("demo"));


        Assert.assertEquals(message.getText(), "Woof!");
        driver.quit();
    }
}
