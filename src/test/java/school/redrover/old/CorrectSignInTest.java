package school.redrover.old;

import org.openqa.selenium.By;

import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

import java.time.Duration;

public class CorrectSignInTest extends BaseTest {

    @Test
    public void testCreateEmptyField() {

        getDriver().findElement(By.cssSelector(".jenkins-avatar"));
        getDriver().findElement(By.cssSelector(".app-branding app-sign-in-register__branding")).click();

        String expectedUrl = "http://localhost:8080";
        String actualUrl = getDriver().getCurrentUrl();

        Assert.assertEquals(actualUrl, expectedUrl,
                "Адрес страницы не совпадает с ожидаемым!");
    }

}