package home.work.pages.web;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static home.work.utils.Helpers.waitBy;

public class WikipediaHomePage {
    public static final By SEARCH_INPUT = By.id("searchInput");
    public static final By SUBMIT_BUTTON = By.xpath("//button[@type='submit']");

    private final WebDriver driver;
    private final WebDriverWait wait;

    public WikipediaHomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public void open() {
        driver.get("https://www.wikipedia.org/");
        waitBy(wait, SEARCH_INPUT);
    }

    public void searchFor(String query) {
        waitBy(wait, SEARCH_INPUT).sendKeys(query);
        waitBy(wait, SUBMIT_BUTTON).click();
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    public boolean isSearchInputVisible() {
        return waitBy(wait, SEARCH_INPUT).isDisplayed();
    }

    public void selectLanguage(String langCode) {
        WebElement langLink = driver.findElement(By.cssSelector("a[href='//" + langCode + ".wikipedia.org/']"));
        langLink.click();
        wait.until(ExpectedConditions.urlContains(langCode + ".wikipedia.org"));
    }
}