package school.redrover;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.annotations.Ignore;
import school.redrover.common.BaseTest;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import school.redrover.pages.FreestyleConfigurationPage;
import school.redrover.pages.HomePage;

public class FreestyleProjectTest extends BaseTest {
    private static final String ITEM_NAME = "test";
    private static final String DESCRIPTION = "test description";

    @Test
    public void testCreate() {
        String projectName = new HomePage(getDriver())
                .clickNewItem()
                .inputProjectName(ITEM_NAME)
                .selectFreestyleAndClickOK()
                .gotoHomePage()
                .getFirstProjectName();

        Assert.assertEquals(projectName, ITEM_NAME);
    }

    @Test
    public void testApplyButton() {
        String notificationText = new HomePage(getDriver())
                .clickNewItem()
                .inputProjectName(ITEM_NAME)
                .selectFreestyleAndClickOK()
                .addDescription(DESCRIPTION)
                .clickApplyButton()
                .getNotificationText();

        Assert.assertEquals(notificationText, "Saved", "Changes were not applied");
    }

    @Test
    public void testIsTriggerSectionAvailable() {
        FreestyleConfigurationPage configPage = new HomePage(getDriver())
                .clickNewItem()
                .inputProjectName(ITEM_NAME)
                .selectFreestyleAndClickOK();

        Assert.assertTrue(
                configPage.isTriggerSectionDisplayed(), "Секция Triggers не отображается");
        Assert.assertTrue(
                configPage.areTriggerCheckboxesDisplayed("pseudoRemoteTrigger", "hudson-triggers-SCMTrigger"),
                "Один из чекбоксов не отображается");
    }

    @Test
    public void testBuildPeriodicallyPositive() {
        FreestyleConfigurationPage configPage = new HomePage(getDriver())
                .clickNewItem()
                .inputProjectName(ITEM_NAME)
                .selectFreestyleAndClickOK()
                .checkBuildPeriodically()
                .inputSchedule("H H * * *")
                .clickApplyButton();

        Assert.assertEquals(configPage.getNotificationText(), "Saved");
        Assert.assertTrue(configPage.getScheduleValidationMessage().contains("Would last have run at"));
    }

    @Test
    public void testBuildPeriodicallyWarning(){
        FreestyleConfigurationPage configPage = new HomePage(getDriver())
                .clickNewItem()
                .inputProjectName(ITEM_NAME)
                .selectFreestyleAndClickOK()
                .checkBuildPeriodically()
                .inputSchedule("0 0 * * *")
                .clickApplyButton();

        Assert.assertEquals(configPage.getNotificationText(), "Saved");
        Assert.assertTrue(configPage.getScheduleValidationMessage().contains("Would last have run at"));
        Assert.assertTrue(configPage.getScheduleWarningMessage().contains("Spread load evenly by using"));
    }

    @Test(dependsOnMethods = "testCreate")
    public void testAddPostBuildAction() {
        getWait5().until(ExpectedConditions.elementToBeClickable(By.cssSelector("#job_" + ITEM_NAME + " a"))).click();
        getWait5().until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href$='configure']"))).click();

        By addButton = By.xpath("//button[contains(., 'Add post-build action')]");
        WebElement button = getWait5().until(
                ExpectedConditions.visibilityOfElementLocated(addButton)
        );

        ((JavascriptExecutor) getDriver()).executeScript(
                "arguments[0].scrollIntoView({block: 'center'});", button
        );
        getWait5().until(ExpectedConditions.elementToBeClickable(addButton)).click();

        Assert.assertTrue(
                getWait5().until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//*[normalize-space()='Aggregate downstream test results']")
                )).isDisplayed()
        );
    }
    @Ignore
    @Test(dependsOnMethods = "testCreateProject")
    public void testDisableProject() {

        final String disableText = "This project is currently disabled";
        getDriver().findElement(By.xpath("//span[text()='Elena Freestyle project']")).click();
        getDriver().findElement(By.xpath("//a[contains(@class, 'task-link') and contains(@href, 'configure')]")).click();
        getDriver().findElement(By.cssSelector("label[for='enable-disable-project']")).click();
        getDriver().findElement(By.name("Submit")).click();
        getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.className("permalinks-header")));
        getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.className("warning")));

        String actualText = getDriver().findElement(By.className("warning")).getAttribute("innerText");
        Assert.assertTrue(actualText.contains(disableText));
    }

    @Ignore
    @Test(dependsOnMethods = "testCreateProject")
    public void testEnableProject() {
        getDriver().findElement(By.xpath("//span[text()='Elena Freestyle project']")).click();
        getDriver().findElement(By.xpath("//a[contains(@class, 'task-link') and contains(@href, 'configure')]")).click();
        getDriver().findElement(By.cssSelector("label[for='enable-disable-project']")).click();
        getDriver().findElement(By.name("Submit")).click();
        getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.className("permalinks-header")));
        getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.className("warning")));

        getDriver().findElement(By.name("Submit")).click();

        boolean isWarningGone;
        try {
            isWarningGone = getWait10().until(ExpectedConditions.invisibilityOfElementLocated(By.className("warning")));
        } catch (Exception e) {
            isWarningGone = true;
        }

        Assert.assertTrue(isWarningGone);
    }

    @Ignore
    @Test(dependsOnMethods = "testCreateProject")
    public void testNewItemCopyFrom(){

        getWait10().until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href='/view/all/newJob']"))).click();
        getDriver().findElement(By.id("name")).sendKeys("Copied item");
        getDriver().findElement(By.xpath("//*[@id='duplicate-job']/div[2]")).click();
        getDriver().findElement(By.id("from")).sendKeys(ITEM_NAME);
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();
        getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.className("page-headline")));
        getDriver().findElement(By.className("app-jenkins-logo")).click();

        WebElement copiedProject = getWait10().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//table[@id='projectstatus']//a[normalize-space()='Copied item']")));
        Assert.assertEquals(copiedProject.getText(), "Copied item");
    }

    @Ignore
    @Test
    public void testTriggerBuildsRemotely(){
        getDriver().findElement(By.className("task-link-no-confirm")).click();
        getDriver().findElement(By.id("name")).sendKeys("new item");
        getDriver().findElement(By.xpath("//*[@id='items']/div[2]/label/span")).click();
        getDriver().findElement(By.id("ok-button")).click();

        WebElement checkBox = getDriver().findElement(By.xpath("//input[@name='pseudoRemoteTrigger']/following-sibling::label"));
        ((JavascriptExecutor)getDriver()).executeScript("arguments[0].scrollIntoView({block: 'center'});", checkBox);
        checkBox.click();

        getDriver().findElement(By.name("authToken")).sendKeys("1234");
        getDriver().findElement(By.name("Submit")).click();
        getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.className("page-headline")));
        getDriver().findElement(By.className("app-jenkins-logo")).click();
        getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table[@id='projectstatus']//a[normalize-space()='new item']"))).click();
        getDriver().findElement(By.xpath("//*[@id='tasks']/div[6]/span/a")).click();

        WebElement checkBox2 = getWait10().until(ExpectedConditions.presenceOfElementLocated(By.name("pseudoRemoteTrigger")));
        Assert.assertTrue(checkBox2.isSelected());
    }

    @Ignore
    @Test(dependsOnMethods = "testCreateProject")
    public void testBuildIfAProjectExist(){

        getDriver().findElement(By.className("task-link-no-confirm")).click();
        getDriver().findElement(By.id("name")).sendKeys("new item");
        getDriver().findElement(By.xpath("//span[normalize-space()='Freestyle project']")).click();
        getDriver().findElement(By.id("ok-button")).click();

        WebElement checkbox = getDriver().findElement(By.name("jenkins-triggers-ReverseBuildTrigger"));
        ((JavascriptExecutor)getDriver()).executeScript("arguments[0].scrollIntoView({block: 'center'});", checkbox);
        ((JavascriptExecutor)getDriver()).executeScript("arguments[0].click();", checkbox);

        getDriver().findElement(By.name("_.upstreamProjects")).sendKeys("new item");
        getDriver().findElement(By.name("Submit")).click();
        getWait10().until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(), 'new item')]//ancestor::div//a[@class='app-jenkins-logo']"))).click();
        getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table[@id='projectstatus']//a[normalize-space()='new item']"))).click();
        getDriver().findElement(By.xpath("//a[contains(@href, 'configure')]")).click();

        WebElement checkbox1 = getWait10().until(ExpectedConditions.presenceOfElementLocated(By.name("jenkins-triggers-ReverseBuildTrigger")));
        Assert.assertTrue(checkbox1.isSelected());
    }

    @Ignore
    @Test
    public void testBuildStepsExecuteWindowsBatchCommand(){
        getDriver().findElement(By.cssSelector("#tasks > div:nth-child(1) > span > a")).click();
        getDriver().findElement(By.id("name")).sendKeys("new item");
        getDriver().findElement(By.cssSelector("#items > div.hudson_model_FreeStyleProject.jenkins-choice-list__item > label > span")).click();
        getDriver().findElement(By.id("ok-button")).click();

        WebElement buildStep = getDriver().findElement(By.xpath("//*[@id='main-panel']/form/div[1]/section[5]/div[3]/span/button"));
        ((JavascriptExecutor)getDriver()).executeScript("arguments[0].scrollIntoView({block: 'center'});", buildStep);
        buildStep.click();

        getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#tippy-5 > div > div > div > div.jenkins-dropdown.jenkins-dropdown--compact > button:nth-child(1)"))).click();
        getDriver().findElement(By.className("fixed-width")).sendKeys("any command");
        getDriver().findElement(By.name("Submit")).click();
        getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='tasks']/div[6]/span/a"))).click();

        WebElement buildStep2 = getDriver().findElement(By.className("fixed-width"));
        ((JavascriptExecutor)getDriver()).executeScript("arguments[0].scrollIntoView({block: 'center'});", buildStep2);

        Assert.assertEquals(buildStep2.getText(), "any command");
    }
}
