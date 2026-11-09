package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import school.redrover.common.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;


public class ConfigurationMatrixTest extends BaseTest {

  private static final By BUTTON_CREATE_ITEM = By.cssSelector("#tasks a[href='/view/all/newJob']");
  private static final By ITEM_NAME_FIELD = By.cssSelector(".jenkins-form-item.add-item-name input");
  private static final By MULTI_CONFIGURATION_ITEM_TYPE = By.cssSelector(".hudson_matrix_MatrixProject.jenkins-choice-list__item");
  private static final By NEW_ITEM_OK_BUTTON = By.cssSelector(".jenkins-button.jenkins-button--primary.jenkins-buttons-row--equal-width");
  private static final By MATRIX_CONFIGURATION_BUTTON = By.cssSelector("button[data-section-id='43-6f-6e-66-69-67-75-72-61-74-69-6f-6e-20-4d-61-74-72-69-78']");

  private static final By BUTTON_ADD_AXIS = By.cssSelector(".config-table button.hetero-list-add[suffix='axis']");
  private static final By COMBINATION_FILTER_CHECKBOX = By.cssSelector("input[name='hasCombinationFilter'] + label");
  private static final By RUN_EACH_CONFIGURATION_SEQUENTIALLY_CHECKBOX = By.name("_.runSequentially");
  private static final By EXECUTE_TOUCHSTONE_BUILDS_FIRST_CHECKBOX = By.name("_.hasTouchStoneCombinationFilter");

  private static final By COMBINATION_FILTER_HELP_ICON = By.cssSelector("a[helpurl='/plugin/matrix-project/help/matrix/combinationfilter.html']");
  private static final By COMBINATION_FILTER_HELP_BLOCK = By.cssSelector("div.optionalBlock-container:has(input[name='hasCombinationFilter']) div.help-area > div.help");
  private static final By COMBINATION_FILTER_FIELD = By.cssSelector("input[name='combinationFilter']");

  private static final By EXECUTE_TOUCHSTONE_CHECKBOX = By.cssSelector("input[name='_.hasTouchStoneCombinationFilter'] + label");
  private static final By EXECUTE_TOUCHSTONE_FILTER_FIELD = By.name("_.touchStoneCombinationFilter");
  private static final By EXECUTE_TOUCHSTONE_REQUIRED_RESULT_SELECT = By.name("touchStoneResultCondition");



  private void createNewMultiConfigurationItem() {
    final WebDriverWait wait5 = getWait5();
    final WebDriver driver = getDriver();

    driver.findElement(BUTTON_CREATE_ITEM).click();
    wait5.until(ExpectedConditions.elementToBeClickable(ITEM_NAME_FIELD)).sendKeys("new multi configuration item");
    driver.findElement(MULTI_CONFIGURATION_ITEM_TYPE).click();
    driver.findElement(NEW_ITEM_OK_BUTTON).click();
    wait5.until(ExpectedConditions.elementToBeClickable(MATRIX_CONFIGURATION_BUTTON)).click();
  }

  @Test
  public void checkConfigurationMatrixSectionContains() {
    createNewMultiConfigurationItem();

    final WebDriver driver = getDriver();

    boolean isButtonAddAxisDisplayed = driver.findElement(BUTTON_ADD_AXIS).isDisplayed();
    boolean isCombinationFilterDisplayed = driver.findElement(COMBINATION_FILTER_CHECKBOX).isDisplayed();
    boolean isConfigurationSequentiallyDisplayed = driver.findElement(RUN_EACH_CONFIGURATION_SEQUENTIALLY_CHECKBOX).isDisplayed();
    boolean isExecuteTouchstoneDisplayed = driver.findElement(EXECUTE_TOUCHSTONE_BUILDS_FIRST_CHECKBOX).isDisplayed();

    Assert.assertTrue(isButtonAddAxisDisplayed);
    Assert.assertTrue(isCombinationFilterDisplayed);
    Assert.assertTrue(isConfigurationSequentiallyDisplayed);
    Assert.assertTrue(isExecuteTouchstoneDisplayed);
  }

  @Test
  public void checkInitialStateCheckboxCombinationFilter() {
    createNewMultiConfigurationItem();
    final WebDriver driver = getDriver();

    boolean isCheckboxCombinationFilterSelected = driver.findElement(COMBINATION_FILTER_CHECKBOX).isSelected();
    Assert.assertFalse(isCheckboxCombinationFilterSelected);
  }

  @Test
  public void checkStructureSelectedCombinationFilter() {
    createNewMultiConfigurationItem();
    final WebDriver driver = getDriver();

    ((JavascriptExecutor) driver).executeScript(
        "arguments[0].scrollIntoView({block: 'center'});",
        driver.findElement(COMBINATION_FILTER_CHECKBOX)
    );
    driver.findElement(COMBINATION_FILTER_CHECKBOX).click();
    driver.findElement(COMBINATION_FILTER_HELP_ICON).click();

    Assert.assertTrue(driver.findElement(COMBINATION_FILTER_HELP_ICON).isDisplayed());
    Assert.assertTrue(driver.findElement(COMBINATION_FILTER_HELP_BLOCK).isDisplayed());
    Assert.assertTrue(driver.findElement(COMBINATION_FILTER_FIELD).isDisplayed());
  }

  @Test
  public void checkInitialStateCheckboxExecuteTouchstone() {
    createNewMultiConfigurationItem();
    final WebDriver driver = getDriver();

    boolean isCheckboxExecuteTouchstoneSelected = driver.findElement(EXECUTE_TOUCHSTONE_CHECKBOX).isSelected();
    Assert.assertFalse(isCheckboxExecuteTouchstoneSelected);
  }

  @Test
  public void checkStructureSelectedExecuteTouchstone() {
    createNewMultiConfigurationItem();
    final WebDriver driver = getDriver();

    List<String> executeTouchstoneRequiredResultNeededSelect= List.of("Stable", "Unstable");


    ((JavascriptExecutor) driver).executeScript(
        "arguments[0].scrollIntoView({block: 'center'});",
        driver.findElement(EXECUTE_TOUCHSTONE_CHECKBOX)
    );
    driver.findElement(EXECUTE_TOUCHSTONE_CHECKBOX).click();

    WebElement executeTouchstoneFilterField = driver.findElement(EXECUTE_TOUCHSTONE_FILTER_FIELD);
    WebElement selectElement = driver.findElement(EXECUTE_TOUCHSTONE_REQUIRED_RESULT_SELECT);

    boolean isExecuteTouchstoneFilterField = executeTouchstoneFilterField.isDisplayed();
    boolean isSelectElement = selectElement.isDisplayed();

    Select executeTouchstoneRequiredResultSelect = new Select(selectElement);
    ArrayList<String> actualExecuteTouchstoneSelectElements = new ArrayList<>();

    executeTouchstoneRequiredResultSelect.getOptions().forEach(
        elem -> actualExecuteTouchstoneSelectElements.add(elem.getText())
    );

    executeTouchstoneRequiredResultSelect.selectByVisibleText(executeTouchstoneRequiredResultNeededSelect.get(1));
    String newSelectedValue = executeTouchstoneRequiredResultSelect.getFirstSelectedOption().getText();

    driver.findElement(EXECUTE_TOUCHSTONE_CHECKBOX).click();
    boolean isFilterFieldDisplayed = driver.findElement(EXECUTE_TOUCHSTONE_FILTER_FIELD).isDisplayed();
    boolean isRequiredResultSelectDisplayed = driver.findElement(EXECUTE_TOUCHSTONE_REQUIRED_RESULT_SELECT).isDisplayed();

    Assert.assertTrue(isExecuteTouchstoneFilterField);
    Assert.assertTrue(isSelectElement);
    Assert.assertEquals(actualExecuteTouchstoneSelectElements, executeTouchstoneRequiredResultNeededSelect);
    Assert.assertEquals(newSelectedValue, executeTouchstoneRequiredResultNeededSelect.get(1));
    Assert.assertFalse(isFilterFieldDisplayed);
    Assert.assertFalse(isRequiredResultSelectDisplayed);
  }
}

