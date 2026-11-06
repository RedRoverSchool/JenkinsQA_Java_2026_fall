package school.redrover;

import org.openqa.selenium.By;
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

    public void openSystemSettings(){
        getDriver().findElement(By.id("root-action-ManageJenkinsAction")).click();
        getDriver().findElement(By.cssSelector("a[href='configure']")).click();
    }
}
