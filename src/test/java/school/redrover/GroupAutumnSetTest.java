package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class GroupAutumnSetTest extends BaseTest {

    @Test
    public void testCreateDescription () {
        getDriver().findElement(By.id("description-link")).click();
        getDriver().findElement(By.id("description-textarea")).sendKeys("Jenkins");
        getDriver().findElement(By.cssSelector("button[data-id='ok']")).click();

        Assert.assertEquals(getDriver().findElement(By.cssSelector("span.jenkins-mobile-hide")).getText(),"Jenkins");
    }

    @Test
    public void testCreateFolder() {

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(20));
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.task-link[href*='newJob']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".jenkins-form, .app-page-body, form")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#name"))).sendKeys("My new folder");

        WebElement folderRadio = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[value='com.cloudbees.hudson.plugins.folder.Folder']")));
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", folderRadio);

        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#ok-button"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.app-jenkins-logo"))).click();
        WebElement itemLink = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("a.jenkins-table__link[href^='job/']")));

        Assert.assertTrue(itemLink.getText().contains("My new folder"));
    }

    @Test
    public void testProfileMenuItemsSimple() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        WebElement profileIcon = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("root-action-UserAction")));

        Actions actions = new Actions(getDriver());
        actions.moveToElement(profileIcon).perform();

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<WebElement> allLinks = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("a.jenkins-dropdown__item")));

        List<String> actualItems = new ArrayList<>();
        for (WebElement link : allLinks) {
            String href = link.getAttribute("href");
            String text = link.getText().trim();
            if (href != null && href.endsWith("/user/admin")) {
                continue;
            }
            if (!text.isEmpty()) {
                actualItems.add(text);
            }
        }
        List<String> expectedItems = List.of(
                "My Views",
                "Account",
                "Appearance",
                "Preferences",
                "Security",
                "Experiments",
                "Credentials",
                "Sign out"
        );

        Assert.assertTrue(actualItems.containsAll(expectedItems));
    }

}
