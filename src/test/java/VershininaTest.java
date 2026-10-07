import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

public class VershininaTest {
    @Test
    public void testPlayGround() {
        for (int i = 0; i < 3; i++) {
            WebDriver driver = new ChromeDriver();
            driver.get("http://uitestingplayground.com/classattr");
            WebElement blueBtn = driver.findElement(By.cssSelector("button.btn.btn-primary"));
            blueBtn.click();

            driver.quit();
        }
    }
}

