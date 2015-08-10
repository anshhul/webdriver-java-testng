package WebdriverPro.web.tests;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import WebdriverPro.web.framework.WebdriverTestBase;

public class DataProviderClassTest extends WebdriverTestBase {

	Logger logger = Logger.getLogger(DataProviderClassTest.class);	
	
	
	@DataProvider(name="testDataProviderClass")
	public static Object[][]  getData(Method m){
		if(m.getName().equals("testDataProviderInTest")){
			return new Object[][]{
				{"Mary","maryHopkin@gmail.com"},
				{"Gary","garyBakwoski@gmail.com"},
			};
		} else {
			
			return new Object[][]{
				{"Mary"},
				{"Gary"},
				{"Kretose"}
			};
			
		}
		
		
}
	@Test(dataProvider="testDataProviderClass")
	public void testDataProviderInTest(String user, String email){
		
		logger.info("Test Method started: testDataProviderInTest()");
		System.out.println("User: "+user+" and Email Address: "+email);
		
	}
	
	
	@Test(dataProvider="testDataProviderClass")
	public void testDataProviderInTestMethod(String user){
		
		logger.info("Test Method started: testDataProviderInTestMethod()");
		System.out.println("User: "+user);
		
	}
	
}
