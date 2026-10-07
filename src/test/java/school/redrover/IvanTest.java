package school.redrover;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
public class IvanTest {
    @Test
    public void checkUserNameField() {

        WebDriver driver = new ChromeDriver();

        driver.get("https://demoqa.com/text-box");

        driver.findElement(By.id("userName")).sendKeys("Ivan");

        String value = driver.findElement(By.id("userName"))
                .getAttribute("value");

        Assert.assertEquals(value, "Ivan");

        driver.quit();
    }
}
