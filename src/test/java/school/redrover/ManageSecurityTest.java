package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

import java.util.List;
import java.util.ArrayList;

public class ManageSecurityTest extends BaseTest {

    @Test
    public void testOpen() {
        getDriver().findElement(By.cssSelector("a[href='/manage']")).click();
        getDriver().findElement(By.cssSelector("a[href='configureSecurity']")).click();

        Assert.assertEquals(getDriver().findElement(By.className("jenkins-app-bar__content")).getText(), "Security");
    }

    @Ignore
    @Test
    public void testDisable() {
        testOpen();
        if (getDriver().findElement(By.name("_.disableRememberMe")).isSelected()) {
            getDriver().findElement(By.name("_.disableRememberMe")).sendKeys(Keys.SPACE);
        }

        getDriver().findElement(By.name("_.disableRememberMe")).sendKeys(Keys.SPACE);
        getDriver().findElement(By.cssSelector(".jenkins-submit-button")).click();

        WebElement userMenuButton = getWait5().until(ExpectedConditions.presenceOfElementLocated(
                By.id("root-action-UserAction")));

        Actions actions = new Actions(getDriver());
        actions.moveToElement(userMenuButton).perform();

        WebElement signOutLink = getWait10().until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("a[href='/logout']")));
        signOutLink.click();

        Assert.assertEquals(getDriver().findElements(By.className("jenkins-checkbox")).size(), 0);
    }

    @Test
    public void testSecurityRealmDefaultValueAndAvailableOptions() {
        testOpen();

        WebElement realmDropdown = getDriver().findElement(By.xpath("//div[contains(text(),'Security Realm')]/..//select"));
        Select selectRealm = new Select(realmDropdown);
        String actualDefault = selectRealm.getFirstSelectedOption().getText();

        List<WebElement> optionsRealm = selectRealm.getOptions();
        List<String> actualOptions = new ArrayList<>();
        for (WebElement option : optionsRealm) {
            actualOptions.add(option.getText());
        }
        List<String> expectedOptions = List.of("Delegate to servlet container", "Jenkins’ own user database", "LDAP", "None");

        Assert.assertEquals(actualDefault, "Jenkins’ own user database");
        Assert.assertEquals(actualOptions, expectedOptions);
    }
}
