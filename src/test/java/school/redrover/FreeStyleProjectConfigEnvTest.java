package school.redrover;

import school.redrover.common.BaseTest;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

public class FreeStyleProjectConfigEnvTest extends BaseTest {

    @Test
    public void testEnvironmentAvailable() {
        getDriver().findElement(By.className("content-block__link")).click();
        getDriver().findElement(By.id("name")).sendKeys("test");
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.id("ok-button")).click();

        Assert.assertEquals(
                getDriver().findElement(By.xpath("(//*[@class='task-link-text'])[4]")).getText(),
                "Environment");
    }
}
