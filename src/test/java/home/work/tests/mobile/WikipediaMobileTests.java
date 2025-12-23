package home.work.tests.mobile;

import home.work.utils.MobileDriverFactory;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.*;
import home.work.pages.mobile.WikipediaMobilePage;

import java.net.MalformedURLException;

import static home.work.utils.Helpers.sleep;

public class WikipediaMobileTests {
    private AppiumDriver driver;
    private WikipediaMobilePage page;

    @BeforeMethod
    public void setUp() throws MalformedURLException {
        driver = MobileDriverFactory.createDriver();
        page = new WikipediaMobilePage(driver);
    }

    @Test
    public void testSearchAndOpenArticle() {
        page.skipOnboarding();
        page.clickSearch();
        page.searchFor("Appium");
        page.skipPopup();
        String title = page.getArticleTitle("Appium");
        Assert.assertTrue(title.toLowerCase().contains("appium"), "Article title should contain 'Appium'");
    }

    @Test
    public void testScrollToSection() {
        page.skipOnboarding();
        page.clickSearch();
        page.searchFor("Java");
        page.skipPopup();
        sleep();
        page.scrollAndCheckSection("Etymology");
        // Проверим, что элемент с текстом "History" теперь виден
        Assert.assertTrue(
                driver.findElement(By.xpath("//*[contains(@text, 'Etymology')]")).isDisplayed()
        );
    }

    @Test
    public void testLanguageChange() {
        page.addLanguage("russian");
        page.skipOnboarding();
        page.clickSearch();
        page.searchFor("Java");
        page.skipPopup();
        page.clickLanguages();
        page.langSearch("russian");
        String title = page.getArticleTitle("Ява");
        Assert.assertTrue(title.toLowerCase().contains("ява"), "Article title should contain 'Appium'");
    }

    @Test
    public void testSearchRu() {
        page.addLanguage("russian");
        page.skipOnboarding();
        page.clickSearch();
        page.changeLanguageRu();
        page.searchFor("Ява");
        page.skipPopup();
        String title = page.getArticleTitle("Ява");
        Assert.assertTrue(title.toLowerCase().contains("ява"), "Article title should contain 'Appium'");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) driver.quit();
    }
}
