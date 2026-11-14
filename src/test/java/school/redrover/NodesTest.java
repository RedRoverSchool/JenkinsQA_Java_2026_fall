package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

import java.util.ArrayList;
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
    public void testCreate() {
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

    @Test(dependsOnMethods = "testCreate")
    public void testConfigure() {
        final String description = "windows-тачка";

        getDriver().findElement(By.xpath(String.format("//span[text()='%s']", NODE_NAME))).click();
        getDriver().findElement(By.xpath("//span[text()='Configure']/ancestor::div[@class='task ']")).click();

        getDriver().findElement(By.xpath("//textarea[@name='nodeDescription']")).sendKeys(description);
        getDriver().findElement(By.xpath("//div[text()='Remote root directory']/following-sibling::*//input[@type='text']")).sendKeys("c:\\jenkins");
        getDriver().findElement(By.xpath("//input[@name='_.labelString']")).sendKeys("windows");
        Select selectMode = new Select(getDriver().findElement(By.name("mode")));
        selectMode.selectByVisibleText("Use this node as much as possible");
        Select selectLaunchMethod = new Select(getDriver().findElement(By.xpath("//div[normalize-space(text())='Launch method']/following-sibling::*/select[@class='jenkins-select__input dropdownList']")));
        selectLaunchMethod.selectByVisibleText("Launch agent by connecting it to the controller");
        getDriver().findElement(By.xpath("//button[@value='Save']")).click();

        Assert.assertEquals(getDriver().findElement(By.xpath("//h1")).getText(), "Agent " + NODE_NAME);
    }

    @Test(dependsOnMethods = "testConfigure")
    public void testDeleteFromConfigurePage() {
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

    @Test(dependsOnMethods = "testOpenManageNodesPage")
    public void testVerifyNoDeleteOptionForBuiltInNode() {
        openNodesPage();
        getDriver().findElement(By.cssSelector("a[href$='(built-in)/']"));
        getDriver().findElement(By.cssSelector("button.jenkins-menu-dropdown-chevron")).click();
        List<String> list = new ArrayList<>();
        list.add(getDriver().findElement(By.cssSelector("a.jenkins-dropdown__item[href$='configure']")).getText());
        list.add(getDriver().findElement(By.cssSelector("a.jenkins-dropdown__item[href$='builds']")).getText());
        list.add(getDriver().findElement(By.cssSelector("a.jenkins-dropdown__item[href$='load-statistics']")).getText());
        list.add(getDriver().findElement(By.cssSelector("a.jenkins-dropdown__item[href$='script']")).getText());
        for (String element : list) {
            Assert.assertNotEquals(element, "Delete Agent");
        }
    }

    private void createPermanentAgent() {
        getWait2().until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='new']"))).click();
        getDriver().findElement(By.xpath("//input[@id='name']")).sendKeys(NODE_NAME);
        getDriver().findElement(By.xpath("//label[@class='jenkins-radio__label' and text()='Permanent Agent']")).click();
        getDriver().findElement(By.xpath("//button[@value='Create']")).click();
        getDriver().findElement(By.xpath("//button[@value='Save']")).click();
    }

    private void openNodesPage() {
        getWait2().until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@id='root-action-ManageJenkinsAction']//*[name()='svg']"))).click();
        getDriver().findElement(By.xpath("//a[.//dt[normalize-space()='Nodes']]")).click();
    }
}
