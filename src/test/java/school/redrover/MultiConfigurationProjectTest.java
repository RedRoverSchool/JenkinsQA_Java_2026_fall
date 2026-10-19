package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import java.time.Duration;


public class MultiConfigurationProjectTest extends BaseTest {

    @Test
    public void testCreateMultiConfigurationProject() {

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(30));

        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.task-link[href*='newJob']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".jenkins-form, .app-page-body, form")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#name"))).sendKeys("MultiConfigurationProject");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//label[input[@value='hudson.matrix.MatrixProject']]"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#ok-button"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.app-jenkins-logo"))).click();
        WebElement itemLink = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("a.jenkins-table__link[href^='job/']")));

        Assert.assertTrue(itemLink.getText().contains("MultiConfigurationProject"));
    }

    @Test
    public void testEnableOrDisableProject() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(30));
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.task-link[href*='newJob']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".jenkins-form, .app-page-body, form")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#name"))).sendKeys("MultiConfigurationProject");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//label[input[@value='hudson.matrix.MatrixProject']]"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#ok-button"))).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("label[for='enable-disable-project']"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[name='Submit']"))).click();

        WebElement alertForm = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("form#enable-project")));

        Assert.assertTrue(alertForm.getText().contains("This project is currently disabled"));

    }
}
