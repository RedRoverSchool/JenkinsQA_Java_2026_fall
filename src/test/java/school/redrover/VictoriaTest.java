package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class VictoriaTest {
    @Test
    public void testHealbe() {
        WebDriver driver = new ChromeDriver();

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

        driver.get("https://healbe.ru/");

        WebElement cookie = driver.findElement(By.cssSelector(".check-cookie-policy .yes-btn"));
        cookie.click();

        WebElement blueButton = driver.findElement(By.xpath("//*[contains(@class, 'hp-prod__colors')]//button[@aria-label='Светло-голубой']"));
        blueButton.click();

        WebElement price = driver.findElement(By.className("hp-prod__price"));

        Assert.assertEquals(price.getText(), "21 500 ₽");

        driver.quit();
    }
}
