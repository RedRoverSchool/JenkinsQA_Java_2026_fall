package school.redrover.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class DescriptionDialog extends BaseModel {

    public DescriptionDialog(WebDriver driver) {
        super(driver);
    }

    public DescriptionDialog waitUntilOpened() {
        getWait2().until(
            ExpectedConditions.visibilityOfElementLocated(
                By.className("jenkins-dialog__title")));
        return this;
    }

    public String getTitle() {
        return getDriver().findElement(By.className("jenkins-dialog__title")).getText();
    }

    public WebElement getTextArea() {
        return getDriver().findElement(By.id("description-textarea"));
    }

    public WebElement getPreviewContainer() {
        return getDriver().findElement(By.cssSelector(".textarea-preview-container"));
    }

    public WebElement getShowPreviewLink() {
        return getDriver().findElement(By.cssSelector(".textarea-show-preview"));
    }

    public WebElement getHidePreviewLink() {
        return getDriver().findElement(By.cssSelector(".textarea-hide-preview"));
    }

    public WebElement getPreviewBlock() {
        return getDriver().findElement(By.cssSelector(".textarea-preview"));
    }

    public WebElement getCancelButton() {
        return getDriver().findElement(By.cssSelector("button[data-id='cancel']"));
    }

    public WebElement getSaveButton() {
        return getDriver().findElement(By.cssSelector("button[data-id='ok']"));
    }

    public DescriptionDialog clear() {
        getTextArea().clear();

        return this;
    }

    public DescriptionDialog setText(String text) {
        getTextArea().sendKeys(text);

        return this;
    }

    public HomePage clickSave() {
        getSaveButton().click();

        return new HomePage(getDriver());
    }
}