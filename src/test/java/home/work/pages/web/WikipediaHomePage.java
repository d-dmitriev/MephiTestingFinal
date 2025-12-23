package home.work.pages.web;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static home.work.utils.Helpers.waitBy;

public class WikipediaHomePage {
    public static final By SUBMIT_BUTTON = By.xpath("//button[@type='submit']");

    public static final By FIRST_SUGGESTION = By.cssSelector("div.suggestions-dropdown a");
    public static final By SUGGESTIONS_DROPDOWN = By.cssSelector(".suggestions-dropdown");
    public static final By SUGGESTION_LINK = By.cssSelector("a.suggestion-link");

    public static final By SEARCH_INPUT = By.id("searchInput");
    public static final By PAGE_HEADING = By.id("firstHeading");

    public static final String URL = "https://www.wikipedia.org/";
    public static final String WIKIPEDIA_ORG = ".wikipedia.org";

    private final WebDriver driver;
    private final WebDriverWait wait;

    public WikipediaHomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public void open() {
        driver.get(URL);
        waitBy(wait, SEARCH_INPUT);
    }

    public void searchFor(String query) {
        waitBy(wait, SEARCH_INPUT).sendKeys(query);
        waitBy(wait, SUBMIT_BUTTON).click();
    }

    public String getPageHeader() {
        return waitBy(wait, PAGE_HEADING).getText();
    }

    public boolean isSearchInputVisible() {
        return waitBy(wait, SEARCH_INPUT).isDisplayed();
    }

    public void selectLanguage(String langCode) {
        WebElement langLink = driver.findElement(By.cssSelector("a[href='//" + langCode + WIKIPEDIA_ORG + "/']"));
        langLink.click();
        wait.until(ExpectedConditions.urlContains(langCode + WIKIPEDIA_ORG));
    }

    public boolean isFooterLinkPresent(String linkText) {
        try {
            return waitBy(wait, By.xpath("//footer//a[normalize-space()='" + linkText + "']")).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void enterSearchQuery(String query) {
        WebElement searchInput = waitBy(wait, SEARCH_INPUT);
        searchInput.clear(); // на случай, если что-то уже введено
        searchInput.sendKeys(query);
    }

    public List<WebElement> listSuggestions() {
        waitBy(wait, SUGGESTIONS_DROPDOWN);
        return driver.findElements(SUGGESTION_LINK);
    }

    public void clickFirstSuggestion() {
        waitBy(wait, FIRST_SUGGESTION).click();
    }
}