package WebdriverPro.web.tests;

import org.apache.log4j.Logger;
import org.testng.annotations.Test;


import WebdriverPro.web.framework.WebdriverTestBase;
import WebdriverPro.web.pages.HomePage;

public class LoginTest extends WebdriverTestBase {

	Logger logger = Logger.getLogger(LoginTest.class);	
	
	@Test
	public void verifyLogin(){
		
		logger.info("test started...");
		verify("test", "test");
		
		
		HomePage homePage = new HomePage(driver);
		
		homePage.navigateToHomePage();
		
		verify("test", "abccdef");
		
		homePage.hoverOverMenu("Flights & Deals");
		
		homePage.clickLogin();
		
		homePage.navigateToHomePage();
		System.out.println(driver.getTitle());
		
		logger.info("Title:" + driver.getTitle());
		
		

		
	}
	
}
