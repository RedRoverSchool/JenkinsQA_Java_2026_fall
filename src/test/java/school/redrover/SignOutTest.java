package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

import java.time.Duration;
import java.util.List;

public class SignOutTest extends BaseTest {

    // ТЕСТ ДЛЯ (TC_08.002.001)
    @Test
    public void testSignOutRedirectsToLoginPage() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        Actions actions = new Actions(getDriver());

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("jenkins-head-icon")));

        WebElement userMenuButton = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.id("root-action-UserAction")));

        actions.moveToElement(userMenuButton).perform();

        WebElement signOutLink = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("a[href='/logout']")));

        signOutLink.click();

        wait.until(ExpectedConditions.urlContains("login"));

        WebElement usernameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("j_username")));
        WebElement passwordField = getDriver().findElement(By.name("j_password"));

        Assert.assertEquals(usernameField.getAttribute("value"), "", "Поле Username не пустое!");
        Assert.assertEquals(passwordField.getAttribute("value"), "", "Поле Password не пустое!");
    }

    // ТЕСТ ДЛЯ  (TC_08.002.002)
    @Test
    public void testSignOutHasNoConfirmationDialog() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        Actions actions = new Actions(getDriver());

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("jenkins-head-icon")));

        WebElement userMenuButton = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.id("root-action-UserAction")));
        actions.moveToElement(userMenuButton).perform();

        WebElement signOutLink = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("a[href='/logout']")));
        signOutLink.click();

        wait.until(ExpectedConditions.urlContains("login"));

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("j_username")));

        List<WebElement> dialogs = getDriver().findElements(By.cssSelector(".jenkins-dialog, .modal"));

        Assert.assertTrue(dialogs.isEmpty(), "Появилось окно подтверждения или предупреждения при выходе!");
    }
}