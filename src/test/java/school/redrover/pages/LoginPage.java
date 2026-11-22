package school.redrover.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

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
        try {
            return getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.id("remember_me"))).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
