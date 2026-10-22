package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public class PipelineTest extends BaseTest {
    public final By NEW_ITEM_ICON = By.xpath("//span[contains(text(),'New Item')]/preceding-sibling::span[1]");
    public final By ITEM_NAME_FIELD = By.id("name");
    public final By JOB_ITEM_TYPE = By.className("org_jenkinsci_plugins_workflow_job_WorkflowJob");
    public final By SUBMIT_BUTTON = By.id("ok-button");
    public final By CONFIGURE_PIPELINE_LINK = By.xpath("//span[normalize-space(.)='Pipeline']");

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
        wait.until(ExpectedConditions.visibilityOfElementLocated((By.id("jenkins-head-icon"))));
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
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(7));
        String expectedErrorMsg = String.format("‘%s’ is an unsafe character", invalidPipelineName);

        getDriver().findElement(By.xpath("//span[contains(text(),'New Item')]/preceding-sibling::span[1]")).click();
        getDriver().findElement(By.id("name")).sendKeys("test" + invalidPipelineName);
        getDriver().findElement(By.className("org_jenkinsci_plugins_workflow_job_WorkflowJob")).click();

        String errorElementText = wait.until(
                ExpectedConditions.visibilityOfElementLocated((By.xpath("//div[@class='error']")))).getText();

        Assert.assertEquals(errorElementText, expectedErrorMsg);
    }

    @Test
    public void testDefinePipelineScriptFromSample() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
        String projectName = "testPipeline";

        clickNewItemIcon();
        inputItemName(projectName);
        selectJobItemType();
        clickSubmitButton();
        clickConfigurePipelineLink();
        WebElement samples = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("workflow-editor-samples")));
        Select select = new Select(samples);
        select.selectByValue("hello");

        Assert.assertEquals(getDriver().findElement(By.xpath("//span[@class='ace_identifier']")).getText(), "pipeline");
    }

    public void clickNewItemIcon() {
        getDriver().findElement(NEW_ITEM_ICON).click();
    }

    public void inputItemName(String projectName) {
        getDriver().findElement(ITEM_NAME_FIELD).sendKeys(projectName);
    }

    public void selectJobItemType() {
        getDriver().findElement(JOB_ITEM_TYPE).click();
    }

    public void clickSubmitButton() {
        getDriver().findElement(SUBMIT_BUTTON).click();
    }

    public void clickConfigurePipelineLink() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
        wait.until(ExpectedConditions.elementToBeClickable(CONFIGURE_PIPELINE_LINK)).click();
    }

    @Test
    public void testSaveButton() {

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        String pipelineName = "New test";
        String addDescription = "New description";


        getDriver().findElement(NEW_ITEM_ICON).click();
        getDriver().findElement(ITEM_NAME_FIELD).sendKeys(pipelineName);
        getDriver().findElement(JOB_ITEM_TYPE).click();
        getDriver().findElement(SUBMIT_BUTTON).click();
        wait.until(ExpectedConditions.elementToBeClickable((By.id("jenkins-head-icon")))).click();

        WebElement chevronOpen = getDriver().findElement(By.cssSelector("#job_New\\ test > td:nth-child(3) > a > button"));
        chevronOpen.click();

        WebElement configureOpen = getDriver().findElement(By.cssSelector("#tippy-4 > div > div > div > a:nth-child(4)"));
        wait.until(ExpectedConditions.elementToBeClickable((configureOpen)));
        configureOpen.click();

        WebElement generalDescription = getDriver().findElement(By.name("description"));
        generalDescription.sendKeys(addDescription);

        WebElement submitButton = getDriver().findElement(By.name("Submit"));
        submitButton.click();

        WebElement newTestDescription = getDriver().findElement(By.xpath("//*[@id='description']"));

        Assert.assertEquals(newTestDescription.getText(), addDescription, "The description does not match");
    }
}
