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
import java.util.List;


public class MultiConfigurationProjectTest extends BaseTest {

    private static final By NEW_JOB = By.cssSelector("a.task-link[href*='newJob']");
    private static final By PROJECT_NAME = By.cssSelector("#name");
    private static final By PROJECT_TYPE = By.xpath("//label[input[@value='hudson.matrix.MatrixProject']]");
    private static final By OK_BUTTON = By.cssSelector("#ok-button");
    private static final By JENKINS_LOGO = By.cssSelector("a.app-jenkins-logo");
    private static final By SUBMIT_BUTTON = By.cssSelector("button[name='Submit']");
    private static final By ADD_AXIS = By.cssSelector("button[suffix='axis']");
    private static final By USER_DEFINED_AXIS = By.xpath("//button[contains(.,'User-defined Axis')]");
    private static final By AXIS_VALUES_FIELD =By.cssSelector("input#textarea\\._\\.valueString");
    private static final By AXIS_NANE_FIELD = By.cssSelector("input[checkurl*='hudson.matrix.TextAxis'][name ='_.name']");
    private static final String JOB_NAME = "MultiConfigDescProject";
    private static final String DESCRIPTION_TEXT = "This is an automated test description.";


    WebDriverWait wait10;
    WebDriverWait wait30 = new WebDriverWait(getDriver(), Duration.ofSeconds(30));



    @Test
    public void testCreateMultiConfigurationProject() {

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(30));

        wait.until(ExpectedConditions.elementToBeClickable(NEW_JOB)).click();
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
        wait.until(ExpectedConditions.visibilityOfElementLocated(PROJECT_NAME)).sendKeys("MultiConfigurationProject");
        wait.until(ExpectedConditions.presenceOfElementLocated(PROJECT_TYPE)).click();
        wait.until(ExpectedConditions.elementToBeClickable(OK_BUTTON)).click();

        WebElement axis = getDriver().findElement(ADD_AXIS);
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView({block: 'center'});", axis);
        axis.click();
        getDriver().findElement(USER_DEFINED_AXIS).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(AXIS_NANE_FIELD)).sendKeys("DB");
        getDriver().findElement(AXIS_VALUES_FIELD).sendKeys("MongoBD SQL");

        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView({block: 'center'});", axis);
        axis.click();
        getDriver().findElement(USER_DEFINED_AXIS).click();
        getDriver().findElements(AXIS_NANE_FIELD)
                .get(1)
                .sendKeys("OS");
        getDriver().findElements(AXIS_VALUES_FIELD).get(1).sendKeys("Win Linux");
        getDriver().findElement(SUBMIT_BUTTON).click();

        List<String> columns= getDriver().findElements(By.cssSelector("#matrix thead td.mp-header span")).stream().map(WebElement::getText).toList();
        Assert.assertEquals(List.of("MongoBD", "SQL"), columns);
    }
}

