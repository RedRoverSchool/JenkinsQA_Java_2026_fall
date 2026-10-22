package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
//import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

import java.time.Duration;

//@Ignore

public class SparrowTest extends BaseTest {

    @Test
    public void testCreateMultiProject() {
        final String projectName = "firstMultiProject";

        getDriver().findElement(By.className("content-block__link")).click();
        getDriver().findElement(By.id("name")).sendKeys(projectName);
        getDriver().findElement(By.className("jenkins-choice-list__item__icon")).click();
        getDriver().findElement(By.id("ok-button")).click();

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(By.id("jenkins-head-icon"))).click();

        Assert.assertEquals(
                getDriver().findElement(By.cssSelector("#job_firstMultiProject > td:nth-child(3) > a > span")).getText(), projectName);
    }
}
