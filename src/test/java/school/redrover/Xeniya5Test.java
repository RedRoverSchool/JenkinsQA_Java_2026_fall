package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class Xeniya5Test {

    @Test
    public void testSimulator() {
        WebDriver driver = new ChromeDriver(); // запускаем Chrome
        try {
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(500));
            driver.get("https://simulator.electude.com/");
            driver.findElement(By.name("email")).sendKeys("test");

            // Нажать на кнопку Испытайте симулятор Electude
            // Проверить переход на страницу с названием Electude Simulator Challenge - Experiment
            WebElement button = driver.findElement(By.xpath("//*[@id=\"home\"]/div/a"));
            button.click();
            WebElement textH1 = driver.findElement(By.id("title"));
            Assert.assertEquals(textH1.getText(), "Electude Simulator Challenge - Experiment");
            System.out.println("OK");

        } finally {
            driver.quit();
        }
    }
}
