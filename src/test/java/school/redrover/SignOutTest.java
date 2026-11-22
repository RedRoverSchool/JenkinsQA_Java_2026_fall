package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.pages.HomePage;
import school.redrover.pages.LoginPage;

public class SignOutTest extends BaseTest {

    @Test
    public void testSignOutRedirectsToLoginPage() {
        LoginPage loginPage = new HomePage(getDriver()).clickSignOut();

        Assert.assertEquals(loginPage.getUsernameValue(), "", "Username field should be empty after logout");
        Assert.assertEquals(loginPage.getPasswordValue(), "", "Password field should be empty after logout");
    }

    @Test
    public void testSignOutHasNoConfirmationDialog() {
        LoginPage loginPage = new HomePage(getDriver()).clickSignOut();

        Assert.assertTrue(loginPage.getUsernameValue().isEmpty(), "Login page is not displayed");
    }
}
