package school.redrover.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class StatusPage extends BasePage {
    private final By pageTitle = By.xpath("//h1");
    private final By description = By.xpath("//*[@id='description']");

    public StatusPage(WebDriver driver) {
        super(driver);
        getWait2().until(ExpectedConditions.visibilityOfElementLocated(pageTitle));
    }

    public String getPageTitle() {
        return getDriver().findElement(pageTitle).getText();
    }

    public String getDescription() {
        return getDriver().findElement(description).getText();
    }
}
