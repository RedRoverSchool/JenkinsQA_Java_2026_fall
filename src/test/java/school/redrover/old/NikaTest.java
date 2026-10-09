package school.redrover.old;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

@Ignore
public class NikaTest {
    WebDriver driver = new ChromeDriver();


    @Test
    public void testLogin(){
        try {
            driver.get("https://www.saucedemo.com/");
            WebElement username = driver.findElement(By.xpath("//*[@aria-label='Username']"));
            username.sendKeys("standard_user");
            WebElement pass = driver.findElement(By.xpath("//*[@type='password']"));
            pass.sendKeys("secret_sauce");
            WebElement button = driver.findElement(By.xpath("//*[@id='login-button']"));
            button.click();
            Assert.assertEquals(driver.getTitle(),"Swag Labs");
        }
        finally {
            driver.quit();
        }

    }
}
