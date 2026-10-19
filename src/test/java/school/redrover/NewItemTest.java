package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

import java.time.Duration;

public class NewItemTest extends BaseTest {

    @Test
    public void testInvalidProjectName() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
        final String project_name = "?, *, /, , ., !, %, $, &, ,, ;, :";
        String expectedErrorText = "is an unsafe character";

        getDriver().findElement(By.cssSelector("a[href*='newJob']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("name"))).sendKeys(project_name);
        wait.until(ExpectedConditions.textToBePresentInElementLocated
                ((By.xpath("//*[@id='createItem']/div[1]/div/div/div")), expectedErrorText));
        WebElement okButton = getDriver().findElement(By.id("ok-button"));
        boolean isEnabled = okButton.isEnabled();

        Assert.assertFalse(isEnabled, "Кнопка 'ОК' должна быть неактивной!");
    }
}
