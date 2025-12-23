package home.work.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Helpers {
    public static WebElement waitBy(WebDriverWait wait, By by) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }
}
