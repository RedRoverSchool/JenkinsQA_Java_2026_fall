package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

import java.time.Duration;


public class MultiConfigurationProjectTest extends BaseTest {

    private static final By NEW_JOB = By.cssSelector("a.task-link[href*='newJob']");
    private static final By PROJECT_NAME = By.cssSelector("#name");
    private static final By PROJECT_TYPE = By.xpath("//label[input[@value='hudson.matrix.MatrixProject']]");
    private static final By OK_BUTTON = By.cssSelector("#ok-button");
    private static final By JENKINS_LOGO = By.cssSelector("a.app-jenkins-logo");
    private static final By SUBMIT_BUTTON = By.cssSelector("button[name='Submit']");
    private static final String JOB_NAME = "MultiConfigDescProject";
    private static final String DESCRIPTION_TEXT = "This is an automated test description.";


    WebDriverWait wait10;
    WebDriverWait wait30 = new WebDriverWait(getDriver(), Duration.ofSeconds(30));



    @Test
    public void testCreateMultiConfigurationProject() {

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(30));

        wait.until(ExpectedConditions.elementToBeClickable(NEW_JOB)).click();
        //wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".jenkins-form, .app-page-body, form")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(PROJECT_NAME)).sendKeys("MultiConfigurationProject");
        wait.until(ExpectedConditions.presenceOfElementLocated(PROJECT_TYPE)).click();
        wait.until(ExpectedConditions.elementToBeClickable(OK_BUTTON)).click();
        wait.until(ExpectedConditions.elementToBeClickable(JENKINS_LOGO)).click();
        WebElement itemLink = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("a.jenkins-table__link[href^='job/']")));

        Assert.assertTrue(itemLink.getText().contains("MultiConfigurationProject"));
    }

    @Test
    public void testEnableOrDisableProject() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(30));
        wait.until(ExpectedConditions.elementToBeClickable(NEW_JOB)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".jenkins-form, .app-page-body, form")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(PROJECT_NAME)).sendKeys("MultiConfigurationProject");
        wait.until(ExpectedConditions.presenceOfElementLocated(PROJECT_TYPE)).click();
        wait.until(ExpectedConditions.elementToBeClickable(OK_BUTTON)).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("label[for='enable-disable-project']"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(SUBMIT_BUTTON)).click();

        WebElement alertForm = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("form#enable-project")));

        Assert.assertTrue(alertForm.getText().contains("This project is currently disabled"));
    }

    @Test
    public void testSetProjectDescription() {
        //WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        //final String projectName = "MultiConfigDescProject";
        //final String descriptionText = "This is an automated test description.";
        wait10= new WebDriverWait(getDriver(), Duration.ofSeconds(10));

        wait10.until(ExpectedConditions.elementToBeClickable(NEW_JOB)).click();
        wait10.until(ExpectedConditions.visibilityOfElementLocated(PROJECT_NAME)).sendKeys(JOB_NAME);
        wait10.until(ExpectedConditions.presenceOfElementLocated(PROJECT_TYPE)).click();
        wait10.until(ExpectedConditions.elementToBeClickable(OK_BUTTON)).click();

        WebElement descriptionField = wait10.until(ExpectedConditions.visibilityOfElementLocated(By.name("description")));
        descriptionField.sendKeys(DESCRIPTION_TEXT);

        wait10.until(ExpectedConditions.elementToBeClickable(SUBMIT_BUTTON)).click();

        WebElement actualDescription = wait10.until(ExpectedConditions.visibilityOfElementLocated(By.id("description")));

        Assert.assertTrue(actualDescription.getText().contains(DESCRIPTION_TEXT),
                "Описание проекта не отобразилось!");
    }

    @Test
    public void testConfigurationMatrixAddAxis(){
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(20));

        wait.until(ExpectedConditions.elementToBeClickable(NEW_JOB)).click();
        //wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".jenkins-form, .app-page-body, form")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(PROJECT_NAME)).sendKeys("MultiConfigurationProject");
        wait.until(ExpectedConditions.presenceOfElementLocated(PROJECT_TYPE)).click();
        wait.until(ExpectedConditions.elementToBeClickable(OK_BUTTON)).click();
        //wait.until(ExpectedConditions.textToBePresentInElementLocated(By.tagName("h1"),"Configure"));
        //wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[.//span[normalize-space(.)='Configuration Matrix']]"))).click();
        //wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[suffix='axis']"))).click();
        WebElement axis= getDriver().findElement(By.cssSelector("button[suffix='axis']"));
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView({block: 'center'});", axis);
        axis.click();
        //axis.findElement(By.xpath("//button[contains(.,'User-defined Axis')]")).click();
        getDriver().findElement(By.xpath("//button[contains(.,'User-defined Axis')]")).click();
        getDriver().findElement(By.cssSelector("input[checkurl*='hudson.matrix.TextAxis'][name ='_.name']")).sendKeys("DB");
        getDriver().findElement(By.cssSelector("input#textarea\\._\\.valueString")).sendKeys("MongoBD SQL");
        //getDriver().findElement(SUBMIT_BUTTON).click();
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView({block: 'center'});", axis);
        axis.click();
        getDriver().findElement(By.xpath("//button[contains(.,'User-defined Axis')]")).click();
        getDriver().findElements(By.cssSelector("input[checkurl*='hudson.matrix.TextAxis'][name='_.name']"))
                .get(1)
                .sendKeys("OS");
        //getDriver().findElement(By.cssSelector("input[checkurl*='hudson.matrix.TextAxis'][name ='_.name']")).sendKeys("OS");
        getDriver().findElements(By.cssSelector("input#textarea\\._\\.valueString")).get(1).sendKeys("Win Linux");
        getDriver().findElement(SUBMIT_BUTTON).click();



    }
}
