package school.redrover.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class FolderPage extends BasePage {

    public FolderPage(WebDriver driver) {
        super(driver);
        getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.tagName("h1")));
    }

    public String getMainHeading() {
        return getDriver().findElement(By.tagName("h1")).getText();
    }

    public FolderConfigurationPage openConfigure() {
        WebElement configureLink = getWait5().until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href$=configure]")));
        configureLink.click();
        getWait10().until(ExpectedConditions.stalenessOf(configureLink));

        return new FolderConfigurationPage(getDriver());
    }

    public NewItemPage clickNewItemFolder() {
        getDriver().findElement(By.cssSelector("a[href$='/newJob']")).click();
        getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.id("name")));

        return new NewItemPage(getDriver());
    }

    public FolderPage goToParentFolder(String folderName) {
        WebElement parentLink = getDriver().findElement(By.cssSelector("a[href$='/job/" + folderName + "/']"));
        parentLink.click();
        getWait2().until(ExpectedConditions.stalenessOf(parentLink));

        return new FolderPage(getDriver());
    }

    public boolean isItemDisplayed(String itemName) {
        return !getDriver().findElements(By.cssSelector("a.jenkins-table__link[href$='job/" + itemName + "/']")).isEmpty();
    }
}
