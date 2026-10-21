package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

public class DsTest extends BaseTest {
    @Test
    public void testCreatePipline() throws InterruptedException {
        final String projectName = "New_pipeline";

        getDriver().findElement(By.className("content-block__link")).click();
        getDriver().findElement(By.id("name")).sendKeys(projectName);
        getDriver().findElement(By.className("icon-xlg")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("description")).sendKeys("Hello World");


        Assert.assertEquals(
                getDriver().findElement(By.name("description")).getAttribute("value"),
                "Hello World");
        Thread.sleep(2000);
    }
}
