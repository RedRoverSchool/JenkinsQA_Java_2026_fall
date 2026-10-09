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
public class SparrowTest {
    @Test
    public void testSeleneum() {
        WebDriver driver = new ChromeDriver();

        driver.get("https://dlptest.com/");

        driver.getTitle();

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

        WebElement httpsPost = driver.findElement(By.xpath("//*[@id=\"primary-navigation\"]/a[4]"));
        httpsPost.click();

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

        WebElement textArea = driver.findElement(By.xpath("//*[@id=\"pt-message\"]"));
        WebElement submitButton = driver.findElement(By.xpath("/html/body/main/div/form[1]/button"));

        textArea.sendKeys("Selenium");
        submitButton.click();

        WebElement messageByRole = driver.findElement(By.xpath("/html/body/main/div/form[1]/p"));
        Assert.assertTrue(messageByRole.isDisplayed(), "Received. Nothing was stored. Check your DLP console for an incident.");

        driver.quit();
    }

}
