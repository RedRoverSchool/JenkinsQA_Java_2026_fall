package school.redrover;
import org.openqa.selenium.JavascriptExecutor;
import school.redrover.common.BaseTest;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class FreestyleProjectTest extends BaseTest {
    private static final String ITEM_NAME = "test";
    private static final String DESCRIPTION = "test description";

    public void createFreestyleProject(){
        getDriver().findElement(By.cssSelector("#tasks a[href$='/newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(ITEM_NAME);
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.id("ok-button")).click();
    }

    @Test
    public void testCreate() {
        createFreestyleProject();
        getDriver().findElement(By.name("Submit")).click();
        getWait2().until(ExpectedConditions.elementToBeClickable(
                By.xpath(String.format(
                        "//div[@id='breadcrumbBar'][.//span[text()='%s']]/preceding-sibling::a//img[@id='jenkins-head-icon']",
                        ITEM_NAME)))).click();

        Assert.assertEquals(
                getDriver().findElement(By.cssSelector("#job_" + ITEM_NAME + " a.model-link")).getText(), ITEM_NAME);
    }

    @Test
    public void testApplyButton() {
        createFreestyleProject();
        getDriver().findElement(By.name("description")).sendKeys(DESCRIPTION);
        getDriver().findElement(By.name("Apply")).click();

        Assert.assertEquals(getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.id("notification-bar"))).getText(), "Saved");
    }

    @Test
    public void testIsTriggerSectionAvailable() {
        createFreestyleProject();

        WebElement triggerSection = getDriver().findElement(
                By.xpath("//section[div[contains(@class, 'jenkins-section__title') and contains(text(), 'Triggers')]]"));

        Assert.assertTrue(triggerSection.isDisplayed());
        Assert.assertTrue(triggerSection.findElement(By.name("pseudoRemoteTrigger")).isDisplayed());
        Assert.assertTrue(triggerSection.findElement(By.name("hudson-triggers-SCMTrigger")).isDisplayed());
    }

    @Test
    public void testBuildPeriodicallyPositive() {
        createFreestyleProject();

        WebElement triggerSection = getDriver().findElement(
                By.xpath("//section[div[contains(@class, 'jenkins-section__title') and contains(text(), 'Triggers')]]"));

        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("arguments[0].click();", triggerSection.findElement(By.name("hudson-triggers-TimerTrigger")));
        js.executeScript("arguments[0].click();", triggerSection.findElement(By.cssSelector("textarea.jenkins-input.validated")));

        triggerSection.findElement(By.cssSelector("textarea.jenkins-input.validated")).sendKeys("H H * * *");
        getDriver().findElement(By.name("Apply")).click();

        WebElement infoMessage = getWait5().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@class='ok' and contains(text(), 'Would last have run at')]")));

        Assert.assertEquals(getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.id("notification-bar"))).getText(), "Saved");
        Assert.assertTrue(infoMessage.getText().contains("Would last have run at"));
    }

    @Test
    public void testBuildPeriodicallyWarning(){
        createFreestyleProject();

        WebElement triggerSection = getDriver().findElement(
                By.xpath("//section[div[contains(@class, 'jenkins-section__title') and contains(text(), 'Triggers')]]"));
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("arguments[0].click();", triggerSection.findElement(By.name("hudson-triggers-TimerTrigger")));
        js.executeScript("arguments[0].click();", triggerSection.findElement(By.cssSelector("textarea.jenkins-input.validated")));

        triggerSection.findElement(By.cssSelector("textarea.jenkins-input.validated")).sendKeys("0 0 * * *");
        getDriver().findElement(By.name("Apply")).click();

        Assert.assertEquals(getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.id("notification-bar"))).getText(), "Saved");
        Assert.assertTrue(getDriver().findElement(By.className("warning")).getText().contains("Spread load evenly by using"));
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
