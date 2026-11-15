package school.redrover.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

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
}
