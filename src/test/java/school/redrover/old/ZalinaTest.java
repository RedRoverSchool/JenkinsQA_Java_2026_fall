package school.redrover.old;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import java.time.Duration;

@Ignore
public class ZalinaTest {

    private static final String URL ="https://demoqa.com/buttons";
    //Создаем статическую переменную со ссылкой на тестовую страницу.

    @Test
    public void testDynamicClick(){
        WebDriver driver = new ChromeDriver(); //открываем браузер
        driver.get(URL); //открываем ссылку на сайт
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1000));
        //устанавливаем неявное ожидание поиска элементов
        WebElement button = driver.findElement
                (By.xpath("//div[@class='mt-4']//button[text()='Click Me']"));
        //находим нужный элемент(кнопку) с помощью Xpath
        button.click();// кликаем по кнопке
        WebElement clickMessage = driver.findElement
                (By.id("dynamicClickMessage"));
        //находим сообщение о том, что кнопка нажата

        Assert.assertEquals(clickMessage.getText(),"You have done a dynamic click");
        //проверяем, что мы нажали именно ту кнопку которую хотели.
        driver.quit();//закрываем браузер
    }
    @Test
    public void testDoubleClick(){
        WebDriver driver = new ChromeDriver();
        driver.get(URL);
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1000));

        WebElement buttons = driver.findElement
                (By.cssSelector("button[type='button']#doubleClickBtn.btn.btn-primary"));
        Actions action = new Actions(driver);
        action.doubleClick(buttons).perform();
        WebElement doubleClickMessage = driver.findElement
                (By.id("doubleClickMessage"));

        Assert.assertEquals(doubleClickMessage.getText(),"You have done a double click");
        driver.quit();
    }
    @Test
    public void testRightClick(){
        WebDriver driver = new ChromeDriver();
        driver.get(URL);
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1000));

        WebElement buttons = driver.findElement
                (By.cssSelector("button[type='button']#rightClickBtn.btn.btn-primary"));
        Actions action = new Actions(driver);
        action.contextClick(buttons).perform();
        WebElement rightClickMessage = driver.findElement
                (By.id("rightClickMessage"));

        Assert.assertEquals(rightClickMessage.getText(),"You have done a right click");
        driver.quit();
    }
}
