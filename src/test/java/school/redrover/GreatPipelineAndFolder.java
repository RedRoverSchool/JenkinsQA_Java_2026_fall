package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

import java.time.Duration;

public class GreatPipelineAndFolder extends BaseTest {

    @Test
    public void testGreatPipelineAndFolder() {
        getDriver().findElement(By.className("content-block__link")).click();

        getDriver().findElement(By.id("name")).sendKeys("123");

        getDriver().findElement(By.xpath("//*[text()='Pipeline']")).click();

        getDriver().findElement(By.id("ok-button")).click();

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable((By.xpath("//a[@class='app-jenkins-logo']")))).click();

        getDriver().findElement(By.className("task-icon-link")).click();

        getDriver().findElement(By.id("name")).sendKeys("222");

        getDriver().findElement(By.xpath("//*[text()='Folder']")).click();

        getDriver().findElement(By.id("ok-button")).click();

    }
}
