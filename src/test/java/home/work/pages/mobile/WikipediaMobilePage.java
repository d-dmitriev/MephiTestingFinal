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

import static home.work.utils.Helpers.waitBy;

public class WikipediaMobilePage {
    private AppiumDriver driver;
    private WebDriverWait wait;

    @AndroidFindBy(id = "search_container")
    private WebElement searchContainer;
    @AndroidFindBy(id = "nav_tab_search")
    private WebElement searchTab;
    @AndroidFindBy(id = "search_card")
    private WebElement searchCard;
    @AndroidFindBy(id = "search_src_text")
    private WebElement searchInput;

    public WikipediaMobilePage(AppiumDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public void searchFor(String query) {
        if (searchContainerExists()) {
            searchContainer.click();
        } else {
            if (findInArticleExists())
                driver.navigate().back();
        }
        if (searchInput.isEnabled()) {
            searchInput.sendKeys(query);
        }
//            searchContainer.click();
//            boolean cardExists = !driver.findElements(AppiumBy.id("search_card")).isEmpty();
//            if (cardExists)
//                searchCard.click();
//        driver.hideKeyboard();
    }

    public void clickFirstResult() {
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//        WebElement firstResult = wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.id("page_list_item_title")));
        var firstResult = waitBy(wait, AppiumBy.id("page_list_item_title"));
//        WebElement firstResult = driver.findElement(By.xpath("(//android.widget.TextView[@resource-id='org.wikipedia.alpha:id/page_list_item_title'])[1]"));
        firstResult.click();
    }

    public String getArticleTitle() {
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//        WebElement title = wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.xpath("(//android.widget.TextView[@text='Appium'])[1]")));
        var title = waitBy(wait, AppiumBy.xpath("(//android.widget.TextView[@text='Appium'])[1]"));
//        WebElement title = driver.findElement(By.xpath("(//android.widget.TextView[@text='Appium'])[1]"));
        return title.getText();
    }

    public void scrollAndCheckSection(String sectionText) {
//        driver.findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector().scrollable(true))" +
//                        ".scrollIntoView(new UiSelector().text(\"" + sectionText + "\"))"
//        ));
//        Map<String, Object> args = Map.of(
//                "strategy", "-android uiautomator",
//                "selector", "new UiSelector().text(\"" + sectionText + "\")"
//        );
//        driver.executeScript("mobile: scrollToElement", args);
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

    private boolean findInArticleExists() {
        return !driver.findElements(AppiumBy.id("page_find_in_article")).isEmpty();
    }

    private boolean searchContainerExists() {
        return !driver.findElements(AppiumBy.id("search_container")).isEmpty();
    }
}
