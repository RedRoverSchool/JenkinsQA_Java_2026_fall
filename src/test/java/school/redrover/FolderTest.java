package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import java.time.Duration;


public class FolderTest extends BaseTest {

    @Test
    public void testCreateFolder() {
        String jobName = "FirstTest";
        String folderName = "New folder";
        String description = "New description text";

        getDriver().findElement(By.cssSelector("a.task-link")).click();
        getDriver().findElement(By.id("name")).sendKeys(jobName);
        getDriver().findElement(By.className("com_cloudbees_hudson_plugins_folder_Folder")).click();
        getDriver().findElement(By.id("ok-button")).click();

        getDriver().findElement(By.name("_.displayNameOrNull")).sendKeys(folderName);
        getDriver().findElement(By.name("_.description")).sendKeys(description);
        getDriver().findElement(By.name("Submit")).click();

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("/job/" + jobName + "/"));
        Assert.assertTrue(getDriver().getCurrentUrl().contains("/job/" + jobName + "/"));
        Assert.assertEquals(getDriver().findElement(By.id("view-message")).getText(), description);
    }

}
