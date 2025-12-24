package home.work.utils;

import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.TestListenerAdapter;

public class DescriptionListener extends TestListenerAdapter {
    @Override
    public void onTestStart(ITestResult result) {
        String description = result.getMethod().getDescription();
        if (description != null && !description.isEmpty()) {
            Reporter.log(description);
        }
    }
}
