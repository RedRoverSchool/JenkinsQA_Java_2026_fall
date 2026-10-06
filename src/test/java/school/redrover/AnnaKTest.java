package school.redrover;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class AnnaKTest {

    @Test
    public void TestSel() {
        WebDriver driver = new ChromeDriver();

        driver.get("https://okolo.city/");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        driver.getTitle();

        WebElement textBox = driver.findElement(By.cssSelector("input[placeholder*='Попробуйте']"));
        textBox.sendKeys("Карьер");

        WebElement submitButton = driver.findElement(By.xpath("//button[contains(text(), 'Найти место')]"));
        submitButton.click();


        WebElement resultInput = driver.findElement(By.cssSelector("input[type='text'], input[type='search'], .search-input"));
        String actualText = resultInput.getAttribute("value");
        Assert.assertEquals(actualText, "Карьер");

        driver.quit();
    }
}
