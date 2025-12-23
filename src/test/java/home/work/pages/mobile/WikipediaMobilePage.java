package home.work.pages.mobile;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static home.work.utils.Helpers.waitBy;

public class WikipediaMobilePage {
    public static final By SEARCH_STRING_EDIT = By.xpath("//android.widget.EditText");
    public static final By SEARCH_LANG_BUTTON = By.xpath("//android.view.View[@content-desc=\"Search\"]");
    public static final By ADD_LANGUAGE_TEXT = By.xpath("//android.widget.TextView[@text=\"Add language\"]");

    public static final By ADD_LANGUAGE_BUTTON = By.id("addLanguageButton");
    public static final By SKIP_BUTTON = By.id("fragment_onboarding_skip_button");
    public static final By DIALOG_CONTAINER = By.id("dialogContainer");
    public static final By CLOSE_BUTTON = By.id("closeButton");
    public static final By SEARCH_CONTAINER = By.id("search_container");
    public static final By PAGE_LANGUAGE = By.id("page_language");
    public static final By SEARCH_SRC_TEXT = By.id("search_src_text");
    public static final By PAGE_LIST_ITEM_TITLE = By.id("page_list_item_title");

    private final AppiumDriver driver;
    private final WebDriverWait wait;

    public WikipediaMobilePage(AppiumDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public void searchFor(String query) {
        waitBy(wait, SEARCH_SRC_TEXT).sendKeys(query);
        waitBy(wait, PAGE_LIST_ITEM_TITLE).click();
    }

    public void changeLanguage(String lang) {
        waitBy(wait, By.xpath("//android.widget.TextView[@text=\"" + lang.toUpperCase() + "\"]")).click();
    }

    public String getArticleTitle(String text) {
        var title = waitBy(wait, By.xpath("//android.widget.TextView[@text=\"" + text + "\"]"));
        return title.getText();
    }

    public void scrollAndCheckSection(String sectionText) {
        for (int i = 0; i < 3; i++) {
            try {
                driver.findElement(AppiumBy.androidUIAutomator(
                        "new UiScrollable(new UiSelector().scrollable(true))"
                                + ".scrollIntoView(new UiSelector().text(\"" + sectionText + "\"))"
                ));
                return;
            } catch (Exception ignored) {
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
            }
        }
    }

    public void addLanguage(String lang) {
        waitBy(wait, ADD_LANGUAGE_BUTTON).click();
        waitBy(wait, ADD_LANGUAGE_TEXT).click();
        langSearch(lang);
        driver.navigate().back();
    }

    public void langSearch(String lang) {
        waitBy(wait, SEARCH_LANG_BUTTON).click();
        waitBy(wait, SEARCH_STRING_EDIT).sendKeys(lang);
        waitBy(wait, By.xpath("//android.widget.TextView[@text=\"" + lang + "\"]")).click();
    }

    public void skipOnboarding() {
        waitBy(wait, SKIP_BUTTON).click();
    }

    public void skipPopup() {
        try {
            if (waitBy(wait, DIALOG_CONTAINER).isDisplayed()) {
                waitBy(wait, CLOSE_BUTTON).click();
            }
        } catch (TimeoutException ignored) {
        }
    }

    public void clickSearch() {
        waitBy(wait, SEARCH_CONTAINER).click();
    }

    public void clickLanguages() {
        waitBy(wait, PAGE_LANGUAGE).click();
    }
}
