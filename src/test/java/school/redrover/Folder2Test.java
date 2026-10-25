package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

public class Folder2Test extends BaseTest {

    @Test
    public void testAddDescriptionToFolder() {
        final String folderName = "MyFolder";
        final String description = "My folder description";

        getDriver().findElement(By.className("content-block__link")).click();
        getDriver().findElement(By.id("name")).sendKeys(folderName);

        WebElement folder = getDriver().findElement(By.className("com_cloudbees_hudson_plugins_folder_Folder"));
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView({block: 'center'});", folder);
        folder.click();

        getDriver().findElement(By.id("ok-button")).click();

        getDriver().findElement(By.name("_.description")).sendKeys(description);
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertEquals(
                getDriver().findElement(By.id("view-message")).getText(),
                description
        );
    }
}