package school.redrover.old;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import java.time.Duration;

@Ignore
public class AntonTest {
    @Test
    public void testInteractions() throws InterruptedException {
        WebDriver driver = new ChromeDriver();

        driver.get("https://demoqa.com/");
        System.out.println(driver.getTitle());
        Thread.sleep(2000);

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        Thread.sleep(2000);

        driver.findElement(By.xpath("//h5[text()='Interactions']")).click();
        Thread.sleep(2000);

        Assert.assertTrue(driver.getCurrentUrl().contains("interaction"));


        driver.quit();
    }

}
