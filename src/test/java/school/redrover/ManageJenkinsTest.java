package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

import java.time.Duration;

public class ManageJenkinsTest extends BaseTest  {

    private static final String HEADER = "Manage Jenkins";
    private static final String HEADER_ABOUT = "Jenkins";
    private static final String VERSION = "Version 2.580.1";


    @Test
    public void testAboutJenkinsVersion() {

        WebDriverWait wait10 = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        getDriver().findElement(By.id("root-action-ManageJenkinsAction")).click();
        wait10.until(ExpectedConditions.textToBePresentInElement(getDriver().findElement(By.xpath("//h1")), HEADER));
        getDriver().findElement(By.xpath("//a[@href = 'about']")).click();
        wait10.until(ExpectedConditions.textToBePresentInElement(getDriver().findElement(By.xpath("//h1")),HEADER_ABOUT ));

        Assert.assertEquals(getDriver().findElement(By.cssSelector(".app-about-version")).getText(), VERSION);
    }
}