package pageobjects;

import org.openqa.selenium.By;

import auto.framework.PageBase;
import auto.framework.ReportLog;
import auto.framework.web.Button;
import auto.framework.web.Page;
import auto.framework.web.TextBox;
import auto.framework.web.WebControl;
import common.TestDataHandler;

public class Guru99LoginPage extends PageBase  {

	public static Page page = new Page("Guru99 Login Page", "https://demo.guru99.com/insurance/v1/index.php");
	//always add the page we visit
	//always add the protocol - http(s)

	//add subclass(es)
	public static class LoginPage {
		//after declaring you subclass, it is now time for you to declare the locators	
		private static TextBox _textBoxEmail = new TextBox("Email textbox", By.xpath("//input[@name='email']"));
		private static TextBox _textBoxPassword = new TextBox("Password textbox", By.xpath("//input[@name='password']"));
		private static Button _buttonLogin = new Button("Login Button", By.xpath("//input[@name='submit']"));

		//add methods - this is where the actions are
		//when creating methods, you have to consider test data. If there is a test data, always add (TestDataHandler testDataHandler)
		public static void fillupLoginForm(TestDataHandler testDataHandler) {
			//setTestStep - we are naming the test step that will reflect from our report
			ReportLog.setTestStep("Fillup login form");
			_textBoxEmail.waitForExist(true, 15);
			_textBoxEmail.type(testDataHandler.Email);
			_textBoxPassword.waitForExist(true, 15);
			_textBoxPassword.type(testDataHandler.Password);
		}


		public static void clickLogin() {
			//setTestStep - we are naming the test step that will reflect from our report
			ReportLog.setTestStep("Click Login");
			_buttonLogin.waitForExist(true, 15);
			_buttonLogin.click();
			WebControl.waitForPageToLoad(60);
		}
	}
}