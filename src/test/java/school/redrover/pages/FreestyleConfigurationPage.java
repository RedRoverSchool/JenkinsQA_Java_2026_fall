package school.redrover.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class FreestyleConfigurationPage extends BasePage {

    public FreestyleConfigurationPage(WebDriver driver) {super(driver);}

    public StatusPage clickSaveButton(){
        getWait10().until(ExpectedConditions.elementToBeClickable(By.name("Submit"))).click();

        return new StatusPage(getDriver());
    }
}
