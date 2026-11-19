package school.redrover.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class AppearancePage extends BasePage{


    public AppearancePage(WebDriver driver) {
        super(driver);
    }

    public AppearancePage clickDarkOnAppearance(){
        getWait10().until(ExpectedConditions.urlContains("/appearance"));
        getWait10().until(ExpectedConditions.elementToBeClickable(By.cssSelector("label[for='radio-block-1']"))).click();
        getWait10().until(ExpectedConditions.elementToBeSelected(By.id("radio-block-1")));

        return this;
    }

    public void clickSaveOnAppearance() {
        WebElement submitButton = getWait10().until(ExpectedConditions.elementToBeClickable(By.name("Submit")));
        submitButton.click();
        getWait10().until(ExpectedConditions.stalenessOf(submitButton));
        getWait10().until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("html[data-theme='dark']")));

        new ProfilePage(getDriver());
    }
}

