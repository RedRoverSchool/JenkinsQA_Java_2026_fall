package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

public class AnnaKTest extends BaseTest {
@Ignore
    @Test

    public void testCreateNewJob(){
        String prjName = "FirstPipeline";

        getDriver().findElement(By.className("task-icon-link")).click();
        getDriver().findElement(By.id("name")).sendKeys(prjName);
        getDriver().findElement(By.className("jenkins-choice-list__item__label")).click();
        getDriver().findElement(By.id("ok-button")).click();

        getDriver().findElement(By.id("jenkins-head-icon")).click();

        Assert.assertEquals( getDriver().findElement(By.cssSelector(".jenkins-table__link > span:first-child")).getText(), prjName);
    }
}
