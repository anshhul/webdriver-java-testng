package WebdriverPro.web;

import org.apache.log4j.Logger;
import org.testng.annotations.Test;


import WebdriverPro.web.framework.PropertyManager;


public class Script extends BasePage {
	
	Logger logger = Logger.getLogger(Script.class) ;
	
	@Test
	public void testCase(){
		logger.info("test loffer INFO");
		
		driver.get("https://www.facebook.com");
		System.out.println(driver.getTitle());
		
		PropertyManager.getProperty("testkey");
	}
	

}
