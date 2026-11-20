package school.redrover;

import org.openqa.selenium.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;
import school.redrover.pages.HomePage;
import school.redrover.pages.PipelineConfigurationPage;
import school.redrover.pages.StatusPage;

import java.util.List;

public class Pipeline2Test extends BaseTest {

    private static final String PIPELINE_NAME = "Pipeline_test";
    private static final List<String> TRIGGER_NAMES = List.of(
            "Build after other projects are built",
            "Build periodically",
            "GitHub hook trigger for GITScm polling",
            "Poll SCM",
            "Trigger builds remotely"
    );

    @Test
    public void testCreateTriggers() {
        PipelineConfigurationPage triggerSection = new HomePage(getDriver())
                .clickNewItem()
                .inputProjectName(PIPELINE_NAME)
                .selectPipelineAndClickOk();

        Assert.assertTrue(triggerSection.isTriggerSectionDisplayed(), "Секция TRIGGERS не обнаружена");

        for (String triggerName : TRIGGER_NAMES) {
            Assert.assertTrue(triggerSection.isTriggerCheckboxPresent(triggerName), "Нет чекбокса у триггера при создании Pipeline: " + triggerName);
            Assert.assertTrue(triggerSection.isTriggerHelpIconPresent(triggerName), "Нет иконки '?' у триггера при создании Pipeline: " + triggerName);
        }
    }

    @Test
    public void testEditTriggers() {
        PipelineConfigurationPage statusPage = new HomePage(getDriver())
                .clickNewItem()
                .inputProjectName(PIPELINE_NAME)
                .selectPipelineAndClickOk();

        Assert.assertTrue(statusPage.isTriggerSectionDisplayed(), "Секция Triggers не обнаружена");

        for (String triggerName : TRIGGER_NAMES) {
            Assert.assertTrue(statusPage.isTriggerCheckboxPresent(triggerName), "Нет чекбокса у триггера на странице Configure: " + triggerName);
            Assert.assertTrue(statusPage.isTriggerHelpIconPresent(triggerName), "Нет иконки '?' у триггера при создании Configure: " + triggerName);
        }
    }

    @Test
    public void testCreateWithoutTriggers() {
        StatusPage statusPage = new HomePage(getDriver())
                .clickNewItem()
                .inputProjectName(PIPELINE_NAME)
                .selectPipelineAndClickOk()
                .clickSaveButton();

        Assert.assertTrue(statusPage.isConfigureLinkDisplayed(), "Проект не был создан без триггеров");
    }

    @Test
    public void testCheckAllTriggers() {
        PipelineConfigurationPage configPage = new HomePage(getDriver())
                .clickNewItem()
                .inputProjectName(PIPELINE_NAME)
                .selectPipelineAndClickOk();

        for (String triggerName : TRIGGER_NAMES) {
            configPage.checkAllTrigger(triggerName);
        }

        for (String triggerName : TRIGGER_NAMES) {
            Assert.assertTrue(configPage.isTriggerChecked(triggerName), "Чекбокс не включился: " + triggerName);
        }
    }
}