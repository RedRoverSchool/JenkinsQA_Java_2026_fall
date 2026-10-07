package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.time.Duration;

public class RomanKalvinTest {
    @Test
    public void seleniumTest(){
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        // 1. Инициализируем явное ожидание (WebDriverWait) вместо неявного
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.get("https://belpost.by/");
        System.out.println(driver.getTitle());

        WebElement searchInput = wait.until(
                ExpectedConditions.elementToBeClickable(By.cssSelector("input[placeholder*='Введите 13-значный номер отправления']"))
        );
        WebElement searchButton = wait.until(
                ExpectedConditions.elementToBeClickable(By.cssSelector("form button[type='submit'], .search-button"))
        );
        searchInput.sendKeys("BB123456789BY");
        searchButton.click();

        System.out.println(driver.getTitle());

        driver.quit();
    }
}
