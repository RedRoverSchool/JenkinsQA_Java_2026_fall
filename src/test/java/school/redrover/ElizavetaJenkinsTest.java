package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
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
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));

        getDriver().get(ProjectUtils.getUrl() + "user/" + ProjectUtils.getUserName() + "/account/");
        wait.until(ExpectedConditions.not(ExpectedConditions.urlContains("login")));
        WebElement description = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.name("_.description"))
        );
        description.clear();
        description.sendKeys("I'm a student");
        getDriver().findElement(By.name("Submit")).click();

        WebElement content = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("description-content"))
        );
        Assert.assertEquals(
                getDriver().findElement(By.id("description-content")).getText(), "I'm a student"
        );
    }
}
