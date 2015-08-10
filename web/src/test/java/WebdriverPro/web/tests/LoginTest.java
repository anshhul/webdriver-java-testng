package WebdriverPro.web.tests;

import org.apache.log4j.Logger;
import org.testng.annotations.Test;


import WebdriverPro.web.framework.WebdriverTestBase;
import WebdriverPro.web.pages.HomePage;

public class LoginTest extends WebdriverTestBase {

	Logger logger = Logger.getLogger(LoginTest.class);	
	
	@Test
	public void verifyHomePage(){
		
		logger.info("Test Method started: verifyHomePage()");
		
		HomePage homePage = new HomePage(driver);
		
		homePage.navigateToHomePage();
		
		homePage.selectEconomy("Business");
		
		homePage.SelectMenuOptions("Corporate Customers", "PartnerPlusBenefit");
		homePage.switchToNewWindow();
		homePage.clickEurope();
		homePage.switchBackToBaseWindow();
			
		homePage.clickLogin();
		
		homePage.navigateToHomePage();
		System.out.println(driver.getTitle());
		
		logger.info("Title:" + driver.getTitle());
		
	}
	
	
	
	@Test
	public void test2(){
		
	}
	
}
