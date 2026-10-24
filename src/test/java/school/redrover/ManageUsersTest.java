package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class ManageUsersTest extends BaseTest {

    private static final String USERNAME = "jsmith";
    private static final String PASSWORD = "unique#!1IDoF3";
    private static final String FULL_NAME = "John Smith";
    private static final String EMAIL = "jsmith@jsm.com";

    @Test
    public void testCreate() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));

        getDriver().findElement(By.cssSelector("a[href='/manage']")).click();
        getDriver().findElement(By.cssSelector("a[href='securityRealm/']")).click();
        getDriver().findElement(By.className("jenkins-button--primary")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username"))).sendKeys(USERNAME);

        getDriver().findElement(By.name("password1")).sendKeys(PASSWORD);
        getDriver().findElement(By.name("password2")).sendKeys(PASSWORD);
        getDriver().findElement(By.name("fullname")).sendKeys(FULL_NAME);
        getDriver().findElement(By.name("email")).sendKeys(EMAIL);
        getDriver().findElement(By.cssSelector(".jenkins-submit-button")).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("#people a[href='user/" + USERNAME + "/']")
        ));

        List<WebElement> rows = getDriver().findElements(By.cssSelector("#people tbody tr"));
        List<String> userList = new ArrayList<>();

        for(WebElement row : rows) {
            String user = row.findElement(By.cssSelector("td:nth-child(2) a")).getText();
            userList.add(user);
        }

        Assert.assertEquals(
                getDriver().getCurrentUrl(),
               "http://localhost:8080/securityRealm/");
        Assert.assertTrue(userList.contains(USERNAME));
    }
}
