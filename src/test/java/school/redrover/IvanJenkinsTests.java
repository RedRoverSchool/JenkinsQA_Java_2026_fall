package school.redrover;
import school.redrover.common.BaseTest;

import org.openqa.selenium.By;

import org.testng.Assert;
import org.testng.annotations.Test;

public class IvanJenkinsTests extends BaseTest {

    @Test
    public void testCreateFreestyleProject(){

        final String itemName = "test";
        final String description = "test description";
        //Создать New item = Freestyle project
        getDriver().findElement(By.cssSelector("#tasks > div:nth-child(1) > span")).click();
        getDriver().findElement(By.id("name")).sendKeys(itemName);
        getDriver().findElement(By.xpath("/html/body/div[2]/div/div/form/fieldset/div/div/div[1]/label/div[2]")).click();
        getDriver().findElement(By.id("ok-button")).click();
        //Ввести описание, сохранить
        getDriver().findElement(By.xpath("//*[@id=\"main-panel\"]/form/div[1]/div[2]/div/div[2]/textarea")).sendKeys(description);
        getDriver().findElement(By.xpath("//*[@id=\"bottom-sticker\"]/div/button[1]")).click();

        getDriver().findElement(By.cssSelector("#page-header > div.jenkins-header__main > div > a > span")).click();
        Assert.assertEquals(
                getDriver().findElement(By.cssSelector("#job_test > td:nth-child(3) > a > span")).getText(),
                itemName);

    }
}
