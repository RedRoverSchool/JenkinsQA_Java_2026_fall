package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.pages.BuildHistoryPage;
import school.redrover.pages.HomePage;
import school.redrover.pages.StatusPage;

public class BuildHistoryTest extends BaseTest {

    private static final String PROJECT_NAME = "BuildHistoryTest";

    @Test
    public void testBuildHistoryConsoleOutput() {
        BuildHistoryPage consoleOutput = new HomePage(getDriver())
                .clickNewItem()
                .inputProjectName(PROJECT_NAME)
                .selectFreestyleAndClickOK()
                .clickSaveButton()
                .clickBuildNow()
                .waitForBuildHistory()
                .goToHomePage()
                .clickBuildsHistory()
                .openConsole();

        Assert.assertTrue(consoleOutput.isConsoleOutputIsDisplayed(), "Консольный вывод билда не отображается");
    }

    @Test(dependsOnMethods = "testBuildHistoryConsoleOutput")
    public void testConteinsConsoleOutput() {
        String consoleOutput = new HomePage(getDriver())
                .clickBuildsHistory()
                .openConsole()
                .getConsoleOutput();

        Assert.assertTrue(consoleOutput.contains("Started by user"),
                "В консоли нет 'Started by user'");
        Assert.assertTrue(consoleOutput.contains("Building in workspace"),
                "В консоли нет 'Building in workspace'");
        Assert.assertTrue(consoleOutput.contains("Finished"),
                "В консоли нет 'Finished'");
    }

    @Test(dependsOnMethods = "testConteinsConsoleOutput")
    public void testTextConsoleOutput() {
        new HomePage(getDriver())
                .clickBuildsHistory()
                .openConsole()
                .clickConsoleTextLink();

        Assert.assertTrue(getDriver().getCurrentUrl().endsWith("consoleText"), "Текстовая страница не обнаружена");
    }

    @Test(dependsOnMethods = "testTextConsoleOutput")
    public void testDownloadHistory() {
        String downButton = new HomePage(getDriver())
                .clickBuildsHistory()
                .openConsole()
                .getDownloadHistoryLink();

        Assert.assertTrue(downButton.endsWith("/consoleText"), "Ссылка не ведет на файл лога" + downButton);
    }

    @Test(dependsOnMethods = "testTextConsoleOutput")
    public void testCopyHistory() {
        boolean copyButton = new HomePage(getDriver())
                .clickBuildsHistory()
                .openConsole()
                .clickCopyButton()
                .isCopyButtonCopied();

        Assert.assertTrue(copyButton, "Кнопка COPY не стала зеленой, значит текст не скопировался");
    }
}
