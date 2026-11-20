package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.pages.ManageJenkinsPage;
import school.redrover.pages.UserProfilePage;
import school.redrover.pages.UsersPage;

public class ManageUsersTest extends BaseTest {

    private static final String USERNAME = "jsmith";
    private static final String PASSWORD = "unique#!1IDoF3";
    private static final String FULL_NAME = "John Smith";
    private static final String EMAIL = "jsmith@jsm.com";

    @Test
    public void testCreate() {
        UsersPage usersPage = new ManageJenkinsPage(getDriver())
                .openManage()
                .openUsers()
                .clickCreateUser()
                .enterUsername(USERNAME)
                .enterPassword(PASSWORD)
                .enterFullName(FULL_NAME)
                .enterEmail(EMAIL)
                .clickCreateButton()
                .waitUntilUserCreated(USERNAME);

        Assert.assertTrue(usersPage.getFirstBreadcrumbText().contains("database"));
        Assert.assertTrue(usersPage.getUserList().contains(USERNAME));
    }

    @Test
    public void testView() {
        testCreate();
        UserProfilePage userProfilePage = new ManageJenkinsPage(getDriver())
                .openManage()
                .openUsers()
                .openUser(USERNAME);

        Assert.assertTrue(userProfilePage.getUserIdText().contains(USERNAME));
        Assert.assertEquals(userProfilePage.getFullNameHeading(), FULL_NAME);
    }

    @Test
    public void testVerify() {
        testCreate();
        UserProfilePage userProfilePage = new UsersPage(getDriver())
                .openUser(USERNAME)
                .clickMenuItems(USERNAME);

        Assert.assertEquals(userProfilePage.getFullNameHeading(),"Access Denied");
    }

    @Test
    public void testDelete() {
        testCreate();
        UsersPage usersPage = new UsersPage(getDriver());
        int numOfUsers = usersPage.getUsersCount();

        UserProfilePage userProfilePage = usersPage.openUser(USERNAME)
                        .clickDeleteLink();
        String dialogText = userProfilePage.getConfirmation();
        boolean isFullNameDisplayed = dialogText.contains(FULL_NAME);

        UsersPage updatedUsersPage = userProfilePage.confirmDelete()
                .openManage()
                .openUsers();

        int usersLeft = updatedUsersPage.getUsersCount();
        boolean userExists = updatedUsersPage.isUserPresent(USERNAME);

        Assert.assertTrue(isFullNameDisplayed);
        Assert.assertEquals(usersLeft, numOfUsers - 1);
        Assert.assertFalse(userExists);
    }
}
