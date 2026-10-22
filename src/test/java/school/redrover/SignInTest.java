package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;

public class SignInTest extends BaseTest {
    @BeforeMethod
    public void loginPage() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));

        WebElement userMenuButton = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.id("root-action-UserAction")));

        Actions actions = new Actions(getDriver());
        actions.moveToElement(userMenuButton).perform();

        WebElement signOutLink = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("a[href='/logout']")));
        signOutLink.click();
    }


    @Test
    public void testCorrectLogin() {

        getDriver().findElement(By.cssSelector("#j_username")).sendKeys("admin");
        getDriver().findElement(By.cssSelector("#j_password")).sendKeys(getJenkinsPassword());
        getDriver().findElement(By.xpath("//*[@id='main-panel']/div/form/button")).click();

        String expectedUrl = "http://localhost:8080/";
        String actualUrl = getDriver().getCurrentUrl();
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
        wait.until(ExpectedConditions.not(ExpectedConditions.urlContains("login")));

        Assert.assertEquals(actualUrl, expectedUrl,
                "Адрес страницы не совпадает с ожидаемым!");
    }

    public String getJenkinsPassword() {
        // Путь к секретному паролю Jenkins по умолчанию
        Path tokenPath = Paths.get(System.getProperty("user.home"), ".jenkins", "secrets", "initialAdminPassword");
        try {
            if (Files.exists(tokenPath)) {
                return Files.readString(tokenPath).trim();
            }
        } catch (Exception e) {
            // Если не удалось прочитать, используем дефолтный
        }
        return "admin";

    }
}
