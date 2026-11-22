package school.redrover.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public String getUsernameValue() {
        return getDriver().findElement(By.id("j_username")).getAttribute("value");
    }

    public String getPasswordValue() {
        return getDriver().findElement(By.name("j_password")).getAttribute("value");
    }

    public boolean isRememberMeCheckboxDisplayed() {

        return getDriver().findElement(By.name("remember_me")).isDisplayed();
    }
}
