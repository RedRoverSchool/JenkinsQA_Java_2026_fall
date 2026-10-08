package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.time.Duration;


public class IlyaVorobyevTest {

    @Test
    public void testDemoQA() {

        WebDriver driver = new ChromeDriver();
        driver.get("https://demoqa.ru/qa-auto/elements/radio-button");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        Assert.assertEquals(driver.getTitle(), "Radio Button - Тестирование радиокнопок | DemoQA");

        WebElement radioButtonLikeSite = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//*[@id=\"likeWebsite-yes\"]")
                )
        );

        Assert.assertEquals(radioButtonLikeSite.getAttribute("data-state"), "unchecked");
        Assert.assertEquals(radioButtonLikeSite.getAttribute("value"), "yes");

        radioButtonLikeSite.click();

        Assert.assertEquals(radioButtonLikeSite.getAttribute("data-state"), "checked");
        Assert.assertEquals(radioButtonLikeSite.getAttribute("value"), "yes");

        driver.quit();

    }

}
