package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

public class GroupAutumnSetTest extends BaseTest {

    @Test
    public void testCreateDescription () {
        getDriver().findElement(By.id("description-link")).click();
        getDriver().findElement(By.id("description-textarea")).sendKeys("Jenkins");
        getDriver().findElement(By.cssSelector("button[data-id='ok']")).click();

        Assert.assertEquals(getDriver().findElement(By.cssSelector("span.jenkins-mobile-hide")).getText(),"Jenkins");
    }
}
