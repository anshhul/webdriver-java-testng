package WebdriverPro.web;

import org.apache.log4j.Logger;
import org.testng.annotations.Test;


import WebdriverPro.web.framework.PropertyManager;
//import WebdriverPro.web.framework.WebdriverTestBase;
import WebdriverPro.web.framework.WebdriverTestBase;


public class Script extends WebdriverTestBase {
	
	Logger logger = Logger.getLogger(Script.class) ;
	
	@Test
	public void testCase(){
		logger.info("test loffer INFO");
		
		driver.get("https://www.facebook.com");
		System.out.println(driver.getTitle());
		
		PropertyManager.getProperty("testkey");
	}
	

}
