package home.work.tests.mobile;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import home.work.pages.mobile.WikipediaMobilePage;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Map;

import static home.work.utils.Helpers.sleep;
import static home.work.utils.Helpers.waitBy;

public class WikipediaMobileTests {
    private AppiumDriver driver;
    private WikipediaMobilePage page;
    private WebDriverWait wait;

//    @BeforeClass
    @BeforeMethod
    public void setUp() throws MalformedURLException {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platformName", "Android");
        caps.setCapability("platformVersion", "16.0");
        caps.setCapability("deviceName", "emulator-5554");
        caps.setCapability("appPackage", "org.wikipedia.alpha");
        caps.setCapability("appActivity", "org.wikipedia.main.MainActivity");
        caps.setCapability("automationName", "UiAutomator2");
        caps.setCapability("noReset", true);

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), caps);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        page = new WikipediaMobilePage(driver);

        page.backFromArticle();
        page.openSearch();
        page.deleteHistory();
    }

    @Test
    public void testSearchAndOpenArticle() {
        page.startMain();
        sleep();
        page.changeLanguageEn();
        page.searchFor("Appium");
        page.clickFirstResult();
        String title = page.getArticleTitle("Appium");
        Assert.assertTrue(title.toLowerCase().contains("appium"), "Article title should contain 'Appium'");
    }

    @Test
    public void testScrollToSection() {
        page.startMain();
        sleep();
        page.changeLanguageEn();
        page.searchFor("Java");
//        sleep();
        page.clickFirstResult();
        sleep();
        page.scrollAndCheckSection("Etymology");
        // Проверим, что элемент с текстом "History" теперь виден
        Assert.assertTrue(
                driver.findElement(By.xpath("//*[contains(@text, 'Etymology')]")).isDisplayed()
        );
    }

//    @Test
//    public void testLanguageChange() {
//        page.startMain();
//        sleep();
//        WebElement language = waitBy(wait, AppiumBy.id("org.wikipedia.alpha:id/page_language"));
//        language.click();
//        WebElement search = waitBy(wait, AppiumBy.xpath("//androidx.compose.ui.platform.ComposeView/android.view.View/android.view.View/android.view.View[2]/android.view.View/android.view.View/android.view.View[3]"));
//        sleep();
//        search.click();
//        WebElement text = waitBy(wait, AppiumBy.xpath("//android.widget.EditText"));
//        text.sendKeys("russian");
//        sleep();
//        WebElement firstElement = waitBy(wait, AppiumBy.xpath("//androidx.compose.ui.platform.ComposeView/android.view.View/android.view.View/android.view.View[1]/android.view.View"));
//        firstElement.click();
//        String title = page.getArticleTitle("Ява");
//        Assert.assertTrue(title.toLowerCase().contains("ява"), "Article title should contain 'Appium'");
////        Assert.assertTrue(
////                driver.findElement(By.xpath("//android.widget.TextView[@text='All languages']")).isDisplayed()
////        );
//        sleep();
//        driver.navigate().back();
//    }

    @Test
    public void testSearchRu() {
        page.startMain();
        sleep();
        page.changeLanguageRu();
        sleep();
        page.searchFor("Ява");
        sleep();
        page.clickFirstResult();
        String title = page.getArticleTitle("Ява");
        Assert.assertTrue(title.toLowerCase().contains("ява"), "Article title should contain 'Appium'");
    }

//    @AfterClass
    @AfterMethod
    public void tearDown() {
        if (driver != null) driver.quit();
    }
}
