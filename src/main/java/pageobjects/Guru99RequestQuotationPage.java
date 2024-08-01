package pageobjects;

import org.openqa.selenium.By;

import auto.framework.PageBase;
import auto.framework.ReportLog;
import auto.framework.web.ListBox;
import auto.framework.web.Page;
import auto.framework.web.RadioButton;
import common.TestDataHandler;

public class Guru99RequestQuotationPage extends PageBase {
	public static Page page = new Page("Guru99 Request Quoatation Page", "https://demo.guru99.com/insurance/v1/header.php");
	//always add the page we visit
	//always add the protocol - http(s)

	//add subclass(es)
	public static class ReqestQuotationPage {
		//after declaring you subclass, it is now time for you to declare the locators
		private static ListBox _breakDownCover = new ListBox("Header", By.xpath("//select[@name='0']"));
		private static RadioButton _yesWindScreenRepairRadioButton = new RadioButton("Yes button", By.xpath("(//input[@name='windscreenrepair'])[1]"));


		//add methods - this is where the actions are
		//when creating methods, you have to consider test data. If there is a test data, always add (TestDataHandler testDataHandler)
		public static void fillupRequestQuotation(TestDataHandler testDataHandler) {
			//setTestStep - we are naming the test step that will reflect from our report
			ReportLog.setTestStep("Fill up the request quoation form");
			_breakDownCover.waitForExist(true, 15);
			_breakDownCover.selectByVisibleText(testDataHandler.BreakdownCover);
			_yesWindScreenRepairRadioButton.waitForExist(true, 15);
			_yesWindScreenRepairRadioButton.click();
		}	
	}
}