package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import org.openqa.selenium.WebElement;

public class FreestyleTest extends BaseTest {

    private void createProject() {
        final String itemName = "Elena Freestyle project";

        getDriver().findElement(By.xpath("//*[@id=\"tasks\"]/div[1]/span/a")).click();
        getDriver().findElement(By.xpath("//*[@id=\"name\"]")).sendKeys(itemName);
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.id("ok-button")).click();
        WebElement headIcon = new WebDriverWait(getDriver(), Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(By.id("jenkins-head-icon")));
        headIcon.click();
    }

    public void disableProject() {
        getDriver().findElement(By.xpath("//span[text()='Elena Freestyle project']")).click();
        getDriver().findElement(By.xpath("//a[contains(@class, 'task-link') and contains(@href, 'configure')]")).click();
        getDriver().findElement(By.cssSelector("label[for='enable-disable-project']")).click();
        getDriver().findElement(By.name("Submit")).click();

        new WebDriverWait(getDriver(), Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.className("warning")));
    }

    @Test
    public void testCreateProject() {
        final String itemName = "Elena Freestyle project";
        createProject();

        Assert.assertEquals(getDriver().findElement(By.xpath("//*[@id=\"job_Elena Freestyle project\"]/td[3]/a")).getText(),
                itemName);
    }

    @Test
    public void testDisableProject() {

        final String disableText = "This project is currently disabled";
        createProject();
        disableProject();

        String actualText = getDriver().findElement(By.className("warning")).getAttribute("innerText");

        Assert.assertTrue(actualText.contains(disableText));
    }

    @Test
    public void testEnableProject() {
        createProject();
        disableProject();

        getDriver().findElement(By.name("Submit")).click();

        boolean isWarningGone;
        try {
            isWarningGone = new WebDriverWait(getDriver(), Duration.ofSeconds(10))
                    .until(ExpectedConditions.invisibilityOfElementLocated(By.className("warning")));
        } catch (Exception e) {
            isWarningGone = true;
        }

        Assert.assertTrue(isWarningGone);
    }

    @Test
    public void testNewItemCopyFrom(){
        createProject();
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));

        WebElement newItem = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href='/view/all/newJob']")));
        newItem.click();

        getDriver().findElement(By.id("name")).sendKeys("Copied item");
        getDriver().findElement(By.xpath("//*[@id=\"duplicate-job\"]/div[2]")).click();
        getDriver().findElement(By.id("from")).sendKeys("Elena Freestyle project");
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("Submit")).click();

        WebElement headIcon = new WebDriverWait(getDriver(), Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(By.id("jenkins-head-icon")));
        headIcon.click();

        WebElement copiedProject = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//table[@id='projectstatus']//a[normalize-space()='Copied item']")
        ));

        Assert.assertEquals(copiedProject.getText(), "Copied item");
    }

    @Test
    public void testTriggerBuildsRemotely(){
        getDriver().findElement(By.className("task-link-no-confirm")).click();
        getDriver().findElement(By.id("name")).sendKeys("new item");
        getDriver().findElement(By.xpath("//*[@id=\"items\"]/div[2]/label/span")).click();
        getDriver().findElement(By.id("ok-button")).click();

        WebElement checkBox = getDriver().findElement(By.xpath("//input[@name='pseudoRemoteTrigger']/following-sibling::label"));
        ((JavascriptExecutor)getDriver()).executeScript("arguments[0].scrollIntoView({block: 'center'});", checkBox);
        checkBox.click();

        getDriver().findElement(By.name("authToken")).sendKeys("1234");
        getDriver().findElement(By.name("Submit")).click();

        getDriver().findElement(By.id("jenkins-head-icon")).click();
        WebElement newProject = new WebDriverWait(getDriver(),Duration.ofSeconds(10)).
                until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table[@id='projectstatus']//a[normalize-space()='new item']")));
        newProject.click();
        getDriver().findElement(By.xpath("//*[@id=\"tasks\"]/div[6]/span/a")).click();

        WebElement checkBox2 = new WebDriverWait(getDriver(), Duration.ofSeconds(10)).
                until(ExpectedConditions.presenceOfElementLocated(By.name("pseudoRemoteTrigger")));

        Assert.assertTrue(checkBox2.isSelected());
    }
}
