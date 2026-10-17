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
}
