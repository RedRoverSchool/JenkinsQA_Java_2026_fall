package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.pages.HomePage;

public class MultibranchPipelineTest extends BaseTest {
    private static final String ITEM_NAME = "test-multibranch-pipeline";

    @Test
    public void testCreate() {
        String projectName = new HomePage(getDriver())
                .clickNewItem()
                .inputProjectName(ITEM_NAME)
                .selectMultibranchePipelineAndClickOk()
                .gotoHomePage()
                .getFirstProjectName();

        Assert.assertEquals(projectName, ITEM_NAME);
    }
}
