package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

public class SystemSettingsTest extends BaseTest {
    private static final By SAVE_BUTTON = By.name("Submit");

    @Test
    public void testEditSystemMessage(){
        openSystemSettings();
        getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.name("system_message")));
        String systemMessage = "This is TEST System Message";
        getDriver().findElement(By.cssSelector("textarea[name='system_message']")).sendKeys(systemMessage);
        getDriver().findElement(SAVE_BUTTON).click();

        String actualMessage = getDriver().findElement(By.id("systemmessage")).getText();
        Assert.assertEquals(actualMessage,systemMessage);
    }

    @Test
    public void testHelpHintOfHomeDirectory(){
        openSystemSettings();
        getWait10().until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[helpurl*='/homeDirectory']"))).click();

        String helpHintHomeDirectory = """
                There are a few ways to change the Jenkins home directory:
                Set the JENKINS_HOME environment variable when launching Jenkins.
                Set the JENKINS_HOME Java system property when launching Jenkins.
                This value cannot be changed while Jenkins is running.
                It is shown here to help you ensure that your configuration is taking effect.""";
        String actualHint = getDriver().findElement(By.cssSelector(".help-area")).getText();

        Assert.assertEquals(actualHint, helpHintHomeDirectory);
    }

    @Test
    public void testComputerRetentionCheckIntervalPositive(){
        openSystemSettings();
        getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.name("_.computerRetentionCheckInterval")));

        fillIntervalInput("1");
        getDriver().findElement(By.name("Apply")).click();
        getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[id='notification-bar'][class*='jenkins-notification--visible']")));
        getDriver().navigate().refresh();

        String newInterval = getDriver().findElement(By.name("_.computerRetentionCheckInterval")).getAttribute("value");

        Assert.assertEquals(newInterval, "1");
    }

    @Test
    public void testComputerRetentionCheckIntervalNegative(){
        openSystemSettings();
        getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.name("_.computerRetentionCheckInterval")));

        fillIntervalInput("61");
        getDriver().findElement(By.name("Apply")).click();

        String error = getDriver().findElement(By.className("error")).getText();

        Assert.assertEquals(error, "This value should be between 1 and 60");
    }

    public void openSystemSettings(){
        getDriver().findElement(By.id("root-action-ManageJenkinsAction")).click();
        getDriver().findElement(By.cssSelector("a[href='configure']")).click();
    }

    public void fillIntervalInput(String intervalValue){
        WebElement intervalInput = getDriver().findElement(By.name("_.computerRetentionCheckInterval"));
        intervalInput.clear();
        intervalInput.sendKeys(intervalValue);
    }
}
