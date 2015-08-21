package WebdriverPro.web.framework;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class WebdriverManager {
	
	
	public static WebDriver getDriver() throws MalformedURLException{
		
		WebDriver driver;
		
		/**
		* Creates an authentication instance using the supplied user name/access key.
		*/

		  String USERNAME = "jitutest";
		  String ACCESS_KEY = "cee24344-0a9c-401a-b275-4f748095688d";
		  String URL = "http://" + USERNAME + ":" + ACCESS_KEY + "@ondemand.saucelabs.com:80/wd/hub";

		
		
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
			
		} else if("saucelabs".equalsIgnoreCase(browser)){
			
			/**
			* Represents the browser type, version, and operating system to be used as part * of the test run.
			*/


			    DesiredCapabilities caps = DesiredCapabilities.firefox();
			    caps.setCapability("platform", "Windows 7");
			    caps.setCapability("version", "30.0");
			    caps.setCapability("name", "Sauce Sample Test");

			     driver = new RemoteWebDriver(new java.net.URL(URL) , caps);
			
		} else {
			
			driver = new HtmlUnitDriver();
		}
		
		return driver;
		
	}
	
	

}
