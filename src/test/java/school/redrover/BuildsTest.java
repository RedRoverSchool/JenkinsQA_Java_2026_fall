package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

import java.time.Duration;
import java.util.List;

public class BuildsTest extends BaseTest {

    @Test
    public void testDeleteBuild(){
        createFreestyle();

        WebElement wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10)).
                until(ExpectedConditions.elementToBeClickable((By.xpath("//a[@title='Schedule a Build for Elena Freestyle project']"))));
        wait.click();
        WebElement historyButton = new WebDriverWait(getDriver(),Duration.ofSeconds(10)).
                until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='/view/all/builds']")));
        historyButton.click();
        getDriver().findElement(By.xpath("//a[contains(@href, 'project/1')]")).click();

        getDriver().findElement(By.xpath("//a[contains(@href, 'confirmDelete')]")).click();
        getDriver().findElement(By.name("Submit")).click();

        WebElement logo = new WebDriverWait(getDriver(), Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(), 'Elena Freestyle project')]//ancestor::div//a[@class='app-jenkins-logo']")));
        logo.click();

        getDriver().findElement(By.xpath("//a[@href='/view/all/builds']")).click();

        List<WebElement> builds = getDriver().findElements(
                By.xpath("//a[contains(@href, '/job/Elena%20Freestyle%20project/1/')]"));
        Assert.assertTrue(builds.isEmpty());
    }

    private void createFreestyle() {
        final String itemName = "Elena Freestyle project";

        getDriver().findElement(By.xpath("//*[@id=\"tasks\"]/div[1]/span/a")).click();
        getDriver().findElement(By.xpath("//*[@id=\"name\"]")).sendKeys(itemName);
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.id("ok-button")).click();
        WebElement logo = new WebDriverWait(getDriver(), Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(), 'Configure')]//ancestor::div//a[@class='app-jenkins-logo']")));
        logo.click();
    }
}
