package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

import java.time.Duration;

public class AddDescriptionTest extends BaseTest {

    @Test
    public void testAddDescriptionToProfile() {
        final String description = "Best description";

        getDriver().findElement(By.id("root-action-UserAction")).click();
        getDriver().findElement(By.id("description-link")).click();

        WebElement textArea = getDriver().findElement(By.id("description-textarea"));
        String previousDescription = textArea.getAttribute("value");

        if (previousDescription != null && !previousDescription.isEmpty()) {
            textArea.clear();
        }

        textArea.sendKeys(description);
        getDriver().findElement(By.className("jenkins-button--primary")).click();

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        String actualDescription = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("description-content"))).getText();

        Assert.assertEquals(actualDescription, description);
    }
}
