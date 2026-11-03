package school.redrover;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

public class NodesTest extends BaseTest {

    @Test
    public void testOpenManageNodesPage() {
        getDriver().findElement(By.xpath("//a[@id='root-action-ManageJenkinsAction']//*[name()='svg']")).click();
        getDriver().findElement(By.xpath("//a[.//dt[normalize-space()='Nodes']]")).click();

        Assert.assertTrue(
                getDriver().findElement(By.tagName("h1")).getText().contains("Nodes"),
                "Manage Nodes page is not opened"
        );
    }
}
