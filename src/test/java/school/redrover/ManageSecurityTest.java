package school.redrover;

import org.openqa.selenium.By;
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
}
