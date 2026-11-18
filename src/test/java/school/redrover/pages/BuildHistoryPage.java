package school.redrover.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class BuildHistoryPage extends BasePage{

    public BuildHistoryPage(WebDriver driver) {super(driver);}

    public BuildHistoryPage clickBuildName(){
        getDriver().findElement(By.xpath("//a[contains(@href, 'project/1')]")).click();

        return this;
    }

    public DeleteBuildPage deleteBuildButton(){
        getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(@href, 'confirmDelete')]"))).click();

        return new DeleteBuildPage(getDriver());
    }

    public boolean buildsIsEmpty(){
        List<WebElement> builds = getDriver().findElements(By.xpath("//a[contains(@href, '/job/Elena%20Freestyle%20project/1/')]"));

        return builds.isEmpty();
    }

}
