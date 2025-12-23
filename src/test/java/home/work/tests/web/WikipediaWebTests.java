package home.work.tests.web;

import home.work.config.ConfigReader;
import home.work.pages.web.WikipediaHomePage;
import home.work.utils.WebDriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class WikipediaWebTests {
    public static final String SEARCH_JAVA = "Java";
    public static final String SEARCH_PYTHON = "Python";
    public static final String SEARCH_SELENIUM = "Selenium";
    public static final String LANG_CODE_EN = "en";
    public static final String LANG_CODE_RU = "ru";
    public static final String RU_WIKIPEDIA_ORG = "ru.wikipedia.org";
    public static final String WIKIPEDIA = "wikipedia";
    public static final String WIKIPEDIA_RU = "википедия";
    public static final String TERMS_OF_USE = "Terms of Use";
    public static final String PRIVACY_POLICY = "Privacy Policy";

    private WebDriver driver;
    private WikipediaHomePage homePage;

    @BeforeMethod
    public void setUp() {
        driver = WebDriverFactory.getDriver(ConfigReader.getProperty("browser"));
        homePage = new WikipediaHomePage(driver);
    }

    @Test
    public void testHomePageLoads() {
        homePage.open();
        Assert.assertTrue(homePage.isSearchInputVisible(), "Search input should be visible");
    }

    @Test
    public void testSearchFunctionality() {
        homePage.open();
        homePage.searchFor(SEARCH_SELENIUM);
        Assert.assertTrue(homePage.getPageHeader().contains(SEARCH_SELENIUM), String.format("Page title should contain '%s'", SEARCH_SELENIUM));
    }

    @Test
    public void testLanguageSwitch() {
        homePage.open();
        homePage.selectLanguage(LANG_CODE_RU);
        Assert.assertTrue(driver.getCurrentUrl().contains(RU_WIKIPEDIA_ORG), String.format("URL should contain '%s'", RU_WIKIPEDIA_ORG));
    }

    @Test
    public void testMultipleSearches() {
        homePage.open();
        homePage.searchFor(SEARCH_JAVA);
        Assert.assertTrue(homePage.getPageHeader().contains(SEARCH_JAVA), "Page header should reflect the first search query");

        homePage.open();
        homePage.searchFor(SEARCH_PYTHON);
        Assert.assertTrue(homePage.getPageHeader().contains(SEARCH_PYTHON), "Page header should reflect the second search query");
    }

    @Test
    public void testSearchSuggestionsAppear() {
        homePage.open();
        homePage.enterSearchQuery(SEARCH_JAVA); // не нажимаем Enter, только ввод
        var suggestions = homePage.listSuggestions();
        Assert.assertFalse(suggestions.isEmpty(), "Search suggestions should appear");
    }

    @Test
    public void testClickFirstSearchSuggestion() {
        homePage.open();
        homePage.enterSearchQuery(SEARCH_PYTHON);
        homePage.clickFirstSuggestion();
        Assert.assertTrue(homePage.getPageHeader().toLowerCase().contains(SEARCH_PYTHON.toLowerCase()), "Page header should reflect the first suggestion clicked");
    }

    @Test
    public void testFooterLinksExist() {
        homePage.open();
        homePage.selectLanguage(LANG_CODE_EN);
        Assert.assertTrue(homePage.isFooterLinkPresent(TERMS_OF_USE), "Terms of Use link should be present in footer");
        Assert.assertTrue(homePage.isFooterLinkPresent(PRIVACY_POLICY), "Privacy Policy link should be present in footer");
    }

    @Test
    public void testRussianHomePageTitle() {
        homePage.open();
        homePage.selectLanguage(LANG_CODE_RU);
        String title = driver.getTitle().toLowerCase();
        Assert.assertTrue(title.contains(WIKIPEDIA_RU) || title.contains(WIKIPEDIA), "Title should reflect Russian language");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) driver.quit();
    }
}
