package school.redrover;


import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

public class RenameFolderTest extends BaseTest {


    @Test
    public void renameFolderTest(){

        String newName = "Second";

        createdFolder();

        //Открыть настройки папки
        getDriver().findElement(By.cssSelector("a.jenkins-table__link")).click();
        getDriver().findElement(By.cssSelector("#tippy-4 > div > div > div > a:nth-child(5)")).click();

        //Меняем название
        getDriver().findElement(By.cssSelector("div.setting-main > input")).clear();
        getDriver().findElement(By.cssSelector("div.setting-main > input")).sendKeys(newName);
        getDriver().findElement(By.className("jenkins-submit-button")).click();

        // Проверка названия в заголовке
        Assert.assertEquals(
                getDriver().findElement(By.className("job-index-headline")).getText(),
                newName,
                "Проверка названия в заголовке");

        //Возврат на Главную
        backToMainPage();

        Assert.assertEquals(
                getDriver().findElement(By.cssSelector(".jenkins-table__link> span:first-child")).getText(),
                newName,
                "Проверка названия на главной странице");



    }


    private void backToMainPage(){
        //Не успевает находить элемент, поэтому сделал через try-catch
        try {
            getDriver().findElement(By.className("app-jenkins-logo")).click();
        } catch (org.openqa.selenium.StaleElementReferenceException e) {
            getDriver().findElement(By.className("app-jenkins-logo")).click();
        }
    }

    private void createdFolder(){
        //Предусловие: Создать папку
        getDriver().findElement(By.cssSelector("a.task-link")).click();
        getDriver().findElement(By.id("name")).sendKeys("First");
        getDriver().findElement(By.className("com_cloudbees_hudson_plugins_folder_Folder")).click();
        getDriver().findElement(By.id("ok-button")).click();

        //Возврат на Главную
        backToMainPage();
    }

}
