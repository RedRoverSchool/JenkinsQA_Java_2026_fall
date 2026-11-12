package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

public class BuildHistoryTest extends BaseTest {

    private static final String PROJECT_NAME = "BuildHistoryTest";
    private final By TEXT_LINK = By.cssSelector("#main-panel > div > div.jenkins-app-bar__controls > a:nth-child(4)");
    private final By DOWNLOAD_LINK = By.cssSelector("#main-panel > div > div.jenkins-app-bar__controls > a:nth-child(1)");
    private final By COPY_LINK = By.cssSelector("#main-panel > div > div.jenkins-app-bar__controls > span > button");

    @Test
    public void testBuildHistoryConsoleOutput() {
        createFreestyleProject(PROJECT_NAME);

        getDriver().findElement(By.cssSelector("a[href$='/build?delay=0sec'].task-link")).click();
        getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.id("jenkins-build-history")));
        getDriver().findElement(By.id("jenkins-head-icon")).click();
        getDriver().findElement(By.cssSelector("a[href$='/view/all/builds']")).click();
        getDriver().findElement(By.cssSelector("a.jenkins-button[href$='/console']")).click();

        WebElement outConsole = getWait2().until(ExpectedConditions.visibilityOfElementLocated(By.id("out")));
        Assert.assertTrue(outConsole.isDisplayed());
    }

    @Test (dependsOnMethods = "testBuildHistoryConsoleOutput")
    public void testConteinsConsoleOutput() {
        getDriver().findElement(By.cssSelector("a[href$='/view/all/builds']")).click();
        getDriver().findElement(By.cssSelector("a.jenkins-button[href$='/console']")).click();
        WebElement consoleText = getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.id("out")));
        String consoleOutputText = consoleText.getText();

        Assert.assertTrue(consoleOutputText.contains("Started by user"));
        Assert.assertTrue(consoleOutputText.contains("Building in workspace"));
        Assert.assertTrue(consoleOutputText.contains("Finished"));
    }

    @Test (dependsOnMethods = "testConteinsConsoleOutput")
    public void testTextConsoleOutput() {

        getDriver().findElement(By.cssSelector("a[href$='/view/all/builds']")).click();
        getDriver().findElement(By.cssSelector("a.jenkins-button[href$='/console']")).click();
        WebElement consoleText = getWait5().until(ExpectedConditions.visibilityOfElementLocated(By.id("out")));
        WebElement textButtom = getWait5().until(ExpectedConditions.elementToBeClickable(TEXT_LINK));
        textButtom.click();
        getWait2().until(ExpectedConditions.urlContains("consoleText"));

        Assert.assertTrue(getDriver().getCurrentUrl().endsWith("consoleText"), "Текстовая страница не обнаружена");
    }

    @Test
    public void testDownloadHistory() {
        createFreestyleProject(PROJECT_NAME);
        createBuildHistory();
        openConsoleOutput();
        WebElement downButton = getWait5().until(ExpectedConditions.elementToBeClickable(DOWNLOAD_LINK));

        Assert.assertTrue(downButton.getAttribute("href").endsWith("/consoleText"), "Ссылка не ведет на файл лога");
    }

    @Test
    public void testCopyHistory() {
        createFreestyleProject(PROJECT_NAME);
        createBuildHistory();
        openConsoleOutput();
        WebElement copyButton = getWait5().until(ExpectedConditions.elementToBeClickable(COPY_LINK));
        copyButton.click();
        String buttomClass = copyButton.getAttribute("class");

        Assert.assertTrue(buttomClass.contains("jenkins-copy-button--copied"), "Кнопка COPY не стала зеленой, значит текст не скопировался");
    }

    private void createFreestyleProject(String name) {
        getDriver().findElement(By.cssSelector("a[href*='newJob']")).click();
        getDriver().findElement(By.id("name")).sendKeys(name);
        getWait5().until(ExpectedConditions.elementToBeClickable(By.cssSelector("#items .hudson_model_FreeStyleProject"))).click();
        getWait5().until(ExpectedConditions.elementToBeClickable(By.id("ok-button"))).click();
        getWait10().until(ExpectedConditions.urlContains("/configure"));

        for (int i = 0; i < 3; i++) {
            try {
                getWait5().until(ExpectedConditions.elementToBeClickable(By.cssSelector("#bottom-sticker button.jenkins-button--primary"))).click();
                getWait5().until(ExpectedConditions.not(ExpectedConditions.urlContains("/configure")));
                break;
            } catch (StaleElementReferenceException | TimeoutException e) {
            }
        }
    }

    private void createBuildHistory() {
        getWait5().until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[href$='/build?delay=0sec'].task-link"))).click();
        getWait10().until(ExpectedConditions.visibilityOfElementLocated(By.id("jenkins-build-history")));
    }

    private void clickJenkinsLogo() {
        String baseXpath = "//span[contains(text(), '%s')]//ancestor::div//a[@class='app-jenkins-logo']";
        String finalXpath = String.format(baseXpath, PROJECT_NAME);
        WebElement logo = getWait5().until(ExpectedConditions.elementToBeClickable(By.xpath(finalXpath)));
        logo.click();
    }

    private void openConsoleOutput() {
        getDriver().get(school.redrover.common.ProjectUtils.getUrl() + "job/" + PROJECT_NAME + "/lastBuild/console");
    }
}
