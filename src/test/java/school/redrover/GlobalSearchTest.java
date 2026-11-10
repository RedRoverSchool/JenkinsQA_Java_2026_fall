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
    public void testEmptyQuery() {
        createItems(ITEMS_TO_CREATE);

        getDriver().findElement(By.id("root-action-SearchAction")).click();
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(
                By.id("command-bar"))).sendKeys("");
        List<WebElement> searchResults = getWait5().until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                By.cssSelector("#search-results a")));

        Assert.assertTrue(searchResults.getFirst().getText().contains("Get help using Jenkins search"));
    }

    @Test(dependsOnMethods = "testEmptyQuery")
    public void testLongQuery() {
        String longQuery = "a".repeat(300);

        getDriver().findElement(By.id("root-action-SearchAction")).click();
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(
                By.id("command-bar"))).sendKeys(longQuery);
        WebElement noResultsElement = getWait5().until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("#search-results span")));

        Assert.assertTrue(noResultsElement.getText().contains("No results for"));
    }

    @Test(dependsOnMethods = "testLongQuery")
    public void testResultsContainsPartialMatch() {
        int expectedMatching = countMatchingItems(ITEMS_TO_CREATE, SEARCH_WORD);

        getDriver().findElement(By.id("root-action-SearchAction")).click();
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(
                By.id("command-bar"))).sendKeys(SEARCH_WORD);
        List<WebElement> searchResults = getWait5().until(ExpectedConditions.numberOfElementsToBe(
                By.cssSelector("#search-results a"), expectedMatching));

        for (WebElement result : searchResults) {
            Assert.assertTrue(result.getText().toLowerCase(Locale.ROOT).contains(SEARCH_WORD));
        }
    }

    @Test(dependsOnMethods = "testResultsContainsPartialMatch")
    public void testModifyQuery() {
        final int initialMatching = countMatchingItems(ITEMS_TO_CREATE, SEARCH_WORD);
        final int expectedMatching = countMatchingItems(ITEMS_TO_CREATE, MODIFY_SEARCH_WORD);

        getDriver().findElement(By.id("root-action-SearchAction")).click();
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.id("command-bar"))).sendKeys(SEARCH_WORD);
        getWait5().until(ExpectedConditions.numberOfElementsToBe(By.cssSelector("#search-results a"), initialMatching));

        getDriver().findElement(By.id("command-bar")).clear();
        getDriver().findElement(By.id("command-bar")).sendKeys(MODIFY_SEARCH_WORD);
        List<WebElement> modifySearchResults = getWait5().until(ExpectedConditions.numberOfElementsToBe(
                By.cssSelector("#search-results a"), expectedMatching));

        for (WebElement result : modifySearchResults) {
            Assert.assertTrue(result.getText().toLowerCase(Locale.ROOT).contains(MODIFY_SEARCH_WORD));
        }
    }

    @Test(dependsOnMethods = "testModifyQuery")
    public void testNavigateToItemFromSearch() {
        getDriver().findElement(By.id("root-action-SearchAction")).click();
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(
                By.id("command-bar"))).sendKeys(MODIFY_SEARCH_WORD);
        WebElement targetResult = getWait5().until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[@id='search-results']//a[contains(., '" + MODIFY_SEARCH_WORD + "')]")));

        String expectedItemName = targetResult.getText();
        targetResult.click();
        WebElement elementFromSearch = getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#main-panel h1")));

        Assert.assertTrue(elementFromSearch.getText().contains(expectedItemName));
    }
}