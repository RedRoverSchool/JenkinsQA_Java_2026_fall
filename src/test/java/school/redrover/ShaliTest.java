package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

import java.time.Duration;

public class ShaliTest extends BaseTest {
    String textTest = "Test";

    @Test
    public void createFolderTest() {

        getDriver().findElement(By.xpath("//span[@class='task-link-text' and text()='New Item']/..")).click();
        getDriver().findElement(By.id("name")).sendKeys(textTest);
        getDriver().findElement(By.xpath("//*[text()= 'Folder']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable((By.id("jenkins-head-icon")))).click();

        WebElement chevron = getDriver().findElement(By.className("jenkins-menu-dropdown-chevron"));
        String hrefAttribute = chevron.getAttribute("data-href");
        Assert.assertTrue(hrefAttribute.contains("/job/Test/"));
    }

    @Test
    public void createPipeline() {
        getDriver().findElement(By.xpath("//span[@class='task-link-text' and text()='New Item']/..")).click();
        getDriver().findElement(By.id("name")).sendKeys(textTest);
        getDriver().findElement(By.xpath("//*[text() = 'Pipeline']")).click();
        getDriver().findElement(By.id("ok-button")).click();
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable((By.id("jenkins-head-icon")))).click();

        WebElement chevron = getDriver().findElement(By.className("jenkins-menu-dropdown-chevron"));
        String hrefAttribute = chevron.getAttribute("data-href");
        Assert.assertTrue(hrefAttribute.contains("/job/Test/"));

        getDriver().findElement(By.xpath("//*[@data-title = 'Add description']")).click();
        getDriver().findElement(By.id("description-textarea")).sendKeys("New test");
        getDriver().findElement(By.xpath("//*[contains(@class, 'jenkins-button--primary')]")).click();
        getDriver().findElement(By.xpath("//span[text()='Edit description']")).click();

        Assert.assertEquals(getDriver().findElement(By.id("description-textarea")).
                getAttribute("value"), "New test");
    }
}
