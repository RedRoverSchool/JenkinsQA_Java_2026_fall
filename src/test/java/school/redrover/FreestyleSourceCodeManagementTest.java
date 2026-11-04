package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

public class FreestyleSourceCodeManagementTest extends BaseTest {

    @Test
    public void testScmSectionIsPresent() {
        getDriver().findElement(By.className("content-block__link")).click();
        getDriver().findElement(By.id("name")).sendKeys("MyFreestyleProjectSCM");
        getDriver().findElement(By.xpath("//span[text()='Freestyle project']")).click();
        getDriver().findElement(By.id("ok-button")).click();

        Assert.assertTrue(
                getDriver().findElement(
                        By.xpath("//div[contains(text(), 'Source Code Management')]")
                ).isDisplayed()
        );
    }
}
