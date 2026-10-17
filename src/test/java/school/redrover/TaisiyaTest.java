package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

public class TaisiyaTest extends BaseTest {

    @Test
    public void CheckVersion (){
getDriver().findElement(By.id("root-action-ManageJenkinsAction")).click();
        Assert.assertEquals(getDriver().findElement(By.tagName("h1")).getText(), "Manage Jenkins");
        getDriver().findElement(By.xpath("//a[@href='about']")).click();
        Assert.assertEquals(getDriver().findElement(By.className("app-about-version")).getText(), "Version 2.580.1");

    }

}
