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

    private void createPipeline(String name) {

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
        getDriver().findElement(By.className("task-icon-link")).click();
        getDriver().findElement(By.id("name")).sendKeys(name);
        getDriver().findElement(By.className("jenkins-choice-list__item__label")).click();
        getDriver().findElement(By.id("ok-button")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated((By.id("jenkins-head-icon"))));
        wait.until(ExpectedConditions.elementToBeClickable((By.id("jenkins-head-icon")))).click();
    }

}
