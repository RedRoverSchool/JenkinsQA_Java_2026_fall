package school.redrover.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ManageJenkinsPage extends BasePage {

    public ManageJenkinsPage(WebDriver driver) {
        super(driver);
    }

    public ManageJenkinsPage openManage() {
        getDriver().findElement(By.cssSelector("a[href='/manage']")).click();
        return this;
    }

    public UsersPage openUsers() {
        getDriver().findElement(By.cssSelector("a[href='securityRealm/']")).click();
        return new UsersPage(getDriver());
    }

    public SecurityPage openSecurity() {
        WebElement securitySettings = getDriver().findElement(By.cssSelector("a[href='configureSecurity']"));
        new Actions(getDriver())
                .scrollToElement(securitySettings)
                .perform();
        securitySettings.click();
        return new SecurityPage(getDriver());
    }

    public ManageJenkinsPage clickSearchSettings() {
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.id("settings-search-bar")));
        getDriver().findElement(By.id("settings-search-bar")).click();
        return new ManageJenkinsPage(getDriver());
    }

    public void searchForSettings(String searchText) {
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.id("settings-search-bar")));
        getDriver().findElement(By.id("settings-search-bar")).sendKeys(searchText);
    }
}
