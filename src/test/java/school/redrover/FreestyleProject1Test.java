package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

public class FreestyleProject1Test extends BaseTest {

    @Test
    public void testCreate() {
        createFreestyleProject();
        getDriver().findElement(By.id("47-65-6e-65-72-61-6c"));
        getWait2().until(ExpectedConditions.elementToBeClickable((By.xpath("//a[@class='app-jenkins-logo']")))).click();

        Assert.assertEquals(
                getDriver().findElement(By.cssSelector(".jenkins-table__link > span:first-child")).getText(),
                "test");
    }

    @Test
    public void testEnvironmentAvailable() {
        createFreestyleProject();

        Assert.assertEquals(
                getDriver().findElement(By.xpath("(//*[@class='task-link-text'])[4]")).getText(),
                "Environment");
    }

    @Test
    public void testStatusClickable() {
        createFreestyleProject();
        getDriver().findElement(By.id("47-65-6e-65-72-61-6c"));
        getWait2().until(ExpectedConditions.elementToBeClickable((By.xpath("//a[@class='app-jenkins-logo']")))).click();

        WebElement link = getDriver().findElement(By.xpath("//a[.//span[text()='test']]"));
        String url = link.getAttribute("href");
        getDriver().get(url);

        Assert.assertEquals(
                getDriver().findElement(By.cssSelector(".task-link.task-link-no-confirm.task-link--active")).getText(),
                "Status");
    }

    public void createFreestyleProject() {
        getDriver().findElement(By.className("content-block__link")).click();
        getDriver().findElement(By.id("name")).sendKeys("test");
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.id("ok-button")).click();

    }
}
