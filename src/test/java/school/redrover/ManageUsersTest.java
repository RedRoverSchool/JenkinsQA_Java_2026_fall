package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import java.util.ArrayList;
import java.util.List;

public class ManageUsersTest extends BaseTest {

    private static final String USERNAME = "jsmith";
    private static final String PASSWORD = "unique#!1IDoF3";
    private static final String FULL_NAME = "John Smith";
    private static final String EMAIL = "jsmith@jsm.com";

    @Test
    public void testCreate() {
        getDriver().findElement(By.cssSelector("a[href='/manage']")).click();
        getDriver().findElement(By.cssSelector("a[href='securityRealm/']")).click();
        getDriver().findElement(By.className("jenkins-button--primary")).click();

        getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.id("username"))).sendKeys(USERNAME);

        getDriver().findElement(By.name("password1")).sendKeys(PASSWORD);
        getDriver().findElement(By.name("password2")).sendKeys(PASSWORD);
        getDriver().findElement(By.name("fullname")).sendKeys(FULL_NAME);
        getDriver().findElement(By.name("email")).sendKeys(EMAIL);
        getDriver().findElement(By.cssSelector(".jenkins-submit-button")).click();

        getWait5().until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("#people a[href='user/" + USERNAME + "/']")
        ));

        List<WebElement> rows = getDriver().findElements(By.cssSelector("#people tbody tr"));
        List<String> userList = new ArrayList<>();

        for(WebElement row : rows) {
            String user = row.findElement(By.cssSelector("td:nth-child(2) a")).getText();
            userList.add(user);
        }

        Assert.assertTrue(getDriver().findElement(By.cssSelector("#breadcrumbs > li")).getText().contains("database"));
        Assert.assertTrue(userList.contains(USERNAME));
    }

    @Test
    public void testView() {
        testCreate();
        getDriver().findElement(By.cssSelector("a[href='/manage']")).click();
        getDriver().findElement(By.cssSelector("a[href='securityRealm/']")).click();
        getDriver().findElement(By.cssSelector("#people a[href='user/" + USERNAME + "/']")).click();

        Assert.assertTrue(
                getDriver().findElement(By.cssSelector("#main-panel > div:nth-child(4)")).getText().contains(USERNAME));
        Assert.assertEquals(getDriver().findElement(By.xpath("//h1")).getText(), FULL_NAME);
    }

    @Test
    public void testVerify() {
        testCreate();
        getWait5().until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#people a[href='user/" + USERNAME + "/']"))).click();

        String[] menu = {
                "builds",
                "my-views",
                "account",
                "appearance",
                "preferences",
                "security",
                "experiments",
                "credentials"
        };

        for (String item : menu) {
            getDriver().findElement(By.cssSelector("a[href='/user/" + USERNAME + "/" + item + "']")).click();
            if(item.equals("my-views")) {
                getDriver().navigate().back();
            }
        }

        Assert.assertEquals(getDriver().findElement(By.xpath("//h1")).getText(),"Access Denied");
    }

    @Test
    public void testDelete() {
        testCreate();
        int numOfUsers = Integer.parseInt(getDriver().findElement(By.className("jenkins-app-bar__subtitle")).getText());

        getDriver().findElement(By.cssSelector("#people a[href='user/" + USERNAME + "/']")).click();
        getDriver().findElement(By.cssSelector(".confirmation-link")).click();

        String dialog = getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.className("jenkins-dialog__contents"))).getText();
        boolean isFullNameDisplayed = dialog.contains(FULL_NAME);

        getDriver().findElement(By.cssSelector("button[data-id='ok']")).click();
        getWait5().until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("a[href='/manage']"))).click();
        getDriver().findElement(By.cssSelector("a[href='securityRealm/']")).click();

        int usersLeft = Integer.parseInt(getDriver().findElement(By.className("jenkins-app-bar__subtitle")).getText());
        boolean userExists = !getDriver().findElements(By.cssSelector("#people a[href='user/" + USERNAME + "/']")).isEmpty();

        Assert.assertTrue(isFullNameDisplayed);
        Assert.assertEquals(usersLeft, numOfUsers - 1);
        Assert.assertFalse(userExists);
    }
}
