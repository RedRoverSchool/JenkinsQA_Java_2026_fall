package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class SearchSettingsFieldTest extends BaseTest {
    private static final By SEARCH_SETTINGS_FIELD = By.id("settings-search-bar");
    private static final By SEARCH_SETTINGS_DROPDOWN_ITEMS = By.cssSelector(".jenkins-search__results-container .jenkins-dropdown__item");
    private static final By MANAGE_JENKINS_BUTTON = By.id("root-action-ManageJenkinsAction");

    private static boolean isElementClickable(WebDriver driver, By locator) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.elementToBeClickable(locator));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    private void openManageJenkins() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(MANAGE_JENKINS_BUTTON));
        getDriver().findElement(MANAGE_JENKINS_BUTTON).click();
    }

    @Test
    public void testVisibilityAndClickability() {
        openManageJenkins();

        Assert.assertTrue(getDriver().getCurrentUrl().contains("manage/"));
        Assert.assertTrue(isElementClickable(getDriver(), SEARCH_SETTINGS_FIELD));
    }

    @DataProvider(name = "searchScenarios")
    private Object[][] searchScenarios() {
        return new Object[][]{
                {"System", List.of("System", "System Information")},
                {"s", List.of("System", "Tools", "Plugins", "Nodes", "Clouds")},
                {"Cred", List.of("Credentials", "Credential Providers")},
                {"Nodes", List.of("Nodes")},
                {"Plugins", List.of("Plugins")},
                {"Lo", List.of("Clouds", "Logs", "Load Statistics")}
        };
    }

    @Test(dataProvider = "searchScenarios")
    public void testValidInput(String searchText, List<String> expectedSuggestions) {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        openManageJenkins();
        getDriver().findElement(SEARCH_SETTINGS_FIELD).sendKeys(searchText);
        List<String> actualSuggestions = new ArrayList<>();
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(SEARCH_SETTINGS_DROPDOWN_ITEMS));
        List<WebElement> searchSuggestions = getDriver().findElements(SEARCH_SETTINGS_DROPDOWN_ITEMS);
        for (WebElement suggestion : searchSuggestions) {
            actualSuggestions.add(suggestion.getText());
        }

        Assert.assertTrue(getDriver().getCurrentUrl().contains("manage/"));
        Assert.assertEquals(actualSuggestions, expectedSuggestions);
    }
}