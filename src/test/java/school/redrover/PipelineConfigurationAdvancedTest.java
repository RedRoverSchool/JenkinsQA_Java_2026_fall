package school.redrover;

import org.openqa.selenium.*;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.pages.HomePage;
import school.redrover.pages.PipelineConfigurationPage;

public class PipelineConfigurationAdvancedTest extends BaseTest {
    private static final String PIPELINE_NAME = "Pipeline_testing";
    private static final String QP_POSITIVE = "10";
    private static final String QP_NEGATIVE = "-2";
    private static final String QP_NOT_INTEGER = "5.5";
    private static final String NEGATIVE_ERROR = "This value should be larger than 0";
    private static final String NOT_INTEGER_ERROR = "Not an integer";
    private static final String TEXT_UNDER_QUESTION_MARK = "When this option is non-zero, newly triggered builds of this project will be added to the queue, but Jenkins will wait for the specified period of time (in seconds) before actually starting the build.\n" +
            "For example, if your builds take a long time to execute, you may want to prevent multiple source control commits that are made at approximately the same time from triggering multiple builds. Enabling the quiet period would prevent a build from being started as soon as Jenkins discovers the first commit; this would give the developer the chance to push more commits which would be included in the build when it starts. This reduces the size of the queue, meaning that developers would get feedback faster for their series of commits, and the load on the Jenkins system would be reduced.\n" +
            "If a new build of this project is triggered while a build is already sitting in the queue, waiting for its quiet period to end, the quiet period will not be reset. The newly triggered build will not be added to the queue, unless this project is parameterized and the build has different parameters than the build already in the queue.";
    private static final String TEXT_UNDER_DISPLAY_NAME = "If set, the optional display name is shown for the project throughout the Jenkins web GUI. As it is for display purposes only, the display name is not required to be unique amongst projects.\n" +
            "If the display name is not set, the Jenkins web GUI will default to showing the project name.\n" +
            "(from Pipeline: Job)";
    private static final String DISPLAY_NAME = "Display Name";
    private static final String QUIET_PERIOD = "Quiet period";

    @Test
    public void testQuietPeriodShowsTextUnderQuestionMark() {
        String helpText = new HomePage(getDriver())
                .clickNewItem()
                .inputProjectName(PIPELINE_NAME)
                .selectPipelineAndClickOk()
                .clickOnLeftSideSection(PipelineConfigurationPage.LeftSideSection.ADVANCED)
                .openAdvancedDropdown()
                .clickOnHelpIcon(QUIET_PERIOD)
                .getHelpContent(QUIET_PERIOD).text();

        Assert.assertEquals(helpText, TEXT_UNDER_QUESTION_MARK);
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

    @Test(
            dependsOnMethods = "testQuietPeriodShowsTextUnderQuestionMark",
            dataProvider = "Display Name"
    )
    public void testDisplayNameSetUp(String displayName) {
        String displayNameOnPage = new HomePage(getDriver())
                .openJobsDropDownMenu()
                .clickConfigureInDropDownMenu()
                .clickOnLeftSideSection(PipelineConfigurationPage.LeftSideSection.ADVANCED)
                .openAdvancedDropdown()
                .setDisplayName(displayName)
                .clickSaveButton()
                .getMainHeading();

        Assert.assertEquals(displayNameOnPage, displayName.trim());
    }

    @DataProvider(name = "NegativeAndNonIntegerValues")
    public Object[][] negativeAndNonIntegerValues() {
        return new Object[][]{
                {QP_NEGATIVE, NEGATIVE_ERROR},
                {QP_NOT_INTEGER, NOT_INTEGER_ERROR}
        };
    }

    @Test(
            dependsOnMethods = "testDisplayNameSetUp",
            dataProvider = "NegativeAndNonIntegerValues"
    )
    public void testQuietPeriodRejectsNegativeAndNonIntegerValues(String value, String expectedError) {
        String error = new HomePage(getDriver())
                .openJobsDropDownMenu()
                .clickConfigureInDropDownMenu()
                .clickOnLeftSideSection(PipelineConfigurationPage.LeftSideSection.ADVANCED)
                .openAdvancedDropdown()
                .setQuietPeriod(value)
                .getQuietPeriodError();

        Assert.assertEquals(error, expectedError);
    }

    @DataProvider(name = "InvalidValues")
    public Object[][] invalidValues() {
        return new Object[][]{
                {"AbCdeEГд"},
                {"<>!@#$%^&*()"},
                {"A512@d"}
        };
    }

    @Test(
            dependsOnMethods = "testQuietPeriodRejectsNegativeAndNonIntegerValues",
            dataProvider = "InvalidValues"
    )
    public void testQuietPeriodLettersAndSymbolsCanNotBeEntered(String invalidValue) {
        String input = new HomePage(getDriver())
                .openJobsDropDownMenu()
                .clickConfigureInDropDownMenu()
                .clickOnLeftSideSection(PipelineConfigurationPage.LeftSideSection.ADVANCED)
                .openAdvancedDropdown()
                .setQuietPeriod(invalidValue)
                .getQuietPeriod();

        String inputExpected = invalidValue.replaceAll("[^0-9]", "");

        Assert.assertEquals(input, inputExpected);
    }

    @Test(dependsOnMethods = "testQuietPeriodLettersAndSymbolsCanNotBeEntered")
    public void testDisplayNameShowsTextUnderQuestionMark() {
        PipelineConfigurationPage.HelpContent helpContent = new HomePage(getDriver())
                .openJobsDropDownMenu()
                .clickConfigureInDropDownMenu()
                .clickOnLeftSideSection(PipelineConfigurationPage.LeftSideSection.ADVANCED)
                .openAdvancedDropdown()
                .clickOnHelpIcon(DISPLAY_NAME)
                .getHelpContent(DISPLAY_NAME);

        Assert.assertEquals(helpContent.text(), TEXT_UNDER_DISPLAY_NAME);
        Assert.assertTrue(helpContent.hasLinkContaining("workflow-job"));
    }

    @Test(dependsOnMethods = "testDisplayNameShowsTextUnderQuestionMark")
    public void testQuietPeriodAcceptsPositiveValue() {
        String getQuietPeriodSeconds = new HomePage(getDriver())
                .openJobsDropDownMenu()
                .clickConfigureInDropDownMenu()
                .clickOnLeftSideSection(PipelineConfigurationPage.LeftSideSection.ADVANCED)
                .openAdvancedDropdown()
                .setQuietPeriod(QP_POSITIVE)
                .clickSaveButton()
                .openConfigurationPage()
                .clickOnLeftSideSection(PipelineConfigurationPage.LeftSideSection.ADVANCED)
                .openAdvancedDropdown()
                .getQuietPeriod();

        Assert.assertEquals(getQuietPeriodSeconds, QP_POSITIVE);
    }
}

