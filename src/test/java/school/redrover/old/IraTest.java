package school.redrover.old;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

@Ignore
public class IraTest {
    @Test
    public void testDemoqa (){
        WebDriver driver = new ChromeDriver();
        driver.get("https://demoqa.com/");

        WebElement element = driver.findElement(By.linkText("Elements"));
        element.click();

        WebElement text = driver.findElement(By.cssSelector(".col-12.mt-4.col-md-6.col-xl-7"));

        Assert.assertEquals(text.getText(), "Please select an item from left to start practice.");
    }

}
