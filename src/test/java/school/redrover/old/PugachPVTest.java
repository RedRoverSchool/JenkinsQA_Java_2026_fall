package school.redrover.old;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

@Ignore
public class PugachPVTest {
    @Test
    public void testLoginRonUser() {

        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setPageLoadStrategy(PageLoadStrategy.EAGER);
        WebDriver driver = new ChromeDriver(chromeOptions);
        driver.manage().window().maximize();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.get("https://www.globalsqa.com/angularJs-protractor/BankingProject/#/login");

        WebElement customerLoginButton = wait.until(ExpectedConditions.elementToBeClickable(
            By.cssSelector("button[ng-click='customer()']")));
        customerLoginButton.click();

        WebElement customerSelector = wait.until(
            ExpectedConditions.elementToBeClickable(By.id("userSelect")));
        customerSelector.click();

        WebElement customerRonOption = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.cssSelector("#userSelect option[value='3']")));
        customerRonOption.click();

        WebElement submitButton = wait.until(
            ExpectedConditions.elementToBeClickable(
                By.xpath("//button[@type='submit']")));
        submitButton.click();

        WebElement withdrawButton = wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.cssSelector("button[ng-click='withdrawl()']")));

        String expectedWelcomeTest = "Welcome Ron Weasly !!";

        WebElement welcomeText = driver.findElement(
            By.xpath("//strong[contains(., 'Welcome')]"));
        Assert.assertEquals(welcomeText.getText(), expectedWelcomeTest);

        driver.quit();
    }
}
