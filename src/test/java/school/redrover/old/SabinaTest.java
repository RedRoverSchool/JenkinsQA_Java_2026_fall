package school.redrover.old;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import java.time.Duration;

@Ignore
public class SabinaTest {
    @Test
    public void testWeb(){
        WebDriver driver = new ChromeDriver();

        driver.get("https://demoqa.com");

        driver.getTitle();

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

        WebElement elementButton = driver.findElement(By.xpath("//a[@href='/elements']"));
        elementButton.click();

        driver.quit();
    }

    @Test
    public void testWebElement(){
        WebDriver driver = new ChromeDriver();

        driver.get("https://demoqa.com/elements");

        driver.getTitle();

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));


        WebElement textButton = driver.findElement(By.xpath("//a[@href='/text-box']"));
        textButton.click();

        driver.quit();
    }

    @Test
    public void testWebCheckBox(){
        WebDriver driver = new ChromeDriver();

        driver.get("https://demoqa.com/checkbox");

        driver.getTitle();

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));


        WebElement switcherButton = driver.findElement(By.className("rc-tree-checkbox"));
        switcherButton.click();

        WebElement message = driver.findElement(By.id("result"));
        String actualText = message.getText().replace("\n", " ");
        Assert.assertEquals(actualText, "You have selected : home desktop documents downloads notes commands workspace office wordFile excelFile react angular veu public private classified general");
        driver.quit();
    }
}
