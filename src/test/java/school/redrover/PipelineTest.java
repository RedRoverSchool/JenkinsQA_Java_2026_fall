package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
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
    private static final By NEW_ITEM_ICON = By.xpath("//span[contains(text(),'New Item')]/preceding-sibling::span[1]");
    private static final By ITEM_NAME_FIELD = By.id("name");
    private static final By JOB_ITEM_TYPE = By.className("org_jenkinsci_plugins_workflow_job_WorkflowJob");
    private static final By SUBMIT_BUTTON = By.id("ok-button");
    private static final By CONFIGURE_PIPELINE_LINK = By.xpath("//span[@class='task-link-text' and contains(text(), 'Pipeline')]/ancestor::button");

    @Test
    public void testCreate() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
        String projectName = "NewPipeline";

        getDriver().findElement(By.xpath("//span[contains(text(),'New Item')]/preceding-sibling::span[1]")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("name"))).sendKeys(projectName);
        getDriver().findElement(By.className("org_jenkinsci_plugins_workflow_job_WorkflowJob")).click();
        wait.until(ExpectedConditions.elementToBeClickable((By.id("ok-button")))).click();
        wait.until(ExpectedConditions.elementToBeClickable((By.xpath("//a[@class='app-jenkins-logo']")))).click();

        Assert.assertEquals(getDriver().findElement(By.cssSelector(".jenkins-table__link > span:first-child")).getText(),
                projectName);
    }

    @Test
    public void testDelete() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
        String projectName = "test";

        getDriver().findElement(By.xpath("//span[contains(text(),'New Item')]/preceding-sibling::span[1]")).click();
        getDriver().findElement(By.id("name")).sendKeys(projectName);
        getDriver().findElement(By.className("org_jenkinsci_plugins_workflow_job_WorkflowJob")).click();
        wait.until(ExpectedConditions.elementToBeClickable((By.id("ok-button")))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated((By.id("jenkins-head-icon"))));
        wait.until(ExpectedConditions.elementToBeClickable((By.xpath("//a[@class='app-jenkins-logo']")))).click();

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
    public void testRename() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
        String projectName = "testName";
        String newProjectName = "testNewName";

        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(projectName);
        getDriver().findElement(By.className("org_jenkinsci_plugins_workflow_job_WorkflowJob")).click();
        wait.until(ExpectedConditions.elementToBeClickable((By.id("ok-button")))).click();
        wait.until(ExpectedConditions.elementToBeClickable((By.xpath("//a[@class='app-jenkins-logo']")))).click();
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[@class='jenkins-menu-dropdown-chevron']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.partialLinkText("Rename"))).click();

        WebElement newNameBtn = getDriver().findElement(By.name("newName"));
        newNameBtn.clear();
        newNameBtn.sendKeys(newProjectName);
        getDriver().findElement(By.xpath("//button[@value='Rename']")).click();
        wait.until(ExpectedConditions.textToBe(By.tagName("h1"), newProjectName));
        String actualProjectName = wait.until(ExpectedConditions.visibilityOfElementLocated((By.tagName("h1")))).getText();

        Assert.assertEquals(actualProjectName, newProjectName);
    }

    @Test
    public void testCreateWithExistingName() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
        String projectName = "ExistingPipeline";

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

    @Ignore
    @Test
    public void testSkipConfigurationAndReturnToDashboard() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
        String projectName = "SkipConfigPipeline";

        getDriver().findElement(By.xpath("//span[contains(text(),'New Item')]/preceding-sibling::span[1]")).click();
        getDriver().findElement(By.id("name")).sendKeys(projectName);
        getDriver().findElement(By.className("org_jenkinsci_plugins_workflow_job_WorkflowJob")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("ok-button"))).click();
        wait.until(ExpectedConditions.elementToBeClickable((By.xpath("//a[@class='app-jenkins-logo']")))).click();

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
    public void testCreateWithUnsupportedCharacters(String invalidPipelineName) {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(7));
        String expectedErrorMsg = String.format("‘%s’ is an unsafe character", invalidPipelineName);

        getDriver().findElement(By.xpath("//span[contains(text(),'New Item')]/preceding-sibling::span[1]")).click();
        getDriver().findElement(By.id("name")).sendKeys("test" + invalidPipelineName);
        getDriver().findElement(By.className("org_jenkinsci_plugins_workflow_job_WorkflowJob")).click();

        wait.until(
                ExpectedConditions.presenceOfElementLocated((By.xpath("//div[@class='error']"))));
        String errorElementText = wait.until(
                ExpectedConditions.visibilityOfElementLocated((By.xpath("//div[@class='error']")))).getText();

        Assert.assertEquals(errorElementText, expectedErrorMsg);
    }

    @Test
    public void testDefinePipelineScriptFromSample() {
        String projectName = "testPipeline";

        clickNewItemIcon();
        inputJobName(projectName);
        selectJobItemType();
        clickSubmitButton();
        clickConfigurePipelineLink();
        Select select = new Select(getDriver().findElement(By.id("workflow-editor-samples")));
        select.selectByValue("hello");

        Assert.assertEquals(getDriver().findElement(By.xpath("//span[@class='ace_identifier']")).getText(), "pipeline");
    }

    public void clickNewItemIcon() {
        getDriver().findElement(NEW_ITEM_ICON).click();
    }

    public void inputJobName(String projectName) {
        getDriver().findElement(ITEM_NAME_FIELD).sendKeys(projectName);
    }

    public void selectJobItemType() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
        wait.until(ExpectedConditions.elementToBeClickable(JOB_ITEM_TYPE)).click();
    }

    public void clickSubmitButton() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(SUBMIT_BUTTON)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(CONFIGURE_PIPELINE_LINK));
    }

    public void clickConfigurePipelineLink() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(CONFIGURE_PIPELINE_LINK)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("workflow-editor-samples")));
    }

    @Test
    public void testSaveButton() {

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        final String pipelineName = "New test";
        final String addDescription = "New description";

        getDriver().findElement(NEW_ITEM_ICON).click();
        getDriver().findElement(ITEM_NAME_FIELD).sendKeys(pipelineName);
        getDriver().findElement(JOB_ITEM_TYPE).click();
        wait.until(ExpectedConditions.elementToBeClickable(SUBMIT_BUTTON)).click();
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

    @Ignore
    @Test
    public void testApplyButton() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        final String pipelineName = "New test";
        final String addDescription = "New description";

        getDriver().findElement(NEW_ITEM_ICON).click();
        getDriver().findElement(ITEM_NAME_FIELD).sendKeys(pipelineName);
        getDriver().findElement(JOB_ITEM_TYPE).click();
        wait.until(ExpectedConditions.elementToBeClickable(SUBMIT_BUTTON)).click();
        wait.until(ExpectedConditions.elementToBeClickable((By.id("jenkins-head-icon")))).click();

        WebElement chevronOpen = getDriver().findElement(By.cssSelector("#job_New\\ test > td:nth-child(3) > a > button"));
        chevronOpen.click();
        WebElement configureOpen = getDriver().findElement(By.cssSelector("#tippy-4 > div > div > div > a:nth-child(4)"));
        wait.until(ExpectedConditions.elementToBeClickable((configureOpen)));
        configureOpen.click();
        WebElement generalDescription = getDriver().findElement(By.name("description"));
        generalDescription.sendKeys(addDescription);
        WebElement applyButton = getDriver().findElement(By.name("Apply"));
        applyButton.click();
        WebElement notificationBar = getDriver().findElement(By.xpath("//*[@id='notification-bar']/span"));
        wait.until(ExpectedConditions.visibilityOf(notificationBar));

        Assert.assertEquals(notificationBar.getText(), "Saved", "Changes were not applied");
    }
}
