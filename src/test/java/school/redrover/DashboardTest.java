package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import school.redrover.common.BaseTest;
import school.redrover.pages.DescriptionDialog;
import school.redrover.pages.HomePage;

public class DashboardTest extends BaseTest {

    private static final String JENKINS_VERSION_TEXT = "Jenkins 2.580.1";
    private static final String DESCRIPTION_TEXT = "Dashboard description text";
    private static final String NEW_DESCRIPTION_TEXT = "Dashboard NEW description text. Version 2.0";
    private static final String ADD_DESCRIPTION_BUTTON_NAME = "Add description";
    private static final String EDIT_DESCRIPTION_BUTTON_NAME = "Edit description";

    @Test
    public void testCheckJenkinsVersion() {

        String actualJenkinsVersion = new HomePage(getDriver()).getJenkinsVersion();

        Assert.assertEquals(actualJenkinsVersion, JENKINS_VERSION_TEXT,
            "Версия Jenkins отличается от версии рекомендованной для курса");
    }

    @Test
    public void testAddDescription() {

        String actualDescription = new HomePage(getDriver())
            .openDescriptionDialog()
            .setText(DESCRIPTION_TEXT)
            .clickSave()
            .gotoHomePage()
            .getDashboardDescriptionText();

        Assert.assertEquals(actualDescription, DESCRIPTION_TEXT);
    }

    @Test
    public void testEditDescription() {

        String actualEditedDescription = new HomePage(getDriver())
            .openDescriptionDialog()
            .setText(DESCRIPTION_TEXT)
            .clickSave()
            .gotoHomePage()
            .openDescriptionDialog()
            .clear()
            .setText(NEW_DESCRIPTION_TEXT)
            .clickSave()
            .gotoHomePage()
            .getDashboardDescriptionText();

        Assert.assertEquals(actualEditedDescription, NEW_DESCRIPTION_TEXT);
    }

    @Test
    public void testDeleteDescription() {

        String actualDescription = new HomePage(getDriver())
            .openDescriptionDialog()
            .setText(DESCRIPTION_TEXT)
            .clickSave()
            .gotoHomePage()
            .openDescriptionDialog()
            .clear()
            .clickSave()
            .gotoHomePage()
            .getDashboardDescriptionText();

        Assert.assertTrue(actualDescription.isEmpty(),
            "Ожидалось пустое описание, но было получено: '" + actualDescription + "'");
    }

    @Test
    public void testAddOrEditDescriptionButtonDisplayedName() {

        String actualDescriptionButtonTextWithoutDescription = new HomePage(getDriver())
            .getDashboardDescriptionButtonText();

        String actualDescriptionButtonTextWithDescription = new HomePage(getDriver())
            .openDescriptionDialog()
            .setText(DESCRIPTION_TEXT)
            .clickSave()
            .gotoHomePage()
            .getDashboardDescriptionButtonText();

        String actualDescriptionButtonTextAfterDescriptionClear = new HomePage(getDriver())
            .openDescriptionDialog()
            .clear()
            .clickSave()
            .gotoHomePage()
            .getDashboardDescriptionButtonText();

        SoftAssert softAssert = new SoftAssert();

        softAssert.assertEquals(actualDescriptionButtonTextWithoutDescription,
            ADD_DESCRIPTION_BUTTON_NAME,
            "Текст кнопки с пустым description отличается от ожидаемого");
        softAssert.assertEquals(actualDescriptionButtonTextWithDescription,
            EDIT_DESCRIPTION_BUTTON_NAME,
            "Текст кнопки с заполненным description отличается от ожидаемого");
        softAssert.assertEquals(actualDescriptionButtonTextAfterDescriptionClear,
            ADD_DESCRIPTION_BUTTON_NAME,
            "Текст кнопки с удаленным description отличается от ожидаемого");
        softAssert.assertAll();
    }

    @Test
    public void testCheckUIElementsDescription() {

        final String expectedCancelButtonText = "Cancel";
        final String expectedSaveButtonText = "Save";
        final String expectedPreviewContainerPlain = "Plain text";
        final String expectedPreviewContainerPreview = "Preview";

        DescriptionDialog dialog = new HomePage(getDriver())
            .openDescriptionDialog()
            .clear();

        SoftAssert softAssert = new SoftAssert();

        softAssert.assertEquals(dialog.getTitle(), ADD_DESCRIPTION_BUTTON_NAME,
            "Текст заголовка отличается");
        softAssert.assertTrue(dialog.getTextArea().isDisplayed(), "Поле ввода должно отображаться");
        softAssert.assertEquals(dialog.getTextArea().getAttribute("value"), "",
            "Поле ввода не пустое");
        softAssert.assertTrue(dialog.getTextArea().isEnabled(), "Поле ввода должно быть доступно");
        softAssert.assertTrue(dialog.getPreviewContainer().isDisplayed(),
            "Контейнер с 'Plain text' должен отображаться");
        softAssert.assertTrue(
            dialog.getPreviewContainer().getText().contains(expectedPreviewContainerPlain),
            expectedPreviewContainerPlain + " отличается от ожидаемого");
        softAssert.assertEquals(dialog.getShowPreviewLink().getText(),
            expectedPreviewContainerPreview,
            expectedPreviewContainerPreview + " отличается от ожидаемого");
        softAssert.assertTrue(dialog.getShowPreviewLink().isDisplayed(),
            "Preview должно отображаться");
        softAssert.assertFalse(dialog.getHidePreviewLink().isDisplayed(),
            "Hide preview должен быть скрыт");
        softAssert.assertFalse(dialog.getPreviewBlock().isDisplayed(),
            "Блок preview должен быть скрыт");
        softAssert.assertTrue(dialog.getCancelButton().isDisplayed(),
            "Кнопка Cancel должна отображаться");
        softAssert.assertEquals(dialog.getCancelButton().getText(), expectedCancelButtonText,
            "Название кнопки отличается от " + expectedCancelButtonText);
        softAssert.assertTrue(dialog.getSaveButton().isDisplayed(),
            "Кнопка Save должна отображаться");
        softAssert.assertEquals(dialog.getSaveButton().getText(), expectedSaveButtonText,
            "Название кнопки отличается от " + expectedSaveButtonText);
        softAssert.assertAll();
    }
}