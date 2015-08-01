package WebdriverPro.web.framework;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class WebdriverManager {
	
	
	public static WebDriver getDriver(){
		
		WebDriver driver;
		
		String browser = PropertyManager.getProperty("browser");
		
		
		if("firefox".equalsIgnoreCase(browser)){
			
			driver = new FirefoxDriver();
			
		} else if("chrome".equalsIgnoreCase(browser)){
			
			String path = null;
			
			try {
				  path = new File(PropertyManager.getProperty("chromeDriver")).getCanonicalPath();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			System.setProperty("webdriver.chrome.driver", path);
			driver = new ChromeDriver();
			
		} else if("ie".equalsIgnoreCase(browser)){
			
			driver = new InternetExplorerDriver();
			
		} else {
			
			driver = new HtmlUnitDriver();
		}
		
		return driver;
		
	}
	
	

}
