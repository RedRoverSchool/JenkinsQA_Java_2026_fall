package school.redrover;

import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.pages.FreestyleConfigurationPage;
import school.redrover.pages.HomePage;
import school.redrover.pages.StatusPage;

public class FreestyleProject2Test extends BaseTest {

    private static final String PROJECT_NAME = "Freestyle_Description";
    private static final String DESK_TEXT = "This is my test";
    private static final String DESK_ADD_TEXT = "A new text for description";
    private static final String DESK_EDIT_TEXT = "I edited the text";
    private static final String OLD_TEXT = "This is old text";

    @Test
    public void testFieldDescription() {
        FreestyleConfigurationPage descriptionField = new HomePage(getDriver())
                .clickNewItem()
                .inputProjectName(PROJECT_NAME)
                .selectFreestyleAndClickOK();

        Assert.assertTrue(descriptionField.isDescriptionFieldDisplayed(), "Элемент поля не виден");
    }

    @Test
    public void testPreviewDescription() {
        FreestyleConfigurationPage preview = new HomePage(getDriver())
                .clickNewItem()
                .inputProjectName(PROJECT_NAME)
                .selectFreestyleAndClickOK();

        Assert.assertTrue(preview.isPreviewPointDisplayed(), "Элемент Preview не видна");
    }

    @Test
    public void testSaveAndVerifyOnStatusPageDescription() {
        StatusPage descStatus = new HomePage(getDriver())
                .clickNewItem()
                .inputProjectName(PROJECT_NAME)
                .selectFreestyleAndClickOK()
                .addDescription(DESK_TEXT)
                .clickSaveButton();

        Assert.assertEquals(descStatus.getDescriptionText(), DESK_TEXT, "Текст на странице не совпал");
    }

    @Test
    public void testAddNewDescription() {
        StatusPage statusPage = new HomePage(getDriver())
                .clickNewItem()
                .inputProjectName(PROJECT_NAME)
                .selectFreestyleAndClickOK()
                .clickSaveButton()
                .clickEditDescription()
                .inputDescription(DESK_ADD_TEXT)
                .clickSaveDescription();

        Assert.assertEquals(statusPage.getDescriptionText(), DESK_ADD_TEXT, "Текст не совпал");
        Assert.assertTrue(statusPage.getDescriptionLinkText().contains("Edit description"), "Текст ссылки не изменился на 'Edit description'");
    }

    @Test
    public void testUpdateDescription() {
        StatusPage statusPage = new HomePage(getDriver())
                .clickNewItem()
                .inputProjectName(PROJECT_NAME)
                .selectFreestyleAndClickOK()
                .clickSaveButton()
                .clickEditDescription()
                .inputDescription(OLD_TEXT)
                .clickSaveDescription()
                .waitForDescriptionText(OLD_TEXT)
                .clickEditDescription()
                .clearDescriptionText()
                .inputDescription(DESK_EDIT_TEXT)
                .clickSaveDescription()
                .waitForDescriptionText(DESK_EDIT_TEXT);

        Assert.assertEquals(statusPage.getDescriptionText(), DESK_EDIT_TEXT, "Текст не изменился");
    }
}