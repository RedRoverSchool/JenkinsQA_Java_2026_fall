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
public class AlischerTest {

    @Test
    public void testSportbox() {
        WebDriver driver = new ChromeDriver();
        driver.get("https://news.sportbox.ru/");

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1000));

        driver.findElement(By.xpath("//span[contains(text(), 'Матч ТВ. Прямой')]")).click();
        WebElement live = driver.findElement(By.cssSelector("#node-header .col-lg-12 h1"));

        Assert.assertEquals(live.getText(), "Прямой эфир телеканала «Матч ТВ»");

        driver.quit();
    }
}
