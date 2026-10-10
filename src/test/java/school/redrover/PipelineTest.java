package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

import java.time.Duration;

public class PipelineTest extends BaseTest {

    @Test
    public void testCreatePipeline() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(3));
        final String projectName = "test";

        getDriver().findElement(By.xpath("//span[contains(text(),'New Item')]/preceding-sibling::span[1]")).click();
        getDriver().findElement(By.id("name")).sendKeys(projectName);
        getDriver().findElement(By.className("org_jenkinsci_plugins_workflow_job_WorkflowJob")).click();
        getDriver().findElement((By.id("ok-button"))).click();

        wait.until(ExpectedConditions.elementToBeClickable((By.id("jenkins-head-icon")))).click();

        Assert.assertEquals(getDriver().findElement(By.cssSelector(".jenkins-table__link > span:first-child")).getText(),
                projectName);
    }
}
