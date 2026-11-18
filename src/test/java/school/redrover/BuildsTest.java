package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.pages.BuildHistoryPage;
import school.redrover.pages.HomePage;

import java.time.Duration;
import java.util.List;

import static org.testng.Assert.assertEquals;

public class BuildsTest extends BaseTest {

    private static final String PROJECT_NAME = "Elena Freestyle project";

    @Test
    public void createFreestyle() {
        String createProject = new HomePage(getDriver())
                .clickNewItem()
                .inputProjectName(PROJECT_NAME)
                .selectFreestyle()
                .clickOK()
                .clickSaveButton()
                .goToHomePage()
                .getFirstProjectName();
        assertEquals(createProject, PROJECT_NAME);
    }

    @Test (dependsOnMethods = "createFreestyle")
    public void testDeleteBuild(){
        BuildHistoryPage deleteBuild = new HomePage(getDriver())
                .clickSceduleABuild()
                .clickBuildsHistory()
                .clickBuildName()
                .deleteBuildButton()
                .clickSubmitButton()
                .goToHomePage()
                .clickBuildsHistory();

        Assert.assertTrue(deleteBuild.buildsIsEmpty());
    }
}
