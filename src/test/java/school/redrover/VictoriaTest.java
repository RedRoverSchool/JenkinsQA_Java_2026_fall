package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

import java.time.Duration;
import java.util.List;
import java.util.Locale;

public class VictoriaTest extends BaseTest {

    private WebDriverWait wait;
    private int expectedMatching;
    private final String targetWord = "item";

    @BeforeMethod
    public void setUp() {
        wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        String[] createdNames = createItemsAndReturnNames();

        expectedMatching = countMatching(createdNames, targetWord);
    }

    @Test
    public void testSearchReturnsMatchingItems() {
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

    public String[] createItemsAndReturnNames() {
        String[] names = {
                "name item 1",
                "test item",
                "test project"
        };

        for (String name : names) {
            createElement(name);
        }
        return names;
    }

    public void createElement(String nameItem) {

        wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("#tasks :first-child"))).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("#createItem input.jenkins-input"))).sendKeys(nameItem);

        wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("#items :first-child"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("#bottom-sticker button"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(
                By.id("jenkins-head-icon"))).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("#tasks")));
    }

    private int countMatching(String[] names, String word) {
        int count = 0;

        for (String name : names) {
            if (name.toLowerCase(Locale.ROOT).contains(word)) {
                count++;
            }
        }
        return count;
    }
}
