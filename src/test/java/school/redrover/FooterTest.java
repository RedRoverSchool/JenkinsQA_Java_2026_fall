package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

public class FooterTest extends BaseTest {

    private static final String JOB_NAME = "my test pipeline";

    @Test
    public void testRestApiButtonRedirectsToDocumentationFromTheJobPage() {
        getDriver().findElement(By.cssSelector("a[href='/view/all/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(JOB_NAME);
        getDriver().findElement(By.className("org_jenkinsci_plugins_workflow_job_WorkflowJob")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();

        getWait10().until(ExpectedConditions.visibilityOfElementLocated((By.id("description-link"))));

        getDriver().findElement(By.cssSelector(".app-jenkins-logo")).click();
        getDriver().findElement(By.id("job_" + JOB_NAME)).click();
        getDriver().findElement(By.cssSelector("a[href*='api/']")).click();

        Assert.assertEquals(getDriver().findElement(By.tagName("h1")).getText(), "REST API");
    }
}
