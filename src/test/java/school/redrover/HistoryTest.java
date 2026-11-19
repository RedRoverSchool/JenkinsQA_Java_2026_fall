package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.pages.HomePage;

import java.util.List;

public class HistoryTest extends BaseTest {

    private static final String SUCCESS_PIPELINE = "Success";
    private static final String FAIL_PIPELINE = "Fail";

    @Test
    public void testBuildHistoryInformation() {

        List<WebElement> buildInfoPipelineFirst = new HomePage(getDriver())
                .clickNewItem()
                .inputProjectName(SUCCESS_PIPELINE)
                .selectPipelineAndClickOk()
                .clickSaveButton()
                .clickBuildNow()
                .gotoHomePage()
                .clickBuildsHistory()
                .waitForBuildsToComplete()
                .getBuildInfoByName(SUCCESS_PIPELINE);

        Assert.assertEquals(buildInfoPipelineFirst.get(0).getText(), SUCCESS_PIPELINE);
        Assert.assertEquals(buildInfoPipelineFirst.get(2).getText(), "#1");
        Assert.assertFalse(buildInfoPipelineFirst.get(3).getText().isBlank());
        Assert.assertEquals(buildInfoPipelineFirst.get(4).getText(), "stable");
        Assert.assertTrue(buildInfoPipelineFirst.get(5).isDisplayed());

        List<WebElement> buildInfoPipelineSecond = new HomePage(getDriver())
                .clickNewItem()
                .inputProjectName(FAIL_PIPELINE)
                .selectPipelineAndClickOk()
                .gotoHomePage()
                .clickSceduleABuild(FAIL_PIPELINE)
                .clickBuildsHistory()
                .waitForBuildsToComplete()
                .getBuildInfoByName(FAIL_PIPELINE);

        Assert.assertEquals(buildInfoPipelineSecond.get(0).getText(), FAIL_PIPELINE);
        Assert.assertEquals(buildInfoPipelineSecond.get(2).getText(), "#1");
        Assert.assertFalse(buildInfoPipelineSecond.get(3).getText().isBlank());
        Assert.assertEquals(buildInfoPipelineSecond.get(4).getText(), "broken since this build");
        Assert.assertTrue(buildInfoPipelineSecond.get(5).isDisplayed());

        assertBuildOrder(FAIL_PIPELINE, SUCCESS_PIPELINE);
    }

    @Test(dependsOnMethods = "testBuildHistoryInformation")
    public void testSortBuildHistory() {

        new HomePage(getDriver()).clickBuildsHistory();

        assertSorting("Time Since", "↓", SUCCESS_PIPELINE, FAIL_PIPELINE);
        assertSorting("Time Since", "↑", FAIL_PIPELINE, SUCCESS_PIPELINE);

        assertSorting("S", "↑", SUCCESS_PIPELINE, FAIL_PIPELINE);
        assertSorting("S", "↓", FAIL_PIPELINE, SUCCESS_PIPELINE);

        assertSorting("Build", "↑", SUCCESS_PIPELINE, FAIL_PIPELINE);
        assertSorting("Build", "↓", FAIL_PIPELINE, SUCCESS_PIPELINE);

        assertSorting("Status", "↑", SUCCESS_PIPELINE, FAIL_PIPELINE);
        assertSorting("Status", "↓", FAIL_PIPELINE, SUCCESS_PIPELINE);
    }

    private void clickSortHeader(String columnName) {
        getWait10().until(
                ExpectedConditions.visibilityOfElementLocated((
                        By.xpath("//th[.//a[normalize-space(text())='" + columnName + "']]//a"))))
                .click();
    }

    private void assertSortArrow(String columnName, String expectedArrow) {
        WebElement arrow =
                getWait10().until(
                        ExpectedConditions.visibilityOfElementLocated((
                                By.xpath("//th[.//a[normalize-space(text())='" + columnName + "']]//span[@class='sortarrow']"))));

        Assert.assertTrue(arrow.isDisplayed());
        Assert.assertEquals(arrow.getText().trim(), expectedArrow);
    }

    private void assertBuildOrder(String firstBuild, String secondBuild) {
        List<WebElement> builds = getDriver().findElements(
                By.cssSelector("a.jenkins-table__link > span")
        );

        Assert.assertEquals(builds.size(), 2);
        Assert.assertEquals(builds.get(0).getText(), firstBuild);
        Assert.assertEquals(builds.get(1).getText(), secondBuild);
    }

    private void assertSorting(
            String columnName,
            String expectedArrow,
            String firstBuild,
            String secondBuild
    ) {
        clickSortHeader(columnName);
        assertSortArrow(columnName, expectedArrow);
        assertBuildOrder(firstBuild, secondBuild);
    }
}