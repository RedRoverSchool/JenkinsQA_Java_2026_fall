package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

public class PolinaJenkinsTest extends BaseTest {

    @Test
    public void testCreateEmptyField(){

        getDriver().findElement(By.cssSelector("a[href='/view/all/newJob']")).click();
        getDriver().findElement(By.cssSelector(".jenkins-choice-list__item__icon")).click();
        getDriver().findElement(By.cssSelector("#ok-button")).click();

        Assert.assertFalse(
                getDriver().findElement(By.cssSelector("#ok-button")).isEnabled());

    }
}
