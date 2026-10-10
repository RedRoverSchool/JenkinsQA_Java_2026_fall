package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import school.redrover.common.BaseTest;

public class PugachTest extends BaseTest {

    private static final By DASHBOARD_DESCRIPTION_BUTTON = By.id("description-link");
    private static final By DASHBOARD_DESCRIPTION_TEXTAREA = By.id("description-textarea");
    private static final By DASHBOARD_DESCRIPTION_CONTENT = By.id("description-content");
    private static final By DESCRIPTION_SAVE_BUTTON = By.cssSelector("button[data-id='ok']");
    private static final By JENKINS_HEAD_ICON = By.id("jenkins-head-icon");
    private static final By JENKINS_VERSION_LABEL = By.className("jenkins_ver");

    private static final String JENKINS_VERSION_TEXT = "Jenkins 2.580.1";
    private static final String DASHBOARD_DESCRIPTION_TEXT = "Dashboard description text";
    private static final String DASHBOARD_NEW_DESCRIPTION_TEXT = "Dashboard NEW description text. Version 2.0";
    private static final String ADD_DASHBOARD_DESCRIPTION_BUTTON_NAME = "Add description";
    private static final String EDIT_DASHBOARD_DESCRIPTION_BUTTON_NAME = "Edit description";

    @Test(description = "Проверка версии Jenkins")
    public void testDisplayedVersion() {

        Assert.assertEquals(getDriver().findElement(JENKINS_VERSION_LABEL).getText(),
            JENKINS_VERSION_TEXT, "Версия Jenkins отличается от версии рекомендованной для курса");
    }

    @Test(description = "Добавление и отображение добавленного dashboard description")
    public void testAddDashboardDescription() {

        putDashboardDescription(DASHBOARD_DESCRIPTION_TEXT);

        Assert.assertEquals(getDriver().findElement(DASHBOARD_DESCRIPTION_CONTENT).getText(),
            DASHBOARD_DESCRIPTION_TEXT);
    }

    @Test(description = "Изменение и отображение измененного dashboard description")
    public void testEditDashboardDescription() {

        putDashboardDescription(DASHBOARD_DESCRIPTION_TEXT);
        putDashboardDescription(DASHBOARD_NEW_DESCRIPTION_TEXT);

        Assert.assertEquals(getDriver().findElement(DASHBOARD_DESCRIPTION_CONTENT).getText(),
            DASHBOARD_NEW_DESCRIPTION_TEXT);
    }

    @Test(description = "Удаление dashboard description")
    public void testDeleteDashboardDescription() {

        putDashboardDescription(DASHBOARD_DESCRIPTION_TEXT);
        deleteDashboardDescription();
        String actualDescription = getDriver().findElement(DASHBOARD_DESCRIPTION_CONTENT).getText();

        Assert.assertTrue(actualDescription.isEmpty(),
            "Ожидалось пустое описание, но было получено: '" + actualDescription + "'");
    }

    @Test(description = "Наименование кнопки редактирования dashboard description в зависимости от заполненности dashboard description")
    public void testAddOrEditDashboardDescriptionButtonDisplayedName() {

        String actualDescriptionButtonTextWithoutDescription =
            getDriver().findElement(DASHBOARD_DESCRIPTION_BUTTON).getText();

        putDashboardDescription(DASHBOARD_DESCRIPTION_TEXT);
        String actualDescriptionButtonTextWithDescription =
            getDriver().findElement(DASHBOARD_DESCRIPTION_BUTTON).getText();

        deleteDashboardDescription();
        String actualDescriptionButtonTextAfterDescriptionClear =
            getDriver().findElement(DASHBOARD_DESCRIPTION_BUTTON).getText();

        SoftAssert softAssert = new SoftAssert();

        softAssert.assertEquals(actualDescriptionButtonTextWithoutDescription,
            ADD_DASHBOARD_DESCRIPTION_BUTTON_NAME,
            "Текст кнопки с пустым description отличается от ожидаемого");
        softAssert.assertEquals(actualDescriptionButtonTextWithDescription,
            EDIT_DASHBOARD_DESCRIPTION_BUTTON_NAME,
            "Текст кнопки с заполненным description отличается от ожидаемого");
        softAssert.assertEquals(actualDescriptionButtonTextAfterDescriptionClear,
            ADD_DASHBOARD_DESCRIPTION_BUTTON_NAME,
            "Текст кнопки с удаленным description отличается от ожидаемого");
        softAssert.assertAll();
    }

    private void putDashboardDescription(String text) {

        openAndClearDashboardDescriptionEditor();
        getDriver().findElement(DASHBOARD_DESCRIPTION_TEXTAREA).sendKeys(text);
        saveOpenedDashboardDescriptionEditor();
    }

    private void openAndClearDashboardDescriptionEditor() {
        getDriver().findElement(DASHBOARD_DESCRIPTION_BUTTON).click();
        getDriver().findElement(DASHBOARD_DESCRIPTION_TEXTAREA).clear();
    }

    private void saveOpenedDashboardDescriptionEditor() {
        getDriver().findElement(DESCRIPTION_SAVE_BUTTON).click();
        getDriver().findElement(JENKINS_HEAD_ICON).click();
    }

    private void deleteDashboardDescription() {

        openAndClearDashboardDescriptionEditor();
        saveOpenedDashboardDescriptionEditor();
    }
}
