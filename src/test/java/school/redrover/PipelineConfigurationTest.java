package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import java.time.Duration;

public class PipelineConfigurationTest extends BaseTest {

    private static final By NEW_ITEM_ICON = By.xpath("//span[contains(text(),'New Item')]/preceding-sibling::span[1]");
    private static final By ITEM_NAME_FIELD = By.id("name");
    private static final By JOB_ITEM_PIPELINE = By.className("org_jenkinsci_plugins_workflow_job_WorkflowJob");
    private static final By SUBMIT_BUTTON = By.id("ok-button");
    private static final By DISABLE_PROJECT_BUTTON = By.cssSelector("label[data-title=\"Disabled\"]");
    private static final By SAVE_BUTTON = By.name("Submit");

    @Test
    public void testDisableProject(){
        createPipeline();
        disableProject();

        Assert.assertTrue(
                getDriver().findElement(By.id("enable-project")).isDisplayed(),
                "Enable button is not displayed"
        );

        WebElement message = getDriver().findElement(By.cssSelector("form[method=\"post\"]"));
        Assert.assertTrue(message.getText().contains("This project is currently disabled"),
                "Message is absent");
    }

    @Test
    public void testDisabledStateOnDashboard(){
        createPipeline();
        disableProject();
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("tasks")));
        getDriver().findElement(By.className("app-jenkins-logo")).click();
        Assert.assertTrue(
                getDriver().findElement(By.className("disabledJob")).isDisplayed(),
                "Disabled icon is not displayed"
        );
    }

    public void createPipeline() {
        getDriver().findElement(NEW_ITEM_ICON).click();
        getDriver().findElement(ITEM_NAME_FIELD).sendKeys("my test pipeline");
        getDriver().findElement(JOB_ITEM_PIPELINE).click();
        getDriver().findElement(SUBMIT_BUTTON).click();
    }

    public void disableProject(){
        getDriver().findElement(DISABLE_PROJECT_BUTTON).click();
        getDriver().findElement(SAVE_BUTTON).click();
    }

}
