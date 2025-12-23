package home.work.utils;

import home.work.config.ConfigReader;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;

public class MobileDriverFactory {
    public static AppiumDriver createDriver() throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        // загрузка из config.properties!
        capabilities.setCapability("platformName", ConfigReader.getProperty("platform.name"));
        capabilities.setCapability("platformVersion", ConfigReader.getProperty("platform.version"));
        capabilities.setCapability("deviceName", ConfigReader.getProperty("device.name"));
        capabilities.setCapability("appPackage", ConfigReader.getProperty("app.package"));
        capabilities.setCapability("appActivity", ConfigReader.getProperty("app.activity"));
        capabilities.setCapability("automationName", "UiAutomator2");
        capabilities.setCapability("noReset", false);

        return new AndroidDriver(new URL(ConfigReader.getProperty("remote.address")), capabilities);
    }
}