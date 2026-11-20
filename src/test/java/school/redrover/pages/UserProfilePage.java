package school.redrover.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class UserProfilePage extends BasePage {
    public UserProfilePage(WebDriver driver) {
        super(driver);
    }

    public String getUserIdText() {
        return getDriver().findElement(By.cssSelector("#main-panel > div:nth-child(4)")).getText();
    }

    public String getFullNameHeading() {
        return getDriver().findElement(By.xpath("//h1")).getText();
    }

    public static final String[] USER_MENU_ITEMS = {
                "builds",
                "my-views",
                "account",
                "appearance",
                "preferences",
                "security",
                "experiments",
                "credentials"
        };

    public UserProfilePage clickMenuItems(String username) {
        for (String item : USER_MENU_ITEMS) {
            getDriver().findElement(By.cssSelector("a[href='/user/" + username + "/" + item + "']")).click();
            if(item.equals("my-views")) {
                getDriver().navigate().back();
            }
        }
        return this;
    }

    public UserProfilePage clickDeleteLink() {
        getDriver().findElement(By.cssSelector(".confirmation-link")).click();
        return this;
    }

    public String getConfirmation() {
        return getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.className("jenkins-dialog__contents"))).getText();
    }

    public ManageJenkinsPage confirmDelete() {
        getDriver().findElement(By.cssSelector("button[data-id='ok']")).click();
        getWait5().until(ExpectedConditions.invisibilityOfElementLocated(By.className("jenkins-dialog__contents")));
        return new ManageJenkinsPage(getDriver());
    }
}
