package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

import java.time.Duration;

public class CipCipJenkinsTest extends BaseTest {
    private final By CREATE_JOB_BUTTON = By.cssSelector("a.content-block__link");
    private final By ITEM_FIELD = By.id("name");
    private final By PIPELINE_TYPE = By.cssSelector("#items > div:first-child");
    private final By TYPE_CHOSEN = By.cssSelector("#items > div:first-child input[type='radio']");
    private final By OK_BUTTON = By.id("ok-button");

    private final String TEST_NAME = "New test Job";
    private final By JOB_NAME = By.linkText(TEST_NAME);
    @Test
    public void testClickButtonCreateJob(){
        clickButtonCreateJob();
        Assert.assertEquals(getDriver().getTitle(), "New Item - Jenkins");
    }

    @Test
    public void testChooseItemType(){
        clickButtonCreateJob();
        chooseItemType();
        WebElement checkButton = getDriver().findElement(TYPE_CHOSEN);
        Assert.assertTrue(checkButton.isSelected());
    }
    @Test
    public void testClickOKButton(){
        createJob();

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        String expectedTitle = TEST_NAME + " Config - Jenkins";
        wait.until(ExpectedConditions.titleIs(expectedTitle));
        Assert.assertEquals(getDriver().getTitle(), expectedTitle);
    }
    @Test
    public void testCheckJobName(){
        createJob();
        String actualJobName = getDriver().findElement(JOB_NAME).getText();
        Assert.assertEquals(actualJobName,TEST_NAME);
    }

    public void createJob(){
        clickButtonCreateJob();
        fillItemName();
        chooseItemType();
        getDriver().findElement(OK_BUTTON).click();
    }
    public void clickButtonCreateJob(){
        getDriver().findElement(CREATE_JOB_BUTTON).click();
    }
    public void fillItemName(){
        WebElement field =  getDriver().findElement(ITEM_FIELD);
        field.click();
        field.sendKeys(TEST_NAME);
    }
    public void chooseItemType(){
        WebElement type =  getDriver().findElement(PIPELINE_TYPE);
        type.click();
    }


}
