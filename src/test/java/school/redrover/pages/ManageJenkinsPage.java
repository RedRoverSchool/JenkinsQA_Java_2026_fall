package school.redrover.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

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
}
