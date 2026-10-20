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
    public void testCreate() {
        getDriver().findElement(By.cssSelector("a.task-link")).click();
        getDriver().findElement(By.id("name")).sendKeys(FOLDER_NAME);
        getDriver().findElement(By.className("com_cloudbees_hudson_plugins_folder_Folder")).click();
        getDriver().findElement(By.id("ok-button")).click();

        getDriver().findElement(By.name("_.displayNameOrNull")).sendKeys(FOLDER_NAME);
        getDriver().findElement(By.name("Submit")).click();

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlMatches(".*/job/" + FOLDER_NAME + "/?$"));
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.app-jenkins-logo"))).click();

        Assert.assertTrue(
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(FOLDER_NAME))).isDisplayed()
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
        wait.until(ExpectedConditions.elementToBeClickable(By.id("jenkins-head-icon"))).click();
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

        wait.until(ExpectedConditions.textToBePresentInElementValue(descriptionField, descriptionText));
        wait.until(ExpectedConditions.elementToBeClickable(By.name("Submit"))).click();
        wait.until(ExpectedConditions.urlContains("job/ConfigTestFolder"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("h1")));
        WebElement pageHeader = wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("h1")));

        Assert.assertTrue(pageHeader.getText().contains(newDisplayName), "Новое имя папки не отобразилось!");

        WebElement actualDescription = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(text(), '" + descriptionText + "')]")));

        Assert.assertTrue(actualDescription.isDisplayed(), "Описание не отобразилось на странице!");
    }
}
