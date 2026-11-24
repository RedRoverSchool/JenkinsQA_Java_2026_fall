package school.redrover.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;

public class SecurityPage extends BasePage {

    public SecurityPage(WebDriver driver) {
        super(driver);
    }

    public String getTitleText() {
        return getDriver().findElement(By.className("jenkins-app-bar__content")).getText();
    }

    public String getSelectSecurityRealm() {
        WebElement realmDropdown = getDriver().findElement(By.xpath("//div[contains(text(),'Security Realm')]/..//select"));
        Select selectRealm = new Select(realmDropdown);
        String actualDefault = selectRealm.getFirstSelectedOption().getText();

        return actualDefault;
    }

    public List<String> getSecurityRealmOptions() {
        WebElement realmDropdown = getDriver().findElement(By.xpath("//div[contains(text(),'Security Realm')]/..//select"));
        Select selectRealm = new Select(realmDropdown);
        List<WebElement> optionsRealm = selectRealm.getOptions();
        List<String> actualOptions = new ArrayList<>();
        for (WebElement option : optionsRealm) {
            actualOptions.add(option.getText());
        }
        return actualOptions;
    }

    public SecurityPage checkDisableRememberMe() {
        if (!getDriver().findElement(By.name("_.disableRememberMe")).isSelected()) {
            getDriver().findElement(By.name("_.disableRememberMe")).sendKeys(Keys.SPACE);
        }
        return this;
    }

    public ManageJenkinsPage clickSave() {
        WebElement saveButton = getDriver().findElement(By.cssSelector(".jenkins-submit-button"));
        saveButton.click();
        getWait10().until(ExpectedConditions.stalenessOf(saveButton));
        return new ManageJenkinsPage(getDriver());
    }

    public WebElement getAuthorizationCheckbox() {
        return getWait10()
                .until(ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector("input[name='_.allowAnonymousRead']")));
    }
}
