package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public class PipelineTest extends BaseTest {

    @Test
    public void testCreatePipeline() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
        final String projectName = "NewPipeline";

        getDriver().findElement(By.xpath("//span[contains(text(),'New Item')]/preceding-sibling::span[1]")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("name"))).sendKeys(projectName);
        getDriver().findElement(By.className("org_jenkinsci_plugins_workflow_job_WorkflowJob")).click();
        wait.until(ExpectedConditions.elementToBeClickable((By.id("ok-button")))).click();
        wait.until(ExpectedConditions.elementToBeClickable((By.id("jenkins-head-icon")))).click();

        Assert.assertEquals(getDriver().findElement(By.cssSelector(".jenkins-table__link > span:first-child")).getText(),
                projectName);
    }

    @Test
    public void testDeletePipeline() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
        final String projectName = "test";

        getDriver().findElement(By.xpath("//span[contains(text(),'New Item')]/preceding-sibling::span[1]")).click();
        getDriver().findElement(By.id("name")).sendKeys(projectName);
        getDriver().findElement(By.className("org_jenkinsci_plugins_workflow_job_WorkflowJob")).click();
        wait.until(ExpectedConditions.elementToBeClickable((By.id("ok-button")))).click();
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

    @Test
    public void testRenamePipeline() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(3));
        final String projectName = "testName";
        final String newProjectName = "testNewName";

        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(projectName);
        getDriver().findElement(By.className("org_jenkinsci_plugins_workflow_job_WorkflowJob")).click();
        wait.until(ExpectedConditions.elementToBeClickable((By.id("ok-button")))).click();
        wait.until(ExpectedConditions.elementToBeClickable((By.id("jenkins-head-icon")))).click();
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

    @Test
    public void testCreatePipelineWithExistingName() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
        final String projectName = "ExistingPipeline";

        getDriver().findElement(By.xpath("//span[contains(text(),'New Item')]/preceding-sibling::span[1]")).click();
        getDriver().findElement(By.id("name")).sendKeys(projectName);
        getDriver().findElement(By.className("org_jenkinsci_plugins_workflow_job_WorkflowJob")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("ok-button"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.id("jenkins-head-icon"))).click();

        getDriver().findElement(By.xpath("//span[contains(text(),'New Item')]/preceding-sibling::span[1]")).click();

        getDriver().findElement(By.id("name")).sendKeys(projectName);

        getDriver().findElement(By.className("org_jenkinsci_plugins_workflow_job_WorkflowJob")).click();

        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(), 'A job already exists')]")));
        Assert.assertTrue(errorMessage.isDisplayed(), "Сообщение об ошибке не появилось!");

        WebElement okButton = getDriver().findElement(By.id("ok-button"));
        Assert.assertFalse(okButton.isEnabled(), "Кнопка ОК должна быть неактивна!");
    }

    @Test
    public void testSkipConfigurationAndReturnToDashboard() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
        final String projectName = "SkipConfigPipeline";

        getDriver().findElement(By.xpath("//span[contains(text(),'New Item')]/preceding-sibling::span[1]")).click();

        getDriver().findElement(By.id("name")).sendKeys(projectName);

        getDriver().findElement(By.className("org_jenkinsci_plugins_workflow_job_WorkflowJob")).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.id("ok-button"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.id("jenkins-head-icon"))).click();

        WebElement itemInList = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".jenkins-table__link > span:first-child")));

        Assert.assertEquals(itemInList.getText(), projectName, "Созданный проект не отображается на Dashboard!");
    }

    @DataProvider(name = "unsupportedCharacters")
    public Object[][] unsupportedCharacters() {
        List<String> unsupportedCharacters = Arrays.asList("!", "@", "#", "$", "%", "^", "&", "*", ";", ":", "|", "\\", "/", "?", "<", ">", "[");
        Object[][] data = new Object[unsupportedCharacters.size()][1];
        for (int i = 0; i < unsupportedCharacters.size(); i++) {
            data[i][0] = unsupportedCharacters.get(i);
        }
        return data;
    }

    @Test(dataProvider = "unsupportedCharacters")
    public void testCreatePipelineWithUnsupportedCharacters(String invalidPipelineName) {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
        String expectedErrorMsg = String.format("‘%s’ is an unsafe character", invalidPipelineName);

        getDriver().findElement(By.xpath("//span[contains(text(),'New Item')]/preceding-sibling::span[1]")).click();
        getDriver().findElement(By.id("name")).sendKeys("test" + invalidPipelineName);
        getDriver().findElement(By.className("org_jenkinsci_plugins_workflow_job_WorkflowJob")).click();

        WebElement errorElement = wait.until(
                ExpectedConditions.visibilityOfElementLocated((By.xpath("//div[@class='error']"))));

        Assert.assertEquals(errorElement.getText(), expectedErrorMsg);
    }
}
