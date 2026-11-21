package school.redrover.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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

    public NewItemPage selectPipeline() {
        getDriver().findElement(By.xpath("//label//span[text()='Pipeline']")).click();

        return this;
    }

    public PipelineConfigurationPage selectPipelineAndClickOk() {
        getDriver().findElement(By.xpath("//label//span[text()='Pipeline']")).click();
        getWait2().until(ExpectedConditions.elementToBeClickable(By.id("ok-button"))).click();

        // waiting for the new page
        getWait10().until(ExpectedConditions.presenceOfElementLocated(By.id("side-panel")));

        return new PipelineConfigurationPage(getDriver());
    }

    public FreestyleConfigurationPage selectFreestyleAndClickOK() {
        getDriver().findElement(By.className("hudson_model_FreeStyleProject")).click();
        getDriver().findElement(By.id("ok-button")).click();

        //waiting for the new page
        getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.className("jenkins-app-bar")));

        return new FreestyleConfigurationPage(getDriver());
    }

    public OrganizationFolderConfigurationPage selectOrganizationFolderAndClickOk() {
        WebDriver driver = getDriver();
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("window.scrollBy(0, 1000)", "");
        getDriver().findElement(By.className("jenkins_branch_OrganizationFolder")).click();
        getWait2().until(ExpectedConditions.elementToBeClickable(By.id("ok-button"))).click();

        // waiting for the new page
        getWait10().until(ExpectedConditions.presenceOfElementLocated(By.id("side-panel")));

        return new OrganizationFolderConfigurationPage(getDriver());
    }

    public String getErrorMessageText() {
        return getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='error']"))).getText();
    }
}
