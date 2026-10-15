package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

public class YaPitonETestJenkins extends BaseTest {

    @Test
    public void testCreatFolder() {
        final String folderName = "FirstFolderTest";

        getDriver().findElement(By.xpath("//a[@href=\"/view/all/newJob\"]")).click();
        getDriver().findElement(By.id("name")).sendKeys("TestFolderField");
        getDriver().findElement(By.xpath("//label[span[text()=\"Folder\"]]")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.name("_.displayNameOrNull")).sendKeys("FirstFolderTest");
        getDriver().findElement(By.name("Submit")).click();

        Assert.assertEquals(
                getDriver().findElement(By.className("job-index-headline")).getText(),
                folderName
        );

        getDriver().findElement(By.xpath("//a[@data-title=\"Delete Folder\"]")).click();
        getDriver().findElement(By.className("jenkins-button--primary")).click();

    }

}
