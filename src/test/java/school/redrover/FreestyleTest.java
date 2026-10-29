package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import org.openqa.selenium.WebElement;

public class FreestyleTest extends BaseTest {

    private void createProject() {
        final String itemName = "Elena Freestyle project";

        getDriver().findElement(By.xpath("//*[@id=\"tasks\"]/div[1]/span/a")).click();
        getDriver().findElement(By.xpath("//*[@id=\"name\"]")).sendKeys(itemName);
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.id("ok-button")).click();
        WebElement logo = new WebDriverWait(getDriver(), Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(), 'Configure')]//ancestor::div//a[@class='app-jenkins-logo']")));
        logo.click();
    }

    public void disableProject() {
        getDriver().findElement(By.xpath("//span[text()='Elena Freestyle project']")).click();
        getDriver().findElement(By.xpath("//a[contains(@class, 'task-link') and contains(@href, 'configure')]")).click();
        getDriver().findElement(By.cssSelector("label[for='enable-disable-project']")).click();
        getDriver().findElement(By.name("Submit")).click();

        new WebDriverWait(getDriver(), Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.className("warning")));
    }

    @Test
    public void testCreateProject() {
        final String itemName = "Elena Freestyle project";
        createProject();

        Assert.assertEquals(getDriver().findElement(By.xpath("//*[@id=\"job_Elena Freestyle project\"]/td[3]/a")).getText(),
                itemName);
    }

    @Test
    public void testDisableProject() {

        final String disableText = "This project is currently disabled";
        createProject();
        disableProject();

        String actualText = getDriver().findElement(By.className("warning")).getAttribute("innerText");

        Assert.assertTrue(actualText.contains(disableText));
    }

    @Test
    public void testEnableProject() {
        createProject();
        disableProject();

        getDriver().findElement(By.name("Submit")).click();

        boolean isWarningGone;
        try {
            isWarningGone = new WebDriverWait(getDriver(), Duration.ofSeconds(10))
                    .until(ExpectedConditions.invisibilityOfElementLocated(By.className("warning")));
        } catch (Exception e) {
            isWarningGone = true;
        }

        Assert.assertTrue(isWarningGone);
    }

    @Test
    public void testNewItemCopyFrom(){
        createProject();
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));

        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href='/view/all/newJob']"))).click();

        getDriver().findElement(By.id("name")).sendKeys("Copied item");
        getDriver().findElement(By.xpath("//*[@id=\"duplicate-job\"]/div[2]")).click();
        getDriver().findElement(By.id("from")).sendKeys("Elena Freestyle project");
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(), 'Copied item')]//ancestor::div//a[@class='app-jenkins-logo']"))).click();

        WebElement copiedProject = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//table[@id='projectstatus']//a[normalize-space()='Copied item']")
        ));

        Assert.assertEquals(copiedProject.getText(), "Copied item");
    }

    @Test
    public void testTriggerBuildsRemotely(){
        getDriver().findElement(By.className("task-link-no-confirm")).click();
        getDriver().findElement(By.id("name")).sendKeys("new item");
        getDriver().findElement(By.xpath("//*[@id=\"items\"]/div[2]/label/span")).click();
        getDriver().findElement(By.id("ok-button")).click();

        WebElement checkBox = getDriver().findElement(By.xpath("//input[@name='pseudoRemoteTrigger']/following-sibling::label"));
        ((JavascriptExecutor)getDriver()).executeScript("arguments[0].scrollIntoView({block: 'center'});", checkBox);
        checkBox.click();

        getDriver().findElement(By.name("authToken")).sendKeys("1234");
        getDriver().findElement(By.name("Submit")).click();

        WebElement logo = new WebDriverWait(getDriver(), Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(), 'new item')]//ancestor::div//a[@class='app-jenkins-logo']")));
        logo.click();
        WebElement newProject = new WebDriverWait(getDriver(),Duration.ofSeconds(10)).
                until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table[@id='projectstatus']//a[normalize-space()='new item']")));
        newProject.click();
        getDriver().findElement(By.xpath("//*[@id=\"tasks\"]/div[6]/span/a")).click();

        WebElement checkBox2 = new WebDriverWait(getDriver(), Duration.ofSeconds(10)).
                until(ExpectedConditions.presenceOfElementLocated(By.name("pseudoRemoteTrigger")));

        Assert.assertTrue(checkBox2.isSelected());
    }

    @Test
    public void testBuildIfAProjectExist(){
        createProject();

        getDriver().findElement(By.className("task-link-no-confirm")).click();
        getDriver().findElement(By.id("name")).sendKeys("new item");
        getDriver().findElement(By.xpath("//span[normalize-space()='Freestyle project']")).click();
        getDriver().findElement(By.id("ok-button")).click();

        WebElement checkbox = getDriver().findElement(By.name("jenkins-triggers-ReverseBuildTrigger"));
        ((JavascriptExecutor)getDriver()).executeScript("arguments[0].scrollIntoView({block: 'center'});", checkbox);
        ((JavascriptExecutor)getDriver()).executeScript("arguments[0].click();", checkbox);

        getDriver().findElement(By.name("_.upstreamProjects")).sendKeys("new item");
        getDriver().findElement(By.name("Submit")).click();

        WebElement logo = new WebDriverWait(getDriver(), Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(), 'new item')]//ancestor::div//a[@class='app-jenkins-logo']")));
        logo.click();

        WebElement newProject = new WebDriverWait(getDriver(),Duration.ofSeconds(10)).
                until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table[@id='projectstatus']//a[normalize-space()='new item']")));
        newProject.click();
        getDriver().findElement(By.xpath("//a[contains(@href, 'configure')]")).click();

        WebElement checkbox1 = new WebDriverWait(getDriver(), Duration.ofSeconds(10)).
                until(ExpectedConditions.presenceOfElementLocated(By.name("jenkins-triggers-ReverseBuildTrigger")));

        Assert.assertTrue(checkbox1.isSelected());
    }

    @Test
    public void testBuildStepsExecuteWindowsBatchCommand(){
        getDriver().findElement(By.cssSelector("#tasks > div:nth-child(1) > span > a")).click();
        getDriver().findElement(By.id("name")).sendKeys("new item");
        getDriver().findElement(By.cssSelector("#items > div.hudson_model_FreeStyleProject.jenkins-choice-list__item > label > span")).click();
        getDriver().findElement(By.id("ok-button")).click();

        WebElement buildStep = getDriver().findElement(By.xpath("//*[@id=\"main-panel\"]/form/div[1]/section[5]/div[3]/span/button"));
        ((JavascriptExecutor)getDriver()).executeScript("arguments[0].scrollIntoView({block: 'center'});", buildStep);
        buildStep.click();

        WebElement buttonOne = new WebDriverWait(getDriver(),Duration.ofSeconds(10)).
                until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#tippy-5 > div > div > div > div.jenkins-dropdown.jenkins-dropdown--compact > button:nth-child(1)")));
        buttonOne.click();
        getDriver().findElement(By.className("fixed-width")).sendKeys("any command");

        getDriver().findElement(By.name("Submit")).click();

        WebElement configureButton = new WebDriverWait(getDriver(),Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"tasks\"]/div[6]/span/a")));
        configureButton.click();

        WebElement buildStep2 = getDriver().findElement(By.className("fixed-width"));
        ((JavascriptExecutor)getDriver()).executeScript("arguments[0].scrollIntoView({block: 'center'});", buildStep2);

        Assert.assertEquals(buildStep2.getText(), "any command");
    }
}
