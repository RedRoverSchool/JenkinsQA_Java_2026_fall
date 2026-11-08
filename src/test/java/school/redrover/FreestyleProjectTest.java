package school.redrover;
import org.openqa.selenium.JavascriptExecutor;
import school.redrover.common.BaseTest;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class FreestyleProjectTest extends BaseTest {
    final String ITEM_NAME = "test";
    final String DESCRIPTION = "test description";

    @Test
    public void testCreate() {
        getDriver().findElement(By.cssSelector("#tasks a[href$='/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(ITEM_NAME);
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("description")).sendKeys(DESCRIPTION);
        getDriver().findElement(By.name("Submit")).click();
        getWait2().until(ExpectedConditions.elementToBeClickable(By.id("jenkins-head-icon"))).click();

        Assert.assertEquals(
                getDriver().findElement(By.cssSelector("#job_" + ITEM_NAME + " a.model-link")).getText(), ITEM_NAME);
    }

    @Test
    public void testApplyButton() {
        getDriver().findElement(By.cssSelector("#tasks a[href$='/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(ITEM_NAME);
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("description")).sendKeys(DESCRIPTION);
        getDriver().findElement(By.name("Apply")).click();

        Assert.assertEquals(getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.id("notification-bar"))).getText(), "Saved");
    }

    @Test
    public void testIsTriggerSectionAvailable() {
        getDriver().findElement(By.cssSelector("#tasks a[href$='/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(ITEM_NAME);
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.id("ok-button")).click();

        WebElement triggerSection = getDriver().findElement(
                By.xpath("//section[div[contains(@class, 'jenkins-section__title') and contains(text(), 'Triggers')]]"));

        Assert.assertTrue(triggerSection.isDisplayed());
        Assert.assertTrue(triggerSection.findElement(By.name("pseudoRemoteTrigger")).isDisplayed());
        Assert.assertTrue(triggerSection.findElement(By.name("com-cloudbees-jenkins-GitHubPushTrigger")).isDisplayed());
        Assert.assertTrue(triggerSection.findElement(By.name("hudson-triggers-TimerTrigger")).isDisplayed());
        Assert.assertTrue(triggerSection.findElement(By.name("jenkins-triggers-ReverseBuildTrigger")).isDisplayed());
        Assert.assertTrue(triggerSection.findElement(By.name("hudson-triggers-SCMTrigger")).isDisplayed());
    }

    @Test
    public void testBuildPeriodicallyPositive(){
        getDriver().findElement(By.cssSelector("#tasks a[href$='/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(ITEM_NAME);
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.id("ok-button")).click();

        ((JavascriptExecutor) getDriver()).executeScript(
                "arguments[0].click();", getWait2().until(ExpectedConditions.presenceOfElementLocated(By.name("hudson-triggers-TimerTrigger"))));
        getWait2().until(ExpectedConditions.elementToBeClickable(By.name("_.spec"))).sendKeys("H H * * *");
        getDriver().findElement(By.name("Apply")).click();

        Assert.assertEquals(getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.id("notification-bar"))).getText(), "Saved");
        Assert.assertTrue(getWait10().until(ExpectedConditions.textToBePresentInElementLocated(By.cssSelector("div.ok"), "Would last have run")));
    }

    @Test
    public void testAddPostBuildAction() {
        testCreate();

        getWait5().until(ExpectedConditions.elementToBeClickable(By.cssSelector("#job_" + ITEM_NAME + " a"))).click();
        getWait5().until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href$='configure']"))).click();

        By addButton = By.xpath("//button[contains(., 'Add post-build action')]");
        WebElement button = getWait5().until(
                ExpectedConditions.visibilityOfElementLocated(addButton)
        );

        ((JavascriptExecutor) getDriver()).executeScript(
                "arguments[0].scrollIntoView({block: 'center'});", button
        );
        getWait5().until(ExpectedConditions.elementToBeClickable(addButton)).click();

        Assert.assertTrue(
                getWait5().until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//*[normalize-space()='Aggregate downstream test results']")
                )).isDisplayed()
        );
    }
}
