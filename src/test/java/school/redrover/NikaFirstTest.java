package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;


public class NikaFirstTest extends BaseTest {
  private final  String folderName = "Folder for new project";


    @Test
    public void testCreationFolder(){

        getDriver().findElement(By.xpath("(//*[@class='task-icon-link'])[1]")).click();
        getDriver().findElement(By.xpath("//*[@id='name']")).sendKeys(folderName);
        getDriver().findElement(By.xpath("(//*[@class='icon-xlg'])[4]")).click();
        getDriver().findElement(By.xpath("//span[text()='Folder']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.xpath("//*[@class='jenkins-mobile-hide']")).click();
        getDriver().findElement(By.id("jenkins-head-icon")).click();
        Assert.assertEquals(getDriver().findElement(By.xpath("(//*[@class='jenkins-table__link model-link inside'])[1]/span")).getText(),folderName);
    }

}
