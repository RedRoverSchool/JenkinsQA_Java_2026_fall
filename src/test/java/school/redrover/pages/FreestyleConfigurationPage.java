package school.redrover.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class FreestyleConfigurationPage extends BasePage {
    private final By descriptionField = By.name("description");
    private final By applyButton = By.name("Apply");
    private final By saveButton = By.name("Submit");
    private final By applyNotificationText = By.xpath("//*[@id='notification-bar']/span");
    private final By triggerSection = By.xpath("//section[div[contains(@class, 'jenkins-section__title') and contains(text(), 'Triggers')]]");
    private final By buildPeriodicallyCheckbox = By.name("hudson-triggers-TimerTrigger");
    private final By scheduleTextarea = By.cssSelector("textarea.jenkins-input.validated");
    private final By scheduleValidationMessage = By.xpath("//div[@class='ok' and contains(text(), 'Would last have run at')]");
    private final By scheduleWarningMessage = By.className("warning");

    public FreestyleConfigurationPage(WebDriver driver) {super(driver);}

    public StatusPage clickSaveButton(){
        getWait10().until(ExpectedConditions.elementToBeClickable(saveButton)).click();

        return new StatusPage(getDriver());
    }

    public FreestyleConfigurationPage clickApplyButton() {
        getWait10().until(ExpectedConditions.elementToBeClickable(applyButton)).click();

        return this;
    }

    public FreestyleConfigurationPage addDescription(String description) {
        getWait2().until(ExpectedConditions.visibilityOfElementLocated(descriptionField)).sendKeys(description);

        return this;
    }

    public String getNotificationText() {
        return getWait2().until(ExpectedConditions.visibilityOfElementLocated(applyNotificationText)).getText();
    }

    public boolean isTriggerSectionDisplayed() {
        return getWait10().until(ExpectedConditions.visibilityOfElementLocated(triggerSection)).isDisplayed();
    }

    public boolean areTriggerCheckboxesDisplayed(String... checkboxNames) {
        WebElement section = getWait10().until(
                ExpectedConditions.visibilityOfElementLocated(triggerSection));

        for (String name : checkboxNames) {
            if (!section.findElement(By.name(name)).isDisplayed()) {
                return false;
            }
        }

        return true;
    }

    public FreestyleConfigurationPage checkBuildPeriodically() {
        WebElement section = getWait10().until(ExpectedConditions.visibilityOfElementLocated(triggerSection));
        WebElement checkbox = section.findElement(buildPeriodicallyCheckbox);
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", checkbox);

        return this;
    }

    public FreestyleConfigurationPage inputSchedule(String schedule) {
        WebElement section = getWait10().until(ExpectedConditions.visibilityOfElementLocated(triggerSection));
        WebElement textarea = getWait5().until(ExpectedConditions.visibilityOf(section.findElement(scheduleTextarea)));
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", textarea);
        textarea.sendKeys(schedule);

        return this;
    }

    public String getScheduleValidationMessage() {
        return getWait5().until(ExpectedConditions.visibilityOfElementLocated(scheduleValidationMessage)).getText();
    }

    public String getScheduleWarningMessage() {
        return getWait5().until(ExpectedConditions.visibilityOfElementLocated(scheduleWarningMessage)).getText();
    }
}

