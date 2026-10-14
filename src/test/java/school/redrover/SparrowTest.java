package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

public class SparrowTest extends BaseTest {

    @Test
    public void testCreateMultiProject(){

        final String projectName = "firstMultiProject";

        getDriver().findElement(By.className("content-block__link")).click();
        getDriver().findElement(By.id("name")).sendKeys(projectName );
        getDriver().findElement(By.className("jenkins-choice-list__item__icon")).click();
        getDriver().findElement(By.id("ok-button")).click();

        getDriver().findElement(By.id(("jenkins-head-icon"))).click();
        Assert.assertEquals(
                getDriver().findElement(By.cssSelector("#job_firstMultiProject > td:nth-child(3) > a > span")).getText(), projectName);

    }
}
