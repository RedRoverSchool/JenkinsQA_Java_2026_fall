package school.redrover.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class BasePage extends BaseModel {

    public BasePage(WebDriver driver) {
        super(driver);
    }

    public HomePage gotoHomePage() {
        getDriver().findElement(By.className("app-jenkins-logo")).click();

        return new HomePage(getDriver());
    }
}
