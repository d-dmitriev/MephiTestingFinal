package home.work.pages.mobile;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static home.work.utils.Helpers.*;

public class WikipediaMobilePage {
    private final AppiumDriver driver;
    private final WebDriverWait wait;

    public WikipediaMobilePage(AppiumDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public void searchFor(String query) {
        waitBy(wait, AppiumBy.id("search_src_text")).sendKeys(query);
        waitBy(wait, AppiumBy.id("page_list_item_title")).click();
    }

    public void changeLanguageRu() {
        waitBy(wait, AppiumBy.xpath("//android.widget.HorizontalScrollView[@resource-id=\"org.wikipedia.alpha:id/horizontal_scroll_languages\"]/android.widget.LinearLayout/android.widget.LinearLayout[2]")).click();
    }

    public String getArticleTitle(String text) {
        var title = waitBy(wait, AppiumBy.xpath("//android.webkit.WebView[@text=\""+text+"\"]"));
        return title.getText();
    }

    public void scrollAndCheckSection(String sectionText) {
        driver.findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector().scrollable(true))" +
                ".scrollIntoView(new UiSelector().text(\"" + sectionText + "\"))"
        ));
    }

    public void addLanguage(String lang) {
        waitBy(wait, AppiumBy.id("addLanguageButton")).click();
        waitBy(wait, AppiumBy.xpath("//androidx.recyclerview.widget.RecyclerView[@resource-id=\"org.wikipedia.alpha:id/wikipedia_languages_recycler\"]/android.widget.LinearLayout[3]")).click();
        langSearch(lang);
        driver.navigate().back();
    }

    public void langSearch(String lang) {
        waitBy(wait, AppiumBy.xpath("//androidx.compose.ui.platform.ComposeView/android.view.View/android.view.View/android.view.View[2]/android.view.View/android.view.View/android.view.View[3]")).click();
        waitBy(wait, AppiumBy.xpath("//android.widget.EditText")).sendKeys(lang);
        waitBy(wait, AppiumBy.xpath("//androidx.compose.ui.platform.ComposeView/android.view.View/android.view.View/android.view.View[1]/android.view.View")).click();
    }

    public void skipOnboarding() {
        waitBy(wait, AppiumBy.id("fragment_onboarding_skip_button")).click();
    }

    public void skipPopup() {
        try {
            if (waitBy(wait, AppiumBy.id("dialogContainer")).isDisplayed()) {
                waitBy(wait, AppiumBy.id("closeButton")).click();
            }
        } catch (TimeoutException ignored) {
        }
    }

    public void clickSearch() {
        waitBy(wait, AppiumBy.id("search_container")).click();
    }

    public void clickLanguages() {
        waitBy(wait, AppiumBy.id("page_language")).click();
    }
}
