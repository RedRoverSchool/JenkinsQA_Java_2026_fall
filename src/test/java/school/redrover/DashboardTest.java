package school.redrover;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import school.redrover.common.BaseTest;

public class DashboardTest extends BaseTest {

    private static final By DASHBOARD_DESCRIPTION_BUTTON = By.id("description-link");
    private static final By DASHBOARD_DESCRIPTION_TEXTAREA = By.id("description-textarea");
    private static final By DASHBOARD_DESCRIPTION_CONTENT = By.id("description-content");
    private static final By DESCRIPTION_SAVE_BUTTON = By.cssSelector("button[data-id='ok']");
    private static final By JENKINS_HEAD_ICON = By.id("jenkins-head-icon");
    private static final By JENKINS_VERSION_LABEL = By.className("jenkins_ver");
    private static final By DESCRIPTION_WINDOW_TITLE = By.className("jenkins-dialog__title");
    private static final By DESCRIPTION_WINDOW_CONTENT = By.id("description-textarea");
    private static final By DESCRIPTION_SHOW_PREVIEW = By.cssSelector(".textarea-show-preview");
    private static final By DESCRIPTION_HIDE_PREVIEW = By.cssSelector(".textarea-hide-preview");
    private static final By DESCRIPTION_PREVIEW_BLOCK = By.cssSelector(".textarea-preview");
    private static final By DESCRIPTION_CANCEL_BUTTON = By.cssSelector("button[data-id='cancel']");
    private static final By DESCRIPTION_PREVIEW_CONTEINER = By.cssSelector(
        ".textarea-preview-container");

    private static final String JENKINS_VERSION_TEXT = "Jenkins 2.580.1";
    private static final String DASHBOARD_DESCRIPTION_TEXT = "Dashboard description text";
    private static final String DASHBOARD_NEW_DESCRIPTION_TEXT = "Dashboard NEW description text. Version 2.0";
    private static final String ADD_DASHBOARD_DESCRIPTION_BUTTON_NAME = "Add description";
    private static final String EDIT_DASHBOARD_DESCRIPTION_BUTTON_NAME = "Edit description";

    @Test
    public void testCheckJenkinsVersion() {

        Assert.assertEquals(getDriver().findElement(JENKINS_VERSION_LABEL).getText(),
            JENKINS_VERSION_TEXT, "Версия Jenkins отличается от версии рекомендованной для курса");
    }

    @Test
    public void testAddDashboardDescription() {

        putDashboardDescription(DASHBOARD_DESCRIPTION_TEXT);

        Assert.assertEquals(getDriver().findElement(DASHBOARD_DESCRIPTION_CONTENT).getText(),
            DASHBOARD_DESCRIPTION_TEXT);
    }

    @Test
    public void testEditDashboardDescription() {

        putDashboardDescription(DASHBOARD_DESCRIPTION_TEXT);
        putDashboardDescription(DASHBOARD_NEW_DESCRIPTION_TEXT);

        Assert.assertEquals(getDriver().findElement(DASHBOARD_DESCRIPTION_CONTENT).getText(),
            DASHBOARD_NEW_DESCRIPTION_TEXT);
    }

    @Test
    public void testDeleteDashboardDescription() {

        putDashboardDescription(DASHBOARD_DESCRIPTION_TEXT);
        deleteDashboardDescription();
        String actualDescription = getDriver().findElement(DASHBOARD_DESCRIPTION_CONTENT).getText();

        Assert.assertTrue(actualDescription.isEmpty(),
            "Ожидалось пустое описание, но было получено: '" + actualDescription + "'");
    }

    @Test
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

    @Test
    public void testCheckUIElementsDashboardDescription() {

        openAndClearDashboardDescriptionEditor();

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
        WebElement title = wait.until(
            ExpectedConditions.visibilityOfElementLocated(DESCRIPTION_WINDOW_TITLE));

        WebElement textArea = getDriver().findElement(DESCRIPTION_WINDOW_CONTENT);

        WebElement previewContainer = getDriver().findElement(DESCRIPTION_PREVIEW_CONTEINER);

        WebElement previewLink = getDriver().findElement(DESCRIPTION_SHOW_PREVIEW);
        WebElement hidePreviewLink = getDriver().findElement(DESCRIPTION_HIDE_PREVIEW);
        WebElement previewBlock = getDriver().findElement(DESCRIPTION_PREVIEW_BLOCK);

        WebElement cancelButton = getDriver().findElement(DESCRIPTION_CANCEL_BUTTON);

        WebElement saveButton = getDriver().findElement(DESCRIPTION_SAVE_BUTTON);

        final String expectedCancelButtonText = "Cancel";
        final String expectedSaveButtonText = "Save";
        final String expectedPreviewContainerPlain = "Plain text";
        final String expectedPreviewConteinerPreview = "Preview";

        SoftAssert softAssert = new SoftAssert();

        softAssert.assertEquals(title.getText(), ADD_DASHBOARD_DESCRIPTION_BUTTON_NAME,
            "Текст заголовка отличается");

        softAssert.assertTrue(textArea.isDisplayed(), "Поле ввода должно отображаться");
        softAssert.assertEquals(textArea.getAttribute("value"), "", "Поле ввода не пустое");
        softAssert.assertTrue(textArea.isEnabled(), "Поле ввода должно быть доступно");

        softAssert.assertTrue(previewContainer.isDisplayed(),
            "Контейнер с 'Plain text' должен отображаться");
        softAssert.assertTrue(previewContainer.getText().contains(expectedPreviewContainerPlain),
            expectedPreviewContainerPlain + " отличается от ожидаемого");

        softAssert.assertEquals(previewLink.getText(), expectedPreviewConteinerPreview,
            expectedPreviewConteinerPreview + " отличается от ожидаемого");
        softAssert.assertTrue(previewLink.isDisplayed(), "Preview должно отображаться");

        softAssert.assertFalse(hidePreviewLink.isDisplayed(), "Hide preview должен быть скрыт");

        softAssert.assertFalse(previewBlock.isDisplayed(), "Блок preview должен быть скрыт");

        softAssert.assertTrue(cancelButton.isDisplayed(), "Кнопка Cancel должна отображаться");
        softAssert.assertEquals(cancelButton.getText(), expectedCancelButtonText,
            "Название кнопки отличается от " + expectedCancelButtonText);

        softAssert.assertTrue(saveButton.isDisplayed(), "Кнопка Save должна отображаться");
        softAssert.assertEquals(saveButton.getText(), expectedSaveButtonText,
            "Название кнопки отличается от " + expectedSaveButtonText);

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
