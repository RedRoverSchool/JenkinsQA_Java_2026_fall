package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.WebElement;
import java.util.List;

public class JenkinsPluginsUpdateTest extends BaseTest {

    private void openPluginsPage() {
        getDriver().findElement(By.id("root-action-ManageJenkinsAction")).click();
        getWait2().until(ExpectedConditions.urlContains("/manage/"));
        getDriver().findElement(By.cssSelector("a[href*='pluginManager']")).click();
        getWait2().until(ExpectedConditions.urlContains("/manage/pluginManager/"));
    }

    @Test
    public void OpenPluginsSection() {
        openPluginsPage();

        WebElement pluginsHeader = getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h1, h2")));

        Assert.assertTrue(pluginsHeader.isDisplayed());
    }

    @Test
    public void OpenPluginDoc() {
        openPluginsPage();

        List<WebElement> pluginDocumentationLinks = getWait2().until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(
                        By.cssSelector("a[href*='https://plugins.jenkins.io/']")
                )
        );

        WebElement firstPluginLink = pluginDocumentationLinks.get(0);
        String expectedUrlPart = "plugins.jenkins.io";
        String originalWindow = getDriver().getWindowHandle();
        firstPluginLink.click();
        getWait2().until(ExpectedConditions.numberOfWindowsToBe(2));
        String documentationWindow = null;

        for (String windowHandle : getDriver().getWindowHandles()) {
            if (!windowHandle.equals(originalWindow)) {
                documentationWindow = windowHandle;
                break;
            }
        }

        getDriver().switchTo().window(documentationWindow);
        getWait2().until(ExpectedConditions.urlContains(expectedUrlPart));

        Assert.assertTrue(getDriver().getCurrentUrl().contains("https://plugins.jenkins.io/"));
    }

    @Test
    public void SearchField() {
        openPluginsPage();
        getDriver().findElement(By.id("filter-box")).sendKeys("Git");
        By pluginLinkLocator = By.cssSelector("a[href='https://plugins.jenkins.io/git-client']");
        WebElement searchField = getWait2().until(ExpectedConditions.visibilityOfElementLocated(pluginLinkLocator));

        Assert.assertTrue(searchField.isDisplayed());
    }
}
