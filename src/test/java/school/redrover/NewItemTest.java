package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class NewItemTest extends BaseTest {

    @Test
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
    }
}
