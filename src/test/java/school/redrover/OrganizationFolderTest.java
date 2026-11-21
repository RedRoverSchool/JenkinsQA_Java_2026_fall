package school.redrover;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.pages.HomePage;

public class OrganizationFolderTest extends BaseTest {

    private static final String ORGANIZATION_FOLDER_NAME = "Organization_folder";

    @Test
    public void testCreateOrganizationFolder() {
        String organizationFolderName = new HomePage(getDriver())
                .clickNewItem()
                .inputProjectName(ORGANIZATION_FOLDER_NAME)
                .selectOrganizationFolderAndClickOk()
                .gotoHomePage()
                .getFirstProjectName();

        Assert.assertEquals(organizationFolderName, ORGANIZATION_FOLDER_NAME);
    }
}
