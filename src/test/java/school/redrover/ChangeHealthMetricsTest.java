package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;


public class ChangeHealthMetricsTest extends BaseTest {

    private final By HEALTH_METRICS_BUTTON = By.cssSelector("button[suffix='healthMetrics']");
    private final By HEALTH_METRICS_BUTTON2 = By.cssSelector("button.jenkins-button.advanced-button.advancedButton");

    @BeforeMethod
    public void createJob() {
        getDriver().findElement(By.cssSelector("a.content-block__link")).click();
        WebElement field = getDriver().findElement(By.id("name"));
        field.click();
        field.sendKeys("New test Job");
        WebElement type = getDriver().findElement(By.cssSelector("#items > div:nth-child(5)"));
        type.click();
        getDriver().findElement(By.id("ok-button")).click();

    }
    @Test
    public void visibleHealthMetricsButton(){
        WebElement button = getWait10().until(ExpectedConditions.visibilityOfElementLocated(HEALTH_METRICS_BUTTON2));
        Assert.assertTrue(button.isDisplayed());
    }
    @Test
    public void clickHealthMetricsButton(){
        ((JavascriptExecutor) getDriver()).executeScript(
                "arguments[0].scrollIntoView({block: 'center', inline: 'nearest'});",
                getDriver().findElement(HEALTH_METRICS_BUTTON2)
        );
        getDriver().findElement(HEALTH_METRICS_BUTTON2).click();
        Assert.assertEquals(
                getDriver().findElement(HEALTH_METRICS_BUTTON).getText().trim(),
                "Add metric"
        );
    }
}
