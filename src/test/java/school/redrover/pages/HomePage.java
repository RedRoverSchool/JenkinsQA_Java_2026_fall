package school.redrover.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HomePage extends BasePage {

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public NewItemPage clickNewItem() {
        getDriver().findElement(By.xpath("//a[.//span[text()='New Item']]")).click();

        return new NewItemPage(getDriver());
    }

    public String getFirstProjectName() {
        return getDriver().findElement(By.cssSelector(".jenkins-table__link > span:first-child")).getText();
    }

    public HomePage openJobsDropDownMenu() {
        getDriver().findElement(By.xpath("//button[@class='jenkins-menu-dropdown-chevron']")).click();

        return this;
    }

    public RenamePage clickRenameInDropDownMenu() {
        getWait2().until(ExpectedConditions.elementToBeClickable(By.partialLinkText("Rename"))).click();

        return new RenamePage(getDriver());
    }

    public PipelineConfigurationPage clickConfigureInDropDownMenu() {
        getWait2().until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(@href, 'configure')]"))).click();

        return new PipelineConfigurationPage(getDriver());
    }

    public HomePage deleteJobFromDropDownMenu() {
        getWait2().until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(normalize-space(.), 'Delete')]"))).click();
        getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//dialog[@class='jenkins-dialog']")));
        getDriver().findElement(By.xpath("//button[@data-id='ok']")).click();

        return this;
    }

    public String getTitle() {
        return getDriver().findElement(By.xpath("//h1")).getText();
    }


}
