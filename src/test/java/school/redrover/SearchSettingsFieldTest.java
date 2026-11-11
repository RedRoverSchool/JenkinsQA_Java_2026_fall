package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import java.util.ArrayList;
import java.util.List;

public class SearchSettingsFieldTest extends BaseTest {

    private static final By SEARCH_SETTINGS_FIELD = By.id("settings-search-bar");
    private static final By SEARCH_SUGGESTIONS = By.cssSelector(".jenkins-search__results-container .jenkins-dropdown__item");
    private static final By MANAGE_JENKINS_BUTTON = By.id("root-action-ManageJenkinsAction");
    private static final By NO_RESULTS_LABEL = By.className("jenkins-search__results__no-results-label");

    private boolean isElementClickable(By locator) {
        try {
            getWait5().until(ExpectedConditions.elementToBeClickable(locator));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    private void openManageJenkins() {
        getWait5().until(ExpectedConditions.elementToBeClickable(MANAGE_JENKINS_BUTTON));
        getDriver().findElement(MANAGE_JENKINS_BUTTON).click();
    }

    @DataProvider(name = "positiveSearchScenarios")
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

    @DataProvider(name = "negativeSearchScenarios")
    private Object[] negativeSearchScenarios() {
        return new Object[] {
                "123", "OOGABOOGA", "@!!!!%%"
        };
    }

    @Test(priority = 1)
    public void testVisibilityAndClickability() {
        openManageJenkins();

        Assert.assertTrue(isElementClickable(SEARCH_SETTINGS_FIELD));
    }

    @Test(dataProvider = "positiveSearchScenarios", priority = 2)
    public void testValidInput(String searchText, List<String> expectedSuggestions) {
        openManageJenkins();

        getWait5().until(ExpectedConditions.visibilityOfElementLocated(SEARCH_SETTINGS_FIELD));
        getDriver().findElement(SEARCH_SETTINGS_FIELD).sendKeys(searchText);
        getWait5().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(SEARCH_SUGGESTIONS));

        List<String> actualSuggestions = new ArrayList<>();
        for (WebElement suggestion : getDriver().findElements(SEARCH_SUGGESTIONS)) {
            actualSuggestions.add(suggestion.getText());
        }

        Assert.assertEquals(actualSuggestions, expectedSuggestions);
    }

    @Test(dataProvider = "negativeSearchScenarios", priority = 3)
    public void testInvalidInput(String searchText) {
        openManageJenkins();

        getWait5().until(ExpectedConditions.visibilityOfElementLocated(SEARCH_SETTINGS_FIELD));
        getDriver().findElement(SEARCH_SETTINGS_FIELD).sendKeys(searchText);
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(NO_RESULTS_LABEL));

        Assert.assertEquals(getDriver().findElement(NO_RESULTS_LABEL).getText(), "No results");
    }
}