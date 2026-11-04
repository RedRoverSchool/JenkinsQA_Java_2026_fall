package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

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

    private void createItem(String itemName) {
        getDriver().findElement(By.cssSelector("#tasks :first-child")).click();
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(
                By.id("add-item-panel")));

        getDriver().findElement(By.id("name")).sendKeys(itemName);
        getDriver().findElement(By.cssSelector("#items :first-child")).click();

        getWait5().until(ExpectedConditions.elementToBeClickable(
                By.id("ok-button"))).click();
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("form .config-table")));

        getDriver().findElement(By.id("jenkins-head-icon")).click();
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("#tasks")));
    }

    private void createItems(String[] items) {
        for (String itemName : items) {
            createItem(itemName);
        }
    }

    private void openSearchAndType(String query) {
        getDriver().findElement(By.id("root-action-SearchAction")).click();
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(
                By.id("command-bar"))).sendKeys(query);
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
    public void testResultsContainsPartialMatch() {
        int expectedMatching = countMatchingItems(ITEMS_TO_CREATE, SEARCH_WORD);
        createItems(ITEMS_TO_CREATE);

        openSearchAndType(SEARCH_WORD);
        List<WebElement> searchResults = getWait5().until(ExpectedConditions.numberOfElementsToBe(
                By.cssSelector("#search-results a"), expectedMatching));

        for (WebElement result : searchResults) {
            Assert.assertTrue(
                    result.getText().toLowerCase(Locale.ROOT).contains(SEARCH_WORD));
        }
    }

    @Test
    public void testNavigateToItemFromSearch() {
        int expectedMatching = countMatchingItems(ITEMS_TO_CREATE, SEARCH_WORD);
        createItems(ITEMS_TO_CREATE);

        openSearchAndType(SEARCH_WORD);
        List<WebElement> searchResults = getWait5().until(ExpectedConditions.numberOfElementsToBe(
                By.cssSelector("#search-results a"), expectedMatching));

        String expectedItemName = searchResults.getFirst().getText();
        searchResults.getFirst().click();
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#main-panel h1")));

        Assert.assertTrue(
                getDriver().findElement(By.cssSelector("#main-panel h1")).getText().contains(expectedItemName));
    }

    @Test
    public void testModifyQuery() {
        final int initialExpected = countMatchingItems(ITEMS_TO_CREATE, SEARCH_WORD);
        final int expectedMatching = countMatchingItems(ITEMS_TO_CREATE, MODIFY_SEARCH_WORD);
        createItems(ITEMS_TO_CREATE);
        openSearchAndType(SEARCH_WORD);
        getWait5().until(ExpectedConditions.numberOfElementsToBe(By.cssSelector("#search-results a"), initialExpected));

        WebElement searchInput = getDriver().findElement(By.id("command-bar"));
        searchInput.clear();
        searchInput.sendKeys(MODIFY_SEARCH_WORD);

        List<WebElement> modifySearchResults = getWait5().until(ExpectedConditions.numberOfElementsToBe(
                By.cssSelector("#search-results a"), expectedMatching));

        for (WebElement result : modifySearchResults) {
            Assert.assertTrue(
                    result.getText().toLowerCase(Locale.ROOT).contains(MODIFY_SEARCH_WORD));
        }
    }

    @Test
    public void testLongQuery() {
        String longQuery = "a".repeat(300);

        openSearchAndType(longQuery);
        WebElement noResultsElement = getWait5().until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("#search-results span")));

        Assert.assertTrue(getDriver().findElement(By.id("command-bar")).isDisplayed());
        Assert.assertTrue(noResultsElement.getText().contains("No results for"));
    }

    @Test
    public void testEmptyQuery() {
        createItems(ITEMS_TO_CREATE);

        openSearchAndType("");
        List<WebElement> searchResults = getWait5().until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("#search-results a")));

        boolean hasHelpLink = false;
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