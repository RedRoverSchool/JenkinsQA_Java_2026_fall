package school.redrover.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CopiedProjectConfigurationPage extends BasePage{

    public CopiedProjectConfigurationPage(WebDriver driver) {super(driver);}

    public StatusPage saveCopiedProject(){
        getDriver().findElement(By.name("Submit")).click();
        getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.className("page-headline")));
        return new StatusPage(getDriver());
    }
}
