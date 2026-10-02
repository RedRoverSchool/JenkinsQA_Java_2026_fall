package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class ViktoriyaTest {

    @Test
    public void checkSauceDemoLoginAndCart() {
        WebDriver driver = new ChromeDriver();

        try {
            driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
            driver.get("https://www.saucedemo.com/");

            driver.findElement(By.id("user-name")).sendKeys("standard_user");
            driver.findElement(By.id("password")).sendKeys("secret_sauce");
            driver.findElement(By.id("login-button")).click();

            Assert.assertEquals(
                    driver.findElement(By.cssSelector(".title")).getText(),
                    "Products"
            );

            driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();

            Assert.assertEquals(
                    driver.findElement(By.cssSelector(".shopping_cart_badge")).getText(),
                    "1"
            );

            driver.findElement(By.cssSelector(".shopping_cart_link")).click();

            Assert.assertEquals(
                    driver.findElement(By.cssSelector(".inventory_item_name")).getText(),
                    "Sauce Labs Backpack"
            );

        } finally {
            driver.quit();
        }
    }
}
