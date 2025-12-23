package home.work.utils;

import home.work.config.ConfigReader;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;

public class MobileDriverFactory {
    public static AppiumDriver createDriver() throws MalformedURLException {
        DesiredCapabilities caps = new DesiredCapabilities();
        // загрузка из config.properties!
        caps.setCapability("platformName", ConfigReader.getProperty("platform.name"));
        caps.setCapability("platformVersion", ConfigReader.getProperty("platform.version"));
        caps.setCapability("deviceName", ConfigReader.getProperty("device.name"));
        caps.setCapability("appPackage", ConfigReader.getProperty("app.package"));
        caps.setCapability("appActivity", ConfigReader.getProperty("app.activity"));
        caps.setCapability("automationName", "UiAutomator2");
        caps.setCapability("noReset", false);

        return new AndroidDriver(new URL("http://127.0.0.1:4723"), caps);
    }
}