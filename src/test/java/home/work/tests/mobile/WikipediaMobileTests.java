package home.work.tests.mobile;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import home.work.pages.mobile.WikipediaMobilePage;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Map;

import static home.work.utils.DriverFactory.sleep;

public class WikipediaMobileTests {
    private AppiumDriver driver;
    private WikipediaMobilePage page;

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
        page = new WikipediaMobilePage(driver);
    }

    @Test
    public void testSearchAndOpenArticle() {
        page.startMain();
        sleep();
        page.searchFor("Appium");
        sleep();
        page.clickFirstResult();
        sleep();
        String title = page.getArticleTitle();
        Assert.assertTrue(title.toLowerCase().contains("appium"), "Article title should contain 'Appium'");
    }

//    @Test
//    public void testScrollToSection() {
////        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
////        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.id("id")));
//        page.startMain();
//        sleep();
//        page.searchFor("Java");
//        sleep();
//        page.clickFirstResult();
//        sleep();
//        page.scrollAndCheckSection("Etymology");
//        // Проверим, что элемент с текстом "History" теперь виден
//        Assert.assertTrue(
//                driver.findElement(By.xpath("//*[contains(@text, 'Etymology')]")).isDisplayed()
//        );
//    }

//    @Test
//    public void testLanguageChange() {
//        Map<String, Object> params = Map.of("component", "org.wikipedia.alpha/org.wikipedia.main.MainActivity");
//        driver.executeScript("mobile: startActivity", params);
//        sleep();
//        // Пример: переключиться на русский (требует доработки под UI)
//        // Для упрощения — опустим или сделаем более простой сценарий
//        // Вместо этого — проверим, что меню открывается
//        WebElement language = driver.findElement(By.id("org.wikipedia.alpha:id/page_language"));
//        language.click();
//        sleep();
//        Assert.assertTrue(
//                driver.findElement(By.xpath("//android.widget.TextView[@text='All languages']")).isDisplayed()
//        );
//    }

//    @AfterClass
    @AfterMethod
    public void tearDown() {
        if (driver != null) driver.quit();
    }
}
