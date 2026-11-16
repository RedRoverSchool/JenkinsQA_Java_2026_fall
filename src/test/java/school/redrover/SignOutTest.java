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

        Assert.assertEquals(loginPage.getUsernameValue(), "", "Поле Username не пустое!");
        Assert.assertEquals(loginPage.getPasswordValue(), "", "Поле Password не пустое!");
    }

    @Test
    public void testSignOutHasNoConfirmationDialog() {
        LoginPage loginPage = new HomePage(getDriver()).clickSignOut();

        Assert.assertTrue(loginPage.getUsernameValue().isEmpty(), "Переход на страницу логина не выполнен!");
    }
}