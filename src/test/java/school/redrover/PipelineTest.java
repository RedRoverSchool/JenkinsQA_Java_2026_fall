package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

import java.time.Duration;

public class PipelineTest extends BaseTest {
  @Ignore
    @Test
    public void testCreatePipeline() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(3));
        final String projectName = "test";

        getDriver().findElement(By.xpath("//span[contains(text(),'New Item')]/preceding-sibling::span[1]")).click();
        getDriver().findElement(By.id("name")).sendKeys(projectName);
        getDriver().findElement(By.className("org_jenkinsci_plugins_workflow_job_WorkflowJob")).click();
        getDriver().findElement((By.id("ok-button"))).click();

        wait.until(ExpectedConditions.elementToBeClickable((By.id("jenkins-head-icon")))).click();

        Assert.assertEquals(getDriver().findElement(By.cssSelector(".jenkins-table__link > span:first-child")).getText(),
                projectName);
    }
    @Ignore
    @Test
    public void testDeletePipeline() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(3));
        final String projectName = "test";

        getDriver().findElement(By.xpath("//span[contains(text(),'New Item')]/preceding-sibling::span[1]")).click();
        getDriver().findElement(By.id("name")).sendKeys(projectName);
        getDriver().findElement(By.className("org_jenkinsci_plugins_workflow_job_WorkflowJob")).click();
        getDriver().findElement((By.id("ok-button"))).click();

        wait.until(ExpectedConditions.elementToBeClickable((By.id("jenkins-head-icon")))).click();

        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[@class='jenkins-menu-dropdown-chevron']"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//button[@class='jenkins-dropdown__item' and contains(normalize-space(.), 'Delete Pipeline')]")))
                .click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//dialog[@class='jenkins-dialog']")));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@data-id='ok']"))).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//h1[contains(text(), 'Welcome to Jenkins!')]")).getText(),
                "Welcome to Jenkins!");
    }
    @Ignore
    @Test
    public void testRenamePipeline() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(3));
        final String projectName = "testName";
        final String newProjectName = "testNewName";

        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(projectName);
        getDriver().findElement(By.className("org_jenkinsci_plugins_workflow_job_WorkflowJob")).click();
        getDriver().findElement((By.id("ok-button"))).click();

        getDriver().findElement(By.name("Submit")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated((By.id("jenkins-head-icon")))).click();
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[@class='jenkins-menu-dropdown-chevron']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.partialLinkText("Rename"))).click();

        WebElement newNameBtn = getDriver().findElement(By.name("newName"));
        newNameBtn.clear();
        newNameBtn.sendKeys(newProjectName);
        getDriver().findElement(By.xpath("//button[@value='Rename']")).click();

        String actualProjectName = wait.until(ExpectedConditions.visibilityOfElementLocated((By.tagName("h1")))).getText();

        Assert.assertEquals(actualProjectName, newProjectName);
    }
}
