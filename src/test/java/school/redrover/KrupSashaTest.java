package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

public class KrupSashaTest extends BaseTest {
    @Test
    public void testNavigationByLinkRestAPI() {
        final String title = "Remote API - Jenkins";
        getDriver().findElement(By.cssSelector("#jenkins > footer > div > div.page-footer__links > a")).click();
        Assert.assertEquals(getDriver().getTitle(), title);
    }
}
