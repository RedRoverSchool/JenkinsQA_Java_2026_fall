package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.pages.HomePage;
import school.redrover.pages.PipelineConfigurationPage;

import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class PipelineProjectTest extends BaseTest {

    private static final String PROJECT_NAME = "NewPipeline";
    private static final String NEW_PROJECT_NAME = "testNewName";
    private static final String DESCRIPTION = "New description";

    @Test
    public void testCreate() {
        String projectName = new HomePage(getDriver())
                .clickNewItem()
                .inputProjectName(PROJECT_NAME)
                .selectPipelineAndClickOk()
                .gotoHomePage()
                .getFirstProjectName();

        assertEquals(projectName, PROJECT_NAME);
    }

    @Test(dependsOnMethods = "testCreate")
    public void testCreateWithExistingName() {

        String errorMessageText = new HomePage(getDriver())
                .clickNewItem()
                .inputProjectName(PROJECT_NAME)
                .selectPipeline()
                .getErrorMessageText();

        assertTrue(errorMessageText.contains("A job already exists"), "Сообщение об ошибке не появилось!");
        Assert.assertFalse(getDriver().findElement(By.id("ok-button")).isEnabled(), "Кнопка ОК должна быть неактивна!");
    }

    @Test(dependsOnMethods = "testCreateWithExistingName")
    public void testRename() {

        String newProjectName = new HomePage(getDriver())
                .openJobsDropDownMenu()
                .clickRenameInDropDownMenu()
                .renameWithCorrectName(NEW_PROJECT_NAME)
                .getMainHeading();

        assertEquals(newProjectName, NEW_PROJECT_NAME);
    }

    @Test(dependsOnMethods = "testRename")
    public void testNotificationAfterAddDescriptionAndApply() {

        String notificationText = new HomePage(getDriver())
                .openJobsDropDownMenu()
                .clickConfigureInDropDownMenu()
                .addDescription(DESCRIPTION)
                .clickApplyButton()
                .getNotificationText();

        assertEquals(notificationText, "Saved", "Changes were not applied");
    }

    @Test(dependsOnMethods = "testNotificationAfterAddDescriptionAndApply")
    public void testSaveDescription() {

        String newTestDescription = new HomePage(getDriver())
                .openJobsDropDownMenu()
                .clickConfigureInDropDownMenu()
                .clickSaveButton()
                .getDescription();

        assertEquals(newTestDescription, DESCRIPTION, "The description does not match");
    }

    @Test(dependsOnMethods = "testSaveDescription")
    public void testDefinePipelineScriptFromSample() {

        List<String> scriptLines = new HomePage(getDriver())
                .openJobsDropDownMenu()
                .clickConfigureInDropDownMenu()
                .clickOnLeftSideSection(PipelineConfigurationPage.LeftSideSection.PIPELINE)
                .selectHelloWorldScriptFromSample()
                .getTextFromScript();

        assertTrue(scriptLines.contains("Hello World"));
    }

    @Test(dependsOnMethods = "testDefinePipelineScriptFromSample")
    public void testDelete() {

        String titleText = new HomePage(getDriver())
                .openJobsDropDownMenu()
                .deleteJobFromDropDownMenu()
                .getTitle();

        assertEquals(titleText, "Welcome to Jenkins!");
    }

    @DataProvider(name = "unsupportedCharacters")
    public Object[][] unsupportedCharacters() {
        List<String> unsupportedCharacters = Arrays.asList("!", "@", "#", "$", "%", "^", "&", "*", ";", ":", "|", "\\", "/", "?", "<", ">", "[");
        Object[][] data = new Object[unsupportedCharacters.size()][1];
        for (int i = 0; i < unsupportedCharacters.size(); i++) {
            data[i][0] = unsupportedCharacters.get(i);
        }
        return data;
    }

    @Ignore
    @Test(dataProvider = "unsupportedCharacters")
    public void testCreateWithUnsupportedCharacters(String invalidPipelineName) {
        String expectedErrorMsg = String.format("‘%s’ is an unsafe character", invalidPipelineName);

        getDriver().findElement(By.xpath("//a[.//span[text()='New Item']]")).click();
        getDriver().findElement(By.id("name")).sendKeys("test" + invalidPipelineName);
        getDriver().findElement(By.xpath("//span[@class='jenkins-choice-list__item__label' and text()='Pipeline']")).click();
        String errorElementText = getWait2().until(
                ExpectedConditions.visibilityOfElementLocated((By.xpath("//div[@class='error']")))).getText();

        assertEquals(errorElementText, expectedErrorMsg);
    }
}
