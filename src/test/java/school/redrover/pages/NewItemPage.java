package school.redrover.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class NewItemPage extends BasePage {

    public NewItemPage(WebDriver driver) {
        super(driver);
    }

    public NewItemPage inputProjectName(String projectName) {
        getDriver().findElement(By.id("name")).sendKeys(projectName);

        return this;
    }

    public PipelineConfigurationPage selectPipelineAndClickOk() {
        getDriver().findElement(By.xpath("//span[@class='jenkins-choice-list__item__label' and text()='Pipeline']")).click();
        getWait2().until(ExpectedConditions.elementToBeClickable(By.id("ok-button"))).click();

        // waiting for the new page
        getWait10().until(ExpectedConditions.presenceOfElementLocated(By.id("side-panel")));

        return new PipelineConfigurationPage(getDriver());
    }
}
