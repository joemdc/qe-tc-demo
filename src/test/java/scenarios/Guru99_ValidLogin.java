package scenarios;

import java.io.IOException;
import org.testng.ITestContext;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import auto.framework.ReportLog;
import auto.framework.TestBase;
import auto.framework.web.WebControl;
import common.BrowserDriver;
import common.TestDataHandler;
import pageobjects.Guru99HomeTabPage;
import pageobjects.Guru99LoginPage;
import pageobjects.Guru99RequestQuotationPage;

public class Guru99_ValidLogin extends TestBase {

	@Override
	@BeforeTest
	  public void startTest(ITestContext context) throws IOException {
		BrowserDriver.startTestMethod(context);
	  }

	@Test
	public void testScenario() throws Exception {
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

}