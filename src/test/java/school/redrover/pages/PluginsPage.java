package school.redrover.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class PluginsPage extends BasePage {

    public PluginsPage(WebDriver driver) {
        super(driver);
    }
    public PluginsPage openPlugins() {
        getDriver().findElement(By.cssSelector("a[href*='pluginManager']")).click();
        getWait2().until(ExpectedConditions.urlContains("/manage/pluginManager/"));
        return new PluginsPage(getDriver());
    }
}
