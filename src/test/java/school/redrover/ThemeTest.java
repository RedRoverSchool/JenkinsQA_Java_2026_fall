

package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.common.JenkinsUtils;
import school.redrover.common.ProjectUtils;
import school.redrover.pages.HomePage;

import java.time.Duration;
import java.util.List;

public class ThemeTest extends BaseTest {

    private void changeTheme() {
        new HomePage(getDriver())
                .clickAppearanceButton()
                .clickDarkOnAppearance()
                .clickSaveOnAppearance();
    }

    @Test
    public void testChangeThemeFromHomePage() {
        HomePage homePage = new HomePage(getDriver())
                .clickThemeButton()
                .clickDarkThemeOnHomePage();

        Assert.assertTrue(homePage.isDarkThemeEnabled());
    }

    @Test
    public void testChangeThemeFromAppearance() {
        changeTheme();
        HomePage homePage = new HomePage(getDriver());

        Assert.assertTrue(homePage.isDarkThemeEnabled());
    }

    @Test
    public void testCheckingThemeAfterLogOut() {
        HomePage homePage = new HomePage(getDriver());
        changeTheme();
        JenkinsUtils.logout(getDriver());
        getDriver().get(ProjectUtils.getUrl() + "login");
        JenkinsUtils.login(getDriver());

        Assert.assertTrue(homePage.isDarkThemeEnabled());
    }
}