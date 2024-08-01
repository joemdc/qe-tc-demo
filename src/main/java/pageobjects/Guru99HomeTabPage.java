package pageobjects;

import org.openqa.selenium.By;

import auto.framework.PageBase;
import auto.framework.ReportLog;
import auto.framework.web.Button;
import auto.framework.web.Element;
import auto.framework.web.Page;
import auto.framework.web.WebControl;
import common.TestDataHandler;

public class Guru99HomeTabPage extends PageBase {

	public static Page page = new Page("Guru99 Home Page", "https://demo.guru99.com/insurance/v1/header.php");
	//always add the page we visit
	//always add the protocol - http(s)

	//add subclass(es)
	public static class HomePage {
		//after declaring you subclass, it is now time for you to declare the locators	
		private static Element _header = new Element("Header", By.xpath("(//h2)[1]"));
		private static Button _requestQuotationButton = new Button("Request Quotation Button", By.xpath("//a[@id='ui-id-2']"));

		//add methods - this is where the actions are
		//when creating methods, you have to consider test data. If there is a test data, always add (TestDataHandler testDataHandler)
		public static void verifyLogin(TestDataHandler testDataHandler) {
			//setTestStep - we are naming the test step that will reflect from our report
			ReportLog.setTestStep("Verify the header");
			_header.waitForExist(true, 15);
			_header.verifyText(testDataHandler.HeaderText);
		}


		public static void clickRequestQuotation() {
			//setTestStep - we are naming the test step that will reflect from our report
			ReportLog.setTestStep("Click the Request Quotation Button");
			_requestQuotationButton.waitForExist(true, 15);
			_requestQuotationButton.click();
			WebControl.waitForPageToLoad(60);
		}

	}
}
