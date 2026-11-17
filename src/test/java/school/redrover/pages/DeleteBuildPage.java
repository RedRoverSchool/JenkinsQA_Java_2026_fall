package school.redrover.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DeleteBuildPage extends BasePage{
    public DeleteBuildPage(WebDriver driver) {super(driver);}

    public StatusPage clickSubmitButton(){
        getDriver().findElement(By.name("Submit")).click();

        return new StatusPage(getDriver());
    }
}
