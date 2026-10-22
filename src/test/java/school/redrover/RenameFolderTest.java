package school.redrover;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

import java.time.Duration;

public class RenameFolderTest extends BaseTest {


    @Test
    public void renameFolderTest() {
        String folderName = "First";
        String newFolderName = "Second";

        createdFolder(folderName);

        //  Найти созданную папку
        getDriver().findElement(By.id("root-action-SearchAction")).click();
        getDriver().findElement(By.id("command-bar")).sendKeys(folderName);
        getDriver().findElement(By.xpath("//a[contains(@href, '/job/First')]")).click();
        waitForClickabilityAndClick(getDriver(), By.xpath("//a[contains(@href, '/confirm-rename')]"));


        //Меняем название
        getDriver().findElement(By.cssSelector("div.setting-main > input")).clear();
        getDriver().findElement(By.cssSelector("div.setting-main > input")).sendKeys(newFolderName);
        waitForClickabilityAndClick(getDriver(), By.className("jenkins-submit-button"));


        // Проверка названия в заголовке
        Assert.assertEquals(
                getDriver().findElement(By.className("job-index-headline")).getText(),
                newFolderName,
                "Проверка названия в заголовке");

        //Возврат на Главную
        backToMainPage();

        Assert.assertEquals(
                getDriver().findElement(By.cssSelector(".jenkins-table__link> span:first-child")).getText(),
                newFolderName,
                "Проверка названия на главной странице");
    }


    private void backToMainPage() {
        waitForClickabilityAndClick(getDriver(), By.className("app-jenkins-logo"));
    }

    private void createdFolder(String name) {
        getDriver().findElement(By.cssSelector("a.task-link")).click();
        getDriver().findElement(By.id("name")).sendKeys(name);
        getDriver().findElement(By.className("com_cloudbees_hudson_plugins_folder_Folder")).click();
        getDriver().findElement(By.id("ok-button")).click();

        backToMainPage();
    }

    private void waitForClickabilityAndClick(WebDriver driver, By locator) {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(locator))
                .click();
    }
}
