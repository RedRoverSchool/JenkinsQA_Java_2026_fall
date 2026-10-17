package school.redrover;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import school.redrover.common.BaseTest;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.testng.Assert.assertFalse;

public class ProfileTest extends BaseTest {
    @Ignore
    @Test

    public void testCheckAddDescription (){
        getDriver().findElement(By.cssSelector(".jenkins-avatar")).click();
        WebElement button = getDriver().findElement(By.id("description-link"));

        Assert.assertEquals(button.getText(), "Add description");
    }

@Ignore
    @Test
    public void testCheckMenu (){
        getDriver().findElement(By.cssSelector(".jenkins-avatar")).click();
        List<WebElement> links = getDriver().findElements(
                By.cssSelector("#tasks .task-link")
        );

        Map<String, String> actual = links.stream()
                .collect(Collectors.toMap(
                        el -> el.findElement(By.cssSelector(".task-link-text")).getText().trim(),
                        el -> el.getDomAttribute("href"),   // ← вот здесь
                        (a, b) -> a,
                        LinkedHashMap::new
                ));

        Map<String, String> expected = new LinkedHashMap<>();
        expected.put("Profile",     "/user/admin/");
        expected.put("Builds",      "/user/admin/builds");
        expected.put("My Views",    "/user/admin/my-views");
        expected.put("Account",     "/user/admin/account");
        expected.put("Appearance",  "/user/admin/appearance");
        expected.put("Preferences", "/user/admin/preferences");
        expected.put("Security",    "/user/admin/security");
        expected.put("Experiments", "/user/admin/experiments");
        expected.put("Credentials", "/user/admin/credentials");

        Assert.assertEquals(actual, expected);
    }
@Ignore
    @Test
    public void testCheckMenu2(){
        getDriver().findElement(By.cssSelector(".jenkins-avatar")).click();

        List<String> actualTitles = getDriver()
                .findElements(By.cssSelector("#tasks .task-link .task-link-text"))
                .stream()
                .map(WebElement::getText)
                .map(String::trim)
                .collect(Collectors.toList());

        List<String> expectedTitles = List.of(
                "Profile", "Builds", "My Views", "Account", "Appearance",
                "Preferences", "Security", "Experiments", "Credentials"
        );

        Assert.assertEquals(actualTitles,expectedTitles);
    }

    @Test
    public void testTasksMenuExists() {

        getDriver().findElement(By.cssSelector(".jenkins-avatar")).click();

        List<WebElement> menu = getDriver().findElements(By.id("tasks"));
        assertFalse(menu.isEmpty(), "Блок #tasks не найден на странице");

        // 3. Проверяем, что внутри есть хотя бы одна ссылка
        List<WebElement> links = getDriver().findElements(
                By.cssSelector("#tasks .task-link")
        );
        assertFalse(links.isEmpty(), "В меню #tasks нет ни одной ссылки");

        // 4. Проверяем, что у каждой ссылки есть текст и href
        for (WebElement link : links) {
            String text = link.findElement(By.cssSelector(".task-link-text"))
                    .getText().trim();
            String href = link.getDomAttribute("href");

            assertFalse(text.isEmpty(), "У ссылки пустой текст");
            Assert.assertNotNull("У ссылки '" + text + "' нет href", href);
            assertFalse(href.isEmpty(), "У ссылки '" + text + "' пустой href");
        }
    }
}
