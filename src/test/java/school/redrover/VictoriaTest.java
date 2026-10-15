package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

import java.time.Duration;
import java.util.List;
import java.util.Locale;

public class VictoriaTest extends BaseTest {

    private final String targetWord = "item";
   @Ignore
    @Test
    public void testSearchReturnsMatchingItems() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));

        String[] createdNames = createItemsAndReturnNames(wait);
        int expectedMatching = countMatching(createdNames);

        getDriver().findElement(By.id("root-action-SearchAction")).click();
        getDriver().findElement(By.id("command-bar")).sendKeys(targetWord);

        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("#search-results a")));

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        List<WebElement> searchResult = getDriver().findElements(By.cssSelector("#search-results a"));

        Assert.assertEquals(searchResult.size(), expectedMatching);

        for (WebElement element : searchResult) {
            Assert.assertTrue(
                    element.getText().toLowerCase(Locale.ROOT).contains(targetWord));
        }
    }

    public String[] createItemsAndReturnNames(WebDriverWait wait) {
        String[] names = {
                "name item 1",
                "test item",
                "test project"
        };

        for (String name : names) {
            createItem(name, wait);
        }
        return names;
    }

    public void createItem(String name, WebDriverWait wait) {

        wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("#tasks :first-child"))).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("#createItem input.jenkins-input"))).sendKeys(name);

        wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("#items :first-child"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("#bottom-sticker button"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(
                By.id("jenkins-head-icon"))).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("#tasks")));
    }

    private int countMatching(String[] names) {
        int count = 0;

        for (String name : names) {
            if (name.toLowerCase(Locale.ROOT).contains(targetWord)) {
                count++;
            }
        }
        return count;
    }
}
