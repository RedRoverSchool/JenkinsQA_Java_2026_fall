package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

import java.time.Duration;

public class CreateMultibranchPipelineTest extends BaseTest {
    @Test

    public void testCreateMultibranchPipeline() {
        String pipelineName = "FirstPipeline";
        String pipelineDisplayName = "PipelineDisplayName";
        String description = "PipelineDescriptions";

        getDriver().findElement(By.cssSelector("a[href='/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(pipelineName);
        getDriver().findElement(By.xpath("//span[normalize-space()='Multibranch Pipeline']")).click();
        getDriver().findElement(By.id("ok-button")).click();

        getDriver().findElement(By.name("_.displayNameOrNull")).sendKeys(pipelineDisplayName);
        getDriver().findElement(By.name("_.description")).sendKeys(description);
        getDriver().findElement(By.name("Submit")).click();

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("/job/" + pipelineName + "/"));
        Assert.assertTrue(getDriver().getCurrentUrl().contains("/job/" + pipelineName + "/"));
        Assert.assertEquals(getDriver().findElement(By.xpath("//h1")).getText(), pipelineDisplayName);
        Assert.assertEquals(getDriver().findElement(By.id("view-message")).getText(), description);

    }

}
