package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

import java.time.Duration;

public class MultibranchPipelineTest extends BaseTest {
    @Test
    public void testCreateMultibranchPipeline() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(3));
        final String projectName = "multibranchTest";

        getDriver().findElement(By.xpath("//span[contains(text(),'New Item')]/preceding-sibling::span[1]")).click();
        getDriver().findElement(By.id("name")).sendKeys(projectName);
        JavascriptExecutor js = (JavascriptExecutor) getDriver(); // Получает JavaScriptExecutor
        js.executeScript("window.scrollTo(0, 1000);");
        getDriver().findElement(By.className("org_jenkinsci_plugins_workflow_multibranch_WorkflowMultiBranchProject")).click();
        getDriver().findElement((By.id("ok-button"))).click();

        wait.until(ExpectedConditions.elementToBeClickable((By.id("jenkins-head-icon")))).click();

        Assert.assertEquals(getDriver().findElement(By.cssSelector(".jenkins-table__link > span:first-child")).getText(),
                projectName);
    }

    @Test
    public void testDeleteMultibranchPipeline() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(3));
        final String projectName = "multibranchTest";

        getDriver().findElement(By.xpath("//span[contains(text(),'New Item')]/preceding-sibling::span[1]")).click();
        getDriver().findElement(By.id("name")).sendKeys(projectName);
        JavascriptExecutor js = (JavascriptExecutor) getDriver(); // Получает JavaScriptExecutor
        js.executeScript("window.scrollTo(0, 1000);");
        getDriver().findElement(By.className("org_jenkinsci_plugins_workflow_multibranch_WorkflowMultiBranchProject")).click();
        getDriver().findElement((By.id("ok-button"))).click();

        wait.until(ExpectedConditions.elementToBeClickable((By.id("jenkins-head-icon")))).click();

        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[@class='jenkins-menu-dropdown-chevron']"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//button[@class='jenkins-dropdown__item' " +
                                "and contains(normalize-space(.), 'Delete Multibranch Pipeline')]")))
                .click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//dialog[@class='jenkins-dialog']")));
        wait.until(ExpectedConditions.elementToBeClickable
                (By.xpath("//button[@data-id='ok']"))).click();

        Assert.assertEquals(getDriver().findElement
                        (By.xpath("//h1[contains(text(), 'Welcome to Jenkins!')]")).getText(),
                "Welcome to Jenkins!");
    }
}
