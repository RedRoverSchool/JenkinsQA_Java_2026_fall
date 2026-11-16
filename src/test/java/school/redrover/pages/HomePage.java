package school.redrover.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HomePage extends BasePage {

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public NewItemPage clickNewItem() {
        getDriver().findElement(By.xpath("//a[.//span[text()='New Item']]")).click();

        return new NewItemPage(getDriver());
    }

    public String getFirstProjectName() {
        return getDriver().findElement(By.cssSelector(".jenkins-table__link > span:first-child")).getText();
    }

    public LoginPage clickSignOut() {

        WebElement userMenuButton = getDriver().findElement(By.id("root-action-UserAction"));
        Actions actions = new Actions(getDriver());
        actions.moveToElement(userMenuButton).perform();

        getWait10().until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href='/logout']"))).click();

        getWait10().until(ExpectedConditions.urlContains("login"));

        return new LoginPage(getDriver());
    }
}
