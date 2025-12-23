package home.work.tests.mobile;

import home.work.pages.mobile.WikipediaMobilePage;
import home.work.utils.MobileDriverFactory;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.net.MalformedURLException;

public class WikipediaMobileTests {
    public static final String LANG_BELARUS = "Беларуская";
    public static final String LANG_RUSSIAN = "Russian";
    public static final String LANG_RUSSIAN_RU = "Русский";
    public static final String SEARCH_APPIUM = "Appium";
    public static final String SEARCH_JAVA = "Java";
    public static final String SEARCH_JAVA_RU = "Ява";
    public static final String SEARCH_ETYMOLOGY = "Etymology";

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
        page.searchFor(SEARCH_APPIUM);
        page.skipPopup();
        String title = page.getArticleTitle(SEARCH_APPIUM);
        Assert.assertTrue(title.toLowerCase().contains(SEARCH_APPIUM.toLowerCase()), String.format("Article title should contain '%s'", SEARCH_APPIUM));
    }

    @Test
    public void testScrollToSection() {
        page.skipOnboarding();
        page.clickSearch();
        page.searchFor(SEARCH_JAVA);
        page.skipPopup();
        page.scrollAndCheckSection(SEARCH_ETYMOLOGY);
        // Проверим, что элемент с текстом "History" теперь виден
        Assert.assertTrue(
                driver.findElement(By.xpath("//*[contains(@text, '" + SEARCH_ETYMOLOGY + "')]")).isDisplayed(),
                String.format("Section '%s' should be visible after scrolling", SEARCH_ETYMOLOGY)
        );
    }

    @Test
    public void testLanguageChange() {
        page.addLanguage(LANG_RUSSIAN);
        page.skipOnboarding();
        page.clickSearch();
        page.searchFor(SEARCH_JAVA);
        page.skipPopup();
        page.clickLanguages();
        page.langSearch(LANG_RUSSIAN);
        String title = page.getArticleTitle(SEARCH_JAVA_RU);
        Assert.assertTrue(title.toLowerCase().contains(SEARCH_JAVA_RU.toLowerCase()), String.format("Article title should contain '%s'", SEARCH_JAVA_RU));
    }

    @DataProvider
    public Object[][] languages() {
        return new Object[][]{
                {LANG_BELARUS, SEARCH_JAVA_RU},
                {LANG_RUSSIAN_RU, SEARCH_JAVA_RU}
        };
    }

    @Test(dataProvider = "languages")
    public void testSearchRu(String langCode, String query) {
        page.addLanguage(langCode);
        page.skipOnboarding();
        page.clickSearch();
        page.changeLanguage(langCode);
        page.searchFor(query);
        page.skipPopup();
        String title = page.getArticleTitle(query);
        Assert.assertTrue(title.toLowerCase().contains(query.toLowerCase()), String.format("Article title should contain '%s'", query));
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) driver.quit();
    }
}
