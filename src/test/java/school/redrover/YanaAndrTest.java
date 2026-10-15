package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

import static org.testng.Assert.*;


public class YanaAndrTest extends BaseTest {

    @Test
    public void testJenkinsIsOpened() {
        assertNotNull(getDriver().getTitle(), "Title not found");
        assertEquals(getDriver().getTitle(), "Sign in - Jenkins",
                "Jenkins title is not 'Sign in - Jenkins'");
    }
}