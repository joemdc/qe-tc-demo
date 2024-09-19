package scenarios;

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
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import auto.framework.ReportLog;
import auto.framework.TestBase;
import auto.framework.TestManager;
import auto.framework.WebManager;
import auto.framework.web.WebControl;
import common.TestDataHandler;
import pageobjects.Guru99HomeTabPage;
import pageobjects.Guru99LoginPage;
import pageobjects.Guru99RequestQuotationPage;


public class Guru99_ValidLogin extends TestBase {

	@Override
	@BeforeTest
	  public void startTest(ITestContext context) throws IOException {
		System.out.println("Hey!!");
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

	
	//make sure to delete all unused importations
	//delete all unnecessary comments when presenting

	@Test
	public void testScenario() throws Exception {
		System.out.println("1!!");
		TestDataHandler testDataHandler = TestDataHandler.loadTestData("Guru99", "RowSelection='Guru99_SignInWithRegisteredUserTest'");		
		//Guru99 is the Test Sheet name
		//Guru99_SignInWithRegisteredUserTest is the value of RowSelection in Excel file

		ReportLog.setTestName("Guru99- SignInFunctionality");
		ReportLog.setTestCase("Valid Login in Guru99");		

		//open browser
		WebControl.open(testDataHandler.URL);
		WebControl.waitForPageToLoad(60);

		//call out the methods created in the pageobjects
		Guru99LoginPage.LoginPage.fillupLoginForm(testDataHandler);
		Guru99LoginPage.LoginPage.clickLogin();
		Guru99HomeTabPage.HomePage.verifyLogin(testDataHandler);
		Guru99HomeTabPage.HomePage.clickRequestQuotation();
		Guru99RequestQuotationPage.ReqestQuotationPage.fillupRequestQuotation(testDataHandler);
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
        
		return new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), options);
	}
}