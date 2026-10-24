package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import java.time.Duration;

public class OrganizationFolderTest extends BaseTest {
    @Test
    public void testCreateOrganizationFolder() {
        final String expectedResult = "Organization_folder";

        getDriver().findElement(By.xpath("//span[@class='task-link-wrapper ']")).click();
        getDriver().findElement(By.id("name")).sendKeys(expectedResult);

        WebDriver driver = getDriver();
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("window.scrollBy(0, 1000)", "");
        getDriver().findElement(By.className("jenkins_branch_OrganizationFolder")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.xpath("//button[@value='Save']")).click();

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(20));
        wait.until(ExpectedConditions.elementToBeClickable(By.id("jenkins-head-icon"))).click();
        String actualResult = getDriver().findElement(By.className("jenkins-table__link")).getAttribute("href");

        Assert.assertTrue(actualResult.contains(expectedResult));
    }
}
