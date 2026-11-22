package school.redrover.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class RenamePage extends BasePage {

    private final By pageTitle = By.cssSelector("h1");
    private final By newNameField = By.name("newName");
    private final By renameButton = By.xpath("//button[@value='Rename']");
    private final By warning = By.cssSelector("div.warning");

    public RenamePage(WebDriver driver) {
        super(driver);
        getWait2().until(ExpectedConditions.visibilityOfElementLocated(pageTitle));
    }

    public StatusPage renameWithCorrectName(String newName) {
        getDriver().findElement(newNameField).clear(); // Clear newNameField
        getDriver().findElement(newNameField).sendKeys(newName);
        getDriver().findElement(renameButton).click();

        //Wait until the Status Page is loaded
        getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.id("jenkins-builds")));

        return new StatusPage(getDriver());
    }
}
