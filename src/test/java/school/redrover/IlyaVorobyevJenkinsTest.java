package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import java.time.Duration;

public class IlyaVorobyevJenkinsTest extends BaseTest {


    private WebDriverWait getWait() {
        return new WebDriverWait(getDriver(), Duration.ofSeconds(10));
    }

    private void createItemFolder(String folderName) {

        getDriver().findElement(By.cssSelector("#tasks > div:nth-child(1)")).click();
        getDriver().findElement(By.id("name")).sendKeys(folderName);

        getDriver().findElement(By.cssSelector("#items > " +
                "div.com_cloudbees_hudson_plugins_folder_Folder.jenkins-choice-list__item" +
                " > label > div.jenkins-choice-list__item__description")).click();

        getDriver().findElement(By.cssSelector("#ok-button")).click();

        getWait().until(ExpectedConditions.urlContains("configure"));

    }


    private void returnToMainPage() {
        getDriver().findElement(By.cssSelector("#page-header > div.jenkins-header__main > div > a > span")).click();
        getWait().until(ExpectedConditions.not(ExpectedConditions.urlContains("configure")));
    }


    private String getElementText(By locator) {
        return getDriver().findElement(locator).getText();
    }


    @Test
    public void testMainPageTexts() {

        final By logoLocator = By.cssSelector("#page-header > div.jenkins-header__main > div > a > span");
        final By newItemButtonLocator = By.cssSelector("#tasks > div:nth-child(1) > span > a > span.task-link-text");
        final By headerTextLocator = By.cssSelector("#main-panel > div:nth-child(3) > div > h1");
        final By headerBodyTextLocator = By.cssSelector("#main-panel > div:nth-child(3) > div > p");

        final String expectedLogoText = "Jenkins";
        final String expectedNewItemButtonText = "New Item";
        final String expectedHeaderText = "Welcome to Jenkins!";
        final String expectedHeaderBodyText = "This page is where your Jenkins jobs will be displayed. " +
                "To get started, you can set up distributed builds or start building a software project.";


        Assert.assertEquals(getElementText(logoLocator), expectedLogoText,
                "Проверка текста логотипа");
        Assert.assertEquals(getElementText(newItemButtonLocator), expectedNewItemButtonText,
                "Проверка текста кнопки New Item");
        Assert.assertEquals(getElementText(headerTextLocator), expectedHeaderText,
                "Проверка текста заголовка");
        Assert.assertEquals(getElementText(headerBodyTextLocator), expectedHeaderBodyText,
                "Проверка текста тела заголовка");

    }


    @Test
    public void testCreateTwoFolders() {

        final String folderOneName = "OogaBooga";
        final String folderTwoName = "GoogooGaga";

        createItemFolder(folderOneName);
        returnToMainPage();
        createItemFolder(folderTwoName);
        returnToMainPage();

        Assert.assertTrue(getDriver().findElement(By.id("job_" + folderOneName)).isDisplayed());
        Assert.assertTrue(getDriver().findElement(By.id("job_" + folderTwoName)).isDisplayed());

    }


}
