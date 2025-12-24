package home.work.tests.mobile;

import home.work.pages.mobile.WikipediaMobilePage;
import home.work.utils.MobileDriverFactory;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.Reporter;
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
        Reporter.log("Searching for '" + SEARCH_APPIUM + "'", true);
        page.skipOnboarding();
        page.clickSearch();
        page.searchFor(SEARCH_APPIUM);
        page.skipPopup();
        String title = page.getArticleTitle(SEARCH_APPIUM);
        Assert.assertTrue(title.toLowerCase().contains(SEARCH_APPIUM.toLowerCase()), String.format("Article title should contain '%s'", SEARCH_APPIUM));
        Reporter.log("Search for '" + SEARCH_APPIUM + "' returned relevant results", true);
    }

    @Test
    public void testScrollToSection() {
        Reporter.log("Scrolling to section '" + SEARCH_ETYMOLOGY + "' in article '" + SEARCH_JAVA + "'", true);
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
        Reporter.log("Scrolled to section '" + SEARCH_ETYMOLOGY + "' successfully", true);
    }

    @Test
    public void testLanguageChange() {
        Reporter.log("Changing language to Russian and searching for '" + SEARCH_JAVA_RU + "'", true);
        page.addLanguage(LANG_RUSSIAN);
        page.skipOnboarding();
        page.clickSearch();
        page.searchFor(SEARCH_JAVA);
        page.skipPopup();
        page.clickLanguages();
        page.langSearch(LANG_RUSSIAN);
        String title = page.getArticleTitle(SEARCH_JAVA_RU);
        Assert.assertTrue(title.toLowerCase().contains(SEARCH_JAVA_RU.toLowerCase()), String.format("Article title should contain '%s'", SEARCH_JAVA_RU));
        Reporter.log("Language changed to Russian and search for '" + SEARCH_JAVA_RU + "' returned relevant results", true);
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
        Reporter.log("Changing language to '" + langCode + "' and searching for '" + query + "'", true);
        page.addLanguage(langCode);
        page.skipOnboarding();
        page.clickSearch();
        page.changeLanguage(langCode);
        page.searchFor(query);
        page.skipPopup();
        String title = page.getArticleTitle(query);
        Assert.assertTrue(title.toLowerCase().contains(query.toLowerCase()), String.format("Article title should contain '%s'", query));
        Reporter.log("Language changed to '" + langCode + "' and search for '" + query + "' returned relevant results", true);
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) driver.quit();
    }
}
