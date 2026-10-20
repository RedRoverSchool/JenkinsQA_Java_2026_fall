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


public class ChangeThemeTest extends BaseTest {

    @Test
    public void testChangeThemeFromHomePage(){
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
}
