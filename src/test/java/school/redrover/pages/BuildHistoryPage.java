package school.redrover.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;
import java.util.List;

public class BuildHistoryPage extends BasePage{

    public BuildHistoryPage(WebDriver driver) {super(driver);}

    public BuildHistoryPage clickBuildName(String projectName){
        getWait10().until(ExpectedConditions.elementToBeClickable(By.xpath( "//button[contains(@data-href, 'job/" + projectName + "/') and contains(@data-href, '/1/')]"))).click();
        return this;
    }

    public DeleteBuildPage deleteBuild(){
        getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(@href, 'confirmDelete')]"))).click();

        return new DeleteBuildPage(getDriver());
    }

    public boolean buildsIsEmpty(String projectName){
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
}
