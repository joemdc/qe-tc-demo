package common;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.SkipException;
import auto.framework.TestBase.Regex;
import auto.framework.TestManager;
import auto.framework.WebManager;

public class BrowserDriver {
    public static void startTestMethod(ITestContext context) throws IOException {
		String executePref = TestManager.Preferences.getPreference("execute","true");
		Boolean executeScript = Boolean.valueOf(executePref);
		if(!executeScript && executePref!=null ) {
			String var = Regex.findMatch(executePref, "(.*?)(?=\\[)");
			String label = Regex.findMatch(executePref, "(?<=\\[)(.*?)(?=\\])");
			if(var!=null && label!=null){
				String varValue = TestManager.Preferences.getPreference(var,"");
				if(Regex.matches(varValue, "^(?<=\\\")(.*?)(?=\\\")$")){
					varValue = varValue.substring(1, varValue.length());
				}
				String[] varValues = varValue.split(",");
				executeScript = Arrays.asList(varValues).contains(label);
			}
		}
		if(!executeScript){
			System.out.println("Test Skipped: "+context.getCurrentXmlTest().getName());
			throw new SkipException ("Skipping Test");	
		}
		  
		String runWebdriver = TestManager.Preferences.getPreference("runWebdriver","true");
		
		if(!runWebdriver.equalsIgnoreCase("false")) {
		
			Boolean defaultSuite = context.getCurrentXmlTest().getSuite().getName().equals("Default suite");
			Boolean defaultTest = defaultSuite && context.getCurrentXmlTest().getName().equals("Default test");
	
			String browserName = TestManager.Preferences.getPreference("browser");
			try {
				if(defaultTest){
					System.err.println( "Test Run mode" );
					browserName = "chrome";
					WebManager.startDriver(browserName);
				} else {
					WebManager.startDriver(browserName);
				}
			} catch (Exception e) {
				System.out.println("startTest failure is catched!");
				WebDriver driver = newChromeDriver();
				WebManager.setDriver(driver);
			}
		}
    }

    
    private static WebDriver newChromeDriver() throws IOException {
    	String chromeDriverPath;

		try {
			chromeDriverPath = System.getProperty("user.dir") + "/src/main/resources/webdriver/chromedriver.exe";
			System.setProperty("webdriver.chrome.driver", chromeDriverPath);
			System.out.println("Chrome Driver: " + chromeDriverPath);
		} catch (Error error) {
			throw new Error("Chrome Driver not found");
		}

		System.setProperty("webdriver.chrome.driver", chromeDriverPath);

		ChromeOptions options = new ChromeOptions();
		options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
		options.addArguments("test-type");
		options.addArguments("start-maximized");
		
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

		options.addArguments("enable-automation");
		options.addArguments("--disable-extensions");
		options.addArguments("--disable-infobars"); //https://stackoverflow.com/a/43840128/1689770
        try {
			return new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), options);
		} catch (Exception e) {
			return new ChromeDriver();
		}
	}
}
