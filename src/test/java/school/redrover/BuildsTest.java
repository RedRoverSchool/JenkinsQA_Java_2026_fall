package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

import java.time.Duration;
import java.util.List;

import static java.sql.DriverManager.getDriver;

public class BuildsTest extends BaseTest {
    @Test
    public void testDeleteBuild(){
        createFreestyle();

        WebElement wait = new WebDriverWait((org.openqa.selenium.WebDriver) getDriver(), Duration.ofSeconds(10)).
                until(ExpectedConditions.elementToBeClickable((By.xpath("//a[@title='Schedule a Build for Elena Freestyle project']"))));
        wait.click();

        getDriver().findElement(By.xpath("//*[@id=\"tasks\"]/div[2]/span/a/span[1]")).click();
        getDriver().findElement(By.xpath("//*[@id=\"projectStatus\"]/tbody/tr/td[2]/a[2]")).click();
        getDriver().findElement(By.xpath("//a[@href='/job/Elena%20Freestyle%20project/1/']")).click();
        getDriver().findElement(By.xpath("//*[@id=\"tasks\"]/div[4]/span/a")).click();
        getDriver().findElement(By.name("Submit")).click();

        WebElement logo = new WebDriverWait(getDriver(), Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@class='app-jenkins-logo']")));
        logo.click();

        getDriver().findElement(By.xpath("//*[@id=\"tasks\"]/div[2]/span/a")).click();

        List<WebElement> projects = getDriver().findElements(By.linkText("Elena Freestyle project"));
        Assert.assertTrue(projects.isEmpty());
    }

    private void createFreestyle() {
        final String itemName = "Elena Freestyle project";

        getDriver().findElement(By.xpath("//*[@id=\"tasks\"]/div[1]/span/a")).click();
        getDriver().findElement(By.xpath("//*[@id=\"name\"]")).sendKeys(itemName);
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.id("ok-button")).click();
        WebElement logo = new WebDriverWait(getDriver(), Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@class='app-jenkins-logo']")));
        logo.click();
    }

}
