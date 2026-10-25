package school.redrover;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import java.time.Duration;

public class AdvancedPipelineConfigurationTest extends BaseTest {
    private static final String PIPELINE_NAME = "Pipeline 1";
    private static final String QP_POSITIVE = "10";
    private static final String QP_NEGATIVE = "-2";
    private static final String NOT_INTEGER = "5.5";
    private static final String NEGATIVE_ERROR = "This value should be larger than 0";
    private static final String NOT_INTEGER_ERROR = "Not an integer";

    @Test
    public void testQuietPeriodPositiveValue() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
        createPipeline();
        setQuietPeriod(QP_POSITIVE);
        getDriver().findElement(By.cssSelector("button[name='Submit']")).click();
        wait.until(ExpectedConditions.elementToBeClickable(
                        By.cssSelector("a[href$=configure]")))
                .click();
        openAdvancedDropdown();
        String getQuietPeriodSeconds = getDriver().findElement(
                By.cssSelector("input[name='quiet_period']"
                )).getAttribute("value");

        Assert.assertEquals(getQuietPeriodSeconds, QP_POSITIVE);
    }

    @Test
    public void testQuietPeriodNegativeValue() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
        createPipeline();
        setQuietPeriod(QP_NEGATIVE);
        String error = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath(
                        "//input[@name='quiet_period']/ancestor::div[contains(@class,'jenkins-form-item')]/descendant::div[@class='error']"
                ))).getText();

        Assert.assertEquals(error, NEGATIVE_ERROR);

    }

    @Test
    public void testQuietPeriodNotIntegerValue() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
        createPipeline();
        setQuietPeriod(NOT_INTEGER);
        String error = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath(
                        "//input[@name='quiet_period']/ancestor::div[contains(@class,'jenkins-form-item')]/descendant::div[@class='error']"
                ))).getText();

        Assert.assertEquals(error, NOT_INTEGER_ERROR);
    }

    private void createPipeline() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href='/view/all/newJob']"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("input[value='org.jenkinsci.plugins.workflow.job.WorkflowJob'] + span"
                ))).click();
        getDriver().findElement(By.id("name")).sendKeys(PIPELINE_NAME);
        getDriver().findElement(By.id("ok-button")).click();
    }

    private void openAdvancedDropdown() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
        wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//span[contains(text(),'Advanced')]/ancestor::button")))
                .click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".jenkins-bottom-app-bar__shadow--stuck")));
        getDriver().findElement(By.cssSelector(".jenkins-section button.advancedButton")).click();
    }

    private void setQuietPeriod(String seconds) {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
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
