package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class NadiaLidtTest {

    @Test
    public void testFistTest() {
        String expectedResult = "Yes";

        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

        driver.get("https://demoqa.com/");

        WebElement menuElemets = driver.findElement(By.linkText("Elements"));
        menuElemets.click();

        WebElement menuRadioButton = driver.findElement(By.linkText("Radio Button"));
        menuRadioButton.click();

        WebElement yesRadoiButton = driver.findElement(By.id("yesRadio"));
        yesRadoiButton.click();

        WebElement message = driver.findElement(By.className("text-success"));

        Assert.assertEquals(message.getText(), expectedResult);

        driver.quit();
    }
}
