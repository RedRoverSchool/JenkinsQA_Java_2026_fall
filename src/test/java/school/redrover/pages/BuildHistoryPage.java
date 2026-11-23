package school.redrover.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;
import java.util.List;

public class BuildHistoryPage extends BasePage {
    private final By consoleLink = By.cssSelector("a.jenkins-button[href$='/console']");
    private final By consoleOutput = By.id("out");
    private final By consoleTextLink = By.cssSelector("#main-panel > div > div.jenkins-app-bar__controls > a:nth-child(4)");
    private final By downloadConsoleLink = By.cssSelector("#main-panel > div > div.jenkins-app-bar__controls > a:nth-child(1)");
    private final By copyConsoleButton = By.cssSelector("#main-panel > div > div.jenkins-app-bar__controls > span > button");

    public BuildHistoryPage(WebDriver driver) {
        super(driver);
    }

    public BuildHistoryPage clickBuildName(String projectName) {
        getWait2().until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(@data-href, 'job/" + projectName + "/') and contains(@data-href, '/1/')]"))).click();
        return this;
    }

    public DeleteBuildPage deleteBuild() {
        getWait10().until(ExpectedConditions.presenceOfElementLocated(
                By.className("jenkins-dropdown")));
        getWait10().until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("a.jenkins-dropdown__item[href*='confirmDelete']")
        )).click();

        return new DeleteBuildPage(getDriver());
    }

    public boolean buildsIsEmpty(String projectName) {
        List<WebElement> builds = getDriver().findElements(By.xpath("//a[contains(@href, 'job/" + projectName + "/')]"));

        return builds.isEmpty();
    }

    public BuildHistoryPage waitForBuildsToComplete() {
        By buildLinks = By.cssSelector("a.jenkins-table__link > span");
        By completedBuildIcons = By.cssSelector(
                "svg[aria-label='success'], svg[aria-label='failure']"
        );

        getWait10()
                .pollingEvery(Duration.ofSeconds(2))
                .until(driver -> {
                    driver.navigate().refresh();

                    int totalBuilds = driver.findElements(buildLinks).size();
                    int completedBuilds = driver.findElements(completedBuildIcons).size();

                    return totalBuilds > 0 && totalBuilds == completedBuilds;
                });

        return this;
    }

    public List<WebElement> getBuildInfoByName(String projectName) {
        By rowLocator = By.xpath(
                "//tr[.//a[contains(@class, 'jenkins-table__link')]"
                        + "/span[normalize-space(.)='" + projectName + "']]"
        );

        WebElement row = getWait10().until(
                ExpectedConditions.visibilityOfElementLocated(rowLocator)
        );

        return List.of(
                row.findElement(By.cssSelector("a.jenkins-table__link > span")),
                row.findElement(By.cssSelector(
                        "svg[aria-label='success'], svg[aria-label='failure']")),
                row.findElement(By.cssSelector("a.jenkins-badge")),
                row.findElement(By.cssSelector("td[data*='T']")),
                row.findElement(By.xpath(
                        "./td[normalize-space(.)='stable' "
                                + "or normalize-space(.)='broken since this build']"
                )),
                row.findElement(By.cssSelector("a[href$='/console']"))
        );
    }

    public BuildHistoryPage openConsole() {
        getWait10().until(ExpectedConditions.visibilityOfElementLocated(consoleLink)).click();

        return this;
    }

    public boolean isConsoleOutputIsDisplayed() {
        return getWait2().until(ExpectedConditions.visibilityOfElementLocated(consoleOutput)).isDisplayed();
    }

    public String getConsoleOutput() {
        return getWait5().until(ExpectedConditions.visibilityOfElementLocated(consoleOutput)).getText();
    }

    public BuildHistoryPage clickConsoleTextLink() {
        getWait5().until(ExpectedConditions.elementToBeClickable(consoleTextLink)).click();
        getWait2().until(ExpectedConditions.urlContains("consoleText"));

        return this;
    }

    public String getDownloadHistoryLink() {
        return getWait5().until(ExpectedConditions.elementToBeClickable(downloadConsoleLink)).getAttribute("href");

    }

    public BuildHistoryPage clickCopyButton() {
        getWait5().until(
                ExpectedConditions.elementToBeClickable(copyConsoleButton)).click();
        return this;
    }

    public boolean isCopyButtonCopied() {
        return getWait5().until(driver ->
                getDriver().findElement(copyConsoleButton)
                        .getAttribute("class")
                        .contains("jenkins-copy-button--copied"));
    }
}
