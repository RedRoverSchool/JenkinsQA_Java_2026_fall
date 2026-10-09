package school.redrover.old;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

@Ignore
public class MariiaSeTest {

     @Test
     public void testWebPage (){
        WebDriver driver = new ChromeDriver();

        try {
            driver.get("https://www.selenium.dev/");

            WebElement documentation = driver.findElement(By.cssSelector("a[href='/documentation']"));
            documentation.click();

            WebElement pageHeader = driver.findElement(By.cssSelector(".td-content h1"));

            Assert.assertEquals(pageHeader.getText(), "The Selenium Browser Automation Project");
        }
        finally {
            driver.quit();
        }
     }
}
