package school.redrover.old;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

@Ignore
public class YanaAndreevaTest {

    @Test
    public void checkYesRadioButton() {

        WebDriver driver = new ChromeDriver();

        driver.get("https://demoqa.com/radio-button");

        driver.findElement(By.cssSelector("label[for='yesRadio']")).click();

        WebElement result = driver.findElement(By.cssSelector("p.mt-3"));

        assertNotNull(result, "Check result is not null");
        assertEquals(result.getText(), "You have selected Yes", "Check result text");

        driver.quit();
    }
}
