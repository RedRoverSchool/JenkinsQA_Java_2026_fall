package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

public class NodesTest extends BaseTest {
    private static final String NODE_NAME = "windows-agent";

    @Test
    public void testOpenManageNodesPage() {
        getDriver().findElement(By.xpath("//a[@id='root-action-ManageJenkinsAction']//*[name()='svg']")).click();
        getDriver().findElement(By.xpath("//a[.//dt[normalize-space()='Nodes']]")).click();

        Assert.assertTrue(
                getDriver().findElement(By.tagName("h1")).getText().contains("Nodes"),
                "Manage Nodes page is not opened"
        );
    }
    @Test
    public void testCreate() {
        getDriver().findElement(By.xpath("//a[@id='root-action-ManageJenkinsAction']//*[name()='svg']")).click();
        getDriver().findElement(By.xpath("//a[.//dt[normalize-space()='Nodes']]")).click();
        getDriver().findElement(By.xpath("//a[@href='new']")).click();
        getDriver().findElement(By.xpath("//input[@id='name']")).sendKeys(NODE_NAME);
        getDriver().findElement(By.xpath("//label[@class='jenkins-radio__label' and text()='Permanent Agent']")).click();
        getDriver().findElement(By.xpath("//button[@value='Create']")).click();
        getDriver().findElement(By.xpath("//button[@value='Save']")).click();

        Assert.assertTrue(getDriver().findElement(By.xpath("//tr[@id='node_windows-agent']")).isDisplayed());
    }

    @Test(dependsOnMethods = "testCreate")
    public void testConfigure() {
        final String description = "windows-тачка";

        getDriver().findElement(By.xpath("//span[text()='windows-agent']")).click();
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
}
