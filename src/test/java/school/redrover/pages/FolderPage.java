package school.redrover.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class FolderPage extends BasePage {
    public FolderPage(WebDriver driver) {
        super(driver);
    }

    public String getHealth() {
        return getDriver().findElement(By.id("48-65-61-6c-74-68-20-6d-65-74-72-69-63-73")).getText();
    }

}
