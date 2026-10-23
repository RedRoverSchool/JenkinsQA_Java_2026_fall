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

public class CreateUserTest extends BaseTest {

    @Test
    public void testCreateUser() {
        final String username = "jsmith";
        final String password = "unique#!1IDoF3";
        final String fullName = "John Smith";
        final String email = "jsmith@jsm.com";

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));

        getDriver().findElement(By.cssSelector("a[href=\"/manage\"]")).click();
        getDriver().findElement(By.cssSelector("a[href=\"securityRealm/\"]")).click();
        getDriver().findElement(By.className("jenkins-button--primary")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username"))).sendKeys(username);

        getDriver().findElement(By.name("password1")).sendKeys(password);
        getDriver().findElement(By.name("password2")).sendKeys(password);
        getDriver().findElement(By.name("fullname")).sendKeys(fullName);
        getDriver().findElement(By.name("email")).sendKeys(email);
        getDriver().findElement(By.cssSelector(".jenkins-submit-button")).click();


        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("#people a[href='user/" + username + "/']")
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
        Assert.assertTrue(userList.contains(username));
    }

}
