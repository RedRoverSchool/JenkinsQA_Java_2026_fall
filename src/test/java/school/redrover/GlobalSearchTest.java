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
    private static final String MODIFY_SEARCH_WORD = "ends";
    private static final String[] ITEMS_TO_CREATE = {
            "has item inside",
            "ends with item",
            "no match here"
    };
    private static final int WAIT_TIMEOUT_SECONDS = 5;

    private void createItem(String itemName) {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(WAIT_TIMEOUT_SECONDS));

        wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("#tasks :first-child"))).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("#createItem input.jenkins-input"))).sendKeys(itemName);

        wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("#items :first-child"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("#bottom-sticker button"))).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("form .config-table")));

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

    private int countMatchingItems(String[] itemNames, String searchWord) {
        int count = 0;

        for (String name : itemNames) {
            if (name.toLowerCase(Locale.ROOT).contains(searchWord)) {
                count++;
            }
        }

        return count;
    }

    @Test
    public void testPartialNameAndNavigate() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(WAIT_TIMEOUT_SECONDS));

        createItems(ITEMS_TO_CREATE);
        int expectedMatching = countMatchingItems(ITEMS_TO_CREATE, SEARCH_WORD);

        wait.until(ExpectedConditions.elementToBeClickable(By.id("root-action-SearchAction"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("command-bar"))).sendKeys(SEARCH_WORD);

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

    @Test
    public void testModifyQuery() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(WAIT_TIMEOUT_SECONDS));

        createItems(ITEMS_TO_CREATE);
        int expectedMatching = countMatchingItems(ITEMS_TO_CREATE, SEARCH_WORD);

        wait.until(ExpectedConditions.elementToBeClickable(By.id("root-action-SearchAction"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("command-bar"))).sendKeys(SEARCH_WORD);

        wait.until(ExpectedConditions.numberOfElementsToBe(
                By.cssSelector("#search-results a"), expectedMatching));

        List<WebElement> searchResults = getDriver().findElements(By.cssSelector("#search-results a"));

        Assert.assertEquals(searchResults.size(), expectedMatching);

        for (WebElement result : searchResults) {
            Assert.assertTrue(
                    result.getText().toLowerCase(Locale.ROOT).contains(SEARCH_WORD));
        }

        WebElement searchInput = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("command-bar")));
        searchInput.clear();
        searchInput.sendKeys(MODIFY_SEARCH_WORD);

        expectedMatching = countMatchingItems(ITEMS_TO_CREATE, MODIFY_SEARCH_WORD);

        wait.until(ExpectedConditions.numberOfElementsToBe(
                By.cssSelector("#search-results a"), expectedMatching));

        List<WebElement> modifySearchResults = getDriver().findElements(By.cssSelector("#search-results a"));

        Assert.assertEquals(modifySearchResults.size(), expectedMatching);

        for (WebElement result : modifySearchResults) {
            Assert.assertTrue(
                    result.getText().toLowerCase(Locale.ROOT).contains(MODIFY_SEARCH_WORD));
        }
    }

    @Test
    public void testLongQuery() {
        final String longQuery = "a".repeat(300);
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(WAIT_TIMEOUT_SECONDS));

        wait.until(ExpectedConditions.elementToBeClickable(By.id("root-action-SearchAction"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("command-bar"))).sendKeys(longQuery);

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("#search-results span")));

        Assert.assertTrue(getDriver().findElement(By.id("command-bar")).isDisplayed());
        Assert.assertTrue(getDriver().findElement(
                By.cssSelector("#search-results span")).getText().contains("No results for"));
    }

    @Test
    public void testEmptyQuery() {
        boolean hasHelpLink = false;
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(WAIT_TIMEOUT_SECONDS));

        createItems(ITEMS_TO_CREATE);

        wait.until(ExpectedConditions.elementToBeClickable(By.id("root-action-SearchAction"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("command-bar")));

        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#search-results")));

        List<WebElement> searchResults = getDriver().findElements(By.cssSelector("#search-results a"));

        for (WebElement result : searchResults) {
            if (result.getText().contains("Get help using Jenkins search")) {
                hasHelpLink = true;
                break;
            }
        }

        Assert.assertTrue(hasHelpLink);

        for (WebElement result : searchResults) {
            String resultText = result.getText().toLowerCase();
            for (String item : ITEMS_TO_CREATE) {
                Assert.assertFalse(resultText.contains(item.toLowerCase()));
            }
        }
    }
}