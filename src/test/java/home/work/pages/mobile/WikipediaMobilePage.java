package home.work.pages.mobile;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Map;

import static home.work.utils.Helpers.*;

public class WikipediaMobilePage {
    private final AppiumDriver driver;
    private final WebDriverWait wait;

    @AndroidFindBy(id = "search_container")
    private WebElement searchContainer;
    @AndroidFindBy(id = "nav_tab_search")
    private WebElement searchTab;
    @AndroidFindBy(id = "search_card")
    private WebElement searchCard;
//    @AndroidFindBy(id = "search_src_text")
//    private WebElement searchInput;
    @AndroidFindBy(id = "history_delete")
    private WebElement historyDelete;

    public WikipediaMobilePage(AppiumDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public void backFromArticle() {
        if (existsById(driver, "page_find_in_article")) {
            while (!existsById(driver, "nav_tab_explore")) {
                driver.navigate().back();
            }
        }
    }

    public void openSearch() {
        if(searchTab.isEnabled() && !searchTab.isSelected()){
            searchTab.click();
        }
    }

    public void deleteHistory() {
        if(existsById(driver, "history_delete") && historyDelete.isEnabled()) {
            historyDelete.click();
            var bar = waitBy(wait, AppiumBy.id("action_bar_root"));
            if(bar.isEnabled()) {
                var button = waitBy(wait, AppiumBy.xpath("//android.widget.Button[@resource-id=\"android:id/button1\"]"));
                if (button.isEnabled()) {
                    button.click();
                }
            }
        }
    }

    public void searchFor(String query) {
//        if (searchContainerExists()) {
//            searchContainer.click();
//        } else {
//            if (findInArticleExists())
//                driver.navigate().back();
//        }
        var searchInput = waitBy(wait, AppiumBy.id("search_src_text"));
        searchInput.sendKeys(query);
//            searchContainer.click();
//            boolean cardExists = !driver.findElements(AppiumBy.id("search_card")).isEmpty();
//            if (cardExists)
//                searchCard.click();
//        driver.hideKeyboard();
    }

    public void clickFirstResult() {
        var firstResult = waitBy(wait, AppiumBy.id("page_list_item_title"));
        firstResult.click();
    }

    public void changeLanguageEn() {
        if (existsById(driver, "search_container")) {
            searchContainer.click();
        } else {
            if (existsById(driver, "page_find_in_article"))
                driver.navigate().back();
        }
        var langElement = waitBy(wait, AppiumBy.xpath("//android.widget.HorizontalScrollView[@resource-id=\"org.wikipedia.alpha:id/horizontal_scroll_languages\"]/android.widget.LinearLayout/android.widget.LinearLayout[1]"));
        System.out.println("!!! changeLanguageEn " + langElement.isEnabled());
        if (!langElement.isSelected())
            langElement.click();
    }

    public void changeLanguageRu() {
        if (existsById(driver, "search_container")) {
            searchContainer.click();
        }
        var langElement = waitBy(wait, AppiumBy.xpath("//android.widget.HorizontalScrollView[@resource-id=\"org.wikipedia.alpha:id/horizontal_scroll_languages\"]/android.widget.LinearLayout/android.widget.LinearLayout[2]"));
        System.out.println("!!! changeLanguageRu " + langElement.isEnabled());
        if (!langElement.isSelected())
            langElement.click();
    }

    public String getArticleTitle(String text) {
        var title = waitBy(wait, AppiumBy.xpath("//android.webkit.WebView[@text=\""+text+"\"]"));
        return title.getText();
    }

    public void scrollAndCheckSection(String sectionText) {
        driver.findElement(AppiumBy.androidUIAutomator(
                "new UiScrollable(new UiSelector().scrollable(true)).scrollForward();" +
                        "new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().text(\"" + sectionText + "\"))"
        ));
        // Утверждение будет в тесте
    }

    public void changeLanguageTo(String lang) {
        driver.findElement(By.id("org.wikipedia.alpha:id/menu_overflow_button")).click();
        driver.findElement(By.xpath("//android.widget.TextView[@text='Settings']")).click();
        driver.findElement(By.xpath("//android.widget.TextView[@text='Wikipedia languages']")).click();
        driver.findElement(By.id("org.wikipedia.alpha:id/add_wiki_language_button")).click();
        driver.findElement(By.xpath("//android.widget.TextView[@text='" + lang + "']")).click();
    }

    public void startMain() {
        Map<String, Object> params = Map.of("component", "org.wikipedia.alpha/org.wikipedia.main.MainActivity");
        driver.executeScript("mobile: startActivity", params);
    }
}
