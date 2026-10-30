package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

import java.time.Duration;


public class DescriptionTest extends BaseTest {

    private static final String DESCRIPTION_TEXT = "Jenkins";
    private static final String DESCRIPTION_TEXT_NEW = "JenkinsNew";
    private static final String DESCRIPTION_CONTENT = "description-content";
    private static final String DESCRIPTION_CLICK = "//a[@id='description-link']/span";
    private static final String SAVE_CLICK = "//button[@data-id='ok']";
    private static final String SIDE_PANEL = "side-panel";
    private static final String MODAL_WINDOW = "description-textarea";

    @Test
    public void testCreateDescription() {

        createDescription();

        Assert.assertEquals(getDriver().findElement(By.id(DESCRIPTION_CONTENT)).getText(), DESCRIPTION_TEXT);
    }

    @Ignore
    @Test
    public void testUpdateDescription() {

        createDescription();
        getDriver().findElement(By.xpath(DESCRIPTION_CLICK)).click();
        getDriver().findElement(By.id(MODAL_WINDOW)).click();
        getDriver().findElement(By.id(MODAL_WINDOW)).sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        getDriver().findElement(By.id(MODAL_WINDOW)).sendKeys((DESCRIPTION_TEXT_NEW));
        getDriver().findElement(By.xpath(SAVE_CLICK)).click();

        getWait10().until(ExpectedConditions.textToBePresentInElementLocated(
                By.id(DESCRIPTION_CONTENT), DESCRIPTION_TEXT_NEW));

        Assert.assertEquals(getDriver().findElement(By.id(DESCRIPTION_CONTENT)).getText(), DESCRIPTION_TEXT_NEW);
    }

    private void createDescription() {
        getDriver().findElement(By.xpath(DESCRIPTION_CLICK)).click();
        getDriver().findElement(By.id(MODAL_WINDOW)).sendKeys((DESCRIPTION_TEXT));
        getDriver().findElement(By.xpath(SAVE_CLICK)).click();
        getDriver().findElement(By.id(SIDE_PANEL)).click();
    }
}
