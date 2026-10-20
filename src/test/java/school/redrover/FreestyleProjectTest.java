package school.redrover;
import school.redrover.common.BaseTest;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class FreestyleProjectTest extends BaseTest {
    final String itemName = "test";
    final String description = "test description";

    @Test
    public void testCreateFreestyleProject(){

        getDriver().findElement(By.cssSelector("#tasks > div:nth-child(1) > span")).click();
        getDriver().findElement(By.id("name")).sendKeys(itemName);
        getDriver().findElement(By.xpath("/html/body/div[2]/div/div/form/fieldset/div/div/div[1]/label/div[2]")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.xpath("//*[@id=\"main-panel\"]/form/div[1]/div[2]/div/div[2]/textarea")).sendKeys(description);
        getDriver().findElement(By.cssSelector("#bottom-sticker > div > button.jenkins-button.jenkins-submit-button.jenkins-button--primary")).click();
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#page-header > div.jenkins-header__main > div > a > span"))).click();

        Assert.assertEquals(
                getDriver().findElement(By.cssSelector("#job_test > td:nth-child(3) > a > span")).getText(),
                itemName);
    }
    @Test
    public void testApplyButton(){
        getDriver().findElement(By.cssSelector("#tasks > div:nth-child(1) > span")).click();
        getDriver().findElement(By.id("name")).sendKeys(itemName);
        getDriver().findElement(By.xpath("/html/body/div[2]/div/div/form/fieldset/div/div/div[1]/label/div[2]")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.cssSelector("#bottom-sticker > div > button.jenkins-button.apply-button")).click();
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        WebElement notification = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#notification-bar")));

        Assert.assertEquals(notification.getText(), "Saved");
    }
}
