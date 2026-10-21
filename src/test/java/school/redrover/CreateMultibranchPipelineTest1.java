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

public class CreateMultibranchPipelineTest1 extends BaseTest {
    @Test
    public void testCreateMultibranchPipeline() {
        String itemName = "test-pipeline";

        new WebDriverWait(getDriver(), Duration.ofSeconds(10)).until(ExpectedConditions.elementToBeClickable(By.linkText("New Item")))
                .click();

        new WebDriverWait(getDriver(), Duration.ofSeconds(10)).until(ExpectedConditions.visibilityOfElementLocated(By.id("name")))
                .sendKeys(itemName);
        WebElement multibranch=getDriver().findElement(By.xpath("//span[text()='Multibranch Pipeline']"));

        ((JavascriptExecutor) getDriver()).executeScript(
                "arguments[0].scrollIntoView({block: 'center'});", multibranch);

        new WebDriverWait(getDriver(), Duration.ofSeconds(10)).until(ExpectedConditions.elementToBeClickable(multibranch)).click();
        new WebDriverWait(getDriver(), Duration.ofSeconds(10)).until(ExpectedConditions.elementToBeClickable(By.id("ok-button")))
                .click();

        new WebDriverWait(getDriver(), Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(By.name("Submit")))
                .click();
        WebElement actualProjectName = new WebDriverWait(getDriver(), Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'%s')]".formatted(itemName))));

        Assert.assertEquals(actualProjectName.getText(), itemName);
    }
}
