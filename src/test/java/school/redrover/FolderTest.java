package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.pages.FolderConfigurationPage;
import school.redrover.pages.FolderPage;
import school.redrover.pages.HomePage;

import javax.swing.*;
import java.time.Duration;

import static org.testng.Assert.assertEquals;

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
                .goToProject(FOLDER_NAME)
                .openFolderConfigurationPage()
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
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));

        createFolder();

        wait.until(ExpectedConditions.elementToBeClickable(By.linkText(FOLDER_NAME))).click();
        wait.until(ExpectedConditions.urlMatches(".*/job/" + FOLDER_NAME + "/?$"));
        getDriver().findElement(By.cssSelector("a[href$='/job/" + FOLDER_NAME + "/newJob']")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("name"))).sendKeys(childFolder);
        getDriver().findElement(By.className("com_cloudbees_hudson_plugins_folder_Folder")).click();
        getDriver().findElement(By.id("ok-button")).click();

        wait.until(ExpectedConditions.urlMatches(".*/job/" + FOLDER_NAME + "/job/" + childFolder + "/configure"));
        wait.until(ExpectedConditions.elementToBeClickable(By.name("Submit"))).click();
        wait.until(ExpectedConditions.urlMatches(".*/job/" + FOLDER_NAME + "/job/" + childFolder + "/?$"));
        getDriver().findElement(By.cssSelector("a[href$='/job/" + FOLDER_NAME + "/']")).click();

        Assert.assertTrue(wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(childFolder))).isDisplayed(), "childFolder should be listed inside " + FOLDER_NAME);
    }

    @Test
    public void testCreateFolder(){
        String folderName = new HomePage(getDriver())
                .clickNewItem()
                .inputFolderName("Test_Folder")
                .selectFolderAndClickOk()
                .gotoHomePage()
                .getFirstProjectName();

        Assert.assertEquals(folderName, "Test_Folder");
    }

    @Test
    public void testErrorMessageAfterClearingNameField() {
        getDriver().findElement(By.cssSelector("a.task-link")).click();
        getWait10().until(ExpectedConditions.elementToBeClickable(By.id("name")));
        getDriver().findElement(By.id("name")).sendKeys("abc");
        getDriver().findElement(By.id("name")).sendKeys(Keys.CONTROL + "a", Keys.DELETE);
        getDriver().findElement(By.className("com_cloudbees_hudson_plugins_folder_Folder")).click();

        WebElement error = getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.className("error")));
        Assert.assertEquals(error.getText(), "This field cannot be empty, please enter a valid name");
    }

    @Test
    public void testCreateFolderWithoutOptionalFields() {
        getDriver().findElement(By.cssSelector("a.task-link")).click();
        getWait10().until(ExpectedConditions.elementToBeClickable(By.id("name")));
        getDriver().findElement(By.id("name")).sendKeys("PlainFolder");
        getDriver().findElement(By.className("com_cloudbees_hudson_plugins_folder_Folder")).click();
        getDriver().findElement(By.id("ok-button")).click();

        getWait10().until(ExpectedConditions.textToBePresentInElementLocated(By.tagName("h1"), "Configuration"));
        getDriver().findElement(By.name("Submit")).click();

        getWait10().until(ExpectedConditions.textToBePresentInElementLocated(By.tagName("h1"), "PlainFolder"));
        Assert.assertEquals(getDriver().findElement(By.tagName("h1")).getText(), "PlainFolder");
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
        FolderPage folderName = new HomePage(getDriver())
                .openFolder("Test_Folder")
                .openConfigure();

        Assert.assertEquals(folderName.getHealth(), "Health metrics");
    }

}
