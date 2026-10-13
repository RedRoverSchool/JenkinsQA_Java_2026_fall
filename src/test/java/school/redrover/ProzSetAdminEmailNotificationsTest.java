package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

import java.time.Duration;

public class ProzSetAdminEmailNotificationsTest extends BaseTest {

    @Test
    public void testSettingAdminEmail() {
        String mail = "admin@example.com";
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));

        getDriver().findElement(By.id("root-action-ManageJenkinsAction")).click();
        getDriver().findElement(By.cssSelector("a[href='configure']")).click();
        WebElement adminEmail = getDriver().findElement(By.cssSelector("input[checkurl$='checkAdminAddress']"));
        adminEmail.clear();
        adminEmail.sendKeys(mail);
        getDriver().findElement(By.cssSelector("button[name='Apply']")).click();

        // Locally works 5/5 but fails 2/2 in CI/Run tests. For this reason "WebDriverWait" is used.
        //Assert.assertEquals(getDriver().findElement(By.className("jenkins-notification--visible")).getText(), "Saved");

        WebElement successPopup = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("jenkins-notification--visible")));
        Assert.assertEquals(successPopup.getText().trim(), "Saved");

        getDriver().navigate().refresh();

        Assert.assertEquals(getDriver().findElement(By.cssSelector("input[checkurl$='checkAdminAddress']")).getAttribute("value"), mail);

    }
}
