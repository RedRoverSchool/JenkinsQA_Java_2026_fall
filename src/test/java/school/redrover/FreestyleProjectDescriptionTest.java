package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

import java.time.Duration;

public class FreestyleProjectDescriptionTest extends BaseTest {

    @Test
    public void testDescriptionFieldAndPreview() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(40));
        String projectName = "Desc_" + System.currentTimeMillis();

        try {
            createFreestyleProject(wait, projectName);

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[href$='/configure']"))).click();

            WebElement descriptionField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("description")));
            Assert.assertTrue(descriptionField.isDisplayed(), "Элемент поля не виден");

            WebElement preview = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("textarea-show-preview")));
            Assert.assertTrue(preview.isDisplayed(), "Элемент Preview не видна");
        } finally {
            deleteProject(projectName);
        }
    }

@Test(description = "TC_02.002.03")
    public void testSaveDescriptionAndVerifyOnStatusPage() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(40));
        String projectName = "DescSave_" + System.currentTimeMillis();
        String descText = "This is my test";

        try {
            createFreestyleProject(wait, projectName);

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[href$='/configure']"))).click();
            WebElement descField = wait.until(ExpectedConditions.elementToBeClickable(By.name("description")));
            descField.sendKeys(descText);
            wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#bottom-sticker button.jenkins-button--primary"))).click();

            WebElement descStatus = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("description-content")));
            Assert.assertEquals(descStatus.getText(), descText, "Текст на странице не совпал");
        } finally {
            deleteProject(projectName);
        }
    }


    private void createFreestyleProject(WebDriverWait wait, String name) {
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href*='newJob']"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("name"))).sendKeys(name);
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#items .hudson_model_FreeStyleProject"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("ok-button"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#bottom-sticker button.jenkins-button--primary"))).click();
    }

    private void deleteProject(String name) {
        try {
            getDriver().get("http://localhost:8080/job/" + name + "/delete");
            WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(15));
            wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#main-panel form button"))).click();
        } catch (Exception ignored) {
            // Если проект не создался — ничего страшного
        }
    }
}