package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.pages.FolderConfigurationPage;
import school.redrover.pages.HomePage;

import javax.swing.*;

public class FolderTest extends BaseTest {

    private static final String FOLDER_NAME = "NewFolder";
    private static final String RENAMED_FOLDER_NAME = "RenamedFolder";

    @Test
    public void createFolder() {
        String folderName = new HomePage(getDriver())
                .clickNewItem()
                .inputProjectName(FOLDER_NAME)
                .selectFolderAndOpenConfiguration()
                .setDisplayName(FOLDER_NAME)
                .clickSaveButton()
                .gotoHomePage()
                .getFirstProjectName();

        Assert.assertEquals(folderName, FOLDER_NAME);
    }

    @Test
    public void testCreateEmptyField() {

        getDriver().findElement(By.cssSelector("a[href='/view/all/newJob']")).click();
        getDriver().findElement(By.cssSelector(".jenkins-choice-list__item__icon")).click();

        Assert.assertFalse(
                getDriver().findElement(By.cssSelector("#ok-button")).isEnabled());
    }

    @Test
    public void testModifyFolderDisplayNameAndDescription() {
        final String folderName = "ConfigTestFolder";
        final String newDisplayName = "UpdatedFolderName";
        final String descriptionText = "This is a new description for the folder.";

        getWait10().until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.task-link[href*='newJob']"))).click();
        getDriver().findElement(By.id("name")).sendKeys(folderName);
        getDriver().findElement(By.xpath("//label[input[@value='com.cloudbees.hudson.plugins.folder.Folder']]")).click();
        getWait10().until(ExpectedConditions.elementToBeClickable(By.id("ok-button"))).click();
        getWait10().until(ExpectedConditions.urlMatches(".*/job/" + folderName + "/configure/?$"));
        getWait10().until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.app-jenkins-logo"))).click();
        getWait10().until(ExpectedConditions.elementToBeClickable(By.linkText(folderName))).click();
        getWait10().until(ExpectedConditions.elementToBeClickable(By.linkText("Configure"))).click();
        getWait10().until(ExpectedConditions.elementToBeClickable(By.name("Submit")));

        WebElement displayNameField = getDriver().findElement(By.name("_.displayNameOrNull"));
        displayNameField.clear();
        displayNameField.sendKeys(newDisplayName);
        WebElement descriptionField = getDriver().findElement(By.xpath("//textarea[contains(@name, 'description')]"));
        descriptionField.clear();
        descriptionField.sendKeys(descriptionText);
        displayNameField.click();
        getWait10().until(ExpectedConditions.elementToBeClickable(By.name("Submit"))).click();
        getWait10().until(ExpectedConditions.textToBePresentInElementLocated(By.tagName("h1"), newDisplayName));
        WebElement actualDescriptionElement = getDriver().findElement(By.xpath("//*[contains(text(), '" + descriptionText + "')]"));

        Assert.assertTrue(actualDescriptionElement.isDisplayed(), "Описание не отобразилось на странице!");
    }

    @Test(dependsOnMethods = "createFolder")
    public void testApplyChanges() {
        FolderConfigurationPage configPage = new HomePage(getDriver())
                .openFolder(FOLDER_NAME)
                .openConfigure()
                .openHealthMetricsSection()
                .clickAddMetricButton()
                .selectChildItemWithWorstHealth()
                .clickRecursiveCheckbox()
                .clickApplyButton();

        Assert.assertTrue(configPage.isConfigurationPageOpened(),
                "User should remain on the folder configuration page");
        Assert.assertFalse(configPage.isRecursiveCheckboxSelected(),
                "Recursive checkbox should not be selected");
    }

    @Test
    public void testCreateNestedFolder() {
        final String childFolder = "childFolder";
        boolean isChildFolderDisplayed = new HomePage(getDriver())
                .clickNewItem()
                .inputFolderName(FOLDER_NAME)
                .selectFolderAndOpenConfiguration()
                .clickSaveButton()
                .clickNewItemFolder()
                .inputFolderName(childFolder)
                .selectFolderAndOpenConfiguration()
                .clickSaveButton()
                .goToParentFolder(FOLDER_NAME)
                .isItemDisplayed(childFolder);

        Assert.assertTrue(isChildFolderDisplayed, "childFolder should be listed inside " + FOLDER_NAME);
    }

    @Test
    public void testCreateFolder(){
        String folderName = new HomePage(getDriver())
                .clickNewItem()
                .inputFolderName("Test_Folder")
                .selectFolderAndOpenConfiguration()
                .gotoHomePage()
                .getFirstProjectName();

        Assert.assertEquals(folderName, "Test_Folder");
    }

    @Test
    public void testErrorMessageAfterClearingNameField() {
        String errorMessage = new HomePage(getDriver())
                .clickNewItem()
                .inputFolderName("abc")
                .clearItemName()
                .selectFolder()
                .getErrorMessageText();

        Assert.assertEquals(errorMessage, "This field cannot be empty, please enter a valid name");
    }

    @Test
    public void testCreateFolderWithoutOptionalFields() {
        String folderHeading = new HomePage(getDriver())
                .clickNewItem()
                .inputFolderName("PlainFolder")
                .selectFolderAndOpenConfiguration()
                .clickSaveButton()
                .getMainHeading();

        Assert.assertEquals(folderHeading, "PlainFolder");
    }

    @Test(dependsOnMethods = {"createFolder", "testApplyChanges"})
    public void testRenameFolder() {
        getDriver().findElement(By.id("root-action-SearchAction")).click();
        getDriver().findElement(By.id("command-bar")).sendKeys(FOLDER_NAME);
        getDriver().findElement(By.xpath("//a[contains(@href, '/job/" + FOLDER_NAME + "')]")).click();
        getWait5().until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(@href, '/confirm-rename')]"))).click();
        getDriver().findElement(By.cssSelector("div.setting-main > input")).clear();
        getDriver().findElement(By.cssSelector("div.setting-main > input")).sendKeys(RENAMED_FOLDER_NAME);
        getWait5().until(ExpectedConditions.elementToBeClickable(By.className("jenkins-submit-button"))).click();

        Assert.assertEquals(
                getDriver().findElement(By.className("job-index-headline")).getText(),
                RENAMED_FOLDER_NAME);
        getWait5().until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.app-jenkins-logo"))).click();
        Assert.assertEquals(
                getDriver().findElement(By.cssSelector(".jenkins-table__link> span:first-child")).getText(),
                RENAMED_FOLDER_NAME);
    }

    @Test(dependsOnMethods = "testCreateFolder")
    public void testOpenConfiguration() {
        FolderConfigurationPage folderName = new HomePage(getDriver())
                .openFolder("Test_Folder")
                .openConfigure();

        Assert.assertEquals(folderName.getHealth(), "Health metrics");
    }

}
