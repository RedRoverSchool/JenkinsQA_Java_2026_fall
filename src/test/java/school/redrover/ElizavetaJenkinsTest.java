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

        //getDriver().findElement(By.className("jenkins-avatar")).click();
        getDriver().get(ProjectUtils.getUrl() + "user/" + ProjectUtils.getUserName() + "/account/");

       WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
       wait.until(ExpectedConditions.elementToBeClickable(
                       By.cssSelector("a[href*='/account']"
       ))).click();

        getDriver().findElement(By.name("_.description")).clear();
        getDriver().findElement(By.name("_.description")).sendKeys("I'm a student");
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertEquals(
                getDriver().findElement(By.id("description-content")).getText(), "I'm a student"
        );
    }
}
