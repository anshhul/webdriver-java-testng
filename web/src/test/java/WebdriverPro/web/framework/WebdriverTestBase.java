package WebdriverPro.web.framework;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;



public abstract class WebdriverTestBase {
	
	protected WebDriver driver;
	
	@BeforeTest
	public void launchBrowser(){
		driver = new FirefoxDriver();
		driver.manage().window().maximize();
		
	}
	
	@AfterTest
	public void quitBrowser(){
		driver.quit();
	}
	
	

}
