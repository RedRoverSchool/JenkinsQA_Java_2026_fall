package school.redrover.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import java.util.List;

public class PipelineConfigurationPage extends BasePage {
    private final By descriptionField = By.name("description");
    private final By saveButton = By.xpath("//button[@value='Save']");
    private final By applyButton = By.name("Apply");

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
}
