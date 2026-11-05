

package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.common.JenkinsUtils;
import school.redrover.common.ProjectUtils;

import java.time.Duration;
import java.util.List;


public class ThemeTest extends BaseTest {

    @Test
    public void testChangeThemeFromHomePage() {

        WebElement userIcon = getDriver().findElement(By.className("jenkins-avatar"));
        new Actions(getDriver()).moveToElement(userIcon).perform();

        getWait10().until(ExpectedConditions.elementToBeClickable(By.cssSelector(".tm-account-theme-picker-select"))).click();
        getWait10().until(ExpectedConditions.elementToBeClickable(By.xpath("//*[normalize-space(text())='Dark']"))).click();

        boolean isDarkTheme = !getDriver().findElements(By.cssSelector("html[data-theme='dark']")).isEmpty();
        Assert.assertTrue(isDarkTheme);
    }

    @Test
    public void testChangeThemeFromAppearance() {

        WebElement userIcon = getDriver().findElement(By.className("jenkins-avatar"));
        new Actions(getDriver()).moveToElement(userIcon).perform();

        getWait10().until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href=\"/user/admin/appearance\"]"))).click();
        getDriver().findElement(By.xpath("//label[@for=\"radio-block-1\"]")).click();
        getDriver().findElement(By.name("Submit")).click();

        boolean isDarkTheme = !getDriver().findElements(By.cssSelector("html[data-theme='dark']")).isEmpty();
        Assert.assertTrue(isDarkTheme);
    }

    @Test
    public void testCheckingThemeAfterLogOut() {
        changeTheme();

        getWait10().until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("html[data-theme='dark']")));

        JenkinsUtils.logout(getDriver());
        getDriver().get(ProjectUtils.getUrl() + "login");
        JenkinsUtils.login(getDriver());

        getWait10().until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[@class='app-jenkins-logo']")));

        boolean isDarkTheme = !getDriver().findElements(By.cssSelector("html[data-theme='dark']")).isEmpty();

        Assert.assertTrue(isDarkTheme);
    }

    private void changeTheme() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));

        WebElement userButton = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("root-action-UserAction")));

        Actions actions = new Actions(getDriver());
        actions.moveToElement(userButton).perform();

        getWait10().until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.jenkins-dropdown__item[href*='/appearance']"))).click();
        getWait10().until(ExpectedConditions.urlContains("/appearance"));
        getWait10().until(ExpectedConditions.elementToBeClickable(By.cssSelector("label[for='radio-block-1']"))).click();
        getWait10().until(ExpectedConditions.elementToBeSelected(By.id("radio-block-1")));

        WebElement submitButton = wait.until(
                ExpectedConditions.elementToBeClickable(By.name("Submit")));
        submitButton.click();
        wait.until(ExpectedConditions.stalenessOf(submitButton));
    }
}