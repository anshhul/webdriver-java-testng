package WebdriverPro.web.framework.utils;

import org.testng.annotations.DataProvider;

public class DataProviderClass {
	
	@DataProvider(name="testDataProviderClass")
	public static Object[][]  getData(){
		return new Object[][]{
		{"Mary","maryHopkin@gmail.com"},
		{"Gary","garyBakwoski@gmail.com"},
	};
}
	
}
