package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

import java.time.Duration;

public class FolderTest extends BaseTest {

    private static String FOLDER_NAME = "NewFolder";

    @Test
    public void createFolder() {

        getDriver().findElement(By.cssSelector("a.task-link")).click();
        getDriver().findElement(By.id("name")).sendKeys(FOLDER_NAME);
        getDriver().findElement(By.className("com_cloudbees_hudson_plugins_folder_Folder")).click();
        getWait5().until(ExpectedConditions.elementToBeClickable(By.id("ok-button"))).click();

        getWait5().until(ExpectedConditions.urlMatches(".*/job/" + FOLDER_NAME + "/configure/?$"));
        getDriver().findElement(By.name("_.displayNameOrNull")).sendKeys(FOLDER_NAME);
        getDriver().findElement(By.name("Submit")).click();

        getWait5().until(ExpectedConditions.urlMatches(".*/job/" + FOLDER_NAME + "/?$"));
        getWait5().until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.app-jenkins-logo"))).click();

        Assert.assertTrue(
                getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.linkText(FOLDER_NAME))).isDisplayed()
        );
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
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        final String folderName = "ConfigTestFolder";
        final String newDisplayName = "UpdatedFolderName";
        final String descriptionText = "This is a new description for the folder.";

        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.task-link[href*='newJob']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("name"))).sendKeys(folderName);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//label[input[@value='com.cloudbees.hudson.plugins.folder.Folder']]"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("ok-button"))).click();
        wait.until(ExpectedConditions.urlMatches(".*/job/" + folderName + "/configure/?$"));
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.app-jenkins-logo"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.linkText(folderName))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Configure"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.name("Submit")));

        WebElement displayNameField = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.name("_.displayNameOrNull")));
        displayNameField.clear();
        displayNameField.sendKeys(newDisplayName);
        WebElement descriptionField = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//textarea[contains(@name, 'description')]")));
        descriptionField.clear();
        descriptionField.sendKeys(descriptionText);
        displayNameField.click();
        wait.until(ExpectedConditions.elementToBeClickable(By.name("Submit"))).click();
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.tagName("h1"), newDisplayName));
        WebElement actualDescriptionElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(text(), '" + descriptionText + "')]")));

        Assert.assertTrue(actualDescriptionElement.isDisplayed(), "Описание не отобразилось на странице!");
    }

    @Test
    public void testApplyChanges() {
        createFolder();

        getDriver().findElement(By.cssSelector("a[href$='" + FOLDER_NAME + "/']")).click();
        getWait5().until(ExpectedConditions.urlMatches(".*/job/" + FOLDER_NAME + "/?$"));
        getDriver().findElement(By.cssSelector("a[href$='configure']")).click();
        getWait5().until(ExpectedConditions.urlMatches(".*/job/" + FOLDER_NAME + "/configure/?$"));

        getDriver().findElement(By.cssSelector("button.advanced-button")).click();
        getDriver().findElement(By.cssSelector("button.jenkins-button.hetero-list-add")).click();

        getWait5().until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[normalize-space()='Child item with worst health']")
        )).click();
        getDriver().findElement(By.cssSelector("label.attach-previous")).click();
        getDriver().findElement(By.cssSelector("button.jenkins-button.apply-button")).click();

        Assert.assertTrue(
                getWait5().until(ExpectedConditions.urlMatches(
                        ".*/job/" + FOLDER_NAME + "/configure/?$")),
                "User should remain on the folder configuration page"
        );
        Assert.assertFalse(
                getDriver().findElement(By.cssSelector("input[name='_.recursive']")).isSelected(),
                "Recursive checkbox should not be selected"
        );
    }

    @Test
    public void testCreateNestedFolder () {
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

        Assert.assertTrue(wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(childFolder))).isDisplayed(),"childFolder should be listed inside " + FOLDER_NAME);
    }
}
