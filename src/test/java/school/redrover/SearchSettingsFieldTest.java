package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import java.time.Duration;

public class SearchSettingsFieldTest extends BaseTest {

    private static boolean isElementClickable(WebDriver driver, By locator, int waitTimeoutInSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(waitTimeoutInSeconds));
            wait.until(ExpectedConditions.elementToBeClickable(locator));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    @Test
    public void testVisibilityAndClickability() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
        wait.until(ExpectedConditions.elementToBeClickable(By.id("root-action-ManageJenkinsAction")));
        getDriver().findElement(By.id("root-action-ManageJenkinsAction")).click();

        Assert.assertTrue(getDriver().getCurrentUrl().contains("manage/"));
        Assert.assertTrue(isElementClickable(getDriver(), By.id("settings-search-bar"), 5));
    }
}
