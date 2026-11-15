package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;


public class GreatPipelineAndFolderTest extends BaseTest {

    @Test
    public void testGreatPipeline() {
        getDriver().findElement(By.className("content-block__link")).click();
        getDriver().findElement(By.id("name")).sendKeys("123");
        getDriver().findElement(By.xpath("//*[text()='Pipeline']")).click();
        getDriver().findElement(By.id("ok-button")).click();

        getWait10().until(ExpectedConditions.elementToBeClickable(By.id("jenkins-head-icon"))).click();
        getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.id("projectstatus")));

        String pageText = getDriver().findElement(By.id("projectstatus")).getText();
        Assert.assertTrue(pageText.contains("123"), "Pipeline '123' не найден на главной странице!");

    }

    @Test
    public void testGreatFolder() {
        getDriver().findElement(By.className("content-block__link")).click();
        getDriver().findElement(By.id("name")).sendKeys("222");
        getDriver().findElement(By.xpath("//*[text()='Folder']")).click();
        getDriver().findElement(By.id("ok-button")).click();

        getWait10().until(ExpectedConditions.elementToBeClickable(By.id("jenkins-head-icon"))).click();
        getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.id("projectstatus")));

        String pageText = getDriver().findElement(By.id("projectstatus")).getText();
        Assert.assertTrue(pageText.contains("222"), "Folder '222' не найден на главной странице!");

    }
}
