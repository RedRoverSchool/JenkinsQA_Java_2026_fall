package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

import java.time.Duration;

public class MultibranchPipelineTest extends BaseTest {
    @Test
    public void testCreate() {
        String itemName = "test-multibranch-pipeline";
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));

        wait.until(ExpectedConditions.elementToBeClickable(By.linkText("New Item"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("name"))).sendKeys(itemName);
        WebElement multibranch=getDriver().findElement(By.xpath("//span[text()='Multibranch Pipeline']"));

        ((JavascriptExecutor) getDriver()).executeScript(
                "arguments[0].scrollIntoView({block: 'center'});", multibranch);

        wait.until(ExpectedConditions.elementToBeClickable(multibranch)).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("ok-button"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.name("Submit"))).click();
        WebElement actualProjectName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'%s')]".formatted(itemName))));

        Assert.assertEquals(actualProjectName.getText(), itemName);
    }
}
