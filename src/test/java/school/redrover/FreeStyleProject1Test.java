package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

import java.time.Duration;

public class FreeStyleProject1Test extends BaseTest {

    @Test
    public void testCreate() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));

        getDriver().findElement(By.className("content-block__link")).click();
        getDriver().findElement(By.id("name")).sendKeys("test");
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.id("ok-button")).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.id("jenkins-head-icon"))).click();

        Assert.assertEquals(
                getDriver().findElement(By.cssSelector(".jenkins-table__link > span:first-child")).getText(),
                "test");
    }
}
