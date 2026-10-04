package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class QAtoriumTest {
    @Test
    public static void testXYZBank() {
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1000));
        
        driver.get("https://www.globalsqa.com/angularJs-protractor/BankingProject/#/login");
        driver.findElement(By.xpath("//button[contains(@ng-click,'customer')]")).click();
        driver.findElement(By.xpath("//option[@value='1']")).click();
        driver.findElement(By.xpath("//button[@type='submit']")).click();

        Assert.assertEquals(driver.findElement(By.cssSelector(".fontBig")).getText(),"Hermoine Granger" );
        driver.quit();
    }
}

