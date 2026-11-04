package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import java.util.ArrayList;
import java.util.List;


public class PipelineConfiguration2Test extends BaseTest {

  private static final List<String> neededResult = List.of("Pipeline script", "Pipeline script from SCM");

  private void createItem() {

    final WebDriverWait wait5 = getWait5();

    wait5.until(ExpectedConditions.elementToBeClickable(
        By.cssSelector("#tasks a[href='/view/all/newJob']"))).click();

    wait5.until(ExpectedConditions.visibilityOfElementLocated(
       By.cssSelector(".jenkins-form-item.add-item-name input"))).sendKeys("newItem");

    wait5.until(ExpectedConditions.elementToBeClickable(
        By.cssSelector(".org_jenkinsci_plugins_workflow_job_WorkflowJob.jenkins-choice-list__item"))).click();

    wait5.until(ExpectedConditions.elementToBeClickable(
        By.cssSelector("button.jenkins-button.jenkins-button--primary.jenkins-buttons-row--equal-width"))).click();

    wait5.until(ExpectedConditions.elementToBeClickable(
        By.cssSelector("button[data-section-id='50-69-70-65-6c-69-6e-65']"))).click();
  }

  @Test
  public void checkDefinitionVariantInPipeline() {

    createItem();

    WebElement definitionSelectElement = getDriver().findElement(
        By.cssSelector("div[id='50-69-70-65-6c-69-6e-65'] ~ div.jenkins-form-item select"));

    Select select = new Select(definitionSelectElement);

    List<WebElement> listDefinitionSelectElement = select.getOptions();

    List<String> contentOflistDefinitionSelectElement = new ArrayList<>();

    for (WebElement option : listDefinitionSelectElement) {
      contentOflistDefinitionSelectElement.add(option.getText());
    }

    Assert.assertEquals(contentOflistDefinitionSelectElement, neededResult);
  }
}
