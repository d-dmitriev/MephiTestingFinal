package home.work.utils;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Helpers {
    public static WebElement waitBy(WebDriverWait wait, By by) {
        System.out.println("!!! waitBy " + by.toString());
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    public static void sleep() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean existsById(WebDriver driver, String id) {
        var r = !driver.findElements(AppiumBy.id(id)).isEmpty();
        System.out.println("!!! exists " + id + " " + r);
        return r;
    }
}
