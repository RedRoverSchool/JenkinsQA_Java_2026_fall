package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

public class ElizavetaJenkinsTest extends BaseTest {
    @Test
    public void testElizavetaJenkins () {
        getDriver().findElement(By.className("jenkins-avatar")).click();
        getDriver().findElement(By.cssSelector("a[href*='/account']")).click();
        getDriver().findElement(By.name("_.description")).clear();
        getDriver().findElement(By.name("_.description")).sendKeys("I'm a student");
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertEquals(
                getDriver().findElement(By.id("description-content")).getText(), "I'm a student"
        );
    }
}
