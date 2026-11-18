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

    private static final String PROJECT_NAME = "Newproject";

    public void createFreestyle() {
        new HomePage(getDriver())
                .clickNewItem()
                .inputProjectName(PROJECT_NAME)
                .selectFreestyleAndClickOK()
                .clickSaveButton()
                .gotoHomePage()
                .getFirstProjectName();
    }

    @Test
    public void testDeleteBuild(){
        createFreestyle();
        BuildHistoryPage deleteBuild = new HomePage(getDriver())
                .clickSceduleABuild(PROJECT_NAME)
                .clickBuildsHistory()
                .clickBuildName(PROJECT_NAME)
                .deleteBuild()
                .clickSubmitButton()
                .gotoHomePage()
                .clickBuildsHistory();

        Assert.assertTrue(deleteBuild.buildsIsEmpty(PROJECT_NAME));
    }
}
