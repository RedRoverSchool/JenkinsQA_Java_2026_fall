package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

import java.time.Duration;

public class MaksimTest extends BaseTest {

    @Test
    public void testGreatPipelineAndFolder() {
        getDriver().findElement(By.xpath("//span[@class='task-link-text' and text()='New Item']/..")).click();

        getDriver().findElement(By.id("name")).sendKeys("123");

        getDriver().findElement(By.xpath("//*[text()='Pipeline']")).click();

        getDriver().findElement(By.id("ok-button")).click();

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));

        getDriver().findElement(By.id("jenkins-head-icon")).click();
        wait.until(ExpectedConditions.elementToBeClickable((By.id("jenkins-head-icon")))).click();

        getDriver().findElement(By.xpath("//span[@class='task-link-text' and text()='New Item']/..")).click();

        getDriver().findElement(By.id("name")).sendKeys("222");

        getDriver().findElement(By.xpath("//*[text()='Folder']")).click();

        getDriver().findElement(By.id("ok-button")).click();

    }
}
