package school.redrover.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class StatusPage extends BasePage {
    private final By mainHeading = By.xpath("//h1");
    private final By description = By.xpath("//*[@id='description']");
    private final By descriptionContent = By.id("description-content");
    private final By editDescriptionLink = By.id("description-link");
    private final By descriptionTextarea = By.id("description-textarea");
    private final By descriptionSaveButton = By.cssSelector("#bottom-sticker button.jenkins-button--primary");

    public StatusPage(WebDriver driver) {
        super(driver);
        getWait2().until(ExpectedConditions.visibilityOfElementLocated(mainHeading));
    }

    public String getMainHeading() {
        return getDriver().findElement(mainHeading).getText();
    }

    public String getDescription() {
        return getDriver().findElement(description).getText();
    }

    public HomePage goToHomePage(){
        getDriver().findElement(By.id("jenkins-head-icon")).click();

        return new HomePage(getDriver());
    }

    public PipelineConfigurationPage openConfigurationPage() {
        getDriver().findElement(By.cssSelector("a[href$=configure]")).click();

        return new PipelineConfigurationPage(getDriver());
    }

    public String getDescriptionText() {
        return getWait5().until(
                ExpectedConditions.visibilityOfElementLocated(descriptionContent)).getText();
    }

    public StatusPage clickEditDescription() {
        getWait5().until(ExpectedConditions.elementToBeClickable(editDescriptionLink)).click();

        return this;
    }

    public StatusPage inputDescription(String text) {
        getDriver().findElement(descriptionTextarea).sendKeys(text);

        return this;
    }

    public StatusPage clickSaveDescription() {
        getDriver().findElement(descriptionSaveButton).click();

        return this;
    }

    public String getDescriptionLinkText() {
        return getWait5().until(ExpectedConditions.visibilityOfElementLocated(editDescriptionLink)).getText();
    }

    public StatusPage clearDescriptionText() {
        getWait5().until(ExpectedConditions.elementToBeClickable(descriptionTextarea)).clear();

        return this;
    }

    public StatusPage waitForDescriptionText(String text) {
        getWait5().until(ExpectedConditions.textToBePresentInElementLocated(descriptionContent, text));

        return this;
    }

    public StatusPage clickBuildNow() {
        getDriver().findElement(
                By.cssSelector("a[href$='/build?delay=0sec']")
        ).click();

        return this;
    }
}
