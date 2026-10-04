package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

public class ProzCookieBannerTest {
    private static final String SITE_URL = "https://www.auto.bg";
    private WebDriver driver;
    WebDriverWait wait;


    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.get(SITE_URL);
        wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("cookiescript_header")));
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void cookieBannerExistTest() {
        WebElement cookiescriptInjected = driver.findElement(By.id("cookiescript_injected"));
        WebElement cookiescriptHeader = driver.findElement(By.id("cookiescript_header"));
        WebElement cookiescriptDescription = driver.findElement(By.id("cookiescript_readmore"));
        WebElement cookiescriptClose = driver.findElement(By.id("cookiescript_close"));
        WebElement cookiescriptAccept = driver.findElement(By.id("cookiescript_accept"));
        WebElement cookiescriptReject = driver.findElement(By.id("cookiescript_reject"));

        Assert.assertTrue(cookiescriptInjected.isDisplayed());

        Assert.assertEquals(cookiescriptHeader.getText(), "Отговорно използване на вашите данни");
        Assert.assertTrue(cookiescriptDescription.getText().contains("Политика за поверителност"));
        Assert.assertEquals(cookiescriptDescription.getDomAttribute("href"), "/gc?act=2");

        Assert.assertTrue(cookiescriptClose.isEnabled());
        Assert.assertTrue(cookiescriptClose.isDisplayed());
        Assert.assertEquals(cookiescriptClose.getDomAttribute("role"), "button");

        Assert.assertEquals(cookiescriptAccept.getText(), "ПРИЕМЕТЕ ВСИЧКИ");
        Assert.assertEquals(cookiescriptAccept.getDomAttribute("role"), "button");

        Assert.assertEquals(cookiescriptReject.getText(), "ОТХВЪРЛЕТЕ ВСИЧКИ");
        Assert.assertEquals(cookiescriptReject.getDomAttribute("role"), "button");

    }

    @Test
    public void cookieBannerAcceptedTest() {
        WebElement cookiescriptInjected = driver.findElement(By.id("cookiescript_injected"));
        WebElement cookiescriptAccept = driver.findElement(By.id("cookiescript_accept"));

        Assert.assertTrue(cookiescriptInjected.isDisplayed());
        Assert.assertEquals(cookiescriptAccept.getText(), "ПРИЕМЕТЕ ВСИЧКИ");
        Assert.assertEquals(cookiescriptAccept.getDomAttribute("role"), "button");
        cookiescriptAccept.click();
        Assert.assertTrue(
                waitBannerDisappearance("cookiescript_injected"),
                "Баннер должен исчезнуть после принятия");

    }

    @Test
    public void cookieBannerRejectedTest() {
        WebElement cookiescriptReject = driver.findElement(By.id("cookiescript_reject"));

        Assert.assertTrue(cookiescriptReject.isDisplayed());
        Assert.assertEquals(cookiescriptReject.getText(), "ОТХВЪРЛЕТЕ ВСИЧКИ");
        Assert.assertEquals(cookiescriptReject.getDomAttribute("role"), "button");
        cookiescriptReject.click();
        Assert.assertTrue(
                waitBannerDisappearance("cookiescript_injected"),
                "Баннер должен исчезнуть после отклонения");
    }

    private boolean waitBannerDisappearance(String attributeID) {
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id(attributeID)));
    }
}



