package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

public class SimaJenkinsFirstTest extends BaseTest {
    @Test

    public void testCreatProject(){

        final String projectName="project";

        getDriver().findElement(By.className("task-icon-link")).click();
        getDriver().findElement(By.id("name")).sendKeys(projectName);
        getDriver().findElement(By.className("jenkins-choice-list__item__label")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.id("jenkins-head-icon")).click();
        Assert.assertEquals(
                getDriver().findElement(By.cssSelector(".jenkins-table__link>span:first-child")).getText(),
                "project");





    }

}
