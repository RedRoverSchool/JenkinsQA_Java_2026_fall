package school.redrover.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HomePage extends BasePage {

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public NewItemPage clickNewItem() {
        getWait2().until(ExpectedConditions.elementToBeClickable(By.xpath("//a[.//span[text()='New Item']]"))).click();

        return new NewItemPage(getDriver());
    }

    public String getFirstProjectName() {
        return getDriver().findElement(By.cssSelector(".jenkins-table__link > span:first-child")).getText();
    }

    public LoginPage clickSignOut() {

        WebElement userMenuButton = getDriver().findElement(By.id("root-action-UserAction"));
        Actions actions = new Actions(getDriver());
        actions.moveToElement(userMenuButton).perform();

        getWait10().until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href='/logout']"))).click();

        getWait10().until(ExpectedConditions.urlContains("login"));

        return new LoginPage(getDriver());
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

    public HomePage clickSceduleABuild(String projectName){
        getWait10().until(ExpectedConditions.elementToBeClickable((By.xpath("//a[@title='Schedule a Build for " + projectName + "']")))).click();

        return this;
    }

    public BuildHistoryPage clickBuildsHistory(){
        getWait10().until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='/view/all/builds']"))).click();

        return new BuildHistoryPage(getDriver());
    }

    public String getTitle() {
        return getDriver().findElement(By.xpath("//h1")).getText();
    }

    public String getJenkinsVersion() {
        return getDriver().findElement(By.className("jenkins_ver")).getText();
    }

    public String getDashboardDescriptionText() {
        return getDriver().findElement(By.id("description-content")).getText();
    }

    public String getDashboardDescriptionButtonText() {
        return getDriver().findElement(By.id("description-link")).getText();
    }

    public DescriptionDialog openDescriptionDialog() {
        getDriver().findElement(By.id("description-link")).click();

        return new DescriptionDialog(getDriver()).waitUntilOpened();
    }

    public AppearancePage clickAppearanceButton(){
        WebElement userButton = getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.id("root-action-UserAction")));

        Actions actions = new Actions(getDriver());
        actions.moveToElement(userButton).perform();

        getWait10().until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.jenkins-dropdown__item[href*='/appearance']"))).click();

        return new AppearancePage(getDriver());
    }

    public HomePage clickThemeButton(){
        WebElement userIcon = getDriver().findElement(By.className("jenkins-avatar"));
        new Actions(getDriver()).moveToElement(userIcon).perform();

        getWait10().until(ExpectedConditions.elementToBeClickable(By.cssSelector(".tm-account-theme-picker-select"))).click();
        return this;
    }

    public HomePage clickDarkThemeOnHomePage(){
        getWait10().until(ExpectedConditions.elementToBeClickable(By.xpath("//*[normalize-space(text())='Dark']"))).click();

        return this;
    }

    public boolean isDarkThemeEnabled(){
        return !getDriver().findElements(By.cssSelector("html[data-theme='dark']")).isEmpty();
    }
}
