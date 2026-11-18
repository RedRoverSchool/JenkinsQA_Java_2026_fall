package school.redrover.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class BuildHistoryPage extends BasePage{

    public BuildHistoryPage(WebDriver driver) {super(driver);}

    public BuildHistoryPage clickBuildName(String projectName){
        getWait10().until(ExpectedConditions.elementToBeClickable(By.xpath( "//button[contains(@data-href, 'job/" + projectName + "/') and contains(@data-href, '/1/')]"))).click();
        return this;
    }

    public DeleteBuildPage deleteBuild(){
        getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(@href, 'confirmDelete')]"))).click();

        return new DeleteBuildPage(getDriver());
    }

    public boolean buildsIsEmpty(String projectName){
        List<WebElement> builds = getDriver().findElements(By.xpath("//a[contains(@href, 'job/" + projectName + "/')]"));

        return builds.isEmpty();
    }

}
