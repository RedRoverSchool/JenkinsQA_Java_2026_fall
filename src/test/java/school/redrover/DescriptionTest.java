package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;


public class DescriptionTest extends BaseTest {

    private static final String DESCRIPTION_TEXT = "Jenkins";

    @Test
    public void testCreateDescription () {


        getDriver().findElement(By.xpath("//a[@id='description-link']/span")).click();
        getDriver().findElement(By.id("description-textarea")).sendKeys((DESCRIPTION_TEXT));
         getDriver().findElement(By.xpath("//button[@data-id='ok']")).click();
        getDriver().findElement(By.id("side-panel")).click();

        Assert.assertEquals(getDriver().findElement(By.id("description-content")).getText(),DESCRIPTION_TEXT);
    }


}
