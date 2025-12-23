package home.work.tests.web;

import home.work.config.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import home.work.pages.web.WikipediaHomePage;
import home.work.utils.WebDriverFactory;

public class WikipediaWebTests {
    public static final String SEARCH_JAVA = "Java";
    public static final String SEARCH_PYTHON = "Python";
    public static final String SEARCH_SELENIUM = "Selenium";
    public static final String LANG_CODE = "ru";
    public static final String RU_WIKIPEDIA_ORG = "ru.wikipedia.org";

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
        Assert.assertTrue(homePage.getPageTitle().contains(SEARCH_SELENIUM), String.format("Page title should contain '%s'", SEARCH_SELENIUM));
    }

    @Test
    public void testLanguageSwitch() {
        homePage.open();
        homePage.selectLanguage(LANG_CODE);
        Assert.assertTrue(driver.getCurrentUrl().contains(RU_WIKIPEDIA_ORG), String.format("URL should contain '%s'", RU_WIKIPEDIA_ORG));
    }

    @Test
    public void testMultipleSearches() {
        homePage.open();
        homePage.searchFor(SEARCH_JAVA);
        Assert.assertTrue(homePage.getPageTitle().contains(SEARCH_JAVA));

        homePage.open();
        homePage.searchFor(SEARCH_PYTHON);
        Assert.assertTrue(homePage.getPageTitle().contains(SEARCH_PYTHON));
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) driver.quit();
    }
}
