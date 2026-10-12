package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

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

        Assert.assertEquals(getDriver().findElement(By.id("description-content")).getText(), description);
    }
}
