package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

import java.util.Arrays;
import java.util.List;

import static org.testng.Assert.assertEquals;

public class PipelineProjectTest extends BaseTest {

    private static final String PROJECT_NAME = "NewPipeline";
    private static final String NEW_PROJECT_NAME = "testNewName";
    private static final String DESCRIPTION = "New description";

    @Test
    public void testCreate() {
        getDriver().findElement(By.xpath("//a[.//span[text()='New Item']]")).click();
        getDriver().findElement(By.id("name")).sendKeys(PROJECT_NAME);
        getDriver().findElement(By.xpath("//span[@class='jenkins-choice-list__item__label' and text()='Pipeline']")).click();
        getWait2().until(ExpectedConditions.elementToBeClickable(By.id("ok-button"))).click();
        getDriver().findElement(By.xpath(String.format("//a[contains(text(), '%s')]//ancestor::div//a[@class='app-jenkins-logo']", PROJECT_NAME))).click();

        assertEquals(getDriver().findElement(By.cssSelector(".jenkins-table__link > span:first-child")).getText(), PROJECT_NAME);
    }

    @Test (dependsOnMethods = "testCreate")
    public void testCreateWithExistingName() {

        getDriver().findElement(By.xpath("//a[.//span[text()='New Item']]")).click();
        getDriver().findElement(By.id("name")).sendKeys(PROJECT_NAME);
        getDriver().findElement(By.className("org_jenkinsci_plugins_workflow_job_WorkflowJob")).click();

        WebElement errorMessage = getDriver().findElement(By.xpath("//*[contains(text(), 'A job already exists')]"));
        WebElement okButton = getDriver().findElement(By.id("ok-button"));

        Assert.assertTrue(errorMessage.isDisplayed(), "Сообщение об ошибке не появилось!");
        Assert.assertFalse(okButton.isEnabled(), "Кнопка ОК должна быть неактивна!");
    }

    @Test(dependsOnMethods = "testCreateWithExistingName")
    public void testRename() {

        getDriver().findElement(By.xpath("//button[@class='jenkins-menu-dropdown-chevron']")).click();
        getWait2().until(ExpectedConditions.elementToBeClickable(By.partialLinkText("Rename"))).click();

        WebElement newNameBtn = getDriver().findElement(By.name("newName"));
        newNameBtn.clear();
        newNameBtn.sendKeys(NEW_PROJECT_NAME);
        getDriver().findElement(By.xpath("//button[@value='Rename']")).click();

        getWait2().until(ExpectedConditions.textToBe(By.tagName("h1"), NEW_PROJECT_NAME));
        getDriver().findElement(By.xpath("//a[@class='app-jenkins-logo']")).click();

        assertEquals(getDriver().findElement(By.cssSelector(".jenkins-table__link > span:first-child")).getText(), NEW_PROJECT_NAME);
    }

    @Test(dependsOnMethods = "testRename")
    public void testVisibilityNotificationAfterAddDescriptionAndApply() {

        getDriver().findElement(By.className("jenkins-menu-dropdown-chevron")).click();
        getWait2().until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(@href, 'configure')]"))).click();
        getDriver().findElement(By.name("description")).sendKeys(DESCRIPTION);
        getDriver().findElement(By.name("Apply")).click();
        String notificationBar = getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='notification-bar']/span"))).getText();

        assertEquals(notificationBar, "Saved", "Changes were not applied");
    }

    @Test (dependsOnMethods = "testVisibilityNotificationAfterAddDescriptionAndApply")
    public void testSaveDescription() {

        getDriver().findElement(By.className("jenkins-menu-dropdown-chevron")).click();
        getWait2().until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(@href, 'configure')]"))).click();
        getDriver().findElement(By.name("Submit")).click();
        WebElement newTestDescription = getDriver().findElement(By.xpath("//*[@id='description']"));

        assertEquals(newTestDescription.getText(), DESCRIPTION, "The description does not match");
    }

    @Test(dependsOnMethods = "testSaveDescription")
    public void testDefinePipelineScriptFromSample() {

        getDriver().findElement(By.xpath("//button[@class='jenkins-menu-dropdown-chevron']")).click();
        getWait2().until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(@href, 'configure')]"))).click();
        getDriver().findElement(By.xpath("//span[@class='task-link-text' and contains(text(), 'Pipeline')]/ancestor::button")).click();
        getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.id("workflow-editor-samples")));
        Select select = new Select(getDriver().findElement(By.id("workflow-editor-samples")));
        select.selectByValue("hello");

        assertEquals(
                getDriver().findElement(By.xpath("//span[@class='ace_identifier']")).getText(),
                "pipeline");
    }

    @Test(dependsOnMethods = "testDefinePipelineScriptFromSample")
    public void testDelete() {

        getDriver().findElement(By.xpath("//button[@class='jenkins-menu-dropdown-chevron']")).click();
        getWait2().until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(normalize-space(.), 'Delete Pipeline')]"))).click();
        getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//dialog[@class='jenkins-dialog']")));
        getDriver().findElement(By.xpath("//button[@data-id='ok']")).click();

        assertEquals(getDriver().findElement(By.xpath("//h1[contains(text(), 'Welcome to Jenkins!')]")).getText(), "Welcome to Jenkins!");
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

        getDriver().findElement(By.xpath("//a[.//span[text()='New Item']]")).click();
        getDriver().findElement(By.id("name")).sendKeys("test" + invalidPipelineName);
        getDriver().findElement(By.xpath("//span[@class='jenkins-choice-list__item__label' and text()='Pipeline']")).click();
        String errorElementText = getWait2().until(
                ExpectedConditions.visibilityOfElementLocated((By.xpath("//div[@class='error']")))).getText();

        assertEquals(errorElementText, expectedErrorMsg);
    }
}
