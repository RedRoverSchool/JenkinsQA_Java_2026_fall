package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

public class KrupSashaTest extends BaseTest {
    @Test
    public void testAddDescriptionTurnsToEditDescription() {
        String testInput = "test";
        String label = "Edit description";
        getDriver().findElement(By.id("description-link")).click();
        getDriver().findElement(By.id("description-textarea")).sendKeys(testInput);
        getDriver().findElement(By.cssSelector("#bottom-sticker > div > button.jenkins-button.jenkins-button--primary")).click();
        getDriver().findElement(By.cssSelector("#description-link > span"));
        Assert.assertEquals(getDriver().findElement(By.cssSelector("#description-link > span")).getText(), label);
    }
    @Test
    public void testNavigationByLinkRestAPI() {
        String title = "Remote API - Jenkins";
        getDriver().findElement(By.cssSelector("#jenkins > footer > div > div.page-footer__links > a")).click();
        Assert.assertEquals(getDriver().getTitle(), title);
    }
}
