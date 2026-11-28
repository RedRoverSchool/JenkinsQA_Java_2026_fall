package school.redrover;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.pages.HomePage;

public class Folder2Test extends BaseTest {

    @Test
    public void testAddDescriptionToFolder() {
        final String folderName = "MyFolder";
        final String description = "My folder description";

        String actualDescription = new HomePage(getDriver())
                .clickNewItem()
                .inputFolderName(folderName)
                .selectFolderAndClickOk()
                .addDescription(description)
                .clickSaveButton()
                .getDescription();
        Assert.assertEquals(actualDescription, description);
    }

    @Test
    public void testSavePipelineDescription() {
        final String pipelineName = "MyPipeline";
        final String description = "My pipeline description";

        String actualDescription = new HomePage(getDriver())
                .clickNewItem()
                .inputProjectName(pipelineName)
                .selectPipelineAndClickOk()
                .addDescription(description)
                .clickSaveButton()
                .getDescriptionText();

        Assert.assertEquals(actualDescription, description);
    }
}