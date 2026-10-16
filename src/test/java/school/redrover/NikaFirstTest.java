package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import java.time.Duration;

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

    @Test
    public void negativeTestCreationFolderWithoutName(){
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));

        getDriver().findElement(By.xpath("(//*[@class='task-icon-link'])[1]")).click();
        getDriver().findElement(By.xpath("//*[@id='name']")).sendKeys(Keys.chord(Keys.CONTROL,folderName),Keys.BACK_SPACE);
        getDriver().findElement(By.xpath("//*[@id='name']"));
        wait.until( ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='error']")));
        Assert.assertEquals(getDriver().findElement(By.xpath("//*[@class='error']")).getText(),"This field cannot be empty, please enter a valid name");

    }
}
