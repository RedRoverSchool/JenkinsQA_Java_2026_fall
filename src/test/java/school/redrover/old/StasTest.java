package school.redrover.old;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import java.time.Duration;

@Ignore
public class StasTest {

    @Test
    public void testSearchOnSeleniumSite() {
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();

        driver.get("https://www.selenium.dev/");

        var searchButton = driver.findElement(By.cssSelector(".DocSearch-Button"));
        searchButton.click();

        var searchInput = driver.findElement(By.cssSelector(".DocSearch-Input"));
        searchInput.sendKeys("webdriver");

        String actualText = searchInput.getAttribute("value");
        Assert.assertEquals(actualText, "webdriver");

        searchInput.sendKeys(Keys.ENTER);

        driver.quit();
    }
}
