package school.redrover.old;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Ignore
public class VitaliyTest {

    WebDriver driver;
    WebDriverWait wait;

//    @BeforeTest
//    public void setup() {
//        driver = new ChromeDriver();
//        wait = new WebDriverWait(driver, Duration.ofSeconds(3));
//        driver.get("https://bonigarcia.dev/selenium-webdriver-java/");
//    }

//    @AfterTest
//    public void tearDown() {
//        driver.quit();
//    }

    @Test
    public void chapter3ContentBtnSizeTest() {
        List<WebElement> chapter3Links;
        chapter3Links = driver.findElements(By.xpath("//h5[contains(text(), 'Chapter 3. WebDriver Fundamentals')]/..//a[@class='btn btn-outline-primary mb-2']"));

        Assert.assertEquals(chapter3Links.size(), 8);
    }

    @Test
    public void chapter3ContentTest() {
        List<String> expectedChapter3BtnNames =
                List.of("Web form", "Navigation", "Dropdown menu", "Mouse over", "Drag and drop",
                        "Draw in canvas", "Loading images", "Slow calculator");
        List<String> actualChapter3BtnNames = new ArrayList<>();
        List<WebElement> chapter3Links = driver.findElements(By.xpath(
                "//h5[contains(text(), 'Chapter 3. WebDriver Fundamentals')]/..//a[@class='btn btn-outline-primary mb-2']"));

        for (WebElement element : chapter3Links) {
            actualChapter3BtnNames.add(element.getText());
        }

        Assert.assertEquals(actualChapter3BtnNames, expectedChapter3BtnNames);
    }

    @Test
    public void openWebFormTest() {
        WebElement webFormBtn = driver.findElement(By.xpath("//a[@href='web-form.html']"));
        webFormBtn.click();
        WebElement title = wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath("//h1[contains(text(), 'Web form')]")));

        Assert.assertTrue(title.isDisplayed());
    }
}
