package school.redrover.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;

public class FolderConfigurationPage extends BasePage {

    private final By displayNameField = By.name("_.displayNameOrNull");
    private final By saveButton = By.name("Submit");
    private final By healthMetricsButton = By.cssSelector("button.advanced-button");
    private final By addMetricButton = By.cssSelector("button.jenkins-button.hetero-list-add");
    private final By childItemWithWorstHealth = By.xpath("//button[normalize-space()='Child item with worst health']");
    private final By recursiveLabel = By.cssSelector("label.attach-previous");
    private final By applyButton = By.cssSelector("button.jenkins-button.apply-button");
    private final By recursiveCheckbox = By.cssSelector("input[name='_.recursive']");

    public FolderConfigurationPage(WebDriver driver) {
        super(driver);
    }

    public FolderConfigurationPage setDisplayName(String displayName) {
        getDriver().findElement(displayNameField).sendKeys(displayName);

        return this;
    }

    public StatusPage clickSaveButton() {
        getWait5().until(elementToBeClickable(saveButton)).click();
        getWait5().until(ExpectedConditions.not(ExpectedConditions.urlContains("/configure")));

        return new StatusPage(getDriver());
    }

    public FolderConfigurationPage openHealthMetricsSection() {
        getWait5().until(elementToBeClickable(healthMetricsButton)).click();

        return this;
    }

    public FolderConfigurationPage clickAddMetricButton() {
        getWait5().until(elementToBeClickable(addMetricButton)).click();

        return this;
    }

    public FolderConfigurationPage selectChildItemWithWorstHealth() {
        getWait5().until(elementToBeClickable(childItemWithWorstHealth)).click();

        return this;
    }

    public FolderConfigurationPage clickRecursiveCheckbox() {
        getDriver().findElement(recursiveLabel).click();

        return this;
    }

    public FolderConfigurationPage clickApplyButton() {
        getWait5().until(elementToBeClickable(applyButton)).click();

        return this;
    }

    public boolean isConfigurationPageOpened() {
        return getWait5().until(ExpectedConditions.urlMatches(".*/configure/?$"));
    }

    public boolean isRecursiveCheckboxSelected() {
        return getDriver().findElement(recursiveCheckbox).isSelected();
    }
}
