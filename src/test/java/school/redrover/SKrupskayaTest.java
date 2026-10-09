package school.redrover;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class SKrupskayaTest {

    @Test
    public static void firstTest() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://practice-automation.com/click-events/");
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
        WebElement button = driver.findElement(By.xpath("//button[@OnClick='catSound()']"));
        button.click();
        driver.quit();
    }
}