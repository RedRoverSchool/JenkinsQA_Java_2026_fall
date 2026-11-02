package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

import java.time.Duration;
import java.util.List;

public class HistoryTest extends BaseTest {

    private static final String PIPELINE_FIRST_NAME = "Pipeline1";
    private static final String PIPELINE_SECOND_NAME = "Pipeline2";
    private static final String SUCCESS_PIPELINE = "Success";
    private static final String FAIL_PIPELINE = "Fail";


    @Test
    public void testBuildHistoryInformation() {

        createPipeline(PIPELINE_FIRST_NAME);
        getDriver().findElement(
                By.cssSelector(String.format("a[title='Schedule a Build for %s']", PIPELINE_FIRST_NAME)))
                .click();
        createPipeline(PIPELINE_SECOND_NAME);
        getDriver().findElement(
                        By.cssSelector(String.format("a[title='Schedule a Build for %s']", PIPELINE_SECOND_NAME)))
                .click();

        getDriver().findElement(By.xpath("//a[@href='/view/all/builds']")).click();

        By failedBuildIcons = By.cssSelector("svg[aria-label='failure']");
        new WebDriverWait(getDriver(), Duration.ofSeconds(20))
                .until(ExpectedConditions.numberOfElementsToBe(failedBuildIcons, 2));

        List<WebElement> builds = getDriver().findElements(
                By.cssSelector("a.jenkins-table__link > span")
        );

        List<WebElement> buildNumbers = getDriver().findElements(
                By.cssSelector(String.format(
                        "a.jenkins-badge[href='/job/%s/1/'], a.jenkins-badge[href='/job/%s/1/']",
                        PIPELINE_FIRST_NAME, PIPELINE_SECOND_NAME
                ))
        );

        List<WebElement> buildTimes = getDriver().findElements(
                By.cssSelector("td[data*='T']")
        );

        List<WebElement> buildStatuses = getDriver().findElements(
                By.cssSelector("td[style*='var(--red)']")
        );

        List<WebElement> consoleOutputLinks = getDriver().findElements(
                By.cssSelector(String.format(
                        "a[href='/job/%s/1/console'], a[href='/job/%s/1/console']",
                        PIPELINE_FIRST_NAME, PIPELINE_SECOND_NAME
                ))
        );

        Assert.assertEquals(builds.size(), 2);
        Assert.assertEquals(builds.get(0).getText(), PIPELINE_SECOND_NAME);
        Assert.assertEquals(builds.get(1).getText(), PIPELINE_FIRST_NAME);
        Assert.assertEquals(getDriver().findElements(failedBuildIcons).size(), 2);
        Assert.assertEquals(buildNumbers.size(), 2);
        Assert.assertEquals(buildNumbers.get(0).getText(), "#1");
        Assert.assertEquals(buildNumbers.get(1).getText(), "#1");
        Assert.assertEquals(buildTimes.size(), 2);
        for (WebElement buildTime : buildTimes) {
            Assert.assertTrue(buildTime.isDisplayed());
            Assert.assertFalse(buildTime.getText().isBlank());
        }
        Assert.assertEquals(buildStatuses.size(), 2);
        for (WebElement status : buildStatuses) {
            Assert.assertEquals(status.getText(), "broken since this build");
        }
        for (WebElement link : consoleOutputLinks) {
            Assert.assertTrue(link.isDisplayed());
        }
    }

    @Test
    public void testSortBuildHistory() {
        createPipelineAndBuildSuccess(SUCCESS_PIPELINE);
        createPipeline(FAIL_PIPELINE);

        getDriver().findElement(
                By.cssSelector(String.format(
                        "a[title='Schedule a Build for %s']",
                        FAIL_PIPELINE))
        ).click();

        getDriver().findElement(
                By.cssSelector("a[href='/view/all/builds']")
        ).click();

        assertSortArrow("Прошло", "↑");
        assertBuildOrder(FAIL_PIPELINE, SUCCESS_PIPELINE);

        assertSorting("Прошло", "↓", SUCCESS_PIPELINE, FAIL_PIPELINE);

        assertSorting("S", "↑", SUCCESS_PIPELINE, FAIL_PIPELINE);
        assertSorting("S", "↓", FAIL_PIPELINE, SUCCESS_PIPELINE);

        assertSorting("Сборка", "↑", SUCCESS_PIPELINE, FAIL_PIPELINE);
        assertSorting("Сборка", "↓", FAIL_PIPELINE, SUCCESS_PIPELINE);

        assertSorting("Статус", "↑", SUCCESS_PIPELINE, FAIL_PIPELINE);
        assertSorting("Статус", "↓", FAIL_PIPELINE, SUCCESS_PIPELINE);
    }

    private void createPipeline(String name) {
        getDriver().findElement(By.className("task-icon-link")).click();
        getDriver().findElement(By.id("name")).sendKeys(name);
        getDriver().findElement(By.className("jenkins-choice-list__item__label")).click();
        getDriver().findElement(By.id("ok-button")).click();

        getWait10().until(ExpectedConditions.visibilityOfElementLocated((By.name("description"))));
        getDriver().findElement((By.id("jenkins-head-icon"))).click();
    }

    private void createPipelineAndBuildSuccess(String name) {

        getDriver().findElement(By.className("task-icon-link")).click();
        getDriver().findElement(By.id("name")).sendKeys(name);
        getDriver().findElement(By.className("jenkins-choice-list__item__label")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.cssSelector("button[name='Submit']")).click();
        getDriver().findElement(
                By.cssSelector("a[href='/job/" + name + "/build?delay=0sec']")
        ).click();

        getDriver().findElement((By.id("jenkins-head-icon"))).click();
    }

    private void clickSortHeader(String columnName) {
        getDriver().findElement(
                By.xpath("//th[.//a[normalize-space(text())='" + columnName + "']]//a")
        ).click();
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