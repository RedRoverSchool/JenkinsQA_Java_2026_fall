package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

import java.time.Duration;

public class IrinaNJenkinsTest extends BaseTest {

    @Test
    public void testCreateFolder() {
        final String itemName = "namespace";

        getDriver().findElement(By.className("content-block__link")).click();
        getDriver().findElement(By.id("name")).sendKeys(itemName);
        getDriver().findElement(By.className("com_cloudbees_hudson_plugins_folder_Folder")).click();
        getDriver().findElement(By.id("ok-button")).click();

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        WebElement headIcon = wait.until(ExpectedConditions.elementToBeClickable(By.id("jenkins-head-icon")));
        headIcon.click();

        Assert.assertEquals(
                getDriver().findElement(By.cssSelector(".jenkins-table__link > span:first-child")).getText(),
                itemName);

        getDriver().findElement(By.cssSelector(".jenkins-table__link > span:first-child")).click();
        getDriver().findElement(By.id("description-link")).click();
        getDriver().findElement(By.id("description-textarea")).sendKeys(itemName.concat(" - is a folder for future projects."));
        getDriver().findElement(By.className("textarea-show-preview")).click();
        getDriver().findElement(By.className("textarea-hide-preview")).click();
        getDriver().findElement(By.className("jenkins-button--primary")).click();




    }
}
