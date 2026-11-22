package school.redrover.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class PipelineConfigurationPage extends BasePage {
    private final By descriptionField = By.name("description");
    private final By saveButton = By.xpath("//button[@value='Save']");
    private final By applyButton = By.name("Apply");
    private final By triggerSection = By.xpath("//section[div[contains(@class, 'jenkins-section__title') " +
            "and normalize-space()='Triggers']]");


    public PipelineConfigurationPage(WebDriver driver) {
        super(driver);
    }

    public enum LeftSideSection {
        GENERAL("General"),
        TRIGGERS("Triggers"),
        PIPELINE("Pipeline"),
        ADVANCED("Advanced");

        private final String sectionName;

        LeftSideSection(String sectionName) {
            this.sectionName = sectionName;
        }

        public String getSectionName() {
            return sectionName;
        }
    }

    public record HelpContent(String text, List<String> hrefs) {
        public boolean hasLinkContaining(String part) {
            return hrefs.stream().anyMatch(h -> h.contains(part));
        }
    }

    public PipelineConfigurationPage addDescription(String description) {
        getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[normalize-space(.)='Configure']")));
        getDriver().findElement(descriptionField).sendKeys(description);

        return this;
    }

    public PipelineConfigurationPage clickApplyButton() {
        getDriver().findElement(applyButton).click();

        return this;
    }

    public StatusPage clickSaveButton() {
        getDriver().findElement(saveButton).click();

        //Wait until the Status Page is loaded
        getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.id("jenkins-builds")));

        return new StatusPage(getDriver());
    }

    public PipelineConfigurationPage selectHelloWorldScriptFromSample() {
        getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.id("workflow-editor-samples")));
        Select select = new Select(getDriver().findElement(By.id("workflow-editor-samples")));
        select.selectByValue("hello");

        return this;
    }

    public List<String> getTextFromScript() {
        return getDriver().findElements(By.className("ace_string")).stream()
                .map(WebElement::getText)
                .map(s -> s.replaceAll("^'|'$", ""))
                .toList();
    }

    public String getNotificationText() {
        return getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='notification-bar']/span"))).getText();
    }

    public PipelineConfigurationPage clickOnLeftSideSection(LeftSideSection leftSideSection) {
        getWait5().until(ExpectedConditions.elementToBeClickable(By.xpath(
                "//span[contains(text(),'" + leftSideSection.getSectionName() + "')]/ancestor::button"
        ))).click();

        return this;
    }

    public PipelineConfigurationPage openAdvancedDropdown() {
        getWait5().until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".jenkins-bottom-app-bar__shadow--stuck")));
        getDriver().findElement(By.cssSelector(".jenkins-section button.advancedButton")).click();

        return this;
    }

    public PipelineConfigurationPage clickOnHelpIcon(String featureName) {
        getWait2().until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("a[aria-label='Help for feature: " + featureName + "'] > span"
                ))).click();
        return this;
    }

    public PipelineConfigurationPage setQuietPeriod(String seconds) {
        getWait2().until(ExpectedConditions.elementToBeClickable(
                        By.cssSelector("input[name='hasCustomQuietPeriod'] + label")))
                .click();
        WebElement quietPeriodSeconds = getDriver().findElement(By.cssSelector("input[name='quiet_period']"));
        quietPeriodSeconds.clear();
        quietPeriodSeconds.sendKeys(seconds, Keys.TAB);

        return this;
    }

    public String getQuietPeriod() {
        return getDriver().findElement(By.cssSelector("input[name='quiet_period']")).getDomProperty("value");
    }

    public PipelineConfigurationPage setDisplayName(String displayName) {
        getWait2().until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("input[checkurl$='checkDisplayNameOrNull']"))).sendKeys(displayName, Keys.TAB);

        return this;
    }

    public String getQuietPeriodError() {
        return getWait2().until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath(
                        "//input[@name='quiet_period']/ancestor::div[contains(@class,'jenkins-form-item')]/descendant::div[@class='error']"
                ))).getText();
    }

    public HelpContent getHelpContent(String featureName) {
        WebElement help = getWait2().until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath(
                        "//a[@aria-label='Help for feature: "
                                + featureName
                                + "']/ancestor::div[contains(@class, 'jenkins-form-item')]//div[@class='help']"
                )));

        List<String> hrefs = help.findElements(By.xpath(".//a[@href]")).stream()
                .map(a -> a.getAttribute("href"))
                .toList();

        return new HelpContent(help.getText(), hrefs);
    }

    public By getTriggerBlock(String triggerName) {
        return By.xpath("//label[contains(normalize-space(.), '" + triggerName + "')]"
                + "/ancestor::div[contains(@class, 'help-sibling') "
                + "or contains(@class, 'jenkins-checkbox-help-wrapper')][1]");
    }

    public boolean isTriggerSectionDisplayed() {
        return getWait5().until(ExpectedConditions.visibilityOfElementLocated(triggerSection)).isDisplayed();
    }

    public boolean isTriggerCheckboxPresent(String triggerName) {
        return !getDriver().findElement(getTriggerBlock(triggerName))
                .findElements(By.xpath(".//input[@type='checkbox']")).isEmpty();
    }

    public boolean isTriggerHelpIconPresent(String triggerName) {
        return !getDriver().findElement(getTriggerBlock(triggerName))
                .findElements(By.xpath(".//a[contains(@class, 'jenkins-help-button')]")).isEmpty();
    }

    public PipelineConfigurationPage checkAllTrigger(String triggerName) {
        WebElement block = getWait5().until(
                ExpectedConditions.visibilityOfElementLocated(getTriggerBlock(triggerName)));
        WebElement label = block.findElement(By.tagName("label"));
        new Actions(getDriver()).scrollToElement(label).scrollByAmount(0, 300).perform();
        getWait5().until(ExpectedConditions.elementToBeClickable(label)).click();
        getWait5().until(ExpectedConditions.elementToBeSelected(
                block.findElement(By.xpath(".//input[@type='checkbox']"))));

        return this;
    }

    public boolean isTriggerChecked(String triggerName) {
        WebElement block = getWait5().until(
                ExpectedConditions.visibilityOfElementLocated(getTriggerBlock(triggerName)));

        return block.findElement(By.xpath(".//input[@type='checkbox']")).isSelected();
    }
}
