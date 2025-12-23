package home.work.tests.web;

import home.work.config.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import home.work.pages.web.WikipediaHomePage;
import home.work.utils.DriverFactory;

import static home.work.utils.Helpers.sleep;

public class WikipediaWebTests {
    private WebDriver driver;
    private WikipediaHomePage homePage;

//    @BeforeClass
    @BeforeMethod
    public void setUp() {
        driver = DriverFactory.getDriver(ConfigReader.getProperty("browser"));
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
        homePage.searchFor("Selenium");
        Assert.assertTrue(homePage.getPageTitle().contains("Selenium"), "Page title should contain 'Selenium'");
    }

    @Test
    public void testLanguageSwitch() {
        homePage.open();
        homePage.selectLanguage("ru");
        Assert.assertTrue(driver.getCurrentUrl().contains("ru.wikipedia.org"), "URL should contain 'ru.wikipedia.org'");
    }

    @Test
    public void testMultipleSearches() {
        homePage.open();
        homePage.searchFor("Java");
        Assert.assertTrue(homePage.getPageTitle().contains("Java"));
        sleep();
        homePage.open();
        homePage.searchFor("Python");
        Assert.assertTrue(homePage.getPageTitle().contains("Python"));
    }

//    @AfterClass
    @AfterMethod
    public void tearDown() {
        if (driver != null) driver.quit();
    }
}
