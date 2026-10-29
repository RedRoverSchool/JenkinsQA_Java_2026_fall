package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

import java.time.Duration;

public class FreestyleDescriptionTest extends BaseTest {
    private static final String PROJECT_NAME = "Freestyle_Description";
    private static final String DESK_TEXT = "This is my test";
    private static final String DESK_ADD_TEXT = "A new text for description";
    private static final String DESK_EDIT_TEXT = "I edited the text";
    private static final String OLD_TEXT = "This is old text";

    @Test
    public void testFieldAndPreview() {
        createFreestyleProject(PROJECT_NAME);
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
        getDriver().findElement(By.cssSelector("a[href$='/configure']")).click();

        WebElement descriptionField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("description")));
        Assert.assertTrue(descriptionField.isDisplayed(), "Элемент поля не виден");
        WebElement preview = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("textarea-show-preview")));
        Assert.assertTrue(preview.isDisplayed(), "Элемент Preview не видна");
    }

    @Test
    public void testSaveAndVerifyOnStatusPage() {
        createFreestyleProject(PROJECT_NAME);
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
        getDriver().findElement(By.cssSelector("a[href$='/configure']")).click();
        getDriver().findElement(By.name("description")).sendKeys(DESK_TEXT);
        getDriver().findElement(By.cssSelector("#bottom-sticker button.jenkins-button--primary")).click();

        WebElement descStatus = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("description-content")));
        Assert.assertEquals(descStatus.getText(), DESK_TEXT, "Текст на странице не совпал");
    }

    @Test
    public void testAddNewDescription() {
        createFreestyleProject(PROJECT_NAME);
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
        wait.until(ExpectedConditions.elementToBeClickable(By.id("jenkins-head-icon"))).click();
        getDriver().findElement(By.linkText(PROJECT_NAME)).click();
        getDriver().findElement(By.id("description-link")).click();
        getDriver().findElement(By.id("description-textarea")).sendKeys(DESK_ADD_TEXT);
        getDriver().findElement(By.cssSelector("#bottom-sticker button.jenkins-button--primary")).click();

        WebElement descNew = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("description-content")));
        Assert.assertTrue(descNew.isDisplayed(), "Мы не на странице Status");
        Assert.assertEquals(descNew.getText(), DESK_ADD_TEXT, "Текст не совпал");
        WebElement linkEdit = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("description-link")));
        Assert.assertTrue(linkEdit.getText().contains("Edit description"), "Текст ссылки не изменился на 'Edit description'");
    }

    @Ignore
    @Test
    public void testUpdateDescription() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
        createFreestyleProject(PROJECT_NAME);

        getDriver().findElement(By.id("jenkins-head-icon")).click();
        getDriver().findElement(By.linkText(PROJECT_NAME)).click();
        getDriver().findElement(By.id("description-link")).click();
        getDriver().findElement(By.id("description-textarea")).sendKeys(OLD_TEXT);
        getDriver().findElement(By.cssSelector("#bottom-sticker button.jenkins-button--primary")).click();
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.id("description-content"), OLD_TEXT));
        getDriver().findElement(By.id("description-link")).click();
        getDriver().findElement(By.id("description-textarea")).clear();
        getDriver().findElement(By.id("description-textarea")).sendKeys(DESK_EDIT_TEXT);
        getDriver().findElement(By.cssSelector("#bottom-sticker button.jenkins-button--primary")).click();
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.id("description-content"), DESK_EDIT_TEXT));

        WebElement descEditField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("description-content")));
        Assert.assertEquals(descEditField.getText(), DESK_EDIT_TEXT, "Текст не изменился");
    }

    private void createFreestyleProject(String name) {
        getDriver().findElement(By.cssSelector("a[href*='newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(name);
        getDriver().findElement(By.cssSelector("#items .hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.id("ok-button")).click();

        for (int i = 0; i < 3; i++) {
            try {
                WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
                wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#bottom-sticker button.jenkins-button--primary"))).click();
                break;
            } catch (StaleElementReferenceException e) {
            }
        }
    }
}