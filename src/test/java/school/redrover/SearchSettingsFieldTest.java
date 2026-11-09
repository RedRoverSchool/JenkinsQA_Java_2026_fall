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
    private static final By SEARCH_SETTINGS_DROPDOWN_ITEMS = By.cssSelector(".jenkins-search__results-container .jenkins-dropdown__item");
    private static final By MANAGE_JENKINS_BUTTON = By.id("root-action-ManageJenkinsAction");

    private boolean isElementClickable(By locator) {
        try {
            super.getWait5().until(ExpectedConditions.elementToBeClickable(locator));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    private void openManageJenkins() {
        super.getWait5().until(ExpectedConditions.elementToBeClickable(MANAGE_JENKINS_BUTTON));
        getDriver().findElement(MANAGE_JENKINS_BUTTON).click();
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

    @Test
    public void testVisibilityAndClickability() {
        openManageJenkins();

        Assert.assertTrue(isElementClickable(SEARCH_SETTINGS_FIELD));
    }

    @Test(dataProvider = "searchScenarios")
    public void testValidInput(String searchText, List<String> expectedSuggestions) {

        openManageJenkins();
        getDriver().findElement(SEARCH_SETTINGS_FIELD).sendKeys(searchText);
        getWait5().until(ExpectedConditions.visibilityOfAllElementsLocatedBy(SEARCH_SETTINGS_DROPDOWN_ITEMS));

        List<String> actualSuggestions = new ArrayList<>();
        for (WebElement suggestion : getDriver().findElements(SEARCH_SETTINGS_DROPDOWN_ITEMS)) {
            actualSuggestions.add(suggestion.getText());
        }

        Assert.assertEquals(actualSuggestions, expectedSuggestions);
    }
}