

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

import java.time.Duration;


public class ThemeTest extends BaseTest {

    @Test
    public void testChangeThemeFromHomePage() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));

        WebElement userIcon = getDriver().findElement(By.className("jenkins-avatar"));
        new Actions(getDriver()).moveToElement(userIcon).perform();

        WebElement themeButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector(".tm-account-theme-picker-select")));
        themeButton.click();

        WebElement darkThemeIcon = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[normalize-space(text())='Dark']")
        ));
        darkThemeIcon.click();

        boolean isDarkTheme = !getDriver().findElements(By.cssSelector("html[data-theme='dark']")).isEmpty();

        Assert.assertTrue(isDarkTheme);
    }

    @Test
    public void testChangeThemeFromAppearance() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));

        WebElement userIcon = getDriver().findElement(By.className("jenkins-avatar"));
        new Actions(getDriver()).moveToElement(userIcon).perform();

        WebElement appearanceButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href=\"/user/admin/appearance\"]")));
        appearanceButton.click();

        getDriver().findElement(By.xpath("//label[@for=\"radio-block-1\"]")).click();
        getDriver().findElement(By.name("Submit")).click();

        boolean isDarkTheme = !getDriver().findElements(By.cssSelector("html[data-theme='dark']")).isEmpty();

        Assert.assertTrue(isDarkTheme);
    }

    @Ignore
    @Test
    public void testCheckingThemeAfterLogOut() {
        changeTheme();

        getDriver().manage().deleteAllCookies();
        getDriver().navigate().refresh();

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("j_username")));
        JenkinsUtils.login(getDriver());

        boolean isDarkTheme = !getDriver().findElements(By.cssSelector("html[data-theme='dark']")).isEmpty();

        Assert.assertTrue(isDarkTheme);
    }

    private void changeTheme() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));

        WebElement userIcon = getDriver().findElement(By.className("jenkins-avatar"));
        new Actions(getDriver()).moveToElement(userIcon).perform();

        WebElement appearanceButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href=\"/user/admin/appearance\"]")));
        appearanceButton.click();

        getDriver().findElement(By.xpath("//label[@for=\"radio-block-1\"]")).click();
        getDriver().findElement(By.name("Submit")).click();
    }
}
