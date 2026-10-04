package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class GroupAutumnSetTest {
    @Test
    public void formErrorTest() {
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
        try {
            driver.get("https://prosto4.ru/");
            driver.findElement(By.xpath("//*[@id='page-content']/section[1]/div[2]/div/div[1]/div/div[6]/button")).click();
            driver.findElement(By.xpath("//*[@id='wpcf7-f1082-p584-o1']/form/p/span[1]/input")).sendKeys("London");
            driver.findElement(By.xpath("//*[@id='wpcf7-f1082-p584-o1']/form/p/input")).click();

            WebElement massage = driver.findElement(By.xpath("/html/body/div[5]/div/div/div/section/div/div/div/div/div[5]/div/form/div"));

            Assert.assertEquals(massage.getText(),"Одно или несколько полей содержат ошибочные данные. Пожалуйста, проверьте их и попробуйте ещё раз.");
        } finally {
            driver.quit();
        }
    }
}
