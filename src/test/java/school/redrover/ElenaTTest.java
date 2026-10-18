package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

public class ElenaTTest extends BaseTest {

    @Test
    public void testADescription() throws InterruptedException {
        String titleDescription = "TEST";

        //Нажать кнопку "добавить описание"
        getDriver().findElement(By.id("description-link")).click();
        //вставить текст в модальное окно
        getDriver().findElement(By.id("description-textarea")).sendKeys(titleDescription);
        //нажать кнопку "Save"
        getDriver().findElement(By.xpath("//*[@id=\"bottom-sticker\"]/div/button[2]")).click();

        Thread.sleep(1000); //Ждем пока обновится сохранение

        Assert.assertEquals(
                getDriver().findElement(By.id("description-content")).getText(),
                titleDescription);

    }

    @Test
    public void newItemFreestyleProject(){

        String itemName = "Elena Freestyle project";

        getDriver().findElement(By.xpath("//*[@id=\"tasks\"]/div[1]/span/a")).click();
        getDriver().findElement(By.xpath("//*[@id=\"name\"]")).sendKeys(itemName);
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.id("ok-button")).click();
        getDriver().findElement(By.id("jenkins-head-icon")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//*[@id=\"job_Elena Freestyle project\"]/td[3]/a")).getText(),
                itemName);
    }


}
