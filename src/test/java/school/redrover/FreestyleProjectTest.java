package school.redrover;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.annotations.Ignore;
import school.redrover.common.BaseTest;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.pages.FreestyleConfigurationPage;
import school.redrover.pages.HomePage;
import school.redrover.pages.StatusPage;

public class FreestyleProjectTest extends BaseTest {
    private static final String ITEM_NAME = "test";
    private static final String DESCRIPTION = "test description";

    @Test
    public void testCreate() {
        String projectName = new HomePage(getDriver())
                .clickNewItem()
                .inputProjectName(ITEM_NAME)
                .selectFreestyleAndClickOK()
                .gotoHomePage()
                .getFirstProjectName();

        Assert.assertEquals(projectName, ITEM_NAME);
    }

    @Test
    public void testApplyButton() {
        String notificationText = new HomePage(getDriver())
                .clickNewItem()
                .inputProjectName(ITEM_NAME)
                .selectFreestyleAndClickOK()
                .addDescription(DESCRIPTION)
                .clickApplyButton()
                .getNotificationText();

        Assert.assertEquals(notificationText, "Saved", "Changes were not applied");
    }

    @Test
    public void testIsTriggerSectionAvailable() {
        FreestyleConfigurationPage configPage = new HomePage(getDriver())
                .clickNewItem()
                .inputProjectName(ITEM_NAME)
                .selectFreestyleAndClickOK();

        Assert.assertTrue(
                configPage.isTriggerSectionDisplayed(), "Секция Triggers не отображается");
        Assert.assertTrue(
                configPage.areTriggerCheckboxesDisplayed("pseudoRemoteTrigger", "hudson-triggers-SCMTrigger"),
                "Один из чекбоксов не отображается");
    }

    @Test
    public void testBuildPeriodicallyPositive() {
        FreestyleConfigurationPage configPage = new HomePage(getDriver())
                .clickNewItem()
                .inputProjectName(ITEM_NAME)
                .selectFreestyleAndClickOK()
                .checkBuildPeriodically()
                .inputSchedule("H H * * *")
                .clickApplyButton();

        Assert.assertEquals(configPage.getNotificationText(), "Saved");
        Assert.assertTrue(configPage.getScheduleValidationMessage().contains("Would last have run at"));
    }

    @Test
    public void testBuildPeriodicallyWarning() {
        FreestyleConfigurationPage configPage = new HomePage(getDriver())
                .clickNewItem()
                .inputProjectName(ITEM_NAME)
                .selectFreestyleAndClickOK()
                .checkBuildPeriodically()
                .inputSchedule("0 0 * * *")
                .clickApplyButton();

        Assert.assertEquals(configPage.getNotificationText(), "Saved");
        Assert.assertTrue(configPage.getScheduleValidationMessage().contains("Would last have run at"));
        Assert.assertTrue(configPage.getScheduleWarningMessage().contains("Spread load evenly by using"));
    }

    @Test(dependsOnMethods = "testCreate")
    public void testAddPostBuildAction() {
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

    @Test(dependsOnMethods = "testCreate")
    public void testDisableProject() {

        final String disableText = "This project is currently disabled";
        StatusPage statusPage = new HomePage(getDriver())
                .goToProject(ITEM_NAME)
                .openFreestyleConfigurationPage()
                .clickEnableAndDisableButton()
                .clickSaveButton();
        String actualText = statusPage.getWarningText();

        Assert.assertTrue(actualText.contains(disableText));
    }

    @Test(dependsOnMethods = "testDisableProject")
    public void testEnableProject() {
        StatusPage statusPage = new HomePage(getDriver())
                .goToProject(ITEM_NAME)
                .openFreestyleConfigurationPage()
                .clickEnableAndDisableButton()
                .clickSaveButton();

        Assert.assertTrue(statusPage.isWarningGone());
    }

    @Test(dependsOnMethods = "testCreate")
    public void testNewItemCopyFrom() {
        String projectName = new HomePage(getDriver())
                .clickNewItem()
                .inputProjectName("Copied")
                .selectCopyFromAndInputNameAndClickOK(ITEM_NAME)
                .saveCopiedProject()
                .goToHomePage()
                .getCopiedProjectName();
        Assert.assertEquals(projectName, "Copied");
    }
}
