package WebdriverPro.web.tests;

import org.apache.log4j.Logger;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;


import WebdriverPro.web.framework.WebdriverTestBase;
import WebdriverPro.web.framework.utils.DataProviderClass;
import WebdriverPro.web.pages.HomePage;

public class DataProviderInTest extends WebdriverTestBase {

	Logger logger = Logger.getLogger(DataProviderInTest.class);	
	
	@Test(dataProviderClass=DataProviderClass.class, dataProvider="testDataProviderClass")
	public void verifyDataProviderClass(String user, String email){
		
		logger.info("Test Method started: testDataProviderClass()");
		System.out.println("User: "+user+" and Email Address: "+email);
		
	}
	
}
