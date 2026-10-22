package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

public class FreestyleScmTypeSelectionTest extends BaseTest {
    @Test
    public void testUserCanSelectScmType() {
        getDriver().findElement(By.className("content-block__link")).click();
        getDriver().findElement(By.id("name")).sendKeys("MyFreestyleProjectSCM");
        getDriver().findElement(By.xpath("//span[text()='Freestyle project']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.xpath("//label[text()='Git']")).click();

        Assert.assertTrue(getDriver().findElement(By.id("radio-block-1")).isSelected());
    }
}
