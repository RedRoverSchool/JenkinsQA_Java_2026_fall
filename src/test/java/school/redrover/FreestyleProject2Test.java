package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

import java.time.Duration;

public class FreestyleProject2Test extends BaseTest {

    private static final String PROJECT_NAME = "Freestyle_Description";
    private static final String DESK_TEXT = "This is my test";
    private static final String DESK_ADD_TEXT = "A new text for description";
    private static final String DESK_EDIT_TEXT = "I edited the text";
    private static final String OLD_TEXT = "This is old text";

    @Test
    public void testFieldAndPreviewDescription() {
        createFreestyleProject(PROJECT_NAME);
        getDriver().findElement(By.cssSelector("a[href$='/configure']")).click();

        WebElement descriptionField = getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.name("description")));
        Assert.assertTrue(descriptionField.isDisplayed(), "Элемент поля не виден");
        WebElement preview = getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.className("textarea-show-preview")));
        Assert.assertTrue(preview.isDisplayed(), "Элемент Preview не видна");
    }

    @Test
    public void testSaveAndVerifyOnStatusPageDescription() {
        createFreestyleProject(PROJECT_NAME);
        getDriver().findElement(By.cssSelector("a[href$='/configure']")).click();
        getDriver().findElement(By.name("description")).sendKeys(DESK_TEXT);
        getDriver().findElement(By.cssSelector("#bottom-sticker button.jenkins-button--primary")).click();

        WebElement descStatus = getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.id("description-content")));
        Assert.assertEquals(descStatus.getText(), DESK_TEXT, "Текст на странице не совпал");
    }

    @Test
    public void testAddNewDescription() {
        createFreestyleProject(PROJECT_NAME);
        clickJenkinsLogo();
        getDriver().findElement(By.linkText(PROJECT_NAME)).click();
        getWait5().until(ExpectedConditions.elementToBeClickable(By.id("description-link"))).click();
        getDriver().findElement(By.id("description-textarea")).sendKeys(DESK_ADD_TEXT);
        getDriver().findElement(By.cssSelector("#bottom-sticker button.jenkins-button--primary")).click();

        WebElement descNew = getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.id("description-content")));
        Assert.assertTrue(descNew.isDisplayed(), "Мы не на странице Status");
        Assert.assertEquals(descNew.getText(), DESK_ADD_TEXT, "Текст не совпал");
        WebElement linkEdit = getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.id("description-link")));
        Assert.assertTrue(linkEdit.getText().contains("Edit description"), "Текст ссылки не изменился на 'Edit description'");
    }

    @Test
    public void testUpdateDescription() {
        createFreestyleProject(PROJECT_NAME);
        clickJenkinsLogo();
        getDriver().findElement(By.linkText(PROJECT_NAME)).click();
        getWait5().until(ExpectedConditions.elementToBeClickable(By.id("description-link"))).click();
        getDriver().findElement(By.id("description-textarea")).sendKeys(OLD_TEXT);
        getDriver().findElement(By.cssSelector("#bottom-sticker button.jenkins-button--primary")).click();
        getWait5().until(ExpectedConditions.textToBePresentInElementLocated(By.id("description-content"), OLD_TEXT));
        getWait5().until(ExpectedConditions.elementToBeClickable(By.id("description-link"))).click();
        WebElement textarea = getWait5().until(ExpectedConditions.elementToBeClickable(By.id("description-textarea")));
        textarea.clear();
        textarea.sendKeys(DESK_EDIT_TEXT);
        getDriver().findElement(By.cssSelector("#bottom-sticker button.jenkins-button--primary")).click();
        getWait5().until(ExpectedConditions.textToBePresentInElementLocated(By.id("description-content"), DESK_EDIT_TEXT));

        WebElement descEditField = getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.id("description-content")));
        Assert.assertEquals(descEditField.getText(), DESK_EDIT_TEXT, "Текст не изменился");
    }

    private void createFreestyleProject(String name) {
        getDriver().findElement(By.cssSelector("a[href*='newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(name);
        getDriver().findElement(By.cssSelector("#items .hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.id("ok-button")).click();

        for (int i = 0; i < 3; i++) {
            try {
                getWait5().until(ExpectedConditions.elementToBeClickable(By.cssSelector("#bottom-sticker button.jenkins-button--primary"))).click();
                break;
            } catch (StaleElementReferenceException | TimeoutException e) {
            }
        }
    }

    private void clickJenkinsLogo() {
        String baseXpath = "//span[contains(text(), '%s')]//ancestor::div//a[@class='app-jenkins-logo']";
        String finalXpath = String.format(baseXpath, PROJECT_NAME);
        WebElement logo = getWait5().until(ExpectedConditions.elementToBeClickable(By.xpath(finalXpath)));
        logo.click();
    }
}