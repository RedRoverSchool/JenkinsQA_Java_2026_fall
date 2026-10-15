package school.redrover;

import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

import static org.testng.Assert.*;


public class YanaAndrTest extends BaseTest {

    @Test
    public void testJenkinsIsOpened() {
        assertNotNull(getDriver().getTitle(), "Title not found");

        assertTrue(getDriver().getTitle().contains("Jenkins"), "Jenkins didn't open");
    }
}