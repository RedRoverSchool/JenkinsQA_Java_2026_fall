package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

public class BuildHistoryTest extends BaseTest {

    private static final String PROJECT_NAME = "BuildHistoryTest";

    @Test
    public void testBuildHistoryConsoleOutput() {
        createFreestyleProject(PROJECT_NAME);
        createBuildHistory();
        openConsoleOutput();

        WebElement outConsole = getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.id("out")));
        Assert.assertTrue(outConsole.isDisplayed());
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

    private void createBuildHistory() {
        getWait5().until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href$='/build?delay=0sec'].task-link"))).click();
        getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.id("jenkins-build-history")));
    }

    private void openConsoleOutput() {
        getDriver().get(school.redrover.common.ProjectUtils.getUrl() + "job/" + PROJECT_NAME + "/lastBuild/console");
    }
}
