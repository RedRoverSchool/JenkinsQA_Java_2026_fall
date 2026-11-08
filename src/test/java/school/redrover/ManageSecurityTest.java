package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

public class ManageSecurityTest extends BaseTest {

    @Test
    public void testOpen() {
        getDriver().findElement(By.cssSelector("a[href='/manage']")).click();
        getDriver().findElement(By.cssSelector("a[href='configureSecurity']")).click();

        Assert.assertEquals(getDriver().findElement(By.className("jenkins-app-bar__content")).getText(), "Security");
    }

    @Test
    public void testDisable() {
        testOpen();
        getDriver().findElement(By.name("_.disableRememberMe")).sendKeys(Keys.SPACE);
        getDriver().findElement(By.cssSelector(".jenkins-submit-button")).click();

        WebElement userMenuButton = getWait5().until(ExpectedConditions.presenceOfElementLocated(
                By.id("root-action-UserAction")));

        Actions actions = new Actions(getDriver());
        actions.moveToElement(userMenuButton).perform();

        WebElement signOutLink = getWait10().until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("a[href='/logout']")));
        signOutLink.click();

        Assert.assertEquals(getDriver().findElements(By.className("jenkins-checkbox")).size(), 0);
    }
}
