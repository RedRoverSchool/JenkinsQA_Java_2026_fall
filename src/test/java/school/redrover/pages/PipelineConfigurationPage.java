package school.redrover.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class PipelineConfigurationPage extends BasePage {
    private final By descriptionField = By.name("description");
    private final By saveButton = By.xpath("//button[@value='Save']");
    private final By applyButton = By.name("Apply");
    private final By pipelineButtonInSidePanel = By.xpath("//button[@data-section-id='50-69-70-65-6c-69-6e-65']");

    public PipelineConfigurationPage(WebDriver driver) {
        super(driver);
    }

    public PipelineConfigurationPage addDescription(String description) {
        getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[normalize-space(.)='Configure']")));
        getDriver().findElement(descriptionField).sendKeys(description);

        return this;
    }

    public PipelineConfigurationPage clickApplyButton() {
        getDriver().findElement(applyButton).click();

        return this;
    }

    public StatusPage clickSaveButton() {
        getDriver().findElement(saveButton).click();

        return new StatusPage(getDriver());
    }

    public PipelineConfigurationPage clickPipelineButtonInSidePanel() {
        getDriver().findElement(pipelineButtonInSidePanel).click();
        getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.id("workflow-editor-samples")));

        return this;
    }

    public PipelineConfigurationPage selectHelloWorldScriptFromSample() {
        Select select = new Select(getDriver().findElement(By.id("workflow-editor-samples")));
        select.selectByValue("hello");

        return this;
    }

    public List<String> getTextFromScript() {
        return getDriver().findElements(By.className("ace_string")).stream()
                .map(WebElement::getText)
                .map(s -> s.replaceAll("^'|'$", ""))
                .toList();
    }

    public String getNotificationText() {
        return getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='notification-bar']/span"))).getText();
    }
}
