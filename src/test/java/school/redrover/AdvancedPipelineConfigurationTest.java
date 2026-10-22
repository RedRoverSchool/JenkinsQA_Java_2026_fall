package school.redrover;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import java.time.Duration;

public class AdvancedPipelineConfigurationTest extends BaseTest {
    String pipeLineName = "Pipeline 1";
    String qpPositiveSeconds = "10";
    String qpNegativeSeconds = "-2";
    WebDriverWait wait;

    @BeforeMethod
    public void createPipeline() {
        wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
        getDriver().findElement(By.cssSelector("a[href='/view/all/newJob']")).click();
        getDriver().findElement(By.cssSelector("input[value='org.jenkinsci.plugins.workflow.job.WorkflowJob'] + span")).click();
        getDriver().findElement(By.id("name")).sendKeys(pipeLineName);
        getDriver().findElement(By.id("ok-button")).click();
    }

    @Test
    public void testQuietPeriodPositiveValue() throws InterruptedException {
        setQuietPeriod(qpPositiveSeconds);
        getDriver().findElement(By.cssSelector("button[name='Submit']")).click();
        wait.until(ExpectedConditions.elementToBeClickable(
                        By.cssSelector("a[href$=configure]")))
                .click();
        openAdvancedDropdown();
        WebElement getQuietPeriodSeconds = getDriver().findElement(By.cssSelector("input[name='quiet_period']"));

        Assert.assertEquals(getQuietPeriodSeconds.getAttribute("value"), qpPositiveSeconds);
    }

    @Test
    public void testQuietPeriodNegativeValue() throws InterruptedException {
        setQuietPeriod(qpNegativeSeconds);
        WebElement error = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath(
                        "//input[@name='quiet_period']/ancestor::div[contains(@class,'jenkins-form-item')]/descendant::div[@class='error']"
                )));

        Assert.assertEquals(error.getText(), "This value should be larger than 0");

    }

    private void openAdvancedDropdown() throws InterruptedException {
        wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//span[contains(text(),'Advanced')]/ancestor::button")))
                .click();

        Thread.sleep(2000);

        wait.until(ExpectedConditions.elementToBeClickable(
                        By.cssSelector(".jenkins-section button.advancedButton")))
                .click();
    }

    private void setQuietPeriod(String seconds) throws InterruptedException {
        openAdvancedDropdown();
        wait.until(ExpectedConditions.elementToBeClickable(
                        By.cssSelector("input[name='hasCustomQuietPeriod'] + label")))
                .click();
        WebElement quietPeriodSeconds = getDriver().findElement(By.cssSelector("input[name='quiet_period']"));
        quietPeriodSeconds.clear();
        quietPeriodSeconds.sendKeys(seconds);
        quietPeriodSeconds.sendKeys(Keys.TAB);
    }
}
