package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class RezanovTest {

    @Test
    public void testDemoQA() {
        WebDriver driver = new ChromeDriver();

        String fullName = "Sokolov Dmitry";
        String email = "SokolovDmitry@gmail.com";
        String currentAddress = "Russia, Moskay, Proletarskya, 50";
        String permanentAddress = "Tbilisi, Kamenschvili, 51";

        driver.get("https://demoqa.com/text-box");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        driver.findElement(By.xpath("//input[@id='userName']")).sendKeys(fullName);
        driver.findElement(By.id("userEmail")).sendKeys(email);
        driver.findElement(By.id("currentAddress")).sendKeys(currentAddress);
        driver.findElement(By.id("permanentAddress")).sendKeys(permanentAddress);


        WebElement submit = driver.findElement(By.id("submit"));
        submit.click();

        WebElement output = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("output")));


        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[@id='name']")));

        Assert.assertEquals(output.findElement(By.id("name")).getText(), "Name:" + fullName);
        Assert.assertEquals(output.findElement(By.id("email")).getText(), "Email:" + email);
        Assert.assertEquals(output.findElement(By.id("currentAddress")).getText(), "Current Address :" + currentAddress);
        Assert.assertEquals(output.findElement(By.id("permanentAddress")).getText(), "Permananet Address :" + permanentAddress);
    }
}
