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
import java.util.Locale;

public class GlobalSearchTest extends BaseTest {

    private static final String SEARCH_WORD = "item";
    private static final String[] ITEMS_TO_CREATE = {
            "has item inside",
            "ends with item",
            "no match here"
    };

    private void createItem(String itemName) {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));

        wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("#tasks :first-child"))).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("#createItem input.jenkins-input"))).sendKeys(itemName);

        wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("#items :first-child"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("#bottom-sticker button"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(
                By.id("jenkins-head-icon"))).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("#tasks")));
    }

    private void createItems(String[] items) {
        for (String itemName : items) {
            createItem(itemName);
        }
    }

    private int countMatchingItems(String[] itemNames) {
        int count = 0;

        for (String name : itemNames) {
            if (name.toLowerCase(Locale.ROOT).contains(SEARCH_WORD)) {
                count++;
            }
        }
        return count;
    }

    @Test
    public void testSearchByPartialNameAndNavigate() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));

        createItems(ITEMS_TO_CREATE);
        int expectedMatching = countMatchingItems(ITEMS_TO_CREATE);

        getDriver().findElement(By.id("root-action-SearchAction")).click();
        getDriver().findElement(By.id("command-bar")).sendKeys(SEARCH_WORD);

        wait.until(ExpectedConditions.numberOfElementsToBe(
                By.cssSelector("#search-results a"), expectedMatching));

        List<WebElement> searchResults = getDriver().findElements(By.cssSelector("#search-results a"));

        Assert.assertEquals(searchResults.size(), expectedMatching);

        for (WebElement result : searchResults) {
            Assert.assertTrue(
                    result.getText().toLowerCase(Locale.ROOT).contains(SEARCH_WORD));
        }

        String clickedItemText = searchResults.getFirst().getText();
        searchResults.getFirst().click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#main-panel h1")));

        Assert.assertTrue(
                getDriver().findElement(By.cssSelector("#main-panel h1")).getText().contains(clickedItemText));
    }
}
