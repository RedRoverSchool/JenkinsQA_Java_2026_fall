package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

public class VickyVTest extends BaseTest {

    @Test
    public void testCreateFolderInNewItem() {

        getDriver().findElement(By.className("task-icon-link")).click();

        Assert.assertTrue(getDriver().getCurrentUrl().contains("/view/all/newJob"));

        getDriver().findElement(By.id("name")).sendKeys("Name create folder");
        getDriver().findElement(By.className("com_cloudbees_hudson_plugins_folder_Folder")).click();
        getDriver().findElement(By.id("ok-button")).click();

        getDriver().findElement(By.name("_.description")).sendKeys("Folder for testing");
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertTrue(getDriver().getCurrentUrl().contains("/job/Name%20create%20folder/"));
    }
}
