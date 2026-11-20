package school.redrover.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.ArrayList;
import java.util.List;

public class UsersPage extends BasePage {

    public UsersPage(WebDriver driver) {
        super(driver);
    }

    public CreateUserPage clickCreateUser() {
        getDriver().findElement(By.className("jenkins-button--primary")).click();
        return new CreateUserPage(getDriver());
    }

    public UsersPage waitUntilUserCreated(String username) {
        getWait5().until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("#people a[href='user/" + username + "/']")
        ));
        return this;
    }

    public List<String> getUserList() {
        List<WebElement> rows = getDriver().findElements(By.cssSelector("#people tbody tr"));
        List<String> userList = new ArrayList<>();

        for(WebElement row : rows) {
            String user = row.findElement(By.cssSelector("td:nth-child(2) a")).getText();
            userList.add(user);
        }
        return userList;
    }

    public String getFirstBreadcrumbText() {
        return getDriver().findElement(By.cssSelector("#breadcrumbs > li")).getText();
    }
}
