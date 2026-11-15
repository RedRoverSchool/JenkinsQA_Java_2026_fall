package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

import java.time.Duration;

public class GreatPipelineAndFolderTest extends BaseTest {

    @Test
    public void testGreatPipelineAndFolder() {
        getDriver().findElement(By.className("content-block__link")).click();
        getDriver().findElement(By.id("name")).sendKeys("123");
        getDriver().findElement(By.xpath("//*[text()='Pipeline']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.id("jenkins-head-icon")).click();

        getDriver().findElement(By.xpath("//span[@class='task-link-text' and text()='New Item']/..")).click();
        getDriver().findElement(By.id("name")).sendKeys("222");
        getDriver().findElement(By.xpath("//*[text()='Folder']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.id("jenkins-head-icon")).click();

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("projectstatus")));
        String pageText = getDriver().findElement(By.id("projectstatus")).getText(); // Берем текст всей таблицы
        Assert.assertTrue(pageText.contains("123"), "Pipeline '123' не найден на главной странице!");
        Assert.assertTrue(pageText.contains("222"), "Folder '222' не найден на главной странице!");


    }
}
