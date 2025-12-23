package home.work.tests.mobile;

import home.work.utils.MobileDriverFactory;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import home.work.pages.mobile.WikipediaMobilePage;

import java.net.MalformedURLException;
import java.time.Duration;

import static home.work.utils.Helpers.sleep;
import static home.work.utils.Helpers.waitBy;

public class WikipediaMobileTests {
    private AppiumDriver driver;
    private WikipediaMobilePage page;
    private WebDriverWait wait;

//    @BeforeClass
    @BeforeMethod
    public void setUp() throws MalformedURLException {
        driver = MobileDriverFactory.createDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        page = new WikipediaMobilePage(driver);

//        page.openSearch();
//        page.deleteHistory();
        page.addLanguage("russian");
        page.skipOnboarding();
    }

    @Test
    public void testSearchAndOpenArticle() {
        page.clickSearch();
        page.searchFor("Appium");
        page.skipPopup();
        String title = page.getArticleTitle("Appium");
        Assert.assertTrue(title.toLowerCase().contains("appium"), "Article title should contain 'Appium'");
    }

//    @Test
//    public void testScrollToSection() {
//        page.clickSearch();
//        page.searchFor("Java");
//        page.skipPopup();
//        page.scrollAndCheckSection("Etymology");
//        // Проверим, что элемент с текстом "History" теперь виден
//        Assert.assertTrue(
//                driver.findElement(By.xpath("//*[contains(@text, 'Etymology')]")).isDisplayed()
//        );
//    }

    @Test
    public void testLanguageChange() {
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
        page.clickSearch();
        page.changeLanguageRu();
        page.searchFor("Ява");
        page.skipPopup();
        String title = page.getArticleTitle("Ява");
        Assert.assertTrue(title.toLowerCase().contains("ява"), "Article title should contain 'Appium'");
    }

//    @AfterClass
    @AfterMethod
    public void tearDown() {
//        page.backFromArticle();
        if (driver != null) driver.quit();
    }
}
