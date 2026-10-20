package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
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

    @Test
    public void testUpdateDescription() {

        createDescription();
        descriptionClick();
        modalWindow().click();
        modalWindow().sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        modalWindow().sendKeys((DESCRIPTION_TEXT_NEW));
        saveClick();

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        wait.until(ExpectedConditions.textToBePresentInElementLocated(
                By.id(DESCRIPTION_CONTENT), DESCRIPTION_TEXT_NEW));

        Assert.assertEquals(getDriver().findElement(By.id(DESCRIPTION_CONTENT)).getText(), DESCRIPTION_TEXT_NEW);
    }

    private void createDescription() {
        descriptionClick();
        modalWindow().sendKeys((DESCRIPTION_TEXT));
        saveClick();
        getDriver().findElement(By.id(SIDE_PANEL)).click();
    }

    private WebElement modalWindow() {

        return getDriver().findElement(By.id(MODAL_WINDOW));
    }

    private void descriptionClick() {

        getDriver().findElement(By.xpath(DESCRIPTION_CLICK)).click();
    }

    private void saveClick() {

        getDriver().findElement(By.xpath(SAVE_CLICK)).click();
    }
}
