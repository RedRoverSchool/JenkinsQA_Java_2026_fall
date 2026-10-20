package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

import java.time.Duration;
<<<<<<< HEAD
=======
import java.util.ArrayList;
import java.util.List;
>>>>>>> c22f358d6abdd74983cbfdfcdbba8ded9c316bc5

public class NewItemTest extends BaseTest {

    @Test
<<<<<<< HEAD
    public void testInvalidProjectName() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));
        final String project_name = "?, *, /, , ., !, %, $, &, ,, ;, :";
        String expectedErrorText = "is an unsafe character";

        getDriver().findElement(By.cssSelector("a[href*='newJob']")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("name"))).sendKeys(project_name);
        wait.until(ExpectedConditions.textToBePresentInElementLocated
                ((By.xpath("//*[@id='createItem']/div[1]/div/div/div")), expectedErrorText));
        WebElement okButton = getDriver().findElement(By.id("ok-button"));
        boolean isEnabled = okButton.isEnabled();

        Assert.assertFalse(isEnabled, "Кнопка 'ОК' должна быть неактивной!");
=======
    public void testNewItemLinkOpensCreationPage() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(40));
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href='/view/all/newJob']"))).click();

        WebElement nameField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("name")));
        Assert.assertTrue(nameField.isDisplayed(), "Поле ввода \"Enter an item name\" не появилось");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#items span.jenkins-choice-list__item__label")));
        List<WebElement> typeElements = getDriver().findElements(By.cssSelector("#items span.jenkins-choice-list__item__label"));
        List<String> actualItems = new ArrayList<>();
        for (WebElement element : typeElements) {
            String text = element.getText().trim();
            if (!text.isEmpty()) {
                actualItems.add(text);
            }
        }
        List<String> items = List.of(
                "Pipeline",
                "Freestyle project",
                "Multi-configuration project",
                "Folder",
                "Multibranch Pipeline",
                "Organization Folder"
        );
        Assert.assertTrue(actualItems.containsAll(items), "На странице не хватает некоторых типов! " +
                "Ожидали: " + items +
                ". Получили: " + actualItems);
>>>>>>> c22f358d6abdd74983cbfdfcdbba8ded9c316bc5
    }
}
