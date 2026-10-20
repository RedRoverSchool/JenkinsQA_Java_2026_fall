import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import java.time.Duration;

public class AidaJenkinsTest extends BaseTest {

    @Test
    public void testJenkins() {

        final String input = "Apply";
        final String string = "//*[normalize-space(text())='Saved']";

        getDriver().findElement(By.id("root-action-UserAction")).click();
        getDriver().findElement(By.id("tasks")).click();
        getDriver().findElement(By.cssSelector("label[for='radio-block-1']")).click();

        getDriver().findElement(By.name(input)).click();

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        By savedNotification = By.xpath(string);
        WebElement notification = wait.until(
                ExpectedConditions.visibilityOfElementLocated(savedNotification));

        Assert.assertEquals(notification.getText().trim(), "Saved");
    }
}
