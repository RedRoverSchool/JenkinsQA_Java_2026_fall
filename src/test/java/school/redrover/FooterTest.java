package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

import java.time.Duration;

public class FooterTest extends BaseTest {
    private static final By NEW_ITEM_ICON = By.xpath("//span[contains(text(),'New Item')]/preceding-sibling::span[1]");
    private static final By ITEM_NAME_FIELD = By.id("name");
    private static final By JOB_ITEM_PIPELINE = By.className("org_jenkinsci_plugins_workflow_job_WorkflowJob");
    private static final By SUBMIT_BUTTON = By.id("ok-button");
    private static final By SAVE_BUTTON = By.name("Submit");
    private static final By APP_LOGO = By.cssSelector(".app-jenkins-logo");
    private static final By API_BUTTON = By.cssSelector("a[href*='api/']");
    private static final By API_TITLE = By.tagName("h1");

    @Test
    public void testRestApiButtonRedirectsToDocumentationFromTheJobPage() {
        WebDriverWait wait10 = new WebDriverWait(getDriver(), Duration.ofSeconds(10));

        createPipeline();

        wait10.until(ExpectedConditions.not(ExpectedConditions.urlContains("/configure")));
        wait10.until(ExpectedConditions.elementToBeClickable(APP_LOGO)).click();
        getDriver().findElement(By.id("job_my test pipeline")).click();
        getDriver().findElement(API_BUTTON).click();

        Assert.assertEquals(getDriver().findElement(API_TITLE).getText(), "REST API");
    }

    public void createPipeline() {
        getDriver().findElement(NEW_ITEM_ICON).click();
        getDriver().findElement(ITEM_NAME_FIELD).sendKeys("my test pipeline");
        getDriver().findElement(JOB_ITEM_PIPELINE).click();
        getDriver().findElement(SUBMIT_BUTTON).click();
        getDriver().findElement(SAVE_BUTTON).click();
    }
}
