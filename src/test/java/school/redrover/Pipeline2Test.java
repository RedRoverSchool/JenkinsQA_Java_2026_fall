package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

import java.util.List;

public class Pipeline2Test extends BaseTest {

    private static final String PIPELINE_NAME = "Pipeline_test";
    private static final List<String> TRIGGER_NAMES = List.of(
            "Build after other projects are built",
            "Build periodically",
            "GitHub hook trigger for GITScm polling",
            "Poll SCM",
            "Trigger builds remotely"
    );
    private static final By TRIGGER_SECTION = By.xpath("//section[div[contains(@class, 'jenkins-section__title') " +
            "and normalize-space()='Triggers']]");
    private static final By PIPELINE_ITEM = By.cssSelector("#items div.org_jenkinsci_plugins_workflow_job_WorkflowJob");
    private static final By TRIGGER_CHECKBOXES = By.xpath(".//div[contains(@class, 'help-sibling')]//input[@type='checkbox']");
    private static final By TRIGGER_ICON_HELPS = By.xpath(".//a[contains(@class, 'jenkins-help-button')]");

    @Test
    public void testCreateTriggers() {
        getDriver().findElement(By.cssSelector("a[href$='/view/all/newJob']")).click();
        getDriver().findElement(By.name("name")).sendKeys(PIPELINE_NAME);
        getDriver().findElement(PIPELINE_ITEM).click();
        getDriver().findElement(By.id("ok-button")).click();
        WebElement triggerSection = getWait5().until(ExpectedConditions.visibilityOfElementLocated(TRIGGER_SECTION));
        for (String triggerName : TRIGGER_NAMES) {
            WebElement block = getDriver().findElement(getTriggerBlock(triggerName));

            Assert.assertTrue(triggerSection.isDisplayed(), "Секция TRIGGERS не обнаружена");
            Assert.assertFalse(block.findElements(By.xpath(".//input[@type='checkbox']")).isEmpty(), "Нет чекбокса у триггера при создании Pipeline: " + triggerName);
            Assert.assertFalse(block.findElements(By.xpath(".//a[contains(@class, 'jenkins-help-button')]")).isEmpty(), "Нет иконки '?' у триггера при создании Pipeline: " + triggerName);
        }
    }

    @Test
    public void testEditTriggers() {
        createPipeline(PIPELINE_NAME);
        getDriver().findElement(By.cssSelector("a[href$='/configure']")).click();
        WebElement triggerSection = getWait5().until(ExpectedConditions.visibilityOfElementLocated(TRIGGER_SECTION));
        for (String triggerName : TRIGGER_NAMES) {
            WebElement block = getDriver().findElement(getTriggerBlock(triggerName));

            Assert.assertTrue(triggerSection.isDisplayed(), "Секция TRIGGERS не обнаружена");
            Assert.assertFalse(block.findElements(By.xpath(".//input[@type='checkbox']")).isEmpty(), "Нет чекбокса у триггера на странице Configure: " + triggerName);
            Assert.assertFalse(block.findElements(By.xpath(".//a[contains(@class, 'jenkins-help-button')]")).isEmpty(), "Нет иконки '?' у триггера при создании Configure: " + triggerName );
        }
    }

    private void createPipeline(String name) {
        getDriver().findElement(By.cssSelector("a[href$='/view/all/newJob']")).click();
        getDriver().findElement(By.name("name")).sendKeys(name);
        getDriver().findElement(PIPELINE_ITEM).click();
        getDriver().findElement(By.id("ok-button")).click();

        for (int i = 0; i < 3; i++) {
            try {
                getWait5().until(ExpectedConditions.elementToBeClickable(By.cssSelector("#bottom-sticker button.jenkins-button--primary"))).click();
                getWait5().until(ExpectedConditions.not(ExpectedConditions.urlContains("/configure")));
                break;
            } catch (StaleElementReferenceException | TimeoutException e) {
            }
        }
    }

    private By getTriggerBlock(String triggerName) {
        return By.xpath("//label[contains(normalize-space(.), '" + triggerName + "')]"
                + "/ancestor::div[contains(@class, 'help-sibling') "
                + "or contains(@class, 'jenkins-checkbox-help-wrapper')][1]");
    }
}