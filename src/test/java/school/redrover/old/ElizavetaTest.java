package school.redrover.old;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

@Ignore
public class ElizavetaTest {
    @Test
public void testTextBox () {
    WebDriver driver = new ChromeDriver();
    try {
        //открываем сайт
        driver.get("https://demoqa.com/text-box");
        System.out.println("Title: " + driver.getTitle()); //вывод заголовка в консоль

        //находим фул нэйм и вводим текст
        WebElement fullName = driver.findElement(By.id("userName")); //ищем по id элемент фуллнэйм
        fullName.sendKeys("Anna"); // воодим имя

        //ищем поле ввода имэйла
        WebElement email = driver.findElement(By.id("userEmail"));
        email.sendKeys("anna@example.com");

        //находим кноппку ввода и кликаем
        WebElement submit = driver.findElement(By.id("submit"));

        // Скроллим к кнопке
        //Кликаем через JS (обходим перекрытие footer)
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", submit);

        //oкно вывода находим
        WebElement output = driver.findElement(By.id("output"));

        //проверяем внеслись ли данные

        Assert.assertTrue(output.getText().contains("Anna"));
        Assert.assertTrue(output.getText().contains("anna@example.com"));

        //выводим результат в окно
        System.out.println("Output: " + output.getText());

    }
    finally {
        driver.quit(); // закрытие браузкра в конце
    }


}
}
