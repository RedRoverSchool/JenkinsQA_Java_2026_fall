package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

public class Pipeline3Test extends BaseTest {

    private static final String NEW_ITEM_TEXT = "New Item";
    private static final String NEW_ITEM_TEXT_ONE = "New Item1";

    @Test
    public void testCreatePipeline() {
        getDriver().findElement(By.xpath("//span[normalize-space(text())='Create a job']")).click();
        getDriver().findElement(By.xpath("//span[contains(@class, 'jenkins-choice-list__item__label') and normalize-space(text())='Pipeline']")).click();
        getDriver().findElement(By.id("name")).sendKeys(NEW_ITEM_TEXT);
        getDriver().findElement(By.id("ok-button")).click();
        getWait5().until(ExpectedConditions.visibilityOfElementLocated((By.className("jenkins-mobile-hide")))).click();

        getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(@class, 'jenkins-table__link')]//span[text()='" + NEW_ITEM_TEXT + "']")
        ));

        Assert.assertEquals(getDriver().findElement(By.xpath("//a[contains(@class, 'jenkins-table__link')]//span[text()='" + NEW_ITEM_TEXT + "']")).getText(), NEW_ITEM_TEXT);
    }

    @Test(dependsOnMethods = "testCreatePipeline")
    public void testRenamePipeline() {
        getDriver().findElement((By.className("jenkins-mobile-hide"))).click();
        getDriver().findElement((By.xpath("//span[text()='" + NEW_ITEM_TEXT + "']")));
        getDriver().findElement((By.xpath("//button[contains(@data-href, 'New%20Item')]"))).click();
        getWait5().until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(@href, 'confirm-rename')]"))).click();

        WebElement renameInput = getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[name='newName']")));

        renameInput.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        renameInput.sendKeys(NEW_ITEM_TEXT_ONE);
        getWait10().until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@name='Submit']"))).click();
        getWait5().until(ExpectedConditions.elementToBeClickable((By.className("jenkins-mobile-hide")))).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//span[text()='" + NEW_ITEM_TEXT_ONE + "']")).getText(), NEW_ITEM_TEXT_ONE);
    }

}