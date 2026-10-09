import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

public class PugachTest extends BaseTest {

    @Test
    public void testDisplayedVersion() {

        final String version = "Jenkins 2.580.1";

        Assert.assertEquals(getDriver().findElement(By.className("jenkins_ver")).getText(),
            version);
    }
}
