package school.redrover.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CreateUserPage extends BasePage {
    public CreateUserPage(WebDriver driver) {
        super(driver);
    }

    public CreateUserPage enterUsername(String username) {
        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.id("username"))).sendKeys(username);
        return this;
    }

    public CreateUserPage enterPassword(String password) {
        getDriver().findElement(By.name("password1")).sendKeys(password);
        getDriver().findElement(By.name("password2")).sendKeys(password);
        return this;
    }

    public CreateUserPage enterFullName(String fullname) {
        getDriver().findElement(By.name("fullname")).sendKeys(fullname);
        return this;
    }

    public CreateUserPage enterEmail(String email) {
        getDriver().findElement(By.name("email")).sendKeys(email);
        return this;
    }

    public UsersPage clickCreateButton() {
        getDriver().findElement(By.cssSelector(".jenkins-submit-button")).click();
        return new UsersPage(getDriver());
    }
}
