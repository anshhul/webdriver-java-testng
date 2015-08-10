package WebdriverPro.web.tests;

import org.apache.log4j.Logger;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;


import WebdriverPro.web.framework.WebdriverTestBase;
import WebdriverPro.web.pages.HomePage;

public class ParameterTestNG extends WebdriverTestBase {

	Logger logger = Logger.getLogger(ParameterTestNG.class);	
	
	@Test
	@Parameters({"user", "email"})
	public void verifyTestNGParameters(@Optional("Optional User")String user, String email){
		
		logger.info("Test Method started: verifyTestNGParameters()");
		System.out.println("User: "+user+" and Email Address: "+email);
		
	}
	
}
