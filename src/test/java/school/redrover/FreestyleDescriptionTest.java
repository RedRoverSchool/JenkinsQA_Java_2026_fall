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

    @Ignore
    @Test(description = "TC_02_002_04")
    public void testAddNewDescription() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(40));
        String projectName = "Desc_" + System.currentTimeMillis();
        String descAddText = "A new text for description";

        try {
            createFreestyleProject(projectName);

            wait.until(ExpectedConditions.elementToBeClickable(By.id("jenkins-head-icon"))).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.linkText(projectName))).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.id("description-link"))).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.id("description-textarea"))).sendKeys(descAddText);
            wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#bottom-sticker button.jenkins-button--primary"))).click();

            WebElement descNew = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("description-content")));
            Assert.assertTrue(descNew.isDisplayed(), "Мы не на странице Status");
            Assert.assertEquals(descNew.getText(), descAddText, "Текст не совпал");
            WebElement linkEdit = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("description-link")));
            Assert.assertTrue(linkEdit.getText().contains("Edit description"), "Текст ссылки не изменился на 'Edit description'");

        } finally {
            deleteProject(projectName);
        }
    }

    @Ignore
    @Test(description = "TC_02_002_05")
    public void testUpdateDescription() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(40));
        String projectName = "Desc_" + System.currentTimeMillis();
        String descEdit = "I edited the text";
        String oldText = "There was an old text here";
        try {
            createFreestyleProject(projectName);

            wait.until(ExpectedConditions.elementToBeClickable(By.id("jenkins-head-icon"))).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.linkText(projectName))).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.id("description-link"))).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.id("description-textarea"))).sendKeys(oldText);
            wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#bottom-sticker button.jenkins-button--primary"))).click();
            wait.until(ExpectedConditions.textToBePresentInElementLocated(By.id("description-content"), oldText));
            wait.until(ExpectedConditions.elementToBeClickable(By.id("description-link"))).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.id("description-textarea"))).clear();
            wait.until(ExpectedConditions.elementToBeClickable(By.id("description-textarea"))).sendKeys(descEdit);
            wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#bottom-sticker button.jenkins-button--primary"))).click();
            wait.until(ExpectedConditions.textToBePresentInElementLocated(By.id("description-content"), descEdit));

            WebElement descEditField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("description-content")));
            Assert.assertEquals(descEditField.getText(), descEdit, "Текст не изменился");
        } finally {
            deleteProject(projectName);
        }
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

    private void deleteProject(String name) {
        try {
            getDriver().get("http://localhost:8080/job/" + name + "/delete");
            WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(15));
            wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#main-panel form button"))).click();
        } catch (Exception ignored) {
        }
    }
}