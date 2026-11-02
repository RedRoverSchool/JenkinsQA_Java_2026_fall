package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import java.time.Duration;


public class NewItem2Test extends BaseTest {

    @Ignore
    @Test
    public void testCreateNewItem() throws InterruptedException {

        final String nameProject = "NewMuliConfigurationProject";

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));

        getDriver().findElement(By.className("task")).click();
        wait.until(ExpectedConditions.visibilityOf(getDriver().findElement(By.id("name")))).sendKeys(nameProject);
        getDriver().findElement(By.className("hudson_matrix_MatrixProject")).click();
        wait.until(ExpectedConditions.elementToBeClickable(getDriver().findElement(By.id("ok-button")))).click();
        wait.until(ExpectedConditions.elementToBeClickable(getDriver().findElement(By.xpath("//button[@name='Submit']")))).click();
        getDriver().findElement(By.className("app-jenkins-logo")).click();

        String foundName = getDriver().findElement(By.id("projectstatus"))
                .findElements(By.tagName("tr")).stream()
                .flatMap(row -> row.findElements(By.tagName("td")).stream())
                .map(WebElement::getText)
                .filter(nameProject::equals)
                .findFirst()
                .orElse(null);

        Assert.assertEquals(foundName, nameProject);
    }
}
