

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


    @Test
    public void testWiki() {
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

        driver.get("https://www.wikipedia.org/");

        WebElement textBox = driver.findElement(By.id("searchInput"));
        WebElement submitButton = driver.findElement(By.className("pure-button-primary-progressive"));

        textBox.sendKeys("Selenium");
        submitButton.click();

        WebElement head = driver.findElement(By.id("firstHeading"));

        Assert.assertEquals(head.getText(), "Selenium");

        driver.quit();
    }

    @Ignore
    public class SergeyTest {

        @Test
        public void testSelenium() {
            WebDriver driver = new ChromeDriver();

            driver.get("https://www.selenium.dev/selenium/web/web-form.html");

            IO.println(driver.getTitle());

            driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

            WebElement textBox = driver.findElement(By.name("my-text"));
            WebElement submitButton = driver.findElement(By.cssSelector("button"));

            textBox.sendKeys("Selenium");
            submitButton.click();

            WebElement message = driver.findElement(By.id("message"));

            Assert.assertEquals(message.getText(), "Received!");

            driver.quit();
        }

    }
}