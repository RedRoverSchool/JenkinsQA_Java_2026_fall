package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

import java.time.Duration;

public class PipelineConfigurationTest extends BaseTest {
    //locators on Dashboard page
    private static final By NEW_ITEM_ICON = By.xpath("//span[contains(text(),'New Item')]/preceding-sibling::span[1]");
    private static final By ITEM_NAME_FIELD = By.id("name");
    private static final By JOB_ITEM_PIPELINE = By.className("org_jenkinsci_plugins_workflow_job_WorkflowJob");
    private static final By SUBMIT_BUTTON = By.id("ok-button");
    //locators on Configure project page
    private static final By DISABLE_PROJECT_BUTTON = By.cssSelector("label[data-title=\"Disabled\"]");
    private static final By SAVE_BUTTON = By.name("Submit");

    @BeforeMethod
    public void createPipeline() {
        getDriver().findElement(NEW_ITEM_ICON).click();
        getDriver().findElement(ITEM_NAME_FIELD).sendKeys("my test pipeline");
        getDriver().findElement(JOB_ITEM_PIPELINE).click();
        getDriver().findElement(SUBMIT_BUTTON).click();
    }

    @Test
    public void testDisableProject(){
        disableProject();
        Assert.assertTrue(
                getDriver().findElement(By.id("enable-project")).isDisplayed(),
                "Enable button is not displayed"
        );
    }

    @Test
    public void testDisabledStateOnDashboard(){
        disableProject();
        WebDriverWait wait10 = new WebDriverWait(getDriver(),Duration.ofSeconds(10));
        getDriver().findElement(By.className("app-jenkins-logo")).click();
        wait10.until(ExpectedConditions.visibilityOf (getDriver().findElement(By.className("disabledJob"))));
        Assert.assertTrue(
                getDriver().findElement(By.className("disabledJob")).isDisplayed(),
                "Disabled icon is not displayed"
        );
    }

    public void disableProject(){
        getDriver().findElement(DISABLE_PROJECT_BUTTON).click();
        getDriver().findElement(SAVE_BUTTON).click();
    }

}
