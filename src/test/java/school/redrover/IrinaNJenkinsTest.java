package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

public class IrinaNJenkinsTest extends BaseTest {

    @Test
    public void testCreateFolder() {
        final String itemName = "namespace";

        getDriver().findElement(By.className("content-block__link")).click();
        getDriver().findElement(By.id("name")).sendKeys(itemName);
        getDriver().findElement(By.className("com_cloudbees_hudson_plugins_folder_Folder")).click();
        getDriver().findElement(By.id("ok-button")).click();

        getDriver().findElement(By.id("jenkins-head-icon")).click();

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
