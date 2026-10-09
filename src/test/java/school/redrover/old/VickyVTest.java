package school.redrover.old;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import java.time.Duration;

@Ignore
public class VickyVTest {

    @Test
    public void testTitleMainPage() {
        WebDriver driver = new ChromeDriver();

        driver.get("https://qapracticehub.com");

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        Assert.assertEquals(driver.getTitle(), "QA Practice Hub — Free Website to Practice Test Automation (Selenium, Playwright, Cypress)");

        driver.quit();
    }
    @Test
    public void testH1MainPage() {
        WebDriver driver = new ChromeDriver();

        driver.get("https://qapracticehub.com");

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        Assert.assertEquals(driver.findElement(By.tagName("h1")).getText(), "QA Practice Hub");

        driver.quit();
    }

}
