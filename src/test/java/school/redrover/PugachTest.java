package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

public class PugachTest extends BaseTest {

    @Test
    public void testDisplayedVersion() {

        final String version = "Jenkins 2.580.1";

        Assert.assertEquals(getDriver().findElement(By.className("jenkins_ver")).getText(),
            version);
    }

    @Test
    public void testAddDashboardDescription() {

        final String dashboardDescriptionText = "Dashboard description text";

        getDriver().findElement(By.id("description-link")).click();
        getDriver().findElement(By.id("description-textarea")).sendKeys(dashboardDescriptionText);
        getDriver().findElement(By.cssSelector("button[data-id='ok']")).click();
        getDriver().findElement(By.id("jenkins-head-icon")).click();

        Assert.assertEquals(getDriver().findElement(By.id("description-content")).getText(),
            dashboardDescriptionText);
    }
}
