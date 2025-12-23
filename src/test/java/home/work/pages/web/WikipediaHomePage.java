package home.work.pages.web;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;

public class WikipediaHomePage {
    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(id = "searchInput")
    private WebElement searchInput;

    @FindBy(xpath = "//button[@type='submit']")
    private WebElement searchButton;

    public WikipediaHomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public void open() {
        driver.get("https://www.wikipedia.org/");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("searchInput")));
    }

    public void searchFor(String query) {
        searchInput.sendKeys(query);
        searchButton.click();
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    public boolean isSearchInputVisible() {
        return searchInput.isDisplayed();
    }

    public void selectLanguage(String langCode) {
        WebElement langLink = driver.findElement(By.cssSelector("a[href='//" + langCode + ".wikipedia.org/']"));
        langLink.click();
        wait.until(ExpectedConditions.urlContains(langCode + ".wikipedia.org"));
    }
}