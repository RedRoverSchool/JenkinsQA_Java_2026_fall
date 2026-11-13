package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import java.util.List;

public class NodesTest extends BaseTest {
    private final static String NODE_NAME = "TestAgent";
    private static final By NODE_NAME_ROW = By.xpath("//table[@id='computers']//tr[contains(@id, '" + NODE_NAME + "')]");

    @Test
    public void testOpenManageNodesPage() {
        openNodesPage();

        Assert.assertTrue(
                getDriver().findElement(By.tagName("h1")).getText().contains("Nodes"),
                "Manage Nodes page is not opened"
        );
    }

    @Test(dependsOnMethods = "testOpenManageNodesPage")
    public void testCreatePermanentAgent() {
        openNodesPage();
        createPermanentAgent();
        WebElement nodeRow = getWait2().until(ExpectedConditions.visibilityOfElementLocated(NODE_NAME_ROW));
        List<WebElement> manageNodeLinks = nodeRow.findElements(
                By.xpath(".//a[contains(@href, '" + NODE_NAME + "')]"));
        List<String> manageNodeText = manageNodeLinks.stream()
                .map(WebElement::getText)
                .map(String::trim)
                .toList();

        Assert.assertTrue(manageNodeText.contains(NODE_NAME));
    }

    @Test(dependsOnMethods = "testCreatePermanentAgent")
    public void deletePermanentAgentFromConfigurePage() {
        openNodesPage();
        getDriver().findElement(By.xpath("//button[contains(@data-href, '" + NODE_NAME + "')]")).click();
        getWait2().until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[contains(@href, '" + NODE_NAME + "') and normalize-space()='" + NODE_NAME + "']")
        )).click();
        getWait2().until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@data-url='doDelete']"))).click();
        getWait2().until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@data-id='ok']"))).click();
        getWait2().until(ExpectedConditions.invisibilityOfElementLocated(NODE_NAME_ROW));
        boolean isNodeNameDeleted = getDriver().findElements(NODE_NAME_ROW).isEmpty();

        Assert.assertTrue(isNodeNameDeleted);
    }

    private void createPermanentAgent() {
        getWait2().until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[@class='jenkins-button jenkins-button--primary']")
        )).click();
        getDriver().findElement(By.xpath("//input[@id='name']")).sendKeys(NODE_NAME);
        getDriver().findElement(By.xpath("//label[@for='hudson.slaves.DumbSlave']")).click();
        getDriver().findElement(By.xpath("//button[@id='ok']")).click();
        WebElement saveButton = getWait2().until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//button[@value='Save']")));
        ((JavascriptExecutor) getDriver())
                .executeScript("arguments[0].scrollIntoView({block: 'center'});", saveButton);
        getDriver().findElement(By.xpath("//button[@value='Save']")).click();
    }

    private void openNodesPage() {
        getWait2().until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@id='root-action-ManageJenkinsAction']//*[name()='svg']"))).click();
        getDriver().findElement(By.xpath("//a[.//dt[normalize-space()='Nodes']]")).click();
    }

}
