package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import org.openqa.selenium.WebElement;

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

        createProject();

        Assert.assertEquals(getDriver().findElement(By.xpath("//*[@id=\"job_Elena Freestyle project\"]/td[3]/a")).getText(),
                itemName);
    }


    private void createProject(){
        String itemName = "Elena Freestyle project";

        getDriver().findElement(By.xpath("//*[@id=\"tasks\"]/div[1]/span/a")).click();
        getDriver().findElement(By.xpath("//*[@id=\"name\"]")).sendKeys(itemName);
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.id("ok-button")).click();
        WebElement headIcon = new WebDriverWait(getDriver(), Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(By.id("jenkins-head-icon")));
        headIcon.click();
    }



    @Test
    public void testDisableProject(){

        String disableText = "This project is currently disabled";
        createProject();
        disableProject();

        String actualText =  getDriver().findElement(By.className("warning")).getAttribute("innerText");

        Assert.assertTrue(actualText.contains(disableText));
    }


    public void disableProject(){
        getDriver().findElement(By.xpath("//span[text()='Elena Freestyle project']")).click();
        getDriver().findElement(By.xpath("//a[contains(@class, 'task-link') and contains(@href, 'configure')]")).click();
        getDriver().findElement(By.cssSelector("label[for='enable-disable-project']")).click();
        getDriver().findElement(By.name("Submit")).click();

        new WebDriverWait(getDriver(), Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.className("warning")));
    }

    @Test
    public void testEnableProject(){
        createProject();
        disableProject();

        getDriver().findElement(By.name("Submit")).click();

        boolean isWarningGone = new WebDriverWait(getDriver(), Duration.ofSeconds(10))
                .until(ExpectedConditions.invisibilityOfElementLocated(By.className("warning")));


        Assert.assertTrue(isWarningGone);
    }



}
