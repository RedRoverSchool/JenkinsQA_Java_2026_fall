package school.redrover;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

public class PipelineAdvancedConfigurationTest extends BaseTest {
    private static final String PIPELINE_NAME = "Pipeline 1";
    private static final String QP_POSITIVE = "10";
    private static final String QP_NEGATIVE = "-2";
    private static final String QP_NOT_INTEGER = "5.5";
    private static final String NEGATIVE_ERROR = "This value should be larger than 0";
    private static final String NOT_INTEGER_ERROR = "Not an integer";
    private static final String TEXT_UNDER_QUESTION_MARK = "When this option is non-zero, newly triggered builds of this project will be added to the queue, but Jenkins will wait for the specified period of time (in seconds) before actually starting the build.\n" +
            "For example, if your builds take a long time to execute, you may want to prevent multiple source control commits that are made at approximately the same time from triggering multiple builds. Enabling the quiet period would prevent a build from being started as soon as Jenkins discovers the first commit; this would give the developer the chance to push more commits which would be included in the build when it starts. This reduces the size of the queue, meaning that developers would get feedback faster for their series of commits, and the load on the Jenkins system would be reduced.\n" +
            "If a new build of this project is triggered while a build is already sitting in the queue, waiting for its quiet period to end, the quiet period will not be reset. The newly triggered build will not be added to the queue, unless this project is parameterized and the build has different parameters than the build already in the queue.";

    @Test
    public void testQuietPeriodShowsTextUnderQuestionMark() {
        createPipeline();
        openAdvancedDropdown();
        getWait5().until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("a[aria-label='Help for feature: Quiet period'] > span"
                ))).click();
        String textInParagraphs = getWait2().until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("div[class = 'help'] div")
        )).getText();

        Assert.assertEquals(textInParagraphs, TEXT_UNDER_QUESTION_MARK);
    }

    @DataProvider(name = "Display Name")
    public Object[][] displayName() {
        return new Object[][]{
                {"Test Display Name"},
                {"Проект-1"},
                {"')"},
                {"' UNION SELECT username,password FROM users -- "},
                {"<script>alert(1)</script>"},
                {"<h1>test</h1>"},
                {"'><script>alert('XSS')</script>"},
                {"; whoami"},
                {"..\\/..\\/..\\/..\\/etc\\/passwd"},
                {"http://127.0.0.1"},
                {"https://evil.com"},
                {"${7*7}"},
                {"<%= 7*7 %>"}
        };
    }

    @Test(dataProvider = "Display Name")
    public void testDisplayNameSetUp(String displayName) {
        createPipeline();
        openAdvancedDropdown();
        setDisplayName(displayName);
        getDriver().findElement(By.cssSelector("button[name='Submit']")).click();
        getWait2().until(ExpectedConditions.not(
                ExpectedConditions.urlContains("/configure")));
        String displayNameOnPage = getDriver().findElement(By.cssSelector("h1")).getText();

        Assert.assertEquals(displayNameOnPage, displayName.trim());
    }

    @Test
    public void testQuietPeriodAcceptsPositiveValue() {
        createPipeline();
        setQuietPeriod(QP_POSITIVE);
        getDriver().findElement(By.cssSelector("button[name='Submit']")).click();
        getWait5().until(ExpectedConditions.elementToBeClickable(
                        By.cssSelector("a[href$=configure]")))
                .click();
        openAdvancedDropdown();
        String getQuietPeriodSeconds = getDriver().findElement(
                By.cssSelector("input[name='quiet_period']"
                )).getAttribute("value");

        Assert.assertEquals(getQuietPeriodSeconds, QP_POSITIVE);
    }

    @DataProvider(name = "NegativeAndNonIntegerValues")
    public Object[][] negativeAndNonIntegerValues() {
        return new Object[][]{
                {QP_NEGATIVE, NEGATIVE_ERROR},
                {QP_NOT_INTEGER, NOT_INTEGER_ERROR}
        };
    }

    @Test(dataProvider = "NegativeAndNonIntegerValues")
    public void testQuietPeriodRejectsNegativeAndNonIntegerValues(String value, String expectedError) {
        createPipeline();
        setQuietPeriod(value);
        String error = getWait5().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath(
                        "//input[@name='quiet_period']/ancestor::div[contains(@class,'jenkins-form-item')]/descendant::div[@class='error']"
                ))).getText();

        Assert.assertEquals(error, expectedError);
    }

    @DataProvider(name = "InvalidValues")
    public Object[][] invalidValues() {
        return new Object[][]{
                {"AbCdeEГд"},
                {"!@#$%^&*()"},
                {"A512@d"}
        };
    }

    @Test(dataProvider = "InvalidValues")
    public void testQuietPeriodLettersAndSymbolsCanNotBeEntered(String invalidValue) {
        createPipeline();
        setQuietPeriod(invalidValue);
        String input = getDriver().findElement(By.cssSelector("input[name='quiet_period']")).getDomProperty("value");
        String inputExpected = invalidValue.replaceAll("[^0-9]", "");

        Assert.assertEquals(input, inputExpected);
    }

    private void createPipeline() {
        getWait2().until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href='/view/all/newJob']"))).click();
        getWait5().until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("input[value='org.jenkinsci.plugins.workflow.job.WorkflowJob'] + span"
                ))).click();
        getDriver().findElement(By.id("name")).sendKeys(PIPELINE_NAME);
        getDriver().findElement(By.id("ok-button")).click();
    }

    private void openAdvancedDropdown() {
        int maxAttempts = 2;
        for (int attempt = 1; attempt <= maxAttempts; attempt++) {
            getWait5().until(ExpectedConditions.elementToBeClickable(
                            By.xpath("//span[contains(text(),'Advanced')]/ancestor::button")))
                    .click();
            try {
                getWait5().until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".jenkins-bottom-app-bar__shadow--stuck")));
                break;
            } catch (TimeoutException e) {
                if (attempt == maxAttempts) {
                    throw new RuntimeException("После " + maxAttempts + " кликов по боковой панели страница не доскроллилась до низа, Save перекрывает Advanced", e);
                }
            }
        }
        getDriver().findElement(By.cssSelector(".jenkins-section button.advancedButton")).click();
    }

    private void setQuietPeriod(String seconds) {
        openAdvancedDropdown();
        getWait5().until(ExpectedConditions.elementToBeClickable(
                        By.cssSelector("input[name='hasCustomQuietPeriod'] + label")))
                .click();
        WebElement quietPeriodSeconds = getDriver().findElement(By.cssSelector("input[name='quiet_period']"));
        quietPeriodSeconds.clear();
        quietPeriodSeconds.sendKeys(seconds);
        quietPeriodSeconds.sendKeys(Keys.TAB);
    }

    private void setDisplayName(String displayName) {
        WebElement displayNameInput = getWait2().until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("input[checkurl$='checkDisplayNameOrNull']")
        ));
        displayNameInput.sendKeys(displayName, Keys.TAB);
    }
}
