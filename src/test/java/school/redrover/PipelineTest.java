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

import static org.testng.Assert.assertEquals;

public class PipelineTest extends BaseTest {
    private static final String PROJECT_NAME = "NewPipeline";
    private static final By NEW_ITEM_ICON = By.xpath("//span[contains(text(),'New Item')]/preceding-sibling::span[1]");
    private static final By ITEM_NAME_FIELD = By.id("name");
    private static final By JOB_ITEM_TYPE = By.className("org_jenkinsci_plugins_workflow_job_WorkflowJob");
    private static final By SUBMIT_BUTTON = By.id("ok-button");

    @Test
    public void testCreate() {
        clickNewItemIcon();
        inputJobName(PROJECT_NAME);
        selectJobItemType();
        clickSubmitButton();
        clickJenkinsLogo();

        assertEquals(getDriver().findElement(By.cssSelector(".jenkins-table__link > span:first-child")).getText(),
                PROJECT_NAME);
    }

    @Test
    public void testDelete() {
        clickNewItemIcon();
        inputJobName(PROJECT_NAME);
        selectJobItemType();
        clickSubmitButton();
        clickJenkinsLogo();

        getDriver().findElement(By.xpath("//button[@class='jenkins-menu-dropdown-chevron']")).click();
        getDriver().findElement(
                        By.xpath("//button[@class='jenkins-dropdown__item' and contains(normalize-space(.), 'Delete Pipeline')]"))
                .click();
        getWait2().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//dialog[@class='jenkins-dialog']")));
        getDriver().findElement(By.xpath("//button[@data-id='ok']")).click();

        assertEquals(getDriver().findElement(By.xpath("//h1[contains(text(), 'Welcome to Jenkins!')]")).getText(),
                "Welcome to Jenkins!");
    }

    @Test
    public void testRename() {
        final String newProjectName = "testNewName";

        clickNewJobLink();
        inputJobName(PROJECT_NAME);
        selectJobItemType();
        clickSubmitButton();
        clickJenkinsLogo();
        getDriver().findElement(By.xpath("//button[@class='jenkins-menu-dropdown-chevron']")).click();
        getDriver().findElement(By.partialLinkText("Rename")).click();

        WebElement newNameBtn = getDriver().findElement(By.name("newName"));
        newNameBtn.clear();
        newNameBtn.sendKeys(newProjectName);

        getDriver().findElement(By.xpath("//button[@value='Rename']")).click();
        getWait5().until(ExpectedConditions.textToBe(By.tagName("h1"), newProjectName));
        String actualProjectName = getDriver().findElement((By.tagName("h1"))).getText();

        assertEquals(actualProjectName, newProjectName);
    }
@Ignore
    @Test
    public void testCreateWithExistingName() {

        String projectName = "ExistingPipeline";

        getDriver().findElement(By.xpath("//span[contains(text(),'New Item')]/preceding-sibling::span[1]")).click();
        getDriver().findElement(By.id("name")).sendKeys(projectName);
        getDriver().findElement(By.className("org_jenkinsci_plugins_workflow_job_WorkflowJob")).click();
        getWait5().until(ExpectedConditions.elementToBeClickable(By.id("ok-button"))).click();
        getWait5().until(ExpectedConditions.elementToBeClickable((By.xpath("//span[contains(text(), 'Configure')]//ancestor::div//a[@class='app-jenkins-logo']")))).click();
        getDriver().findElement(By.xpath("//span[contains(text(),'New Item')]/preceding-sibling::span[1]")).click();
        getDriver().findElement(By.id("name")).sendKeys(projectName);
        getDriver().findElement(By.className("org_jenkinsci_plugins_workflow_job_WorkflowJob")).click();

        WebElement errorMessage = getDriver().findElement(By.xpath("//*[contains(text(), 'A job already exists')]"));
        WebElement okButton = getDriver().findElement(By.id("ok-button"));

        Assert.assertTrue(errorMessage.isDisplayed(), "Сообщение об ошибке не появилось!");
        Assert.assertFalse(okButton.isEnabled(), "Кнопка ОК должна быть неактивна!");
    }

    @Test
    public void testSkipConfigurationAndReturnToDashboard() {
        String projectName = "SkipConfigPipeline";

        getDriver().findElement(By.xpath("//span[contains(text(),'New Item')]/preceding-sibling::span[1]")).click();
        getDriver().findElement(By.id("name")).sendKeys(projectName);
        getDriver().findElement(By.className("org_jenkinsci_plugins_workflow_job_WorkflowJob")).click();
        getWait5().until(ExpectedConditions.elementToBeClickable(By.id("ok-button"))).click();
        getWait5().until(ExpectedConditions.elementToBeClickable((By.xpath("//span[contains(text(), 'Configure')]//ancestor::div//a[@class='app-jenkins-logo']")))).click();

        WebElement itemInList = getDriver().findElement(By.cssSelector(".jenkins-table__link > span:first-child"));

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
        String expectedErrorMsg = String.format("‘%s’ is an unsafe character", invalidPipelineName);

        clickNewItemIcon();
        getDriver().findElement(By.id("name")).sendKeys("test" + invalidPipelineName);
        getDriver().findElement(By.className("org_jenkinsci_plugins_workflow_job_WorkflowJob")).click();

        String errorElementText = getWait5().until(
                ExpectedConditions.visibilityOfElementLocated((By.xpath("//div[@class='error']")))).getText();

        assertEquals(errorElementText, expectedErrorMsg);
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

        assertEquals(getDriver().findElement(By.xpath("//span[@class='ace_identifier']")).getText(), "pipeline");
    }

    public void clickNewItemIcon() {
        getDriver().findElement(By.xpath("//span[contains(text(),'New Item')]/preceding-sibling::span[1]")).click();
    }

    public void clickNewJobLink() {
        getDriver().findElement(By.xpath("//a[@href='newJob']")).click();
    }

    public void inputJobName(String projectName) {
        getDriver().findElement(By.id("name")).sendKeys(projectName);
    }

    public void selectJobItemType() {
        getDriver().findElement(By.className("org_jenkinsci_plugins_workflow_job_WorkflowJob")).click();
    }

    public void clickSubmitButton() {
        getDriver().findElement(By.id("ok-button")).click();
    }

    public void clickConfigurePipelineLink() {
        getDriver().findElement(By.xpath("//span[@class='task-link-text' and contains(text(), 'Pipeline')]/ancestor::button")).click();
        getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.id("workflow-editor-samples")));
    }

    private void clickJenkinsLogo() {
        String selector = String.format("//a[contains(text(), '%s')]//ancestor::div//a[@class='app-jenkins-logo']", PROJECT_NAME);
        getDriver().findElement(By.xpath(selector)).click();
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
        wait.until(ExpectedConditions.elementToBeClickable(
                (By.xpath("//span[contains(text(), 'Configure')]//ancestor::div//a[@class='app-jenkins-logo']")))).click();

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

        assertEquals(newTestDescription.getText(), addDescription, "The description does not match");
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
        wait.until(ExpectedConditions.elementToBeClickable(
                (By.xpath("//span[contains(text(), 'Configure')]//ancestor::div//a[@class='app-jenkins-logo']")))).click();

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

        assertEquals(notificationBar.getText(), "Saved", "Changes were not applied");
    }
}
