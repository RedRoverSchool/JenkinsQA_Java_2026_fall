package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.common.ProjectUtils;

import java.time.Duration;

public class ElizavetaJenkinsTest extends BaseTest {
    @Test
    public void testElizavetaJenkins () {
        getDriver().get(ProjectUtils.getUrl() + "user/" + ProjectUtils.getUserName() + "/account/");
        getDriver().findElement(By.name("_.description")).clear();
        getDriver().findElement(By.name("_.description")).sendKeys("I'm a student");
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertEquals(
                getDriver().findElement(By.id("description-content")).getText(), "I'm a student"
        );
    }
}
