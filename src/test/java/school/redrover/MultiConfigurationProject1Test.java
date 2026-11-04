package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

import java.time.Duration;

public class MultiConfigurationProject1Test extends BaseTest {
    private static final By NEW_JOB = By.cssSelector("a[href='/view/all/newJob']");
    private static final By PROJECT_NAME = By.id("name");
    private static final By MULTI_PROJECT = By.cssSelector(".hudson_matrix_MatrixProject.jenkins-choice-list__item");
    public static final By OK_BUTTON = By.id("ok-button");
    public static final By SAVE_BUTTON = By.cssSelector("[name='Submit']");

    private WebDriverWait getWait() {
        return new WebDriverWait(getDriver(), Duration.ofSeconds(10));
    }

    private String CreateMultiConfigurationProject() {
        String itemName = "TEST MultiConfigurationProject" + System.currentTimeMillis();
        WebDriverWait wait = getWait();

        wait.until(ExpectedConditions.elementToBeClickable(NEW_JOB)).click();
        wait.until(ExpectedConditions.elementToBeClickable(PROJECT_NAME)).sendKeys(itemName);
        wait.until(ExpectedConditions.elementToBeClickable(MULTI_PROJECT)).click();
        wait.until(ExpectedConditions.elementToBeClickable(OK_BUTTON)).click();
        wait.until(ExpectedConditions.elementToBeClickable(SAVE_BUTTON)).click();

        return itemName;
    }

    @Test
    public void testCreateMultiConfigurationProject() {
        String itemName = CreateMultiConfigurationProject();

        Assert.assertTrue(getWait().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".matrix-project-headline.page-headline"))).getText().contains(itemName), "Item " + itemName + " не найден на странице");
    }

    @Test
    public void testAddConfigDescription() {
        CreateMultiConfigurationProject();
        String descriptionText = "TST auto description" + System.currentTimeMillis();

        getWait().until(ExpectedConditions.elementToBeClickable(By.id("description-link"))).click();
        getWait().until(ExpectedConditions.elementToBeClickable(By.id("description-textarea"))).sendKeys(descriptionText);
        getWait().until(ExpectedConditions.elementToBeClickable(By.cssSelector("[data-id='ok']"))).click();

        Assert.assertTrue(getWait().until(ExpectedConditions.visibilityOfElementLocated(By.id("description-content"))).isDisplayed(),
                "Текст описания  " + descriptionText + " не отображается");
    }
}