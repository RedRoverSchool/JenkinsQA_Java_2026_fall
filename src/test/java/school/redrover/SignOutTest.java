package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

import java.util.List;

public class SignOutTest extends BaseTest {

    @Test
    public void testSignOutRedirectsToLoginPage() {
        getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.id("jenkins-head-icon")));

        WebElement userMenuButton = getDriver().findElement(By.id("root-action-UserAction"));

        Actions actions = new Actions(getDriver());
        actions.moveToElement(userMenuButton).perform();

        WebElement signOutLink = getDriver().findElement(By.cssSelector("a[href='/logout']"));
        signOutLink.click();

        getWait10().until(ExpectedConditions.urlContains("login"));

        WebElement usernameField = getDriver().findElement(By.name("j_username"));
        WebElement passwordField = getDriver().findElement(By.name("j_password"));

        Assert.assertEquals(usernameField.getAttribute("value"), "", "Поле Username не пустое!");
        Assert.assertEquals(passwordField.getAttribute("value"), "", "Поле Password не пустое!");
    }

    @Test
    public void testSignOutHasNoConfirmationDialog() {
        getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.id("jenkins-head-icon")));

        WebElement userMenuButton = getDriver().findElement(By.id("root-action-UserAction"));

        Actions actions = new Actions(getDriver());
        actions.moveToElement(userMenuButton).perform();

        WebElement signOutLink = getDriver().findElement(By.cssSelector("a[href='/logout']"));
        signOutLink.click();

        getWait10().until(ExpectedConditions.urlContains("login"));
        getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.name("j_username")));

        List<WebElement> dialogs = getDriver().findElements(By.cssSelector(".jenkins-dialog, .modal"));

        Assert.assertTrue(dialogs.isEmpty(), "Появилось окно подтверждения или предупреждения при выходе!");
    }
}