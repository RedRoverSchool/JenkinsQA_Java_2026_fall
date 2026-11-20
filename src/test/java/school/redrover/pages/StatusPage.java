package school.redrover.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class StatusPage extends BasePage {
    private final By mainHeading = By.xpath("//h1");
    private final By description = By.xpath("//*[@id='description']");
    private final By configureLink = By.cssSelector("a[href$=configure]");

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

    public StatusPage clickBuildNow() {
        getDriver().findElement(
                By.cssSelector("a[href$='/build?delay=0sec']")
        ).click();

        return this;
    }

    public boolean isConfigureLinkDisplayed() {
        return getWait5().until(
                ExpectedConditions.visibilityOfElementLocated(configureLink)).isDisplayed();
    }
}
