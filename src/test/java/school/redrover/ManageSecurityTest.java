package school.redrover;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.pages.ManageJenkinsPage;
import school.redrover.pages.SecurityPage;

import java.util.List;


public class ManageSecurityTest extends BaseTest {

    @Test
    public void testOpen() {
        String titleText = new ManageJenkinsPage(getDriver())
                .openManage()
                .openSecurity()
                .getTitleText();

        Assert.assertEquals(titleText, "Security");
    }

    @Test
    public void testDisable() {
        boolean isCheckboxDisplayed = new ManageJenkinsPage(getDriver())
                .openManage()
                .openSecurity()
                .checkDisableRememberMe()
                .clickSave()
                .gotoHomePage()
                .clickSignOut()
                .isRememberMeCheckboxDisplayed();

        Assert.assertFalse(isCheckboxDisplayed);
    }

    @Test
    public void testSecurityRealmDefaultValueAndAvailableOptions() {
        SecurityPage securityPage = new ManageJenkinsPage(getDriver())
                .openManage()
                .openSecurity();

        String actualDefault = securityPage.getSelectSecurityRealm();
        List<String> actualOptions = securityPage.getSecurityRealmOptions();
        List<String> expectedOptions = List.of("Delegate to servlet container", "Jenkins’ own user database", "LDAP", "None");

        Assert.assertEquals(actualDefault, "Jenkins’ own user database");
        Assert.assertEquals(actualOptions, expectedOptions);
    }

    @Test
    public void testByDefaultAllowAnonymousReadAccessIsUnchecked() {
        WebElement authorizationCheckbox = new ManageJenkinsPage(getDriver())
                .openManage()
                .openSecurity()
                .getAuthorizationCheckbox();

        Assert.assertFalse(authorizationCheckbox.isSelected());
    }
}
